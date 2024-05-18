package team.devblook.pepitocore.plugin.module;

import com.google.inject.Injector;
import org.bukkit.plugin.Plugin;
import team.devblook.pepitocore.api.module.CoreModule;
import team.devblook.pepitocore.plugin.module.back.GoBackBindingModule;
import team.devblook.pepitocore.plugin.module.back.GoBackModule;
import team.devblook.pepitocore.plugin.module.broadcast.BroadcastModule;
import team.devblook.pepitocore.plugin.module.chat.ChatModule;
import team.devblook.pepitocore.plugin.module.event.EventModule;
import team.devblook.pepitocore.plugin.module.indicator.IndicatorModule;
import team.devblook.pepitocore.plugin.module.sleep.SleepModule;
import team.devblook.pepitocore.plugin.module.tpa.TPAModule;
import team.devblook.pepitocore.plugin.module.tpa.binding.TPABindingModule;
import team.devblook.pepitocore.plugin.module.warps.WarpsModule;
import team.devblook.pepitocore.plugin.module.warps.binding.WarpBindingModule;

import java.util.Map;
import java.util.function.Function;

public interface Modules {

    Map<String, Function<Injector, CoreModule>> MAPPINGS = Map.of(
            "tpa", injector -> injector.createChildInjector(new TPABindingModule()).getInstance(TPAModule.class),
            "events", injector -> injector.getInstance(EventModule.class),
            "sleep", injector -> injector.getInstance(SleepModule.class),
            "broadcast", injector -> injector.getInstance(BroadcastModule.class),
            "warps", injector -> injector.createChildInjector(new WarpBindingModule(injector.getInstance(Plugin.class))).getInstance(WarpsModule.class),
            "damage-indicator", injector -> injector.getInstance(IndicatorModule.class),
            "chat", injector -> injector.getInstance(ChatModule.class),
            "back", injector -> injector.createChildInjector(new GoBackBindingModule()).getInstance(GoBackModule.class)
    );
}