package com.casperboost;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RenderCullingHandler {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onRenderLiving(RenderLivingEvent.Pre<EntityLivingBase> event) {
        if (!CasperConfig.enableEntityCulling) return;

        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.player;
        if (player == null) return;

        EntityLivingBase entity = event.getEntity();
        if (entity == null || entity == player) return;

        double dx = entity.posX - player.posX;
        double dy = entity.posY - player.posY;
        double dz = entity.posZ - player.posZ;
        double distSq = dx * dx + dy * dy + dz * dz;

        // 1. Distance culling based on entity category
        double maxDist;
        if (entity instanceof IMob) {
            maxDist = CasperConfig.maxMobDistance;
        } else if (entity instanceof EntityAnimal) {
            maxDist = CasperConfig.maxPassiveDistance;
        } else {
            maxDist = CasperConfig.maxMobDistance;
        }

        if (distSq > (maxDist * maxDist)) {
            event.setCanceled(true);
            return;
        }

        // 2. FOV / Frustum Culling (Skip entities behind player's camera)
        if (CasperConfig.enableFovCulling && distSq > 9.0D) { // Only check if outside 3 blocks melee radius
            Vec3d lookVec = player.getLookVec();
            double len = Math.sqrt(dx * dx + dz * dz);
            if (len > 0.001D) {
                double dirX = dx / len;
                double dirZ = dz / len;
                // Dot product of horizontal look direction and entity direction
                double dot = lookVec.x * dirX + lookVec.z * dirZ;
                // If dot product is negative or less than cos(85 deg) ~= 0.08, entity is out of horizontal view
                if (dot < 0.05D) {
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onFogDensity(EntityViewRenderEvent.FogDensity event) {
        if (CasperConfig.disableFog) {
            // Cancel fog rendering to eliminate GPU fragment shader overhead on Intel UHD 600
            event.setDensity(0.0F);
            event.setCanceled(true);
        }
    }
}
