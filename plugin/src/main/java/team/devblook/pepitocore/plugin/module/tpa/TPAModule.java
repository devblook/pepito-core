package team.devblook.pepitocore.plugin.module.tpa;

import com.google.inject.Inject;
import me.fixeddev.commandflow.CommandManager;
import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilder;
import org.bukkit.plugin.Plugin;
import team.devblook.pepitocore.api.module.CoreModule;
import team.devblook.pepitocore.plugin.module.tpa.command.TPACommand;
import team.devblook.pepitocore.plugin.module.tpa.task.TPARequestExpireTask;

public class TPAModule implements CoreModule {

    private @Inject Plugin plugin;
    private @Inject CommandManager manager;
    private @Inject AnnotatedCommandTreeBuilder treeBuilder;

    private @Inject TPACommand command; // command is being built two times, fix that
    private @Inject TPARequestExpireTask task;

    @Override
    public void enable() {
        manager.registerCommands(treeBuilder.fromClass(command));
        task.runTaskTimerAsynchronously(plugin, 20L, 20L);
    }

    @Override
    public void disable() {
        manager.unregisterCommands(treeBuilder.fromClass(command));
        if (task == null || task.isCancelled()) {
            return;
        }

        task.cancel();
    }
}