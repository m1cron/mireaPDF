package ru.micron.Json;

import com.google.gson.Gson;
import ru.micron.Utils.UtilsForIO;

import java.io.FileWriter;
import java.io.IOException;

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
    public static void saveStudentJson(String studName, String tchName, String groupNum) {
        Gson gson = new Gson();
        try {
            FileWriter file = new FileWriter("info.json");
            gson.toJson(new StudentInfo(studName, tchName, groupNum), file);
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
