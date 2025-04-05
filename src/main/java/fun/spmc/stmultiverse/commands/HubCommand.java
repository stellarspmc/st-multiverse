package fun.spmc.stmultiverse.commands;

import fun.spmc.stmultiverse.STMultiverse;
import fun.spmc.stmultiverse.island.IslandUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HubCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player player) {
            if (strings.length != 0) return falseArgument(player);
            IslandUtils.teleportPlayerLobby(player);
        } return false;
    }

    private static boolean falseArgument(Player player) {
        STMultiverse.adventure().player(player).sendMessage(Component.text("Use /hub to go back to hub.").color(NamedTextColor.RED));
        return false;
    }
}
