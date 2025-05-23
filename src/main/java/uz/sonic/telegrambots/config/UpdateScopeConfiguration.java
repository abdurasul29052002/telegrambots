package uz.sonic.telegrambots.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import uz.sonic.telegrambots.component.UpdateContextHolder;
import uz.sonic.telegrambots.messages.AutoSendMessage;

@Configuration
public class UpdateScopeConfiguration {
    @Bean
    @Scope(value = "update", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public AutoSendMessage sendMessage() {
        String chatId = UpdateContextHolder.getChatId();
        var autoSendMessage = new AutoSendMessage();
        autoSendMessage.setChatId(chatId);
        return autoSendMessage;
    }

}
