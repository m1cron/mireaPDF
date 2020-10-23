package ru.micron;

import ru.micron.interfaces.MapFilling;

import java.util.List;
import java.util.Map;

public class ReportFormatting implements MapFilling {
    private final List<String> codeArr;
    private final StringBuffer code;

    public ReportFormatting(List<String> codeArr) {
        code = new StringBuffer(5000);
        this.codeArr = codeArr;
        formatCode();
    }

    private String formatWidth(String str) {
        final short maxWidth = 70;      /*                <<------ page maxWidth content change HERE */
        if (str.length() > maxWidth) {
            return str.substring(0, maxWidth) + "\n" + formatWidth(str.substring(maxWidth));
        }
        return str;
    }

    private void formatCode() {
        String divStart = "<div class=\"page\">\n\t\t<div class=\"content\">\n\t\n";
        String divEnd = "\n\t\t</pre>\n\t</div>\n</div>\n\n";
        String codeStart = "\t\t<pre class=\"code\">\n";

        code.append(divStart).append("\t<h2 class=\"h2\">Код</h2>").append(codeStart);
        short count = 4;
        for (String s : codeArr) {
            code.append(formatWidth(s)).append("\n");
            if (count == 47) {      /*                      <<------ page maxHeight content change HERE */
                code.append(divEnd).append(divStart).append(codeStart);
                count = 0;
            }
            count++;
        }
        code.append(divEnd);
    }

    public String getCode() {
        return code.toString();
    }

    @Override
    public void fillMap(Map<String, String> map) {
        map.put("code", getCode());
    }
}
