package team.devblook.pepitocore.plugin.module.event;

import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class GameEventExecutor<T extends Event> implements EventExecutor {

    private final Class<T> eventClass;
    private final Consumer<T> action;

    public GameEventExecutor(Class<T> eventClass, Consumer<T> action) {
        this.eventClass = eventClass;
        this.action = action;
    }

    @Override
    public void execute(@NotNull Listener listener, @NotNull Event event) {
        if (!eventClass.isInstance(event)) {
            return;
        }

        action.accept(eventClass.cast(event));
    }
}