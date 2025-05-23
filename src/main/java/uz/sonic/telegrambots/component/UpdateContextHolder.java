package uz.sonic.telegrambots.component;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

public class UpdateContextHolder {
    private static final ThreadLocal<Map<String, Object>> context = new ThreadLocal<>();

    public static void set(Update update) {
        Map<String, Object> contextData = new HashMap<>();
        contextData.put("updateId", update.getUpdateId());
        contextData.put("chatId", extractChatId(update));
        context.set(contextData);
    }

    public static String getChatId() {
        Map<String, Object> contextData = context.get();
        if (contextData != null) {
            return contextData.get("chatId").toString();
        }
        return "chatId";
    }

    public static String getUpdateId() {
        Map<String, Object> contextData = context.get();
        if (contextData != null) {
            return contextData.get("updateId").toString();
        }
        return "updateId";
    }

    public static void clear() {
        context.remove();
    }

    private static Long extractChatId(Update update) {
        if (update.hasMessage()) {
            return update.getMessage().getChatId();
        } else if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getMessage().getChatId();
        }
        throw new IllegalStateException("No chatId found in update");
    }
}