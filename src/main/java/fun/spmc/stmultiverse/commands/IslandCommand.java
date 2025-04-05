package fun.spmc.stmultiverse.commands;

import fun.spmc.stmultiverse.STMultiverse;
import fun.spmc.stmultiverse.island.IslandUtils;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class IslandCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player player) {
            Audience audience = STMultiverse.adventure().player(player);

            if (strings.length == 0) return IslandUtils.teleportPlayerIsland(player, strings);
            return switch (strings[0]) {
                case "create" -> IslandUtils.createIsland(player);
                case "tp" -> IslandUtils.teleportPlayerIsland(player, strings);
                case "coop" -> IslandUtils.turnIslandCoop(player);
                case "delete" -> IslandUtils.deleteIsland(player);
                default -> falseArgument(audience);
            };
        }
        return false;
    }

    private static boolean falseArgument(Audience audience) {
        audience.sendMessage(Component.text("Use /island create to create an island.").color(NamedTextColor.RED));
        return false;
    }
}
