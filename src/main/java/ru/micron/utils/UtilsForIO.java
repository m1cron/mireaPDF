package ru.micron.utils;

import com.google.common.io.Resources;
import ru.micron.json.MyProxy;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

abstract public class UtilsForIO {

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

    public static String readStringFromURL(String url, MyProxy myProxyproxy) {
        InputStream urlCon = null;
        try {
            urlCon = new URL(url).openConnection(myProxyproxy.getProxy()).getInputStream();
        } catch (IOException e) {
            try {
                urlCon = new URL(url).openConnection(myProxyproxy.getNewProxy()).getInputStream();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        assert urlCon != null;
        Scanner scanner = new Scanner(urlCon, Charset.defaultCharset()).useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "";
    }

    public static String readStringFromURL(String url) {
        InputStream urlCon = null;
        try {
            urlCon = new URL(url).openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert urlCon != null;
        Scanner scanner = new Scanner(urlCon, Charset.defaultCharset()).useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "";
    }
}