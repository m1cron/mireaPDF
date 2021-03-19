package ru.micron;

import org.junit.Assert;
import org.junit.Test;
import ru.micron.web.GithubAPI;
import ru.micron.web.WebConstants;

public class GithubAPITest {

  @Test
  public void GithubAPI() {
    String url = "https://github.com/m1cron/java_rtu/blob/master/src/ru/micron/task4";
    new GithubAPI(url, false, "100").getCodeArr().forEach(Assert::assertNotNull);
  }

  @Test
  public void recursSearchGit() {
    String url = "https://github.com/m1cron/java_rtu/blob/master/src/ru/micron/task2";
    GithubAPI gh = new GithubAPI(url, false, "100");
    gh.getCodeArr().forEach(Assert::assertNotNull);
  }

  @Test
  public void parseUrl() {
    String url = "https://github.com/m1cron/java_rtu/blob/master/src/ru/micron";
    String destUrl = "https://api.github.com/repos/m1cron/java_rtu/contents/src/ru/micron";
    Assert.assertEquals(GithubAPI.parseUrl(url), destUrl);
  }

  @Test
  public void getRegExPattern() {
    String[] arr0 = {"test.cpp", "kjdhzfg.java", "ajdhgf.JaVa", "IUYGFDRSGdgf31Pp.cs",
        "src/ru/micron/task1/Main.java"};
    for (String s : arr0) {
      Assert.assertTrue(s.matches(WebConstants.MATCHES_REGEX));
    }
    String[] arr1 = {"kjdhzfg..java", "ajdhgf.JaV!a", "IUYGFDRSGdgf3#1Pp.cs", "tes/t.cpP "};
    for (String s : arr1) {
      Assert.assertFalse(s.matches(WebConstants.MATCHES_REGEX));
    }
  }

}