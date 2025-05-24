# TelegramBots Starter 
![Version](https://img.shields.io/badge/version-0.0.2-blue.svg) 
![License](https://img.shields.io/badge/license-apache2.0-green.svg)

**TelegramBots Starter** is a Spring Boot starter library designed to simplify the development of Telegram bot applications.

Instead of manually creating message objects, copying `chatId`s, and managing message dispatch logic, this library allows you to focus solely on the core logic of your bot.

---

## ✨ Features

- Easy-to-use API
- Faster development of Telegram bots with Spring Boot
- Automatic management of `Message` beans
- Automatic extraction of data from `Update` objects
- Automatic message sending after logic execution
- Built-in support for virtual threads (Java 21+ required)

---

## 📦 Installation

> **Note:** This library requires the [telegrambots](https://github.com/rubenlagus/TelegramBots) dependency.

First, add the Telegram Bots dependency:

```xml
<dependency>
    <groupId>org.telegram</groupId>
    <artifactId>telegrambots</artifactId>
    <version>6.9.7.0</version>
</dependency>
```

Then, add this starter library:
```xml
<dependency>
    <groupId>uz.sonic</groupId>
    <artifactId>spring-boot-starter-telegrambots</artifactId>
    <version>0.0.2</version>
</dependency>
```


## **Usage**
### 1. Configure Your Bot
Add the following properties to your `application.yml` (or `application.properties`):

```yml
telegram:
  bot:
    username: ${BOT_USERNAME}
    token: ${BOT_TOKEN}
```

### 2. Create Your Telegram Bot
Extend `AbstractTelegramBot` and annotate the class with `@Component`:

```java
@Component
public class MyBot extends AbstractTelegramBot {

    protected MyBot(DefaultBotOptions options, TelegramBotProperties properties, BotService service) {
        super(options, properties, service);
    }
}
```

### 3. Implement Your Bot Logic
Create a class that implements `BotService` and annotate it with `@Component`:

```java
@Component
@Slf4j
public class MyBotService implements BotService {

    @Override
    public void onUpdateReceived(Update update) {
        log.info("Received update: {}", update.getUpdateId());
    }
}
```

### 4. Automatically Send Messages
You can inject `AutoSendMessage` (which extends `SendMessage`) and use it directly:

```java
@Component
@Slf4j
public class MyBotService implements BotService {

    private final AutoSendMessage sendMessage;

    public MyBotService(AutoSendMessage sendMessage) {
        this.sendMessage = sendMessage;
    }

    @Override
    public void onUpdateReceived(Update update) {
        sendMessage.setText("Hello, this is a test message from MyBotService!");
        log.info("Received update: {}", update.getUpdateId());
    }
}
```
> This library will automatically send the message when `AutoSendMessage` is configured properly.

## 🔧 Requirements

Java 21 or higher (required for virtual thread support)

## 📂 Source Code
Find the full source code on GitHub: [telegrambots-sample](https://github.com/abdurasul29052002/telegrambots-sample)

## 🤝 Contributing

Pull requests are welcome! For major changes, please open an issue first to discuss what you would like to change.

See `CONTRIBUTING.md` for details.

## 📄 License

This project is licensed under the Apache 2.0 License – see the [LICENSE](LICENSE) file for details.
