package ru.micron.Json;

import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

class Json {
    public String name, path, url, download_url, type;
    public int size;

    @Override
    public String toString() {
        return "root{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", url='" + url + '\'' +
                ", download_url='" + download_url + '\'' +
                ", type='" + type + '\'' +
                ", size=" + size +
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
            Json[] roots = gson.fromJson(IOUtils.toString(new URL(url), Charset.defaultCharset()), Json[].class);
            for (Json root : roots) {
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
            e.printStackTrace();
        }
    }

    public void printCode(){
        System.out.println(code.toString());
    }

    public String getCode() {
        return code.toString();
    }
}
