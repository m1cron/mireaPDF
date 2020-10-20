package ru.micron;

import ru.micron.json.JsonIO;
import ru.micron.json.StudentJson;
import ru.micron.utils.UtilsForIO;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class StudentJsonIO extends JsonIO {
    private static final String studentJsonName = "info.json";

    public void saveStudentJson(String studName, String groupNum, String tchName) {
        try {
            FileWriter file = new FileWriter(studentJsonName);
            gson.toJson(new StudentJson(studName, tchName, groupNum), file);
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getStudentJson(Map<String, String> map) {
        StudentJson info;
        if ((info = getStudentInfo()) != null) {
            map.put("student", info.getStudName());
            map.put("group", info.getGroupNum());
            map.put("teacher", info.getTchName());
        }
    }

    public StudentJson getStudentInfo() {
        try {
            return gson.fromJson(UtilsForIO.readFile(studentJsonName), StudentJson.class);
        } catch (IOException e) {
            System.out.println("Student json doesn't exist!");
        }
        return null;
    }
}