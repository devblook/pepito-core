package team.devblook.pepitocore.plugin.module.warps.binding;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.bukkit.plugin.Plugin;
import team.devblook.pepitocore.api.datastore.TDatastore;
import team.devblook.pepitocore.api.datastore.TJsonDatastore;
import team.devblook.pepitocore.plugin.module.warps.model.Warp;

import java.io.File;

public class WarpBindingModule extends AbstractModule {


    @Provides
    @Singleton
    public  TDatastore<Warp> provideWarpDatastore(final Plugin plugin) {
        final File dataFolder = new File(plugin.getDataFolder(), "warps/user-data");
        return new TJsonDatastore<>(dataFolder, Warp.class);
    }

    @Override
    protected void configure() {

    }
}
