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
public class GetDate implements MapFilling {

    private static final List<String> months;
    private final String year;
    private final String month;
    private final String day;

    static {
        months = Stream.of("января", "февраля", "марта", "апреля", "мая", "июня", "июля",
                "августа", "сентября", "октября", "ноября", "декабря").collect(Collectors.toList());
    }

    public GetDate() {
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
