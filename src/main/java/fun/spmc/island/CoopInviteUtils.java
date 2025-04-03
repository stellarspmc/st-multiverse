package fun.spmc.island;

import fun.spmc.STMultiverse;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

import java.util.List;

public class CoopInviteUtils {

    public static void invitePlayer(CoopIsland island, Player player) {
        if (CoopInviteCache.hasInvited(island, player)) {
            STMultiverse.adventure().player(island.getCoopLeader()).sendMessage(Component.text("ERROR: You have already invited %s!".formatted(player.getDisplayName())).color(NamedTextColor.RED));
            return;
        }

        if (CoopInviteCache.invitePlayer(island, player)) {
            STMultiverse.adventure().player(island.getCoopLeader()).sendMessage(Component.text("You have invited %s to join the coop!".formatted(player.getDisplayName())).color(NamedTextColor.GREEN));
            STMultiverse.adventure().player(player).sendMessage(Component.text("%s has invited you to join their coop!".formatted(island.getCoopLeader().getDisplayName())).color(NamedTextColor.AQUA));
            return;
        }

        STMultiverse.adventure().player(island.getCoopLeader()).sendMessage(Component.text("ERROR: %s may already be in a coop.".formatted(player.getDisplayName())).color(NamedTextColor.RED));
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
            STMultiverse.adventure().player(island.getCoopLeader()).sendMessage(Component.text("%s has accepted to join your coop!".formatted(player.getDisplayName())).color(NamedTextColor.GREEN));
            STMultiverse.adventure().player(player).sendMessage(Component.text("You have accepted to join %s's coop!".formatted(island.getCoopLeader().getDisplayName())).color(NamedTextColor.GREEN));
            return true;
        }

        STMultiverse.adventure().player(player).sendMessage(Component.text("ERROR: You are not invited!").color(NamedTextColor.RED));
        return false;
    }

    public static boolean rejectInvite(Player player) {
        if (CoopInviteCache.isInvited(player)) {
            CoopIsland island = CoopInviteCache.getInvitation(player);

            assert island != null;
            CoopInviteCache.rejectInvite(island, player);
            STMultiverse.adventure().player(island.getCoopLeader()).sendMessage(Component.text("%s has rejected to join your coop!".formatted(player.getDisplayName())).color(NamedTextColor.GREEN));
            STMultiverse.adventure().player(player).sendMessage(Component.text("You have rejected to join %s's coop!".formatted(island.getCoopLeader().getDisplayName())).color(NamedTextColor.GREEN));
            return true;
        }

        STMultiverse.adventure().player(player).sendMessage(Component.text("ERROR: You are not invited!").color(NamedTextColor.RED));
        return false;
    }
}
