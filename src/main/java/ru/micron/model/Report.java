package ru.micron.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Report {

    private String studName;
    private String tchName;
    private String groupNum;

    private String prac_number;
    private String target;
    private String theory;
    private String step_by_step;
    private String code;
    private String conclusion;
    private String literature;

    public Report() {
        this.studName = "�������";
        this.tchName = "�������������";
        this.groupNum = "������";

        this.prac_number = "�";
        this.target = "���� ������";
        this.theory = "������������� ��������";
        this.step_by_step = "��� ������";
        this.code = "��� � GitHub ��� ������";
        this.conclusion = "�����";
        this.literature = "������������ ����������";
    }

}