package team.devblook.pepitocore.plugin.module.chat;

import com.google.inject.Inject;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import team.devblook.pepitocore.api.module.CoreModule;
import team.devblook.pepitocore.plugin.module.chat.listener.UserChatListener;

public class ChatModule implements CoreModule {

    private @Inject UserChatListener listener;
    private @Inject Plugin plugin;

    @Override
    public void enable() {
        Bukkit.getPluginManager().registerEvents(
                listener,
                plugin
        );
    }

    @Override
    public void disable() {
        HandlerList.unregisterAll(listener);
    }
}
