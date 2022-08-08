package anchig.commands;

// Import AnChIg libraries.

import anchig.AnChIg;
import anchig.handlers.ColorScheme;
import anchig.handlers.DecimalFormatter;
import anchig.handlers.TextUtils;

// Import mc api libraries.
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

// Imports java libraries.
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Bredo
 * @version 1.0
 * @apiNote Use /Lag, /Performance or /ServerPerformance.
 */

public final class LagCmd extends BukkitCommand {

    //<editor-fold desc="Server Performance">
    private int performance; // Server performance in percentage from 0 - 100%
    private int processors; // Server processors.
    private double ramUsed, ramMax; // Server ram used and max allocated ram.
    private double diskUsed, diskMax; // Server disk used and max disk.
    //</editor-fold>


    public LagCmd(final String name) { // Setup command.
        super(name); // Call super command.
        setAliases(new ArrayList<String>(Arrays.asList("ServerPerformance", "Performance"))); // Set /Lag alias as /ServerPerformance or /Performance.
        setUsage("/<command> [Performance/Ram/Disk/Processors/Ping]"); // Set usage for command.
        setDescription("See Server performance"); // Set description for command.
    }

    @Override // Command executor method
    public boolean execute(final CommandSender sender, final String commandLabel, final String[] args) {
        sender.sendMessage(TextUtils.top("Server Performance")); // Send top message to sender
        updateData(); // Update Server performance data.

        if (args.length == 1) { // if there is 1 argument after command

            if (args[0].equalsIgnoreCase("Performance")) { // Checks if argument is "Performance".
                sendPerformance(sender); // Send performance information to sender.
                return true; // End command.
            }

            if (args[0].equalsIgnoreCase("Ram")) { // Checks if argument is "Ram".
                sendRam(sender); // Send performance information to sender.
                return true; // End command.
            }

            if (args[0].equalsIgnoreCase("Disk")) { // Checks if argument is "Disk".
                sendDisk(sender); // Send performance information to sender.
                return true; // End command.
            }

            if (args[0].equalsIgnoreCase("Processors")) { // Checks if argument is "Processors".
                sendProcessors(sender); // Send performance information to sender.
                return true; // End command.
            }

            if (args[0].equalsIgnoreCase("Ping")) { // Checks if argument is "Ping".
                if (sender instanceof Player player) { // Checks if sender is a player.
                    sendPing(player); // Send performance information to sender.
                    return true; // End command.
                } else { // Checks if sender is console.
                    sender.sendMessage(TextUtils.onlyPlayer()); // Send only player to console.
                    return true; // End command.
                }
            }
        }

        sendPerformance(sender); // Send performance information to sender.
        sendRam(sender); // Send ram information to sender.
        sendDisk(sender); // Send disk information to sender.
        sendProcessors(sender); // Send processors information to sender.
        if (performance <= 85) sender.sendMessage(Component.text("\nThere is server lag right now!").color(ColorScheme.error)); // Sens server lag to sender.
        if (sender instanceof Player player) sendPing(player); // Send ping information to player.
        return true; // End command.
    }

    public final void sendPerformance(final CommandSender sender) { // Send performance to sender method.
        sender.sendMessage(TextUtils.value("Server performance: ", performance, "%")); // Send performance to sender.
    }

    public final void sendRam(final CommandSender sender) { // Send ram to sender method.
        sender.sendMessage(TextUtils.value("Ram: ", ramUsed + "/" + ramMax, " gib")); // Send ram to sender.
    }

    public final void sendDisk(final CommandSender sender) { // Send disk to sender method.
        sender.sendMessage(TextUtils.value("Disk: ", diskUsed + "/" + diskMax, " gib")); // Send disk to sender.
    }

    public final void sendProcessors(final CommandSender sender) { // Send processors to sender method.
        sender.sendMessage(TextUtils.value("Processors: ", processors, " vCPU")); // Send processors to sender.
    }

    public final void sendPing(final Player player) { // Send ping to player method.
        player.sendMessage(TextUtils.value("Your ping is ", player.getPing(), "ms")); // Send ping to player.
    }

    @Override // Command tab complete method.
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        final List<String> list = new ArrayList<String>(); // Create an empty string list
        if (args.length == 1) { // Checks if there's about to be written another argument to command.
            if ("Performance".toLowerCase().contains(args[0].toLowerCase()) || args[0].length() == 0)
                list.add("Performance"); // Check if "Performance" contain new argument, if so add "Performance" to string list.
            if ("Ram".toLowerCase().contains(args[0].toLowerCase()) || args[0].length() == 0) list.add("Ram"); // Check if "Ram" contain new argument, if so add "Ram" to string list.
            if ("Disk".toLowerCase().contains(args[0].toLowerCase()) || args[0].length() == 0) list.add("Disk"); // Check if "Disk" contain new argument, if so add "Disk" to string list.
            if ("Processors".toLowerCase().contains(args[0].toLowerCase()) || args[0].length() == 0)
                list.add("Processors"); // Check if "Processors" contain new argument, if so add "Processors" to string list.
            if ("Ping".toLowerCase().contains(args[0].toLowerCase()) || args[0].length() == 0) list.add("Ping"); // Check if "Ping" contain new argument, if so add "Ping" to string list.
        }
        return list; // Return string list.
    }

    public final void updateData() { // Update server performance data.
        performance = (int) (AnChIg.getInstance().getServer().getTPS()[0] * 5.0D); // Set performance from server tps.
        ramMax = Double.parseDouble(DecimalFormatter.format(byteToGIB(Runtime.getRuntime().maxMemory()))); // Set max allocated server ram.
        ramUsed = Double.parseDouble(DecimalFormatter.format(byteToGIB(Runtime.getRuntime().maxMemory() - Runtime.getRuntime().freeMemory()))); // Set used server ram.
        processors = Runtime.getRuntime().availableProcessors(); // Set server processors.
        diskMax = Double.parseDouble(DecimalFormatter.format(byteToGIB(new File("/").getTotalSpace()))); // Set total disk space.
        diskUsed = Double.parseDouble(DecimalFormatter.format(byteToGIB(new File("/").getTotalSpace() - new File("/").getFreeSpace()))); // Set used disk space.
    }


    public double byteToGIB(final Long bytes) { // Converts bytes into GIB method
        return bytes / 1073741824D; // Dives bytes by 1.073.741.824.
    }
}
