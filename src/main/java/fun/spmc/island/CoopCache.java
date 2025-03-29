package fun.spmc.island;

import fun.spmc.STMultiverse;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class CoopCache implements Listener {
    private static final ArrayList<CoopIsland> islands = new ArrayList<>();

    public CoopCache() {
        Bukkit.getOnlinePlayers().stream().filter(p -> STMultiverse.getPluginConfig().get(String.valueOf(p.getUniqueId())) != null).map(p -> new CoopIsland((ArrayList<Player>) STMultiverse.getPluginConfig().getStringList(String.valueOf(p.getUniqueId())).stream().map(Bukkit::getPlayer).collect(Collectors.toList()), p.getPlayer())).forEach(islands::add);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (STMultiverse.getPluginConfig().get(String.valueOf(p.getUniqueId())) != null)
            islands.add(new CoopIsland((ArrayList<Player>) STMultiverse.getPluginConfig().getStringList(String.valueOf(p.getUniqueId())).stream().map(Bukkit::getPlayer).collect(Collectors.toList()), p.getPlayer()));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (STMultiverse.getPluginConfig().get(String.valueOf(p.getUniqueId())) != null)
            islands.remove(new CoopIsland((ArrayList<Player>) STMultiverse.getPluginConfig().getStringList(String.valueOf(p.getUniqueId())).stream().map(Bukkit::getPlayer).collect(Collectors.toList()), p.getPlayer()));
    }

    public static CoopIsland getIsland(Player player) {
        AtomicReference<CoopIsland> returnedIsland = new AtomicReference<>();
        islands.stream().filter(island -> island.playerInCoop(player)).forEach(returnedIsland::set);
        return returnedIsland.get();
    }

}
