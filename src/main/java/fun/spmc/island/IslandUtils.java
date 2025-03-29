package fun.spmc.island;

import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import static fun.spmc.STMultiverse.core;

public class IslandUtils {

    public static boolean doesIslandExist(Player player) {
        return (getIsland(player) != null);
    }

    public static boolean createIsland(Player player) {
        if (doesIslandExist(player)) return false;
        boolean clone = core.getMVWorldManager().cloneWorld("world2", "island_%s".formatted(player.getUniqueId()));
        if (clone) player.sendMessage("%sCreated island!".formatted(ChatColor.GREEN));
        else player.sendMessage("%sFailed to create island.".formatted(ChatColor.RED));
        return clone;
    }


    public static boolean deleteIsland(Player player) {
        if (!doesIslandExist(player)) return false;
        teleportPlayerLobby(player);

        boolean del = core.getMVWorldManager().deleteWorld("island_%s".formatted(player.getUniqueId()));
        if (del) player.sendMessage("%sDeleted island!".formatted(ChatColor.GREEN));
        else player.sendMessage("%sFailed to delete island.".formatted(ChatColor.RED));
        return del;
    }

    public static boolean turnIslandCoop(Player player) {
        if (!doesIslandExist(player)) return false;
        CoopIsland coopIsland = IslandCoopUtils.turnIslandIntoCoop(getIsland(player));
        boolean coop = (coopIsland.getCoopLeader() != null);
        if (coop) player.sendMessage("%sTurned island into coop! Invite players via /coop invite <name>.".formatted(ChatColor.GREEN));
        else player.sendMessage("%sFailed to turn island into coop.".formatted(ChatColor.RED));
        return coop;
    }

    public static MultiverseWorld getIsland(Player player) {
        return core.getMVWorldManager().getMVWorld("island_%s".formatted(player.getUniqueId()));
    }

    public static boolean teleportPlayerIsland(Player player, String string) {
        if (!doesIslandExist(player) || CoopCache.getIsland(player) == null) {
            player.sendMessage("%sCreate an island with /island create first!".formatted(ChatColor.RED));
            return false;
        }

        switch (string) {
            case "solo" -> {
                core.teleportPlayer(player, player, new Location(getIsland(player).getCBWorld(), 3, 67, 3));
                return true;
            }
            case "coop" -> {
                core.teleportPlayer(player, player, new Location(CoopCache.getIsland(player).getMVWorld().getCBWorld(), 3, 67, 3));
                return true;
            }
        }

        return false;
    }

    public static boolean teleportPlayerLobby(Player player) {
        core.teleportPlayer(player, player, new Location(core.getMVWorldManager().getMVWorld("world").getCBWorld(), 3, 67, 3));
        return true;
    }
}
