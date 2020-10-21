package ru.micron;

import com.google.gson.Gson;
import ru.micron.interfaces.JsonIO;
import ru.micron.json.StudentJson;
import ru.micron.utils.UtilsForIO;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class StudentJsonIO implements JsonIO<StudentJson> {
    private static final String studentJsonName = "info.json";
    private static Gson gson;
    private final StudentJson studentJson;

    public StudentJsonIO() {
        gson = new Gson();
        studentJson = getJson();
    }

    @Override
    public void saveJson(StudentJson obj) {
        try {
            FileWriter file = new FileWriter(studentJsonName);
            gson.toJson(obj, file);
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public StudentJson getJson() {
        try {
            return gson.fromJson(UtilsForIO.readFile(studentJsonName), StudentJson.class);
        } catch (IOException e) {
            System.out.println("Student json doesn't exist!");
            return new StudentJson("Преподаватель","Студент", "Группа");
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