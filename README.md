# MIREA Java генератор отчётов с GitHub

### `Установка`

```
$ git clone https://github.com/m1cron/mireaPDF
$ cd mireaPDF
$ mvn package
$ java -jar mireaPDF*.jar --help
```

### `Использование`

```
$ java -jar mireaPDF*.jar -json "Student name" "Group ID" "Teacher name"    <--- [only 1 time]
$ java -jar mireaPDF*.jar -make "Work number" "Url to the Github folder"
```

#### Фикс лист
- [ ] Учитывать максимум контента по ширине
- [ ] Учитывать максимум контента по высоте
- [ ] Сделать скачивание из корневой директории GitHub
- [ ] Добавить конвертацию в DOCX
- [ ] Добавить выбор прокси и пинга
- [ ] Добавить выбор драйвера (chrome/firefox/etc)
- [ ] OPTIONAL: Добавить загрузку из интернета (chrome/firefox/etc)

#### `Best free API's for get proxy lists:`
##### ```https://www.proxyscan.io/api```
##### ```https://www.proxy-list.download/api/v1```
##### ```https://proxyscrape.com/api-documentation```
##### ```https://ipstack.com/product```