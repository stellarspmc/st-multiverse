package fun.spmc.commands.coop;

import fun.spmc.island.CoopCache;
import fun.spmc.island.CoopInviteUtils;
import fun.spmc.island.CoopIsland;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ManageCoopCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player player) {
            if (CoopCache.getIsland(player) != null) {
                CoopIsland coopIsland = CoopCache.getIsland(player);

                assert coopIsland != null;
                if (strings[0].equalsIgnoreCase("list")) return sendList(coopIsland, player);
                if (coopIsland.getCoopLeader() == player) {
                    return switch (strings[0]) {
                        case "invite" -> CoopInviteUtils.bulkInvite(coopIsland, Arrays.stream(Arrays.copyOfRange(strings, 1, strings.length)).map(Bukkit::getPlayer).toArray(Player[]::new));
                        case "remove" -> wip(player);
                        default -> falseArgument(player);
                    };
                } else player.sendMessage("%sYou are not a coop leader!".formatted(ChatColor.RED));
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
        player.sendMessage(ChatColor.AQUA + coopIsland.getCoopMembers().stream().map(Player::getName).collect(Collectors.joining(", ")));
        return true;
    }

    private static boolean falseArgument(Player player) {
        player.sendMessage("%sUse /coop (invite / list / remove) to manage your coop.".formatted(ChatColor.RED));
        return false;
    }

    private static boolean wip(Player player) {
        player.sendMessage("%sWork in progress!".formatted(ChatColor.RED));
        return false;
    }

    private static boolean falseArgument2(Player player) {
        player.sendMessage("%sYou do not own a coop!".formatted(ChatColor.RED));
        return false;
    }
}