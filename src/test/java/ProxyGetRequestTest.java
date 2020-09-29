import java.io.IOException;
import java.net.*;
import java.nio.charset.Charset;
import java.util.Scanner;

public class ProxyGetRequestTest {
    private static final String api = "https://api.github.com/repos/m1cron/java_rtu/contents/src/ru/micron/task1";


    public static String readStringFromURL(String requestURL) {
        try (Scanner scanner = new Scanner(new URL(requestURL).openStream(),
                Charset.defaultCharset())
                .useDelimiter("\\A")) {
            return scanner.hasNext() ? scanner.next() : "";
        } catch (IOException e) {
            e.printStackTrace();
            return "null";
        }
    }

    public static void main(String[] args) throws IOException {
        //185.114.137.14
        while(true) {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("185.114.137.14", 12457));
            URLConnection conn = new URL(api).openConnection(proxy);

            Scanner scanner = new Scanner(conn.getInputStream(), Charset.defaultCharset()).useDelimiter("\\A");
            System.out.println(scanner.next());
        }


        //System.out.println(readStringFromURL(api));

    }
}
