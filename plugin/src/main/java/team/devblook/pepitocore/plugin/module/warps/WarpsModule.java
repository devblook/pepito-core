package team.devblook.pepitocore.plugin.module.warps;

import com.google.inject.Inject;
import me.fixeddev.commandflow.CommandManager;
import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilder;
import team.devblook.pepitocore.api.module.CoreModule;
import team.devblook.pepitocore.plugin.module.warps.command.WarpCommand;

public class WarpsModule implements CoreModule {

    private @Inject CommandManager manager;
    private @Inject AnnotatedCommandTreeBuilder treeBuilder;

    private @Inject WarpCommand command;

    @Override
    public void enable() {
        manager.registerCommands(treeBuilder.fromClass(command));
    }

    @Override
    public void disable() {
        manager.unregisterCommands(treeBuilder.fromClass(command));
    }
}
