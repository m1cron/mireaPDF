package ru.micron;

import org.junit.Test;

public class GetDateTest {

    @Test
    public void GetDateTest() {
        GetDate date = new GetDate();
        System.out.println(date.getDay() + "\t" + date.getMonth() + "\t" + date.getYear());
    }
}