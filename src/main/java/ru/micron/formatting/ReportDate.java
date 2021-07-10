package ru.micron.formatting;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.micron.converting.ResultMapHolder;

@Data
@Component
public class ReportDate {

  private static final List<String> MONTHS = List.of(
      "января", "февраля", "марта", "апреля", "мая", "июня", "июля",
      "августа", "сентября", "октября", "ноября", "декабря");

  private final int year;
  private final String month;
  private final int day;
  private final ResultMapHolder mapHolder;

  @Autowired
  public ReportDate(
      Locale locale,
      ResultMapHolder mapHolder
  ) {
    this.mapHolder = mapHolder;
    var instance = Calendar.getInstance(locale);
    year = instance.get(Calendar.YEAR);
    month = MONTHS.get(instance.get(Calendar.MONTH));
    day = instance.get(Calendar.DATE);
  }

  @PostConstruct
  public void init() {
    mapHolder.save(this);
  }
}
