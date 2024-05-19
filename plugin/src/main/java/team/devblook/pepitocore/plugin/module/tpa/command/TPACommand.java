package team.devblook.pepitocore.plugin.module.tpa.command;

import com.google.inject.Inject;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.ArgOrSub;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import team.devblook.pepitocore.plugin.module.tpa.TPARequestManager;

@Command(names = "tpa")
@ArgOrSub(value = true)
public class TPACommand implements CommandClass {

    private @Inject TPARequestManager requestManager;

    @Command(names = "")
    public void send(@Sender Player player, OfflinePlayer target) {
        if (self(player, target)) {
            player.sendMessage(
                    Component.text("¿Por qué solicitarías TPA a ti mismo?", TextColor.fromHexString("#E7783C"))
            );
            return;
        }

        Player to = online(target);
        if (to == null) {
            player.sendMessage(
                    Component.text("El jugador " + target.getName() + " no está conectado.", TextColor.fromHexString("#E7783C"))
            );
            return;
        }

        requestManager.send(player, to);
    }

    @Command(names = "cancel")
    public void cancel(@Sender Player player, OfflinePlayer target) {
        if (self(player, target)) {
            player.sendMessage(
                    Component.text("Emm, ¿Si no puedes solicitarte un TPA, por qué crees que esto es lógico?", TextColor.fromHexString("#E7783C"))
            );
            return;
        }

        Player to = online(target);
        if (to == null) {
            player.sendMessage(Component.text("El jugador " + target.getName() + " no está conectado.", TextColor.fromHexString("#E7783C")));
            return;
        }

        requestManager.cancel(player, target.getPlayer());
    }

    @Command(names = "accept")
    public void accept(@Sender Player player, OfflinePlayer target) {
        if (self(player, target)) {
            player.sendMessage(
                    Component.text("Emm, ¿Si no puedes solicitarte un TPA, por qué crees que esto es lógico?", TextColor.fromHexString("#E7783C"))
            );
            return;
        }
        Player from = online(target);
        if (from == null) {
            player.sendMessage(Component.text("El jugador" + target.getName() + " no está conectado.", TextColor.fromHexString("#E7783C")));
            return;
        }

        requestManager.accept(player, from);
    }

    @Command(names = "deny")
    public void deny(@Sender Player player, OfflinePlayer target) {
        if (self(player, target)) {
            player.sendMessage(
                    Component.text("Emm, ¿Si no puedes solicitar un TPA a ti, porque crees que esto es lógico?", TextColor.fromHexString("#E7783C"))
            );
            return;
        }

        Player from = online(target);
        if (from == null) {
            player.sendMessage(Component.text("El jugador" + target.getName() + " no está conectado.", TextColor.fromHexString("#E7783C")));
            return;
        }

        requestManager.deny(player, from);
    }

    private boolean self(Player from, OfflinePlayer to) {
        return from.getName().equals(to.getName());
    }

    private Player online(OfflinePlayer player) {
        Player connected = player.getPlayer();
        if (!player.isOnline() || connected == null) {
            return null;
        }

        return connected;
    }
}