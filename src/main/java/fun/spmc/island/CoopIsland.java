package fun.spmc.island;

import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import fun.spmc.STMultiverse;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static fun.spmc.STMultiverse.core;

public class CoopIsland {

    private final MultiverseWorld world;
    private final ArrayList<Player> coopMembers;
    private final Player coopLeader;

    public CoopIsland(MultiverseWorld world) {
        this(world, Bukkit.getPlayer(world.getName().replace("island_", "")));
    }

    public CoopIsland(MultiverseWorld world, Player coopLeader) {
        this(world, (ArrayList<Player>) List.of(coopLeader), coopLeader);
    }

    public CoopIsland(ArrayList<Player> coopMembers, Player coopLeader) {
        this(core.getMVWorldManager().getMVWorld("island_%s".formatted(coopLeader.getUniqueId())), coopMembers, coopLeader);
    }

    public CoopIsland(MultiverseWorld world, ArrayList<Player> coopMembers, Player coopLeader) {
        this.world = world;
        this.coopMembers = coopMembers;
        this.coopLeader = coopLeader;

        if (STMultiverse.getPluginConfig().get(String.valueOf(coopLeader.getUniqueId())) == null)
            STMultiverse.getPluginConfig().addDefault("%s.members".formatted(coopLeader.getUniqueId()), coopMembers.stream().map(Player::getName).toArray());
    }

    public ArrayList<Player> getCoopMembers() {
        return coopMembers;
    }

    public Player getCoopLeader() {
        return coopLeader;
    }

    public void addPlayers(Player... players) {
        coopMembers.addAll(List.of(players));
        saveCoop();
    }

    public void removePlayers(Player... players) {
        coopMembers.removeAll(List.of(players));
        saveCoop();
    }

    public boolean playerInCoop(Player player) {
        return coopMembers.contains(player);
    }

    public MultiverseWorld getMVWorld() {
        return world;
    }

    private void saveCoop() {
        STMultiverse.getPluginConfig().set("%s.members".formatted(coopLeader.getUniqueId()), coopMembers.stream().map(Player::getName).toArray());
    }
}
