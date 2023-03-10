//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.aircraft;

import net.minecraft.world.*;
import mcheli.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import mcheli.wrapper.*;
import mcheli.weapon.*;
import java.util.*;

public class MCH_MissileDetector
{
    private MCH_EntityAircraft ac;
    private World world;
    private int alertCount;
    public static final int SEARCH_RANGE = 60;
    
    public MCH_MissileDetector(final MCH_EntityAircraft aircraft, final World w) {
        this.world = w;
        this.ac = aircraft;
        this.alertCount = 0;
    }
    
    public void update() {
        if (!this.ac.haveFlare()) {
            return;
        }
        if (this.alertCount > 0) {
            --this.alertCount;
        }
        final boolean isLocked = this.ac.getEntityData().getBoolean("Tracking");
        if (isLocked) {
            this.ac.getEntityData().setBoolean("Tracking", false);
        }
        if (this.ac.getEntityData().getBoolean("LockOn")) {
            if (this.alertCount == 0) {
                this.alertCount = 10;
                if (this.ac != null && this.ac.haveFlare() && !this.ac.isDestroyed()) {
                    for (int i = 0; i < 2; ++i) {
                        final Entity entity = this.ac.getEntityBySeatId(i);
                        if (entity instanceof EntityPlayerMP) {
                            MCH_PacketNotifyLock.sendToPlayer((EntityPlayer)entity);
                        }
                    }
                }
            }
            this.ac.getEntityData().setBoolean("LockOn", false);
        }
        if (this.ac.isDestroyed()) {
            return;
        }
        Entity rider = this.ac.getRiddenByEntity();
        if (rider == null) {
            rider = this.ac.getEntityBySeatId(1);
        }
        if (rider != null) {
            if (this.ac.isFlareUsing()) {
                this.destroyMissile();
            }
            else if (!this.ac.isUAV() && !this.world.isRemote) {
                if (this.alertCount == 0 && (isLocked || this.isLockedByMissile())) {
                    this.alertCount = 20;
                    W_WorldFunc.MOD_playSoundAtEntity((Entity)this.ac, "alert", 1.0f, 1.0f);
                }
            }
            else if (this.ac.isUAV() && this.world.isRemote && this.alertCount == 0 && (isLocked || this.isLockedByMissile())) {
                this.alertCount = 20;
                if (W_Lib.isClientPlayer(rider)) {
                    W_McClient.MOD_playSoundFX("alert", 1.0f, 1.0f);
                }
            }
        }
    }
    
    public boolean destroyMissile() {
        final List list = this.world.getEntitiesWithinAABB((Class)MCH_EntityBaseBullet.class, this.ac.boundingBox.expand(60.0, 60.0, 60.0));
        if (list != null) {
            for (int i = 0; i < list.size(); ++i) {
                final MCH_EntityBaseBullet msl = list.get(i);
                if (msl.targetEntity != null && (this.ac.isMountedEntity(msl.targetEntity) || msl.targetEntity.equals((Object)this.ac))) {
                    msl.targetEntity = null;
                    msl.setDead();
                }
            }
        }
        return false;
    }
    
    public boolean isLockedByMissile() {
        final List list = this.world.getEntitiesWithinAABB((Class)MCH_EntityBaseBullet.class, this.ac.boundingBox.expand(60.0, 60.0, 60.0));
        if (list != null) {
            for (int i = 0; i < list.size(); ++i) {
                final MCH_EntityBaseBullet msl = list.get(i);
                if (msl.targetEntity != null && (this.ac.isMountedEntity(msl.targetEntity) || msl.targetEntity.equals((Object)this.ac))) {
                    return true;
                }
            }
        }
        return false;
    }
}
