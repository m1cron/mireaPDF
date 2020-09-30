import ru.micron.json.GetGithubFiles;

public class GetGithubFilesTest {
    public static void main(String[] args) {
        GetGithubFiles gh = new GetGithubFiles();
        gh.recursSearchGit("https://api.github.com/repos/m1cron/java_rtu/contents/src/ru/micron/task6");
        System.out.println("OK");
        System.out.println(gh.getCode());
    }
}