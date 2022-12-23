//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.weapon;

import net.minecraft.world.*;
import net.minecraft.util.*;

public class MCH_EntityTorpedo extends MCH_EntityBaseBullet
{
    public double targetPosX;
    public double targetPosY;
    public double targetPosZ;
    public double accelerationInWater;
    
    public MCH_EntityTorpedo(final World par1World) {
        super(par1World);
        this.accelerationInWater = 2.0;
        this.targetPosX = 0.0;
        this.targetPosY = 0.0;
        this.targetPosZ = 0.0;
    }
    
    public void onUpdate() {
        super.onUpdate();
        if (this.getInfo() != null && this.getInfo().isGuidedTorpedo) {
            this.onUpdateGuided();
        }
        else {
            this.onUpdateNoGuided();
        }
        if (this.isInWater() && this.getInfo() != null && !this.getInfo().disableSmoke) {
            this.spawnParticle(this.getInfo().trajectoryParticleName, 3, 5.0f * this.getInfo().smokeSize * 0.5f);
        }
    }
    
    private void onUpdateNoGuided() {
        if (!this.worldObj.isRemote && this.isInWater()) {
            this.motionY *= 0.800000011920929;
            if (this.acceleration < this.accelerationInWater) {
                this.acceleration += 0.1;
            }
            else if (this.acceleration > this.accelerationInWater + 0.20000000298023224) {
                this.acceleration -= 0.1;
            }
            final double x = this.motionX;
            final double y = this.motionY;
            final double z = this.motionZ;
            final double d = MathHelper.sqrt_double(x * x + y * y + z * z);
            this.motionX = x * this.acceleration / d;
            this.motionY = y * this.acceleration / d;
            this.motionZ = z * this.acceleration / d;
        }
        if (this.isInWater()) {
            final double a = (float)Math.atan2(this.motionZ, this.motionX);
            this.rotationYaw = (float)(a * 180.0 / 3.141592653589793) - 90.0f;
        }
    }
    
    private void onUpdateGuided() {
        if (!this.worldObj.isRemote && this.isInWater()) {
            if (this.acceleration < this.accelerationInWater) {
                this.acceleration += 0.1;
            }
            else if (this.acceleration > this.accelerationInWater + 0.20000000298023224) {
                this.acceleration -= 0.1;
            }
            final double x = this.targetPosX - this.posX;
            final double y = this.targetPosY - this.posY;
            final double z = this.targetPosZ - this.posZ;
            final double d = MathHelper.sqrt_double(x * x + y * y + z * z);
            this.motionX = x * this.acceleration / d;
            this.motionY = y * this.acceleration / d;
            this.motionZ = z * this.acceleration / d;
        }
        if (this.isInWater()) {
            final double a = (float)Math.atan2(this.motionZ, this.motionX);
            this.rotationYaw = (float)(a * 180.0 / 3.141592653589793) - 90.0f;
            final double r = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.rotationPitch = -(float)(Math.atan2(this.motionY, r) * 180.0 / 3.141592653589793);
        }
    }
    
    public MCH_EntityTorpedo(final World par1World, final double posX, final double posY, final double posZ, final double targetX, final double targetY, final double targetZ, final float yaw, final float pitch, final double acceleration) {
        super(par1World, posX, posY, posZ, targetX, targetY, targetZ, yaw, pitch, acceleration);
        this.accelerationInWater = 2.0;
    }
    
    public MCH_BulletModel getDefaultBulletModel() {
        return MCH_DefaultBulletModels.Torpedo;
    }
}
