package uz.sonic.telegrambots.component;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "telegram.bot")
public class TelegramBotProperties {
    private String token;
    private String username;
}
