package team.devblook.pepitocore.plugin.module.event;

import com.google.inject.Inject;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import team.devblook.pepitocore.plugin.module.event.model.GameEvent;
import team.devblook.pepitocore.plugin.module.event.type.NoMoveGameEvent;
import team.devblook.pepitocore.plugin.module.event.type.PoisonGameEvent;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class EventPool implements Runnable {

    private static final Random RANDOM = new Random();
    private static final List<GameEvent> EVENTS = List.of(
            new PoisonGameEvent(),
            new NoMoveGameEvent()
    );

    private @Inject Plugin plugin;

    private GameEvent current;

    private int task;


    @Override
    public void run() {
        if (current == null) {
            current = EVENTS.get(RANDOM.nextInt(EVENTS.size()));
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.showTitle(current.title());
            player.playSound(current.sound());
            player.sendMessage(current.description());
        }

        for (Map.Entry<Class<? extends Event>, GameEventExecutor<? extends Event>> entry : current.events().entrySet()) {
            Bukkit.getPluginManager().registerEvent(
                    entry.getKey(),
                    current,
                    EventPriority.NORMAL,
                    entry.getValue(),
                    plugin
            );
        }

        current.begin();

        task = Bukkit.getScheduler().scheduleSyncDelayedTask(
                plugin,
                () -> {
                    current.end();
                    HandlerList.unregisterAll(current);

                    current = null;

                    int interval = 1;
                    if (interval < 1) {
                        run();
                        return;
                    }

                    task = Bukkit.getScheduler().scheduleSyncDelayedTask(
                            plugin,
                            this,
                            interval * 20L * 60L
                    );
                },
                current.duration() * 20L * 60L // sum interval
        );
    }

    public void stop() {
        Bukkit.getScheduler().cancelTask(task);
    }
}