package team.devblook.pepitocore.plugin.module.warps;

import com.google.common.collect.Multimap;
import com.google.inject.Inject;
import me.fixeddev.commandflow.CommandManager;
import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilder;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import team.devblook.pepitocore.api.datastore.TJsonDatastore;
import team.devblook.pepitocore.api.module.CoreModule;
import team.devblook.pepitocore.plugin.module.warps.command.WarpCommand;
import team.devblook.pepitocore.plugin.module.warps.listener.UserWarpListener;
import team.devblook.pepitocore.plugin.module.warps.model.Warp;

import java.util.UUID;

public class WarpsModule implements CoreModule {

    private @Inject CommandManager manager;
    private @Inject AnnotatedCommandTreeBuilder treeBuilder;

    private @Inject UserWarpListener listener;
    private @Inject Plugin plugin;
    private @Inject WarpCommand command;

    private @Inject TJsonDatastore<Warp> datastore;
    private @Inject Multimap<UUID, Warp> warps;

    @Override
    public void enable() {
        manager.registerCommands(treeBuilder.fromClass(command));
        Bukkit.getPluginManager().registerEvents(
                listener,
                plugin
        );
    }

    @Override
    public void disable() {
        manager.unregisterCommands(treeBuilder.fromClass(command));
        HandlerList.unregisterAll(listener);

        for (Warp warp : warps.values()) {
            datastore.save(
                    warp.owner(),
                    warp
            );
        }
    }
}