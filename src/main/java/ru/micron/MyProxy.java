package ru.micron;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import ru.micron.interfaces.ReadStringFromURL;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

public class MyProxy implements ReadStringFromURL {
    protected final Gson gson;
    private final String proxyApi;
    private volatile Proxy proxy;

    public MyProxy(String proxyPing) {
        this.proxyApi = "https://www.proxyscan.io/api/proxy?format=json&uptime=75&last_check=600&ping=" + proxyPing;
        this.gson = new Gson();
        getNewProxy();
    }

    public synchronized void getNewProxy() {
        JsonArray proxyList = gson.fromJson(readStringFromURL(proxyApi), JsonArray.class);
        JsonObject proxyObj = (JsonObject) proxyList.get(0);

        String ip = proxyObj.get("Ip").toString().replace("\"", " ").trim();
        int port = proxyObj.get("Port").getAsInt();
        String proxyMode = proxyObj.getAsJsonArray("Type").get(0).toString();

        System.setProperty("socksProxyVersion", proxyMode.contains("4") ? "4" : "5");
        proxy = new Proxy(proxyMode.contains("SOCKS") ? Proxy.Type.SOCKS : Proxy.Type.HTTP, new InetSocketAddress(ip, port));
        System.out.println("getNewProxy >> connect to " + ip + "\t\t\t" + port + "\t\t" + proxyMode + "\t\tfrom  " + Thread.currentThread().getName());
    }

    public String readStringFromURL(String url) {
        URLConnection urlCon;
        try {
            urlCon = new URL(url).openConnection();
            urlCon.setConnectTimeout(2000);
            urlCon.setReadTimeout(3000);
            return scanInStream(urlCon.getInputStream());
        } catch (IOException e) {
            System.out.println("Enabled proxy!");
            return readStringFromURL(url, this);
        }
    }

    public Proxy getProxy() {
        return proxy;
    }
}