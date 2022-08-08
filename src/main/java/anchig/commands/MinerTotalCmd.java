package anchig.commands;

import anchig.handlers.ColorScheme;
import anchig.handlers.TextUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.TimeUnit;

public final class MinerTotalCmd extends BukkitCommand {

    public final Material[] materials;

    public MinerTotalCmd(final String name) {
        super(name);
        setAliases(null);
        setUsage("/<command> [Top/List]");
        setDescription("Competition for most mined blocks.");
        materials = new Material[]{Material.DIRT, Material.GRASS_BLOCK, Material.STONE, Material.GRANITE, Material.DIORITE, Material.ANDESITE, Material.DEEPSLATE, Material.CALCITE, Material.TUFF, Material.DRIPSTONE_BLOCK, Material.GRAVEL, Material.COAL_ORE, Material.DEEPSLATE_COAL_ORE, Material.IRON_ORE, Material.DEEPSLATE_IRON_ORE, Material.COPPER_ORE, Material.DEEPSLATE_COPPER_ORE, Material.GOLD_ORE, Material.DEEPSLATE_GOLD_ORE, Material.REDSTONE_ORE, Material.DEEPSLATE_REDSTONE_ORE, Material.EMERALD_ORE, Material.DEEPSLATE_EMERALD_ORE, Material.LAPIS_ORE, Material.DEEPSLATE_LAPIS_ORE, Material.DIAMOND_ORE, Material.DEEPSLATE_DIAMOND_ORE, Material.SCULK};
    }

    @Override
    public boolean execute(final CommandSender sender, final String commandLabel, final String[] args) {
        sender.sendMessage(TextUtils.top("Miner Total"));

        if (args.length == 1) {

            if (args[0].equalsIgnoreCase("Top")) {
                sendTop(sender);
                return true;
            }

            if (args[0].equalsIgnoreCase("List")) {
                sendList(sender);
                return true;
            }
        }

        if (sender instanceof Player player) sendMinedBlocks(player);

        sendHelp(sender);
        sendDaysLeft(sender);
        return true;
    }

    @Override
    public final List<String> tabComplete(final CommandSender sender, final String alias, final String[] args) throws IllegalArgumentException {
        final List<String> list = new ArrayList<>();
        if (args.length == 1) {
            if ("Top".toLowerCase().contains(args[0].toLowerCase()) || args[0].length() == 0) list.add("Top");
            if ("List".toLowerCase().contains(args[0].toLowerCase()) || args[0].length() == 0) list.add("List");
        }
        return list;
    }

    public final void sendTop(final CommandSender sender) {
        final HashMap<String, Long> map = new HashMap<>();

        for (OfflinePlayer op : Bukkit.getOfflinePlayers()) map.put(op.getName(), getMinedBlocks(op));

        final Map<String, Long> sorted = sortByValue(map);

        for (int index = 0; index < sorted.keySet().size(); index++)
            sender.sendMessage(TextUtils.marker("{" + (index + 1) + "}", sorted.keySet().toArray()[(sorted.keySet().size() - 1 - index)].toString()));
    }

    public final void sendList(final CommandSender sender) {
        for (int index = 0; index < materials.length; index++) {
            String name = materials[index].name();
            sender.sendMessage(TextUtils.marker("{" + index + "}", name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase().replace('-', ' ')));
        }
    }

    public final void sendHelp(final CommandSender sender) {
        sender.sendMessage(TextUtils.value("Type ", "\"/MinerTotal List\"", " To see blocks that counts"));
        sender.sendMessage(TextUtils.value("Type ", "\"/MinerTotal Top\"", " To see top players"));
    }

    public final void sendMinedBlocks(final Player player) {
        player.sendMessage(TextUtils.value("You have mined ", String.format(Locale.US, "%,d", getMinedBlocks(player)).replace(',', '.'), " blocks"));
    }

    public final void sendDaysLeft(final CommandSender sender) {
        sender.sendMessage(TextUtils.value("Challenge ends in ", getDays(new Date()), " days"));
    }

    public final long getMinedBlocks(final OfflinePlayer player) {
        long total = 0;
        for (Material material : materials) total += player.getStatistic(Statistic.MINE_BLOCK, material);
        return total;
    }

    public final HashMap<String, Long> sortByValue(HashMap<String, Long> hm) {
        // Create a list from elements of HashMap
        final List<Map.Entry<String, Long>> list = new LinkedList<>(hm.entrySet());

        // Sort the list
        Collections.sort(list, Map.Entry.comparingByValue());

        // put data from sorted list to hashmap
        final HashMap<String, Long> temp = new LinkedHashMap<>();
        for (Map.Entry<String, Long> aa : list) temp.put(aa.getKey(), aa.getValue());
        return temp;
    }

    public final long getDays(Date d1) {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(2022, 7, 20);
        final Date d2 = calendar.getTime();
        final long diff = d2.getTime() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }
}
