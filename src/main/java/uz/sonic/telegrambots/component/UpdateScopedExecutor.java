package uz.sonic.telegrambots.component;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@RequiredArgsConstructor
public class UpdateScopedExecutor {

    private final UpdateScope updateScope;
    private final Executor executor = Executors.newVirtualThreadPerTaskExecutor();


    public void runInUpdateScope(Update update, Runnable work) {
        executor.execute(() -> {
            try {
                UpdateContextHolder.set(update);
                work.run();
            } finally {
                updateScope.clear(update.getUpdateId().toString());
                UpdateContextHolder.clear();
            }
        });

    }
}