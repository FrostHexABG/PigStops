package me.pigalala.pigstops;

import me.makkuusen.timing.system.api.TimingSystemAPI;
import me.makkuusen.timing.system.heat.Heat;
import me.makkuusen.timing.system.participant.Driver;
import me.pigalala.pigstops.pit.management.PitGame;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;

import static me.pigalala.pigstops.PigStops.getPlugin;

public abstract class Utils {

    public static final String pitNameBase = "§dPigStops §7§l- §d";

    public static String getCustomMessage(String message, String... replacements) {

        for (int i = 0; i < replacements.length; i += 2) {
            message = message.replace(replacements[i], replacements[i + 1]);
        }

        message = ChatColor.translateAlternateColorCodes('&', message);
        return message;
    }

    public static void broadcastMessage(TextComponent message, Heat heat, @Nullable String simpleMessage) {
        heat.getParticipants().forEach(participant -> {
            if(participant.getTPlayer().getPlayer() == null) return;
            participant.getTPlayer().getPlayer().sendMessage(message);
        });
        if(simpleMessage != null) PigStops.getPlugin().getLogger().log(Level.INFO, heat.getName() + " Pit: " + simpleMessage);
    }

    public static void createNewPitFile(String path, String name, Integer invSize) {
        File f = new File(path);
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        YamlConfiguration yamlConfig = YamlConfiguration.loadConfiguration(f);

        yamlConfig.set("name", name);
        yamlConfig.set("invsize", invSize);
        yamlConfig.set("background", new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
        yamlConfig.set("pitmode", "DEFAULT");
        for (int i = 0; i < 54; i++) {
            yamlConfig.set("item" + i, new ItemStack(Material.AIR));
        }

        try {
            yamlConfig.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setDefaultPitGame(PitGame pitGame) {
        if(pitGame == null) getPlugin().getConfig().set("pitgame", "");
        else getPlugin().getConfig().set("pitGame", pitGame.getPath());

        getPlugin().saveConfig();
        getPlugin().reloadConfig();
        PigStops.defaultPitGame = pitGame;
    }

    public static void setPitBlock(Material block) {
        PigStops.pitBlock = block;
        PigStops.getPlugin().getConfig().set("pitBlock", PigStops.pitBlock.name().toLowerCase());
        PigStops.getPlugin().saveConfig();
        PigStops.getPlugin().reloadConfig();
    }

    public static boolean isPlayerHeatDriver(UUID uuid) {
        Optional<Driver> driver = TimingSystemAPI.getDriverFromRunningHeat(uuid);
        return driver.isPresent();
    }

    public static boolean isPlayerHeatDriver(Player p) {
        return isPlayerHeatDriver(p.getUniqueId());
    }
}
