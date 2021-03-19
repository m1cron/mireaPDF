package ru.micron.web;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyProxy {

  @Getter
  private volatile Proxy proxy;
  private final String proxyApi;
  protected final Gson gson;
  protected final boolean useProxy;

  public MyProxy(String proxyPing, boolean useProxy) {
    this.proxyApi = String.format(WebConstants.PROXY_URL, proxyPing);
    this.gson = new Gson();
    if ((this.useProxy = useProxy)) {
      log.info("Using proxy in this case!");
      getNewProxy();
    }
  }

  public synchronized Proxy getNewProxy() {
    JsonArray proxyList = gson.fromJson(readStringFromURL(proxyApi), JsonArray.class);
    JsonObject proxyObj = (JsonObject) proxyList.get(0);

    String ip = proxyObj.get("Ip").toString().replace("\"", "");
    int port = proxyObj.get("Port").getAsInt();
    String proxyMode = proxyObj.getAsJsonArray("Type").get(0).toString();

    System.setProperty("socksProxyVersion", proxyMode.contains("4") ? "4" : "5");
    proxy = new Proxy(proxyMode.contains("SOCKS") ? Proxy.Type.SOCKS : Proxy.Type.HTTP,
        new InetSocketAddress(ip, port));
    log.info("proxy > connect to {}:{} {}\tfrom {}", ip, port, proxyMode,
        Thread.currentThread().getName());
    return proxy;
  }

  public String readStringFromURL(String url) {
    URLConnection urlCon;
    try {
      urlCon = new URL(url).openConnection();
      urlCon.setConnectTimeout(WebConstants.CONNECT_TIMEOUT_MS);
      urlCon.setReadTimeout(WebConstants.READ_TIMEOUT_MS);
      return scanInStream(urlCon.getInputStream());
    } catch (IOException e) {
      log.info("Enabled proxy!");
      return readStringFromURL(url, getNewProxy());
    }
  }

  public String readStringFromURL(String url, Proxy myProxy) {
    URLConnection urlCon;
    try {
      urlCon = new URL(url).openConnection(myProxy);
      urlCon.setConnectTimeout(WebConstants.CONNECT_TIMEOUT_MS);
      urlCon.setReadTimeout(WebConstants.READ_TIMEOUT_MS);
      return scanInStream(urlCon.getInputStream());
    } catch (IOException e) {
      return readStringFromURL(url, getNewProxy());
    }
  }

  private String scanInStream(InputStream stream) {
    String inputLine;
    StringBuilder response = new StringBuilder();

    try (BufferedReader in = new BufferedReader(new InputStreamReader(stream))) {
      while ((inputLine = in.readLine()) != null) {
        response.append(inputLine).append("\n");
      }
    } catch (IOException e) {
      log.warn(e.getMessage());
    }
    return response.toString();
  }

}