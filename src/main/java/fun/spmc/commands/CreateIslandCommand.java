package fun.spmc.commands;

import fun.spmc.STMultiverse;
import fun.spmc.island.IslandUtils;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CreateIslandCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player player) {
            Audience audience = STMultiverse.adventure().player(player);

            if (strings.length == 0) return falseArgument(audience);
            return switch (strings[0]) {
                case "create" -> IslandUtils.createIsland(player);
                case "tp" -> IslandUtils.teleportPlayerIsland(player, strings);
                case "lobby" -> IslandUtils.teleportPlayerLobby(player);
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
