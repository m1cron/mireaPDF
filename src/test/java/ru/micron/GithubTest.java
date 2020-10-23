package ru.micron;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class GithubTest {

    @Test
    public void GithubWork() {
        String url = "https://github.com/m1cron/java_rtu/blob/master/src/ru/micron/task4/DrawIO/Human.drawio";
        Assert.assertNotEquals(new Github(url, false, "100").getCodeArr(), null);
    }

}