package team.devblook.pepitocore.plugin.module.warps.binding;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import org.bukkit.plugin.Plugin;
import team.devblook.pepitocore.api.datastore.TDatastore;
import team.devblook.pepitocore.api.datastore.TJsonDatastore;
import team.devblook.pepitocore.plugin.module.warps.model.Warp;

import java.util.UUID;

public class WarpBindingModule extends AbstractModule {

    private final Plugin plugin;

    public WarpBindingModule(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    protected void configure() {
        bind(new TypeLiteral<Multimap<UUID, Warp>>() {
        }).toInstance(MultimapBuilder.hashKeys().hashSetValues().build());
        bind(new TypeLiteral<TDatastore<Warp>>() {
        }).toInstance(new TJsonDatastore<>(plugin.getDataFolder(), Warp.class));
    }
}
