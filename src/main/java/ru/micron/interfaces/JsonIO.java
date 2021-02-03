package ru.micron.interfaces;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public interface JsonIO<T> extends MapFilling {

    void saveJson(T obj);

    T getJson(Class<T> jsonClass);

    default String readFile(String fileDir) throws IOException {
        Path fileName = Path.of(fileDir);
        return Files.readString(fileName);
    }

}
