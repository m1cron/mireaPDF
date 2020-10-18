package ru.micron.json;

import com.google.gson.Gson;
import ru.micron.utils.UtilsForIO;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class StudentJson extends UtilsForIO {
    private static final String studentJsonName = "info.json";

    public static void saveStudentJson(String studName, String groupNum, String tchName) {
        Gson gson = new Gson();
        try {
            FileWriter file = new FileWriter(studentJsonName);
            gson.toJson(new StudentInfo(studName, tchName, groupNum), file);
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getStudentJson(Map<String, String> map) {
        StudentInfo info;
        if ((info = getStudentInfo()) != null) {
            map.put("student", info.getStudName());
            map.put("group", info.getGroupNum());
            map.put("teacher", info.getTchName());
        }
    }

    public static StudentInfo getStudentInfo() {
        Gson gson = new Gson();
        try {
            return gson.fromJson(readFile(studentJsonName), StudentInfo.class);
        } catch (IOException e) {
            System.out.println("Json is missing!");
        }
        return null;
    }
}