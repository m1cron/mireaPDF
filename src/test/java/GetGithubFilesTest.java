import ru.micron.Json.GetGithubFiles;

public class GetGithubFilesTest {
    public static void main(String[] args) {
        GetGithubFiles gh = new GetGithubFiles();
        gh.recursSearchGit("https://api.github.com/repos/m1cron/java_rtu/contents/src/ru/micron/task2");
        System.out.println(gh.getCode());
    }
}