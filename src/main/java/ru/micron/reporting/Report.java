package ru.micron.reporting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Report {

  private String studName = "Студент";
  private String tchName = "Преподаватель";
  private String groupNum = "Группа";
  private String pracNumber = "№";
  private String target = "Цель работы";
  private String theory = "Теоретическое введение";
  private String stepByStep = "Ход работы";
  private String code = "Код с GitHub или ссылка";
  private String conclusion = "Вывод";
  private String literature = "Используемая литература";
}