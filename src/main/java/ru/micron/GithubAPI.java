package ru.micron;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.micron.model.Github;

@Slf4j
public class GithubAPI extends MyProxy {

  @Getter
  private static final String regExPattern =
      "(?i)[\\w/]+\\.(java|c|cpp|hpp|h|cs|cc|cxx|html|css|js|php|py)$";

  private final Gson gson;
  private final boolean useProxy;
  @Getter
  private final List<String> codeArr;
  private final List<Thread> threadArr;

  public GithubAPI(String link, boolean useProxy, String proxyPing) {
    super(proxyPing, useProxy);
    this.useProxy = useProxy;
    gson = new Gson();
    codeArr = new ArrayList<>(500);
    threadArr = new ArrayList<>(24);
    recursSearchGit(parseUrl(link));
    threadArr.forEach(thread -> {
      try {
        thread.join();
      } catch (InterruptedException e) {
        log.warn(e.getMessage());
      }
    });
  }

  private synchronized void splitAdd(String path, String codeBuff) {
    codeArr.add(String.format("\t\n\n<strong>%s</strong>\n\n", path));
    Collections.addAll(codeArr, codeBuff.split("\n"));
  }

  public void downloadFromGit(Github github) {
    Thread thread = new Thread(() -> {
      splitAdd(github.getPath(),
          useProxy ? readStringFromURL(github.getDownload_url(), getProxy()) :
              readStringFromURL(github.getDownload_url()));
      log.info(String
          .format("download %s in thread %s", github.getPath(), Thread.currentThread().getName()));
    });
    thread.start();
    threadArr.add(thread);
  }

  public void recursSearchGit(String url) {
    List<Github> githubArr = new ArrayList<>();
    String stringJson = useProxy ? readStringFromURL(url, getProxy()) : readStringFromURL(url);
    try {
      githubArr.addAll(Arrays.asList(gson.fromJson(stringJson, Github[].class)));
    } catch (JsonParseException e) {
      githubArr.add(gson.fromJson(stringJson, Github.class));
    } finally {
      for (Github github : githubArr) {
        if (github.getType().equals("dir")) {
          recursSearchGit(github.getUrl());
        } else if (github.getPath().matches(regExPattern) && github.getDownload_url() != null) {
          downloadFromGit(github);
        }
      }
    }
  }

  public static String parseUrl(String url) {
    return url.replace("github.com", "api.github.com/repos")
        .replace("/tree/master/", "/contents/")
        .replace("/blob/master/", "/contents/");
  }

}