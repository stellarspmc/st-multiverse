package fun.spmc.commands;

import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import fun.spmc.STMultiverse;
import fun.spmc.island.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static fun.spmc.STMultiverse.core;

public class VisitCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player player) {
            if (strings.length != 1) return falseArgument(player);
            Player target = Bukkit.getPlayer(strings[0]);
            if (target == null) return falseArgument(player);

            MultiverseWorld targetWorld = IslandUtils.getIsland(player);
            if (targetWorld == null) return noIsland(player);

            if (target == player) {
                core.teleportPlayer(player, player, new Location(targetWorld.getCBWorld(), 3, 67, 3));
                return true;
            }

            if (CoopCache.getIslandByOwner(target) != null) {
                CoopIsland targetIsland = CoopCache.getIslandByOwner(target);
                if (targetIsland.playerInCoop(player)) {
                    core.teleportPlayer(player, player, new Location(targetIsland.getMVWorld().getCBWorld(), 3, 67, 3));
                    return true;
                }
            }

            core.teleportPlayer(player, player, new Location(targetWorld.getCBWorld(), 3, 67, 3));
            player.setGameMode(GameMode.ADVENTURE);
            return true;
        } return false;
    }

    private static boolean falseArgument(Player player) {
        STMultiverse.adventure().player(player).sendMessage(Component.text("Use /visit <player> to visit players.").color(NamedTextColor.RED));
        return false;
    }

    private static boolean noIsland(Player player) {
        STMultiverse.adventure().player(player).sendMessage(Component.text(player.getName()).appendSpace().append(Component.text("has no island!")).color(NamedTextColor.RED));
        return false;
    }
}
