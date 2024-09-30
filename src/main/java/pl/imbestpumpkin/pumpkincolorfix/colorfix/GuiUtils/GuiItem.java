package pl.imbestpumpkin.pumpkincolorfix.colorfix.GuiUtils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.imbestpumpkin.pumpkincolorfix.colorfix.ListColorFix;

import java.util.List;

import static pl.imbestpumpkin.pumpkincolorfix.colorfix.StringColorFix.colorOf;

public class GuiItem {

    public static ItemStack createGuiItem(Material material) {
        return new ItemStack(material);
    }
    public static ItemStack createGuiItem(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(colorOf(name, true, true));
        itemMeta.setItemName(colorOf(name, true, true));
        item.setItemMeta(itemMeta);
        return item;
    }
    public static ItemStack createGuiItem(Material material, String name, List<String> lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(colorOf(name, true, true));
        itemMeta.setItemName(colorOf(name, true, true));
        itemMeta.setLore(ListColorFix.colorOf(lore));
        item.setItemMeta(itemMeta);
        return item;
    }
    public static ItemStack createGuiItem(Material material, String name, List<String> lore, int count) {
        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(colorOf(name, true, true));
        itemMeta.setItemName(colorOf(name, true, true));
        itemMeta.setLore(ListColorFix.colorOf(lore));
        item.setAmount(count);
        item.setItemMeta(itemMeta);
        return item;
    }
    public static ItemStack createGuiItem(Material material, String name, List<String> lore, Integer customModelData) {
        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(colorOf(name, true, true));
        itemMeta.setItemName(colorOf(name, true, true));
        itemMeta.setLore(ListColorFix.colorOf(lore));
        itemMeta.setCustomModelData(customModelData);
        item.setAmount(1);
        item.setItemMeta(itemMeta);
        return item;
    }
    public static ItemStack createGuiItem(Material material, String name, List<String> lore, int count, int customModelData) {
        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(colorOf(name, true, true));
        itemMeta.setItemName(colorOf(name, true, true));
        itemMeta.setLore(ListColorFix.colorOf(lore));
        itemMeta.setCustomModelData(customModelData);
        item.setAmount(count);
        item.setItemMeta(itemMeta);
        return item;
    }
    @Deprecated
    public static ItemStack createGuiItem(Material material, ItemMeta meta) {
        ItemStack item = new ItemStack(material);
        item.setItemMeta(meta);
        return item;
    }
}
