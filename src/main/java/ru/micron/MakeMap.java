package ru.micron;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class MakeMap {

    private final Map<String, String> map;

    public MakeMap(String code) {
        map = new HashMap<>(16, 1);

        new GetDate().fillMap(map);
        new StudentJsonIO().fillMap(map);
        new ReportJsonIO().fillMap(map);
        new ReportFormatting(new Github(code, false, "100").getCodeArr()).fillMap(map);
    }

}