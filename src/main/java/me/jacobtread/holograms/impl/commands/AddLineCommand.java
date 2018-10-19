package me.jacobtread.holograms.impl.commands;

import me.jacobtread.holograms.impl.Holograms;
import me.jacobtread.holograms.utils.Hologram;
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
 * Class was created on: 20/10/18 at 9:07 AM.
 * -
 */
public class AddLineCommand implements HoloCommand {

    /**
     * Used for adding a line to a hologram
     *
     * @param args   A list of arguments passed through by MainCommand
     * @param sender The player who ran the command
     */
    @Override
    public void run(String[] args, Player sender) {
        if (MainCommand.GET.lengthCheck(sender, args, 3, "addline {name} {text}"))
            return;
        String name = args[1];
        Hologram hologram = Holograms.GET.find(name);
        if (hologram == null) {
            sender.sendMessage(Holograms.GET.cmdPrefix + "Hologram not found!");
        } else {
            hologram.addLine(MainCommand.GET.buildArgs(args, 2));
            sender.sendMessage(Holograms.GET.cmdPrefix + "Added a new line to " + hologram.getName());
        }
    }

    @Override
    public String name() {
        return "addline";
    }

    @Override
    public String syntax() {
        return "addline {name} {text}";
    }
}
