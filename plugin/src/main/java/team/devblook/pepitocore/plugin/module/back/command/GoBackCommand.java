package team.devblook.pepitocore.plugin.module.back.command;

import com.google.inject.Inject;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.ArgOrSub;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import team.devblook.pepitocore.api.registry.TRegistry;
import team.devblook.pepitocore.plugin.module.back.model.LockedLocation;

import java.util.UUID;

import static team.devblook.pepitocore.plugin.module.back.UnlockMaterials.MATERIALS;
import static team.devblook.pepitocore.plugin.util.Randoms.RANDOM;

@Command(names = "back")
@ArgOrSub(value = true)
public class GoBackCommand implements CommandClass {

    private static final ItemStack ROLL_PRICE = new ItemStack(Material.COPPER_INGOT, 10);

    private @Inject TRegistry<UUID, LockedLocation> locations;

    @Command(names = "")
    public void execute(@Sender Player player) {
        LockedLocation back = locations.find(player.getUniqueId());
        if (back == null) {
            player.sendMessage(Component.text("No se ha encontrado ningún punto de muerte reciente, probablemente ya expiró.", TextColor.fromHexString("#E7783C")));
            return;
        }

        PlayerInventory inventory = player.getInventory();

        ItemStack hand = inventory.getItemInMainHand();
        if (hand.getType() != back.type() || hand.getAmount() < back.amount()) {
            player.sendMessage(Component.text(
                    "Necesitas " + back.amount() + "x " + back.type() + " en la mano como pago para enviarte a tu último punto de muerte.",
                    TextColor.fromHexString("#E7783C")
            ));
            return;
        }

        locations.delete(player.getUniqueId());

        Location location = back.location().toLocation();
        if (location == null) {
            player.sendMessage(Component.text(
                    "El  mundo en que se encontraba tu último punto de muerte ya no existe.",
                    TextColor.fromHexString("#E7783C")
            ));
            return;
        }

        hand.setAmount(hand.getAmount() - back.amount());
        inventory.setItemInMainHand(hand);

        player.teleport(location);

        player.sendMessage(Component.text(
                "¡Has sido enviado a tu último punto de muerte!",
                TextColor.fromHexString("#35BD30")
        ));
    }

    @Command(names = "roll")
    public void roll(@Sender Player player) {
        LockedLocation back = locations.find(player.getUniqueId());
        if (back == null) {
            player.sendMessage("No se ha encontrado ningún punto de muerte reciente, probablemente ya expiró.");
            return;
        }

        PlayerInventory inventory = player.getInventory();
        ItemStack hand = inventory.getItemInMainHand();
        if (hand.getType() != ROLL_PRICE.getType() || hand.getAmount() < ROLL_PRICE.getAmount()) {
            player.sendMessage(Component.text(
                    "Necesitas " + ROLL_PRICE.getAmount() + "x " + ROLL_PRICE.getType() + " en la mano como pago para cambiar el precio de pago y extender la duración.",
                    TextColor.fromHexString("#E7783C")
            ));
            return;
        }

        hand.setAmount(hand.getAmount() - ROLL_PRICE.getAmount());
        inventory.setItemInMainHand(hand);

        Material material = MATERIALS.get(RANDOM.nextInt(MATERIALS.size()));
        while (material == back.type()) { // ensure item is not the same as before
            material = MATERIALS.get(RANDOM.nextInt(MATERIALS.size()));
        }

        int amount = RANDOM.nextInt(material.getMaxStackSize() - 1) + 1;
        locations.insert(
                player.getUniqueId(),
                new LockedLocation(
                        material,
                        amount,
                        back.location()
                )
        );

        player.sendMessage(Component.text(
                "El precio para regresar a tu último punto de muerte ahora es de " + amount + "x " + material + ".",
                TextColor.fromHexString("#E7783C")
        ));
    }
}