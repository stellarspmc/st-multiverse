package fun.spmc.island;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class CoopInviteCache {
    private static final HashMap<CoopIsland, Player> coopInviteCache = new HashMap<>();

    public static boolean invitePlayer(CoopIsland island, Player player) {
        if (!coopInviteCache.containsValue(player) && CoopCache.getIslandByOwner(player) == null) {
            coopInviteCache.put(island, player);
            return true;
        }
        return false;
    }

    public static boolean hasInvited(CoopIsland island, Player player) {
        return coopInviteCache.containsKey(island) && coopInviteCache.get(island).equals(player);
    }

    public static boolean isInvited(Player player) {
        return coopInviteCache.containsValue(player);
    }

    public static CoopIsland getInvitation(Player player) {
        if (!isInvited(player)) return null;
        for (Map.Entry<CoopIsland, Player> entry : coopInviteCache.entrySet()) {
            if (entry.getValue().equals(player)) return entry.getKey();
        }
        return null;
    }

    public static void acceptInvite(CoopIsland island, Player player) {
        coopInviteCache.remove(island, player);
        island.addPlayers(player);
    }

    public static void rejectInvite(CoopIsland island, Player player) {
        coopInviteCache.remove(island, player);
    }
}
