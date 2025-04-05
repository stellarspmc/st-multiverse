package fun.spmc.stmultiverse.econ;

import fun.spmc.stmultiverse.STMultiverse;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class EconomyListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Economy econ = STMultiverse.getEcon();

       if (!econ.hasAccount(player)) econ.createPlayerAccount(player);
    }

}
