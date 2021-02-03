package ru.micron;

import lombok.Getter;
import ru.micron.interfaces.MapFilling;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public class ReportDate implements MapFilling {

    private static final List<String> months;
    private final String year;
    private final String month;
    private final String day;

    static {
        months = Stream.of("€нвар€", "феврал€", "марта", "апрел€", "ма€", "июн€", "июл€",
                "августа", "сент€бр€", "окт€бр€", "но€бр€", "декабр€").collect(Collectors.toList());
    }

    public ReportDate() {
        Locale locale = new Locale("ru");
        year = Integer.toString(Calendar.getInstance(locale).get(Calendar.YEAR));
        month = months.get(Calendar.getInstance(locale).get(Calendar.MONTH));
        day = Integer.toString(Calendar.getInstance(locale).get(Calendar.DATE));
    }

    @Override
    public void fillMap(Map<String, String> map) {
        map.put("day", day);
        map.put("month", month);
        map.put("year", year);
    }

}
