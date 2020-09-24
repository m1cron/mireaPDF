package ru.micron.Json;

import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

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
    private ArrayList<StringBuffer> links;
    private StringBuffer code;

    public GetGithubFiles() {
        code = new StringBuffer();
    }

    public void getLinks() {
        System.out.println("Paste links! For stop entering press Ctrl + D");
        links = new ArrayList<>();
        Scanner scan = new Scanner(System.in);
        while(scan.hasNext()) {
            StringBuffer buffer = new StringBuffer(scan.nextLine());
            links.add(buffer);
        }
        scan.close();
    }

    public String fillCode() {
        try {
            for (StringBuffer link : links) {
                String out = new Scanner(new URL(link.toString()).openStream(), StandardCharsets.UTF_8).useDelimiter("\\A").next();
                code.append(out);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return code.toString();
    }

    public void printf(){
        System.out.println(code.toString());
    }


    //      https://github.com/          m1cron/java_rtu/tree/master/src/ru/micron/task1
    // -->  https://api.github.com/repos/m1cron/java_rtu/contents/src/ru/micron/task1

    //String url = "https://api.github.com/repos/m1cron/java_rtu/contents/src/ru/micron/task3";

    public void recursSearchGit(String url) {
        try {
            String buff = IOUtils.toString(new URL(url), StandardCharsets.UTF_8);

            Gson gson = new Gson();
            Json[] roots = gson.fromJson(buff, Json[].class);

            for (int i = 0; i < roots.length; i++) {
                if (roots[i].type.equals("dir")) {
                    recursSearchGit(roots[i].url);
                    //System.out.println(roots[i].name + "\trecurs is dir \t" + roots[i].url);
                } else if (roots[i].download_url != null) {
                    code.append("\n\n").append(roots[i].path).append(".java").append("\n\n").append(IOUtils.toString(new URL(roots[i].download_url), StandardCharsets.UTF_8));
                    //System.out.println(roots[i].name + "\ttry download\t\t\t" + roots[i].download_url);
                }
                //System.out.println(roots[i].toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
