package team.devblook.pepitocore.plugin.module.chat;

import io.papermc.paper.chat.ChatRenderer;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class UserChatRenderer implements ChatRenderer {

    @Override
    public @NotNull Component render(
            @NotNull final Player source,
            @NotNull final Component sourceDisplayName,
            @NotNull final Component message,
            @NotNull final Audience viewer
    ) {
        // final TagResolver.Single playerPlaceholder = Placeholder.component("player", sourceDisplayName);
        //final TagResolver.Single messagePlaceholder = Placeholder.component("message", message);

        return Component.text()
                .append(sourceDisplayName)
                .append(Component.text(" » ", TextColor.color(0xAAAAAA)))
                .append(message)
                .build();
    }
}