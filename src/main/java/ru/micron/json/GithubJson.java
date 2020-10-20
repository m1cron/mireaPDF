package ru.micron.json;

public class GithubJson {
    private String name, path, url, download_url, type;

    public GithubJson(String name, String path, String url, String download_url, String type) {
        this.name = name;
        this.path = path;
        this.url = url;
        this.download_url = download_url;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public String getUrl() {
        return url;
    }

    public String getDownload_url() {
        return download_url;
    }

    public String getType() {
        return type;
    }

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
