package ru.micron.ui;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MainFrame extends JFrame {

  private final MainPanel mainPanel;

  public void run() {
    setTitle(UiConstants.TITLE);
    setIconImage(new ImageIcon(getClass().getResource(UiConstants.ICON_PNG)).getImage());
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);
    setLocationRelativeTo(null);
    add(mainPanel);
    pack();
    setSize(UiConstants.FRAME_WIDTH, UiConstants.FRAME_HEIGHT);
    setVisible(true);
  }

}
