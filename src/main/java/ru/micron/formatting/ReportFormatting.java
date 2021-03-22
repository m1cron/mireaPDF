package ru.micron.formatting;

import static ru.micron.formatting.ReportConstants.DIVS_ENDS;
import static ru.micron.formatting.ReportConstants.DIVS_STARTS;
import static ru.micron.formatting.ReportConstants.HEAD_LINES;
import static ru.micron.formatting.ReportConstants.HEAD_TEMPLATE;
import static ru.micron.formatting.ReportConstants.MAX_WIDTH;
import static ru.micron.formatting.ReportConstants.PRAC_NUMBER;
import static ru.micron.formatting.ReportConstants.PRAC_NUMBER_TEMPLATE;
import static ru.micron.formatting.ReportConstants.PRE_END;
import static ru.micron.formatting.ReportConstants.PRE_START;
import static ru.micron.formatting.ReportConstants.REPORT_CODE_PAGES_FIELDS;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ReportFormatting implements MapFilling {

  private StringBuffer result;

  public static List<String> parseStrToArrayByWidth(String str) {
    List<String> result = new ArrayList<>();
    if (str.length() <= MAX_WIDTH) {
      result.add(str);
      return result;
    }
    var buff = new StringBuilder();
    for (String s : str.split(" ")) {
      if (buff.length() + s.length() <= MAX_WIDTH) {
        buff.append(s).append(" ");
      } else {
        result.add(buff.append("\n").toString());
        buff = new StringBuilder(s).append(" ");
      }
    }
    result.add(buff.toString());
    return result;
  }

  public ReportFormatting formatDocument(Map<String, Object> report) {
    result = new StringBuffer(ReportConstants.STR_BUFFER_SIZE);
    result.append(String.format(PRAC_NUMBER_TEMPLATE, report.get(PRAC_NUMBER)));
    int count = 5;
    for (int i = 0; i < HEAD_LINES.length; i++) {
      result.append(String.format(HEAD_TEMPLATE, HEAD_LINES[i]));
      count++;
      if (i == 3) {
        result.append(PRE_START);
      }
      String res = (String) report.get(REPORT_CODE_PAGES_FIELDS[i]);
      for (String s : parseStrToArrayByWidth(res)) {

        for (byte countLinebrakes : s.getBytes(StandardCharsets.UTF_8)) {
          if (countLinebrakes == '\n') {
            count++;
          }
          if (count >= ReportConstants.MAX_HEIGHT) {
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
    return this;
}

  @Override
  public void fillMap(Map<String, Object> map) {
    map.put(ReportConstants.CODE, result.toString());
  }

}
