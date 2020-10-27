package ru.micron;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class GetDateTest {

    @Test
    public void fillMap() {
        GetDate date = new GetDate();

        Map<String, String> map = new HashMap<>(3, 1);
        date.fillMap(map);

        Assert.assertNotNull(map.get("day"));
        Assert.assertNotNull(map.get("month"));
        Assert.assertNotNull(map.get("year"));
    }

    @Test
    public void getYear() {
        GetDate date = new GetDate();
        Assert.assertNotNull(date.getYear());
    }

    @Test
    public void getMonth() {
        GetDate date = new GetDate();
        Assert.assertNotNull(date.getMonth());
    }

    @Test
    public void getDay() {
        GetDate date = new GetDate();
        Assert.assertNotNull(date.getDay());
    }
}