package io.hamza.github.listener;

import io.hamza.github.main.Main;
import io.hamza.github.utilities.BomberJetItem;
import io.hamza.github.utilities.BomberJetRules;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class BomberListener implements Listener {

    private final HashMap<Player, Location> playerLocationHashMap;
    private final HashMap<Player, Short> flyspeedHashMap;
    private final ArrayList<UUID> itemUUIDArrayList;

    /**
     * - playerLocationHashMap is used to calculate the distance between Location A and Location B
     * - flySpeedHashMap is used in BomberListener#throwArrowBomb to check for minimum flySpeed
     * - itemUUIDArrayList is used to check for particular items e. g not every Arrow/Snowball which hits the ground is going to explode
     */

    private ItemStack nuke;
    private ItemStack elytra;
    private ItemStack snowball;
    private final int tick = 10;
    private BossBar bar;

    public BomberListener() {
        this.playerLocationHashMap = new HashMap<>();
        this.flyspeedHashMap = new HashMap<>();
        this.itemUUIDArrayList = new ArrayList<>();
    }

    // TODO: Add comments for more readability
    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!playerLocationHashMap.containsKey(player)) {
            playerLocationHashMap.put(player, player.getLocation());
        }

        bar = Bukkit.createBossBar(
                "0.00B/s",
                BarColor.WHITE,
                BarStyle.SOLID
        );

        bar.addPlayer(player);
        bar.setProgress(0);

        nuke = BomberJetItem.NUKE_ROCKET.getItemStack();
        elytra = BomberJetItem.ELYTRA.getItemStack();
        snowball = BomberJetItem.SNOWBALL.getItemStack();

        player.getInventory().setItem(0, nuke);
        player.getInventory().setChestplate(elytra);
        player.getInventory().setItem(1, snowball);

        /*
         * (Blocks_Per_n_ticks / tick) * 20 = Blocks per Second
         *
         */
        Bukkit.getScheduler().runTaskTimer(Main.getPlugin(), () -> {
            if (!player.isOnline()) return;
            double speed = player.getLocation().distance(playerLocationHashMap.get(player).clone());
            double BLOCKS_PER_SECOND = (speed / tick) * 20;
            flyspeedHashMap.put(player, (short) BLOCKS_PER_SECOND);

            String title = String.format("%.2f", BLOCKS_PER_SECOND) + "B/s";
            bar.setTitle(title);
            bar.setProgress(BomberJetRules.setBossBarProgress(BLOCKS_PER_SECOND));
            bar.setColor(BomberJetRules.setBossBarColorRules(BLOCKS_PER_SECOND));

            playerLocationHashMap.put(player, player.getLocation().clone());
        }, 0, tick);

    }

    @EventHandler
    public void inventoryClickEvent(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) return;
        ItemStack item = event.getCurrentItem();

        if (item == null) return;

        if (item.isSimilar(elytra) || item.isSimilar(nuke) || item.isSimilar(snowball)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void playerQuitEven(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        playerLocationHashMap.remove(player);
        flyspeedHashMap.remove(player);
    }

    @EventHandler
    public void openBombBay(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (!(event.getAction() == Action.RIGHT_CLICK_AIR)) return;

        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        if (itemInHand.isSimilar(nuke)) {
            throwArrowBomb(player, flyspeedHashMap.get(player));
            return;
        }

        if (event.getAction() == Action.RIGHT_CLICK_AIR && itemInHand.isSimilar(snowball)) {
            itemUUIDArrayList.add(player.getUniqueId());
        }
    }

    @EventHandler
    public void throwSnowBall(ProjectileHitEvent event) {
        if (!(event.getEntity().getShooter() instanceof Player player)) return;

        Location location = event.getEntity().getLocation();

        // Code Translated means: If player is NOT null AND the player's UUID is inside the itemUUID ArrayList AND the projectile item is a Snowball
        final boolean CHECK_IF_PARTICULAR_SNOWBALL = itemUUIDArrayList.contains(player.getUniqueId()) && event.getEntity().getType() == EntityType.SNOWBALL;

        if (CHECK_IF_PARTICULAR_SNOWBALL) {
            location.createExplosion(BomberJetItem.SNOWBALL.getExplosionStrength(), true);
            itemUUIDArrayList.remove(player.getUniqueId());
            return;
        }

        if (itemUUIDArrayList.contains(event.getEntity().getUniqueId())) {
            location.createExplosion(BomberJetItem.NUKE_ROCKET.getExplosionStrength(), true);
            itemUUIDArrayList.remove(event.getEntity().getUniqueId());
        }
    }

    public void throwArrowBomb(Player player, int speed) {
        if (!(isWearingElytra(player) && player.isGliding() && speed >= BomberJetRules.MINIMUM_SPEED.getMinSpeed())) return;
        Entity entity = player.getLocation().getWorld().spawnEntity(player.getLocation(), EntityType.ARROW);
        Arrow arrow = (Arrow) entity;
        arrow.setShooter(player);
        itemUUIDArrayList.add(entity.getUniqueId());
    }

    public boolean isWearingElytra(Player player) {
        return Objects.requireNonNull(player.getInventory().getChestplate()).isSimilar(elytra);
    }

}
