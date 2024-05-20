package team.devblook.pepitocore.plugin.module.restart;

import com.google.inject.Inject;
import me.fixeddev.commandflow.CommandManager;
import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilder;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import team.devblook.pepitocore.api.module.CoreModule;
import team.devblook.pepitocore.plugin.module.restart.command.RestartCommand;

public class RestartModule implements CoreModule {

    private @Inject Plugin plugin;

    private @Inject RestartRunnable restartRunnable;

    private @Inject CommandManager manager;
    private @Inject AnnotatedCommandTreeBuilder builder;

    private @Inject RestartCommand command; // command is being built two times, fix that

    @Override
    public void enable() {
        manager.registerCommands(builder.fromClass(command));

        Bukkit.getScheduler().runTaskLater(
                plugin,
                restartRunnable,
                24 * 60 * 60 * 20
        );
    }

    @Override
    public void disable() {
        manager.unregisterCommands(builder.fromClass(command));
    }
}