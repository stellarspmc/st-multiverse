package fun.spmc.commands;

import fun.spmc.STMultiverse;
import fun.spmc.island.IslandAdminUtils;
import fun.spmc.island.IslandUtils;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class IslandAdminCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player player) {
            Audience audience = STMultiverse.adventure().player(player);
            if (!player.isOp()) return invalidPermission(audience);

            if (strings.length == 0) return falseArgument(audience);
            return switch (strings[0]) {
                case "forcetp" -> IslandAdminUtils.teleportToPlayerIsland(player, strings);
                case "forcelobby" -> IslandAdminUtils.teleportPlayerToLobby(strings);
                case "forcedelete" -> IslandAdminUtils.deleteIsland(strings);
                case "reload" -> IslandAdminUtils.reloadConfig();
                case "cooplist" -> IslandAdminUtils.getCoopList(audience);
                default -> falseArgument(audience);
            };
        } else if (commandSender instanceof ConsoleCommandSender) {
            return switch (strings[0]) {
                case "forcelobby" -> IslandAdminUtils.teleportPlayerToLobby(strings);
                case "forcedelete" -> IslandAdminUtils.deleteIsland(strings);
                case "reload" -> IslandAdminUtils.reloadConfig();
                default -> false;
            };
        }
        return false;
    }

    private static boolean falseArgument(Audience audience) {
        audience.sendMessage(Component.text("Invalid usage!").color(NamedTextColor.RED));
        return false;
    }

    private static boolean invalidPermission(Audience audience) {
        audience.sendMessage(Component.text("You don't have enough permission!").color(NamedTextColor.RED));
        return false;
    }
}
