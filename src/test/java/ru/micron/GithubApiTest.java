package ru.micron;

import org.junit.Assert;
import org.junit.Test;
import ru.micron.web.GithubApi;
import ru.micron.web.WebConstants;

public class GithubApiTest {

  @Test
  public void GithubApi() {
    String url = "https://github.com/m1cron/java_rtu/blob/master/src/ru/micron/task4";
    new GithubApi(url).getCodeArr().forEach(Assert::assertNotNull);
  }

  @Test
  public void recursSearchGit() {
    String url = "https://github.com/m1cron/java_rtu/blob/master/src/ru/micron/task2";
    GithubApi gh = new GithubApi(url);
    gh.getCodeArr().forEach(Assert::assertNotNull);
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