package ru.micron.ui;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UIUtils {

  public static final int FRAME_WIDTH = 725;
  public static final int FRAME_HEIGHT = 530;
  public static final int FRAME_FONT = Font.PLAIN;
  public static final int FRAME_SIZE = 14;
  public static final Color FRAME_COLOR = Color.WHITE;

  public static final String TITLE = "MIREA PDF";
  public static final String ICON_PNG = "/icon.png";
  public static final String DIALOG = "Dialog";
  public static final String CHECK_MAKE_DOCX = "Make DOCX?";
  public static final String BTN_MAKE_PDF = "Make PDF";
  public static final Font DEFAULT_FONT = new Font(DIALOG, FRAME_FONT, FRAME_SIZE);
  public static final int ROWS_SIZE = 10;
  public static final int COLUMNS_SIZE = 20;

  public static final int TEACHER_FIELD_SIZE = 20;
  public static final int STUDENT_FIELD_SIZE = 20;
  public static final int GROUP_FIELD_SIZE = 13;
  public static final int PRAC_NUM_FIELD_SIZE = 6;

  public static JTextField createTextField(String text, int column) {
    JTextField jTextField = new JTextField(text, column);
    jTextField.setBackground(FRAME_COLOR);
    return jTextField;
  }

  public static JTextArea createTextArea(String text) {
    JTextArea jTextArea = new JTextArea(text, ROWS_SIZE, COLUMNS_SIZE);
    jTextArea.setFont(DEFAULT_FONT);
    return jTextArea;
  }

  public static JCheckBox createCheckBox(String text, int position) {
    JCheckBox jCheckBox = new JCheckBox(text);
    jCheckBox.setFont(DEFAULT_FONT);
    jCheckBox.setHorizontalTextPosition(position);
    return jCheckBox;
  }

  public static JButton createButton(String text) {
    JButton jButton = new JButton(text);
    jButton.setFont(DEFAULT_FONT);
    return jButton;
  }
}
