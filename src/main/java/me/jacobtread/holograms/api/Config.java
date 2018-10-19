package me.jacobtread.holograms.api;

import me.jacobtread.holograms.Plugin;
import me.jacobtread.holograms.impl.Holograms;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

/**
 * -
 *
 * @author Jacobtread#3770
 * -
 * Any of the following code used without permission
 * of Jacobtread. or stolen without knowledge
 * from me, makes you obligated for any
 * actions I may take against you, your plugin, or
 * anything you use the following code in.
 * -
 * For information on how to use this code with permission from
 * the creator, please contact the owner at the discord link above.
 * -
 * Class was created on: 15/10/18 at 7:33 PM.
 * -
 */
public class Config {

    private FileConfiguration config;
    private File file;
    private Plugin plugin;

    /**
     * Sets up the config by calling {@link Config#checkFile()}
     * to check / create need directories and files
     */
    public Config() {
        this.plugin = Holograms.GET.plugin;
        this.config = plugin.getConfig();
        this.checkFile();
    }

    /**
     * Used to load the plugin config and create
     * the needed holograms
     */
    public void load() {
        try {
            config.load(file);
            if (!config.contains("holograms")) return;
            ConfigurationSection holograms = config.getConfigurationSection("holograms");
            for (String name : holograms.getKeys(false)) {
                List<String> lines = holograms.getStringList(name + ".lines");
                String path = name + ".location.";
                Location location = null;
                if (holograms.contains(path + "x") && holograms.contains(path + "y") && holograms.contains(path + "z") && holograms.contains(path + "world")) {
                    double x = holograms.getDouble(path + "x");
                    double y = holograms.getDouble(path + "y");
                    double z = holograms.getDouble(path + "z");
                    World world = Bukkit.getWorld(holograms.getString(path + "world"));
                    if (world == null) {
                        plugin.getLogger().log(Level.SEVERE, "An error occurred when trying to load config file: could not find world for " + name);
                        return;
                    }
                    location = new Location(world, x, y, z);
                } else {
                    plugin.getLogger().log(Level.SEVERE, "An error occurred when trying to load config file: Missing location");
                }
                if (location != null) {
                    Hologram hologram = new Hologram(name);
                    Holograms.GET.create(hologram, location, lines);
                }
            }
        } catch (IOException | InvalidConfigurationException e) {
            plugin.getLogger().log(Level.SEVERE, "An error occurred when trying to load config file: " + e.getMessage());
        }
    }

    /**
     * Used to save the plugin config which contains
     * the locations and text of the holograms
     */
    public void save(List<Hologram> holograms) {
        try {
            if (holograms == null || holograms.isEmpty())
                return;
            for (Hologram hologram : holograms) {
                String path = "holograms." + hologram.getName();
                config.set(path + ".lines", hologram.getLines());
                if (hologram.getLocation() != null) {
                    Location location = hologram.getLocation();
                    String pathLocation = ".location.";
                    config.set(path + pathLocation + "x", location.getX());
                    config.set(path + pathLocation + "y", location.getY());
                    config.set(path + pathLocation + "z", location.getZ());
                    config.set(path + pathLocation + "world", location.getWorld().getName());
                }
            }
            config.save(file);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "An error occurred when trying to save config file: " + e.getMessage());
        }
    }

    /**
     * Checks if the config directory (plugins/Holograms)
     * and if it does not exist it creates the directories and
     * the config file
     */
    private void checkFile() {
        file = new File(plugin.getDataFolder() + "/config.yml");
        if (file.exists()) return;
        boolean madeFiles = file.getParentFile().mkdirs();
        if (madeFiles)
            plugin.getLogger().log(Level.FINE, "Created missing config dir and created config");
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
