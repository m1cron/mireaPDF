package ru.micron.converting;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConvertingConstants {

  public static final String DRIVER_NAME = "webdriver.chrome.driver";

  public static final String OS_NAME = "os.name";
  public static final String OS_WIN_REGEX = "(.*)(win)(.*)";
  public static final String OS_LINUX_REGEX = "(.*)(nix|nux|aix)(.*)";
  public static final String OS_MAC_REGEX = "(.*)(mac)(.*)";

  public static final String DRIVER_WIN_PATH = "./chromedriver.exe";
  public static final String DRIVER_LINUX_PATH = "/usr/bin/chromedriver";
  public static final String DRIVER_MAC_PATH = "/usr/local/bin/chromedriver";

  public static final String CONVERT_TO_PDF_URL = "https://deftpdf.com/ru/html-to-pdf";
  public static final String CONVERT_TO_DOCX_URL = "https://www.pdf2go.com/ru/pdf-to-word";
  public static final String INPUT_ELEMENT = "input[type=file]";
  public static final String HREF = "href";
  public static final String IMG = "img";
}
