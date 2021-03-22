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

  @Getter
  private final StringBuilder buff;
  private final List<Thread> threadArr;
  private final RestHandler restHandler;

  public GithubApi(String link) {
    this.buff = new StringBuilder(WebConstants.CODE_BUFF_SIZE);
    this.restHandler = new RestHandler();
    threadArr = new ArrayList<>();
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
    buff.append(String.format("\t\n\n<strong>%s</strong>\n\n", path))
        .append(codeBuff
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;"));
  }

  public void downloadFromGit(GithubEntity github) {
    var thread = new Thread(() -> {
      Optional.ofNullable(restHandler.getObject(github.getDownload_url(), String.class))
          .ifPresent(str -> splitAdd(github.getPath(), str));
      log.info("download {} in {}", github.getPath(), Thread.currentThread().getName());
    });
    thread.start();
    threadArr.add(thread);
  }

  public void recursSearchGit(String url) {
    List<GithubEntity> githubArr = new ArrayList<>();
    try {
      Optional.ofNullable(restHandler.getObject(url, GithubEntity[].class))
          .ifPresent(json -> githubArr.addAll(Arrays.asList(json)));
    } catch (RestClientException e) {
      Optional.ofNullable(restHandler.getObject(url, GithubEntity.class))
          .ifPresent(githubArr::add);
    } finally {
      for (var github : githubArr) {
        if (github.getType().contains("dir")) {
          recursSearchGit(github.getUrl());
        } else if (github.getPath().matches(WebConstants.MATCHES_REGEX)
            && github.getDownload_url() != null) {
          downloadFromGit(github);
        }
      }
    }
  }

  private String parseUrl(String url) {
    var subSplitUrl = url.substring(url.indexOf(".com/") + 5).split("/");
    var temp = new StringBuilder();
    for (int i = 4; i < subSplitUrl.length; i++)
      temp.append(subSplitUrl[i]).append("/");
    var res = String.format(WebConstants.GITHUB_API_TEMPLATE_URL,
        subSplitUrl[0], subSplitUrl[1], temp);
    log.info("Pars link to {}", res);
    return res;
  }
}