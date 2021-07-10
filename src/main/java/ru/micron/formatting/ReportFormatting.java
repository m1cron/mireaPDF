package ru.micron.formatting;

import static org.apache.commons.lang3.StringUtils.SPACE;
import static ru.micron.converting.ResultMapHolder.WORK_NUMBER;
import static ru.micron.converting.ResultMapHolder.REPORT;
import static ru.micron.converting.ResultMapHolder.REPORT_FIELDS;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import ru.micron.converting.ResultMapHolder;

@Component
@RequiredArgsConstructor
public class ReportFormatting {

  private static final String DIVS_STARTS = "<div class=\"page\">\n\t\t\t<div class=\"content\">\n";
  private static final String DIVS_ENDS = "\n\t\t\t</div>\n\t\t</div>\n";
  private static final String PRE_START = "\t\t\t\t<pre class=\"code\">";
  private static final String PRE_END = "\t</pre>\n";
  private static final String HEAD_TEMPLATE = "\n\t\t\t\t<h2 class=\"h2\">%s</h2>\n";
  private static final String PRAC_NUMBER_TEMPLATE =
      "\n\t\t\t\t<h1 class=\"h1\">Практическая работа № %s</h1>\n";
  private static final List<String> HEAD_LINES = List.of("Цель работы", "Теоретическое введение",
      "Ход работы", "Код", "Выводы по работе", "Используемая литература");
  private static final short MAX_WIDTH = 70;         /* page maxWidth content */
  private static final short MAX_HEIGHT = 72;        /* page maxHeight content */
  private static final int STR_BUFFER_SIZE = 5000;

  private final ResultMapHolder mapHolder;

  public static List<String> parseStrToArrayByWidth(String str) {
    List<String> result = new ArrayList<>();
    if (str.length() <= MAX_WIDTH) {
      result.add(str);
      return result;
    }
    var buff = new StringBuilder();
    for (String s : str.split(SPACE)) {
      if (buff.length() + s.length() <= MAX_WIDTH) {
        buff.append(s).append(SPACE);
      } else {
        result.add(buff.append(StringUtils.LF).toString());
        buff = new StringBuilder(s).append(SPACE);
      }
    }
    result.add(buff.toString());
    return result;
  }

  public void formatResultMap() {
    var report = mapHolder.getMap();
    var result = new StringBuilder(STR_BUFFER_SIZE)
        .append(String.format(PRAC_NUMBER_TEMPLATE, report.get(WORK_NUMBER)));
    int count = 5;
    for (int i = 0; i < HEAD_LINES.size(); i++) {
      result.append(String.format(HEAD_TEMPLATE, HEAD_LINES.get(i)));
      count++;
      if (i == 3) {
        result.append(PRE_START);
      }
      String res = (String) report.get(REPORT_FIELDS.get(i));
      for (String s : parseStrToArrayByWidth(res)) {

        for (byte countLinebrakes : s.getBytes(StandardCharsets.UTF_8)) {
          if (countLinebrakes == CharUtils.LF) {
            count++;
          }
          if (count >= MAX_HEIGHT) {
            count = 0;
            if (i == 3) {
              result.append(PRE_END);
            }
            result.append(DIVS_ENDS).append(DIVS_STARTS);
            if (i == 3) {
              result.append(PRE_START);
            }
          }
        }
        result.append(s);
        count++;
      }
      if (i == 3) {
        result.append(PRE_END);
      }

    }
    mapHolder.save(REPORT, result.toString());
  }
}
