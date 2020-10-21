package ru.micron;

import java.util.HashMap;
import java.util.Map;

public class MakeMap {
    private Map<String, String> map;

    public void makeMap(String code) {
        map = new HashMap<>();

        new GetDate().fillMap(map);
        new StudentJsonIO().fillMap(map);
        new ReportJsonIO().fillMap(map);

        map.put("code", new Github(code, false, "100").getCode());
    }

    public Map<String, String> getMap() {
        return map;
    }
}