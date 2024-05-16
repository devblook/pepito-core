package team.devblook.pepitocore.plugin.module.chat.listener;

import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import team.devblook.pepitocore.plugin.module.chat.UserChatRenderer;

public class UserChatListener implements Listener {

    private static final UserChatRenderer USER_CHAT_RENDERER = new UserChatRenderer();

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onUserFormatChat(final AsyncChatEvent event) {
        event.renderer(USER_CHAT_RENDERER);
    }
}