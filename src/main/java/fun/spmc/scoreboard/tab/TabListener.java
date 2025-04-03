package fun.spmc.scoreboard.tab;

import fun.spmc.STMultiverse;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class TabListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Audience audience = STMultiverse.adventure().player(player);

        Component header = Component.text("Welcome").appendSpace().append(Component.text(player.getName()).color(NamedTextColor.GREEN));
        Component footer = Component.text("You are playing on").appendSpace().append(Component.text("mc.spmc.fun").color(NamedTextColor.LIGHT_PURPLE));

        audience.sendPlayerListHeaderAndFooter(header, footer);
    }
}
