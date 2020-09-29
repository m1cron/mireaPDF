package ru.micron.utils;

import com.google.common.io.Resources;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

abstract public class UtilsForIO {

    public static Proxy setProxy(String ip, int port, boolean socksOrNo) {
        System.setProperty("socksProxyVersion", "4");
        Proxy proxy = null;
        if (socksOrNo)
            proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(ip, port));
        else
            proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port));
        return proxy;
    }

    public static String readFileFromRes(final String fileName, Charset charset) throws IOException {
        return Resources.toString(Resources.getResource(fileName), charset);
    }

    public static void writeFile(String fileDir, String str) throws IOException {
        Path fileName = Path.of(fileDir);
        Files.writeString(fileName, str);
    }

    public static String readFile(String fileDir) throws IOException {
        Path fileName = Path.of(fileDir);
        return Files.readString(fileName);
    }

    public static String readStringFromURL(String url, Proxy proxy) {
        try {
            Scanner scanner = new Scanner(new URL(url).openConnection(proxy).getInputStream(), Charset.defaultCharset()).useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : "";
        } catch (IOException e) {
            e.printStackTrace();
            return "null";
        }
    }
}