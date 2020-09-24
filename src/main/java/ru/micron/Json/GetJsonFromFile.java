package ru.micron.Json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.Iterator;
import java.util.Map;

public class GetJsonFromFile {
    private static final String filePath = "theory.json";

    public static void parseJson(Map<String, Object> map, int practiceNum) {
        try (FileReader reader = new FileReader(ClassLoader.getSystemResource(filePath).getFile())) {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

            JSONArray practice = (JSONArray) jsonObject.get("practice");

            Iterator it = practice.iterator();
            for (int i = 0; it.hasNext(); i++) {
                if (i == practiceNum) {
                    JSONObject innerObj = (JSONObject) it.next();
                    map.put("prac_number", practiceNum);
                    map.put("target_content", innerObj.get("target"));
                    map.put("teor_content", innerObj.get("theory"));
                    map.put("conclusion_content", innerObj.get("conclusion"));
                    map.put("literature_content", innerObj.get("literature"));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
