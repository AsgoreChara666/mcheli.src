//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.weapon;

import net.minecraft.world.*;
import net.minecraft.entity.*;

public class MCH_EntityRocket extends MCH_EntityBaseBullet
{
    public MCH_EntityRocket(final World par1World) {
        super(par1World);
    }
    
    public MCH_EntityRocket(final World par1World, final double posX, final double posY, final double posZ, final double targetX, final double targetY, final double targetZ, final float yaw, final float pitch, final double acceleration) {
        super(par1World, posX, posY, posZ, targetX, targetY, targetZ, yaw, pitch, acceleration);
    }
    
    public void onUpdate() {
        super.onUpdate();
        this.onUpdateBomblet();
        if (this.isBomblet <= 0 && this.getInfo() != null && !this.getInfo().disableSmoke) {
            this.spawnParticle(this.getInfo().trajectoryParticleName, 3, 5.0f * this.getInfo().smokeSize * 0.5f);
        }
    }
    
    public void sprinkleBomblet() {
        if (!this.worldObj.isRemote) {
            final MCH_EntityRocket e = new MCH_EntityRocket(this.worldObj, this.posX, this.posY, this.posZ, this.motionX, this.motionY, this.motionZ, this.rotationYaw, this.rotationPitch, this.acceleration);
            e.setName(this.getName());
            e.setParameterFromWeapon((MCH_EntityBaseBullet)this, this.shootingAircraft, this.shootingEntity);
            final float MOTION = this.getInfo().bombletDiff;
            final float RANDOM = 1.2f;
            final MCH_EntityRocket mch_EntityRocket = e;
            mch_EntityRocket.motionX += (this.rand.nextFloat() - 0.5) * MOTION;
            final MCH_EntityRocket mch_EntityRocket2 = e;
            mch_EntityRocket2.motionY += (this.rand.nextFloat() - 0.5) * MOTION;
            final MCH_EntityRocket mch_EntityRocket3 = e;
            mch_EntityRocket3.motionZ += (this.rand.nextFloat() - 0.5) * MOTION;
            e.setBomblet();
            this.worldObj.spawnEntityInWorld((Entity)e);
        }
    }
    
    public MCH_BulletModel getDefaultBulletModel() {
        return MCH_DefaultBulletModels.Rocket;
    }
}
