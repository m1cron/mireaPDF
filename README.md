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
- [ ] сверстать макет для FreeMaker
- [x] нет обработки исключений
- [x] String строки для парсинга Json'ок получаются через костыль
- [x] не работают ссылки с .java на конце
- [ ] GitHub API кидает в бан на 30 мин если часто генерировать PDF (нужно проксирование запросов)