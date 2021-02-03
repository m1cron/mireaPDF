package ru.micron.json;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentJson {

    private String studName;
    private String tchName;
    private String groupNum;

    public StudentJson() {
        this.studName = "Студент";
        this.tchName = "Преподаватель";
        this.groupNum = "Группа";
    }

}