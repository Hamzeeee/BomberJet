package io.hamza.github.utilities;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class CreateItemMeta {

    public static ItemStack createItem(Material material, Component displayName, List<Component> lore, boolean enchantmentGlintOverride) {

        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(displayName);
        itemMeta.lore(lore);
        itemMeta.setEnchantmentGlintOverride(enchantmentGlintOverride);

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public static ItemStack createItem(Material material, Component displayName, boolean enchantmentGlintOverride, boolean unbreakable) {

        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(displayName);
        itemMeta.setEnchantmentGlintOverride(enchantmentGlintOverride);
        itemMeta.setUnbreakable(unbreakable);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public static ItemStack createItem(Material material, Component displayName, boolean unbreakable) {

        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(displayName);
        itemMeta.setUnbreakable(unbreakable);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public static ItemStack createItem(Material material, Component displayName, List<Component> lore, boolean enchantmentGlintOverride, boolean unbreakable) {

        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(displayName);
        itemMeta.lore(lore);
        itemMeta.setEnchantmentGlintOverride(enchantmentGlintOverride);
        itemMeta.setUnbreakable(true);

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

}
