package ru.micron.interfaces;

import ru.micron.MyProxy;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Scanner;

public interface ReadStringFromURL {

    default String readStringFromURL(String url) {
        InputStream urlCon = null;
        try {
            urlCon = new URL(url).openConnection().getInputStream();
        } catch (IOException e) {
            System.out.println("Turn on proxy!");
        }
        return scanInStream(urlCon);
    }

    default String readStringFromURL(String url, MyProxy myProxy) {
        InputStream urlCon;
        try {
            urlCon = new URL(url).openConnection(myProxy.getProxy()).getInputStream();
        } catch (IOException e) {
            myProxy.getNewProxy();
            return readStringFromURL(url, myProxy);
        }
        return scanInStream(urlCon);
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