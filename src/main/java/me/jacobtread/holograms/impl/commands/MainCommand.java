package me.jacobtread.holograms.impl.commands;

import me.jacobtread.holograms.impl.Holograms;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

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
public enum MainCommand implements CommandExecutor {

    GET;

    private Map<String[], HoloCommand> commands;

    /**
     * Adds all commands the the map along with their aliases for
     * use in-game
     */
    public void setup() {
        commands = new HashMap<>();
        commands.put(new String[]{"create", "new", "make", "+"}, new CreateCommand());
        commands.put(new String[]{"delete", "remove", "dl", "rm", "-"}, new DeleteCommand());
        commands.put(new String[]{"addline", "al", "newline", "++"}, new AddLineCommand());
        commands.put(new String[]{"setline", "sl", "replaceline", "="}, new SetLineCommand());
        commands.put(new String[]{"removeline", "rl", "deleteline", "--"}, new RemoveLineCommand());
    }

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
                String text = "" + ChatColor.AQUA + ChatColor.BOLD + "Holograms v" + Holograms.GET.version;
                StringBuilder buffer = new StringBuilder();
                for (int i = 0; i < 32 - (text.length()); i++)
                    buffer.append(" ");
                sender.sendMessage(buffer.toString() + text);
                sender.sendMessage(ChatColor.DARK_GRAY + "================================");
                commands.forEach((aliases, cmd) -> sender.sendMessage(" " + ChatColor.GRAY + WordUtils.capitalizeFully(cmd.name()) + ChatColor.DARK_GRAY + ChatColor.BOLD + " >> " + ChatColor.GRAY + cmd.syntax()));
                sender.sendMessage(ChatColor.DARK_GRAY + "================================");
            } else {
                AtomicBoolean found = new AtomicBoolean(false);
                commands.forEach((aliases, cmd) -> {
                    for (String alias : aliases) {
                        if (args[0].equalsIgnoreCase(alias)) {
                            cmd.run(args, (Player) sender);
                            found.set(true);
                        }
                    }
                });
                if (!found.get()) {
                    sender.sendMessage(Holograms.GET.cmdPrefix +"Command not found!");
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
    public boolean lengthCheck(Player sender, String[] args, int length, String command) {
        if (args.length < length) {
            sender.sendMessage(Holograms.GET.cmdPrefix + "Missing Arguments, Usage:");
            sender.sendMessage(Holograms.GET.cmdPrefix + "/holograms " + command);
            return true;
        } else return false;
    }

    /**
     * Used to make the arguments a single string from a string array
     *
     * @param args A list of strings provided after the command
     * @return A completed string
     */
    public String buildArgs(String[] args, int start) {
        StringBuilder builder = new StringBuilder();
        for (int i = start; i < args.length; i++) {
            if (i != start)
                builder.append(" ");
            builder.append(args[i]);
        }
        return ChatColor.translateAlternateColorCodes('&', builder.toString());
    }

}
