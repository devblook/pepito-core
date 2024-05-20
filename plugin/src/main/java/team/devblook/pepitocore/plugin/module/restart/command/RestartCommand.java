package team.devblook.pepitocore.plugin.module.restart.command;

import com.google.inject.Inject;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.Named;
import me.fixeddev.commandflow.annotated.annotation.OptArg;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import team.devblook.pepitocore.plugin.module.restart.RestartRunnable;

@Command(names = "autorestart")
public class RestartCommand implements CommandClass {

    private @Inject RestartRunnable restartRunnable;

    private @Inject Plugin plugin;

    private int autoRestartTaskId = -1;

    @Command(names = "")
    public void main(@Sender CommandSender sender, @Named("time") @OptArg("0") int time) {
        if (autoRestartTaskId != -1) {
            sender.sendMessage("Ya se ha programado un reinicio.");
            return;
        }

        if (time < 1) {
            restartRunnable.run();
            return;
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage("Se ha programado un reinicio del servidor en " + time + " segundos.");
        }

        autoRestartTaskId = Bukkit.getScheduler().runTaskLater(
                plugin,
                restartRunnable,
                time * 20L
        ).getTaskId();
    }

    @Command(names = "cancel")
    public void cancel(@Sender CommandSender sender) {
        if (autoRestartTaskId == -1) {
            sender.sendMessage("No se ha programado ningún reinicio.");
            return;
        }

        Bukkit.getScheduler().cancelTask(autoRestartTaskId);

        autoRestartTaskId = -1;

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage("Se ha cancelado el reinicio.");
        }
    }
}