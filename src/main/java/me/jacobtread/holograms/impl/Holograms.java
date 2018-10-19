package me.jacobtread.holograms.impl;

import me.jacobtread.holograms.Plugin;
import me.jacobtread.holograms.impl.commands.MainCommand;
import me.jacobtread.holograms.utils.Config;
import me.jacobtread.holograms.utils.Hologram;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static org.bukkit.ChatColor.*;

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
 * Class was created on: 15/10/18 at 6:29 PM.
 * -
 */
public enum Holograms {
    GET;

    public final String cmdPrefix = DARK_GRAY + "[" + AQUA + "Holograms" + DARK_GRAY + "] " + RESET;
    public double version = 2.1;
    public Plugin plugin;
    public List<Hologram> holograms;
    public Config config;

    /**
     * Called in {@link Plugin#onEnable}
     * runs when the server starts / reload is complete
     *
     * @param plugin Provides the main plugin class to allow its use
     */
    public void onEnable(Plugin plugin) {
        this.plugin = plugin;
        this.plugin.getCommand("hologram").setExecutor(MainCommand.GET);
        MainCommand.GET.setup();
        this.holograms = new ArrayList<>();
        this.config = new Config();
        this.config.load();
        // Bukkit repeating task setup. Period is in ticks (20 ticks = 1 Second)
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this::onUpdate, 0, 20);
    }

    /**
     * A method invoked every second to update the hologram
     * entities
     */
    public void onUpdate() {
        if (holograms == null || holograms.isEmpty()) return;
        for (Hologram hologram : holograms)
            hologram.onUpdate();
    }

    /**
     * Called in {@link Plugin#onDisable}
     * runs when the server shuts down / reloads
     */
    public void onDisable() {
        this.config.save(holograms);
    }


    /**
     * Add a hologram to the list, set it up and then save the config
     *
     * @param hologram A hologram to add to the holograms
     * @param location A location for the hologram to be placed
     */
    public void create(Hologram hologram, Location location, List<String> lines) {
        hologram.setup(location);
        for (String line : lines)
            hologram.addLine(line);
        this.holograms.add(hologram);
        this.config.save(holograms);
    }

    /**
     * uses {@link Holograms#find(String)} to loop the holograms
     * until it finds one with the matching name then destroys
     * it, removes it from the ArrayList and saves config
     *
     * @param name The name of the hologram to delete
     */
    public void delete(String name, Player player) {
        Hologram hologram = find(name);
        if (hologram == null) return;
        hologram.destroy();
        this.holograms.remove(hologram);
        this.config.save(holograms);
        player.sendMessage(cmdPrefix + "Deleted " + hologram.getName());
    }

    /**
     * Loops through all holograms to find one with a matching name
     * then returns it
     *
     * @param name The name of the hologram to search for
     * @return null if no holograms is found or returns a hologram
     */
    public Hologram find(String name) {
        if (holograms == null || holograms.isEmpty()) return null;
        for (Hologram hologram : holograms) {
            if (hologram.getName().equalsIgnoreCase(name))
                return hologram;
        }
        return null;
    }
}
