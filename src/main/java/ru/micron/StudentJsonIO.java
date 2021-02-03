package ru.micron;

import com.google.gson.Gson;
import ru.micron.interfaces.JsonIO;
import ru.micron.json.StudentJson;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

public class StudentJsonIO implements JsonIO<StudentJson> {

    private static final String jsonName = "info.json";
    private static Gson gson;
    private final StudentJson studentJson;

    public StudentJsonIO() {
        gson = new Gson();
        studentJson = getJson(StudentJson.class);
    }

    @Override
    public void saveJson(StudentJson obj) {
        try {
            FileWriter file = new FileWriter(jsonName);
            gson.toJson(obj, file);
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public StudentJson getJson(Class<StudentJson> jsonClass) {
        try {
            return gson.fromJson(readFile(jsonName), (Type) jsonClass);
        } catch (IOException e) {
            System.out.println("Student json doesn't exist!");
            return new StudentJson();
        }
    }

    @Override
    public void fillMap(Map<String, String> map) {
        StudentJson info;
        if ((info = getStudentJson()) != null) {
            map.put("student", info.getStudName());
            map.put("group", info.getGroupNum());
            map.put("teacher", info.getTchName());
        }
    }

    public StudentJson getStudentJson() {
        return studentJson;
    }
}