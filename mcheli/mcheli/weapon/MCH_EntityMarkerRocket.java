//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.weapon;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import mcheli.particles.*;
import net.minecraft.util.*;
import mcheli.*;
import mcheli.wrapper.*;
import net.minecraft.block.*;

public class MCH_EntityMarkerRocket extends MCH_EntityBaseBullet
{
    public int countDown;
    
    public MCH_EntityMarkerRocket(final World par1World) {
        super(par1World);
        this.setMarkerStatus(0);
        this.countDown = 0;
    }
    
    public MCH_EntityMarkerRocket(final World par1World, final double posX, final double posY, final double posZ, final double targetX, final double targetY, final double targetZ, final float yaw, final float pitch, final double acceleration) {
        super(par1World, posX, posY, posZ, targetX, targetY, targetZ, yaw, pitch, acceleration);
        this.setMarkerStatus(0);
        this.countDown = 0;
    }
    
    protected void entityInit() {
        super.entityInit();
        this.getDataWatcher().addObject(28, (Object)0);
    }
    
    public void setMarkerStatus(final int n) {
        if (!this.worldObj.isRemote) {
            this.getDataWatcher().updateObject(28, (Object)(byte)n);
        }
    }
    
    public int getMarkerStatus() {
        return this.getDataWatcher().getWatchableObjectByte(28);
    }
    
    public boolean attackEntityFrom(final DamageSource par1DamageSource, final float par2) {
        return false;
    }
    
    public void onUpdate() {
        final int status = this.getMarkerStatus();
        if (this.worldObj.isRemote) {
            if (this.getInfo() == null) {
                super.onUpdate();
            }
            if (this.getInfo() != null && !this.getInfo().disableSmoke) {
                if (status != 0) {
                    if (status == 1) {
                        super.onUpdate();
                        this.spawnParticle(this.getInfo().trajectoryParticleName, 3, 5.0f * this.getInfo().smokeSize * 0.5f);
                    }
                    else {
                        final float gb = this.rand.nextFloat() * 0.3f;
                        this.spawnParticle("explode", 5, (float)(10 + this.rand.nextInt(4)), this.rand.nextFloat() * 0.2f + 0.8f, gb, gb, (this.rand.nextFloat() - 0.5f) * 0.7f, 0.3f + this.rand.nextFloat() * 0.3f, (this.rand.nextFloat() - 0.5f) * 0.7f);
                    }
                }
            }
        }
        else if (status == 0 || this.isInWater()) {
            this.setDead();
        }
        else if (status == 1) {
            super.onUpdate();
        }
        else if (this.countDown > 0) {
            --this.countDown;
            if (this.countDown == 40) {
                for (int num = 6 + this.rand.nextInt(2), i = 0; i < num; ++i) {
                    final MCH_EntityBomb e = new MCH_EntityBomb(this.worldObj, this.posX + (this.rand.nextFloat() - 0.5f) * 15.0f, (double)(260.0f + this.rand.nextFloat() * 10.0f + i * 30), this.posZ + (this.rand.nextFloat() - 0.5f) * 15.0f, 0.0, -0.5, 0.0, 0.0f, 90.0f, 4.0);
                    e.setName(this.getName());
                    e.explosionPower = 3 + this.rand.nextInt(2);
                    e.explosionPowerInWater = 0;
                    e.setPower(30);
                    e.piercing = 0;
                    e.shootingAircraft = this.shootingAircraft;
                    e.shootingEntity = this.shootingEntity;
                    this.worldObj.spawnEntityInWorld((Entity)e);
                }
            }
        }
        else {
            this.setDead();
        }
    }
    
    public void spawnParticle(final String name, final int num, final float size, final float r, final float g, final float b, final float mx, final float my, final float mz) {
        if (this.worldObj.isRemote) {
            if (name.isEmpty() || num < 1 || num > 50) {
                return;
            }
            final double x = (this.posX - this.prevPosX) / num;
            final double y = (this.posY - this.prevPosY) / num;
            final double z = (this.posZ - this.prevPosZ) / num;
            for (int i = 0; i < num; ++i) {
                final MCH_ParticleParam prm = new MCH_ParticleParam(this.worldObj, "smoke", this.prevPosX + x * i, this.prevPosY + y * i, this.prevPosZ + z * i);
                prm.motionX = mx;
                prm.motionY = my;
                prm.motionZ = mz;
                prm.size = size + this.rand.nextFloat();
                prm.setColor(1.0f, r, g, b);
                prm.isEffectWind = true;
                MCH_ParticlesUtil.spawnParticle(prm);
            }
        }
    }
    
    protected void onImpact(final MovingObjectPosition m, final float damageFactor) {
        if (this.worldObj.isRemote) {
            return;
        }
        if (m.entityHit != null || W_MovingObjectPosition.isHitTypeEntity(m)) {
            return;
        }
        int x = m.blockX;
        int y = m.blockY;
        int z = m.blockZ;
        switch (m.sideHit) {
            case 0: {
                --y;
                break;
            }
            case 1: {
                ++y;
                break;
            }
            case 2: {
                --z;
                break;
            }
            case 3: {
                ++z;
                break;
            }
            case 4: {
                --x;
                break;
            }
            case 5: {
                ++x;
                break;
            }
        }
        if (this.worldObj.isAirBlock(x, y, z)) {
            final MCH_Config config = MCH_MOD.config;
            if (MCH_Config.Explosion_FlamingBlock.prmBool) {
                W_WorldFunc.setBlock(this.worldObj, x, y, z, (Block)W_Blocks.fire);
            }
            int noAirBlockCount = 0;
            for (int i = y + 1; i < 256 && (this.worldObj.isAirBlock(x, i, z) || ++noAirBlockCount < 5); ++i) {}
            if (noAirBlockCount < 5) {
                this.setMarkerStatus(2);
                this.setPosition(x + 0.5, y + 1.1, z + 0.5);
                this.prevPosX = this.posX;
                this.prevPosY = this.posY;
                this.prevPosZ = this.posZ;
                this.countDown = 100;
            }
            else {
                this.setDead();
            }
        }
        else {
            this.setDead();
        }
    }
    
    public MCH_BulletModel getDefaultBulletModel() {
        return MCH_DefaultBulletModels.Rocket;
    }
}
