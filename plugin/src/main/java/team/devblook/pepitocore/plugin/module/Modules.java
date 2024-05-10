package team.devblook.pepitocore.plugin.module;

import com.google.inject.Injector;
import team.devblook.pepitocore.api.module.CoreModule;
import team.devblook.pepitocore.plugin.module.event.EventModule;
import team.devblook.pepitocore.plugin.module.sleep.SleepModule;
import team.devblook.pepitocore.plugin.module.tpa.TPAModule;
import team.devblook.pepitocore.plugin.module.tpa.binding.TPABindingModule;

import java.util.Map;
import java.util.function.Function;

public interface Modules {

    Map<String, Function<Injector, CoreModule>> MAPPINGS = Map.of(
            "tpa", injector -> injector.createChildInjector(new TPABindingModule()).getInstance(TPAModule.class),
            "events", injector -> injector.getInstance(EventModule.class),
            "sleep", injector -> injector.getInstance(SleepModule.class)
    );
}