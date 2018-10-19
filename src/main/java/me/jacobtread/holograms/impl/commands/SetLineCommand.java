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
public class SetLineCommand implements HoloCommand {
    /**
     * Used to set the text of a line in a hologram
     *
     * @param args   A list of arguments passed through by MainCommand
     * @param sender The player who ran the command
     */
    @Override
    public void run(String[] args, Player sender) {
        if (MainCommand.GET.lengthCheck(sender, args, 4, "setline {name} {line} {text}"))
            return;
        try {
            String name = args[1];
            int index = Integer.parseInt(args[2]);
            Hologram hologram = Holograms.GET.find(name);
            if (hologram == null) {
                sender.sendMessage(Holograms.GET.cmdPrefix + "Hologram not found!");
            } else {
                if (index < hologram.getLines().size()) {
                    String replacement = MainCommand.GET.buildArgs(args, 3);
                    hologram.setLine(index, replacement);
                    sender.sendMessage(Holograms.GET.cmdPrefix + "Set line " + index + " of " + hologram.getName() + " to " + replacement);
                } else
                    sender.sendMessage(Holograms.GET.cmdPrefix + "Line not found choose from (0 - " + (hologram.getLines().size() - 1) + ")");
            }
        } catch (Exception e) {
            sender.sendMessage(Holograms.GET.cmdPrefix + "Error please check that you provided a number!");
        }
    }

    @Override
    public String name() {
        return "setline";
    }

    @Override
    public String syntax() {
        return "setline {name} {line} {text}";
    }
}
