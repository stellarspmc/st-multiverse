package fun.spmc.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TabCompletion implements TabCompleter {

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        switch (command.getName()) {
            case "coop" -> {
                if (strings.length == 1) return List.of("list", "invite", "remove", "accept", "reject");
                else if (strings.length > 1) if (strings[0].equals("invite") || strings[0].equals("remove")) return Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();
            }
            case "island" -> {
                 switch (strings.length) {
                    case 1 -> { return List.of("create", "tp", "coop", "delete"); }
                    case 2 -> {
                        if (strings[0].equals("tp")) return List.of("solo", "coop");
                    }
                }
            }
            case "isa" -> {
                if (strings.length == 1) return List.of("forcetp", "forcelobby", "forcedelete", "reload", "cooplist");
                else if (strings.length > 1) if (strings[0].equals("forcetp") || strings[0].equals("forcelobby") || strings[0].equals("forcedelete")) return Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();
            }
            case "visit" -> {
                if (strings.length == 1) return Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();
            }
        }

        return List.of();
    }
}
