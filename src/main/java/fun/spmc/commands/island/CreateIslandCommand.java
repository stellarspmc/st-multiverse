package fun.spmc.commands.island;

import fun.spmc.island.IslandUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CreateIslandCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player player) {
            if (strings.length == 0) return falseArgument(player);
            return switch (strings[0]) {
                case "create" -> IslandUtils.createIsland(player);
                case "tp" -> IslandUtils.teleportPlayerIsland(player, strings[1]);
                case "lobby" -> IslandUtils.teleportPlayerLobby(player);
                case "coop" -> IslandUtils.turnIslandCoop(player);
                case "delete" -> IslandUtils.deleteIsland(player);
                default -> falseArgument(player);
            };
        }
        return false;
    }

    private static boolean falseArgument(Player player) {
        player.sendMessage("%sUse /island create to create an island.".formatted(ChatColor.RED));
        return false;
    }
}
