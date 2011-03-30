package vitaminmoo.InvZones;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * InvZones for Bukkit
 *
 * @author vitaminmoo
 */
public class InvZones extends JavaPlugin {
    private final InvZonesPlayerListener playerListener = new InvZonesPlayerListener(this);

    public void onEnable() {

        // Register our events
        PluginManager pm = getServer().getPluginManager();
       
        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
    }
    public void onDisable() {
        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is disabled" );
    }
}

