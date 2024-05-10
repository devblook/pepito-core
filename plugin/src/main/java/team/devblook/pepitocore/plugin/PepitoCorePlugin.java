package team.devblook.pepitocore.plugin;

import com.google.inject.Guice;
import com.google.inject.Inject;
import me.fixeddev.commandflow.CommandManager;
import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilder;
import org.bukkit.plugin.java.JavaPlugin;
import team.devblook.pepitocore.api.PepitoCoreAPI;
import team.devblook.pepitocore.api.module.CoreModule;
import team.devblook.pepitocore.api.registry.TRegistry;
import team.devblook.pepitocore.plugin.command.ModuleCommand;

public final class PepitoCorePlugin
        extends JavaPlugin
        implements PepitoCoreAPI {

    private @Inject CommandManager manager;
    private @Inject AnnotatedCommandTreeBuilder builder;

    private @Inject ModuleCommand command;

    private @Inject TRegistry<String, CoreModule> modules;

    @Override
    public void onLoad() {
        Guice.createInjector(new PepitoCoreBindingModule(this))
                .injectMembers(this);
    }

    @Override
    public void onEnable() {
        manager.registerCommands(builder.fromClass(command));
    }

    @Override
    public void onDisable() {
        for (CoreModule module : modules) {
            module.disable();
        }
    }
}