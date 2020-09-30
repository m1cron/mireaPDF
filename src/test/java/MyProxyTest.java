import ru.micron.json.MyProxy;
import ru.micron.utils.UtilsForIO;

import java.net.Proxy;

public class MyProxyTest {
    public static void main(String[] args) {
        MyProxy myProxy = new MyProxy();
        Proxy proxy = myProxy.getProxy();
        System.out.println(UtilsForIO.readStringFromURL("https://www.proxyscan.io/api/proxy?format=json", proxy));
        System.out.println(myProxy.getIp());
        System.out.println(myProxy.getPort());
        System.out.println(myProxy.getProxyMode());
    }
}
