//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli;

import net.minecraftforge.event.*;
import mcheli.command.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import mcheli.wrapper.*;
import net.minecraftforge.event.entity.living.*;
import java.util.*;
import net.minecraftforge.event.entity.player.*;
import mcheli.chain.*;
import mcheli.aircraft.*;
import net.minecraft.item.*;
import net.minecraftforge.event.entity.*;
import mcheli.weapon.*;

public class MCH_EventHook extends W_EventHook
{
    @Override
    public void commandEvent(final CommandEvent event) {
        MCH_Command.onCommandEvent(event);
    }
    
    @Override
    public void entitySpawn(final EntityJoinWorldEvent event) {
        if (W_Lib.isEntityLivingBase(event.entity) && !W_EntityPlayer.isPlayer(event.entity)) {
            final Entity entity = event.entity;
            final double renderDistanceWeight = entity.renderDistanceWeight;
            final MCH_Config config = MCH_MOD.config;
            entity.renderDistanceWeight = renderDistanceWeight * MCH_Config.MobRenderDistanceWeight.prmDouble;
        }
        else if (event.entity instanceof MCH_EntityAircraft) {
            final MCH_EntityAircraft aircraft = (MCH_EntityAircraft)event.entity;
            if (!aircraft.worldObj.isRemote && !aircraft.isCreatedSeats()) {
                aircraft.createSeats(UUID.randomUUID().toString());
            }
        }
        else if (W_EntityPlayer.isPlayer(event.entity)) {
            final Entity e = event.entity;
            boolean b = Float.isNaN(e.rotationPitch);
            b |= Float.isNaN(e.prevRotationPitch);
            b |= Float.isInfinite(e.rotationPitch);
            b |= Float.isInfinite(e.prevRotationPitch);
            if (b) {
                MCH_Lib.Log(event.entity, "### EntityJoinWorldEvent Error:Player invalid rotation pitch(" + e.rotationPitch + ")", new Object[0]);
                e.rotationPitch = 0.0f;
                e.prevRotationPitch = 0.0f;
            }
            b = Float.isInfinite(e.rotationYaw);
            b |= Float.isInfinite(e.prevRotationYaw);
            b |= Float.isNaN(e.rotationYaw);
            b |= Float.isNaN(e.prevRotationYaw);
            if (b) {
                MCH_Lib.Log(event.entity, "### EntityJoinWorldEvent Error:Player invalid rotation yaw(" + e.rotationYaw + ")", new Object[0]);
                e.rotationYaw = 0.0f;
                e.prevRotationYaw = 0.0f;
            }
            if (!e.worldObj.isRemote && event.entity instanceof EntityPlayerMP) {
                MCH_Lib.DbgLog(false, "EntityJoinWorldEvent:" + event.entity, new Object[0]);
                MCH_PacketNotifyServerSettings.send((EntityPlayerMP)event.entity);
            }
        }
    }
    
    @Override
    public void livingAttackEvent(final LivingAttackEvent event) {
        final MCH_EntityAircraft ac = this.getRiddenAircraft(event.entity);
        if (ac == null) {
            return;
        }
        if (ac.getAcInfo() == null) {
            return;
        }
        if (ac.isDestroyed()) {
            return;
        }
        if (ac.getAcInfo().damageFactor > 0.0f) {
            return;
        }
        final Entity attackEntity = event.source.getEntity();
        if (attackEntity == null) {
            event.setCanceled(true);
        }
        else if (W_Entity.isEqual(attackEntity, event.entity)) {
            event.setCanceled(true);
        }
        else if (ac.isMountedEntity(attackEntity)) {
            event.setCanceled(true);
        }
        else {
            final MCH_EntityAircraft atkac = this.getRiddenAircraft(attackEntity);
            if (W_Entity.isEqual((Entity)atkac, (Entity)ac)) {
                event.setCanceled(true);
            }
        }
    }
    
    @Override
    public void livingHurtEvent(final LivingHurtEvent event) {
        final MCH_EntityAircraft ac = this.getRiddenAircraft(event.entity);
        if (ac == null) {
            return;
        }
        if (ac.getAcInfo() == null) {
            return;
        }
        if (ac.isDestroyed()) {
            return;
        }
        final Entity attackEntity = event.source.getEntity();
        if (attackEntity == null) {
            ac.attackEntityFrom(event.source, event.ammount * 2.0f);
            event.ammount *= ac.getAcInfo().damageFactor;
        }
        else if (W_Entity.isEqual(attackEntity, event.entity)) {
            ac.attackEntityFrom(event.source, event.ammount * 2.0f);
            event.ammount *= ac.getAcInfo().damageFactor;
        }
        else if (ac.isMountedEntity(attackEntity)) {
            event.ammount = 0.0f;
            event.setCanceled(true);
        }
        else {
            final MCH_EntityAircraft atkac = this.getRiddenAircraft(attackEntity);
            if (W_Entity.isEqual((Entity)atkac, (Entity)ac)) {
                event.ammount = 0.0f;
                event.setCanceled(true);
            }
            else {
                ac.attackEntityFrom(event.source, event.ammount * 2.0f);
                event.ammount *= ac.getAcInfo().damageFactor;
            }
        }
    }
    
    public MCH_EntityAircraft getRiddenAircraft(final Entity entity) {
        MCH_EntityAircraft ac = null;
        final Entity ridden = entity.ridingEntity;
        if (ridden instanceof MCH_EntityAircraft) {
            ac = (MCH_EntityAircraft)ridden;
        }
        else if (ridden instanceof MCH_EntitySeat) {
            ac = ((MCH_EntitySeat)ridden).getParent();
        }
        if (ac == null) {
            final List list = entity.worldObj.getEntitiesWithinAABB((Class)MCH_EntityAircraft.class, entity.boundingBox.expand(50.0, 50.0, 50.0));
            if (list != null) {
                for (int i = 0; i < list.size(); ++i) {
                    final MCH_EntityAircraft tmp = list.get(i);
                    if (tmp.isMountedEntity(entity)) {
                        return tmp;
                    }
                }
            }
        }
        return ac;
    }
    
    @Override
    public void entityInteractEvent(final EntityInteractEvent event) {
        final ItemStack item = event.entityPlayer.getHeldItem();
        if (item == null) {
            return;
        }
        if (item.getItem() instanceof MCH_ItemChain) {
            MCH_ItemChain.interactEntity(item, event.target, event.entityPlayer, event.entityPlayer.worldObj);
            event.setCanceled(true);
        }
        else if (item.getItem() instanceof MCH_ItemAircraft) {
            ((MCH_ItemAircraft)item.getItem()).rideEntity(item, event.target, event.entityPlayer);
        }
    }
    
    @Override
    public void entityCanUpdate(final EntityEvent.CanUpdate event) {
        if (event.entity instanceof MCH_EntityBaseBullet) {
            final MCH_EntityBaseBullet bullet = (MCH_EntityBaseBullet)event.entity;
            bullet.setDead();
        }
    }
}
