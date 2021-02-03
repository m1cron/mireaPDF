package ru.micron;

import com.google.gson.Gson;
import ru.micron.interfaces.JsonIO;
import ru.micron.model.Student;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

public class StudentJsonIO implements JsonIO<Student> {

    private static final String jsonName = "info.json";
    private static Gson gson;
    private final Student student;

    public StudentJsonIO() {
        gson = new Gson();
        student = getJson(Student.class);
    }

    @Override
    public void saveJson(Student obj) {
        try {
            FileWriter file = new FileWriter(jsonName);
            gson.toJson(obj, file);
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Student getJson(Class<Student> jsonClass) {
        try {
            return gson.fromJson(readFile(jsonName), (Type) jsonClass);
        } catch (IOException e) {
            System.out.println("Student json doesn't exist!");
            return new Student();
        }
    }

    @Override
    public void fillMap(Map<String, String> map) {
        Student info;
        if ((info = getStudentJson()) != null) {
            map.put("student", info.getStudName());
            map.put("group", info.getGroupNum());
            map.put("teacher", info.getTchName());
        }
    }

    public Student getStudentJson() {
        return student;
    }
}