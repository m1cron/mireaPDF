package ru.micron;

import ru.micron.json.Github;
import ru.micron.json.StudentInfo;
import ru.micron.json.StudentJson;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class GUI {
    private final JFrame frame;
    private final JPanel panel;

    public GUI() throws HeadlessException {
        frame = new JFrame("MIREA PDF");
        ImageIcon img = new ImageIcon("java.png");
        frame.setIconImage(img.getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(700, 460));
        frame.add(panel);
        frame.pack();
    }

    public void run() {
        StudentInfo info = StudentJson.getStudentInfo();

        JTextField teacher = new JTextField(info == null ? "Преподаватель" : info.getTchName(), 20);
        teacher.setBackground(Color.WHITE);

        JTextField student = new JTextField(info == null ? "Студент" : info.getStudName(), 20);
        student.setBackground(Color.WHITE);

        JTextField group = new JTextField(info == null ? "Группа" : info.getGroupNum(), 14);
        group.setBackground(Color.WHITE);

        JTextField prac_number = new JTextField("№", 5);
        prac_number.setBackground(Color.WHITE);

        JTextArea target_content = new JTextArea("Цель работы", 10, 20);
        target_content.setFont(new Font("Dialog", Font.PLAIN, 14));

        JTextArea teor_content = new JTextArea("Теоретическое введение", 10, 20);
        teor_content.setFont(new Font("Dialog", Font.PLAIN, 14));

        JTextArea step_by_step = new JTextArea("Ход работы", 10, 20);
        step_by_step.setFont(new Font("Dialog", Font.PLAIN, 14));

        JTextArea code = new JTextArea("Код с GitHub или ссылка", 10, 20);
        code.setFont(new Font("Dialog", Font.PLAIN, 14));

        JTextArea conclusion_content = new JTextArea("Вывод", 10, 20);
        conclusion_content.setFont(new Font("Dialog", Font.PLAIN, 14));

        JTextArea literature_content = new JTextArea("Используемая литература", 10, 20);
        literature_content.setFont(new Font("Dialog", Font.PLAIN, 14));

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

        createPdf.addActionListener(e -> {
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
            map.put("year", "2020");

            StudentJson.saveStudentJson(student.getText(),
                                        group.getText(),
                                        teacher.getText());

            if (code.getText().contains("github.com/"))
                map.put("code", new Github().getCode(code.getText()));
            else {
                String buf = "<div class=\"page\">\n<div class=\"content\">\n" +
                        "<h2 class=\"h2\">Код</h2>\n<pre class=\"code\">\n" +
                        code.getText() + "\n</pre>\n</div>\n</div>";
                map.put("code", buf);
            }

            new MakeHtml("./templates", "index.ftl")
                    .makeHtml(map, "./index.html");
            new MakePdf().makePdf("./index.html", "pdf.pdf");
        });

        panel.add(createPdf);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new GUI().run();
    }
}