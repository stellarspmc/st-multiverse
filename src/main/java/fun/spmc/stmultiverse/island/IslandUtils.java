package fun.spmc.stmultiverse.island;

import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import fun.spmc.stmultiverse.STMultiverse;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.text.MessageFormat;
import java.util.Objects;

import static fun.spmc.stmultiverse.STMultiverse.core;

public class IslandUtils {

    public static boolean doesIslandExist(Player player) {
        return (getIsland(player) != null);
    }

    public static boolean createIsland(Player player) {
        if (doesIslandExist(player)) {
            STMultiverse.adventure().player(player).sendMessage(Component.text("Island exists already!").color(NamedTextColor.RED));
            return false;
        }

        boolean clone = core.getMVWorldManager().cloneWorld("world2", MessageFormat.format("island_{0}", player.getUniqueId()));
        if (clone) STMultiverse.adventure().player(player).sendMessage(Component.text("Created island!").color(NamedTextColor.GREEN));
        else STMultiverse.adventure().player(player).sendMessage(Component.text("Failed to create island.").color(NamedTextColor.RED));
        return clone;
    }


    public static boolean deleteIsland(Player player) {
        if (!doesIslandExist(player)) {
            STMultiverse.adventure().player(player).sendMessage(Component.text("Create an island with /island create first!").color(NamedTextColor.RED));
            return false;
        }

        if (CoopCache.getIslandByOwner(player) != null) CoopCache.removeIsland(CoopCache.getIslandByOwner(player));
        if (player.getWorld().getName().equals(MessageFormat.format("island_{0}", player.getUniqueId()))) teleportPlayerLobby(player);
        boolean del = core.getMVWorldManager().deleteWorld(MessageFormat.format("island_{0}", player.getUniqueId()));
        if (del) STMultiverse.adventure().player(player).sendMessage(Component.text("Deleted island!").color(NamedTextColor.GREEN));
        else STMultiverse.adventure().player(player).sendMessage(Component.text("Failed to delete island.").color(NamedTextColor.RED));
        return del;
    }

    public static boolean turnIslandCoop(Player player) {
        if (!doesIslandExist(player)) {
            STMultiverse.adventure().player(player).sendMessage(Component.text("Create an island with /island create first!").color(NamedTextColor.RED));
            return false;
        } if (CoopCache.getIslandByOwner(player) != null) {
            STMultiverse.adventure().player(player).sendMessage(Component.text("Your island is already a coop!").color(NamedTextColor.RED));
            return false;
        } if (CoopCache.getIsland(player) != null) {
            STMultiverse.adventure().player(player).sendMessage(Component.text("You are already in a coop!").color(NamedTextColor.RED));
            return false;
        }

        CoopIsland coopIsland = IslandCoopUtils.turnIslandIntoCoop(getIsland(player));
        CoopCache.addIsland(coopIsland);
        boolean coop = (coopIsland.getCoopLeader() != null);
        if (coop) STMultiverse.adventure().player(player).sendMessage(Component.text("Turned island into coop! Invite players via /coop invite <name>.").color(NamedTextColor.GOLD));
        else STMultiverse.adventure().player(player).sendMessage(Component.text("Failed to turn island into coop.").color(NamedTextColor.RED));
        return coop;
    }

    public static MultiverseWorld getIsland(Player player) {
        return core.getMVWorldManager().getMVWorld(MessageFormat.format("island_{0}", player.getUniqueId()));
    }

    public static boolean teleportPlayerIsland(Player player, String[] strings) {
        if (!doesIslandExist(player) && CoopCache.getIsland(player) == null) {
            STMultiverse.adventure().player(player).sendMessage(Component.text("Create an island with /island create first!").color(NamedTextColor.RED));
            return false;
        }

        if (strings.length > 1) {
            if (strings[1].equals("coop") && CoopCache.getIsland(player) != null) {
                core.teleportPlayer(player, player, new Location(Objects.requireNonNull(CoopCache.getIsland(player)).getMVWorld().getCBWorld(), 3, 67, 3));
                player.setGameMode(GameMode.SURVIVAL);
                return true;
            }
        }
        core.teleportPlayer(player, player, new Location(getIsland(player).getCBWorld(), 3, 67, 3));
        player.setGameMode(GameMode.SURVIVAL);
        return true;
    }

    public static boolean teleportPlayerLobby(Player player) {
        core.teleportPlayer(player, player, new Location(core.getMVWorldManager().getMVWorld("world").getCBWorld(), 3, 67, 3));
        return true;
    }
}
