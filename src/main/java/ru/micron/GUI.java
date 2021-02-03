package ru.micron;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.micron.config.AppConfiguration;
import ru.micron.model.Report;
import ru.micron.model.Student;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GUI {
    private final JFrame frame;
    private final JPanel panel;
    private final StudentJsonIO studentJsonIO;
    private final ReportJsonIO reportJsonIO;
    private final ApplicationContext context;

    public GUI() throws HeadlessException {
        context = new AnnotationConfigApplicationContext(AppConfiguration.class);
        frame = new JFrame("MIREA PDF");
        frame.setIconImage(new ImageIcon("./icon.png").getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(825, 490));
        frame.add(panel);
        frame.pack();
        frame.setResizable(true);

        studentJsonIO = new StudentJsonIO();
        reportJsonIO = new ReportJsonIO();
    }

    public void run() {
        Student studentJson = studentJsonIO.getStudentJson();
        Report report = reportJsonIO.getReportJson();

        JTextField teacher = new JTextField(studentJson.getTchName(), 20);
        teacher.setBackground(Color.WHITE);

        JTextField student = new JTextField(studentJson.getStudName(), 20);
        student.setBackground(Color.WHITE);

        JTextField group = new JTextField(studentJson.getGroupNum(), 14);
        group.setBackground(Color.WHITE);

        JTextField prac_number = new JTextField(report.getPrac_number(), 5);
        prac_number.setBackground(Color.WHITE);

        JTextArea target_content = new JTextArea(report.getTarget(), 10, 20);
        target_content.setFont(new Font("Dialog", Font.PLAIN, 14));

        JTextArea teor_content = new JTextArea(report.getTheory(), 10, 20);
        teor_content.setFont(new Font("Dialog", Font.PLAIN, 14));

        JTextArea step_by_step = new JTextArea(report.getStep_by_step(), 10, 20);
        step_by_step.setFont(new Font("Dialog", Font.PLAIN, 14));

        JTextArea code = new JTextArea(report.getCode(), 10, 20);
        code.setFont(new Font("Dialog", Font.PLAIN, 14));

        JTextArea conclusion_content = new JTextArea(report.getConclusion(), 10, 20);
        conclusion_content.setFont(new Font("Dialog", Font.PLAIN, 14));

        JTextArea literature_content = new JTextArea(report.getLiterature(), 10, 20);
        literature_content.setFont(new Font("Dialog", Font.PLAIN, 14));

        JCheckBox checkMakeDocx = new JCheckBox("Делать DOCX?");

        JCheckBox useProxy = new JCheckBox("Прокси?");
        useProxy.setHorizontalTextPosition(SwingConstants.LEFT);

        JTextArea pingText = new JTextArea("Ping:");
        pingText.setBackground(panel.getBackground());
        pingText.setEditable(false);

        JTextField proxyPing = new JTextField("100", 3);
        proxyPing.setBackground(Color.WHITE);

        JButton createPdf = new JButton("Создать PDF");

        panel.add(teacher);
        panel.add(student);
        panel.add(group);
        panel.add(prac_number);

        panel.add(new JScrollPane(target_content));
        panel.add(new JScrollPane(teor_content));
        panel.add(new JScrollPane(step_by_step));
        panel.add(new JScrollPane(code));
        panel.add(new JScrollPane(conclusion_content));
        panel.add(new JScrollPane(literature_content));

        panel.add(pingText);
        panel.add(proxyPing);
        panel.add(useProxy);
        panel.add(createPdf);
        panel.add(checkMakeDocx);

        createPdf.addActionListener(e0 -> {
            Map<String, String> map = new HashMap<>(16, 1);

            studentJson.setGroupNum(group.getText());
            studentJson.setStudName(student.getText());
            studentJson.setTchName(teacher.getText());

            report.setPrac_number(prac_number.getText());
            report.setTarget(target_content.getText());
            report.setTheory(teor_content.getText());
            report.setStep_by_step(step_by_step.getText());
            report.setConclusion(conclusion_content.getText());
            report.setLiterature(literature_content.getText());
            report.setCode(code.getText());

            studentJsonIO.fillMap(map);
            reportJsonIO.fillMap(map);

            studentJsonIO.saveJson(studentJson);
            reportJsonIO.saveJson(report);

            long startTime = System.currentTimeMillis();
            if (code.getText().contains("github.com/")) {
                new ReportFormatting(new GithubAPI(code.getText(), useProxy.isSelected(), proxyPing.getText()).getCodeArr()).fillMap(map);
            } else {
                new ReportFormatting(Arrays.asList(code.getText().split("\n"))).fillMap(map);
            }
            System.out.println("Total execution time: " + (System.currentTimeMillis() - startTime) + "ms");

            context.getBean(ReportDate.class).fillMap(map);
            MakeDocuments docs = context.getBean(MakeDocuments.class);
            docs.makeHtml(map);
            docs.makePdf();
            if (checkMakeDocx.isSelected()) {
                docs.makeWord();
                docs.destroy();
            } else {
                docs.destroy();
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
