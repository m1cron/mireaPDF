package ru.micron;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

public class MyProxy {

    private final String proxyApi;
    private volatile Proxy proxy;
    protected final Gson gson;
    protected final boolean useProxy;
    private static final Logger logger;

    static {
        logger = LoggerFactory.getLogger(MyProxy.class);
    }

    public MyProxy(String proxyPing, boolean useProxy) {
        this.proxyApi = String.format("https://www.proxyscan.io/api/proxy?format=json&uptime=75&last_check=600&ping=%s", proxyPing);
        this.gson = new Gson();
        if ((this.useProxy = useProxy)) {
            logger.info("Using proxy in this case!");
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
        proxy = new Proxy(proxyMode.contains("SOCKS") ? Proxy.Type.SOCKS : Proxy.Type.HTTP, new InetSocketAddress(ip, port));
        logger.info(String.format("getNewProxy >> connect to %s\t\t\t%d\t\t%s\t\tfrom  %s", ip, port, proxyMode, Thread.currentThread().getName()));
        return proxy;
    }

    public String readStringFromURL(String url) {
        URLConnection urlCon;
        try {
            urlCon = new URL(url).openConnection();
            urlCon.setConnectTimeout(2000);
            urlCon.setReadTimeout(3000);
            return scanInStream(urlCon.getInputStream());
        } catch (IOException e) {
            logger.info("Enabled proxy!");
            return readStringFromURL(url, getNewProxy());
        }
    }

    public String readStringFromURL(String url, Proxy myProxy) {
        URLConnection urlCon;
        try {
            urlCon = new URL(url).openConnection(myProxy);
            urlCon.setConnectTimeout(2000);
            urlCon.setReadTimeout(3000);
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
            e.printStackTrace();
        }
        return response.toString();
    }

    public Proxy getProxy() {
        return proxy;
    }
}