package ru.micron.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Student {

    private String studName;
    private String tchName;
    private String groupNum;

    public Student() {
        this.studName = "Студент";
        this.tchName = "Преподаватель";
        this.groupNum = "Группа";
    }

}