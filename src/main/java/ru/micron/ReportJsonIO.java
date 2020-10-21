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
                    "№",
                    "Цель работы",
                    "Теоретическое введение",
                    "Ход работы",
                    "Код с GitHub или ссылка",
                    "Вывод",
                    "Используемая литература");
            return reportJson;
        }
    }

    @Override
    public void fillMap(Map<String, String> map) {
        ReportJson report;
        if ((report = getReportJson()) != null) {
            map.put("prac_number", report.getPrac_number());
            map.put("target_content", report.getTarget());
            map.put("teor_content", report.getTheory());
            map.put("step_by_step", report.getStep_by_step());
            map.put("conclusion_content", report.getConclusion());
            map.put("literature_content", report.getLiterature());
        }
    }

    public ReportJson getReportJson() {
        return reportJson;
    }
}