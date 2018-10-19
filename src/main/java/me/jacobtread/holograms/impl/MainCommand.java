package me.jacobtread.holograms.impl;

import me.jacobtread.holograms.api.Hologram;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;

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
 * Class was created on: 15/10/18 at 6:36 PM.
 * -
 */
public class MainCommand implements CommandExecutor {

    static final String cmdPrefix = DARK_GRAY + "[" + AQUA + "Holograms" + DARK_GRAY + "] " + RESET;

    /**
     * Executes the given command, returning its success
     *
     * @param sender  Source of the command
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    Passed command arguments
     * @return true if a valid command, otherwise false
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (args == null || args.length == 0) {

                sender.sendMessage(cmdPrefix + ChatColor.BOLD + ChatColor.GREEN + "Commands: ");
                sender.sendMessage(cmdPrefix + ChatColor.GRAY + "create {name} {text}");
                sender.sendMessage(cmdPrefix + ChatColor.GRAY + "delete {name}");
                sender.sendMessage(cmdPrefix + ChatColor.GRAY + "addLine {name} {text}");
                sender.sendMessage(cmdPrefix + ChatColor.GRAY + "setLine {name} {line} {text}");
                sender.sendMessage(cmdPrefix + ChatColor.GRAY + "removeLine {name} {line}");
            } else if (args[0].equalsIgnoreCase("create")) {
                if (lengthCheck((Player) sender, args, 3, " create {name} {text}"))
                    return true;
                String name = args[1];
                Holograms.GET.create(new Hologram(name), ((Player) sender).getLocation().add(0, -0.25, 0), Collections.singletonList(buildArgs(args, 2)));
                sender.sendMessage(cmdPrefix + "Created " + name);
            } else if (args[0].equalsIgnoreCase("delete")) {
                if (lengthCheck((Player) sender, args, 2, "delete {name}"))
                    return true;
                String name = args[1];
                Holograms.GET.delete(name, (Player) sender);
            } else if (args[0].equalsIgnoreCase("addline")) {
                if (lengthCheck((Player) sender, args, 3, "addline {name} {text}"))
                    return true;
                String name = args[1];
                Hologram hologram = Holograms.GET.find(name);
                if (hologram == null) {
                    sender.sendMessage(cmdPrefix + "Hologram not found!");
                } else {
                    hologram.addLine(buildArgs(args, 2));
                    sender.sendMessage(cmdPrefix + "Added a new line to " + hologram.getName());
                }
            } else if (args[0].equalsIgnoreCase("setline")) {
                if (lengthCheck((Player) sender, args, 4, "setline {name} {line} {text}"))
                    return true;
                try {
                    String name = args[1];
                    int index = Integer.parseInt(args[2]);
                    Hologram hologram = Holograms.GET.find(name);
                    if (hologram == null) {
                        sender.sendMessage(cmdPrefix + "Hologram not found!");
                    } else {
                        if (index < hologram.getLines().size()) {
                            String replacement = buildArgs(args, 3);
                            hologram.setLine(index, replacement);
                            sender.sendMessage(cmdPrefix + "Set line " + index + " of " + hologram.getName() + " to " + replacement);
                        } else
                            sender.sendMessage(cmdPrefix + "Line not found choose from (0 - " + (hologram.getLines().size() - 1) + ")");
                    }
                } catch (Exception e) {
                    sender.sendMessage(cmdPrefix + "Error please check that you provided a number!");
                }
            } else if (args[0].equalsIgnoreCase("removeline")) {
                if (lengthCheck((Player) sender, args, 3, "removeline {name} {line}"))
                    return true;
                try {
                    String name = args[1];
                    int index = Integer.parseInt(args[2]);
                    Hologram hologram = Holograms.GET.find(name);
                    if (hologram == null) {
                        sender.sendMessage(cmdPrefix + "Hologram not found!");
                    } else {
                        if (index < hologram.getLines().size()) {
                            hologram.removeLine(index);
                            sender.sendMessage(cmdPrefix + "Removed line " + index + " from " + hologram.getName());
                        } else
                            sender.sendMessage(cmdPrefix + "Line not found choose from (0 - " + (hologram.getLines().size() - 1) + ")");
                    }
                } catch (Exception e) {
                    sender.sendMessage(cmdPrefix + "Error please check that you provided a number!");
                }
            }
            return true;
        } else {
            sender.sendMessage("This command needs to be performed by a player!");
            return false;
        }
    }

    /**
     * @param sender  The player who called the command
     * @param args    An array of strings arguments
     * @param length  The needed length for the args
     * @param command A response to send if the length is to small
     * @return true if the length is correct false if it is not
     */
    private boolean lengthCheck(Player sender, String[] args, int length, String command) {
        if (args.length < length) {
            sender.sendMessage(cmdPrefix + "Missing Arguments, Usage:");
            sender.sendMessage(cmdPrefix + "/holograms " + command);
            return true;
        } else return false;
    }

    /**
     * Used to make the arguments a single string from a string array
     *
     * @param args A list of strings provided after the command
     * @return A completed string
     */
    private String buildArgs(String[] args, int start) {
        StringBuilder builder = new StringBuilder();
        for (int i = start; i < args.length; i++) {
            if (i != start)
                builder.append(" ");
            builder.append(args[i]);
        }
        return ChatColor.translateAlternateColorCodes('&', builder.toString());
    }
}
