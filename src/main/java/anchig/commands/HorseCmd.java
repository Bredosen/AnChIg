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
import org.bukkit.entity.Vehicle;

// Imports java libraries.
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Bredo
 * @version 1.0
 * @apiNote Use /Horse or /HorseInformation.
 */
public final class HorseCmd extends BukkitCommand {

    public HorseCmd(final String name) { // Setup command.
        super(name); // Call super command.
        setAliases(new ArrayList<String>(Arrays.asList("HorseInformation"))); // Set /Horse alias as /HorseInformation.
        setUsage("/<command>"); // Set usage for command.
        setDescription("See information about the currently riding horse"); // Set description for command.
    }

    @Override // Command executor method
    public boolean execute(final CommandSender sender, final String commandLabel, final String[] args) {
        if (!(sender instanceof Player player)) { // Checks if sender is not a player
            sender.sendMessage(TextUtils.onlyPlayer()); // Sends only player can execute this command to sender.
            return true; // End command.
        }

        Entity vehicle; // create empty entity instance.
        if ((vehicle = player.getVehicle()) == null) { // Checks if player has vehicle and sets instance.
            sendNeedHorse(player); // Sends need horse message to player.
            return true; // End command.
        }

        if (!(vehicle instanceof Horse horse)) { // Checks if player is riding a horse.
            sendNeedHorse(player); // Sends need horse message to player.
            return true; // End command.
        }

        sendHorseInformation(player, horse); // Send horse information to player.
        return true; // Return command.
    }

    public final void sendHorseInformation(final Player player, final Horse horse) { // sends horse information to player method.
        player.sendMessage(TextUtils.top("Horse")); // Sends horse top message to player.
        player.sendMessage(TextUtils.value("Name: ", horse.getName(), ".")); // Sends horse name to player.
        player.sendMessage(TextUtils.value("Speed: ", DecimalFormatter.format(horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue() * 43.17D), "b/sec")); // Sends horse speed to player.
        player.sendMessage(TextUtils.value("Jump: ", horse.getJumpStrength(), ".")); // Sends horse jump force to player.
        player.sendMessage(TextUtils.value("Age: ", horse.getAge(), ".")); // Sends horse age to player.
        player.sendMessage(TextUtils.value("Color: ", horse.getColor().name(), ".")); // Sends horse color to player.
    }

    public final void sendNeedHorse(final Player player) { // Sends need horse message to player method.
        player.sendMessage(TextUtils.error("You need to be riding a horse for this command")); // Sends message to player.
    }
}
