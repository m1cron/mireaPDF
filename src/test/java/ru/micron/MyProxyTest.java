package ru.micron;

import org.junit.Test;
import ru.micron.interfaces.ReadStringFromURL;

public class MyProxyTest implements ReadStringFromURL {

    @Test
    public void MyProxyBurnTest() {
        MyProxy myProxy = new MyProxy("100");
        myProxy.getNewProxy();
        for (int i = 0; i < 20; i++) {
            System.out.printf("proxy\t%s\t\t%d\n", myProxy.getIp(), myProxy.getPort());
            readStringFromURL("https://api.github.com/repos/m1cron/java_rtu", myProxy);
        }
    }
}