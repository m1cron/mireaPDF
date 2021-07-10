package ru.micron;

import static ru.micron.web.GithubApi.MATCHES_REGEX;

import org.junit.Assert;
import org.junit.Test;
import ru.micron.web.GithubApi;

public class GithubApiTest {

  @Test
  public void GithubApi() {
    String url = "https://github.com/m1cron/java_rtu/tree/master/3_semester/src/ru/micron/task1";
    Assert.assertNotNull(new GithubApi(url).getBuff().toString());
  }

  @Test
  public void recursSearchGit() {
    String url = "https://github.com/m1cron/java_rtu/tree/master/3_semester/src/ru/micron/task1";
    GithubApi gh = new GithubApi(url);
    Assert.assertNotNull(gh.getBuff());
  }

  @Test
  public void getRegExPattern() {
    String[] arr0 = {"test.cpp", "kjdhzfg.java", "ajdhgf.JaVa", "IUYGFDRSGdgf31Pp.cs",
        "src/ru/micron/task1/Main.java"};
    for (String s : arr0) {
      Assert.assertTrue(s.matches(MATCHES_REGEX));
    }
    String[] arr1 = {"kjdhzfg..java", "ajdhgf.JaV!a", "IUYGFDRSGdgf3#1Pp.cs", "tes/t.cpP "};
    for (String s : arr1) {
      Assert.assertFalse(s.matches(MATCHES_REGEX));
    }
  }

}