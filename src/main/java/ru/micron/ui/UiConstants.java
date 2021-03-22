package ru.micron.ui;

import java.awt.Color;
import java.awt.Font;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UiConstants {

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
}
