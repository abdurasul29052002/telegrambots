package uz.sonic.telegrambots.messages;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import uz.sonic.telegrambots.annotations.AutoSend;
import uz.sonic.telegrambots.component.AbstractTelegramBot;

@AutoSend(AbstractTelegramBot.class)
public class AutoSendMessage extends SendMessage {
}
