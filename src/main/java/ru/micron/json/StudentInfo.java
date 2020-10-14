package ru.micron.json;

public class StudentInfo {
    private final String studName;
    private final String tchName;
    private final String groupNum;

    StudentInfo(String studName, String tchName, String groupNum) {
        this.studName = studName;
        this.tchName = tchName;
        this.groupNum = groupNum;
    }

    public String getStudName() {
        return studName;
    }

    public String getTchName() {
        return tchName;
    }

    public String getGroupNum() {
        return groupNum;
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