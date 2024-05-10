package team.devblook.pepitocore.plugin.module.event.type;

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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import team.devblook.pepitocore.plugin.module.event.GameEventExecutor;
import team.devblook.pepitocore.plugin.module.event.model.GameEvent;

import java.util.Map;

public class PoisonGameEvent implements GameEvent {

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
        return Component.text("Poison Game");
    }

    @Override
    public Component description() {
        return Component.text()
                .append(Component.text("¡Bienvenido al juego del veneno!", TextColor.color(0x526AA9)))
                .append(Component.text("Tendrás veneno por" + duration() + " minutos", TextColor.color(0xE4FFE5)))
                .appendNewline()
                .append(Component.text("Ah... Y no podrán tomar leche para curarse.", TextColor.color(0xE4FFE5)))
                .appendNewline()
                .append(Component.text("¡Buena suerte!", TextColor.color(0x35BD30)))
                .build();
    }

    @Override
    public Title title() {
        return Title.title(
                Component.text("Poison Game"),
                Component.text("Sexo")
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
    public void begin() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.addPotionEffect(poison);
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
                )
        );
    }
}