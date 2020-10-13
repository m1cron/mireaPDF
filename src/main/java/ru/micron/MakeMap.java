package ru.micron;

import ru.micron.json.Github;

import java.util.HashMap;
import java.util.Map;

public class MakeMap {
    private Map<String, String> map;

    public void makeMap(int pracNum, String code) {
        map = new HashMap<>();
        map.put("year", "2020");
        map.put("step_by_step", "test");

        //StudentJson.getStudentJson(map);
        //GetJsonFromFile.parseJson(map, pracNum);

        map.put("all_code", new Github().getCode(code));
    }

    public Map<String, String> getMap() {
        return map;
    }
}