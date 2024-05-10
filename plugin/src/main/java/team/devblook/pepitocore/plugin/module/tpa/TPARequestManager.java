package team.devblook.pepitocore.plugin.module.tpa;

import com.google.common.collect.Multimap;
import org.bukkit.entity.Player;
import team.devblook.pepitocore.plugin.module.tpa.model.TPARequest;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;
import java.util.UUID;

@Singleton
public class TPARequestManager {

    @Inject
    private Multimap<UUID, TPARequest> requests;

    public void send(Player from, Player to) {
        TPARequest request = new TPARequest(from, to);

        Collection<TPARequest> sent = requests.get(to.getUniqueId());
        if (sent.contains(request)) {
            from.sendMessage("You've already sent a TPA request to '" + to.getName() + "'!");
            return;
        }

        sent.add(request);

        from.sendMessage("TPA request sent to '" + to.getName() + "'!");
        to.sendMessage("TPA request received from '" + from.getName() + "'!");
    }

    public void accept(Player receiver, Player from) {
        TPARequest request = new TPARequest(from, receiver);

        Collection<TPARequest> received = requests.get(receiver.getUniqueId());
        if (!received.remove(request)) {
            receiver.sendMessage("You don't have any incoming TPA requests from '" + from.getName() + "'.");
            return;
        }

        from.teleport(receiver.getLocation());
        from.sendMessage("Your TPA request to '" + receiver.getName() + "' was accepted!");
        receiver.sendMessage("TPA request from '" + from.getName() + "' accepted!");
    }

    public void cancel(Player from, Player to) {
        TPARequest request = new TPARequest(from, to);

        Collection<TPARequest> sent = requests.get(to.getUniqueId());
        if (!sent.remove(request)) {
            from.sendMessage("You haven't sent a TPA request to '" + to.getName() + "'!");
            return;
        }

        from.sendMessage("TPA request to '" + to.getName() + "' cancelled!");
        to.sendMessage("The TPA request from '" + from.getName() + "' was cancelled!");
    }

    public void deny(Player receiver, Player from) {
        TPARequest request = new TPARequest(from, receiver);

        Collection<TPARequest> received = requests.get(receiver.getUniqueId());
        if (!received.remove(request)) {
            return;
        }

        from.sendMessage("Your TPA request to '" + receiver.getName() + "' was denied!");
        receiver.sendMessage("Denied TPA request from '" + from.getName() + "'!");
    }
}