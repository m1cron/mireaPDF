# Report generator in PDF and DOCX format from GitHub

### `Installing`

```
step 0: install the Chrome Driver and put it in /usr/bin    <--- for Unix systems
$ git clone https://github.com/m1cron/mireaPDF
$ cd mireaPDF
$ gradlew fatJar
```

### `Usage`

```
$ cd build/libs/
$ java -jar MIREA-PDF-*.jar 
```

### `Technology Stack`
```
Java 11
Spring Boot 2
Gradle
Github API (GSON)
Selenium API with Chrome Driver
FreeMaker
HTML + CSS
Java Swing
jUnit 4
Apache Commons-IO
```


##### `Best free API's for get proxy lists:`
```https://www.proxyscan.io/api``` <br/>
```https://www.proxy-list.download/api/v1``` <br/>
```https://proxyscrape.com/api-documentation``` <br/>
```https://ipstack.com/product``` <br/>
