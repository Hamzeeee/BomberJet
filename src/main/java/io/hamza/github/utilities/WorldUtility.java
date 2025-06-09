package io.hamza.github.utilities;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WorldUtility {
    public static double blocksPerSecond(Player player, HashMap<Player, Location> playerLocationHashMap,
                                         HashMap<Player, Long> playerLastTimeHashMap) {


        int delay = 1;
        int ticksInMilliseconds = ticksConverter(delay);
        long timePeriod = System.currentTimeMillis() + ticksInMilliseconds;

        Location location = player.getLocation();

        if (!(playerLocationHashMap.containsKey(player) && playerLastTimeHashMap.containsKey(player))) {
            playerLastTimeHashMap.put(player, timePeriod);
            playerLocationHashMap.put(player, location);
        }


        // TODO: Rewrite blocksPerSecond logic.. 
        double distance = player.getLocation().distance(playerLocationHashMap.get(player));
        double speed = (distance / delay) * 20;
        playerLastTimeHashMap.remove(player, timePeriod);
        playerLocationHashMap.remove(player, location);

        return speed;

    }

    public static int ticksConverter(int ticks) {
        return ticks * 50;
    }


}