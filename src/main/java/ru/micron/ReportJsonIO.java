package ru.micron;

import com.google.gson.Gson;
import ru.micron.json.ReportJson;
import ru.micron.utils.UtilsForIO;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

public class ReportJsonIO implements JsonIO<ReportJson> {
    private static Gson gson;
    private static final String fileJson = "theory.json";
    private static final String fileJson0 = "report.json";
    private static ReportJson reportJson;

    public ReportJsonIO() {
        gson = new Gson();
        reportJson = getJson();
    }

    @Override
    public void saveJson(ReportJson obj) {
        try {
            FileWriter file = new FileWriter(fileJson0);
            gson.toJson(obj, file);
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ReportJson getJson() {
        try {
            return reportJson = gson.fromJson(UtilsForIO.readFile(fileJson0), ReportJson.class);
        } catch (IOException e) {
            System.out.println("Report json doesn't exist!");
            reportJson = new ReportJson(
                    "Цель работы",
                    "Теоретическое введение",
                    "Ход работы",
                    "Код с GitHub или ссылка",
                    "Вывод",
                    "Используемая литература");
            return reportJson;
        }
    }

    public ReportJson getReportJson() {
        return reportJson;
    }

    public void parseJson(Map<String, String> map, int pracNum) {
        try {
            ReportJson[] arr = gson.fromJson(UtilsForIO.readFileFromRes(fileJson, Charset.defaultCharset()), ReportJson[].class);
            ReportJson obj = arr[pracNum];
            map.put("prac_number", Integer.toString(pracNum));
            map.put("target_content", obj.getTarget());
            map.put("teor_content", obj.getTheory());
            map.put("step_by_step", obj.getStep_by_step());
            map.put("conclusion_content", obj.getConclusion());
            map.put("literature_content", obj.getLiterature());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}