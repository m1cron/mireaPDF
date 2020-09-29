import ru.micron.json.GetGithubFiles;

public class GetGithubFilesTest {
    public static void main(String[] args) {
        GetGithubFiles gh = new GetGithubFiles("103.216.82.18", (short) 6667, false);
        gh.recursSearchGit("https://api.github.com/repos/m1cron/java_rtu/contents/src/ru/micron/task2");
        System.out.println(gh.getCode());
    }
}