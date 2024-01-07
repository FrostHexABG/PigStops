package me.pigalala.pigstops;

import me.makkuusen.timing.system.tplayer.TPlayer;
import me.pigalala.pigstops.pit.management.PitGame;
import me.pigalala.pigstops.pit.management.pitmodes.Pit;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class OinkMessages {

    private OinkMessages() {
    }

    public static TextComponent getSoloFinishText(String time, int accuracy, int misclicks) {
        return Component.text()
                .content("PigStop finished in ").color(NamedTextColor.GREEN)
                .append(Component.text(time).color(NamedTextColor.GOLD))
                .hoverEvent(
                        Component.text().content(time).color(TextColor.color(0xF38AFF))
                        .appendNewline().append(Component.text("Accuracy: ").color(TextColor.color(0x7BF200)))
                        .append(Component.text(accuracy + "%"))
                        .appendNewline().append(Component.text("Misclicks: ").color(TextColor.color(0x7BF200)))
                        .append(Component.text(misclicks))
                        .build()
                )
                .build();
    }

    public static TextComponent getRaceFinishText(TPlayer player, String pitName, int pit, String time, int accuracy, int misclicks, Pit.PitMode pm) {
        return Component.text().content("").color(NamedTextColor.GREEN)
                .append(Component.text("|| ", player.getSettings().getTextColor(), TextDecoration.BOLD, TextDecoration.ITALIC))
                .append(Component.text(player.getName(), NamedTextColor.WHITE))
                .append(Component.text(" has completed PigStop "))
                .append(Component.text(pit).color(NamedTextColor.GOLD))
                .append(Component.text(" in "))
                .append(Component.text(time).color(NamedTextColor.GOLD))
                .hoverEvent(
                        Component.text().content("").color(TextColor.color(0xF38AFF))
                        .append(Component.text("|| ", player.getSettings().getTextColor(), TextDecoration.BOLD, TextDecoration.ITALIC))
                        .append(Component.text(player.getName(), NamedTextColor.WHITE))
                        .appendNewline().append(Component.text("----------").color(NamedTextColor.GRAY))
                        .appendNewline().append(Component.text(pitName)).append(Component.text(".pigstop").color(NamedTextColor.GRAY))
                        .appendNewline().append(Component.text(pm.getDisplayName()).color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, true))
                        .appendNewline().append(Component.text("----------").color(NamedTextColor.GRAY))
                        .appendNewline().append(Component.text(time))
                        .appendNewline().append(Component.text("Accuracy: ").color(TextColor.color(0x7BF200))).append(Component.text(accuracy + "%"))
                        .appendNewline().append(Component.text("Misclicks: ").color(TextColor.color(0x7BF200))).append(Component.text(misclicks))
                        .build()
                )
                .build();
    }

    public static TextComponent getPitGameInfoText(PitGame pg) {

        return Component.text().content("").color(NamedTextColor.GRAY)
                .append(Component.text("--- "))
                .append(Component.text(pg.name).color(TextColor.color(0xF38AFF))).append(Component.text(".pigstop"))
                .append(Component.text(" ---"))
                .appendNewline().append(Component.text("PitMode: ")).append(Component.text(pg.pitMode.getDisplayName()).color(TextColor.color(0xF38AFF)))
                .appendNewline().append(Component.text("InventorySize: ")).append(Component.text(pg.inventorySize).color(TextColor.color(0xF38AFF)))
                .build();
    }
}
