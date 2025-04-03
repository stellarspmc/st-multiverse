package fun.spmc.island;

import com.onarandombox.MultiverseCore.api.MultiverseWorld;

public class IslandCoopUtils {

    private static CoopIsland getCoopIsland(MultiverseWorld world) {
        return new CoopIsland(world);
    }

    public static CoopIsland turnIslandIntoCoop(MultiverseWorld world) {
        return getCoopIsland(world);
    }

    //public static boolean removeCoopMembers(CoopIsland island, String... players) {
        //return island.removePlayers(Arrays.stream(players).map(Bukkit::getPlayer).toArray(Player[]::new));
    //}
}
