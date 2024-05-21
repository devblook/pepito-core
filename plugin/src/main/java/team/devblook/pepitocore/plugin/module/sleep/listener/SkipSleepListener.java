package team.devblook.pepitocore.plugin.module.sleep.listener;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;

import java.util.concurrent.atomic.AtomicInteger;

public class SkipSleepListener implements Listener {

    private static final AtomicInteger SLEEPING = new AtomicInteger(0);

    @EventHandler
    public void onEnterBed(PlayerBedEnterEvent event) {
        if (event.getBedEnterResult() != PlayerBedEnterEvent.BedEnterResult.OK) {
            return;
        }

        int sleeping = SLEEPING.incrementAndGet();
        int needed = needed();

        if (sleeping >= needed) {
            World world = event.getBed().getWorld();
            world.setTime(1000);

            broadcast("Night skipped!");
            return;
        }

        broadcast("Sleeping (" + sleeping + "/" + needed + ").");
    }

    @EventHandler
    public void onLeaveBed(PlayerBedLeaveEvent event) {
        broadcast("Sleeping (" +  SLEEPING.decrementAndGet() + "/" + needed() + ").");
    }

    private int needed() {
        return (int) Math.ceil(0.5 * Bukkit.getOnlinePlayers().size());
    }

    private void broadcast(String message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(message);
        }
    }
}