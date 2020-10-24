package ru.micron.interfaces;

import ru.micron.MyProxy;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Scanner;

public interface ReadStringFromURL {

    default String readStringFromURL(String url, MyProxy myProxy) {
        URLConnection urlCon;
        try {
            urlCon = new URL(url).openConnection(myProxy.getProxy());
            urlCon.setConnectTimeout(2000);
            urlCon.setReadTimeout(3000);
            return scanInStream(urlCon.getInputStream());
        } catch (IOException e) {
            myProxy.getNewProxy();
            return readStringFromURL(url, myProxy);
        }
    }

    default String scanInStream(InputStream stream) {
        try (Scanner scanner = new Scanner(stream, Charset.defaultCharset()).useDelimiter("\\A")) {
            return scanner.hasNext() ? scanner.next() : "";
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}