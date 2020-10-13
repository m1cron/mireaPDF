package ru.micron.json;

import com.google.gson.Gson;
import ru.micron.utils.UtilsForIO;

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
        StudentInfo info = getStudentInfo();
        map.put("student", info.studName);
        map.put("group", info.groupNum);
        map.put("teacher", info.tchName);
    }

    public static StudentInfo getStudentInfo() {
        Gson gson = new Gson();
        try {
            return gson.fromJson(readFile(studentJsonName), StudentInfo.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}