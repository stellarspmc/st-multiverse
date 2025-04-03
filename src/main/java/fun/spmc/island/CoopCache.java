package fun.spmc.island;

import fun.spmc.STMultiverse;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public class CoopCache implements Listener {
    private static final ArrayList<CoopIsland> islands = new ArrayList<>();

    public CoopCache() {
        Bukkit.getOnlinePlayers().stream().filter(p -> STMultiverse.getPlugin(STMultiverse.class).getConfig().get(MessageFormat.format("{0}.members", p.getUniqueId())) != null).map(p -> new CoopIsland((ArrayList<Player>) STMultiverse.getPlugin(STMultiverse.class).getConfig().getStringList(String.valueOf(p.getUniqueId())).stream().map(Bukkit::getPlayer).collect(Collectors.toList()), p.getPlayer())).forEach(islands::add);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (STMultiverse.getPlugin(STMultiverse.class).getConfig().get(String.valueOf(p.getUniqueId())) != null)
            islands.add(new CoopIsland((ArrayList<Player>) STMultiverse.getPlugin(STMultiverse.class).getConfig().getStringList(MessageFormat.format("{0}.members", p.getUniqueId())).stream().map(Bukkit::getPlayer).collect(Collectors.toList()), p.getPlayer()));
        System.out.println(islands);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (STMultiverse.getPlugin(STMultiverse.class).getConfig().get(String.valueOf(p.getUniqueId())) != null)
            islands.remove(new CoopIsland((ArrayList<Player>) STMultiverse.getPlugin(STMultiverse.class).getConfig().getStringList(MessageFormat.format("{0}.members", p.getUniqueId())).stream().map(Bukkit::getPlayer).collect(Collectors.toList()), p.getPlayer()));
    }

    public static CoopIsland getIsland(Player player) {
        int index = -1;
        for (CoopIsland island : islands) if (island.playerInCoop(player)) index = islands.indexOf(island);

        if (index == -1) return null;
        return islands.get(index);
    }

    public static CoopIsland getIslandByOwner(Player player) {
        Optional<CoopIsland> optional = islands.stream().filter(island -> island.getCoopLeader().equals(player)).findFirst();
        return optional.orElse(null);
    }

    protected static void addIsland(CoopIsland island) {
        islands.add(island);
    }

    protected static void removeIsland(CoopIsland island) {
        islands.remove(island);
    }

}
