package pl.imbestpumpkin.pumpkincolorfix.colorfix;

import org.bukkit.ChatColor;
import pl.imbestpumpkin.pumpkincolorfix.colorfix.Exception.ValueOfMapIsNotStringException;

import java.util.HashMap;
import java.util.Map;

public class MapColorFix {
    public static Map<Object, String> colorOf(Map<Object, Object> map) throws ValueOfMapIsNotStringException {
        for (Map. Entry<Object, Object> entry : map.entrySet()) {
            if (!(entry.getValue() instanceof String)) {
                throw new ValueOfMapIsNotStringException();
            } else {
                break;
            }
        }

        Map<Object, String> coloredMap = new HashMap<>();
        map.forEach((k, v) -> {
            coloredMap.put(k, ChatColor.translateAlternateColorCodes('&', v.toString()));
        });
        return coloredMap;
    }
}
