package ru.micron.ui;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class MainFrame extends JFrame {

  public MainFrame() {
    setTitle(UiConstants.TITLE);
    setIconImage(new ImageIcon(getClass().getResource(UiConstants.ICON_PNG)).getImage());
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);
    setLocationRelativeTo(null);
    add(new MainPanel());
    pack();
    setSize(UiConstants.FRAME_WIDTH, UiConstants.FRAME_HEIGHT);
    setVisible(true);
  }

}
