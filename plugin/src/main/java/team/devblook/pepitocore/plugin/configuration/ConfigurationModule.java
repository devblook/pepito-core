package team.devblook.pepitocore.plugin.configuration;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.bukkit.plugin.Plugin;

public class ConfigurationModule extends AbstractModule {

    @Provides
    @Singleton
    public BukkitConfiguration provideConfiguration(final Plugin plugin) {
        return new BukkitConfiguration(plugin, "config.yml");
    }
}
