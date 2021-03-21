package ru.micron.reporting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report {

  private String studName = "Студент";
  private String tchName = "Преподаватель";
  private String groupNum = "Группа";

  private String prac_number = "№";
  private String target = "Цель работы";
  private String theory = "Теоретическое введение";
  private String step_by_step = "Ход работы";
  private String code = "Код с GitHub или ссылка";
  private String conclusion = "Вывод";
  private String literature = "Используемая литература";

}