package ru.micron;

import com.google.gson.Gson;
import ru.micron.json.StudentJson;
import ru.micron.utils.UtilsForIO;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class StudentJsonIO extends UtilsForIO {
    private static final String studentJsonName = "info.json";

    public static void saveStudentJson(String studName, String groupNum, String tchName) {
        Gson gson = new Gson();
        try {
            FileWriter file = new FileWriter(studentJsonName);
            gson.toJson(new StudentJson(studName, tchName, groupNum), file);
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getStudentJson(Map<String, String> map) {
        StudentJson info;
        if ((info = getStudentInfo()) != null) {
            map.put("student", info.getStudName());
            map.put("group", info.getGroupNum());
            map.put("teacher", info.getTchName());
        }
    }

    public static StudentJson getStudentInfo() {
        Gson gson = new Gson();
        try {
            return gson.fromJson(readFile(studentJsonName), StudentJson.class);
        } catch (IOException e) {
            System.out.println("Json is missing!");
        }
        return null;
    }
}