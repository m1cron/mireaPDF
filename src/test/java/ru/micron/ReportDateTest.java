package ru.micron;

import static ru.micron.config.AppConfiguration.APP_LANGUAGE;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.micron.formatting.ReportConstants;
import ru.micron.formatting.ReportDate;

@SpringBootTest
public class ReportDateTest {

  private final ReportDate date = new ReportDate(new Locale(APP_LANGUAGE));

  @Test
  public void fillMap() {
    Map<String, String> map = new HashMap<>();
    date.fillMap(map);
    Assert.assertNotNull(map.get(ReportConstants.DAY));
    Assert.assertNotNull(map.get(ReportConstants.MONTH));
    Assert.assertNotNull(map.get(ReportConstants.YEAR));
  }

  @Test
  public void getYear() {
    Assert.assertNotNull(date.getYear());
  }

  @Test
  public void getMonth() {
    Assert.assertNotNull(date.getMonth());
  }

  @Test
  public void getDay() {
    Assert.assertNotNull(date.getDay());
  }

}