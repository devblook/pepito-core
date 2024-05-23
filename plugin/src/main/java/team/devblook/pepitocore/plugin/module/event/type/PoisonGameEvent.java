package team.devblook.pepitocore.plugin.module.event.type;

import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import team.devblook.pepitocore.plugin.module.event.GameEventExecutor;
import team.devblook.pepitocore.plugin.module.event.model.GameEvent;

import java.time.Duration;
import java.util.Map;

public class PoisonGameEvent implements GameEvent {

    private final BossBar bossBar = BossBar.bossBar(this::name, 1, BossBar.Color.GREEN, BossBar.Overlay.PROGRESS);
    private final PotionEffect poison = new PotionEffect(
            PotionEffectType.POISON,
            duration() * 20 * 60,
            4
    );

    @Override
    public String id() {
        return "poison";
    }

    @Override
    public Component name() {
        return Component.text("ᴊᴜᴇɢᴏ ᴅᴇʟ ᴠᴇɴᴇɴᴏ", TextColor.fromHexString("#2d36ac"));
    }

    @Override
    public Component description() {
        return Component.text()
                .appendNewline()
                .appendNewline()
                .appendNewline()
                .append(Component.text("¡Bienvenido al juego del veneno!", TextColor.fromHexString("#2d36ac")))
                .appendNewline()
                .appendNewline()
                .append(Component.text("    Tendrás veneno por " + duration() + " minuto(s)", TextColor.color(0xE4FFE5)))
                .appendNewline()
                .append(Component.text("    Ah... Y no podrán tomar leche para curarse.", TextColor.color(0xE4FFE5)))
                .appendNewline()
                .append(Component.text("    ¡Buena suerte!", TextColor.color(0x35BD30)))
                .appendNewline()
                .appendNewline()
                .appendNewline()
                .build();
    }

    @Override
    public Title title() {
        return Title.title(
                Component.text("ᴛᴏxɪᴄᴏs", TextColor.color(0x43AE33)),
                Component.text("Tu amiga la toxica te enveneno por " + duration() + " minuto(s)", TextColor.color(0xE4FFE5)),
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
                Key.key("minecraft", "entity.villager.yes"),
                Sound.Source.PLAYER,
                1.0f,
                1.0f
        );
    }

    @Override
    public BossBar bossBar() {
        return this.bossBar;
    }

    @Override
    public int duration() {
        return 1;
    }

    @Override
    public void begin() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.addPotionEffect(poison);
        }
    }

    @Override
    public void end() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.removePotionEffect(poison.getType());
        }
    }

    @Override
    public Map<Class<? extends Event>, GameEventExecutor<? extends Event>> events() {
        return Map.of(
                PlayerItemConsumeEvent.class,
                new GameEventExecutor<>(
                        PlayerItemConsumeEvent.class,
                        event -> {
                            if (event.getItem().getType() != Material.MILK_BUCKET) {
                                return;
                            }

                            event.setCancelled(true);
                        }
                ),
                PlayerJoinEvent.class,
                new GameEventExecutor<>(
                        PlayerJoinEvent.class,
                        event -> {
                            Player player = event.getPlayer();

                            player.addPotionEffect(poison);
                            player.showBossBar(bossBar);
                        }
                ),
                PlayerQuitEvent.class,
                new GameEventExecutor<>(
                        PlayerQuitEvent.class,
                        event -> {
                            Player player = event.getPlayer();

                            player.removePotionEffect(poison.getType());
                            player.hideBossBar(bossBar);
                        }
                )
        );
    }
}