package com.casperboost;

import net.minecraftforge.common.config.Configuration;
import java.io.File;

public class CasperConfig {
    public static Configuration config;

    public static boolean enableFastMath = true;
    public static boolean enableEntityCulling = true;
    public static boolean enableFovCulling = true;
    public static double maxMobDistance = 28.0D;
    public static double maxPassiveDistance = 20.0D;
    public static double maxItemDistance = 16.0D;
    public static boolean disableFog = true;
    public static boolean enableMemoryWatcher = true;
    public static int memoryTrimIntervalTicks = 3600;

    public static void init(File configFile) {
        if (config == null) {
            config = new Configuration(configFile);
            load();
        }
    }

    public static void load() {
        try {
            config.load();

            enableFastMath = config.getBoolean("enableFastMath", "optimizations", true,
                    "Enables high-speed trigonometric math lookup tables instead of standard Math.");

            enableEntityCulling = config.getBoolean("enableEntityCulling", "rendering", true,
                    "Stops rendering entities beyond tight distance limits to save draw calls on Intel UHD 600.");

            enableFovCulling = config.getBoolean("enableFovCulling", "rendering", true,
                    "Skips rendering entities completely behind the player's view direction.");

            maxMobDistance = config.getFloat("maxMobDistance", "rendering", 28.0F, 8.0F, 64.0F,
                    "Maximum render distance in blocks for hostile mobs.");

            maxPassiveDistance = config.getFloat("maxPassiveDistance", "rendering", 20.0F, 6.0F, 48.0F,
                    "Maximum render distance in blocks for animals/passive mobs.");

            maxItemDistance = config.getFloat("maxItemDistance", "rendering", 16.0F, 4.0F, 32.0F,
                    "Maximum render distance in blocks for dropped items and XP orbs.");

            disableFog = config.getBoolean("disableFog", "rendering", true,
                    "Bypasses OpenGL fog rendering to eliminate GPU fillrate overhead.");

            enableMemoryWatcher = config.getBoolean("enableMemoryWatcher", "memory", true,
                    "Monitors JVM heap and trims texture/sound memory before low-RAM freezing occurs.");

            memoryTrimIntervalTicks = config.getInt("memoryTrimIntervalTicks", "memory", 3600, 600, 24000,
                    "Tick interval (20 ticks = 1 sec) for periodic memory health checks.");

        } catch (Exception e) {
            System.err.println("[CasperBoost] Failed to load config: " + e.getMessage());
        } finally {
            if (config.hasChanged()) {
                config.save();
            }
        }
    }
}
