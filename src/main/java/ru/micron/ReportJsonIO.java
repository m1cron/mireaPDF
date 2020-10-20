package ru.micron;

import com.google.gson.*;
import ru.micron.json.ReportJson;
import ru.micron.utils.UtilsForIO;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

public class ReportJsonIO extends UtilsForIO {
    private static final String fileJson = "theory.json";

    public static void parseJson(Map<String, String> map, int pracNum) {
        try {
            Gson gson = new Gson();
            ReportJson[] arr = gson.fromJson(readFileFromRes(fileJson, Charset.defaultCharset()), ReportJson[].class);
            ReportJson obj = arr[pracNum];
            map.put("prac_number", Integer.toString(pracNum));
            map.put("target_content", obj.target);
            map.put("teor_content", obj.theory);
            map.put("step_by_step", obj.step_by_step);
            map.put("conclusion_content", obj.conclusion);
            map.put("literature_content", obj.literature);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}