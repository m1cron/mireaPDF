import ru.micron.json.Github;

public class GithubTest {
    public static void main(String[] args) {
        String url = "https://api.github.com/repos/m1cron/java_rtu/contents/src/ru/micron/task1";
        System.out.println(new Github(url).getCode());

        System.out.println(new Github("72636587234759236495eijhghdfsghkjsdlkjghdslgkjhlkjh").getCode());
    }
}