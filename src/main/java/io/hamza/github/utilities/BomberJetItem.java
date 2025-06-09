package io.hamza.github.utilities;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public enum BomberJetItem {
    NUKE_ROCKET(
            CreateItemMeta.createItem(Material.FIREWORK_ROCKET, Component.text("Nuke", TextColor.fromHexString("#cae00d")),
                    true, true), 20),
    SNOWBALL(
            CreateItemMeta.createItem(Material.SNOWBALL, Component.text("Snowball", TextColor.fromHexString("#bbeff6")),
                    true, true), 5),
    AIRSTRIKE(CreateItemMeta.createItem(Material.GOAT_HORN, Component.text("Airstrike", TextColor.fromHexString("#ff51f")),
                    true, true), 50),

    ELYTRA(
            CreateItemMeta.createItem(Material.ELYTRA,
                    Component.text("Elytra", TextColor.fromHexString("#aba6e4")),
                    List.of(Component.text("BomberJet")), true, true));


    private ItemStack itemStack;
    private int explosionStrength;

    BomberJetItem(ItemStack itemStack) {
        this.itemStack = itemStack;
    }


    BomberJetItem(ItemStack itemStack, int explosionStrength) {
        this.itemStack = itemStack;
        this.explosionStrength = explosionStrength;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public int getExplosionStrength() {
        return explosionStrength;
    }

    public Material getMaterial() {
        return itemStack.getType();
    }

}
