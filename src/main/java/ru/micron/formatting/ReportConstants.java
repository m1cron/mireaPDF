package ru.micron.formatting;

import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReportConstants {

  public static final short MAX_WIDTH = 70;         /* page maxWidth content */
  public static final short MAX_HEIGHT = 72;        /* page maxHeight content */
  public static final int STR_BUFFER_SIZE = 5000;

  public static final String DIVS_STARTS = "<div class=\"page\">\n\t\t\t<div class=\"content\">\n";
  public static final String DIVS_ENDS = "\n\t\t\t</div>\n\t\t</div>\n";
  public static final String PRE_START = "\t\t\t\t<pre class=\"code\">";
  public static final String PRE_END = "\t</pre>\n";
  public static final String HEAD_TEMPLATE = "\n\t\t\t\t<h2 class=\"h2\">%s</h2>\n";
  public static final String PRAC_NUMBER_TEMPLATE =
      "\n\t\t\t\t<h1 class=\"h1\">Практическая работа № %s</h1>\n";

  public static final List<String> HEAD_LINES = List.of(
      "Цель работы",
      "Теоретическое введение",
      "Ход работы",
      "Код",
      "Выводы по работе",
      "Используемая литература"
  );

  // Map
  public static final String REPORT = "report";
  public static final String PRAC_NUMBER = "pracNumber";
  public static final String TARGET_CONTENT = "target";
  public static final String TEOR_CONTENT = "theory";
  public static final String STEP_BY_STEP = "stepByStep";
  public static final String CODE = "code";
  public static final String CONCLUSION = "conclusion";
  public static final String LITERATURE = "literature";
  public static final List<String> REPORT_FIELDS = List.of(
      TARGET_CONTENT, TEOR_CONTENT, STEP_BY_STEP, CODE, CONCLUSION, LITERATURE
  );

  // Months
  public static final List<String> MONTHS = List.of("января", "февраля", "марта", "апреля",
      "мая", "июня", "июля", "августа", "сентября", "октября", "ноября", "декабря"
  );
}
