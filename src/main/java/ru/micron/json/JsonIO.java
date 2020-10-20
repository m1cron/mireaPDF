package ru.micron.json;

import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;

public class JsonIO {
    protected static Gson gson;

    public JsonIO() {
        gson = new Gson();
    }

    public void saveJson(Object obj, String path) {
        try {
            FileWriter file = new FileWriter(path);
            gson.toJson(obj, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}