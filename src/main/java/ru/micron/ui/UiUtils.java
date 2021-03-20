package ru.micron.ui;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UiUtils {

  public static JTextField getTextField(String text, int column) {
    JTextField jTextField = new JTextField(text, column);
    jTextField.setBackground(UiConstants.FRAME_COLOR);
    return jTextField;
  }

  public static JTextArea getTextArea(String text) {
    JTextArea jTextArea = new JTextArea(text, UiConstants.ROWS_SIZE, UiConstants.COLUMNS_SIZE);
    jTextArea.setFont(UiConstants.DEFAULT_FONT);
    return jTextArea;
  }

  public static JCheckBox getCheckBox(String text, int position) {
    JCheckBox jCheckBox = new JCheckBox(text);
    jCheckBox.setFont(UiConstants.DEFAULT_FONT);
    jCheckBox.setHorizontalTextPosition(position);
    return jCheckBox;
  }

  public static JButton getButton(String text) {
    JButton jButton = new JButton(text);
    jButton.setFont(UiConstants.DEFAULT_FONT);
    return jButton;
  }

}
