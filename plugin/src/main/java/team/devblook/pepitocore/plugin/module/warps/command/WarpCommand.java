package team.devblook.pepitocore.plugin.module.warps.command;

import com.google.inject.Inject;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.ArgOrSub;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import team.devblook.pepitocore.plugin.module.warps.WarpManager;
import team.devblook.pepitocore.plugin.module.warps.model.Warp;

import java.util.Collection;

@Command(names = "warp")
@ArgOrSub(value = true)
public class WarpCommand implements CommandClass {

    private @Inject WarpManager warpManager;

    @Command(names = "")
    public void teleport(@Sender Player player, String name) {
        warpManager.teleport(player, name);
    }

    @Command(names = "create")
    public void create(@Sender Player player, String name) {
        warpManager.create(player, name);
    }

    @Command(names = "remove")
    public void remove(@Sender Player player, String name) {
        warpManager.remove(player, name);
    }

    @Command(names = "list")
    public void list(@Sender Player player) {
        Collection<Warp> warps = warpManager.list(player);
        if (warps.size() == 0) {
            player.sendMessage(Component.text()
                    .append(Component.text("WTF, bro no tienes warps", TextColor.fromHexString("#E7783C")))
            );
            return;
        }

        for (Warp warp : warps) {
            player.sendMessage(Component.text()
                    .append(Component.text("    Warp: " + warp.id(), TextColor.fromHexString("#B3FFF2")))
                    .append(Component.text(warp.id(), TextColor.fromHexString("#F3DD22")))
                    .append(Component.text(" | Ubicación: " + warp.pos(), TextColor.fromHexString("#B3FFF2")))
                    .append(Component.text(String.valueOf(warp.pos()), TextColor.fromHexString("#F3DD22")))
                    .build()
            );
        }
    }
}
