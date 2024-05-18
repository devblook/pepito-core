package team.devblook.pepitocore.plugin.module.back.command;

import com.google.inject.Inject;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import team.devblook.pepitocore.api.registry.TRegistry;
import team.devblook.pepitocore.plugin.module.back.LockedLocation;

import java.util.UUID;

@Command(names = "back")
public class GoBackCommand implements CommandClass {

    private @Inject TRegistry<UUID, LockedLocation> locations;

    @Command(names = "")
    public void main(@Sender Player player) {
        LockedLocation back = locations.find(player.getUniqueId());
        if (back == null) {
            player.sendMessage("No se ha encontrado ningún punto de muerte reciente, probablemente ya expiró.");
            return;
        }

        PlayerInventory inventory = player.getInventory();

        ItemStack hand = inventory.getItemInMainHand();
        if (hand.getType() != back.type() || hand.getAmount() < back.amount()) {
            player.sendMessage("Necesitas " + back.amount() + "x " + back.type() + " en la mano como pago para enviarte a tu último punto de muerte.");
            return;
        }

        locations.delete(player.getUniqueId());

        Location location = back.location().toLocation();
        if (location == null) {
            player.sendMessage("El  mundo en que se encontraba tu último punto de muerte ya no existe.");
            return;
        }

        inventory.setItemInMainHand(new ItemStack(back.type(), hand.getAmount() - back.amount()));
        player.teleport(location);

        player.sendMessage("Has sido enviado a tu último punto de muerte.");
    }
}