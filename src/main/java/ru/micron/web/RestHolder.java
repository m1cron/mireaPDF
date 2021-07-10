package ru.micron.web;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@NoArgsConstructor
public class RestHolder {

  public static final int CONNECT_TIMEOUT_MS = 2000;
  public static final int READ_TIMEOUT_MS = 3000;

  private final List<String> proxyList = new LinkedList<>();
  private final RestTemplate rest = new RestTemplate();
  private final AtomicInteger atomicInteger = new AtomicInteger();
  private RestTemplate proxyRest;

  @Value("${app.proxy-urls}")
  private List<String> proxyUlrList;

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
    for (var proxyUrl : proxyUlrList) {
      try {
        log.info("Getting new proxy list from: {}", proxyUrl);
        Optional.ofNullable(rest.getForObject(proxyUrl, String.class))
            .ifPresent(text -> proxyList.addAll(Arrays.asList(text.split("\\r?\\n"))));
      } catch (HttpServerErrorException.ServiceUnavailable e) {
        log.error("{} {}", e.getStatusText(), proxyUrl);
      }
    }
    atomicInteger.set(0);
    log.info("Total loaded proxies: {}", proxyList.size());
  }

  private synchronized void getNewRest() {
    if (atomicInteger.get() >= proxyList.size() ||
        atomicInteger.get() == 0 ||
        proxyList.size() == 0
    ) {
      getNewProxyLists();
    }
    var host = proxyList.get(atomicInteger.getAndIncrement()).split(":");
    var proxy = new Proxy(Proxy.Type.HTTP,
        new InetSocketAddress(host[0], Integer.parseInt(host[1])));

    var requestFactory = new SimpleClientHttpRequestFactory();
    requestFactory.setProxy(proxy);
    requestFactory.setConnectTimeout(CONNECT_TIMEOUT_MS);
    requestFactory.setReadTimeout(READ_TIMEOUT_MS);
    proxyRest = new RestTemplate(requestFactory);
    log.info("Proxy connect to {}:{}", host[0], host[1]);
  }
}