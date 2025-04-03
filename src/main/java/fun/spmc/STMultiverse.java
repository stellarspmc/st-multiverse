package fun.spmc;

import com.onarandombox.MultiverseCore.MultiverseCore;
import fun.spmc.commands.TabCompletion;
import fun.spmc.commands.ManageCoopCommand;
import fun.spmc.commands.CreateIslandCommand;
import fun.spmc.econ.EconomyListener;
import fun.spmc.island.CoopCache;
import fun.spmc.scoreboard.sidebar.ScoreboardListener;
import fun.spmc.scoreboard.tab.TabListener;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.NonNull;

import java.util.Objects;

public final class STMultiverse extends JavaPlugin {

    public static MultiverseCore core;

    private static BukkitAudiences adventure;

    public static Economy getEcon() {
        return econ;
    }

    private static Economy econ = null;


    public static @NonNull BukkitAudiences adventure() {
        if(adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return adventure;
    }

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        if (!setupEconomy()) {
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
        adventure = BukkitAudiences.create(this);

        Objects.requireNonNull(getCommand("island")).setExecutor(new CreateIslandCommand());
        Objects.requireNonNull(getCommand("coop")).setExecutor(new ManageCoopCommand());

        Objects.requireNonNull(getCommand("coop")).setTabCompleter(new TabCompletion());
        Objects.requireNonNull(getCommand("island")).setTabCompleter(new TabCompletion());

        getServer().getPluginManager().registerEvents(new CoopCache(), this);
        getServer().getPluginManager().registerEvents(new ScoreboardListener(), this);
        getServer().getPluginManager().registerEvents(new TabListener(), this);
        getServer().getPluginManager().registerEvents(new EconomyListener(), this);
    }

    @Override
    public void onDisable() {
        if(adventure != null) {
            adventure.close();
            adventure = null;
        }
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
}
