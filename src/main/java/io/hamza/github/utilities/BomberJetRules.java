package io.hamza.github.utilities;

import org.bukkit.boss.BarColor;

import java.util.logging.Logger;

public enum BomberJetRules {
    MAXIMAL_SPEED(20, 30, BarColor.RED, (double) 3 / 4),
    MINIMUM_SPEED(10, 20, BarColor.YELLOW, (double) 2 / 4),
    LOW_SPEED(1, 10, BarColor.GREEN, (double) 1 / 4);

    private final double minSpeed;
    private final double maxSpeed;
    private final BarColor color;
    private final double progress;
    private static final double fullSpeed = 30;

    BomberJetRules(double minSpeed, double maxSpeed, BarColor color, double progress) {
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
        this.color = color;
        this.progress = progress;
    }

    public static BarColor setBossBarColorRules(double blocksPerSecond) {
        if (blocksPerSecond >= fullSpeed)
            return BarColor.RED;


        for (BomberJetRules rules : values()) {
            if (rules.minSpeed <= blocksPerSecond && rules.maxSpeed >= blocksPerSecond) {
                return rules.color;
            }
        }

        return BarColor.WHITE;
    }

    public static double setBossBarProgress(double blocksPerSecond) {
        if (blocksPerSecond >= fullSpeed) return 1.0;

        for (BomberJetRules rules : values()) {
            if (rules.minSpeed <= blocksPerSecond && rules.maxSpeed >= blocksPerSecond) return rules.progress;
        }

        return 0;
    }

    public double getMinSpeed() {
        return BomberJetRules.MINIMUM_SPEED.minSpeed;
    }

}
