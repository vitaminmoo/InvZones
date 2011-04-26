package vitaminmoo.InvZones;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;
import org.yaml.snakeyaml.Yaml;


/**
 * InvZones for Bukkit
 *
 * @author vitaminmoo
 */
public class InvZones extends JavaPlugin {
    private final InvZonesPlayerListener playerListener = new InvZonesPlayerListener(this);

    public static String leavingMsg = "§fLeaving zone §c%z§f. Inventory saved.";
    public static String enteringMsg = "§fEntering zone §a%z§f. Inventory loaded.";

    private static HashMap<String, String> WorldsToZonesMap = new HashMap<String, String>();

    private static Logger log;
    private static Configuration config;

    private static File dataFolder;

    public void onEnable() {
        config = this.getConfiguration();
        log = Logger.getLogger("Minecraft");

        // Register our events
        PluginManager pm = getServer().getPluginManager();

        PluginDescriptionFile pdfFile = this.getDescription();
        log.info( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );

        reloadConfig();

        dataFolder = this.getDataFolder();

        pm.registerEvent(Event.Type.PLAYER_TELEPORT, playerListener, Priority.Normal, this);
    }

    @SuppressWarnings("unchecked")
	public static void reloadConfig() {
        config.load();
        leavingMsg = config.getString("leaving-message", leavingMsg);
        enteringMsg = config.getString("entering-message", enteringMsg);

        WorldsToZonesMap = (HashMap<String, String>)config.getProperty("worlds");
        if(WorldsToZonesMap == null){
        	WorldsToZonesMap = new HashMap<String, String>();
        	WorldsToZonesMap.put("world", "default");
        }
        else if(WorldsToZonesMap.size() == 0)
        	WorldsToZonesMap.put("world", "default");
        
        saveConfig();
    }
    public static void saveConfig() {
        config.setProperty("leaving-message", leavingMsg);
        config.setProperty("entering-message", enteringMsg);
        config.setProperty("worlds", WorldsToZonesMap);
        config.save();
    }

    public void onDisable() {
        PluginDescriptionFile pdfFile = this.getDescription();
        log.info( pdfFile.getName() + " version " + pdfFile.getVersion() + " is disabled" );
    }

    public static void clearInventory(Player p){
        // clear the inventory
        p.getInventory().clear();
        p.getInventory().setBoots(null);
        p.getInventory().setChestplate(null);
        p.getInventory().setHelmet(null);
        p.getInventory().setLeggings(null);
    }

    // create a hash from an itemstack
    private static HashMap<String, Object> makeInvEntry(ItemStack i){
        HashMap<String, Object> invEntry = new HashMap<String, Object>();

        if(i != null){
            invEntry.put("amount", i.getAmount());
            invEntry.put("durability", (int)i.getDurability());
            invEntry.put("type", i.getTypeId());
            return invEntry;
        }
        else return null;
    }

    // serialize inventory to file
    public static void saveInventory(Player player, String zone){
        try {
            PlayerInventory inv = player.getInventory();
            ItemStack[] invContents = inv.getContents();

            // build an ArrayList from the inventory
            HashMap<Integer, HashMap<String, Object>> invList = new HashMap<Integer, HashMap<String, Object>>();
            HashMap<String, Object> invEntry;

            invEntry = makeInvEntry(inv.getBoots());
            if(invEntry != null) invList.put(-1, invEntry);

            invEntry = makeInvEntry(inv.getChestplate());
            if(invEntry != null) invList.put(-2, invEntry);

            invEntry = makeInvEntry(inv.getHelmet());
            if(invEntry != null) invList.put(-3, invEntry);

            invEntry = makeInvEntry(inv.getLeggings());
            if(invEntry != null) invList.put(-4, invEntry);

            for(int x=0; x<invContents.length; x++){
                invEntry = makeInvEntry(invContents[x]);
                if(invEntry != null)
                    invList.put(x, invEntry);
            }

            Yaml yaml = new Yaml();
            File invFile = new File(dataFolder, "inv-" + player.getName().toLowerCase() + "-" + zone.toLowerCase() + ".yml");
            FileOutputStream fos = new FileOutputStream(invFile);
            OutputStreamWriter out = new OutputStreamWriter(fos);
            out.write(yaml.dump(invList));
            out.close();
            fos.close();
            
            // tell the player what happened
            player.sendMessage(leavingMsg.replaceAll("%z", zone));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // make an itemstack from a hash
    private static ItemStack makeItemStack(HashMap<String, Object> entry){

        try{
            Integer type = (Integer)entry.get("type");
            Integer amount = (Integer)entry.get("amount");
            short durability = ((Integer)entry.get("durability")).shortValue();

            if(type != 0){
                ItemStack is = new ItemStack(type, amount, durability);
                return is;
            }
            else
                return null;

        }
        catch(Exception ex){
            return null;
        }
    }

    // deserialize inventory from file
    @SuppressWarnings("unchecked")
    public static void loadInventory(Player player, String zone){
        try {
            Yaml yaml = new Yaml();
            File invFile = new File(dataFolder, "inv-" + player.getName().toLowerCase() + "-" + zone.toLowerCase() + ".yml");
            if (invFile.exists()){
                FileInputStream fis = new FileInputStream(invFile);
                HashMap<Integer, HashMap<String, Object>> invList = (HashMap<Integer, HashMap<String, Object>>)yaml.load(fis);
                PlayerInventory inv = player.getInventory();
                ItemStack is;

                is = makeItemStack(invList.get(-1));
                if(is != null) inv.setBoots(is);

                is = makeItemStack(invList.get(-2));
                if(is != null) inv.setChestplate(is);

                is = makeItemStack(invList.get(-3));
                if(is != null) inv.setHelmet(is);

                is = makeItemStack(invList.get(-4));
                if(is != null) inv.setLeggings(is);


                for(int x=0; x<inv.getContents().length; x++){
                    if(invList.containsKey(x)){
                        is = makeItemStack(invList.get(x));
                        if(is != null)
                            inv.setItem(x, is);
                    }
                }

                // tell the player what happened
                player.sendMessage(enteringMsg.replaceAll("%z", zone));
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

	public static String getWorldZone(String world){
		if(!WorldsToZonesMap.containsKey(world)){
			WorldsToZonesMap.put(world, "default");
			saveConfig();
		}
		
		return WorldsToZonesMap.get(world);
	}
}