package fun.spmc.stmultiverse.island;

import fun.spmc.stmultiverse.STMultiverse;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class CoopCache implements Listener {
    private static final ArrayList<CoopIsland> islands = new ArrayList<>();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (STMultiverse.getPlugin(STMultiverse.class).getConfig().get(MessageFormat.format("{0}.members", p.getUniqueId())) != null)
            islands.add(new CoopIsland((ArrayList<Player>) STMultiverse.getPlugin(STMultiverse.class).getConfig().getStringList(MessageFormat.format("{0}.members", p.getUniqueId())).stream().map(UUID::fromString).map(Bukkit::getPlayer).collect(Collectors.toList()), p.getPlayer()));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        CoopIsland island = getIslandByOwner(p);
        if (island == null) return;
        islands.remove(island);
    }

    public static CoopIsland getIsland(Player player) {
        int index = -1;
        for (CoopIsland island : islands) {
            if (island.playerInCoop(player)) index = islands.indexOf(island);
        }

        if (index == -1) return null;
        return islands.get(index);
    }

    public static CoopIsland getIslandByOwner(Player player) {
        Optional<CoopIsland> optional = islands.stream().filter(island -> island.getCoopLeader().equals(player)).findFirst();
        return optional.orElse(null);
    }

    protected static void removeIsland(CoopIsland island) {
        STMultiverse.getPlugin(STMultiverse.class).getConfig().set(island.getCoopLeader().getUniqueId().toString(), null);
        STMultiverse.getPlugin(STMultiverse.class).saveConfig();
        islands.remove(island);
    }

    protected static ArrayList<CoopIsland> getIslands() {
        return islands;
    }

    protected static void addIsland(CoopIsland island) {
        islands.add(island);
    }

}
