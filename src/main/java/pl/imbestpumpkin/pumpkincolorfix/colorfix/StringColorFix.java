package pl.imbestpumpkin.pumpkincolorfix.colorfix;

import org.bukkit.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringColorFix {
    public static String colorOf(String text, boolean andSign, boolean fromHex) {
        String colored = text;
        colored = andSign ? ChatColor.translateAlternateColorCodes('&', colored) : text;

        Matcher matcher = Pattern.compile("<#([A-Fa-f0-9]{6})>").matcher(colored);
        StringBuffer buffer = new StringBuffer();

        while (matcher.find()) {
            String hexColor = matcher.group(1);
            matcher.appendReplacement(buffer, net.md_5.bungee.api.ChatColor.of("#" + hexColor).toString());
        }
        matcher.appendTail(buffer);

        colored = fromHex ? buffer.toString() : text;
        return colored;
    }

}
