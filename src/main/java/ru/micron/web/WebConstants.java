package ru.micron.web;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WebConstants {

  public static final String MATCHES_REGEX =
      "(?i)[\\w/]+\\.(java|xml|gradle|c|cpp|hpp|h|cs|cc|cxx|html|css|js|php|py)$";
  public static final String PROXY_URL =
      "https://www.proxyscan.io/api/proxy?format=json&uptime=75&last_check=600&ping=%s";
  public static final int CONNECT_TIMEOUT_MS = 2000;
  public static final int READ_TIMEOUT_MS = 3000;
}
