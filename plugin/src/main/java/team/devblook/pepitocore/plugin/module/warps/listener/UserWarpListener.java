package team.devblook.pepitocore.plugin.module.warps.listener;

import com.google.common.collect.Multimap;
import com.google.inject.Inject;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import team.devblook.pepitocore.api.datastore.TDatastore;
import team.devblook.pepitocore.plugin.module.warps.model.Warp;

import java.util.UUID;

public class UserWarpListener implements Listener {

    private @Inject TDatastore<Warp> datastore;
    private @Inject Multimap<UUID, Warp> warps;
    private @Inject Plugin plugin;

    @EventHandler
    public void onUserLoadWarps(PlayerJoinEvent event) {
        final UUID uuid = event.getPlayer().getUniqueId();
        this.datastore.values(uuid.toString()).whenComplete((owning, throwable) -> {
            if (throwable != null) {
                this.plugin.getComponentLogger().error("Failed to load warps for user {}", uuid, throwable);
                return;
            }

            owning.forEach(warp -> this.warps.put(uuid, warp));
        });
    }

    @EventHandler(ignoreCancelled = true)
    public void onUserSaveWarps(PlayerQuitEvent event) {
        final UUID uuid = event.getPlayer().getUniqueId();

        this.warps.get(uuid).forEach(warp -> this.datastore.save(uuid.toString(), warp).whenComplete((__, throwable) -> {
            if (throwable != null) {
                this.plugin.getComponentLogger().error("Failed to save warp {} for user {}", warp.id(), uuid, throwable);
            }
        }));
    }
}
