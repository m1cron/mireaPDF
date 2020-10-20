package ru.micron;

import ru.micron.json.JsonIO;
import ru.micron.json.ReportJson;
import ru.micron.utils.UtilsForIO;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

public class ReportJsonIO extends JsonIO {
    private static final String fileJson = "theory.json";
    private static final String fileJson0 = "report.json";
    private static ReportJson reportJson;

    public ReportJsonIO() {
        try {
            reportJson = gson.fromJson(UtilsForIO.readFile(fileJson0), ReportJson.class);
        } catch (IOException e) {
            System.out.println("Report json doesn't exist!");
            reportJson = new ReportJson(
                    "Цель работы",
                    "Теоретическое введение",
                    "Ход работы",
                    "Код с GitHub или ссылка",
                    "Вывод",
                    "Используемая литература");
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

    public static void main(String[] args) {
        System.out.println(new ReportJsonIO().getReportJson());
    }
}