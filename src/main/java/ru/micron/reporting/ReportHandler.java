package ru.micron.reporting;

import static ru.micron.reporting.ReportConstants.REPORT_FILE_NAME;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.IOException;
import java.nio.file.Path;
import javax.annotation.PostConstruct;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.micron.web.GithubApi;

@Slf4j
@Component
@NoArgsConstructor
public class ReportHandler {

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

  public Report save(Report report) {
    try {
      this.report = report;
      ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
      writer.writeValue(Path.of(REPORT_FILE_NAME).toFile(), report);
      log.info("Report json saved!");
      this.report.setCode(
          report.getCode().contains("github.com/")
              ? new GithubApi(report.getCode()).getBuff().toString()
              : report.getCode()
      );
    } catch (IOException e) {
      log.warn(e.getMessage());
    }
    return this.report;
  }
}