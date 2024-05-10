package team.devblook.pepitocore.plugin.command;

import com.google.inject.Injector;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.command.CommandSender;
import team.devblook.pepitocore.api.module.CoreModule;
import team.devblook.pepitocore.api.registry.TRegistry;
import team.devblook.pepitocore.plugin.module.Modules;

import javax.inject.Inject;
import java.util.function.Function;

@Command(names = "module")
public class ModuleCommand implements CommandClass {

    private @Inject Injector injector;
    private @Inject TRegistry<String, CoreModule> modules;

    @Command(names = "enable")
    public void enable(@Sender CommandSender sender, String name) {
        String lowered = name.toLowerCase();
        if (modules.find(lowered) != null) {
            sender.sendMessage("Module '" + name + "' is already enabled!");
            return;
        }

        Function<Injector, CoreModule> function = Modules.MAPPINGS.get(lowered);
        if (function == null) {
            sender.sendMessage("Module '" + name + "' not found!");
            return;
        }

        CoreModule module = function.apply(injector);
        module.enable();

        modules.insert(lowered, module);
        sender.sendMessage("Module '" + name + "' enabled!");
    }

    @Command(names = "disable")
    public void disable(@Sender CommandSender sender, String name) {
        CoreModule module = modules.delete(name.toLowerCase());
        if (module == null) {
            sender.sendMessage("Module '" + name + "' is already disabled!");
            return;
        }

        module.disable();
        sender.sendMessage("Module '" + name + "' disabled!");
    }

    @Command(names = "list")
    public void list(@Sender CommandSender sender) {
        sender.sendMessage("Enabled modules:");
        for (CoreModule module : modules) {
            sender.sendMessage("Module: " + module.id());
        }
    }
}