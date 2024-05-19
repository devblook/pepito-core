package team.devblook.pepitocore.plugin.module.event.type;

import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import team.devblook.pepitocore.plugin.module.event.GameEventExecutor;
import team.devblook.pepitocore.plugin.module.event.model.GameEvent;

import java.time.Duration;
import java.util.Map;

public class IncreasedMobDamageEvent implements GameEvent {

    private final BossBar bossBar = BossBar.bossBar(
            this::name,
            1,
            BossBar.Color.GREEN,
            BossBar.Overlay.PROGRESS
    );

    private final PotionEffect slowness = new PotionEffect(
            PotionEffectType.SLOW,
            duration() * 20 * 60,
            3
    );

    @Override
    public String id() {
        return "increased-mob-damage";
    }

    @Override
    public Component name() {
        return Component.text("ᴅᴀñᴏ ᴀᴜᴍᴇɴᴛᴀᴅᴏ", TextColor.fromHexString("#be0603"));
    }

    @Override
    public Component description() {
        return Component.text()
                .appendNewline()
                .appendNewline()
                .appendNewline()
                .appendNewline()
                .append(Component.text("Recibes daño incrementado", TextColor.fromHexString("#7d44be")))
                .appendNewline()
                .appendNewline()
                .append(Component.text("    Todas las entidades hacen el triple de daño que normalmente...", TextColor.color(0xE4FFE5)))
                .appendNewline()
                .append(Component.text("    ¿Será que tu armadura te ayudará?.", TextColor.color(0xE4FFE5)))
                .appendNewline()
                .append(Component.text("    Descúbrelo...", TextColor.color(0xE4FFE5)))
                .build();
    }

    @Override
    public Title title() {
        return Title.title(
                Component.text("Recibes daño incrementado", TextColor.fromHexString("#7d44be")),
                Component.text("Recibirás el triple de daño de todas las entidades..."),
                Title.Times.times(
                        Duration.ofSeconds(2),
                        Duration.ofSeconds(5),
                        Duration.ofSeconds(2)
                )
        );
    }

    @Override
    public Sound sound() {
        return Sound.sound(
                Key.key("minecraft", "entity.villager.no"),
                Sound.Source.PLAYER,
                1.0f,
                1.0f
        );
    }

    @Override
    public BossBar bossBar() {
        return bossBar;
    }

    @Override
    public int duration() {
        return 4;
    }

    @Override
    public void begin() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.addPotionEffect(slowness);
        }
    }

    @Override
    public Map<Class<? extends Event>, GameEventExecutor<? extends Event>> events() {
        return Map.of(
                EntityDamageByEntityEvent.class,
                new GameEventExecutor<>(
                        EntityDamageByEntityEvent.class,
                        event -> {
                            if (event.getDamager().getType() == EntityType.PLAYER) {
                                return;
                            }

                            event.setDamage(event.getDamage() * 3);
                        }
                )
        );
    }
}