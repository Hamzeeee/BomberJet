package io.hamza.github.listener;

import io.hamza.github.utilities.BomberJetItem;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Random;

public class AirStrikeListener implements Listener {

    int radius = 90;
    int spawn_height = 80;

    @EventHandler
    public void onJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Inventory inventory = player.getInventory();
        inventory.setItem(2, BomberJetItem.AIRSTRIKE.getItemStack());
    }

    @EventHandler
    public void callStrike(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        if (!(itemInHand.isSimilar(BomberJetItem.AIRSTRIKE.getItemStack()))) return;

        // TODO: Fix Arrow not exploding
        for (int i = 0; i < 30; i++) {
            int x = player.getLocation().getBlockX() + new Random().nextInt(-radius, radius);
            int z = player.getLocation().getBlockZ() + new Random().nextInt(-radius, radius);
            World world = player.getWorld();
            Location location = new Location(world, x, player.getY() + spawn_height, z);
            Fireball fireball = world.spawn(location, Fireball.class);
            fireball.setDirection(new Vector(0, -1, 0));
            fireball.setYield(BomberJetItem.AIRSTRIKE.getExplosionStrength());
        }
    }
}
