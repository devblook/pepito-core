package team.devblook.pepitocore.plugin.module.event.type;

import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import team.devblook.pepitocore.plugin.module.event.model.GameEvent;

import java.time.Duration;

public class AdventureEvent implements GameEvent {

    private final BossBar bossBar = BossBar.bossBar(
            this::name,
            1.0f,
            BossBar.Color.RED,
            BossBar.Overlay.PROGRESS
    );

    @Override
    public Component name() {
        return Component.text("ᴀᴠᴇɴᴛᴜʀᴀ", TextColor.fromHexString("#ff3d3a"));
    }

    @Override
    public Component description() {
        return Component.text()
                .appendNewline()
                .appendNewline()
                .appendNewline()
                .appendNewline()
                .append(Component.text("¡Estamos de aventura!", TextColor.fromHexString("#ff3d3a")))
                .appendNewline()
                .appendNewline()
                .append(Component.text("    Te actualizamos tu modo de juego, ahora estamos en", TextColor.color(0xE4FFE5)))
                .appendNewline()
                .append(Component.text("    modo aventura, no creo que necesitemos suerte.", TextColor.color(0xE4FFE5)))
                .appendNewline()
                .appendNewline()
                .appendNewline()
                .build();
    }

    @Override
    public Title title() {
        return Title.title(
                Component.text("¡ᴀᴠᴇɴᴛᴜʀᴀ!", TextColor.fromHexString("#ff3d3a")),
                Component.text("¡Vamos a divertirnos mucho!", TextColor.color(0xE4FFE5)),
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
        return this.bossBar;
    }

    @Override
    public int duration() {
        return 3;
    }

    @Override
    public String id() {
        return "adventure";
    }

    @Override
    public void begin() {
        Bukkit.getOnlinePlayers().forEach(player -> player.setGameMode(GameMode.ADVENTURE));
    }

    @Override
    public void end() {
        Bukkit.getOnlinePlayers().forEach(player -> player.setGameMode(GameMode.SURVIVAL));
    }
}
