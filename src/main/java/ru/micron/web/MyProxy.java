package ru.micron.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class MyProxy {

  private final List<String> proxyList;
  private final RestTemplate rest;
  private final AtomicInteger atomicInteger;
  @Getter
  private volatile Proxy proxy;
  protected final boolean useProxy;

  public MyProxy(boolean useProxy) {
    this.atomicInteger = new AtomicInteger(0);
    this.proxyList = new ArrayList<>();
    this.rest = new RestTemplate();
    if ((this.useProxy = useProxy)) {
      log.info("Using proxy in this case!");
      getAndAddNewProxyLists();
      getNewProxy();
    }
  }

  public void getAndAddNewProxyLists() {
    for (var proxyUrl : WebConstants.PROXY_URLS) {
      log.info("Getting new proxy list from: {}", proxyUrl);
      Optional.ofNullable(rest.getForObject(proxyUrl, String.class))
          .ifPresent(e ->
              proxyList.addAll(Arrays.asList(e.split("\\r?\\n"))));
    }
  }

  public synchronized Proxy getNewProxy() {
    if (atomicInteger.get() >= proxyList.size()) {
      getAndAddNewProxyLists();
      atomicInteger.set(0);
    }
    var ip_port = proxyList.get(atomicInteger.getAndIncrement()).split(":");
    proxy = new Proxy(Proxy.Type.HTTP,
        new InetSocketAddress(ip_port[0], Integer.parseInt(ip_port[1])));
    log.info("Proxy connect to {} from {}", ip_port, Thread.currentThread().getName());
    return proxy;
  }

  public String readStringFromURL(String url) {
    try {
      URLConnection urlCon = new URL(url).openConnection();
      urlCon.setConnectTimeout(WebConstants.CONNECT_TIMEOUT_MS);
      urlCon.setReadTimeout(WebConstants.READ_TIMEOUT_MS);
      return scanInStream(urlCon.getInputStream());
    } catch (IOException e) {
      log.info("Enabled proxy!");
      return readStringFromURL(url, getNewProxy());
    }
  }

  public String readStringFromURL(String url, Proxy myProxy) {
    try {
      URLConnection urlCon = new URL(url).openConnection(myProxy);
      urlCon.setConnectTimeout(WebConstants.CONNECT_TIMEOUT_MS);
      urlCon.setReadTimeout(WebConstants.READ_TIMEOUT_MS);
      return scanInStream(urlCon.getInputStream());
    } catch (IOException e) {
      return readStringFromURL(url, getNewProxy());
    }
  }

  private String scanInStream(InputStream stream) {
    StringBuilder response = new StringBuilder();
    try (BufferedReader in = new BufferedReader(new InputStreamReader(stream))) {
      String inputLine;
      while ((inputLine = in.readLine()) != null) {
        response.append(inputLine).append("\n");
      }
    } catch (IOException e) {
      log.warn(e.getMessage());
    }
    return response.toString();
  }
}