package vitaminmoo.InvZones;

//import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerTeleportEvent;

/**
 * Handle events for all Player related events
 * @author vitaminmoo
 */
public class InvZonesPlayerListener extends PlayerListener {
//    private final InvZones plugin;

    public InvZonesPlayerListener(InvZones instance) {
//        plugin = instance;
    }

    @Override
        public void onPlayerTeleport(PlayerTeleportEvent event) {
//            Logger log = Logger.getLogger("Minecraft");
            Player player = event.getPlayer();
            String fromZone = InvZones.getWorldZone((event.getFrom().getWorld().getName()));
            String toZone = InvZones.getWorldZone((event.getTo().getWorld().getName()));

            //log.info(player.getName() + " teleported from " + fromWorld.getName() + " to " + toWorld.getName());

            // if the player teleported between zones, then switch inventories
            if(!fromZone.equalsIgnoreCase(toZone)){
                InvZones.saveInventory(player, fromZone);
                InvZones.clearInventory(player);
                InvZones.loadInventory(player, toZone);
            }
        }
}

