package me.jacobtread.holograms.impl.commands;

import me.jacobtread.holograms.impl.Holograms;
import me.jacobtread.holograms.utils.Hologram;
import org.bukkit.entity.Player;

import java.util.Collections;

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
 * Class was created on: 20/10/18 at 9:07 AM.
 * -
 */
public class CreateCommand implements HoloCommand {
    /**
     * Used to create a hologram
     *
     * @param args   A list of arguments passed through by MainCommand
     * @param sender The player who ran the command
     */
    @Override
    public void run(String[] args, Player sender) {
        if (MainCommand.GET.lengthCheck(sender, args, 3, " create {name} {text}"))
            return;
        String name = args[1];
        Holograms.GET.create(new Hologram(name), sender.getLocation().add(0, -0.25, 0), Collections.singletonList(MainCommand.GET.buildArgs(args, 2)));
        sender.sendMessage(Holograms.GET.cmdPrefix + "Created " + name);
    }

    @Override
    public String name() {
        return "create";
    }

    @Override
    public String syntax() {
        return "create {name} {text}";
    }
}
