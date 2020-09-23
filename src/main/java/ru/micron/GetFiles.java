package ru.micron;

import java.io.*;
import java.net.URL;

import java.util.Scanner;

public class GetFiles {
    public String code;
    public void copyURLToFile(URL url, File file) {

        try {
            InputStream input = url.openStream();
            if (file.exists()) {
                if (file.isDirectory())
                    throw new IOException("File '" + file + "' is a directory");

                if (!file.canWrite())
                    throw new IOException("File '" + file + "' cannot be written");
            } else {
                File parent = file.getParentFile();
                if ((parent != null) && (!parent.exists()) && (!parent.mkdirs())) {
                    throw new IOException("File '" + file + "' could not be created");
                }
            }

            FileOutputStream output = new FileOutputStream(file);

            byte[] buffer = new byte[4096];
            int n = 0;
            while (-1 != (n = input.read(buffer))) {
                output.write(buffer, 0, n);
            }
            System.out.println(code);
            input.close();
            output.close();

            System.out.println("File '" + file + "' downloaded successfully!");
        }
        catch(IOException ioEx) {
            ioEx.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {

        //URL pointing to the file
        String out = new Scanner(new URL("https://raw.githubusercontent.com/m1cron/java_rtu/master/src/ru/micron/task2/Dog.java").openStream(), "UTF-8").useDelimiter("\\A").next();
        System.out.println(out);
        String sUrl = "https://raw.githubusercontent.com/m1cron/java_rtu/master/src/ru/micron/task2/Dog.java";

        URL url = new URL(sUrl);

        //File where to be downloaded
        File file = new File("C:/sf.txt");

        GetFiles files = new GetFiles();

        files.copyURLToFile(url, file);
    }
}
