package uz.sonic.telegrambots.messages;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import uz.sonic.telegrambots.annotations.AutoSend;
import uz.sonic.telegrambots.component.AbstractTelegramBot;

@AutoSend(AbstractTelegramBot.class)
@Component
public class AutoSendMessage extends SendMessage {
}
