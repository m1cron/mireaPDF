package ru.micron.reporting;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReportConstants {

  public static final String REPORT_FILE_NAME = "report.json";
  public static final String DOCX_FILE_NAME = "word.docx";
  public static final String PDF_FILE_NAME = "pdf.pdf";
  public static final String HTML_FILE_NAME = "index.html";
  public static final String TEMPLATE_FILE_NAME = "index.ftl";
  public static final String TEMPLATE_DIR_NAME = "/";

}
