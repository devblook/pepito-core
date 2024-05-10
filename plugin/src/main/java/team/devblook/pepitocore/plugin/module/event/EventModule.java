package team.devblook.pepitocore.plugin.module.event;

import com.google.inject.Inject;
import team.devblook.pepitocore.api.module.CoreModule;

public class EventModule implements CoreModule {

    private @Inject EventPool pool;

    @Override
    public void enable() {
        pool.run();
    }

    @Override
    public void disable() {
        pool.stop();
    }
}