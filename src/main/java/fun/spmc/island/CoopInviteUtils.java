package fun.spmc.island;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class CoopInviteUtils {

    public static void invitePlayer(CoopIsland island, Player player) {
        if (CoopInviteCache.hasInvited(island, player)) {
            island.getCoopLeader().sendMessage("%sERROR: You have already invited %s!".formatted(ChatColor.RED, player.getDisplayName()));
            return;
        }

        if (CoopInviteCache.invitePlayer(island, player)) {
            island.getCoopLeader().sendMessage("%sYou have invited %s to join the coop!".formatted(ChatColor.GREEN, player.getDisplayName()));
            player.sendMessage("%s%s has invited you to join their coop!".formatted(ChatColor.GREEN, island.getCoopLeader().getDisplayName()));
            return;
        }

        island.getCoopLeader().sendMessage("%sERROR: %s may already be in a coop.".formatted(ChatColor.RED, player.getDisplayName()));
    }

    public static boolean bulkInvite(CoopIsland island, Player[] players) {
        List.of(players).forEach(player -> invitePlayer(island, player));
        return true;
    }

    public static boolean acceptInvite(Player player) {
        if (CoopInviteCache.isInvited(player)) {
            CoopIsland island = CoopInviteCache.getInvitation(player);

            assert island != null;
            CoopInviteCache.acceptInvite(island, player);
            island.getCoopLeader().sendMessage("%s%s has accepted to join your coop!".formatted(ChatColor.GREEN, player.getDisplayName()));
            player.sendMessage("%sYou have accepted to join %s's coop!".formatted(ChatColor.GREEN, island.getCoopLeader().getDisplayName()));
            return true;
        }

        player.sendMessage("%sERROR: You are not invited!".formatted(ChatColor.RED));
        return false;
    }

    public static boolean rejectInvite(Player player) {
        if (CoopInviteCache.isInvited(player)) {
            CoopIsland island = CoopInviteCache.getInvitation(player);

            assert island != null;
            CoopInviteCache.rejectInvite(island, player);
            island.getCoopLeader().sendMessage("%s%s has rejected to join your coop!".formatted(ChatColor.GREEN, player.getDisplayName()));
            player.sendMessage("%sYou have rejected to join %s's coop!".formatted(ChatColor.GREEN, island.getCoopLeader().getDisplayName()));
            return true;
        }

        player.sendMessage("%sERROR: You are not invited!".formatted(ChatColor.RED));
        return false;
    }
}
