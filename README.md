# Telgrambots Starter ![Version](https://img.shields.io/badge/version-0.0.2-blue.svg) ![License](https://img.shields.io/badge/license-apache2.0-green.svg)
This library is the starter library for Spring boot applications. 

The purpose of this library to make easier building telegram bot applications using Spring boot. 
With this library you can focus on your logic of own telegram bot instead of creating message objects copying `chatId` and send them to the user manually. 

## ✨ Features
- Easy use of api
- Faster creation of telegram bot applications with Spring boot
- Automatic management of `Message` beans
- Automatic retreive data from `Upatade`
- Automatic send `Message` to the user after execution of you logic

## 📦 Installation

Using `Maven`:

This library requires the original telegram bot library therefore you need to add `telegrambots` dependency first
```xml
<dependency>
    <groupId>org.telegram</groupId>
    <artifactId>telegrambots</artifactId>
    <version>6.9.7.0</version>
</dependency>
```

Then you can add this starter library. 
```xml
<dependency>
    <groupId>uz.sonic</groupId>
    <artifactId>spring-boot-starter-telegrambots</artifactId>
    <version>0.0.2</version>
</dependency>
```


## **Usage**
You just need to add your telegram bot username and token to your `application.properties` of `application.yml`

```yml
telegram:
  bot:
    username: ${BOT_USERNAME}
    token: ${BOT_TOKEN}
```

Then you need to create your telegram bot class and extend that class from this library's `AbstractTelegramBot` class. 

`Note`: YOUR TELEGRAM BOT CLASS SHOULD BE SPRING BEAN. Don't forget to put `@Component` annotation in it.

```java
@Component
public class MyBot extends AbstractTelegramBot {

    protected MyBot(DefaultBotOptions options, TelegramBotProperties properties, BotService service) {
        super(options, properties, service);
    }
}
```

Then you need to create your bot service class which implements from our `BotService` interface and make it spring bean.

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
And that's it. You will receive your updates in your bot service class. 

In addition this is not only redirect's update to the service. This library provides automatic send message to the user. 
There are classes which extends from `SendMessage` class. You just need to inject that beans and use them like `SendMessage` object.
It will automatic send messages to the user

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

Moreover this library uses `virtual threads` for every update. Due to this you need at least 21 version of Java.

This source code can be found on GitHub [link](https://github.com/abdurasul29052002/telegrambots-sample)

## 🤝 Contributing

Pull requests are welcome! For major changes, please open an issue first to discuss what you would like to change.

See `CONTRIBUTING.md` for details.

## 📄 License

This project is licensed under the Apache 2.0 License – see the [LICENSE](LICENSE) file for details.
