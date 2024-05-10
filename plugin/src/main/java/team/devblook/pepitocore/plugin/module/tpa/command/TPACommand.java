package team.devblook.pepitocore.plugin.module.tpa.command;

import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.ArgOrSub;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import team.devblook.pepitocore.plugin.module.tpa.TPARequestManager;

import javax.inject.Inject;

@Command(names = "tpa")
@ArgOrSub(value = true)
public class TPACommand implements CommandClass {

    @Inject
    private TPARequestManager requestManager;

    @Command(names = "")
    public void send(@Sender Player player, OfflinePlayer target) {
        if (self(player, target)) {
            player.sendMessage("Why do you want to send a TPA request to yourself?");
            return;
        }

        Player to = online(target);
        if (to == null) {
            player.sendMessage("The player '" + target.getName() + "' is not online!");
            return;
        }

        requestManager.send(player, to);
    }

    @Command(names = "cancel")
    public void cancel(@Sender Player player, OfflinePlayer target) {
        if (self(player, target)) {
            player.sendMessage("Why do you want to send a TPA request to yourself?");
            return;
        }

        Player to = online(target);
        if (to == null) {
            player.sendMessage("The player '" + target.getName() + "' is not online!");
            return;
        }

        requestManager.cancel(player, target.getPlayer());
    }

    @Command(names = "accept")
    public void accept(@Sender Player receiver, OfflinePlayer target) {
        if (self(receiver, target)) {
            receiver.sendMessage("Why do you want to accept a TPA request to yourself?");
            return;
        }
        Player from = online(target);
        if (from == null) {
            receiver.sendMessage("The player '" + target.getName() + "' is not online!");
            return;
        }

        requestManager.accept(receiver, from);
    }

    @Command(names = "deny")
    public void deny(@Sender Player player, OfflinePlayer target) {
        if (self(player, target)) {
            player.sendMessage("Why do you want to deny a TPA request to yourself?");
            return;
        }

        Player from = online(target);
        if (from == null) {
            player.sendMessage("The player '" + target.getName() + "' is not online!");
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