import ru.micron.Github;

public class GithubTest {
    public static void main(String[] args) {
        String url = "https://github.com/m1cron/java_rtu/tree/master/src/ru/micron/task1";
        new Github(url, false, "100").getCodeArr().forEach(System.out::println);
    }
}