package team.devblook.pepitocore.plugin.module.event.model;

import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import team.devblook.pepitocore.api.Identity;
import team.devblook.pepitocore.plugin.module.event.GameEventExecutor;

import java.util.HashMap;
import java.util.Map;

public interface GameEvent extends Identity, Listener {

    Map<Class<? extends Event>, GameEventExecutor<? extends Event>> DEFAULT_EVENTS = new HashMap<>();

    Component name();

    Component description();

    Title title();

    Sound sound();

    BossBar bossBar();

    int duration();

    default void begin() {

    }

    default void end() {

    }

    default Map<Class<? extends Event>, GameEventExecutor<? extends Event>> events() {
        return DEFAULT_EVENTS;
    }

    default EventPriority priority() {
        return EventPriority.NORMAL;
    }

    default boolean subtractDuration() {
        return false;
    }
}