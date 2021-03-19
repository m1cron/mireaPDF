package ru.micron.ui;

import static ru.micron.ui.UiUtils.getButton;
import static ru.micron.ui.UiUtils.getCheckBox;
import static ru.micron.ui.UiUtils.getLabel;
import static ru.micron.ui.UiUtils.getTextArea;
import static ru.micron.ui.UiUtils.getTextField;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.micron.web.GithubAPI;
import ru.micron.converting.MakeDocuments;
import ru.micron.formatting.ReportDate;
import ru.micron.formatting.ReportFormatting;
import ru.micron.model.Report;
import ru.micron.model.ReportHandler;

@Slf4j
@Component
@RequiredArgsConstructor
public class MainPanel extends JPanel {

  private final ReportHandler reportHandler;
  private final MakeDocuments makeDocuments;
  private final ReportDate reportDate;
  private final ReportFormatting reportFormatting;
  private Report report;

  private JTextField teacher;
  private JTextField student;
  private JTextField group;
  private JTextField prac_number;
  private JTextField proxyPing;

  private JTextArea target_content;
  private JTextArea teor_content;
  private JTextArea step_by_step;
  private JTextArea code;
  private JTextArea conclusion;
  private JTextArea literature;

  private JCheckBox checkMakeDocx;
  private JCheckBox useProxy;

  @PostConstruct
  public void run() {
    report = reportHandler.getReport();

    teacher = getTextField(report.getTchName(), UiConstants.TEACHER_FIELD_SIZE);
    student = getTextField(report.getStudName(), UiConstants.STUDENT_FIELD_SIZE);
    group = getTextField(report.getGroupNum(), UiConstants.GROUP_FIELD_SIZE);
    prac_number = getTextField(report.getPrac_number(), UiConstants.PRAC_NUM_FIELD_SIZE);
    proxyPing = getTextField(UiConstants.PROXY_PING, UiConstants.PROXY_PING_FIELD_SIZE);

    target_content = getTextArea(report.getTarget());
    teor_content = getTextArea(report.getTheory());
    step_by_step = getTextArea(report.getStep_by_step());
    code = getTextArea(report.getCode());
    conclusion = getTextArea(report.getConclusion());
    literature = getTextArea(report.getLiterature());

    checkMakeDocx = getCheckBox(UiConstants.CHECK_MAKE_DOCX, SwingConstants.RIGHT);
    useProxy = getCheckBox(UiConstants.CHECK_USE_PROXY, SwingConstants.LEFT);

    JButton createPdf = getButton(UiConstants.BTN_MAKE_PDF);
    createPdf.addActionListener(e -> doWork());

    add(teacher);
    add(student);
    add(group);
    add(prac_number);
    add(new JScrollPane(target_content));
    add(new JScrollPane(teor_content));
    add(new JScrollPane(step_by_step));
    add(new JScrollPane(code));
    add(new JScrollPane(conclusion));
    add(new JScrollPane(literature));
    add(getLabel(UiConstants.PING_TEXT));
    add(proxyPing);
    add(useProxy);
    add(createPdf);
    add(checkMakeDocx);
  }

  private void doWork() {
    Map<String, String> map = new HashMap<>(16, 1);

    report.setGroupNum(group.getText());
    report.setStudName(student.getText());
    report.setTchName(teacher.getText());
    report.setPrac_number(prac_number.getText());
    report.setTarget(target_content.getText());
    report.setTheory(teor_content.getText());
    report.setStep_by_step(step_by_step.getText());
    report.setConclusion(conclusion.getText());
    report.setLiterature(literature.getText());
    report.setCode(code.getText());

    reportHandler.fillMap(map);
    reportHandler.saveJson(report);

    long startTime = System.currentTimeMillis();
    if (code.getText().contains("github.com/")) {
      reportFormatting.formatCode(
          new GithubAPI(code.getText(), useProxy.isSelected(), proxyPing.getText())
              .getCodeArr())
          .fillMap(map);
    } else {
      reportFormatting.formatCode(Arrays.asList(code.getText().split("\n")))
          .fillMap(map);
    }
    log.info("Total execution time: {} ms", System.currentTimeMillis() - startTime);

    reportDate.fillMap(map);
    makeDocuments.makeHtml(map);
    makeDocuments.makePdf();
    if (checkMakeDocx.isSelected()) {
      makeDocuments.makeWord();
    }
    makeDocuments.destroy();
  }

}
