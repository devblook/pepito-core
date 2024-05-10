package team.devblook.pepitocore.plugin.module.tpa.model;

import org.bukkit.entity.Player;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class TPARequest {

    private final UUID from;
    private final UUID to;

    private final long elapsed;

    public TPARequest(Player requester, Player target) {
        this.from = requester.getUniqueId();
        this.to = target.getUniqueId();

        this.elapsed = new Date().toInstant().toEpochMilli();
    }

    public UUID from() {
        return from;
    }

    public UUID to() {
        return to;
    }

    public long elapsed() {
        return elapsed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TPARequest that = (TPARequest) o;

        if (!Objects.equals(from, that.from)) return false;
        return Objects.equals(to, that.to);
    }

    @Override
    public int hashCode() {
        int result = from != null ? from.hashCode() : 0;
        result = 31 * result + (to != null ? to.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TPARequest{" +
                "from=" + from +
                ", to=" + to +
                ", elapsed=" + elapsed +
                '}';
    }
}