package ru.micron.json;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
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

    private void addInBuff(String path, String codeBuff) {
        code.append("\n\n<strong>")
                .append(path)
                .append("</strong>\n\n")
                .append(codeBuff);
    }

    public void recursSearchGit(String url) {
        try {
            JsonFields[] roots = gson.fromJson(IOUtils.toString(new URL(url), Charset.defaultCharset()), JsonFields[].class);               // <<---------
            for (JsonFields root : roots) {
                if (root.type.equals("dir")) {
                    recursSearchGit(root.url);
                } else if (root.download_url != null) {
                    addInBuff(root.path, IOUtils.toString(new URL(root.download_url), Charset.defaultCharset()));                           // <<---------
                }
            }
        } catch (JsonParseException e) {
            try {
                JsonFields root = gson.fromJson(IOUtils.toString(new URL(url), Charset.defaultCharset()), JsonFields.class);
                addInBuff(root.path, IOUtils.toString(new URL(root.download_url), Charset.defaultCharset()));                               // <<---------
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getCode() {
        return code.toString();
    }
}
