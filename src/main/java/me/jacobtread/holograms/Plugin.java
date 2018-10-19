package me.jacobtread.holograms;

import me.jacobtread.holograms.impl.Holograms;
import org.bukkit.plugin.java.JavaPlugin;

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
 * Class was created on: 15/10/18 at 6:28 PM.
 * -
 */
public final class Plugin extends JavaPlugin {

    /**
     * Runs when the server is started / when a
     * server reload is complete
     */
    @Override
    public void onEnable() {
        Holograms.GET.onEnable(this);
    }

    /**
     * Runs when the server is stopped / when a
     * server reload is started
     */
    @Override
    public void onDisable() {
        Holograms.GET.onDisable();
    }
}
