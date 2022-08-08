package anchig.manager;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.defaults.BukkitCommand;

public final class CommandManager {

    private static CommandMap commandMap;


    public final static void setupCommandMap() {
        commandMap = Bukkit.getServer().getCommandMap();
    }

    public final static void registerCommand(final BukkitCommand command) {
        getCommandMap().register(command.getLabel(), command);
    }

    public final static CommandMap getCommandMap() {
        return commandMap;
    }
}
