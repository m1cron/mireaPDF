package ru.micron;

import ru.micron.json.ReportJson;
import ru.micron.json.StudentJson;

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
        StudentJson studentJson = studentJsonIO.getStudentJson();
        ReportJson reportJson = reportJsonIO.getReportJson();

        JTextField teacher = new JTextField(studentJson.getTchName(), 20);
        teacher.setBackground(Color.WHITE);

        JTextField student = new JTextField(studentJson.getStudName(), 20);
        student.setBackground(Color.WHITE);

        JTextField group = new JTextField(studentJson.getGroupNum(), 14);
        group.setBackground(Color.WHITE);

        JTextField prac_number = new JTextField(reportJson.getPrac_number(), 5);
        prac_number.setBackground(Color.WHITE);

        JTextArea target_content = new JTextArea(reportJson.getTarget(), 10, 20);
        target_content.setFont(new Font("Dialog", Font.PLAIN, 14));

        JTextArea teor_content = new JTextArea(reportJson.getTheory(), 10, 20);
        teor_content.setFont(new Font("Dialog", Font.PLAIN, 14));

        JTextArea step_by_step = new JTextArea(reportJson.getStep_by_step(), 10, 20);
        step_by_step.setFont(new Font("Dialog", Font.PLAIN, 14));

        JTextArea code = new JTextArea(reportJson.getCode(), 10, 20);
        code.setFont(new Font("Dialog", Font.PLAIN, 14));

        JTextArea conclusion_content = new JTextArea(reportJson.getConclusion(), 10, 20);
        conclusion_content.setFont(new Font("Dialog", Font.PLAIN, 14));

        JTextArea literature_content = new JTextArea(reportJson.getLiterature(), 10, 20);
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

            studentJson.setGroupNum(group.getText());
            studentJson.setStudName(student.getText());
            studentJson.setTchName(teacher.getText());

            reportJson.setPrac_number(prac_number.getText());
            reportJson.setTarget(target_content.getText());
            reportJson.setTheory(teor_content.getText());
            reportJson.setStep_by_step(step_by_step.getText());
            reportJson.setConclusion(conclusion_content.getText());
            reportJson.setLiterature(literature_content.getText());
            reportJson.setCode(code.getText());

            studentJsonIO.fillMap(map);
            reportJsonIO.fillMap(map);

            studentJsonIO.saveJson(studentJson);
            reportJsonIO.saveJson(reportJson);

            long startTime = System.currentTimeMillis();

            if (code.getText().contains("github.com/")) {
                new ReportFormatting(new Github(code.getText(), useProxy.isSelected(), proxyPing.getText()).getCodeArr()).fillMap(map);
            } else {
                new ReportFormatting(Arrays.asList(code.getText().split("\n"))).fillMap(map);
            }

            long endTime = System.currentTimeMillis();
            System.out.println("Total execution time: " + (endTime-startTime) + "ms");

            new GetDate().fillMap(map);

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

    public static void main(String[] args) {
        new GUI().run();
    }
}
