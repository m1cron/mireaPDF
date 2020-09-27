import ru.micron.MakeHtml;

public class MakeHtmlTest {
    public static void main(String[] args) {
        String url = "https://github.com/m1cron/java_rtu/blob/master/src/ru/micron/task3/book/Book.java";
        System.out.println(MakeHtml.parseUrl(url));
    }
}
