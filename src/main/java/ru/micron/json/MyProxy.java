package ru.micron.json;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import ru.micron.utils.UtilsForIO;

import java.net.InetSocketAddress;
import java.net.Proxy;

public class MyProxy extends UtilsForIO {
    private String ip;
    private int port;
    private int proxyMode;
    private final Gson gson;

    public MyProxy() {
        gson = new Gson();
    }

    private void TakeProxyInfo() {
        String proxyApi = "https://www.proxyscan.io/api/proxy?format=json&ping=50";
        JsonArray proxy = gson.fromJson(UtilsForIO.readStringFromURL(proxyApi), JsonArray.class);
        JsonObject proxyObj = (JsonObject) proxy.get(0);

        ip = proxyObj.get("Ip").toString().replace("\"", " ").trim();
        port = proxyObj.get("Port").getAsInt();

        String mode = proxyObj.getAsJsonArray("Type").get(0).toString();
        if (mode.equals("\"SOCKS4\""))
            proxyMode = 4;
        else if (mode.equals("\"SOCKS5\""))
            proxyMode = 5;
        else
            proxyMode = 0;
    }

    public Proxy getProxy() {
        TakeProxyInfo();
        Proxy proxy = null;
        if (proxyMode != 0) {
            System.setProperty("socksProxyVersion", Integer.toString(proxyMode));
            proxy = new java.net.Proxy(Proxy.Type.SOCKS, new InetSocketAddress(ip, port));
        }
        else
            proxy = new java.net.Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port));
        return proxy;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public int getProxyMode() {
        return proxyMode;
    }
}
