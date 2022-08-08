package anchig;

import anchig.commands.HorseCmd;
import anchig.commands.LagCmd;
import anchig.commands.MinerTotalCmd;
import anchig.manager.CommandManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class AnChIg extends JavaPlugin {

    //<editor-fold desc="Constructor">
    private static AnChIg instance;

    public final static AnChIg getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {
        AnChIg.instance = this;
    }
    //</editor-fold>

    @Override
    public void onEnable() {
        //<editor-fold desc="Commands">
        CommandManager.setupCommandMap();
        CommandManager.registerCommand(new LagCmd("Lag"));
        CommandManager.registerCommand(new HorseCmd("Horse"));
        CommandManager.registerCommand(new MinerTotalCmd("MinerTotal"));

        //</editor-fold>
    }

    @Override
    public void onDisable() {
    }
}
