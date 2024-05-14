package team.devblook.pepitocore.plugin.module.broadcast.listener;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class UserBroadcastListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onUserJoinBroadcast(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        final Component joinMessage = Component.text()
                .append(Component.text("✓ ", TextColor.color(0x43AE33)).decorate(TextDecoration.BOLD))
                .append(Component.text(player.getName(), TextColor.color(0xDFFFEF)))
                .append(Component.text(" ingresó al servidor.", TextColor.color(0xA3C18F)))
                .build();

        event.joinMessage(joinMessage);
    }

    @EventHandler(ignoreCancelled = true)
    public void onUserQuitBroadcast(final PlayerQuitEvent event) {
        final Player player = event.getPlayer();

        final Component quitMessage = Component.text()
                .append(Component.text("✕ ", TextColor.color(0xAE1F20)).decorate(TextDecoration.BOLD))
                .append(Component.text(player.getName(), TextColor.color(0xDFFFEF)))
                .append(Component.text(" salió al servidor.", TextColor.color(0xA3C18F)))
                .build();

        event.quitMessage(quitMessage);
    }
}
