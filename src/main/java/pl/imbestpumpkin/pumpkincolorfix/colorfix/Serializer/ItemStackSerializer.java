package pl.imbestpumpkin.pumpkincolorfix.colorfix.Serializer;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;

import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;

import java.util.*;

import static pl.imbestpumpkin.pumpkincolorfix.colorfix.StringColorFix.colorOf;

// This code is modified version of ItemStack Serializer from Spigot Forum

public class ItemStackSerializer {
    public static String serialize(ItemStack item) {
        StringBuilder builder = new StringBuilder();
        builder.append(item.getType().toString());
        if (item.getDurability() != 0) builder.append(":" + item.getDurability());
        builder.append(" " + item.getAmount());
        for (Enchantment enchant:item.getEnchantments().keySet())
            builder.append(" " + enchant.getName() + ":" + item.getEnchantments().get(enchant));

        String dName = getDisplayName(item);
        if (dName != null) builder.append(" displayName:").append(dName);

        String name = getName(item);
        if (name != null) builder.append(" name:" + name);

        String itemFlags = getItemFlags(item);
        if (itemFlags != null) builder.append(" itemFlags:" + itemFlags);

        boolean unbreakable = getUnbreakeble(item);
        if (unbreakable) builder.append(" unbreakable:" + unbreakable);

        Integer customModelData = getCustomModelData(item);
        if (customModelData != null) builder.append(" customModelData:" + customModelData);

        String lore = getLore(item);
        if (lore != null) builder.append(" lore:" + lore);

        String enchMeta = getEnchantmentMeta(item); // EnchantmentStorageMeta
        if (enchMeta != null) builder.append(" enchMeta:" + enchMeta);

        Color potionColor = getPotionColor(item);
        if (potionColor != null) builder.append(" potioncolorrgb:" + potionColor.getRed() + "|" + potionColor.getGreen() + "|" + potionColor.getBlue());

        Color color = getArmorColor(item);
        if (color != null) builder.append(" rgb:" + color.getRed() + "|" + color.getGreen() + "|" + color.getBlue());

        String owner = getOwner(item);
        if (owner != null) builder.append(" owner:" + owner);

        return builder.toString();
    }
    public static ItemStack deserialize(String serializedItem){
        String[] strings = serializedItem.split(" ");
        Map<Enchantment, Integer> enchants = new HashMap<Enchantment, Integer>();
        String[] args;
        ItemStack item = new ItemStack(Material.AIR);
        for (String str: strings) {
            args = str.split(":");
            if(Material.matchMaterial(args[0]) != null && item.getType() == Material.AIR){
                item.setType(Material.matchMaterial(args[0]));
                if(args.length == 2) item.setDurability(Short.parseShort(args[1]));
                break;
            }
        }

        if (item.getType() == Material.AIR) return null;

        for(String str: strings){
            args = str.split(":", 2);
            if (isNumber(args[0])) item.setAmount(Integer.parseInt(args[0]));
            if (args.length == 1) continue;
            if (args[0].equalsIgnoreCase("name")) {
                setName(item, colorOf(args[1]));
                continue;
            }
            if (args[0].equalsIgnoreCase("displayName")) {
                setDisplayName(item, colorOf(args[1]));
                continue;
            }
            if (args[0].equalsIgnoreCase("customModelData")) {
                setCustomModelData(item, Integer.parseInt(args[1]));
                continue;
            }
            if (args[0].equalsIgnoreCase("unbreakable")) {
                setUnbreakable(item, Boolean.parseBoolean(args[1]));
                continue;
            }
            if (args[0].equalsIgnoreCase("lore")) {
                setLore(item, colorOf(args[1]));
                continue;
            }

            if (args[0].equalsIgnoreCase("enchMeta")) {
                setEnchantmentMeta(item, args[1]);
                continue;
            }
            if (args[0].equalsIgnoreCase("potioncolorrgb")) {
                setPotionColor(item, args[1]);
                continue;
            }
            if (args[0].equalsIgnoreCase("rgb")) {
                setArmorColor(item, args[1]);
                continue;
            }
            if (args[0].equalsIgnoreCase("owner")){
                setOwner(item, args[1]);
                continue;
            }
            if (args[0].equalsIgnoreCase("itemFlags")) {
                setItemFlags(item, args[1]);
                continue;
            }
            String[] key = null;
            String prefix = args[0];
            if (args.length == 2 && prefix.equalsIgnoreCase("pumpkin"))
                key = args[1].split(":");


            if (Enchantment.getByName(args[0].toUpperCase()) != null || (key != null && Enchantment.getByKey(new NamespacedKey(prefix, key[0])) != null)) {
                if (Enchantment.getByName(args[0].toUpperCase()) != null) {
                    enchants.put(Enchantment.getByName(args[0].toUpperCase()), Integer.parseInt(args[1]));
                    item.addUnsafeEnchantment(Enchantment.getByName(args[0].toUpperCase()), Integer.parseInt(args[1]));
                } else {
                    enchants.put(Enchantment.getByKey(new NamespacedKey(prefix, key[0])), Integer.parseInt(key[1]));
                    item.addUnsafeEnchantment(Enchantment.getByKey(new NamespacedKey(prefix, key[0])), Integer.parseInt(key[1]));
                }

                continue;
            }
            item.addUnsafeEnchantments(enchants);

        }

        return item.getType().equals(Material.AIR) ? null : item;
    }

    private static String getOwner(ItemStack item){
        if(!(item.getItemMeta() instanceof SkullMeta)) return null;
        return ((SkullMeta)item.getItemMeta()).getOwner();
    }

    private static void setOwner(ItemStack item, String owner){
        try {
            SkullMeta meta = (SkullMeta) item.getItemMeta();
            meta.setOwner(owner);
            item.setItemMeta(meta);
        } catch (Exception exception) {
            return;
        }
    }

    private static Integer getCustomModelData(ItemStack item) {
        if(!item.hasItemMeta()) return null;
        if(!item.getItemMeta().hasCustomModelData()) return null;
        return item.getItemMeta().getCustomModelData();
    }

    private static void setUnbreakable(ItemStack item, boolean unbreakable) {
        ItemMeta meta = item.getItemMeta();
        meta.setUnbreakable(unbreakable);
        item.setItemMeta(meta);
    }

    private static boolean getUnbreakeble(ItemStack item){
        if (!item.hasItemMeta()) return false;
        if (!item.getItemMeta().isUnbreakable()) return false;
        return true;
    }

    private static void setCustomModelData(ItemStack item, int customModelData) {
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(customModelData);
        item.setItemMeta(meta);
    }

    private static String getDisplayName(ItemStack item){
        if (!item.hasItemMeta()) return null;
        if (!item.getItemMeta().hasDisplayName()) return null;
        return item.getItemMeta().getDisplayName().replace(" ", "_").replace(ChatColor.COLOR_CHAR, '&');
    }

    private static void setDisplayName(ItemStack item, String name){
        name = name.replace("_", " ");
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
    }

    private static String getName(ItemStack item){
        if (!item.hasItemMeta()) return null;
        if (!item.getItemMeta().hasItemName()) return null;
        return item.getItemMeta().getItemName().replace(" ", "_").replace(ChatColor.COLOR_CHAR, '&');
    }

    private static void setName(ItemStack item, String name){
        name = name.replace("_", " ");
        ItemMeta meta = item.getItemMeta();
        meta.setItemName(name);
        item.setItemMeta(meta);
    }

    private static String getItemFlags(ItemStack item){
        if (!item.hasItemMeta()) return null;
        if (!item.getItemMeta().getItemFlags().contains(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)) return null;

        StringBuilder builder = new StringBuilder();
        List<ItemFlag> itemFlags = new ArrayList<>();
        itemFlags.addAll(item.getItemMeta().getItemFlags());

        for(int ind = 0; ind < itemFlags.size(); ind++){
            builder.append((ind > 0 ? "|" : "") + itemFlags.get(ind).name());
        }
        return builder.toString();
    }

    private static void setItemFlags(ItemStack item, String itemFlags) {
        ItemMeta meta = item.getItemMeta();
        List<String> itemFlagsList = Arrays.asList(itemFlags.split("\\|"));

        for (String itemFlag: itemFlagsList) {
            meta.addItemFlags(ItemFlag.valueOf(itemFlag));
        }
        item.setItemMeta(meta);
    }

    private static String getLore(ItemStack item){
        if (!item.hasItemMeta()) return null;
        if (!item.getItemMeta().hasLore()) return null;
        StringBuilder builder = new StringBuilder();
        List<String> lore = item.getItemMeta().getLore();
        for(int ind = 0;ind<lore.size();ind++){
            builder.append((ind > 0 ? "|" : "") + lore.get(ind).replace(" ", "_").replace(ChatColor.COLOR_CHAR, '&'));
        }
        return builder.toString();
    }

    private static void setLore(ItemStack item, String lore){
        lore = lore.replace("_", " ");
        ItemMeta meta = item.getItemMeta();
        meta.setLore(Arrays.asList(lore.split("\\|")));
        item.setItemMeta(meta);
    }

    private static Color getArmorColor(ItemStack item){
        if (!(item.getItemMeta() instanceof LeatherArmorMeta)) return null;
        return ((LeatherArmorMeta)item.getItemMeta()).getColor();
    }

    private static Color getPotionColor(ItemStack item) {
        if (!(item.getItemMeta() instanceof PotionMeta)) return null;
        return ((PotionMeta)item.getItemMeta()).getColor();
    }

    private static String getEnchantmentMeta(ItemStack item) {
        if (!item.hasItemMeta()) return null;
        try {
            if (!((EnchantmentStorageMeta) item.getItemMeta()).hasStoredEnchants()) return null;
        } catch (Exception e) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        Map<NamespacedKey, Integer> enchs = new HashMap<>();
        for (Map.Entry<Enchantment, Integer> entry : ((EnchantmentStorageMeta) item.getItemMeta()).getStoredEnchants().entrySet()) {
            enchs.put(entry.getKey().getKey(), entry.getValue());
        }
        for (Map.Entry<NamespacedKey, Integer> entry : enchs.entrySet()) {
            builder.append(entry.getKey() + "|" + entry.getValue() + "|");
        }
        return builder.toString();
    }

    private static void setEnchantmentMeta(ItemStack item, String enchs) {
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();
        String[] enchsl = enchs.split("\\|");
        for (int i = 0; i != enchsl.length-1; i++) {
            String[] parts = enchsl[i].split(":");
            if (parts.length == 2) {
                meta.addStoredEnchant(Registry.ENCHANTMENT.get(new NamespacedKey(parts[0], parts[1])), Integer.parseInt(enchsl[(i + 1)]), true);
            }
        }

        item.setItemMeta(meta);
    }

    private static void setArmorColor(ItemStack item, String str){
        try {
            String[] colors = str.split("\\|");
            int red = Integer.parseInt(colors[0]);
            int green = Integer.parseInt(colors[1]);
            int blue = Integer.parseInt(colors[2]);
            LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
            meta.setColor(Color.fromRGB(red, green, blue));
            item.setItemMeta(meta);
        } catch (Exception ignored) {}
    }

    private static void setPotionColor(ItemStack item, String str){
        try {
            String[] colors = str.split("\\|");
            int red = Integer.parseInt(colors[0]);
            int green = Integer.parseInt(colors[1]);
            int blue = Integer.parseInt(colors[2]);
            PotionMeta meta = (PotionMeta) item.getItemMeta();
            meta.setColor(Color.fromRGB(red, green, blue));
            item.setItemMeta(meta);
        } catch (Exception ignored) {}
    }

    private static boolean isNumber(String str){
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException exception) {
            return false;
        }
        return true;
    }
}

