package ru.micron.Json;

import com.google.gson.Gson;
import ru.micron.Utils.UtilsForIO;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

class StudentInfo {
    public String studName, tchName, groupNum;

    StudentInfo(String studName, String tchName, String groupNum) {
        this.studName = studName;
        this.tchName = tchName;
        this.groupNum = groupNum;
    }

    @Override
    public String toString() {
        return "Info{" +
                "studName='" + studName + '\'' +
                ", tchName='" + tchName + '\'' +
                ", groupNum='" + groupNum + '\'' +
                '}';
    }
}

public class StudentJson extends UtilsForIO {
    private static String studentJsonName = "info.json";

    public static void saveStudentJson(String studName, String tchName, String groupNum) {
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
        try {
            Gson gson = new Gson();
            StudentInfo info = gson.fromJson(readFile(studentJsonName), StudentInfo.class);
            map.put("student", info.studName);
            map.put("group", info.groupNum);
            map.put("teacher", info.tchName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
