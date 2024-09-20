package pl.imbestpumpkin.pumpkincolorfix.colorfix;

import org.bukkit.ChatColor;

public class StringColorFix {
    public static String colorOf(String text) { return ChatColor.translateAlternateColorCodes('&', text); }
}
