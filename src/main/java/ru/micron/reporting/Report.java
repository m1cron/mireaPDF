package ru.micron.reporting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report {

  private String studName = "�������";
  private String tchName = "�������������";
  private String groupNum = "������";

  private String prac_number = "�";
  private String target = "���� ������";
  private String theory = "������������� ��������";
  private String step_by_step = "��� ������";
  private String code = "��� � GitHub ��� ������";
  private String conclusion = "�����";
  private String literature = "������������ ����������";

}