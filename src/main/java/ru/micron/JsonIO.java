package ru.micron;

public interface JsonIO<T> {
    void saveJson(T obj);
    T getJson();
}
