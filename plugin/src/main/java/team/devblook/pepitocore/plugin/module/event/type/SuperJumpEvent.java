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

public class SuperJumpEvent implements GameEvent {

    private final BossBar bossBar = BossBar.bossBar(
            this::name,
            1,
            BossBar.Color.PURPLE,
            BossBar.Overlay.PROGRESS
    );

    private final PotionEffect poison = new PotionEffect(
            PotionEffectType.JUMP,
            duration() * 20 * 60,
            20
    );

    @Override
    public String id() {
        return "super-jump";
    }

    @Override
    public Component name() {
        return Component.text("sᴜᴘᴇʀ sᴀʟᴛᴏ", TextColor.fromHexString("#7d44be"));
    }

    @Override
    public Component description() {
        return Component.text()
                .appendNewline()
                .appendNewline()
                .appendNewline()
                .appendNewline()
                .append(Component.text("¡Salta como nunca antes!", TextColor.fromHexString("#7d44be")))
                .appendNewline()
                .appendNewline()
                .append(Component.text("    Ahora podrás saltar tan alto que alcanzarás las estrellas...", TextColor.color(0xE4FFE5)))
                .appendNewline()
                .append(Component.text("    Upss... Se nos olvidó habilitarte piernas de acero.", TextColor.color(0xE4FFE5)))
                .appendNewline()
                .append(Component.text("    Así que... ten cuidado al caer :)", TextColor.color(0xE4FFE5)))
                .build();
    }

    @Override
    public Title title() {
        return Title.title(
                Component.text("¡Salta como nunca antes!", TextColor.fromHexString("#7d44be")),
                Component.text("No olvides caer de pie, pero no eres un gato :c"),
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