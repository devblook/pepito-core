package team.devblook.pepitocore.plugin.module.event.type;

import io.papermc.paper.ban.BanListType;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import team.devblook.pepitocore.plugin.module.event.GameEventExecutor;
import team.devblook.pepitocore.plugin.module.event.model.GameEvent;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.UUID;

public class NoMoveGameEvent implements GameEvent {

    private final BossBar bossBar = BossBar.bossBar(
            this::name,
            1,
            BossBar.Color.PINK,
            BossBar.Overlay.PROGRESS
    );

    private UUID gay;

    @Override
    public String id() {
        return "no-move";
    }

    @Override
    public Component name() {
        return Component.text("ᴇʟ ǫᴜᴇ sᴇ ᴍᴜᴇᴠᴀ ᴇs ɢᴀʏ", TextColor.color(0xA9009D));
    }

    @Override
    public Component description() {
        return Component.text()
                .appendNewline()
                .appendNewline()
                .append(Component.text("¡EL QUE SE MUEVA ES GAY!", TextColor.color(0xA9009D)))
                .appendNewline()
                .appendNewline()
                .append(Component.text("    No te muevas, porque si no serás muy gay", TextColor.color(0xE4FFE5)))
                .appendNewline()
                .append(Component.text("    Y el que se salga será baneado", TextColor.color(0xE4FFE5)))
                .appendNewline()
                .append(Component.text("    ¡No seas gay!", TextColor.color(0x35BD30)))
                .appendNewline()
                .appendNewline()
                .build();
    }

    @Override
    public Title title() {
        return Title.title(
                Component.text("¡EL QUE SE MUEVA ES GAY!", TextColor.color(0xA9009D)),
                Component.text("Si te mueves eres bien GAY :D"),
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
        if (gay != null) {
            Bukkit.dispatchCommand(
                    Bukkit.getConsoleSender(),
                    "lp user " + gay + " parent removetemp gay"
            );

            gay = null;
        }
    }

    @Override
    public void end() {
        Bukkit.dispatchCommand(
                Bukkit.getConsoleSender(),
                "lp user " + gay + " parent addtemp gay 1d"
        );
    }

    @Override
    public Map<Class<? extends Event>, GameEventExecutor<? extends Event>> events() {
        return Map.of(
                PlayerMoveEvent.class,
                new GameEventExecutor<>(
                        PlayerMoveEvent.class,
                        event -> {
                            if (gay != null) {
                                return;
                            }

                            Player player = event.getPlayer();

                            gay = player.getUniqueId();

                            // do stuff with gay player
                            player.sendMessage(Component.text("Sos re gay.", TextColor.color(0xFF0000)));

                            Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
                                if (onlinePlayer != player) {
                                    onlinePlayer.sendMessage(Component.text(player.getName() + " es re gay.", TextColor.color(0xFF0000)));
                                }
                            });
                        }
                ),
                PlayerQuitEvent.class,
                new GameEventExecutor<>(
                        PlayerQuitEvent.class,
                        event -> {
                            if (gay != null) {
                                return;
                            }

                            Bukkit.getBanList(BanListType.PROFILE).addBan(
                                    event.getPlayer().getPlayerProfile(),
                                    "Homosexual",
                                    Duration.of(1, ChronoUnit.HOURS),
                                    "Homosexual Judgement"
                            );
                        }
                )
        );
    }
}