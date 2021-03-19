package ru.micron.formatting;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ReportFormatting implements MapFilling {

  private StringBuffer code;

  private String formatWidth(String str) {
    if (str.length() > ReportConstants.MAX_WIDTH) {
      return str.substring(0, ReportConstants.MAX_WIDTH) + "\n" + formatWidth(
          str.substring(ReportConstants.MAX_WIDTH));
    }
    return str;
  }

  public ReportFormatting formatCode(List<String> codeArr) {
    code = new StringBuffer(ReportConstants.STR_BUFFER_SIZE);
    String divStart = "<div class=\"page\">\n\t\t<div class=\"content\">\n\t\n";
    String divEnd = "\n\t\t</pre>\n\t</div>\n</div>\n\n";
    String codeStart = "\t\t<pre class=\"code\">\n";

    code.append(divStart).append("\t<h2 class=\"h2\">Код</h2>").append(codeStart);
    short count = 4;
    for (String s : codeArr) {
      code.append(formatWidth(s)).append("\n");
      if (count == ReportConstants.MAX_HEIGHT) {
        code.append(divEnd).append(divStart).append(codeStart);
        count = 0;
      }
      count++;
    }
    code.append(divEnd);
    return this;
  }

  @Override
  public void fillMap(Map<String, String> map) {
    map.put(ReportConstants.CODE, code.toString());
  }

}
