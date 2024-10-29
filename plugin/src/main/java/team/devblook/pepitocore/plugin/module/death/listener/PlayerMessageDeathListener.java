package team.devblook.pepitocore.plugin.module.death.listener;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import team.devblook.pepitocore.plugin.module.death.util.DeathUtil;

public class PlayerMessageDeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        final EntityDamageEvent damageEvent = event.getPlayer().getLastDamageCause();
        event.getPlayer()
                .getWorld()
                .strikeLightningEffect(event.getPlayer().getLocation());

        final Sound sound = Sound.sound(Key.key("entity.lightning_bolt.thunder"), Sound.Source.AMBIENT, 1.0f, 1.0f);
        Bukkit.getOnlinePlayers().forEach(player -> player.playSound(sound));


        if (damageEvent == null) {
            event.deathMessage(Component.text("El jugador ha muerto sin causa aparente. O.o", TextColor.fromHexString("#ff505e")));
            return;
        }

        final EntityDamageEvent.DamageCause cause = damageEvent.getCause();
        final Player player = event.getPlayer();

        switch (cause) {
            case ENTITY_ATTACK:
                final Player killer = event.getPlayer().getKiller();
                final Entity entity = damageEvent.getEntity();

                if (killer == null) {
                    DeathUtil.entityAttackDeathMessage(event, entity);
                    return;
                }

                event.deathMessage(
                        player.name().append(Component.text(" fue asesinado por ", TextColor.fromHexString("#ff505e"))
                                .append(killer.name())
                        ));
                break;
            case ENTITY_EXPLOSION:
                event.deathMessage(
                        player.name().append(Component.text(" quedó hecho trizas por una explosión.", TextColor.fromHexString("#ff505e")))
                );
                break;
            case FALL:
                event.deathMessage(
                        player.name().append(Component.text(" es un capo, cayó al vacío.", TextColor.fromHexString("#ff505e")))
                );
                break;
            case FIRE:
            case FIRE_TICK:
                event.deathMessage(
                        player.name().append(Component.text(" al parecer le gusta andar caliente, tanto que se murió.", TextColor.fromHexString("#ff505e")))
                );
                break;
            case LAVA:
                event.deathMessage(
                        player.name().append(Component.text(" se cayó en lava, que noob.", TextColor.fromHexString("#ff505e")))
                );
                break;
            case DROWNING:
                event.deathMessage(
                        player.name().append(Component.text(" se ahogó, que noob.", TextColor.fromHexString("#ff505e")))
                );
                break;
            case VOID:
                event.deathMessage(
                        player.name().append(Component.text(" tocó fondo, pero no el que esperaba.", TextColor.fromHexString("#ff505e")))
                );
                break;
            case SUFFOCATION:
                event.deathMessage(
                        player.name().append(Component.text(" se asfixió, pero es que hay que ser tarado.", TextColor.fromHexString("#ff505e")))
                );
                break;
            case LIGHTNING:
                event.deathMessage(
                        player.name().append(Component.text(" fue fulminado por un rayo.", TextColor.fromHexString("#ff505e")))
                );
                break;
            case STARVATION:
                event.deathMessage(
                        player.name().append(Component.text(" murió de hambre, sal de tu país bro.", TextColor.fromHexString("#ff505e")))
                );
                break;
            case POISON:
            case WITHER:
                event.deathMessage(
                        player.name().append(Component.text(" murió envenenado, lol.", TextColor.fromHexString("#ff505e")))
                );
                break;
            case MAGIC:
                event.deathMessage(
                        player.name().append(Component.text(" fue asesinado por magia negra.", TextColor.fromHexString("#ff505e")))
                );
                break;
            default:
                event.deathMessage(
                        player.name().append(Component.text(" es tan raro, que hasta su muerte es desconocida.", TextColor.fromHexString("#ff505e")))
                );
        }
    }
}
