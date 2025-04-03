package fun.spmc.island;

import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import fun.spmc.STMultiverse;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.text.MessageFormat;
import java.util.*;

import static fun.spmc.STMultiverse.core;

public class CoopIsland {

    private final MultiverseWorld world;
    private final ArrayList<Player> coopMembers;
    private final Player coopLeader;

    public CoopIsland(MultiverseWorld world) {
        this(world, Bukkit.getPlayer(UUID.fromString(world.getName().replace("island_", ""))));
    }

    public CoopIsland(MultiverseWorld world, Player coopLeader) {
        this(world, new ArrayList<>(Collections.singletonList(coopLeader)), coopLeader);
    }

    public CoopIsland(ArrayList<Player> coopMembers, Player coopLeader) {
        this(core.getMVWorldManager().getMVWorld("island_%s".formatted(coopLeader.getUniqueId())), coopMembers, coopLeader);
    }

    public CoopIsland(MultiverseWorld world, ArrayList<Player> coopMembers, Player coopLeader) {
        this.world = world;
        this.coopMembers = coopMembers;
        this.coopLeader = coopLeader;

        if (STMultiverse.getPlugin(STMultiverse.class).getConfig().get(MessageFormat.format("{0}.members", coopLeader.getUniqueId())) == null) {
            STMultiverse.getPlugin(STMultiverse.class).getConfig().set(MessageFormat.format("{0}.members", coopLeader.getUniqueId()), (coopMembers.stream().map(Player::getUniqueId).map(UUID::toString)).toArray());
            STMultiverse.getPlugin(STMultiverse.class).saveConfig();
        }
    }

    public ArrayList<Player> getCoopMembers() {
        return coopMembers;
    }

    public Player getCoopLeader() {
        return coopLeader;
    }

    public void addPlayers(Player... players) {
        Collections.addAll(coopMembers, players);
        saveCoop();
    }

    public void removePlayers(Player... players) {
        coopMembers.removeAll(List.of(players));
        saveCoop();
    }

    public boolean playerInCoop(Player player) {
        return coopMembers.contains(player) || coopLeader == player;
    }

    public MultiverseWorld getMVWorld() {
        return world;
    }

    private void saveCoop() {
        STMultiverse.getPlugin(STMultiverse.class).getConfig().set(MessageFormat.format("{0}.members", coopLeader.getUniqueId()), (coopMembers.stream().map(Player::getUniqueId).map(UUID::toString)).toArray());
        STMultiverse.getPlugin(STMultiverse.class).saveConfig();
    }
}
