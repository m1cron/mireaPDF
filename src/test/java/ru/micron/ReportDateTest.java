package ru.micron;

import static ru.micron.config.AppConfiguration.APP_LANGUAGE;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;
import ru.micron.formatting.ReportDate;

public class ReportDateTest {

  private static final Locale locale = new Locale(APP_LANGUAGE);

  @Test
  public void fillMap() {
    ReportDate date = new ReportDate(locale);

    Map<String, String> map = new HashMap<>(3, 1);
    date.fillMap(map);

    Assert.assertNotNull(map.get("day"));
    Assert.assertNotNull(map.get("month"));
    Assert.assertNotNull(map.get("year"));
  }

  @Test
  public void getYear() {
    ReportDate date = new ReportDate(locale);
    Assert.assertNotNull(date.getYear());
  }

  @Test
  public void getMonth() {
    ReportDate date = new ReportDate(locale);
    Assert.assertNotNull(date.getMonth());
  }

  @Test
  public void getDay() {
    ReportDate date = new ReportDate(locale);
    Assert.assertNotNull(date.getDay());
  }

}