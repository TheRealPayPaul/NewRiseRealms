package com.paulpanosch.newriserealms;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemManager {
    public static ItemStack getPowerfulCrystal() {
        ItemStack item = new ItemStack(Material.DIAMOND);
        ItemMeta meta = item.getItemMeta();

        List<String> lore = new ArrayList<>();
        lore.add("A very powerful crystal");
        lore.add("forged in the end");
        lore.add("by the Ender Dragon itself");

        meta.setDisplayName("Powerful Crystal");
        meta.setLocalizedName("Powerful Crystal");
        meta.setLore(lore);
        meta.setCustomModelData(1);
        meta.setUnbreakable(true);
        meta.addEnchant(Enchantment.DURABILITY, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        item.setItemMeta(meta);

        return item;
    }
}
