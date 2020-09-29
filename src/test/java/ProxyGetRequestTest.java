import java.io.IOException;
import java.net.*;
import java.nio.charset.Charset;
import java.util.Scanner;

public class ProxyGetRequestTest {
    private static final String api = "https://api.github.com/repos/m1cron/java_rtu/contents/src/ru/micron/task1";

    public static Proxy setProxy(String ip, short port, boolean socksOrNo) {
        Proxy proxy = null;
        if (socksOrNo)
            proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(ip, port));
        else
            proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port));
        return proxy;
    }


    public static void main(String[] args) throws IOException {

        System.setProperty("socksProxyVersion", "4");

        //for(int i = 0; i < 1; i++) {
        //System.out.printf("%d\t", i);
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("178.130.75.68", 8080));

        URLConnection conn = new URL(api).openConnection(proxy);

        Scanner scanner = new Scanner(conn.getInputStream(), Charset.defaultCharset()).useDelimiter("\\A");
        System.out.println(scanner.next());
        //}


    }

    //System.out.println(readStringFromURL(api));

}
