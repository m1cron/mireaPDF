package ru.micron.ui;

import static ru.micron.ui.UIUtils.BTN_MAKE_PDF;
import static ru.micron.ui.UIUtils.CHECK_MAKE_DOCX;
import static ru.micron.ui.UIUtils.GROUP_FIELD_SIZE;
import static ru.micron.ui.UIUtils.PRAC_NUM_FIELD_SIZE;
import static ru.micron.ui.UIUtils.STUDENT_FIELD_SIZE;
import static ru.micron.ui.UIUtils.TEACHER_FIELD_SIZE;
import static ru.micron.ui.UIUtils.createButton;
import static ru.micron.ui.UIUtils.createCheckBox;
import static ru.micron.ui.UIUtils.createTextArea;
import static ru.micron.ui.UIUtils.createTextField;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.micron.converting.MakeDocuments;
import ru.micron.converting.ResultMapHolder;
import ru.micron.formatting.ReportFormatting;
import ru.micron.reporting.Report;
import ru.micron.reporting.ReportHolder;

@Component
@RequiredArgsConstructor
public class MainPanel extends JPanel {

  private final ReportHolder reportHolder;
  private final MakeDocuments makeDocuments;
  private final ReportFormatting reportFormatting;
  private final ResultMapHolder resultMapHolder;
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
  public void init() {
    report = reportHolder.getReport();

    JButton createPdf;
    add(teacher = createTextField(report.getTchName(), TEACHER_FIELD_SIZE));
    add(student = createTextField(report.getStudName(), STUDENT_FIELD_SIZE));
    add(group = createTextField(report.getGroupNum(), GROUP_FIELD_SIZE));
    add(pracNumber = createTextField(report.getPracNumber(), PRAC_NUM_FIELD_SIZE));
    add(new JScrollPane(targetContent = createTextArea(report.getTarget())));
    add(new JScrollPane(teorContent = createTextArea(report.getTheory())));
    add(new JScrollPane(stepByStep = createTextArea(report.getStepByStep())));
    add(new JScrollPane(code = createTextArea(report.getCode())));
    add(new JScrollPane(conclusion = createTextArea(report.getConclusion())));
    add(new JScrollPane(literature = createTextArea(report.getLiterature())));
    add(createPdf = createButton(BTN_MAKE_PDF));
    add(checkMakeDocx = createCheckBox(CHECK_MAKE_DOCX, SwingConstants.RIGHT));

    createPdf.addActionListener(e -> doWork());
  }

  private void doWork() {
    report.setGroupNum(group.getText())
        .setStudName(student.getText())
        .setTchName(teacher.getText())
        .setPracNumber(pracNumber.getText())
        .setTarget(targetContent.getText())
        .setTheory(teorContent.getText())
        .setStepByStep(stepByStep.getText())
        .setConclusion(conclusion.getText())
        .setLiterature(literature.getText())
        .setCode(code.getText());

    reportHolder.save(report);

    reportFormatting.formatResultMap();

    makeDocuments.makeHtml();
    makeDocuments.makePdf();
    if (checkMakeDocx.isSelected()) {
      makeDocuments.makeWord();
    }
    makeDocuments.destroy();
    resultMapHolder.clear();
  }
}
