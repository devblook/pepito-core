package team.devblook.pepitocore.plugin.module.back;

import com.google.inject.Inject;
import me.fixeddev.commandflow.CommandManager;
import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilder;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import team.devblook.pepitocore.api.module.CoreModule;
import team.devblook.pepitocore.plugin.module.back.command.GoBackCommand;
import team.devblook.pepitocore.plugin.module.back.listener.PlayerDeathListener;

public class GoBackModule implements CoreModule {

    private @Inject Plugin plugin;
    private @Inject PlayerDeathListener listener;

    private @Inject CommandManager commandManager;
    private @Inject AnnotatedCommandTreeBuilder builder;
    private @Inject GoBackCommand command; // command is being built two times, fix that

    @Override
    public void enable() {
        Bukkit.getPluginManager().registerEvents(
                listener,
                plugin
        );

        commandManager.registerCommands(builder.fromClass(command));
    }

    @Override
    public void disable() {
        HandlerList.unregisterAll(listener);
        commandManager.unregisterCommands(builder.fromClass(command));
    }
}