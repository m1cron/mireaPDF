package ru.micron.web;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WebConstants {

  public static final String GITHUB_API_TEMPLATE_URL =
      "https://api.github.com/repos/%s/%s/contents/%s";

  public static final String MATCHES_REGEX =
      "(?i)[\\w/]+\\.(java|xml|gradle|c|cpp|hpp|h|cs|cc|cxx|html|css|js|php|py)$";

  public static final String[] PROXY_URLS = {
      "http://pubproxy.com/api/proxy?format=txt&type=http&limit=5&https=true&last_check=30",
      "https://api.proxyscrape.com/?request=displayproxies&proxytype=https&timeout=100"
  };

  public static final int CONNECT_TIMEOUT_MS = 1000;
  public static final int READ_TIMEOUT_MS = 2000;
}
