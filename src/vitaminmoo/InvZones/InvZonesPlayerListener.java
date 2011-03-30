package vitaminmoo.InvZones;

import org.bukkit.event.player.PlayerListener;

/**
 * Handle events for all Player related events
 * @author vitaminmoo
 */
public class InvZonesPlayerListener extends PlayerListener {
    private final InvZones plugin;

    public InvZonesPlayerListener(InvZones instance) {
        plugin = instance;
    }
}

