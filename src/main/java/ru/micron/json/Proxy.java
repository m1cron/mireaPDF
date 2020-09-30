package ru.micron.json;

import ru.micron.utils.UtilsForIO;

public class Proxy extends UtilsForIO {
    private static final String proxyApi = "https://www.proxyscan.io/api/proxy?format=json&type=http,https,socks4&ping=150";

    public static void start() {
        System.out.println(UtilsForIO.readStringFromURL(proxyApi));
    }

    public static void main(String[] args) {
        start();
    }
}
