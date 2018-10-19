package me.jacobtread.holograms.utils;

import me.jacobtread.holograms.impl.Holograms;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * Class was created on: 15/10/18 at 6:30 PM.
 * -
 */
public class Hologram {

    private String name;
    private Map<String, ArmorStand> armorStands;
    private Location initialLocation;
    private List<String> lines;

    /**
     * @param name The holograms name (Needed for storage)
     */
    public Hologram(String name) {
        this.name = name;
        this.armorStands = new HashMap<>();
        this.lines = new ArrayList<>();
    }

    /**
     * Updates the entities name to match the text
     * provided
     */
    public void onUpdate() {
        if (armorStands == null || armorStands.isEmpty()) return;
        armorStands.forEach((line, stand) -> stand.setCustomName(line));
    }

    /**
     * Used to destroy the armor-stand and clear the text
     * to be removed from holograms
     */
    public void destroy() {
        armorStands.values().forEach(Entity::remove);
    }

    /**
     * Sets up the armor stand for being a hologram
     * and creates a new entity if old one is not found
     *
     * @param initialLocation The starting location for all armorStands
     */
    public void setup(Location initialLocation) {
        if (this.armorStands == null) this.armorStands = new HashMap<>();
        this.lines.clear();
        this.armorStands.clear();
        this.initialLocation = initialLocation;
    }

    /**
     * @param index The location in the lines array the hologram is
     * @param line  The text for the holograms name-tag
     */
    private void setupEntity(int index, String line) {
        Location location = new Location(initialLocation.getWorld(), initialLocation.getX(), initialLocation.getY() + (index * -0.25), initialLocation.getZ());
        Entity found = findEntityAt(location, line);
        if (found == null) {
            World world = location.getWorld();
            found = world.spawnEntity(location, EntityType.ARMOR_STAND);
        }
        if (found instanceof ArmorStand) {
            ArmorStand entity = (ArmorStand) found;
            entity.setVisible(false);
            entity.setBasePlate(false);
            entity.setCustomNameVisible(true);
            entity.setGravity(false);
            entity.setCustomName(line);
            this.armorStands.put(line, entity);
        }
    }

    /**
     * Search a location for an entity of a specific type
     *
     * @param location The location to search for entities
     * @return Returns an entity if found otherwise returns null
     */
    private Entity findEntityAt(Location location, String name) {
        for (Entity entity : location.getChunk().getEntities()) {
            Location pos = entity.getLocation();
            if (pos.getX() >= location.getX() - 2 && pos.getY() >= location.getY() - 3 && pos.getZ() >= location.getZ() - 2) {
                if (pos.getX() <= location.getX() + 2 && pos.getY() <= location.getY() + 3 && pos.getZ() <= location.getZ() + 2) {
                    if (entity.getType().equals(EntityType.ARMOR_STAND) && entity.getCustomName() != null && ChatColor.stripColor(entity.getCustomName()).equalsIgnoreCase(ChatColor.stripColor(name)))
                        return entity;
                }
            }
        }
        return null;
    }

    /**
     * Used to add a new line of text to the name-tag
     *
     * @param text A string of text to be added as a line
     */
    public void addLine(String text) {
        if (lines == null) lines = new ArrayList<>();
        if (lines.contains(text)) return;
        this.lines.add(text);
        doLine(text);
        Holograms.GET.config.save(Holograms.GET.holograms);
    }

    /**
     * Loops the lines using an index until it finds the matching line
     * and using index for height
     *
     * @param text The text to find and setup entity at that index
     */
    private void doLine(String text) {
        int index = 0;
        for (String line : lines) {
            if (line.equalsIgnoreCase(text))
                setupEntity(index, text);
            index++;
        }
        Holograms.GET.config.save(Holograms.GET.holograms);
    }

    /**
     * Used to update / replace a line of the hologram
     *
     * @param number The armorStands index number in the {@link Hologram#armorStands} array
     * @param text   The string of text to replace the line with
     */
    public void setLine(int number, String text) {
        if (lines == null) return;
        if (number < lines.size()) {
            ArmorStand stand = armorStands.get(lines.get(number));
            armorStands.remove(lines.get(number));
            stand.setCustomName(text);
            lines.set(number, text);
            armorStands.put(text, stand);
            Holograms.GET.config.save(Holograms.GET.holograms);
        }
    }

    /**
     * @param number The armorStands index number in the {@link Hologram#armorStands} array
     */
    public void removeLine(int number) {
        if (lines != null && !lines.isEmpty() && lines.size() > number) {
            ArmorStand stand = armorStands.get(lines.get(number));
            stand.remove();
            armorStands.remove(lines.get(number));
            lines.remove(number);
            Holograms.GET.config.save(Holograms.GET.holograms);
        }
    }

    /**
     * Used to get the holograms name
     *
     * @return A string which is the name of the hologram (For storage)
     */
    public String getName() {
        return name;
    }

    /**
     * Used to get the holograms armorStands of text
     *
     * @return The a list of strings for the holograms text
     */
    public List<String> getLines() {
        return lines;
    }

    /**
     * Used to get the location for the hologram
     *
     * @return The location in which the hologram was placed
     */
    Location getLocation() {
        return initialLocation;
    }
}
