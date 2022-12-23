//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.weapon;

import net.minecraft.world.*;
import mcheli.wrapper.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.block.*;

public class MCH_EntityASMissile extends MCH_EntityBaseBullet
{
    public double targetPosX;
    public double targetPosY;
    public double targetPosZ;
    
    public MCH_EntityASMissile(final World par1World) {
        super(par1World);
        this.targetPosX = 0.0;
        this.targetPosY = 0.0;
        this.targetPosZ = 0.0;
    }
    
    @Override
    public float getGravity() {
        if (this.getBomblet() == 1) {
            return -0.03f;
        }
        return super.getGravity();
    }
    
    @Override
    public float getGravityInWater() {
        if (this.getBomblet() == 1) {
            return -0.03f;
        }
        return super.getGravityInWater();
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.getInfo() != null && !this.getInfo().disableSmoke && this.getBomblet() == 0) {
            this.spawnParticle(this.getInfo().trajectoryParticleName, 3, 10.0f * this.getInfo().smokeSize * 0.5f);
        }
        if (this.getInfo() != null && !this.worldObj.isRemote && this.isBomblet != 1) {
            final Block block = W_WorldFunc.getBlock(this.worldObj, (int)this.targetPosX, (int)this.targetPosY, (int)this.targetPosZ);
            if (block != null && block.isCollidable()) {
                final double dist = this.getDistance(this.targetPosX, this.targetPosY, this.targetPosZ);
                if (dist < this.getInfo().proximityFuseDist) {
                    if (this.getInfo().bomblet > 0) {
                        for (int i = 0; i < this.getInfo().bomblet; ++i) {
                            this.sprinkleBomblet();
                        }
                    }
                    else {
                        final MovingObjectPosition mop = new MovingObjectPosition((Entity)this);
                        this.onImpact(mop, 1.0f);
                    }
                    this.setDead();
                }
                else if (this.getGravity() == 0.0) {
                    double up = 0.0;
                    if (this.getCountOnUpdate() < 10) {
                        up = 20.0;
                    }
                    final double x = this.targetPosX - this.posX;
                    final double y = this.targetPosY + up - this.posY;
                    final double z = this.targetPosZ - this.posZ;
                    final double d = MathHelper.sqrt_double(x * x + y * y + z * z);
                    this.motionX = x * this.acceleration / d;
                    this.motionY = y * this.acceleration / d;
                    this.motionZ = z * this.acceleration / d;
                }
                else {
                    final double x2 = this.targetPosX - this.posX;
                    double y2 = this.targetPosY - this.posY;
                    y2 *= 0.3;
                    final double z2 = this.targetPosZ - this.posZ;
                    final double d2 = MathHelper.sqrt_double(x2 * x2 + y2 * y2 + z2 * z2);
                    this.motionX = x2 * this.acceleration / d2;
                    this.motionZ = z2 * this.acceleration / d2;
                }
            }
        }
        final double a = (float)Math.atan2(this.motionZ, this.motionX);
        this.rotationYaw = (float)(a * 180.0 / 3.141592653589793) - 90.0f;
        final double r = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationPitch = -(float)(Math.atan2(this.motionY, r) * 180.0 / 3.141592653589793);
        this.onUpdateBomblet();
    }
    
    @Override
    public void sprinkleBomblet() {
        if (!this.worldObj.isRemote) {
            final MCH_EntityASMissile e = new MCH_EntityASMissile(this.worldObj, this.posX, this.posY, this.posZ, this.motionX, this.motionY, this.motionZ, (float)this.rand.nextInt(360), 0.0f, this.acceleration);
            e.setParameterFromWeapon(this, this.shootingAircraft, this.shootingEntity);
            e.setName(this.getName());
            final float MOTION = 0.5f;
            final float RANDOM = this.getInfo().bombletDiff;
            e.motionX = this.motionX * 0.5 + (this.rand.nextFloat() - 0.5f) * RANDOM;
            e.motionY = this.motionY * 0.5 / 2.0 + (this.rand.nextFloat() - 0.5f) * RANDOM / 2.0f;
            e.motionZ = this.motionZ * 0.5 + (this.rand.nextFloat() - 0.5f) * RANDOM;
            e.setBomblet();
            this.worldObj.spawnEntityInWorld((Entity)e);
        }
    }
    
    public MCH_EntityASMissile(final World par1World, final double posX, final double posY, final double posZ, final double targetX, final double targetY, final double targetZ, final float yaw, final float pitch, final double acceleration) {
        super(par1World, posX, posY, posZ, targetX, targetY, targetZ, yaw, pitch, acceleration);
    }
    
    @Override
    public MCH_BulletModel getDefaultBulletModel() {
        return MCH_DefaultBulletModels.ASMissile;
    }
}
