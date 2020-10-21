package ru.micron;

import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

public class GetDate {
    private final String year;
    private final String month;
    private final String day;

    public GetDate() {
        String[] months = {
                "января", "февраля", "марта", "апреля", "мая", "июня",
                "июля", "августа", "сентября", "октября", "ноября", "декабря" };
        Locale locale = new Locale("ru");
        year = Integer.toString(Calendar.getInstance(locale).get(Calendar.YEAR));
        month = months[Calendar.getInstance(locale).get(Calendar.MONTH)];
        day = Integer.toString(Calendar.getInstance(locale).get(Calendar.DATE));
    }

    public void fillMap(Map<String, String> map) {
        map.put("day", day);
        map.put("month", month);
        map.put("year", year);
    }


    public String getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }

    public String getDay() {
        return day;
    }
}
