package team.devblook.pepitocore.plugin.module.broadcast;

import com.google.inject.Inject;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import team.devblook.pepitocore.api.module.CoreModule;
import team.devblook.pepitocore.plugin.module.broadcast.listener.UserBroadcastListener;

public class BroadcastModule implements CoreModule {

    private @Inject Plugin plugin;
    private @Inject UserBroadcastListener userBroadcastListener;

    @Override
    public void enable() {
        Bukkit.getPluginManager().registerEvents(
                userBroadcastListener,
                plugin
        );
    }

    @Override
    public void disable() {
        HandlerList.unregisterAll(userBroadcastListener);
    }
}