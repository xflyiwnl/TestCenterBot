<h1 align="center">Это <a target="_blank">TestCenterBot</a> 
<img src="https://github.com/blackcater/blackcater/raw/main/images/Hi.gif" height="32"/></h1>
<h3 align="center">Телеграм бот, парсер свободных дней для Единого Национального Центра</h3>

<h3>Настройка</h3>
В классе `LongPoll` указываем тэг вашего телеграм бота, ключ, а также айди беседы, в котором находится бот (у бота должны быть админ права).
Затем, рядом с джарником создаём `auth-key.txt` и указываем в нём хеадер `Authentication` (Начинается на `Bearer ...`, можно получить с помощью `F12 -> Network` на сайте тестцентра)

<h3>Компиляция и запуск</h3>

Компилируем команой:
```
mvn clean package
```

Запуск:
```
java -jar TestCenterBot-1.0-SNAPSHOT.jar -Xmx500M
```
