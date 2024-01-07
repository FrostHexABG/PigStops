package me.pigalala.pigstops;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.collect.ImmutableList;

import co.aikar.commands.PaperCommandManager;
import me.pigalala.pigstops.pit.management.PitGame;
import me.pigalala.pigstops.pit.management.pitmodes.Pit;

public final class PigStops extends JavaPlugin {

    private static PigStops plugin;
    public static final HashMap<Player, PitPlayer> pitPlayers = new HashMap<>();
    public static final List<PitGame> pitGames = new ArrayList<>();
    public static PitGame defaultPitGame;

    public static Material pitBlock;

    @Override
    public void onEnable() {
        plugin = this;

        new OinkListener();
        OinkConfig.onStartup();
        setupCommands();
        getLogger().log(Level.INFO, "Enabled PigStops v" + getPluginMeta().getVersion() + " created by Pigalala.");
        getLogger().log(Level.INFO, "PigStops is now maintained by JustBru00. For bug reports and the latest releases, please check out the GitHub page at: https://github.com/JustBru00/PigStops");
    }

    private void setupCommands() {
        PaperCommandManager commandManager = new PaperCommandManager(plugin);

        commandManager.getCommandCompletions().registerAsyncCompletion("pits", c -> {
            List<String> gameNames = new ArrayList<>();
            pitGames.forEach(p -> {
                gameNames.add(p.name);
            });
            return gameNames;
        });

        commandManager.getCommandCompletions().registerAsyncCompletion("items", c -> {
            List<String> e = new ArrayList<>();
            for(Material v : Material.values()) {
                if(v == Material.AIR) continue;
                e.add(v.toString().toLowerCase());
            }
            return e;
        });

        commandManager.getCommandCompletions().registerAsyncCompletion("blocks", c -> {
            List<String> blocks = new ArrayList<>();
            Arrays.stream(Material.values()).toList().forEach(e -> {
                if(!e.isSolid()) return;
                if(!e.isBlock()) return;
                blocks.add(e.toString().toLowerCase());
            });
            return ImmutableList.copyOf(blocks.toArray(new String[0]));
        });

        commandManager.getCommandCompletions().registerAsyncCompletion("pitmode", c -> {
            List<String> modes = new ArrayList<>();
            for(Pit.PitMode pm : Pit.PitMode.values()) {
                modes.add(pm.toString().toLowerCase());
            }
            return modes;
        });

        commandManager.getCommandContexts().registerContext(PitGame.class, PitGame.getPitGameContextResolver());
        commandManager.getCommandContexts().registerContext(Material.class, OinkCommand.getMaterialContextResolver());
        commandManager.getCommandContexts().registerContext(Sound.class, OinkCommand.getSoundContextCompletions());
        commandManager.getCommandContexts().registerContext(Pit.PitMode.class, Pit.PitMode.getPitModeCommandContext());

        commandManager.registerCommand(new OinkCommand());
    }

    public static PigStops getPlugin() {return plugin;}
}