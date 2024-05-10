package team.devblook.pepitocore.plugin.module.tpa.task;

import com.google.common.collect.Multimap;
import com.google.inject.Inject;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import team.devblook.pepitocore.plugin.module.tpa.model.TPARequest;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class TPARequestExpireTask extends BukkitRunnable {

    private static final long EXPIRATION_DIFFERENCE
            = TimeUnit.MINUTES.toMillis(2);

    private @Inject Multimap<UUID, TPARequest> requests;

    @Override
    public void run() {
        for (UUID key : requests.keys()) {
            Collection<TPARequest> sent = requests.get(key);
            for (TPARequest request : sent) {
                if (System.currentTimeMillis() - request.elapsed() < EXPIRATION_DIFFERENCE) {
                    return;
                }

                sent.remove(request);

                Player player = Bukkit.getPlayer(request.from());
                if (player == null) {
                    continue;
                }

                OfflinePlayer offline = Bukkit.getOfflinePlayer(request.to());
                Player to = offline.getPlayer();

                if (to != null) {
                    to.sendMessage("TPA request from '" + player.getName() + "' expired!");
                }

                player.sendMessage("TPA request to " + offline.getName() + "' expired!");
            }
        }
    }
}