package vitaminmoo.InvZones;

import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

/**
 * Handle events for all Player related events
 * @author vitaminmoo
 */
public class InvZonesPlayerListener extends PlayerListener {
    private final InvZones plugin;

    public InvZonesPlayerListener(InvZones instance) {
        plugin = instance;
    }

    @Override
        public void onPlayerTeleport(PlayerTeleportEvent event) {
            Logger log = Logger.getLogger("Minecraft");
            Player player = event.getPlayer();
            log.info(player.getName() + " teleported from " + event.getFrom().getWorld().getName() + " to " + event.getTo().getWorld().getName());
        }
}

