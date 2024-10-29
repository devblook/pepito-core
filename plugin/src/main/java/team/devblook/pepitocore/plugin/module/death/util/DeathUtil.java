package team.devblook.pepitocore.plugin.module.death.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

public final class DeathUtil {

    private DeathUtil() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    public static final TextColor DEATH_COLOR_TEXT = TextColor.fromHexString("#ff505e");

    public static void entityAttackDeathMessage(
            final PlayerDeathEvent event,
            final Entity entity
    ) {

        final Player player = event.getEntity();

        switch (entity.getType()) {
            case ZOMBIE_VILLAGER:
            case ZOMBIE:
                event.deathMessage(
                        player.name().append(Component.text(" fue mordido por un zombie.", DEATH_COLOR_TEXT))
                );
                break;
            case SKELETON:
                event.deathMessage(
                        player.name().append(Component.text(" fue flechado por un esqueleto.", DEATH_COLOR_TEXT))
                );
                break;
            case SPIDER:
                event.deathMessage(
                        player.name().append(Component.text(" fue mordido por una araña.", DEATH_COLOR_TEXT))
                );
                break;
            case CREEPER:
                event.deathMessage(
                        player.name().append(Component.text(" fue explotado por un creeper.", DEATH_COLOR_TEXT))
                );
                break;
            case ENDERMAN:
                event.deathMessage(
                        player.name().append(Component.text(" fue asesinado por un enderman.", DEATH_COLOR_TEXT))
                );
                break;
            case WITHER:
                event.deathMessage(
                        player.name().append(Component.text(" fue asesinado por un wither.", TextColor.fromHexString("#1f1f42")))
                );
                break;
            case WITCH:
                event.deathMessage(
                        player.name().append(Component.text(" fue envenenado por una bruja.", DEATH_COLOR_TEXT))
                );
                break;
            case VEX:
                event.deathMessage(
                        player.name().append(Component.text(" fue asesinado por un vex.", DEATH_COLOR_TEXT))
                );
                break;
            case VINDICATOR:
                event.deathMessage(
                        player.name().append(Component.text(" fue asesinado por un vindicator.", DEATH_COLOR_TEXT))
                );
                break;
            case PILLAGER:
                event.deathMessage(
                        player.name().append(Component.text(" fue asesinado por un pillager.", DEATH_COLOR_TEXT))
                );
                break;
            case RAVAGER:
                event.deathMessage(
                        player.name().append(Component.text(" fue asesinado por un ravager.", DEATH_COLOR_TEXT))
                );
                break;
            case ILLUSIONER:
                event.deathMessage(
                        player.name().append(Component.text(" fue asesinado por un illusioner.", DEATH_COLOR_TEXT))
                );
                break;
            case EVOKER:
                event.deathMessage(
                        player.name().append(Component.text(" fue asesinado por un evoker.", DEATH_COLOR_TEXT))
                );
                break;
            case DROWNED:
                event.deathMessage(
                        player.name().append(Component.text(" fue ahogado por un ahogado.", DEATH_COLOR_TEXT))
                );
                break;
            case BLAZE:
                event.deathMessage(
                        player.name().append(Component.text(" fue asesinado por un blaze.", DEATH_COLOR_TEXT))
                );
                break;
            case GHAST:
                event.deathMessage(
                        player.name().append(Component.text(" fue asesinado por un ghast.", DEATH_COLOR_TEXT))
                );
                break;
            case HUSK:
                event.deathMessage(
                        player.name().append(Component.text(" fue asesinado por un husk.", DEATH_COLOR_TEXT))
                );
                break;
            case MAGMA_CUBE:
                event.deathMessage(
                        player.name().append(Component.text(" fue asesinado por un magma cube.", DEATH_COLOR_TEXT))
                );
                break;
            case PHANTOM:
                event.deathMessage(
                        player.name().append(Component.text(" fue asesinado por un phantom.", DEATH_COLOR_TEXT))
                );
                break;
            case PIGLIN:
                event.deathMessage(
                        player.name().append(Component.text(" fue asesinado por un piglin.", DEATH_COLOR_TEXT))
                );
                break;
            case SHULKER:
                event.deathMessage(
                        player.name().append(Component.text(" fue asesinado por un shulker.", DEATH_COLOR_TEXT))
                );
                break;
            case SILVERFISH:
                event.deathMessage(
                        player.name().append(Component.text(" fue asesinado por un silverfish.", DEATH_COLOR_TEXT))
                );
                break;
            case STRAY:
                event.deathMessage(
                        player.name().append(Component.text(" fue asesinado por un stray.", DEATH_COLOR_TEXT))
                );
                break;
            case WITHER_SKELETON:
                event.deathMessage(
                        player.name().append(Component.text(" fue asesinado por un wither skeleton.", DEATH_COLOR_TEXT))
                );
                break;
            case ZOGLIN:
                event.deathMessage(
                        player.name().append(Component.text(" fue asesinado por un hoglin.", DEATH_COLOR_TEXT))
                );
                break;
            case ZOMBIFIED_PIGLIN:
                event.deathMessage(
                        player.name().append(Component.text(" fue asesinado por un zombificado piglin.", DEATH_COLOR_TEXT))
                );
                break;
            case ENDER_DRAGON:
                event.deathMessage(
                        player.name().append(Component.text(" fue asesinado por el ender dragon.", TextColor.fromHexString("#7438ff")))
                );
                break;
            default:
                event.deathMessage(
                        player.name().append(Component.text(" fue asesinado por algún bicho (Me cansé de poner mobs, jodanse. ~ Jonakls).", DEATH_COLOR_TEXT))
                );
        }

    }


}
