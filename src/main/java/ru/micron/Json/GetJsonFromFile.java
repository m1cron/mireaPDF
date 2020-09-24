package ru.micron.Json;

import com.google.common.io.Resources;
import com.google.gson.Gson;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Map;

class Practice {
    public PracticeFields[] practice;

    @Override
    public String toString() {
        return "Practice{" +
                "practice=" + Arrays.toString(practice) +
                '}';
    }
}

class PracticeFields {
    public String target, theory, conclusion, literature;

    @Override
    public String toString() {
        return "JsonFile{" +
                "target='" + target + '\'' +
                ", theory='" + theory + '\'' +
                ", conclusion='" + conclusion + '\'' +
                ", literature='" + literature + '\'' +
                '}';
    }
}

public class GetJsonFromFile {
    private static final String fileJson = "theory.json";

    public static String readFile(final String fileName, Charset charset) throws IOException {
        return Resources.toString(Resources.getResource(fileName), charset);
    }

    public static void parseJson(Map<String, String> map, int pracNum) {
        try {
            Gson gson = new Gson();
            Practice groups = gson.fromJson(readFile(fileJson, Charset.defaultCharset()), Practice.class);

            map.put("prac_number", Integer.toString(pracNum));
            map.put("target_content", groups.practice[pracNum].target);
            map.put("teor_content", groups.practice[pracNum].theory);
            map.put("conclusion_content", groups.practice[pracNum].conclusion);
            map.put("literature_content", groups.practice[pracNum].literature);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
