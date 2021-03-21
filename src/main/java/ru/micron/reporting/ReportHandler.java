package ru.micron.reporting;

import static ru.micron.reporting.ReportConstants.REPORT_FILE_NAME;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.micron.formatting.MapFilling;
import ru.micron.formatting.ReportConstants;

@Slf4j
@Component
@NoArgsConstructor
public class ReportHandler implements MapFilling {

  @Getter
  private Report report;
  private final ObjectMapper mapper = new ObjectMapper();

  @PostConstruct
  private void initRepostJson() {
    try {
      this.report =  mapper.readValue(Path.of(REPORT_FILE_NAME).toFile(), Report.class);
      log.info("Report received successfully!");
    } catch (Exception e) {
      this.report = new Report();
      log.warn("Report json doesn't exist!");
    }
  }

  public void saveJson(Report report) {
    try {
      ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
      writer.writeValue(Path.of(REPORT_FILE_NAME).toFile(), report);
      log.info("Report json saved!");
    } catch (IOException e) {
      log.warn(e.getMessage());
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