package uz.sonic.telegrambots;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import uz.sonic.telegrambots.component.AbstractTelegramBot;
import uz.sonic.telegrambots.component.TelegramBotProperties;

@Slf4j
@Component
public class Handler extends AbstractTelegramBot {
    protected Handler(DefaultBotOptions options, TelegramBotProperties properties) {
        super(options, properties, update -> {
            log.info("Update received: {}", update);
        });
    }
}
