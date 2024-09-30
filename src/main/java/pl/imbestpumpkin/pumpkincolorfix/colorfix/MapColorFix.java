package pl.imbestpumpkin.pumpkincolorfix.colorfix;

import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.Map;

public class MapColorFix {
    public static Map<Object, String> colorOf(Map<Object, String> map){

        Map<Object, String> coloredMap = new HashMap<>();
        map.forEach((k, v) -> {
            coloredMap.put(k, ChatColor.translateAlternateColorCodes('&', v.toString()));
        });
        return coloredMap;
    }
}
