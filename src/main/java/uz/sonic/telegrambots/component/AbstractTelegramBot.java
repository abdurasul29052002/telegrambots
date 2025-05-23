package uz.sonic.telegrambots.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.util.WebhookUtils;
import uz.sonic.telegrambots.service.BotService;

@Slf4j
public abstract class AbstractTelegramBot extends DefaultAbsSender implements LongPollingBot {
    private final String botUsername;
    protected final BotService service;
    private UpdateScopedExecutor executor;

    protected AbstractTelegramBot(DefaultBotOptions options, TelegramBotProperties properties, BotService service) {
        super(options, properties.getToken());
        this.botUsername = properties.getUsername();
        this.service = service;
    }

    @Override
    public void onUpdateReceived(Update update) {
        executor.runInUpdateScope(update, () -> service.onUpdateReceived(update));
    }

    @Override
    public void clearWebhook() throws TelegramApiRequestException {
        WebhookUtils.clearWebhook(this);
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Autowired
    public void setExecutor(UpdateScopedExecutor executor) {
        this.executor = executor;
    }
}
