package team.devblook.pepitocore.plugin;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import me.fixeddev.commandflow.CommandManager;
import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilder;
import org.bukkit.plugin.java.JavaPlugin;
import team.devblook.pepitocore.api.PepitoCoreAPI;
import team.devblook.pepitocore.api.module.CoreModule;
import team.devblook.pepitocore.api.registry.TRegistry;
import team.devblook.pepitocore.plugin.command.ModuleCommand;
import team.devblook.pepitocore.plugin.configuration.BukkitConfiguration;
import team.devblook.pepitocore.plugin.module.Modules;

public final class PepitoCorePlugin
        extends JavaPlugin
        implements PepitoCoreAPI {

    private @Inject CommandManager manager;
    private @Inject AnnotatedCommandTreeBuilder builder;

    private @Inject ModuleCommand command;

    private @Inject TRegistry<String, CoreModule> modules;
    private @Inject BukkitConfiguration configuration;
    private Injector injector;

    @Override
    public void onLoad() {
        this.injector = Guice.createInjector(new PepitoCoreBindingModule(this));
        this.injector.injectMembers(this);
    }

    @Override
    public void onEnable() {
        manager.registerCommands(builder.fromClass(command));

        configuration.get().getStringList("module.active-modules").forEach(value -> {
            final CoreModule module = Modules.MAPPINGS.get(value).apply(this.injector);
            if (module != null) {

                modules.insert(module.id(), module);
                module.enable();
                this.getComponentLogger().info("Enabling module: {}", module.id());
            }
        });
    }

    @Override
    public void onDisable() {
        for (CoreModule module : modules) {
            module.disable();
        }
    }
}