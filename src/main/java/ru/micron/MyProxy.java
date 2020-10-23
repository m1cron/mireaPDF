package ru.micron;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import ru.micron.interfaces.ReadStringFromURL;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

public class MyProxy implements ReadStringFromURL {
    private final String proxyApi;
    private Proxy proxy;
    private String ip;
    private int port;
    protected final Gson gson;

    public MyProxy(String proxyPing) {
        this.proxyApi = "https://www.proxyscan.io/api/proxy?format=json&uptime=75&last_check=600&ping=" + proxyPing;
        this.gson = new Gson();
        getNewProxy();
    }

    public void getNewProxy() {
        JsonArray proxyList = gson.fromJson(readStringFromURL(proxyApi), JsonArray.class);
        JsonObject proxyObj = (JsonObject) proxyList.get(0);

        ip = proxyObj.get("Ip").toString().replace("\"", " ").trim();
        port = proxyObj.get("Port").getAsInt();
        String proxyMode = proxyObj.getAsJsonArray("Type").get(0).toString();

        System.setProperty("socksProxyVersion", proxyMode.contains("4") ? "4" : "5");
        proxy = new Proxy(proxyMode.contains("SOCKS") ? Proxy.Type.SOCKS : Proxy.Type.HTTP, new InetSocketAddress(ip, port));
        System.out.printf("connect to %s\t\t\t%d\t%s\n", ip, port, proxyMode);
    }

    public String readStringFromURL(String url) {
        InputStream urlCon = null;
        try {
            urlCon = new URL(url).openConnection().getInputStream();
        } catch (IOException e) {
            System.out.println("Enabled proxy!");
            readStringFromURL(url, this);
        }
        return scanInStream(urlCon);
    }

    public Proxy getProxy() {
        return proxy;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }
}