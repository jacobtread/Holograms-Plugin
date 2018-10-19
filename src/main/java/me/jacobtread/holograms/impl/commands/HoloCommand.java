package me.jacobtread.holograms.impl.commands;

import org.bukkit.entity.Player;

/**
 * -
 *
 * @author Jacobtread#3770
 * -
 * Any of the following code used without permission
 * of Jacobtread. or stolen without knowledge
 * from me, makes you obligated for any
 * actions I may take against you, your client, or
 * anything you use the following code in.
 * -
 * For information on how to use this code with permission from
 * the creator, please contact the owner at the discord link above.
 * -
 * Class was created on: 20/10/18 at 9:10 AM.
 * -
 */
public interface HoloCommand {

    /**
     * @param args   A list of arguments passed through by MainCommand
     * @param sender The player who ran the command
     */
    void run(String[] args, Player sender);

    /**
     * @return The commands name (used for main command)
     */
    String name();

    /**
     * @return The commands syntax (used for main command)
     */
    String syntax();
}
