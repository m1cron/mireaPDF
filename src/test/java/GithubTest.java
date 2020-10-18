import ru.micron.json.Github;

public class GithubTest {
    public static void main(String[] args) {
        String url = "https://github.com/m1cron/java_rtu/tree/master/src/ru/micron/task1";
        System.out.println(new Github(url, false, "100").getCode());

        //System.out.println(new Github("72636587234759236495eijhghdfsghkjsdlkjghdslgkjhlkjh", false, "100").getCode());
    }
}