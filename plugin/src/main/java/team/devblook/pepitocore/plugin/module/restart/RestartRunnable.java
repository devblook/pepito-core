package team.devblook.pepitocore.plugin.module.restart;

import com.google.inject.Singleton;
import org.bukkit.Bukkit;
import org.bukkit.World;

@Singleton
public class RestartRunnable implements Runnable {

    @Override
    public void run() {
        for (World world : Bukkit.getWorlds()) {
            world.save();
        }

        Bukkit.getServer().shutdown();

        System.out.println("restarting");
    }
}