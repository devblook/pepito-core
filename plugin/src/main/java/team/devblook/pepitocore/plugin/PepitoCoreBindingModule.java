package team.devblook.pepitocore.plugin;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import me.fixeddev.commandflow.CommandManager;
import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilder;
import me.fixeddev.commandflow.annotated.part.PartInjector;
import me.fixeddev.commandflow.annotated.part.defaults.DefaultsModule;
import me.fixeddev.commandflow.bukkit.BukkitCommandManager;
import me.fixeddev.commandflow.bukkit.factory.BukkitModule;
import org.bukkit.plugin.Plugin;
import team.devblook.pepitocore.api.module.CoreModule;
import team.devblook.pepitocore.api.registry.LocalTRegistry;
import team.devblook.pepitocore.api.registry.TRegistry;
import team.devblook.pepitocore.plugin.configuration.ConfigurationModule;

import java.util.HashMap;

public class PepitoCoreBindingModule extends AbstractModule {

    private final Plugin plugin;

    public PepitoCoreBindingModule(PepitoCorePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    protected void configure() {
        bind(Plugin.class).toInstance(plugin);

        bind(CommandManager.class).toInstance(new BukkitCommandManager(plugin.getName()));

        PartInjector injector = PartInjector.create();
        injector.install(new DefaultsModule());
        injector.install(new BukkitModule());

        AnnotatedCommandTreeBuilder builder = AnnotatedCommandTreeBuilder.create(injector);

        bind(AnnotatedCommandTreeBuilder.class).toInstance(builder);

        bind(new TypeLiteral<TRegistry<String, CoreModule>>() {
        }).toInstance(new LocalTRegistry<>(new HashMap<>()));

        this.install(new ConfigurationModule());
    }
}