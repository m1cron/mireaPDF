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
    private boolean flag0 = true;
    private final boolean useProxy;

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
        code.append("<div class=\"page\">\n\t<div class=\"content\">\n\t");

        if (flag0) {
            code.append("\t<h2 class=\"h2\">Код</h2>");
        }

        code.append("\n\t\t<pre class=\"code\">\n");

        if (!path.equals("")) {
            code.append("<strong>")
                    .append(path)
                    .append("</strong>\n\n");
        }

        code.append(codeBuff)
                .append("\n\t\t</pre>\n\t</div>\n</div>");

        flag0 = false;
        System.out.printf("github len -> %d\t total len -> %d\n", codeBuff.length(), code.length());
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
