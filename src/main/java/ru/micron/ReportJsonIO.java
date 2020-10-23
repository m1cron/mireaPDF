package ru.micron;

import com.google.gson.Gson;
import ru.micron.interfaces.JsonIO;
import ru.micron.json.ReportJson;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class ReportJsonIO implements JsonIO<ReportJson> {
    private static Gson gson;
    private static final String jsonName = "report.json";
    private static ReportJson reportJson;

    public ReportJsonIO() {
        gson = new Gson();
        reportJson = getJson(ReportJson.class);
    }

    @Override
    public void saveJson(ReportJson obj) {
        try {
            FileWriter file = new FileWriter(jsonName);
            gson.toJson(obj, file);
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ReportJson getJson(Class<ReportJson> jsonClass) {
        try {
            return reportJson = gson.fromJson(readFile(jsonName), jsonClass);
        } catch (IOException e) {
            System.out.println("Report json doesn't exist!");
            reportJson = new ReportJson();
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