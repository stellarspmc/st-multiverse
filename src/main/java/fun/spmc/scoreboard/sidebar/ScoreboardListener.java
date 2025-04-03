package fun.spmc.scoreboard.sidebar;

import fun.spmc.STMultiverse;
import me.catcoder.sidebar.ProtocolSidebar;
import me.catcoder.sidebar.Sidebar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class ScoreboardListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Sidebar<Component> sidebar = ProtocolSidebar.newAdventureSidebar(Component.text("spmc.fun").color(NamedTextColor.LIGHT_PURPLE), STMultiverse.getPlugin(STMultiverse.class));

        sidebar.addLine(Component.text(new SimpleDateFormat("dd/MM/yy").format(new Date())).color(NamedTextColor.GRAY));
        sidebar.addBlankLine();
        sidebar.addUpdatableLine(player -> getWorldDate(player.getWorld().getGameTime()));
        sidebar.addUpdatableLine(player -> getWorldTime(player.getWorld().getGameTime()));
        sidebar.addUpdatableLine(this::getLocation);
        sidebar.addBlankLine();
        sidebar.addUpdatableLine(player -> Component.text("Purse:").appendSpace().append(Component.text(STMultiverse.getEcon().getBalance(player)).color(NamedTextColor.GOLD)));
        sidebar.addBlankLine();
        sidebar.addLine(Component.text("mc.spmc.fun").color(NamedTextColor.LIGHT_PURPLE));

        sidebar.updateLinesPeriodically(0, 10);
        sidebar.addViewer(event.getPlayer());
    }

    private Component getWorldDate(long time) {
        long days = (time / 20 * 72) / 60 / 60 / 24;

        int season = (int) ((days / 60) % 4);

        Component component = switch (season) {
            case 0 -> Component.text("Spring").color(NamedTextColor.GREEN);
            case 1 -> Component.text("Summer").color(NamedTextColor.GOLD);
            case 2 -> Component.text("Autumn").color(NamedTextColor.BLUE);
            case 3 -> Component.text("Winter").color(NamedTextColor.AQUA);
            default -> Component.text("Error").color(NamedTextColor.RED);
        };


        return component.appendSpace().append(Component.text(ordinal((int) (days % 60 + 1))));
    }

    private Component getWorldTime(long time) {
        long seconds = time / 20 * 72;
        long millis = seconds * 1000;

        String timeStr = new SimpleDateFormat("hh:mm a").format(new Date(millis));
        return Component.text(timeStr).color(NamedTextColor.GRAY);
    }

    private Component getLocation(Player player) {
        Component returnedValue = Component.text("⌖").appendSpace();

        String worldName = player.getWorld().getName();
        switch (worldName) {
            case "world" -> {
                return returnedValue.append(Component.text("Hub").color(NamedTextColor.RED));
            }
            case "world2" -> {
                return returnedValue.append(Component.text("Skyblock Generator").color(NamedTextColor.BLUE));
            }
            default -> {
                try {
                    UUID realUUID = UUID.fromString(worldName.replace("island_", ""));
                    OfflinePlayer ownerOfIsland = Bukkit.getOfflinePlayer(realUUID);
                    if (ownerOfIsland == player) return returnedValue.append(Component.text("Your Island").color(NamedTextColor.GREEN));
                    return returnedValue.append(Component.text(MessageFormat.format("{0}''s Island", Objects.requireNonNull(ownerOfIsland).getName())).color(NamedTextColor.GREEN));
                } catch (Exception a) {
                    return returnedValue.append(Component.text("Unknown").color(NamedTextColor.GRAY));
                }
            }
        }
    }

    public static String ordinal(int i) {
        String[] suffixes = new String[] { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };
        return switch (i % 100) {
            case 11, 12, 13 -> MessageFormat.format("{0}th", i);
            default -> i + suffixes[i % 10];
        };
    }
}
