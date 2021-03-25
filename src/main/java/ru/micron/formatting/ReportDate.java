package ru.micron.formatting;

import java.util.Calendar;
import java.util.Locale;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ReportDate {

  private final String year;
  private final String month;
  private final String day;

  public ReportDate(Locale locale) {
    year = Integer.toString(Calendar.getInstance(locale).get(Calendar.YEAR));
    month = ReportConstants.MONTHS.get(Calendar.getInstance(locale).get(Calendar.MONTH));
    day = Integer.toString(Calendar.getInstance(locale).get(Calendar.DATE));
  }
}
