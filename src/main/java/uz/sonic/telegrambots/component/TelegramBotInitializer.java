package uz.sonic.telegrambots.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@RequiredArgsConstructor
@Slf4j
public class TelegramBotInitializer {
    private final AbstractTelegramBot telegramBot;

    @EventListener(ApplicationReadyEvent.class)
    public void init(){
        try {
            var telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(telegramBot);
            log.info("Bot {} registered successfully", telegramBot.getBotUsername());
        } catch (Exception e) {
            log.warn("Could not register bot: {}", e.getMessage());
        }
    }
}
