package vitaminmoo.InvZones;

//import java.util.logging.Logger;

import org.bukkit.World;
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
            World fromWorld = event.getFrom().getWorld();
            World toWorld = event.getTo().getWorld();

            //log.info(player.getName() + " teleported from " + fromWorld.getName() + " to " + toWorld.getName());

            // if the player teleported between worlds, then switch inventories
            if(!fromWorld.getName().equalsIgnoreCase(toWorld.getName())){
                InvZones.saveInventory(player, fromWorld);
                InvZones.clearInventory(player);
                InvZones.loadInventory(player, toWorld);
            }
        }
}

