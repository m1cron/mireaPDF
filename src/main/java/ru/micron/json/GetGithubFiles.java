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

public class GetGithubFiles extends UtilsForIO {
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
        String json = UtilsForIO.readStringFromURL(url);
        try {
            JsonFields[] roots = gson.fromJson(json, JsonFields[].class);
            for (JsonFields root : roots) {
                if (root.type.equals("dir")) {
                    recursSearchGit(root.url);
                } else if (root.download_url != null) {
                    addInBuff(root.path, UtilsForIO.readStringFromURL(root.download_url));
                }
            }
        } catch (JsonParseException e) {
            JsonFields root = gson.fromJson(json, JsonFields.class);
            addInBuff(root.path, UtilsForIO.readStringFromURL(root.download_url));
        }
    }

    public String getCode() {
        return code.toString();
    }
}
