package ru.micron.ui;

import static ru.micron.ui.UiUtils.getButton;
import static ru.micron.ui.UiUtils.getCheckBox;
import static ru.micron.ui.UiUtils.getLabel;
import static ru.micron.ui.UiUtils.getTextArea;
import static ru.micron.ui.UiUtils.getTextField;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.micron.GithubAPI;
import ru.micron.MakeDocuments;
import ru.micron.ReportDate;
import ru.micron.ReportFormatting;
import ru.micron.config.AppConfiguration;
import ru.micron.model.Report;
import ru.micron.model.ReportHandler;

@Slf4j
public class MainPanel extends JPanel {

  private final ReportHandler reportHandler;
  private final MakeDocuments makeDocuments;
  private final ReportDate reportDate;
  private final Report report;

  private final JTextField teacher;
  private final JTextField student;
  private final JTextField group;
  private final JTextField prac_number;
  private final JTextField proxyPing;

  private final JTextArea target_content;
  private final JTextArea teor_content;
  private final JTextArea step_by_step;
  private final JTextArea code;
  private final JTextArea conclusion;
  private final JTextArea literature;

  private final JCheckBox checkMakeDocx;
  private final JCheckBox useProxy;


  public MainPanel() {
    ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
    reportHandler = context.getBean(ReportHandler.class);
    makeDocuments = context.getBean(MakeDocuments.class);
    reportDate = context.getBean(ReportDate.class);
    report = reportHandler.getReportJson();

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
      new ReportFormatting().formatCode(
          new GithubAPI(code.getText(), useProxy.isSelected(), proxyPing.getText())
              .getCodeArr()).fillMap(map);
    } else {
      new ReportFormatting().formatCode(Arrays.asList(code.getText().split("\n")))
          .fillMap(map);
    }
    log.info(String.format("Total execution time: %d ms", System.currentTimeMillis() - startTime));

    reportDate.fillMap(map);
    makeDocuments.makeHtml(map);
    makeDocuments.makePdf();
    if (checkMakeDocx.isSelected()) {
      makeDocuments.makeWord();
    }
    makeDocuments.destroy();
  }

}
