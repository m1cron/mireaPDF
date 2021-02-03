package ru.micron;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ReportDateTest {

    @Test
    public void fillMap() {
        ReportDate date = new ReportDate();

        Map<String, String> map = new HashMap<>(3, 1);
        date.fillMap(map);

        Assert.assertNotNull(map.get("day"));
        Assert.assertNotNull(map.get("month"));
        Assert.assertNotNull(map.get("year"));
    }

    @Test
    public void getYear() {
        ReportDate date = new ReportDate();
        Assert.assertNotNull(date.getYear());
    }

    @Test
    public void getMonth() {
        ReportDate date = new ReportDate();
        Assert.assertNotNull(date.getMonth());
    }

    @Test
    public void getDay() {
        ReportDate date = new ReportDate();
        Assert.assertNotNull(date.getDay());
    }

}