package team.devblook.pepitocore.plugin.module.sleep.listener;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
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
            world.setThundering(false);
            world.setStorm(false);

            broadcast(
                    Component.text("¡Buenos días!", TextColor.fromHexString("#43ae33"))
                            .appendNewline()
                            .append(Component.text("La noche ha sido saltada.", TextColor.fromHexString("#E4FFE5")))
            );
            return;
        }
        broadcast(
                Component.text("Durmiendo: ", TextColor.fromHexString("#526aa9"))
                        .append(Component.text(
                                "(" + sleeping + "/" + needed + ")", TextColor.fromHexString("#E4FFE5")
                        ))
        );
    }

    @EventHandler
    public void onLeaveBed(PlayerBedLeaveEvent event) {
        int sleeping = 0;
        if (SLEEPING.get() > 0) { // imagine the module is enabled while someone is already sleeping
            sleeping = SLEEPING.decrementAndGet();
        }

        // checks if time is between 0-12000
        if (event.getBed().getWorld().isDayTime()) {
            return;
        }

        broadcast(
                Component.text("Durmiendo: ", TextColor.fromHexString("#526aa9"))
                        .append(Component.text(
                                "(" + sleeping + "/" + needed() + ")", TextColor.fromHexString("#E4FFE5")
                        ))
        );
    }

    private int needed() {
        return (int) Math.ceil(0.5 * Bukkit.getOnlinePlayers().size());
    }

    private void broadcast(final Component message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(message);
        }
    }
}