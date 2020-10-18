import com.google.gson.Gson;
import ru.micron.json.MyProxy;
import ru.micron.utils.UtilsForIO;

public class MyProxyTest {
    public static void main(String[] args) {
        MyProxy myProxy = new MyProxy(new Gson(), "100");
        myProxy.getNewProxy();
        for(int i = 0; i < 500; i++) {
            System.out.printf("proxy ip   -->>  %s\tport -->>  %d\tproxy mode -->>  %s", myProxy.getIp(), myProxy.getPort(), myProxy.getProxyMode());
            if (!UtilsForIO.readStringFromURL("https://github.com/m1cron/BSQ", myProxy, myProxy.getProxy()).equals("null"));
                System.out.printf("\t YES\n");
            System.out.printf("proxy ip   -->>  %s\tport -->>  %d\tproxy mode -->>  %s\n", myProxy.getIp(), myProxy.getPort(), myProxy.getProxyMode());
            UtilsForIO.readStringFromURL("https://api.github.com/repos/m1cron/java_rtu", myProxy, myProxy.getProxy());
            System.out.printf("proxy ip   -->>  %s\tport -->>  %d\tproxy mode -->>  %s\n", myProxy.getIp(), myProxy.getPort(), myProxy.getProxyMode());
            UtilsForIO.readStringFromURL("https://github.com/m1cron/ALL_DATA_STRUCTS", myProxy, myProxy.getProxy());
            System.out.printf("proxy ip   -->>  %s\tport -->>  %d\tproxy mode -->>  %s\n", myProxy.getIp(), myProxy.getPort(), myProxy.getProxyMode());
            UtilsForIO.readStringFromURL("https://github.com/m1cron/oop_labs", myProxy, myProxy.getProxy());
        }
    }
}
