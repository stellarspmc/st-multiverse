package fun.spmc;

import com.onarandombox.MultiverseCore.MultiverseCore;
import fun.spmc.commands.TabCompletion;
import fun.spmc.commands.coop.ManageCoopCommand;
import fun.spmc.commands.island.CreateIslandCommand;
import fun.spmc.island.CoopCache;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class STMultiverse extends JavaPlugin {

    public static MultiverseCore core;

    private static FileConfiguration config;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        config = getConfig();

        core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");

        Objects.requireNonNull(getCommand("island")).setExecutor(new CreateIslandCommand());
        Objects.requireNonNull(getCommand("coop")).setExecutor(new ManageCoopCommand());

        Objects.requireNonNull(getCommand("coop")).setTabCompleter(new TabCompletion());
        Objects.requireNonNull(getCommand("island")).setTabCompleter(new TabCompletion());

        getServer().getPluginManager().registerEvents(new CoopCache(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static @NotNull FileConfiguration getPluginConfig() {
        return config;
    }
}
