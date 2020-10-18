import ru.micron.utils.UtilsForIO;

import java.util.ArrayList;

public class UtilsTest {
    public static void main(String[] args) {
        ArrayList<String> test = UtilsForIO.readArrayStringsFromUrl("https://raw.githubusercontent.com/m1cron/java_rtu/master/src/ru/micron/task1/Main.java");
        for(int i = 0; i < test.size(); i++)
            System.out.println(i + test.get(i));

    }
}
