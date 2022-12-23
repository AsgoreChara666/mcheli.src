//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.weapon;

import net.minecraft.world.*;
import net.minecraft.util.*;

public class MCH_EntityAAMissile extends MCH_EntityBaseBullet
{
    public MCH_EntityAAMissile(final World par1World) {
        super(par1World);
        this.targetEntity = null;
    }
    
    public MCH_EntityAAMissile(final World par1World, final double posX, final double posY, final double posZ, final double targetX, final double targetY, final double targetZ, final float yaw, final float pitch, final double acceleration) {
        super(par1World, posX, posY, posZ, targetX, targetY, targetZ, yaw, pitch, acceleration);
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.getCountOnUpdate() > 4 && this.getInfo() != null && !this.getInfo().disableSmoke) {
            this.spawnParticle(this.getInfo().trajectoryParticleName, 3, 7.0f * this.getInfo().smokeSize * 0.5f);
        }
        if (!this.worldObj.isRemote && this.getInfo() != null) {
            if (this.shootingEntity != null && this.targetEntity != null && !this.targetEntity.isDead) {
                final double x = this.posX - this.targetEntity.posX;
                final double y = this.posY - this.targetEntity.posY;
                final double z = this.posZ - this.targetEntity.posZ;
                final double d = x * x + y * y + z * z;
                if (d > 3422500.0) {
                    this.setDead();
                }
                else if (this.getCountOnUpdate() > this.getInfo().rigidityTime) {
                    if (this.usingFlareOfTarget(this.targetEntity)) {
                        this.setDead();
                        return;
                    }
                    if (this.getInfo().proximityFuseDist >= 0.1f && d < this.getInfo().proximityFuseDist) {
                        final MovingObjectPosition mop = new MovingObjectPosition(this.targetEntity);
                        this.posX = (this.targetEntity.posX + this.posX) / 2.0;
                        this.posY = (this.targetEntity.posY + this.posY) / 2.0;
                        this.posZ = (this.targetEntity.posZ + this.posZ) / 2.0;
                        this.onImpact(mop, 1.0f);
                    }
                    else {
                        this.guidanceToTarget(this.targetEntity.posX, this.targetEntity.posY, this.targetEntity.posZ);
                    }
                }
            }
            else {
                this.setDead();
            }
        }
    }
    
    @Override
    public MCH_BulletModel getDefaultBulletModel() {
        return MCH_DefaultBulletModels.AAMissile;
    }
}
