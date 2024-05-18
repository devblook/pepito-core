package team.devblook.pepitocore.plugin.module.event;

import com.google.inject.Inject;
import net.kyori.adventure.bossbar.BossBar;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import team.devblook.pepitocore.plugin.configuration.BukkitConfiguration;
import team.devblook.pepitocore.plugin.module.event.model.GameEvent;
import team.devblook.pepitocore.plugin.module.event.task.BossBarTask;
import team.devblook.pepitocore.plugin.module.event.type.*;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class EventPool implements Runnable {

    private static final Random RANDOM = new Random();
    private static final List<GameEvent> EVENTS = List.of(
            new PoisonGameEvent(),
            new NoMoveGameEvent(),
            new SuperJumpEvent(),
            new OneHearthEvent(),
            new IncreasedMobDamageEvent()
    );

    private @Inject Plugin plugin;
    private @Inject BukkitConfiguration configuration;

    private GameEvent current;

    private int task;
    private int bossBarTask;


    @Override
    public void run() {
        if (current == null) {
            current = EVENTS.get(RANDOM.nextInt(EVENTS.size()));
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.showTitle(current.title());
            player.playSound(current.sound());
            player.sendMessage(current.description());

            BossBar bossBar = current.bossBar();
            if (bossBar != null) {
                player.showBossBar(bossBar);
            }
        }

        // improve this shit fr
        task = Bukkit.getScheduler().runTaskLater(
                plugin,
                () -> {
                    current.begin();

                    bossBarTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(
                            plugin,
                            new BossBarTask(current),
                            0L,
                            20L
                    );

                    for (Map.Entry<Class<? extends Event>, GameEventExecutor<? extends Event>> entry : current.events().entrySet()) {
                        Bukkit.getPluginManager().registerEvent(
                                entry.getKey(),
                                current,
                                EventPriority.NORMAL,
                                entry.getValue(),
                                plugin
                        );
                    }

                    task = Bukkit.getScheduler().scheduleSyncDelayedTask(
                            plugin,
                            () -> {
                                current.end();

                                Bukkit.getScheduler().cancelTask(bossBarTask);
                                HandlerList.unregisterAll(current);

                                current = null;

                                int interval = configuration.get().getInt("events.interval", 10);
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
                            current.duration() * 20L * 60L
                    );
                },
                5 * 20
        ).getTaskId();
    }

    public void stop() {
        Bukkit.getScheduler().cancelTask(task);
        Bukkit.getScheduler().cancelTask(bossBarTask);
    }
}