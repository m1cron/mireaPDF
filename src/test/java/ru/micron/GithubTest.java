package ru.micron;

import org.junit.Assert;
import org.junit.Test;

public class GithubTest {

    @Test
    public void Github() {
        String url = "https://github.com/m1cron/java_rtu/blob/master/src/ru/micron/task4";
        new Github(url, false, "100").getCodeArr().forEach(Assert::assertNotNull);
    }

    @Test
    public void recursSearchGit() {
        String url = "https://github.com/m1cron/java_rtu/blob/master/src/ru/micron/task2";
        Github gh = new Github(url, false, "100");
        gh.getCodeArr().forEach(Assert::assertNotNull);
    }

    @Test
    public void parseUrl() {
        String url = "https://github.com/m1cron/java_rtu/blob/master/src/ru/micron";
        String destUrl = "https://api.github.com/repos/m1cron/java_rtu/contents/src/ru/micron";
        Assert.assertEquals(Github.parseUrl(url), destUrl);
    }

    @Test
    public void getRegExPattern() {
        String[] arr0 = {"kjdhzfg.java", "ajdhgf.JaVa", "IUYGFDRSGdgf31Pp.cs"};
        for (String s : arr0) {
            Assert.assertTrue(s.matches(Github.getRegExPattern()));
        }
        String[] arr1 = {"kjdhzfg..java", "ajdhgf.JaV!a", "IUYGFDRSGdgf3#1Pp.cs"};
        for (String s : arr1) {
            Assert.assertFalse(s.matches(Github.getRegExPattern()));
        }
    }
}