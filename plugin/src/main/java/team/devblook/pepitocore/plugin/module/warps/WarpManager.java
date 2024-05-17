package team.devblook.pepitocore.plugin.module.warps;

import com.google.common.collect.Multimap;
import com.google.inject.Inject;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import team.devblook.pepitocore.plugin.module.warps.model.Warp;

import java.util.Collection;
import java.util.UUID;

public class WarpManager {

    private @Inject Multimap<UUID, Warp> warps;

    public void create(Player player, String name) {
        Collection<Warp> owning = warps.get(player.getUniqueId());

        Warp warp = new Warp(name, player.getUniqueId().toString(), BlockPos.of(player.getLocation()));
        if (owning.contains(warp)) {
            player.sendMessage(Component.text()
                    .append(Component.text("El Warp '" + name + "' ya existe", TextColor.fromHexString("#E7783C")))
            );
            return;
        }

        owning.add(warp);
        player.sendMessage(Component.text()
                .append(Component.text("El warp '" + name + "' fue creado en: " + warp.pos(), TextColor.fromHexString("#35bd30")))
        );
    }

    public void remove(Player player, String name) {
        Collection<Warp> owning = warps.get(player.getUniqueId());

        if (!owning.removeIf(warp -> warp.id().equals(name) && warp.owner().equals(player.getUniqueId().toString()))) {
            player.sendMessage(Component.text()
                    .append(Component.text("El Warp '" + name + "' no existe", TextColor.fromHexString("#E7783C")))
            );
            return;
        }

        player.sendMessage(Component.text()
                .append(Component.text("El warp '" + name + "' fue creado eliminado.", TextColor.fromHexString("#35bd30")))
        );
    }

    public Collection<Warp> list(Player player) {
        return warps.get(player.getUniqueId());
    }

    public void teleport(Player player, String name) {
        Warp warp = warps.get(player.getUniqueId())
                .stream()
                .filter(search -> search.id().equals(name) && search.owner().equals(player.getUniqueId().toString()))
                .findFirst()
                .orElse(null);

        if (warp == null) {
            player.sendMessage(Component.text()
                    .append(Component.text("WTF, el warp '" + name + "' no existe.", TextColor.fromHexString("#E7783C")))
            );
            return;
        }

        player.teleport(warp.pos().toLocation());
        player.sendMessage(Component.text()
                .append(Component.text("Enviandote al warp '" + name + "'", TextColor.fromHexString("#35bd30")))
        );
    }
}
