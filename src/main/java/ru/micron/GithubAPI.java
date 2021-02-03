package ru.micron;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.micron.model.Github;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GithubAPI extends MyProxy {

    private final Gson gson;
    private final boolean useProxy;
    private final List<String> codeArr;
    private final List<Thread> threadArr;
    private static final String regExPattern;
    private static final Logger logger;

    static {
        regExPattern = "(?i)[\\w/]+\\.(java|c|cpp|hpp|h|cs|cc|cxx|html|css|js|php|py)$";
        logger = LoggerFactory.getLogger(GithubAPI.class);
    }

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
                logger.warn(e.getMessage());
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
            logger.info(String.format("download %s in thread %s", github.getPath(), Thread.currentThread().getName()));
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

    public List<String> getCodeArr() {
        return codeArr;
    }

    public static String getRegExPattern() {
        return regExPattern;
    }
}