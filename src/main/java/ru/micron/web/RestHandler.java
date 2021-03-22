package ru.micron.web;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class RestHandler {

  private final List<String> proxyList;
  private final RestTemplate rest;
  private final AtomicInteger atomicInteger;
  private RestTemplate proxyRest;

  public RestHandler() {
    this.atomicInteger = new AtomicInteger(0);
    this.proxyList = new ArrayList<>();
    this.rest = new RestTemplate();
  }

  public <T> T getObject(String url, Class<T> responseType)
      throws HttpClientErrorException.NotFound
  {
    try {
      return rest.getForObject(url, responseType);
    } catch (HttpClientErrorException.Forbidden e) {
      log.info("Proxing!");
      return getObjectWithProxy(url, responseType);
    }
  }

  public <T> T getObjectWithProxy(String url, Class<T> responseType)
      throws HttpClientErrorException.NotFound
  {
    try {
      getNewRest();
      return proxyRest.getForObject(url, responseType);
    } catch (HttpClientErrorException.Forbidden | ResourceAccessException e) {
      return getObjectWithProxy(url, responseType);
    }
  }

  private void getNewProxyLists() {
    for (var proxyUrl : WebConstants.PROXY_URLS) {
      try {
        log.info("Getting new proxy list from: {}", proxyUrl);
        Optional.ofNullable(rest.getForObject(proxyUrl, String.class))
            .ifPresent(text -> proxyList.addAll(Arrays.asList(text.split("\\r?\\n"))));
      } catch (HttpServerErrorException.ServiceUnavailable e) {
        log.warn("{} {}", e.getStatusText(), proxyUrl);
      }
    }
    log.info("Total loaded proxies: {}", proxyList.size());
  }

  private synchronized void getNewRest() {
    if (atomicInteger.get() >= proxyList.size() || atomicInteger.get() == 0 || proxyList.size() == 0) {
      getNewProxyLists();
      atomicInteger.set(0);
    }
    var ip_port = proxyList.get(atomicInteger.getAndIncrement()).split(":");
    var proxy = new Proxy(Proxy.Type.HTTP,
        new InetSocketAddress(ip_port[0], Integer.parseInt(ip_port[1])));

    var requestFactory = new SimpleClientHttpRequestFactory();
    requestFactory.setProxy(proxy);
    requestFactory.setConnectTimeout(WebConstants.CONNECT_TIMEOUT_MS);
    requestFactory.setReadTimeout(WebConstants.READ_TIMEOUT_MS);
    proxyRest = new RestTemplate(requestFactory);
    log.info("Proxy connect to {}:{}", ip_port[0], ip_port[1]);
  }
}