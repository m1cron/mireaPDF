package ru.micron;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.micron.converting.ResultMapHolder;
import ru.micron.formatting.ReportDate;

@ExtendWith(MockitoExtension.class)
public class ReportDateTest {

  private ReportDate date = Mockito.mock(ReportDate.class);
  private ResultMapHolder resultMapHolder = Mockito.mock(ResultMapHolder.class);

  @Test
  public void getYear() {
    Assert.assertNotNull(date.getYear());
  }

  @Test
  public void getMonth() {
    Assert.assertNull(date.getMonth());
  }

  @Test
  public void getDay() {
    Assert.assertNotNull(date.getDay());
  }

}