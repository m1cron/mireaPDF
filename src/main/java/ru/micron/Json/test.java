package ru.micron.Json;

public class test {
    public static void main(String[] args) {
        GetGithubFiles gh = new GetGithubFiles();
        gh.recursSearchGit("https://api.github.com/repos/m1cron/java_rtu/contents/src/ru/micron/task2");
        gh.printCode();
    }
}
