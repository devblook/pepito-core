package team.devblook.pepitocore.plugin.module.indicator.listener;

import com.google.inject.Inject;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class EntityDamageListener implements Listener {

    private static final NamespacedKey INDICATOR_KEY = new NamespacedKey("bc", "indicator");

    private @Inject Plugin plugin;

    @EventHandler(ignoreCancelled = true)
    public void onEntityDamageByPlayer(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player)) {
            return;
        }

        int damage = (int) event.getFinalDamage();

        ArmorStand stand = (ArmorStand) player.getWorld()
                .spawnEntity(
                        event.getEntity().getLocation()
                                .add(0, 1, 0),
                        EntityType.ARMOR_STAND
                );

        stand.setInvisible(true);
        stand.setInvulnerable(true);
        stand.setMarker(true);
        stand.setCustomNameVisible(true);
        stand.customName(Component.text("-" + damage, TextColor.color(0xAE1F20))); //this.configuration.getComponent("damage.indicator-format", indicator));
        stand.getPersistentDataContainer().set(
                INDICATOR_KEY,
                PersistentDataType.STRING,
                "active"
        );

        Bukkit.getScheduler()
                .runTaskLater(
                        plugin,
                        stand::remove,
                        20L
                );
    }
}