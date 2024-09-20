package pl.imbestpumpkin.pumpkincolorfix.colorfix;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class ListColorFix {
    public static List<String> colorOf(List<String> list) {
        List<String> coloredList = new ArrayList<>();

        for (String s: list) {
            coloredList.add(ChatColor.translateAlternateColorCodes('&', s));
        }

        return coloredList;
    }
}
