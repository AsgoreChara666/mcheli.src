//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli;

import com.google.common.io.*;
import net.minecraft.entity.*;
import mcheli.wrapper.*;
import mcheli.lweapon.*;
import mcheli.aircraft.*;
import net.minecraft.entity.player.*;

public class MCH_CommonPacketHandler
{
    public static void onPacketEffectExplosion(final EntityPlayer player, final ByteArrayDataInput data) {
        if (!player.worldObj.isRemote) {
            return;
        }
        final MCH_PacketEffectExplosion pkt = new MCH_PacketEffectExplosion();
        pkt.readData(data);
        final Entity exploder = null;
        if (player.getDistanceSq(pkt.prm.posX, pkt.prm.posY, pkt.prm.posZ) <= 40000.0) {
            if (!pkt.prm.inWater) {
                final MCH_Config config = MCH_MOD.config;
                if (!MCH_Config.DefaultExplosionParticle.prmBool) {
                    MCH_Explosion.effectExplosion(player.worldObj, exploder, pkt.prm.posX, pkt.prm.posY, pkt.prm.posZ, pkt.prm.size, true);
                }
                else {
                    MCH_Explosion.DEF_effectExplosion(player.worldObj, exploder, pkt.prm.posX, pkt.prm.posY, pkt.prm.posZ, pkt.prm.size, true);
                }
            }
            else {
                MCH_Explosion.effectExplosionInWater(player.worldObj, exploder, pkt.prm.posX, pkt.prm.posY, pkt.prm.posZ, pkt.prm.size, true);
            }
        }
    }
    
    public static void onPacketIndOpenScreen(final EntityPlayer player, final ByteArrayDataInput data) {
        if (player.worldObj.isRemote) {
            return;
        }
        final MCH_PacketIndOpenScreen pkt = new MCH_PacketIndOpenScreen();
        pkt.readData(data);
        if (pkt.guiID == 3) {
            final MCH_EntityAircraft ac = MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)player);
            if (ac != null) {
                ac.openInventory(player);
            }
        }
        else {
            player.openGui((Object)MCH_MOD.instance, pkt.guiID, player.worldObj, (int)player.posX, (int)player.posY, (int)player.posZ);
        }
    }
    
    public static void onPacketNotifyServerSettings(final EntityPlayer player, final ByteArrayDataInput data) {
        if (!player.worldObj.isRemote) {
            return;
        }
        MCH_Lib.DbgLog(false, "onPacketNotifyServerSettings:" + player, new Object[0]);
        final MCH_PacketNotifyServerSettings pkt = new MCH_PacketNotifyServerSettings();
        pkt.readData(data);
        if (!pkt.enableCamDistChange) {
            W_Reflection.setThirdPersonDistance(4.0f);
        }
        MCH_ServerSettings.enableCamDistChange = pkt.enableCamDistChange;
        MCH_ServerSettings.enableEntityMarker = pkt.enableEntityMarker;
        MCH_ServerSettings.enablePVP = pkt.enablePVP;
        MCH_ServerSettings.stingerLockRange = pkt.stingerLockRange;
        MCH_ServerSettings.enableDebugBoundingBox = pkt.enableDebugBoundingBox;
        MCH_ServerSettings.enableRotationLimit = pkt.enableRotationLimit;
        MCH_ServerSettings.pitchLimitMax = pkt.pitchLimitMax;
        MCH_ServerSettings.pitchLimitMin = pkt.pitchLimitMin;
        MCH_ServerSettings.rollLimit = pkt.rollLimit;
        MCH_ClientLightWeaponTickHandler.lockRange = MCH_ServerSettings.stingerLockRange;
    }
    
    public static void onPacketNotifyLock(final EntityPlayer player, final ByteArrayDataInput data) {
        final MCH_PacketNotifyLock pkt = new MCH_PacketNotifyLock();
        pkt.readData(data);
        if (!player.worldObj.isRemote) {
            if (pkt.entityID >= 0) {
                final Entity target = player.worldObj.getEntityByID(pkt.entityID);
                if (target != null) {
                    MCH_EntityAircraft ac = null;
                    if (target instanceof MCH_EntityAircraft) {
                        ac = (MCH_EntityAircraft)target;
                    }
                    else if (target instanceof MCH_EntitySeat) {
                        ac = ((MCH_EntitySeat)target).getParent();
                    }
                    else {
                        ac = MCH_EntityAircraft.getAircraft_RiddenOrControl(target);
                    }
                    if (ac != null && ac.haveFlare() && !ac.isDestroyed()) {
                        for (int i = 0; i < 2; ++i) {
                            final Entity entity = ac.getEntityBySeatId(i);
                            if (entity instanceof EntityPlayerMP) {
                                MCH_PacketNotifyLock.sendToPlayer((EntityPlayer)entity);
                            }
                        }
                    }
                    else if (target.ridingEntity != null && target instanceof EntityPlayerMP) {
                        MCH_PacketNotifyLock.sendToPlayer((EntityPlayer)target);
                    }
                }
            }
        }
        else {
            MCH_MOD.proxy.clientLocked();
        }
    }
}
