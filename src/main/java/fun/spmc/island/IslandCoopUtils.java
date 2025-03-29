package fun.spmc.island;

import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import fun.spmc.STMultiverse;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class IslandCoopUtils {

    public static CoopIsland getCoopIsland(MultiverseWorld world) {
        if (!isCoopIsland(world)) return null;

        return new CoopIsland(world);
    }

    public static boolean isCoopIsland(MultiverseWorld world) {
        String coopLeader = world.getName().replace("island_", "");
        Player coopLeaderPlayer = Bukkit.getPlayer(coopLeader);

        assert coopLeaderPlayer != null;
        return STMultiverse.getPluginConfig().get(String.valueOf(coopLeaderPlayer.getUniqueId())) != null;
    }

    public static CoopIsland turnIslandIntoCoop(MultiverseWorld world) {
        return getCoopIsland(world);
    }

    //public static boolean removeCoopMembers(CoopIsland island, String... players) {
        //return island.removePlayers(Arrays.stream(players).map(Bukkit::getPlayer).toArray(Player[]::new));
    //}
}
