package ru.micron;

import static ru.micron.config.AppConfiguration.APP_LANGUAGE;

import java.util.Locale;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.micron.converting.Mapper;
import ru.micron.formatting.ReportDate;

@SpringBootTest
public class ReportDateTest {

  private final ReportDate date = new ReportDate(new Locale(APP_LANGUAGE));

  @Test
  public void fillMap() {
    Map<String, String> map = Mapper.map(date);
    Assert.assertNotNull(map.get("day"));
    Assert.assertNotNull(map.get("month"));
    Assert.assertNotNull(map.get("year"));
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