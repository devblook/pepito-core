package team.devblook.pepitocore.plugin.module.warps.model;

import team.devblook.pepitocore.api.Identity;
import team.devblook.pepitocore.plugin.BlockLocation;

import java.util.Objects;

public class Warp implements Identity {

    private final String name;
    private final String owner;

    private final BlockLocation pos;

    public Warp(String name, String owner, BlockLocation pos) {
        this.name = name;
        this.owner = owner;
        this.pos = pos;
    }

    @Override
    public String id() {
        return this.name;
    }

    public String owner() {
        return this.owner;
    }

    public BlockLocation pos() {
        return this.pos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Warp warp = (Warp) o;

        if (!Objects.equals(name, warp.name)) return false;
        if (!Objects.equals(owner, warp.owner)) return false;
        return Objects.equals(pos, warp.pos);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (pos != null ? pos.hashCode() : 0);
        return result;
    }
}