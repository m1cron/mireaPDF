import com.google.gson.Gson;
import ru.micron.json.MyProxy;
import ru.micron.utils.UtilsForIO;

public class MyProxyTest {
    public static void main(String[] args) {
        MyProxy myProxy = new MyProxy(new Gson());
        myProxy.getNewProxy();
        for(int i = 0; i < 1; i++) {
            System.out.printf("proxy ip   -->>  %s\tport -->>  %d\tproxy mode -->>  %s\t", myProxy.getIp(), myProxy.getPort(), myProxy.getProxyMode());
            System.out.println(UtilsForIO.readStringFromURL("https://api.github.com/repos/m1cron/java_rtu/contents/src/ru/micron/task2", myProxy));
            System.out.printf("proxy ip   -->>  %s\tport -->>  %d\tproxy mode -->>  %s\t", myProxy.getIp(), myProxy.getPort(), myProxy.getProxyMode());
            System.out.println(UtilsForIO.readStringFromURL("https://api.github.com/repos/m1cron/java_rtu/contents/src/ru/micron/task4", myProxy));
            System.out.printf("proxy ip   -->>  %s\tport -->>  %d\tproxy mode -->>  %s\t", myProxy.getIp(), myProxy.getPort(), myProxy.getProxyMode());
            System.out.println(UtilsForIO.readStringFromURL("https://api.github.com/repos/m1cron/java_rtu/contents/src/ru/micron/task1", myProxy));
            System.out.printf("proxy ip   -->>  %s\tport -->>  %d\tproxy mode -->>  %s\t", myProxy.getIp(), myProxy.getPort(), myProxy.getProxyMode());
            System.out.println(UtilsForIO.readStringFromURL("https://api.github.com/repos/m1cron/java_rtu/contents/src/ru/micron/task6", myProxy));
        }
    }
}
