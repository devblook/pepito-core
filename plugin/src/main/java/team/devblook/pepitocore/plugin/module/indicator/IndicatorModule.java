package team.devblook.pepitocore.plugin.module.indicator;

import com.google.inject.Inject;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import team.devblook.pepitocore.api.module.CoreModule;
import team.devblook.pepitocore.plugin.module.indicator.listener.EntityDamageListener;

public class IndicatorModule implements CoreModule {

    private @Inject Plugin plugin;
    private @Inject EntityDamageListener listener;

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