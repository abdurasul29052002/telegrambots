package uz.sonic.telegrambots.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import uz.sonic.telegrambots.component.TelegramBotProperties;
import uz.sonic.telegrambots.component.UpdateContextHolder;
import uz.sonic.telegrambots.messages.AutoSendMessage;

@Configuration
@ConditionalOnProperty(
        prefix = "telegram.bot",
        name = "enabled",
        havingValue = "true",
        matchIfMissing = true
)
@EnableConfigurationProperties(TelegramBotProperties.class)
public class TelegramBotAutoConfiguration {

    @Bean
    public DefaultBotOptions defaultBotOptions() {
        return new DefaultBotOptions();
    }

    @Bean
    @Scope(value = "update", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public AutoSendMessage sendMessage() {
        String chatId = UpdateContextHolder.getChatId();
        var autoSendMessage = new AutoSendMessage();
        autoSendMessage.setChatId(chatId);
        return autoSendMessage;
    }
}
