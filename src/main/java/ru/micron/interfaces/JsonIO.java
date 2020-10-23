package ru.micron.interfaces;

public interface JsonIO<T> extends MapFilling {
    void saveJson(T obj);
    T getJson(Class<T> jsonClass);
}
