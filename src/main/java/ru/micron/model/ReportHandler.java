package ru.micron.model;

import com.google.gson.Gson;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.micron.formatting.MapFilling;
import ru.micron.formatting.ReportConstants;

@Slf4j
@Component
public class ReportHandler implements MapFilling {

  @Value("${file.name.report}")
  private String reportFileName;
  @Getter
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
      report = gson.fromJson(Files.readString(Path.of("report.json"),
          Charset.forName("windows-1251")), Report.class);
      log.info("Report received successfully!");
      return report;
    } catch (IOException e) {
      log.info("Report json doesn't exist!");
      return new Report();
    }
  }

  @Override
  public void fillMap(Map<String, String> map) {
    Report report = this.report;
    if (report != null) {
      map.put(ReportConstants.STUDENT, report.getStudName());
      map.put(ReportConstants.TEACHER, report.getTchName());
      map.put(ReportConstants.GROUP, report.getGroupNum());
      map.put(ReportConstants.PRAC_NUMBER, report.getPrac_number());
      map.put(ReportConstants.TARGET_CONTENT, report.getTarget());
      map.put(ReportConstants.TEOR_CONTENT, report.getTheory());
      map.put(ReportConstants.STEP_BY_STEP, report.getStep_by_step());
      map.put(ReportConstants.CONCLUSION, report.getConclusion());
      map.put(ReportConstants.LITERATURE, report.getLiterature());
    }
  }

}