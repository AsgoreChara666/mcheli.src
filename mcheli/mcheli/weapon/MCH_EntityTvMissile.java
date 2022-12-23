//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.weapon;

import net.minecraft.world.*;
import mcheli.aircraft.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public class MCH_EntityTvMissile extends MCH_EntityBaseBullet
{
    public boolean isSpawnParticle;
    
    public MCH_EntityTvMissile(final World par1World) {
        super(par1World);
        this.isSpawnParticle = true;
    }
    
    public MCH_EntityTvMissile(final World par1World, final double posX, final double posY, final double posZ, final double targetX, final double targetY, final double targetZ, final float yaw, final float pitch, final double acceleration) {
        super(par1World, posX, posY, posZ, targetX, targetY, targetZ, yaw, pitch, acceleration);
        this.isSpawnParticle = true;
    }
    
    public void onUpdate() {
        super.onUpdate();
        if (this.isSpawnParticle && this.getInfo() != null && !this.getInfo().disableSmoke) {
            this.spawnParticle(this.getInfo().trajectoryParticleName, 3, 5.0f * this.getInfo().smokeSize * 0.5f);
        }
        if (this.shootingEntity != null) {
            final double x = this.posX - this.shootingEntity.posX;
            final double y = this.posY - this.shootingEntity.posY;
            final double z = this.posZ - this.shootingEntity.posZ;
            if (x * x + y * y + z * z > 1440000.0) {
                this.setDead();
            }
            if (!this.worldObj.isRemote && !this.isDead) {
                this.onUpdateMotion();
            }
        }
        else if (!this.worldObj.isRemote) {
            this.setDead();
        }
    }
    
    public void onUpdateMotion() {
        final Entity e = this.shootingEntity;
        if (e != null && !e.isDead) {
            final MCH_EntityAircraft ac = MCH_EntityAircraft.getAircraft_RiddenOrControl(e);
            if (ac != null && ac.getTVMissile() == this) {
                final float yaw = e.rotationYaw;
                final float pitch = e.rotationPitch;
                final double tX = -MathHelper.sin(yaw / 180.0f * 3.1415927f) * MathHelper.cos(pitch / 180.0f * 3.1415927f);
                final double tZ = MathHelper.cos(yaw / 180.0f * 3.1415927f) * MathHelper.cos(pitch / 180.0f * 3.1415927f);
                final double tY = -MathHelper.sin(pitch / 180.0f * 3.1415927f);
                this.setMotion(tX, tY, tZ);
                this.setRotation(yaw, pitch);
            }
        }
    }
    
    public MCH_BulletModel getDefaultBulletModel() {
        return MCH_DefaultBulletModels.ATMissile;
    }
}
