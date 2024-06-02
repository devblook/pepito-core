package team.devblook.pepitocore.plugin.module.event.type;

import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerBedEnterEvent;
import team.devblook.pepitocore.plugin.module.event.GameEventExecutor;
import team.devblook.pepitocore.plugin.module.event.model.GameEvent;

import java.time.Duration;
import java.util.Map;

public class Insomnia implements GameEvent {

    private final BossBar bossBar = BossBar.bossBar(
            this::name,
            1.0f,
            BossBar.Color.BLUE,
            BossBar.Overlay.PROGRESS
    );

    @Override
    public Component name() {
        return Component.text("ɪɴѕᴏᴍɴɪᴏ", TextColor.fromHexString("#2661e0"));
    }

    @Override
    public Component description() {
        return Component.text()
                .appendNewline()
                .appendNewline()
                .appendNewline()
                .appendNewline()
                .append(Component.text("¡No podrás dormir!", TextColor.fromHexString("#2661e0")))
                .appendNewline()
                .appendNewline()
                .append(Component.text("    Ahora es de noche y parece que por el mal día no puedes", TextColor.color(0xE4FFE5)))
                .appendNewline()
                .append(Component.text("    dormir, cuidado con los peligros de la noche.", TextColor.color(0xE4FFE5)))
                .appendNewline()
                .appendNewline()
                .appendNewline()
                .build();
    }

    @Override
    public Title title() {
        return Title.title(
                Component.text("¡ɪɴѕᴏᴍɴɪᴏ!", TextColor.fromHexString("#2661e0")),
                Component.text("Nos quedamos sin píldoras para dormir", TextColor.color(0xE4FFE5)),
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
                Key.key("minecraft", "entity.enderman.death"),
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
        return 9;
    }

    @Override
    public String id() {
        return "insomnia";
    }

    @Override
    public void begin() {
        Bukkit.getWorlds().get(0).setTime(13000);
    }

    @Override
    public Map<Class<? extends Event>, GameEventExecutor<? extends Event>> events() {
        return Map.of(
                PlayerBedEnterEvent.class,
                new GameEventExecutor<>(
                        PlayerBedEnterEvent.class,
                        event -> {
                            if (event.getBedEnterResult() == PlayerBedEnterEvent.BedEnterResult.OK) {
                                event.getPlayer().sendMessage(Component.text(
                                        "No puedes dormir ahora, no tienes sueño.",
                                        TextColor.fromHexString("#E7783C")
                                ));
                                event.setCancelled(true);
                            }
                        }
                )
        );
    }

    @Override
    public EventPriority priority() {
        return EventPriority.LOWEST;
    }

    @Override
    public boolean subtractDuration() {
        return true;
    }
}
