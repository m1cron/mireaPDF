package ru.micron.Utils;

import com.google.common.io.Resources;

import java.io.IOException;
import java.nio.charset.Charset;

abstract public class UtilsForIO {
    public static String readFile(final String fileName, Charset charset) throws IOException {
        return Resources.toString(Resources.getResource(fileName), charset);
    }
}
