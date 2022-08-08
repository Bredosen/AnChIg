package anchig.commands;

// Imports AnChIg libraries.
import anchig.handlers.DecimalFormatter;
import anchig.handlers.TextUtils;

// Imports mc api libraries.
import org.bukkit.attribute.Attribute;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;

// Imports java libraries.
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Bredo
 * @version 1.0
 * @apiNote Use /Horse or /HorseInformation.
 */
public final class HorseCmd extends BukkitCommand {

    // Setup command.
    public HorseCmd(final String name) {
        // Call super command.
        super(name);

        // Set /Horse alias as /HorseInformation.
        setAliases(new ArrayList<String>(Arrays.asList("HorseInformation")));

        // Set usage for command.
        setUsage("/<command>");

        // Set description for command.
        setDescription("See information about the currently riding horse");
    }

    // Command executor method
    @Override
    public boolean execute(final CommandSender sender, final String commandLabel, final String[] args) {
        // Checks if sender is not a player
        if (!(sender instanceof Player player)) {

            // Sends only player can execute this command to sender.
            sender.sendMessage(TextUtils.onlyPlayer());

            // End command.
            return true;
        }

        // create empty entity instance.
        Entity vehicle;

        // Checks if player has vehicle and sets instance.
        if ((vehicle = player.getVehicle()) == null) {

            // Sends need horse message to player.
            sendNeedHorse(player);

            // End command.
            return true;
        }

        // Checks if player is riding a horse.
        if (!(vehicle instanceof Horse horse)) {

            // Sends need horse message to player.
            sendNeedHorse(player);

            // End command.
            return true;
        }

        // Send horse information to player.
        sendHorseInformation(player, horse);

        // Return command.
        return true;
    }

    // sends horse information to player method.
    public final void sendHorseInformation(final Player player, final Horse horse) {
        // Sends horse top message to player.
        player.sendMessage(TextUtils.top("Horse"));

        // Sends horse name to player.
        player.sendMessage(TextUtils.value("Name: ", horse.getName(), "."));

        // Sends horse speed to player.
        player.sendMessage(TextUtils.value("Speed: ", DecimalFormatter.format(horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue() * 43.17D), "b/sec"));

        // Sends horse jump force to player.
        player.sendMessage(TextUtils.value("Jump: ", horse.getJumpStrength(), "."));

        // Sends horse age to player.
        player.sendMessage(TextUtils.value("Age: ", horse.getAge(), "."));

        // Sends horse color to player.
        player.sendMessage(TextUtils.value("Color: ", horse.getColor().name(), "."));
    }

    // Sends need horse message to player method.
    public final void sendNeedHorse(final Player player) {

        // Sends message to player.
        player.sendMessage(TextUtils.error("You need to be riding a horse for this command"));
    }
}
