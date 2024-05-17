package team.devblook.pepitocore.plugin.module.event.task;

import net.kyori.adventure.bossbar.BossBar;
import team.devblook.pepitocore.plugin.module.event.model.GameEvent;

public class BossBarTask implements Runnable {

    private final BossBar bossBar;
    private final float delta;

    public BossBarTask(final GameEvent gameEvent) {
        this.bossBar = gameEvent.bossBar();
        this.delta = 1.0f / (gameEvent.duration() * 60.0f);
    }

    @Override
    public void run() {
        if ((bossBar.progress() - delta) <= 0.0f) {
            bossBar.progress(0);
            return;
        }

        bossBar.progress(bossBar.progress() - delta);
    }
}