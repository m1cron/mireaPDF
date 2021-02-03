package ru.micron;

import com.google.gson.Gson;
import ru.micron.interfaces.JsonIO;
import ru.micron.model.Report;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class ReportJsonIO implements JsonIO<Report> {

    private static Gson gson;
    private static final String jsonName = "report.json";
    private static Report report;

    public ReportJsonIO() {
        gson = new Gson();
        report = getJson(Report.class);
    }

    @Override
    public void saveJson(Report obj) {
        try {
            FileWriter file = new FileWriter(jsonName);
            gson.toJson(obj, file);
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Report getJson(Class<Report> jsonClass) {
        try {
            return report = gson.fromJson(readFile(jsonName), jsonClass);
        } catch (IOException e) {
            System.out.println("Report json doesn't exist!");
            report = new Report();
            return report;
        }
    }

    @Override
    public void fillMap(Map<String, String> map) {
        Report report;
        if ((report = getReportJson()) != null) {
            map.put("prac_number", report.getPrac_number());
            map.put("target_content", report.getTarget());
            map.put("teor_content", report.getTheory());
            map.put("step_by_step", report.getStep_by_step());
            map.put("conclusion_content", report.getConclusion());
            map.put("literature_content", report.getLiterature());
        }
    }

    public Report getReportJson() {
        return report;
    }
}