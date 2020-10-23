package ru.micron;

import com.google.gson.Gson;
import org.junit.Test;
import ru.micron.interfaces.ReadStringFromURL;

public class MyProxyTest implements ReadStringFromURL {

    @Test
    public void MyProxyBurnTest() {
        MyProxy myProxy = new MyProxy(new Gson(), "100");
        myProxy.getNewProxy();
        for (int i = 0; i < 20; i++) {
            System.out.printf("proxy ip   -->>  %s\tport -->>  %d\tproxy mode -->>  %s\n", myProxy.getIp(), myProxy.getPort(), myProxy.getProxyMode());
            readStringFromURL("https://api.github.com/repos/m1cron/java_rtu", myProxy);
        }
    }
}