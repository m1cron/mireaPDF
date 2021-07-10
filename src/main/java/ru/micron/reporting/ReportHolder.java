package ru.micron.reporting;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.IOException;
import java.nio.file.Path;
import javax.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.micron.converting.ResultMapHolder;
import ru.micron.web.GithubApi;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReportHolder {

  private static final String REPORT_FILE_NAME = "report.json";

  private final ObjectMapper mapper;
  private final ResultMapHolder mapHolder;

  @Getter
  private Report report;

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

  public void save(Report report) {
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
      log.error(e.getMessage());
    }
    mapHolder.save(this.report);
  }
}