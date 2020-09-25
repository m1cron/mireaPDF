package ru.micron.Utils;

import com.google.common.io.Resources;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

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
}