package fun.spmc.stmultiverse.island;

import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import fun.spmc.stmultiverse.STMultiverse;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Collectors;

import static fun.spmc.stmultiverse.STMultiverse.core;

public class IslandAdminUtils {
    public static boolean teleportToPlayerIsland(Player player, @NotNull String[] strings) {
        if (strings.length > 1) {
            String playerString = strings[1];
            Player target = Bukkit.getPlayer(playerString);
            if (target == null) {
                STMultiverse.adventure().player(player).sendMessage(Component.text("Player not found!").color(NamedTextColor.RED));
                return false;
            }

            MultiverseWorld island = IslandUtils.getIsland(target);
            if (island == null) {
                STMultiverse.adventure().player(player).sendMessage(Component.text("Island not found!").color(NamedTextColor.RED));
                return false;
            }

            core.teleportPlayer(player, player, new Location(island.getCBWorld(), 3, 67, 3));
            return true;
        } else STMultiverse.adventure().player(player).sendMessage(Component.text("Invalid usage!").color(NamedTextColor.RED));
        return false;
    }

    public static boolean teleportPlayerToLobby(@NotNull String[] strings) {
        if (strings.length > 1) {
            String playerString = strings[1];
            Player target = Bukkit.getPlayer(playerString);
            if (target == null) return false;

            return IslandUtils.teleportPlayerLobby(target);
        } return false;
    }

    public static boolean deleteIsland(@NotNull String[] strings) {
        if (strings.length > 1) {
            String playerString = strings[1];
            Player target = Bukkit.getPlayer(playerString);
            if (target == null) return false;

            return IslandUtils.deleteIsland(target);
        } return false;
    }

    public static boolean reloadConfig() {
        STMultiverse.getPlugin(STMultiverse.class).reloadConfig();
        return true;
    }

    public static boolean getCoopList(Audience audience) {
        audience.sendMessage(Component.text("List of coops:").appendSpace().append(Component.text(CoopCache.getIslands().stream().map(CoopIsland::getCoopLeader).map(Player::getName).collect(Collectors.joining(", "))).color(NamedTextColor.AQUA)));
        return true;
    }
}
