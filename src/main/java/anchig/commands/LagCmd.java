package anchig.commands;

// Import AnChIg libraries.
import anchig.AnChIg;
import anchig.handlers.DecimalFormatter;
import anchig.handlers.TextUtils;

// Import mc api libraries.
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

    // Server performance in percentage from 0 - 100%
    private int performance;

    // Server processors.
    private int processors;

    // Server ram used and max allocated ram.
    private double ramUsed, ramMax;

    // Server disk used and max disk.
    private double diskUsed, diskMax;

    // Setup command.
    public LagCmd(final String name) {
        // Call super command.
        super(name);

        // Set /Lag alias as /ServerPerformance or /Performance.
        setAliases(new ArrayList<String>(Arrays.asList("ServerPerformance", "Performance")));

        // Set usage for command.
        setUsage("/<command> [Performance/Ram/Disk/Processors/Ping]");

        // Set description for command.
        setDescription("See Server performance");
    }

    // Command executor method
    @Override
    public boolean execute(final CommandSender sender, final String commandLabel, final String[] args) {

        // Send top message to sender
        sender.sendMessage(TextUtils.top("Server Performance"));

        // Update Server performance data.
        updateData();

        // if there is 1 argument after command
        if (args.length == 1) {

            // Checks if argument is "Performance".
            if (args[0].equalsIgnoreCase("Performance")) {

                // Send performance information to sender.
                sendPerformance(sender);

                // End command.
                return true;
            }

            // Checks if argument is "Ram".
            if (args[0].equalsIgnoreCase("Ram")) {

                // Send performance information to sender.
                sendRam(sender);

                // End command.
                return true;
            }

            // Checks if argument is "Disk".
            if (args[0].equalsIgnoreCase("Disk")) {

                // Send performance information to sender.
                sendDisk(sender);

                // End command.
                return true;
            }

            // Checks if argument is "Processors".
            if (args[0].equalsIgnoreCase("Processors")) {

                // Send performance information to sender.
                sendProcessors(sender);

                // End command.
                return true;
            }

            if (args[0].equalsIgnoreCase("Ping")) { // Checks if argument is "Ping".
                // End command.

                // Checks if sender is a player if so sends performance information to sender.
                if (sender instanceof Player player) sendPing(player);

                    // Checks if sender is console if so sends only player to console.
                else sender.sendMessage(TextUtils.onlyPlayer());

                // End command.
                return true;
            }
        }

        // Send performance information to sender.
        sendPerformance(sender);

        // Send ram information to sender.
        sendRam(sender);

        // Send disk information to sender.
        sendDisk(sender);

        // Send processors information to sender.
        sendProcessors(sender);

        // Sends server lag to sender if performance is under 85 %
        if (performance <= 85) sender.sendMessage(TextUtils.error("There is server lag right now!"));

        // Send ping information to player if sender is player.
        if (sender instanceof Player player) sendPing(player);

        // End command.
        return true;
    }

    // Send performance to sender method.
    public final void sendPerformance(final CommandSender sender) {

        // Send performance to sender.
        sender.sendMessage(TextUtils.value("Server performance: ", performance, "%"));
    }

    // Send ram to sender method.
    public final void sendRam(final CommandSender sender) {

        // Send ram to sender.
        sender.sendMessage(TextUtils.value("Ram: ", ramUsed + "/" + ramMax, " gib"));
    }

    // Send disk to sender method.
    public final void sendDisk(final CommandSender sender) {

        // Send disk to sender.
        sender.sendMessage(TextUtils.value("Disk: ", diskUsed + "/" + diskMax, " gib"));
    }

    // Send processors to sender method.
    public final void sendProcessors(final CommandSender sender) {

        // Send processors to sender.
        sender.sendMessage(TextUtils.value("Processors: ", processors, " vCPU"));
    }

    // Send ping to player method.
    public final void sendPing(final Player player) {

        // Send ping to player.
        player.sendMessage(TextUtils.value("Your ping is ", player.getPing(), "ms"));
    }

    // Command tab complete method.
    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {

        // Create an empty string list
        final List<String> list = new ArrayList<String>();

        // Checks if there's about to be written another argument to command.
        if (args.length == 1) {

            // Check if "Performance" contain new argument, if so add "Performance" to string list.
            if ("Performance".toLowerCase().contains(args[0].toLowerCase()) || args[0].length() == 0) list.add("Performance");

            // Check if "Ram" contain new argument, if so add "Ram" to string list.
            if ("Ram".toLowerCase().contains(args[0].toLowerCase()) || args[0].length() == 0) list.add("Ram");

            // Check if "Disk" contain new argument, if so add "Disk" to string list.
            if ("Disk".toLowerCase().contains(args[0].toLowerCase()) || args[0].length() == 0) list.add("Disk");

            // Check if "Processors" contain new argument, if so add "Processors" to string list.
            if ("Processors".toLowerCase().contains(args[0].toLowerCase()) || args[0].length() == 0) list.add("Processors");

            // Check if "Ping" contain new argument, if so add "Ping" to string list.
            if ("Ping".toLowerCase().contains(args[0].toLowerCase()) || args[0].length() == 0) list.add("Ping");
        }

        // Return string list.
        return list;
    }

    // Update server performance data.
    public final void updateData() {

        // Set performance from server tps.
        performance = (int) (AnChIg.getInstance().getServer().getTPS()[0] * 5.0D);

        // Set max allocated server ram.
        ramMax = Double.parseDouble(DecimalFormatter.format(byteToGIB(Runtime.getRuntime().maxMemory())));

        // Set used server ram.
        ramUsed = Double.parseDouble(DecimalFormatter.format(byteToGIB(Runtime.getRuntime().maxMemory() - Runtime.getRuntime().freeMemory())));

        // Set server processors.
        processors = Runtime.getRuntime().availableProcessors();

        // Set total disk space.
        diskMax = Double.parseDouble(DecimalFormatter.format(byteToGIB(new File("/").getTotalSpace())));

        // Set used disk space.
        diskUsed = Double.parseDouble(DecimalFormatter.format(byteToGIB(new File("/").getTotalSpace() - new File("/").getFreeSpace())));
    }

    // Converts bytes into GIB method
    public double byteToGIB(final Long bytes) {

        // Dives bytes by 1.073.741.824.
        return bytes / 1_073_741_824D;
    }
}
