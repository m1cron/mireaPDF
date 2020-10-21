package ru.micron;

import ru.micron.json.ReportJson;
import ru.micron.utils.UtilsForIO;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MakeMap {
    private Map<String, String> map;

    public void makeMap(int pracNum, String code) {
        map = new HashMap<>();
        map.put("step_by_step", "test");

        GetDate date = new GetDate();
        map.put("day", date.getDay());
        map.put("month", date.getMonth());
        map.put("year", date.getYear());

        new StudentJsonIO().fillMap(map);

        ReportJson report = new ReportJsonIO().getReportJson();

        map.put("code", new Github(code, false, "100").getCode());
    }

    public Map<String, String> getMap() {
        return map;
    }
}