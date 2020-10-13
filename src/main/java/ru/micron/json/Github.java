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
    private final Gson gson;
    private final MyProxy myProxy;
    private final String divStart = "";
    private final String divEnd = "";
    private boolean flag0 = true;

    public Github() {
        code = new StringBuffer();
        gson = new Gson();
        myProxy = new MyProxy(gson);
        myProxy.getNewProxy();
    }

    public static String parseUrl(String url) {
        return url.replace("github.com", "api.github.com/repos")
                .replace("/tree/master/", "/contents/")
                .replace("/blob/master/", "/contents/");
    }

    private void addInBuff(String path, String codeBuff) {
        code.append("<div class=\"page\">\n" +
                "        <div class=\"content\">\n");

        if (flag0)
            code.append("<h2 class=\"h2\">Код</h2>");

        code
                .append("\n\n<pre class=\"code\">\n<strong>")
                .append(path)
                .append("</strong>\n\n")
                .append(codeBuff)
                .append("\n</pre>\n")
                .append("</div>\n</div>\n\n");

        flag0 = false;
    }

    public void recursSearchGit(String url) {
        String json = UtilsForIO.readStringFromURL(url, myProxy, myProxy.getProxy());
        try {
            JsonFields[] roots = gson.fromJson(json, JsonFields[].class);
            for (JsonFields root : roots) {
                if (root.type.equals("dir")) {
                    recursSearchGit(root.url);
                } else if (root.download_url != null) {
                    System.out.println("download " + root.path);
                    addInBuff(root.path, UtilsForIO.readStringFromURL(root.download_url, myProxy, myProxy.getProxy()));
                }
            }
        } catch (JsonParseException e) {
            JsonFields root = gson.fromJson(json, JsonFields.class);
            addInBuff(root.path, UtilsForIO.readStringFromURL(root.download_url, myProxy, myProxy.getProxy()));
        }
    }

    public String getCode(String gitUrl) {
        recursSearchGit(parseUrl(gitUrl));
        return code.toString();
    }
}
