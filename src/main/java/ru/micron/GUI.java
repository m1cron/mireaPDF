package ru.micron;

import ru.micron.json.ReportJson;
import ru.micron.json.StudentJson;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class GUI {
    private final JFrame frame;
    private final JPanel panel;
    private final StudentJsonIO studentJsonIO;
    private final ReportJsonIO reportJsonIO;

    public GUI() throws HeadlessException {
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
        StudentJson studInfo = studentJsonIO.getStudentInfo();
        ReportJson report = reportJsonIO.getReportJson();

        JTextField teacher = new JTextField(studInfo.getTchName(), 20);
        teacher.setBackground(Color.WHITE);

        JTextField student = new JTextField(studInfo.getStudName(), 20);
        student.setBackground(Color.WHITE);

        JTextField group = new JTextField(studInfo.getGroupNum(), 14);
        group.setBackground(Color.WHITE);

        JTextField prac_number = new JTextField("№", 5);
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
            Map<String, String> map = new HashMap<>();

            map.put("teacher", teacher.getText());
            map.put("student", student.getText());
            map.put("group", group.getText());
            map.put("prac_number", prac_number.getText());
            map.put("target_content", target_content.getText());
            map.put("teor_content", teor_content.getText());
            map.put("step_by_step", step_by_step.getText());
            map.put("conclusion_content", conclusion_content.getText());
            map.put("literature_content", literature_content.getText());

            GetDate date = new GetDate();
            map.put("day", date.getDay());
            map.put("month", date.getMonth());
            map.put("year", date.getYear());

            studentJsonIO.saveStudentJson(student.getText(),
                                        group.getText(),
                                        teacher.getText());

            map.put("code", new Github(code.getText(), useProxy.isSelected(), proxyPing.getText()).getCode());

            new MakeHtml("./templates", "index.ftl")
                    .makeHtml(map, "./index.html");

            MakeDocuments docs = new MakeDocuments();
            docs.makePdf("./index.html", "pdf.pdf");
            if (checkMakeDocx.isSelected())
                docs.makeWord("pdf.pdf", "word.docx");
            docs.closeDriver();
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
