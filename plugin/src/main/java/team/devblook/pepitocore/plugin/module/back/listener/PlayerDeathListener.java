package team.devblook.pepitocore.plugin.module.back.listener;

import com.google.inject.Inject;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import team.devblook.pepitocore.api.registry.TRegistry;
import team.devblook.pepitocore.plugin.BlockLocation;
import team.devblook.pepitocore.plugin.module.back.LockedLocation;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class PlayerDeathListener implements Listener {

    private static final Random RANDOM = new Random();
    private static final List<Material> MATERIALS = List.of(
            Material.STONE
    );

    private @Inject TRegistry<UUID, LockedLocation> locations;

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getPlayer();

        LockedLocation location = new LockedLocation(
                MATERIALS.get(RANDOM.nextInt(MATERIALS.size())),
                RANDOM.nextInt(64),
                BlockLocation.fromLocation(player.getLocation())
        );

        player.sendMessage("Si quieres volver al punto en que moriste, puedes usar /back por el módico precio de " + location.amount() + "x " + location.type() + ".");

        locations.insert(
                player.getUniqueId(),
                location
        );
    }
}