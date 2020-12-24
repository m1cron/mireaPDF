package ru.micron;

import com.google.gson.JsonParseException;
import ru.micron.json.GithubJson;

import java.util.*;

public class Github extends MyProxy {
    private final List<String> codeArr;
    private final List<Thread> threadArr;
    private static final String regExPattern = "(?i)[\\w/]+\\.(java|c|cpp|hpp|h|cs|cc|cxx|html|css|js|php|py)$";

    public Github(String link, boolean useProxy, String proxyPing) {
        super(proxyPing, useProxy);
        codeArr = new ArrayList<>(500);
        threadArr = new ArrayList<>(24);
        recursSearchGit(parseUrl(link));
        threadArr.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private synchronized void splitAdd(String path, String codeBuff) {
        codeArr.add(String.format("\t\n\n<strong>%s</strong>\n\n", path));
        Collections.addAll(codeArr, codeBuff.split("\n"));
    }

    public void downloadFromGit(GithubJson json) {
        Thread thread = new Thread(() -> {
            splitAdd(json.getPath(),
                    useProxy ? readStringFromURL(json.getDownload_url(), getProxy()) :
                                readStringFromURL(json.getDownload_url()));
            System.out.printf("download %s in thread %s\n", json.getPath(), Thread.currentThread().getName());
        });
        thread.start();
        threadArr.add(thread);
    }

    public void recursSearchGit(String url) {
        List<GithubJson> githubArr = new ArrayList<>();
        String stringJson = useProxy ? readStringFromURL(url, getProxy()) : readStringFromURL(url);
        try {
            githubArr.addAll(Arrays.asList(gson.fromJson(stringJson, GithubJson[].class)));
        } catch (JsonParseException e) {
            githubArr.add(gson.fromJson(stringJson, GithubJson.class));
        } finally {
            for (GithubJson json : githubArr) {
                if (json.getType().equals("dir")) {
                    recursSearchGit(json.getUrl());
                } else if (json.getPath().matches(regExPattern) && json.getDownload_url() != null) {
                    downloadFromGit(json);
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