package uz.sonic.telegrambots.annotations;

import org.telegram.telegrambots.meta.generics.LongPollingBot;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoSend {
    Class<? extends LongPollingBot> value();
}
