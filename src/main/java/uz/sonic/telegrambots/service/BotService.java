package uz.sonic.telegrambots.service;

import org.telegram.telegrambots.meta.api.objects.Update;

@FunctionalInterface
public interface BotService {
    void onUpdateReceived(Update update);
}
