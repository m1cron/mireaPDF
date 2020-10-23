package ru.micron;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import ru.micron.interfaces.ReadStringFromURL;

import java.net.InetSocketAddress;
import java.net.Proxy;

public class MyProxy implements ReadStringFromURL {
    protected final Gson gson;
    protected String ip;
    protected int port;
    protected int proxyMode;
    protected Proxy proxy;
    protected final String proxyPing;

    public MyProxy() {
        this.gson = new Gson();
        this.proxyPing = "100";
        this.proxy = getNewProxy();
    }

    public MyProxy(Gson gson, String proxyPing) {
        this.gson = gson;
        this.proxyPing = proxyPing;
        this.proxy = getNewProxy();
    }

    private void TakeProxyInfo() {
        String proxyApi = "https://www.proxyscan.io/api/proxy?format=json&uptime=70&not_country=cn,nl,us&last_check=600&ping=";
        JsonArray proxy = gson.fromJson(readStringFromURL(proxyApi + proxyPing), JsonArray.class);
        JsonObject proxyObj = (JsonObject) proxy.get(0);

        ip = proxyObj.get("Ip").toString().replace("\"", " ").trim();
        port = proxyObj.get("Port").getAsInt();
        String mode = proxyObj.getAsJsonArray("Type").get(0).toString();

        System.out.printf("proxy info -->>\t%s\t%d\t%s\n", ip, port, mode);

        if (mode.equals("\"SOCKS4\""))
            proxyMode = 4;
        else if (mode.equals("\"SOCKS5\""))
            proxyMode = 5;
        else
            proxyMode = 0;
    }

    public Proxy getNewProxy() {
        TakeProxyInfo();
        if (proxyMode != 0) {
            System.setProperty("socksProxyVersion", Integer.toString(proxyMode));
            proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(ip, port));
            System.out.printf("connect to -->> %s\t%d\tping -> %s\tSOCKS v%d\n", ip, port, proxyPing, proxyMode);
        } else {
            proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port));
            System.out.printf("connect to -->> %s\t%d\tping -> %s\tHTTP\n", ip, port, proxyPing);
        }
        return proxy;
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

    public String getProxyPing() {
        return proxyPing;
    }

    public int getProxyMode() {
        return proxyMode;
    }
}