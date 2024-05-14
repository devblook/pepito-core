package team.devblook.pepitocore.plugin.module.warps;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class BlockPos {

    private final String worldName;
    private final int x;
    private final int y;
    private final int z;

    public BlockPos(String worldName, int x, int y, int z) {
        this.worldName = worldName;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Location toLocation() {
        return new Location(Bukkit.getWorld(worldName), x, y, z);
    }

    public static BlockPos of(Location location) {
        return new BlockPos(
                location.getWorld().getName(),
                location.getBlockX(),
                location.getBlockY(),
                location.getBlockZ()
        );
    }

    public static BlockPos from(String s) {
        String[] parts = s.split(" ");
        return new BlockPos(
                parts[0],
                Integer.parseInt(parts[1]),
                Integer.parseInt(parts[2]),
                Integer.parseInt(parts[3])
        );
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", x, y, z);
    }
}
