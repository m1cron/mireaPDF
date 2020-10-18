package ru.micron.json;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import ru.micron.utils.UtilsForIO;

class JsonFields {
    public String name, path, url, download_url, type;

    @Override
    public String toString() {
        return "Json{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", url='" + url + '\'' +
                ", download_url='" + download_url + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}

public class Github extends UtilsForIO {
    private final StringBuffer code;
    private Gson gson;
    private MyProxy myProxy;
    private final boolean useProxy;
    private boolean headerFlag = true;
    private boolean startFlag = true;
    private boolean endFlag = false;
    private boolean flagNewPage = false;
    private final String divStart = "<div class=\"page\">\n\t\t<div class=\"content\">\n\t";
    private final String divEnd = "\n\t\t</pre>\n\t</div>\n</div>\n\n";
    private final String codeStart = "\n\t\t<pre class=\"code\">\n";
    private final int maxHeight = 50;

    public Github(String codeOrUrl, boolean useProxy, String proxyPing) {
        this.useProxy = useProxy;
        code = new StringBuffer();
        if (codeOrUrl.contains("github.com/")) {
            gson = new Gson();
            if (useProxy) {
                myProxy = new MyProxy(gson, proxyPing);
                myProxy.getNewProxy();
            }
            recursSearchGit(parseUrl(codeOrUrl));
        } else {
            addInBuff("", codeOrUrl);
        }
    }

    public static String parseUrl(String url) {
        return url.replace("github.com", "api.github.com/repos")
                    .replace("/tree/master/", "/contents/")
                    .replace("/blob/master/", "/contents/");
    }

    private void addInBuff(String path, String codeBuff) {
        if (startFlag) {
            code.append(divStart);
            if (headerFlag) {
                code.append("\t<h2 class=\"h2\">Код</h2>");
            }
            headerFlag = false;
        }

        code.append(codeStart);

        if (!path.equals("")) {
            code.append("\t<strong>")
                    .append(path)
                    .append("</strong>\n\n");
        }
        String[] codeStrs = codeBuff.split("\n");

        int startWith = 0;

        if (codeStrs.length > maxHeight) {
            flagNewPage = true;
            endFlag = true;
            for (startWith = 0; startWith < maxHeight; startWith++) {
                code.append(codeStrs[startWith]);
            }
            code.append(divEnd);
        } else {
            startFlag = true;
        }

        if (flagNewPage) {
            code.append(divStart);
            code.append(codeStart);
            flagNewPage = false;
        }

        for (; startWith < codeStrs.length; startWith++) {
            code.append(codeStrs[startWith]);
        }

        if (endFlag) {
            code.append(divEnd);
        }


    }

    public void recursSearchGit(String url) {
        String json = useProxy ? UtilsForIO.readStringFromURL(url, myProxy, myProxy.getProxy()) :
                                    UtilsForIO.readStringFromURL(url);
        try {
            JsonFields[] roots = gson.fromJson(json, JsonFields[].class);
            for (JsonFields root : roots) {
                if (root.type.equals("dir")) {
                    recursSearchGit(root.url);
                } else if (root.download_url != null) {
                    System.out.println("download " + root.path);
                    addInBuff(root.path, useProxy ? UtilsForIO.readStringFromURL(root.download_url, myProxy, myProxy.getProxy()) :
                                                    UtilsForIO.readStringFromURL(root.download_url));
                }
            }
        } catch (JsonParseException e) {
            JsonFields root = gson.fromJson(json, JsonFields.class);
            addInBuff(root.path, useProxy ? UtilsForIO.readStringFromURL(root.download_url, myProxy, myProxy.getProxy()) :
                                            UtilsForIO.readStringFromURL(root.download_url));
        }
    }

    public String getCode() {
        return code.toString();
    }
}
