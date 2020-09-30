import ru.micron.json.Github;

public class GithubTest {
    public static void main(String[] args) {
        String url = "https://api.github.com/repos/m1cron/java_rtu/contents/src/ru/micron/task6";

        Github gh = new Github();
        System.out.println(gh.getCode(url));
    }
}