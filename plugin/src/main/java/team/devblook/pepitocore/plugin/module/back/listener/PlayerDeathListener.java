package team.devblook.pepitocore.plugin.module.back.listener;

import com.google.inject.Inject;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import team.devblook.pepitocore.api.registry.TRegistry;
import team.devblook.pepitocore.plugin.BlockLocation;
import team.devblook.pepitocore.plugin.module.back.model.LockedLocation;

import java.util.UUID;

import static team.devblook.pepitocore.plugin.module.back.UnlockMaterials.MATERIALS;
import static team.devblook.pepitocore.plugin.util.Randoms.RANDOM;

public class PlayerDeathListener implements Listener {

    private @Inject TRegistry<UUID, LockedLocation> locations;

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getPlayer();

        Material material = MATERIALS.get(RANDOM.nextInt(MATERIALS.size()));
        LockedLocation location = new LockedLocation(
                material,
                RANDOM.nextInt(material.getMaxStackSize() - 1) + 1,
                BlockLocation.fromLocation(player.getLocation())
        );

        player.sendMessage("Si quieres volver al punto en que moriste, puedes usar /back por el módico precio de " + location.amount() + "x " + location.type() + ".");

        locations.insert(
                player.getUniqueId(),
                location
        );
    }
}