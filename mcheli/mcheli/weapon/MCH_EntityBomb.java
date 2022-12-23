//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.weapon;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import mcheli.wrapper.*;
import net.minecraft.util.*;
import java.util.*;

public class MCH_EntityBomb extends MCH_EntityBaseBullet
{
    public MCH_EntityBomb(final World par1World) {
        super(par1World);
    }
    
    public MCH_EntityBomb(final World par1World, final double posX, final double posY, final double posZ, final double targetX, final double targetY, final double targetZ, final float yaw, final float pitch, final double acceleration) {
        super(par1World, posX, posY, posZ, targetX, targetY, targetZ, yaw, pitch, acceleration);
    }
    
    public void onUpdate() {
        super.onUpdate();
        if (!this.worldObj.isRemote && this.getInfo() != null) {
            this.motionX *= 0.999;
            this.motionZ *= 0.999;
            if (this.isInWater()) {
                this.motionX *= this.getInfo().velocityInWater;
                this.motionY *= this.getInfo().velocityInWater;
                this.motionZ *= this.getInfo().velocityInWater;
            }
            final float dist = this.getInfo().proximityFuseDist;
            if (dist > 0.1f && this.getCountOnUpdate() % 10 == 0) {
                final List list = this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this, this.boundingBox.expand((double)dist, (double)dist, (double)dist));
                if (list != null) {
                    for (int i = 0; i < list.size(); ++i) {
                        final Entity entity = list.get(i);
                        if (W_Lib.isEntityLivingBase(entity) && this.canBeCollidedEntity(entity)) {
                            final MovingObjectPosition m = new MovingObjectPosition((int)(this.posX + 0.5), (int)(this.posY + 0.5), (int)(this.posZ + 0.5), 0, Vec3.createVectorHelper(this.posX, this.posY, this.posZ));
                            this.onImpact(m, 1.0f);
                            break;
                        }
                    }
                }
            }
        }
        this.onUpdateBomblet();
    }
    
    public void sprinkleBomblet() {
        if (!this.worldObj.isRemote) {
            final MCH_EntityBomb e = new MCH_EntityBomb(this.worldObj, this.posX, this.posY, this.posZ, this.motionX, this.motionY, this.motionZ, (float)this.rand.nextInt(360), 0.0f, this.acceleration);
            e.setParameterFromWeapon((MCH_EntityBaseBullet)this, this.shootingAircraft, this.shootingEntity);
            e.setName(this.getName());
            final float MOTION = 1.0f;
            final float RANDOM = this.getInfo().bombletDiff;
            e.motionX = this.motionX * 1.0 + (this.rand.nextFloat() - 0.5f) * RANDOM;
            e.motionY = this.motionY * 1.0 / 2.0 + (this.rand.nextFloat() - 0.5f) * RANDOM / 2.0f;
            e.motionZ = this.motionZ * 1.0 + (this.rand.nextFloat() - 0.5f) * RANDOM;
            e.setBomblet();
            this.worldObj.spawnEntityInWorld((Entity)e);
        }
    }
    
    public MCH_BulletModel getDefaultBulletModel() {
        return MCH_DefaultBulletModels.Bomb;
    }
}
