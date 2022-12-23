//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.weapon;

import net.minecraft.world.*;
import net.minecraft.util.*;

public class MCH_EntityATMissile extends MCH_EntityBaseBullet
{
    public int guidanceType;
    
    public MCH_EntityATMissile(final World par1World) {
        super(par1World);
        this.guidanceType = 0;
    }
    
    public MCH_EntityATMissile(final World par1World, final double posX, final double posY, final double posZ, final double targetX, final double targetY, final double targetZ, final float yaw, final float pitch, final double acceleration) {
        super(par1World, posX, posY, posZ, targetX, targetY, targetZ, yaw, pitch, acceleration);
        this.guidanceType = 0;
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.getInfo() != null && !this.getInfo().disableSmoke && this.ticksExisted >= this.getInfo().trajectoryParticleStartTick) {
            this.spawnParticle(this.getInfo().trajectoryParticleName, 3, 5.0f * this.getInfo().smokeSize * 0.5f);
        }
        if (!this.worldObj.isRemote) {
            if (this.shootingEntity != null && this.targetEntity != null && !this.targetEntity.isDead) {
                if (this.usingFlareOfTarget(this.targetEntity)) {
                    this.setDead();
                    return;
                }
                this.onUpdateMotion();
            }
            else {
                this.setDead();
            }
        }
        final double a = (float)Math.atan2(this.motionZ, this.motionX);
        this.rotationYaw = (float)(a * 180.0 / 3.141592653589793) - 90.0f;
        final double r = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationPitch = -(float)(Math.atan2(this.motionY, r) * 180.0 / 3.141592653589793);
    }
    
    public void onUpdateMotion() {
        final double x = this.targetEntity.posX - this.posX;
        final double y = this.targetEntity.posY - this.posY;
        final double z = this.targetEntity.posZ - this.posZ;
        double d = x * x + y * y + z * z;
        if (d > 2250000.0 || this.targetEntity.isDead) {
            this.setDead();
        }
        else if (this.getInfo().proximityFuseDist >= 0.1f && d < this.getInfo().proximityFuseDist) {
            final MovingObjectPosition mop = new MovingObjectPosition(this.targetEntity);
            mop.entityHit = null;
            this.onImpact(mop, 1.0f);
        }
        else {
            final int rigidityTime = this.getInfo().rigidityTime;
            final float af = (this.getCountOnUpdate() < rigidityTime + this.getInfo().trajectoryParticleStartTick) ? 0.5f : 1.0f;
            if (this.getCountOnUpdate() > rigidityTime) {
                if (this.guidanceType == 1) {
                    if (this.getCountOnUpdate() <= rigidityTime + 20) {
                        this.guidanceToTarget(this.targetEntity.posX, this.shootingEntity.posY + 150.0, this.targetEntity.posZ, af);
                    }
                    else if (this.getCountOnUpdate() <= rigidityTime + 30) {
                        this.guidanceToTarget(this.targetEntity.posX, this.shootingEntity.posY, this.targetEntity.posZ, af);
                    }
                    else {
                        if (this.getCountOnUpdate() == rigidityTime + 35) {
                            this.setPower((int)(this.getPower() * 1.2f));
                            if (this.explosionPower > 0) {
                                ++this.explosionPower;
                            }
                        }
                        this.guidanceToTarget(this.targetEntity.posX, this.targetEntity.posY, this.targetEntity.posZ, af);
                    }
                }
                else {
                    d = MathHelper.sqrt_double(d);
                    this.motionX = x * this.acceleration / d * af;
                    this.motionY = y * this.acceleration / d * af;
                    this.motionZ = z * this.acceleration / d * af;
                }
            }
        }
    }
    
    @Override
    public MCH_BulletModel getDefaultBulletModel() {
        return MCH_DefaultBulletModels.ATMissile;
    }
}
