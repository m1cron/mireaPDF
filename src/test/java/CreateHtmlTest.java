import ru.micron.CreateHtml;

public class CreateHtmlTest {
    static private final String gihubApiUrl = "https://github.com/m1cron/java_rtu/tree/master/src/ru/micron/task1";

    public static void main(String[] args) {
        CreateHtml.parsLink(gihubApiUrl);
    }
}
