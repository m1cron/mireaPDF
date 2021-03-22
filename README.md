# Report generator in PDF and DOCX format from GitHub

### `Installing`

```
$ git clone https://github.com/m1cron/mireaPDF
$ cd mireaPDF
$ gradle bootJar
$ cp build/libs/*.jar .

if OS.UNIX
    install the Chrome Driver;
or OS.WINDOWS
    download latest Chrome Driver;
    put ChromeDriver along with the .jar;
```
[ChromeDriver](https://chromedriver.storage.googleapis.com/index.html)

### `Usage`
```
$ java -jar MIREA-PDF-*.jar  [or double click]
```

### `Technology Stack`
```
Java 11
Spring Boot 2
Gradle
Github API Rest Teamplate with proxy
Thymeleaf
Selenium with Chrome Driver
Logback (SLF4J)
Java Swing
jUnit
```


##### `Best free API's for get proxy lists:`
```https://www.proxyscan.io/api``` <br/>
```https://www.proxy-list.download/api/v1``` <br/>
```https://proxyscrape.com/api-documentation``` <br/>
```https://ipstack.com/product``` <br/>
```https://best-proxies.ru/api/``` <br/>
```https://github.com/fate0/proxylist``` <br/>
