package team.devblook.pepitocore.plugin.module.indicator.listener;

import com.google.inject.Inject;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import team.devblook.pepitocore.plugin.module.indicator.util.IndicatorUtil;

public class EntityDamageListener implements Listener {

    private @Inject Plugin plugin;

    @EventHandler(ignoreCancelled = true)
    public void onEntityDamageByPlayer(final EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player)) {
            return;
        }

        if (!(event.getEntity() instanceof LivingEntity entity)) {
            return;
        }

        final TagResolver.Single indicator = Placeholder.component(
                "damage",
                Component.text((int) event.getFinalDamage()));

        IndicatorUtil.spawnIndicator(
                this.plugin,
                player,
                entity,
                null); //this.configuration.getComponent("damage.indicator-format", indicator));
    }


}
