package uz.sonic.telegrambots.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import uz.sonic.telegrambots.annotations.AutoSend;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class UpdateScope implements Scope, ApplicationContextAware {

    private final Map<String, Map<String, Object>> scopedBeans = new ConcurrentHashMap<>();
    private final Map<String, Map<String, Runnable>> destructionCallbacks = new ConcurrentHashMap<>();
    private ApplicationContext applicationContext;


    @NotNull
    @Override
    public Object get(@NotNull String name, @NotNull ObjectFactory<?> objectFactory) {
        var updateId = UpdateContextHolder.getUpdateId();
        scopedBeans.putIfAbsent(updateId, new ConcurrentHashMap<>());
        var beans = scopedBeans.get(updateId);
        return beans.computeIfAbsent(name, k -> {
            var bean = objectFactory.getObject();

            if (bean.getClass().isAnnotationPresent(AutoSend.class)) {
                var autoSend = bean.getClass().getAnnotation(AutoSend.class);
                Class<? extends LongPollingBot> botClass = autoSend.value();
                LongPollingBot botInstance = applicationContext.getBean(botClass);

                registerDestructionCallback(name, () -> {
                    try {
                        var executeMethod = botClass.getMethod("execute", bean.getClass());
                        executeMethod.invoke(botInstance, bean);
                    } catch (Exception e) {
                        log.error("Auto-send failed for {}", bean.getClass(), e);
                    }
                });
            }
            return bean;
        });
    }

    @Override
    public Object remove(@NotNull String name) {
        var updateId = UpdateContextHolder.getUpdateId();
        var beans = scopedBeans.get(updateId);
        var result = beans != null ? beans.remove(name) : null;

        var callbacks = destructionCallbacks.get(updateId);
        if (callbacks != null) {
            callbacks.remove(name);
        }

        return result;
    }

    @Override
    public void registerDestructionCallback(@NotNull String name, @NotNull Runnable callback) {
        var updateId = UpdateContextHolder.getUpdateId();
        destructionCallbacks
                .computeIfAbsent(updateId, k -> new ConcurrentHashMap<>())
                .put(name, callback);
    }

    @Override
    public Object resolveContextualObject(@NotNull String key) {
        return null;
    }

    @Override
    public String getConversationId() {
        return UpdateContextHolder.getUpdateId();
    }

    public void clear(String updateId) {
        var callbacks = destructionCallbacks.remove(updateId);
        if (callbacks != null) {
            callbacks.values().forEach(Runnable::run);
        }
        scopedBeans.remove(updateId);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}