package ru.micron;

import org.junit.Test;

import static org.junit.Assert.*;

public class MakeHtmlTest {

    @Test
    public void makeHtml() {
        String url = "https://github.com/m1cron/java_rtu/tree/master/src/ru/micron/task1";
        new MakeHtml("./templates", "index.ftl").
                makeHtml(new MakeMap(url).
                        getMap(), "./index.html");
    }
}