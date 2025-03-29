package fun.spmc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TabCompletion implements TabCompleter {

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        switch (command.getName()) {
            case "coop" -> {
                if (strings.length == 1) return List.of("list", "invite", "remove", "accept", "reject");

            }
            case "island" -> {
                 switch (strings.length) {
                    case 1 -> { return List.of("create", "tp", "lobby", "coop", "delete"); }
                    case 2 -> {
                        if (strings[0].equals("tp")) return List.of("solo", "coop");
                    }
                }
            }
        }

        return List.of();
    }
}
