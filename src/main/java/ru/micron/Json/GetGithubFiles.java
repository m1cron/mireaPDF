package ru.micron.Json;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class GetGithubFiles {
    private long count;
    private ArrayList<StringBuffer> links;
    private StringBuffer code;

    public void getLinks() {
        System.out.println("Paste links! For stop entering press Ctrl + D");
        links = new ArrayList<>();
        Scanner scan = new Scanner(System.in);
        for (count = 0; scan.hasNext(); count++) {
            StringBuffer buffer = new StringBuffer(scan.nextLine());
            links.add(buffer);
        }
        scan.close();
    }

    public String fillCode() {
        try {
            code = new StringBuffer();
            for(int i = 0; i < links.size(); i++) {
                String out = new Scanner(new URL(links.get(i).toString()).openStream(), "UTF-8").useDelimiter("\\A").next();
                code.append(out);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return code.toString();
    }
}
