//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.weapon;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import mcheli.wrapper.*;
import java.util.*;
import mcheli.*;
import net.minecraft.block.*;
import net.minecraft.util.*;

public class MCH_EntityBullet extends MCH_EntityBaseBullet
{
    public MCH_EntityBullet(final World par1World) {
        super(par1World);
    }
    
    public MCH_EntityBullet(final World par1World, final double pX, final double pY, final double pZ, final double targetX, final double targetY, final double targetZ, final float yaw, final float pitch, final double acceleration) {
        super(par1World, pX, pY, pZ, targetX, targetY, targetZ, yaw, pitch, acceleration);
    }
    
    public void onUpdate() {
        super.onUpdate();
        if (!this.isDead && !this.worldObj.isRemote && this.getCountOnUpdate() > 1 && this.getInfo() != null && this.explosionPower > 0) {
            float pDist = this.getInfo().proximityFuseDist;
            if (pDist > 0.1) {
                ++pDist;
                final float rng = pDist + MathHelper.abs(this.getInfo().acceleration);
                final List list = this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this, this.boundingBox.expand((double)rng, (double)rng, (double)rng));
                for (int i = 0; i < list.size(); ++i) {
                    final Entity entity1 = list.get(i);
                    if (this.canBeCollidedEntity(entity1) && entity1.getDistanceSqToEntity((Entity)this) < pDist * pDist) {
                        MCH_Lib.DbgLog(this.worldObj, "MCH_EntityBullet.onUpdate:proximityFuse:" + entity1, new Object[0]);
                        this.posX = (entity1.posX + this.posX) / 2.0;
                        this.posY = (entity1.posY + this.posY) / 2.0;
                        this.posZ = (entity1.posZ + this.posZ) / 2.0;
                        final MovingObjectPosition mop = W_MovingObjectPosition.newMOP((int)this.posX, (int)this.posY, (int)this.posZ, 0, W_WorldFunc.getWorldVec3EntityPos((Entity)this), false);
                        this.onImpact(mop, 1.0f);
                        break;
                    }
                }
            }
        }
    }
    
    protected void onUpdateCollided() {
        final double mx = this.motionX * this.accelerationFactor;
        final double my = this.motionY * this.accelerationFactor;
        final double mz = this.motionZ * this.accelerationFactor;
        final float damageFactor = 1.0f;
        MovingObjectPosition m = null;
        for (int i = 0; i < 5; ++i) {
            final Vec3 vec3 = W_WorldFunc.getWorldVec3(this.worldObj, this.posX, this.posY, this.posZ);
            final Vec3 vec4 = W_WorldFunc.getWorldVec3(this.worldObj, this.posX + mx, this.posY + my, this.posZ + mz);
            m = W_WorldFunc.clip(this.worldObj, vec3, vec4);
            boolean continueClip = false;
            if (this.shootingEntity != null && W_MovingObjectPosition.isHitTypeTile(m)) {
                final Block block = W_WorldFunc.getBlock(this.worldObj, m.blockX, m.blockY, m.blockZ);
                final MCH_Config config = MCH_MOD.config;
                if (MCH_Config.bulletBreakableBlocks.contains(block)) {
                    W_WorldFunc.destroyBlock(this.worldObj, m.blockX, m.blockY, m.blockZ, true);
                    continueClip = true;
                }
            }
            if (!continueClip) {
                break;
            }
        }
        final Vec3 vec3 = W_WorldFunc.getWorldVec3(this.worldObj, this.posX, this.posY, this.posZ);
        Vec3 vec4 = W_WorldFunc.getWorldVec3(this.worldObj, this.posX + mx, this.posY + my, this.posZ + mz);
        if (this.getInfo().delayFuse > 0) {
            if (m != null) {
                this.boundBullet(m.sideHit);
                if (this.delayFuse == 0) {
                    this.delayFuse = this.getInfo().delayFuse;
                }
            }
            return;
        }
        if (m != null) {
            vec4 = W_WorldFunc.getWorldVec3(this.worldObj, m.hitVec.xCoord, m.hitVec.yCoord, m.hitVec.zCoord);
        }
        Entity entity = null;
        final List list = this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this, this.boundingBox.addCoord(mx, my, mz).expand(21.0, 21.0, 21.0));
        double d0 = 0.0;
        for (int j = 0; j < list.size(); ++j) {
            final Entity entity2 = list.get(j);
            if (this.canBeCollidedEntity(entity2)) {
                final float f = 0.3f;
                final AxisAlignedBB axisalignedbb = entity2.boundingBox.expand((double)f, (double)f, (double)f);
                final MovingObjectPosition m2 = axisalignedbb.calculateIntercept(vec3, vec4);
                if (m2 != null) {
                    final double d2 = vec3.distanceTo(m2.hitVec);
                    if (d2 < d0 || d0 == 0.0) {
                        entity = entity2;
                        d0 = d2;
                    }
                }
            }
        }
        if (entity != null) {
            m = new MovingObjectPosition(entity);
        }
        if (m != null) {
            this.onImpact(m, damageFactor);
        }
    }
    
    public MCH_BulletModel getDefaultBulletModel() {
        return MCH_DefaultBulletModels.Bullet;
    }
}
