package vitaminmoo.InvZones;

import java.util.logging.Logger;

import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;


/**
 * InvZones for Bukkit
 *
 * @author vitaminmoo
 */
public class InvZones extends JavaPlugin {
    private final InvZonesPlayerListener playerListener = new InvZonesPlayerListener(this);

    private static String clearedMsg = "Your inventory has been cleared";

    private static Logger log;
    private Configuration config;

    public void onEnable() {

        config = this.getConfiguration();
        log = Logger.getLogger("Minecraft");

        // Register our events
        PluginManager pm = getServer().getPluginManager();

        PluginDescriptionFile pdfFile = this.getDescription();
        log.info( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );

        this.reloadConfig();

        pm.registerEvent(Event.Type.PLAYER_TELEPORT, playerListener, Priority.Normal, this);

    }

    public void reloadConfig() {
        config.load();
        clearedMsg = config.getString("cleared-message", clearedMsg);
        saveConfig();
    }
    public void saveConfig() {
        config.setProperty("cleared-message", clearedMsg);
        config.save();
    }

    public void onDisable() {
        PluginDescriptionFile pdfFile = this.getDescription();
        log.info( pdfFile.getName() + " version " + pdfFile.getVersion() + " is disabled" );
    }
}

