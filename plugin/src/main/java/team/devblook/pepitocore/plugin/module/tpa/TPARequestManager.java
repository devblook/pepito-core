package team.devblook.pepitocore.plugin.module.tpa;

import com.google.common.collect.Multimap;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import team.devblook.pepitocore.plugin.module.tpa.model.TPARequest;

import java.util.Collection;
import java.util.UUID;

@Singleton
public class TPARequestManager {

    private @Inject Multimap<UUID, TPARequest> requests;

    public void send(Player from, Player to) {
        TPARequest request = new TPARequest(from, to);

        Collection<TPARequest> sent = requests.get(to.getUniqueId());
        if (sent.contains(request)) {
            from.sendMessage(
                    Component.text("Ya enviaste una solicitud a '" + to.getName() + "'.", TextColor.fromHexString("#E7783C"))
            );
            return;
        }

        sent.add(request);

        from.sendMessage(
                Component.text("Enviaste solicitud de TPA a '" + to.getName() + "'.", TextColor.fromHexString("#35bd30"))
        );
        to.sendMessage(
                Component.text("Tienes una solicitud de TPA pendiente de '" + from.getName() + "'.", TextColor.fromHexString("#35bd30"))
        );
    }

    public void accept(Player receiver, Player from) {
        TPARequest request = new TPARequest(from, receiver);

        Collection<TPARequest> received = requests.get(receiver.getUniqueId());
        if (!received.remove(request)) {
            receiver.sendMessage(
                    Component.text("No tienes solicitudes pendientes de '" + from.getName() + "'.", TextColor.fromHexString("#E7783C"))
            );
            return;
        }

        from.teleport(receiver.getLocation());
        from.sendMessage(
                Component.text("La solicitud de '" + receiver.getName() + "' fue aceptada.", TextColor.fromHexString("#35bd30"))
        );
        receiver.sendMessage(
                Component.text("Aceptaste la solicitud de '" + from.getName() + "'.", TextColor.fromHexString("#35bd30"))
        );
    }

    public void cancel(Player from, Player to) {
        TPARequest request = new TPARequest(from, to);

        Collection<TPARequest> sent = requests.get(to.getUniqueId());
        if (!sent.remove(request)) {
            from.sendMessage(
                    Component.text("No enviaste solicitudes a '" + from.getName() + "'.", TextColor.fromHexString("#E7783C"))
            );
            return;
        }

        from.sendMessage(
                Component.text("La solicitud de '" + to.getName() + "' fue cancelada.", TextColor.fromHexString("#35bd30"))
        );
        to.sendMessage(
                Component.text("Cancelaste la solicitud de '" + from.getName() + "'.", TextColor.fromHexString("#35bd30"))
        );
    }

    public void deny(Player receiver, Player from) {
        TPARequest request = new TPARequest(from, receiver);

        Collection<TPARequest> received = requests.get(receiver.getUniqueId());
        if (!received.remove(request)) {
            return;
        }

        from.sendMessage(
                Component.text("Tu solicitud de '" + receiver.getName() + "' fue denegada.", TextColor.fromHexString("#35bd30"))
        );
        receiver.sendMessage(
                Component.text("Se denegó la solicitud de '" + from.getName() + "'.", TextColor.fromHexString("#35bd30"))
        );
    }
}