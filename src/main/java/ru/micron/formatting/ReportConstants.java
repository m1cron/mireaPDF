package ru.micron.formatting;

import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReportConstants {

  public static final short MAX_WIDTH = 70;     /* page maxWidth content */
  public static final short MAX_HEIGHT = 47;    /* page maxHeight content */
  public static final int STR_BUFFER_SIZE = 5000;

  // Months
  public static final List<String> MONTHS = List.of("€нвар€", "феврал€", "марта", "апрел€",
      "ма€", "июн€", "июл€", "августа", "сент€бр€", "окт€бр€", "но€бр€", "декабр€");

  // Map Filling
  public static final String CODE = "code";
  public static final String DAY = "day";
  public static final String MONTH = "month";
  public static final String YEAR = "year";

  public static final String STUDENT = "student";
  public static final String TEACHER = "teacher";
  public static final String GROUP = "group";

  public static final String PRAC_NUMBER = "prac_number";
  public static final String TARGET_CONTENT = "target_content";
  public static final String TEOR_CONTENT = "teor_content";
  public static final String STEP_BY_STEP = "step_by_step";
  public static final String CONCLUSION = "conclusion_content";
  public static final String LITERATURE = "literature_content";
}
