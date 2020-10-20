package ru.micron;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import ru.micron.json.GithubJson;
import ru.micron.utils.UtilsForIO;

import java.util.ArrayList;
import java.util.Collections;

public class Github extends UtilsForIO {
    private final StringBuffer code;
    private Gson gson;
    private MyProxy myProxy;
    private final boolean useProxy;
    private final ArrayList<String> codeArr;

    public Github(String codeOrUrl, boolean useProxy, String proxyPing) {
        codeArr = new ArrayList<>(500);
        this.useProxy = useProxy;
        code = new StringBuffer(5000);
        if (codeOrUrl.contains("github.com/")) {
            gson = new Gson();
            if (useProxy) {
                myProxy = new MyProxy(gson, proxyPing);
                myProxy.getNewProxy();
            }
            recursSearchGit(parseUrl(codeOrUrl));
        } else {
            splitAdd(codeOrUrl);
        }
    }

    public static String parseUrl(String url) {
        return url.replace("github.com", "api.github.com/repos")
                    .replace("/tree/master/", "/contents/")
                    .replace("/blob/master/", "/contents/");
    }

    private void splitAdd(String path, String codeBuff) {
        if (!path.equals("")) {
            codeArr.add("\t\n\n<strong>" + path + "</strong>\n\n");
        }
        Collections.addAll(codeArr, codeBuff.split("(?=\n)"));
    }

    private void splitAdd(String codeBuff) {
        Collections.addAll(codeArr, codeBuff.split("(?=\n)"));
    }

    private String formatWidth(String str) {
        final short maxWidth = 65;      /*                <<------ page maxWidth content change HERE */
        if (str.length() > maxWidth) {
            return str.substring(0, maxWidth) + "\n" + formatWidth(str.substring(maxWidth));
        }
        return str;
    }

    private void getFormatCode() {
        String divStart = "<div class=\"page\">\n\t\t<div class=\"content\">\n\t\n\t\t<pre class=\"code\">\n";
        String divEnd = "\n\t\t</pre>\n\t</div>\n</div>\n\n";

        code.append(divStart).append("\t<h2 class=\"h2\">Код</h2>");
        short count = 4;
        for (String s : codeArr) {
            code.append(formatWidth(s));
            if (count == 45) {      /*                      <<------ page maxHeight content change HERE */
                code.append(divEnd).append(divStart);
                count = 0;
            }
            count++;
        }
        code.append(divEnd);
    }

    public void recursSearchGit(String url) {
        String json = useProxy ?
                            UtilsForIO.readStringFromURL(url, myProxy, myProxy.getProxy()) :
                            UtilsForIO.readStringFromURL(url);
        try {
            GithubJson[] roots = gson.fromJson(json, GithubJson[].class);
            for (GithubJson root : roots) {
                if (root.type.equals("dir")) {
                    recursSearchGit(root.url);
                } else if (root.download_url != null) {
                    if (root.path.contains(".java")) {
                        System.out.println("download " + root.path);
                        splitAdd(root.path,
                                            useProxy ?
                                                UtilsForIO.readStringFromURL(root.download_url, myProxy, myProxy.getProxy()) :
                                                UtilsForIO.readStringFromURL(root.download_url));
                    }
                }
            }
        } catch (JsonParseException e) {
            GithubJson root = gson.fromJson(json, GithubJson.class);
            if (root.path.contains(".java")) {
                splitAdd(root.path,
                                    useProxy ?
                                        UtilsForIO.readStringFromURL(root.download_url, myProxy, myProxy.getProxy()) :
                                        UtilsForIO.readStringFromURL(root.download_url));
            }
        }
    }

    public String getCode() {
        getFormatCode();
        return code.toString();
    }
}