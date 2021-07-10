package ru.micron.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestClientException;
import ru.micron.web.dto.GithubEntity;

@Slf4j
public class GithubApi {

  public static final String MATCHES_REGEX = "(?i)[\\w/]+\\.(java|yaml|groovy|yml|"
      + "properties|xml|gradle|c|cpp|hpp|h|cs|cc|cxx|html|css|js|php|py|Dockerfile|sh)$";

  private static final String GITHUB_API_TEMPLATE_URL =
      "https://api.github.com/repos/%s/%s/contents/%s";
  private static final int CODE_BUFF_SIZE = 1000;

  @Getter
  private final StringBuilder buff;
  private final List<Thread> threadArr;
  private final RestHolder restHolder;

  public GithubApi(String url) {
    this.buff = new StringBuilder(CODE_BUFF_SIZE);
    this.restHolder = new RestHolder();
    this.threadArr = new ArrayList<>();

    recursSearchGit(decodeUrl(url));

    threadArr.forEach(thread -> {
      try {
        thread.join();
      } catch (InterruptedException e) {
        log.error(e.getMessage());
      }
    });
    System.out.println(buff);
  }

  private void addCodeSection(String path, String codeBuff) {
    synchronized (this) {
      buff.append(String.format("\t\n\n<strong>%s</strong>\n\n", path))
          .append(codeBuff
              .replace("<", "&lt;")
              .replace(">", "&gt;")
              .replace("\"", "&quot;"));
    }
  }

  private void downloadFromGit(GithubEntity github) {
    var thread = new Thread(() -> {
      Optional.ofNullable(restHolder.getObject(github.getDownload_url(), String.class))
          .ifPresent(str -> addCodeSection(github.getPath(), str));
      log.info("download {} in {}", github.getPath(), Thread.currentThread().getName());
    });
    thread.start();
    threadArr.add(thread);
  }

  private void recursSearchGit(String url) {
    List<GithubEntity> githubArr = new ArrayList<>();
    try {
      Optional.ofNullable(restHolder.getObject(url, GithubEntity[].class))
          .ifPresent(json -> githubArr.addAll(Arrays.asList(json)));
    } catch (RestClientException e) {
      Optional.ofNullable(restHolder.getObject(url, GithubEntity.class))
          .ifPresent(githubArr::add);
    } finally {
      for (var github : githubArr) {
        if (github.getType().contains("dir")) {
          recursSearchGit(github.getUrl());
        } else if (github.getName().matches(MATCHES_REGEX)
            && github.getDownload_url() != null) {
          downloadFromGit(github);
        }
      }
    }
  }

  private String decodeUrl(String url) {
    var subSplitUrl = url.substring(url.indexOf(".com/") + 5).split("/");
    var endsPath = new StringBuilder();
    for (int i = 4; i < subSplitUrl.length; i++) {
      endsPath.append(subSplitUrl[i]).append("/");
    }
    var res = String.format(GITHUB_API_TEMPLATE_URL,
        subSplitUrl[0], subSplitUrl[1], endsPath);
    log.info("Pars link to {}", res);
    return res;
  }
}