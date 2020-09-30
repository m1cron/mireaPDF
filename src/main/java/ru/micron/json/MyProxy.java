package ru.micron.json;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import ru.micron.utils.UtilsForIO;

import java.net.InetSocketAddress;
import java.net.Proxy;

public class MyProxy extends UtilsForIO {
    private final Gson gson;
    private String ip;
    private int port;
    private int proxyMode;
    private Proxy proxy;

    public MyProxy(Gson gson) {
        this.gson = gson;
    }

    private void TakeProxyInfo() {
        String proxyApi = "https://www.proxyscan.io/api/proxy?format=json&ping=80";                  // << ---- proxy max ping switch
        JsonArray proxy = gson.fromJson(UtilsForIO.readStringFromURL(proxyApi), JsonArray.class);
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
            System.out.printf("connect to -->> %s\t%d\tSOCKS v%d\n", getIp(), getPort(), proxyMode);
        }
        else{
            proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port));
            System.out.printf("connect to -->> %s\t%d\tHTTP %d\n", getIp(), getPort(), proxyMode);
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

    public int getProxyMode() {
        return proxyMode;
    }
}
