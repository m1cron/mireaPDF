package ru.micron.json;

import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

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

public class GetGithubFiles {
    private final StringBuffer code;
    private final Gson gson;

    public GetGithubFiles() {
        code = new StringBuffer();
        gson = new Gson();
    }

    public void recursSearchGit(String url) { // ошибка если вставить .java
        try {
            JsonFields[] roots = gson.fromJson(IOUtils.toString(new URL(url), Charset.defaultCharset()), JsonFields[].class);
            for (JsonFields root : roots) {
                if (root.type.equals("dir")) {
                    recursSearchGit(root.url);
                } else if (root.download_url != null) {
                    code.append("\n\n<strong>")
                            .append(root.path)
                            .append("</strong>\n\n")
                            .append(IOUtils.toString(new URL(root.download_url), Charset.defaultCharset()));
                }
            }
        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }

    public String getCode() {
        return code.toString();
    }
}
