package team.devblook.pepitocore.plugin.module.event.type;

import io.papermc.paper.ban.BanListType;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
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

public class NoMoveGameEvent implements GameEvent {

    private boolean isSomeoneGay;

    @Override
    public String id() {
        return "no-move-game-event";
    }

    @Override
    public Component name() {
        return Component.text("No Move");
    }

    @Override
    public Component description() {
        return Component.text("Descripción Perrona");
    }

    @Override
    public Title title() {
        return Title.title(
                Component.text("No Move"),
                Component.text("Si te mueves eres bien GAY")
        );
    }

    @Override
    public Sound sound() {
        return Sound.sound(Key.key("music_disc.13"), Sound.Source.AMBIENT, 3f, 1f);
    }

    @Override
    public int duration() {
        return 1;
    }

    @Override
    public void end() {
        isSomeoneGay = false;
    }

    @Override
    public Map<Class<? extends Event>, GameEventExecutor<? extends Event>> events() {
        return Map.of(
                PlayerMoveEvent.class,
                new GameEventExecutor<>(
                        PlayerMoveEvent.class,
                        event -> {
                            if (isSomeoneGay) {
                                return;
                            }

                            isSomeoneGay = true;

                            Player player = event.getPlayer();
                            // do stuff with gay player
                            player.sendMessage("You loose!");
                        }
                ),
                PlayerQuitEvent.class,
                new GameEventExecutor<>(
                        PlayerQuitEvent.class,
                        event -> {
                            if (isSomeoneGay) {
                                return;
                            }

                            Player player = event.getPlayer();

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