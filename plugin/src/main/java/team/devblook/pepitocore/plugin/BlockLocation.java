package team.devblook.pepitocore.plugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record BlockLocation(String world, double x, double y, double z) {

    public @Nullable Location toLocation() {
        World target = Bukkit.getWorld(world);
        if (target == null) {
            return null;
        }

        return new Location(
                Bukkit.getWorld(world),
                x,
                y,
                z
        );
    }

    public static @NotNull BlockLocation fromLocation(Location location) {
        return new BlockLocation(
                location.getWorld().getName(),
                location.x(),
                location.y(),
                location.z()
        );
    }
}