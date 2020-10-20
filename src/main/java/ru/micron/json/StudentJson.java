package ru.micron.json;

public class StudentJson {
    private String studName;
    private String tchName;
    private String groupNum;

    public StudentJson(String studName, String tchName, String groupNum) {
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

    public void setStudName(String studName) {
        this.studName = studName;
    }

    public void setTchName(String tchName) {
        this.tchName = tchName;
    }

    public void setGroupNum(String groupNum) {
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