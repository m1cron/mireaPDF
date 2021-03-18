package ru.micron.model;

import com.google.gson.Gson;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import ru.micron.interfaces.MapFilling;

@Slf4j
public class ReportHandler implements MapFilling {

  @Value("${report.json.file}")
  private String reportFileName;
  private Report report;
  private final Gson gson;

  public ReportHandler() {
    gson = new Gson();
    report = getJson();
  }

  public void saveJson(Report report) {
    try {
      FileWriter file = new FileWriter(reportFileName);
      gson.toJson(report, file);
      file.close();
      log.info("Report json saved!");
    } catch (IOException e) {
      log.warn(e.getMessage());
    }
  }

  public Report getJson() {
    try {
      report = gson.fromJson(Files.readString(Path.of("report.json"), Charset.forName("windows-1251")), Report.class);
      log.info("Report received successfully!");
      return report;
    } catch (IOException e) {
      log.info("Report json doesn't exist!");
      return new Report();
    }
  }

  @Override
  public void fillMap(Map<String, String> map) {
    Report report;
    if ((report = getReportJson()) != null) {
      map.put("student", report.getStudName());
      map.put("teacher", report.getTchName());
      map.put("group", report.getGroupNum());

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