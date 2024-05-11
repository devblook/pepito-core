package team.devblook.pepitocore.plugin.module.indicator.util;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class IndicatorUtil {

    private IndicatorUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static void spawnIndicator(
            final Plugin plugin,
            final Player player,
            final Entity entity,
            final Component damageIndicator
    ) {
        final ArmorStand indicator = (ArmorStand) player.getWorld()
                .spawnEntity(
                        entity.getLocation()
                                .add(0, 1, 0),
                        EntityType.ARMOR_STAND
                );
        final NamespacedKey indicatorKey = NamespacedKey.fromString("bc:indicator");

        if (indicatorKey == null) {
            return;
        }

        indicator.setInvisible(true);
        indicator.setInvulnerable(true);
        indicator.setMarker(true);
        indicator.setCustomNameVisible(true);
        indicator.customName(damageIndicator);
        indicator.getPersistentDataContainer()
                .set(indicatorKey, PersistentDataType.STRING, "active");

        Bukkit.getScheduler()
                .runTaskLater(
                        plugin,
                        indicator::remove,
                        20L
                );
    }

}
