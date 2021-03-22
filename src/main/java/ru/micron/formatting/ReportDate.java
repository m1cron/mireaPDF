package ru.micron.formatting;

import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ReportDate implements MapFilling {

  private final String year;
  private final String month;
  private final String day;

  public ReportDate(Locale locale) {
    year = Integer.toString(Calendar.getInstance(locale).get(Calendar.YEAR));
    month = ReportConstants.MONTHS.get(Calendar.getInstance(locale).get(Calendar.MONTH));
    day = Integer.toString(Calendar.getInstance(locale).get(Calendar.DATE));
  }

  @Override
  public void fillMap(Map<String, Object> map) {
    map.put(ReportConstants.DAY, day);
    map.put(ReportConstants.MONTH, month);
    map.put(ReportConstants.YEAR, year);
  }

}
