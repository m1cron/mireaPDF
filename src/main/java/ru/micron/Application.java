package ru.micron;

import ru.micron.ui.MainFrame;

public class Application {
  public static void main(String[] args) {
    System.setProperty("file.encoding", "windows-1251");
    new MainFrame();
  }
}