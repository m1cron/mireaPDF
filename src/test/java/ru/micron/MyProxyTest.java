package ru.micron;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.Test;
import ru.micron.web.MyProxy;

public class MyProxyTest {

  @Test
  public void getNewProxy() {
    MyProxy myProxy = new MyProxy(false);
    for (int i = 0; i < 10; i++) {
      myProxy.getNewProxy();
      assertNotNull(myProxy.getProxy());
    }
  }

  @Test
  public void readStringFromURL() {
    String url = "https://api.github.com/";
    MyProxy myProxy = new MyProxy(false);
    for (int i = 0; i < 10; i++) {
      assertNotNull(myProxy.readStringFromURL(url));
    }
  }

  @Test
  public void testReadStringFromURL() {
    String url = "https://api.github.com/";
    MyProxy myProxy = new MyProxy(true);
    for (int i = 0; i < 10; i++) {
      assertNotNull(myProxy.readStringFromURL(url, myProxy.getProxy()));
    }
  }
}