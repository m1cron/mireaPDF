package ru.micron.ui;

import static ru.micron.ui.UiUtils.getButton;
import static ru.micron.ui.UiUtils.getCheckBox;
import static ru.micron.ui.UiUtils.getTextArea;
import static ru.micron.ui.UiUtils.getTextField;

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
import ru.micron.converting.MakeDocuments;
import ru.micron.converting.Mapper;
import ru.micron.formatting.ReportConstants;
import ru.micron.formatting.ReportDate;
import ru.micron.formatting.ReportFormatting;
import ru.micron.reporting.Report;
import ru.micron.reporting.ReportHandler;

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
  private JTextField pracNumber;
  private JTextArea targetContent;
  private JTextArea teorContent;
  private JTextArea stepByStep;
  private JTextArea code;
  private JTextArea conclusion;
  private JTextArea literature;
  private JCheckBox checkMakeDocx;

  @PostConstruct
  public void run() {
    report = reportHandler.getReport();

    teacher = getTextField(report.getTchName(), UiConstants.TEACHER_FIELD_SIZE);
    student = getTextField(report.getStudName(), UiConstants.STUDENT_FIELD_SIZE);
    group = getTextField(report.getGroupNum(), UiConstants.GROUP_FIELD_SIZE);
    pracNumber = getTextField(report.getPracNumber(), UiConstants.PRAC_NUM_FIELD_SIZE);
    targetContent = getTextArea(report.getTarget());
    teorContent = getTextArea(report.getTheory());
    stepByStep = getTextArea(report.getStepByStep());
    code = getTextArea(report.getCode());
    conclusion = getTextArea(report.getConclusion());
    literature = getTextArea(report.getLiterature());
    checkMakeDocx = getCheckBox(UiConstants.CHECK_MAKE_DOCX, SwingConstants.RIGHT);
    JButton createPdf = getButton(UiConstants.BTN_MAKE_PDF);
    createPdf.addActionListener(e -> doWork());

    add(teacher);
    add(student);
    add(group);
    add(pracNumber);
    add(new JScrollPane(targetContent));
    add(new JScrollPane(teorContent));
    add(new JScrollPane(stepByStep));
    add(new JScrollPane(code));
    add(new JScrollPane(conclusion));
    add(new JScrollPane(literature));
    add(createPdf);
    add(checkMakeDocx);
  }

  private void doWork() {
    report.setGroupNum(group.getText());
    report.setStudName(student.getText());
    report.setTchName(teacher.getText());
    report.setPracNumber(pracNumber.getText());
    report.setTarget(targetContent.getText());
    report.setTheory(teorContent.getText());
    report.setStepByStep(stepByStep.getText());
    report.setConclusion(conclusion.getText());
    report.setLiterature(literature.getText());
    report.setCode(code.getText());
    makeDocuments.getMap().putAll(Mapper.map(reportHandler.save(report)));
    makeDocuments.getMap().putAll(Mapper.map(reportDate));
    makeDocuments.getMap()
        .put(ReportConstants.REPORT, reportFormatting.formatDocument(makeDocuments.getMap()));
    makeDocuments.makeHtml();
    makeDocuments.makePdf();
    if (checkMakeDocx.isSelected()) {
      makeDocuments.makeWord();
    }
    makeDocuments.destroy();
  }

}
