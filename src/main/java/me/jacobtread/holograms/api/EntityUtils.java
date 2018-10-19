package me.jacobtread.holograms.api;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.Collection;

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
 * Class was created on: 15/10/18 at 6:45 PM.
 * -
 */
public enum EntityUtils {
    GET;

    /**
     * Search a location for an entity of a specific type
     *
     * @param location The location to search for entities
     * @param type     The type of entity to look for
     * @return Returns an entity if found otherwise returns null
     */
    public Entity findEntityAt(Location location, EntityType type, String name) {
        Collection<Entity> nearbyEntities = location.getWorld().getNearbyEntities(location, 2, 3, 2);
        for (Entity entity : nearbyEntities) {
            if (entity.getType().equals(type) && entity.getCustomName() != null && ChatColor.stripColor(entity.getCustomName()).equalsIgnoreCase(ChatColor.stripColor(name)))
                return entity;
        }
        return null;
    }
}
