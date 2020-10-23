package ru.micron;

import com.google.gson.JsonParseException;
import ru.micron.json.GithubJson;

import java.util.*;

public class Github extends MyProxy {
    private MyProxy myProxy;
    private final boolean useProxy;
    private final List<String> codeArr;
    private final List<Thread> threadArr;

    public Github(String codeOrUrl, boolean useProxy, String proxyPing) {
        this.useProxy = useProxy;
        codeArr = new ArrayList<>(500);
        threadArr = new ArrayList<>(24);

        if (useProxy) {
            myProxy = new MyProxy(gson, proxyPing);
        }
        recursSearchGit(parseUrl(codeOrUrl));

        threadArr.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private synchronized void splitAdd(String path, String codeBuff) {
        codeArr.add("\t\n\n<strong>" + path + "</strong>\n\n");
        Collections.addAll(codeArr, codeBuff.split("\n"));
    }

    public void downloadFromGit(GithubJson json) {
        Thread thread = new Thread(() -> {
            System.out.println("download " + json.getPath() + " in thread " + Thread.currentThread().getName());
            splitAdd(json.getPath(),
                    useProxy  ?
                                readStringFromURL(json.getDownload_url(), myProxy) :
                                readStringFromURL(json.getDownload_url()));
        });
        thread.start();
        threadArr.add(thread);
    }

    public void recursSearchGit(String url) {
        List<GithubJson> githubArr = new ArrayList<>(12);

        String stringJson = useProxy  ?
                                        readStringFromURL(url, myProxy) :
                                        readStringFromURL(url);
        try {
            githubArr.addAll(Arrays.asList(gson.fromJson(stringJson, GithubJson[].class)));
        } catch (JsonParseException e) {
            githubArr.add(gson.fromJson(stringJson, GithubJson.class));
        } finally {
            for (GithubJson json : githubArr) {
                if (json.getType().equals("dir")) {
                    recursSearchGit(json.getUrl());
                } else if ((json.getPath().toLowerCase().contains(".java") ||
                            json.getPath().toLowerCase().contains(".c")    ||
                            json.getPath().toLowerCase().contains(".cpp")  ||
                            json.getPath().toLowerCase().contains(".html") ||
                            json.getPath().toLowerCase().contains(".js")   ||
                            json.getPath().toLowerCase().contains(".css")) && json.getDownload_url() != null) {
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
}