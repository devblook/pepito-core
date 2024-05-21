package team.devblook.pepitocore.plugin.module.death;

import com.google.inject.Inject;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import team.devblook.pepitocore.api.module.CoreModule;
import team.devblook.pepitocore.plugin.module.death.listener.PlayerMessageDeathListener;

public class DeathModule implements CoreModule {

    private @Inject Plugin plugin;
    private @Inject PlayerMessageDeathListener listener;

    @Override
    public void enable() {
        Bukkit.getPluginManager().registerEvents(
                listener,
                plugin
        );
    }

    @Override
    public void disable() {
        HandlerList.unregisterAll(listener);
    }
}
