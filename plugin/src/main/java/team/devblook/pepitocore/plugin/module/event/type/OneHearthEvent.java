package team.devblook.pepitocore.plugin.module.event.type;

import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import team.devblook.pepitocore.plugin.module.event.model.GameEvent;

import java.time.Duration;

public class OneHearthEvent implements GameEvent {

    private final BossBar bossBar = BossBar.bossBar(
            this::name,
            1.0f,
            BossBar.Color.RED,
            BossBar.Overlay.PROGRESS
    );

    @Override
    public Component name() {
        return Component.text("ᴜɴ ᴄᴏʀᴀᴢóɴ", TextColor.fromHexString("#ff3d3a"));
    }

    @Override
    public Component description() {
        return Component.text()
                .appendNewline()
                .appendNewline()
                .appendNewline()
                .appendNewline()
                .append(Component.text("¡Un Corazón!", TextColor.fromHexString("#7d44be")))
                .appendNewline()
                .appendNewline()
                .append(Component.text("    Bueno, tendrás 2 minutos de tensión, ahora solo tenemos un", TextColor.color(0xE4FFE5)))
                .appendNewline()
                .append(Component.text("    corazón, así que yo de ti, tendría cuidado...", TextColor.color(0xE4FFE5)))
                .appendNewline()
                .appendNewline()
                .appendNewline()
                .build();
    }

    @Override
    public Title title() {
        return Title.title(
                Component.text("¡ᴜɴ ᴄᴏʀᴀᴢóɴ!", TextColor.fromHexString("#7d44be")),
                Component.text("Como para que le pongas valor a tu vida..."),
                Title.Times.times(
                        Duration.ofSeconds(2),
                        Duration.ofSeconds(5),
                        Duration.ofSeconds(2)
                )
        );
    }

    @Override
    public Sound sound() {
        return Sound.sound(Key.key("minecraft:entity.ender_dragon.growl"), Sound.Source.AMBIENT, 1.0f, 1.0f);
    }

    @Override
    public BossBar bossBar() {
        return this.bossBar;
    }

    @Override
    public int duration() {
        return 2;
    }

    @Override
    public String id() {
        return "one-hearth";
    }

    @Override
    public void begin() {
        this.bossBar.progress(1);
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.setHealthScale(2);
            player.setHealth(2);
            player.setMaxHealth(2);
        });
    }

    @Override
    public void end() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.setHealthScale(20);
            player.setHealth(20);
        });

        Bukkit.getOnlinePlayers().forEach(player -> player.hideBossBar(bossBar));
    }
}
