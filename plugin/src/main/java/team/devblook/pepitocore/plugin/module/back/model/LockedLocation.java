package team.devblook.pepitocore.plugin.module.back.model;

import org.bukkit.Material;
import team.devblook.pepitocore.plugin.BlockLocation;

public record LockedLocation(Material type, int amount, BlockLocation location) { }