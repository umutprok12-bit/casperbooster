package com.casperboost;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(
    modid = CasperBoostMod.MODID,
    name = CasperBoostMod.NAME,
    version = CasperBoostMod.VERSION,
    clientSideOnly = true,
    acceptedMinecraftVersions = "[1.12.2]"
)
public class CasperBoostMod {
    public static final String MODID = "casperboost";
    public static final String NAME = "CasperBoost";
    public static final String VERSION = "1.0.0";

    public static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        logger.info("[CasperBoost] Initializing Ultra-Low Spec optimizations for Casper C370...");

        // Load or create configuration
        CasperConfig.init(event.getSuggestedConfigurationFile());

        // Register client render and memory event listeners
        MinecraftForge.EVENT_BUS.register(new RenderCullingHandler());
        MinecraftForge.EVENT_BUS.register(new MemoryOptimizer());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        logger.info("[CasperBoost] Active features: FastMath=" + CasperConfig.enableFastMath
                + ", EntityCulling=" + CasperConfig.enableEntityCulling
                + ", FogDisabled=" + CasperConfig.disableFog
                + ", MemoryWatcher=" + CasperConfig.enableMemoryWatcher);
    }
}
