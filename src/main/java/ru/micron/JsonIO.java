package ru.micron;

import java.util.Map;

public interface JsonIO<T> {
    void saveJson(T obj);
    T getJson();
    void fillMap(Map<String, String> map);
}
