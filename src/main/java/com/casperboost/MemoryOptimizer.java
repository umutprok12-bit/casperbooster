package com.casperboost;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class MemoryOptimizer {
    private int tickCounter = 0;

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (!CasperConfig.enableMemoryWatcher) return;

        tickCounter++;
        if (tickCounter >= CasperConfig.memoryTrimIntervalTicks) {
            tickCounter = 0;
            performMemoryTrim(false);
        }
    }

    @SubscribeEvent
    public void onDisconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        // Thorough cleanup when leaving a world/server to free RAM for Casper C370
        performMemoryTrim(true);
    }

    private void performMemoryTrim(boolean aggressive) {
        Runtime runtime = Runtime.getRuntime();
        long maxMem = runtime.maxMemory();
        long totalMem = runtime.totalMemory();
        long freeMem = runtime.freeMemory();
        long usedMem = totalMem - freeMem;

        // If used memory exceeds 82% of max heap or on world disconnect
        if (aggressive || (maxMem > 0 && ((double) usedMem / (double) maxMem) > 0.82D)) {
            System.gc();
        }
    }
}
