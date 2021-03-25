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
  private String pracNumber = "�";
  private String target = "���� ������";
  private String theory = "������������� ��������";
  private String stepByStep = "��� ������";
  private String code = "��� � GitHub ��� ������";
  private String conclusion = "�����";
  private String literature = "������������ ����������";
}