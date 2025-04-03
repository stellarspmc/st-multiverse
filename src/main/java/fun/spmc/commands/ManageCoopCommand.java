package fun.spmc.commands;

import fun.spmc.STMultiverse;
import fun.spmc.island.CoopCache;
import fun.spmc.island.CoopInviteUtils;
import fun.spmc.island.CoopIsland;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ManageCoopCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player player) {
            if (strings.length == 0) return falseArgument(player);
            CoopIsland coopIsland = CoopCache.getIsland(player);
            if (coopIsland != null) {
                if (strings[0].equalsIgnoreCase("list")) return sendList(coopIsland, player);
                if (strings[0].equalsIgnoreCase("leave")) return wip(player);
                if (coopIsland.getCoopLeader() == player) {
                    return switch (strings[0]) {
                        case "invite" -> CoopInviteUtils.bulkInvite(coopIsland, Arrays.stream(Arrays.copyOfRange(strings, 1, strings.length)).map(Bukkit::getPlayer).toArray(Player[]::new));
                        case "remove" -> wip(player);
                        default -> falseArgument(player);
                    };
                } else STMultiverse.adventure().player(player).sendMessage(Component.text("You are not a coop leader!").color(NamedTextColor.RED));
            } else {
                return switch(strings[0]) {
                    case "accept" -> CoopInviteUtils.acceptInvite(player);
                    case "reject" -> CoopInviteUtils.rejectInvite(player);
                    default -> falseArgument2(player);
                };
            }
        }

        return false;
    }

    private static boolean sendList(CoopIsland coopIsland, Player player) {
        STMultiverse.adventure().player(player).sendMessage(Component.text("Players in").appendSpace().append(Component.text(coopIsland.getCoopLeader().getName())).append(Component.text("'s coop:")).appendSpace().append(Component.text(coopIsland.getCoopMembers().stream().map(Player::getName).collect(Collectors.joining(", "))).color(NamedTextColor.AQUA)));
        return true;
    }

    private static boolean falseArgument(Player player) {
        STMultiverse.adventure().player(player).sendMessage(Component.text("Use /coop (invite / list / remove) to manage your coop.").color(NamedTextColor.RED));
        return false;
    }

    private static boolean wip(Player player) {
        STMultiverse.adventure().player(player).sendMessage(Component.text("Work in progress!").color(NamedTextColor.RED));
        return false;
    }

    private static boolean falseArgument2(Player player) {
        STMultiverse.adventure().player(player).sendMessage(Component.text("You do not own a coop!").color(NamedTextColor.RED));
        return false;
    }
}