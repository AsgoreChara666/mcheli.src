//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.throwable;

import net.minecraft.entity.projectile.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import mcheli.wrapper.*;
import net.minecraft.block.material.*;
import mcheli.*;
import net.minecraft.entity.*;
import net.minecraft.block.*;
import mcheli.particles.*;

public class MCH_EntityThrowable extends EntityThrowable
{
    private static final int DATAID_NAME = 31;
    private int countOnUpdate;
    private MCH_ThrowableInfo throwableInfo;
    public double boundPosX;
    public double boundPosY;
    public double boundPosZ;
    public MovingObjectPosition lastOnImpact;
    public int noInfoCount;
    
    public MCH_EntityThrowable(final World par1World) {
        super(par1World);
        this.init();
    }
    
    public MCH_EntityThrowable(final World par1World, final EntityLivingBase par2EntityLivingBase, final float acceleration) {
        super(par1World, par2EntityLivingBase);
        this.motionX *= acceleration;
        this.motionY *= acceleration;
        this.motionZ *= acceleration;
        this.init();
    }
    
    public MCH_EntityThrowable(final World par1World, final double par2, final double par4, final double par6) {
        super(par1World, par2, par4, par6);
        this.init();
    }
    
    public MCH_EntityThrowable(final World p_i1777_1_, final double x, final double y, final double z, final float yaw, final float pitch) {
        this(p_i1777_1_);
        this.setSize(0.25f, 0.25f);
        this.setLocationAndAngles(x, y, z, yaw, pitch);
        this.posX -= MathHelper.cos(this.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
        this.posY -= 0.10000000149011612;
        this.posZ -= MathHelper.sin(this.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
        this.setPosition(this.posX, this.posY, this.posZ);
        this.yOffset = 0.0f;
        final float f = 0.4f;
        this.motionX = -MathHelper.sin(this.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(this.rotationPitch / 180.0f * 3.1415927f) * f;
        this.motionZ = MathHelper.cos(this.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(this.rotationPitch / 180.0f * 3.1415927f) * f;
        this.motionY = -MathHelper.sin((this.rotationPitch + this.func_70183_g()) / 180.0f * 3.1415927f) * f;
        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, this.func_70182_d(), 1.0f);
    }
    
    public void init() {
        this.lastOnImpact = null;
        this.countOnUpdate = 0;
        this.setInfo(null);
        this.noInfoCount = 0;
        this.getDataWatcher().addObject(31, (Object)new String(""));
    }
    
    public void setDead() {
        final String s = (this.getInfo() != null) ? this.getInfo().name : "null";
        MCH_Lib.DbgLog(this.worldObj, "MCH_EntityThrowable.setDead(%s)", new Object[] { s });
        super.setDead();
    }
    
    public void onUpdate() {
        this.boundPosX = this.posX;
        this.boundPosY = this.posY;
        this.boundPosZ = this.posZ;
        if (this.getInfo() != null) {
            final Block block = W_WorldFunc.getBlock(this.worldObj, (int)(this.posX + 0.5), (int)this.posY, (int)(this.posZ + 0.5));
            final Material mat = W_WorldFunc.getBlockMaterial(this.worldObj, (int)(this.posX + 0.5), (int)this.posY, (int)(this.posZ + 0.5));
            if (block != null && mat == Material.water) {
                this.motionY += this.getInfo().gravityInWater;
            }
            else {
                this.motionY += this.getInfo().gravity;
            }
        }
        super.onUpdate();
        if (this.lastOnImpact != null) {
            this.boundBullet(this.lastOnImpact);
            this.setPosition(this.boundPosX + this.motionX, this.boundPosY + this.motionY, this.boundPosZ + this.motionZ);
            this.lastOnImpact = null;
        }
        ++this.countOnUpdate;
        if (this.countOnUpdate >= 2147483632) {
            this.setDead();
            return;
        }
        if (this.getInfo() == null) {
            final String s = this.getDataWatcher().getWatchableObjectString(31);
            if (!s.isEmpty()) {
                this.setInfo(MCH_ThrowableInfoManager.get(s));
            }
            if (this.getInfo() == null) {
                ++this.noInfoCount;
                if (this.noInfoCount > 10) {
                    this.setDead();
                }
                return;
            }
        }
        if (this.isDead) {
            return;
        }
        if (!this.worldObj.isRemote) {
            if (this.countOnUpdate == this.getInfo().timeFuse && this.getInfo().explosion > 0) {
                MCH_Explosion.newExplosion(this.worldObj, (Entity)null, (Entity)null, this.posX, this.posY, this.posZ, (float)this.getInfo().explosion, (float)this.getInfo().explosion, true, true, false, true, 0);
                this.setDead();
                return;
            }
            if (this.countOnUpdate >= this.getInfo().aliveTime) {
                this.setDead();
            }
        }
        else if (this.countOnUpdate >= this.getInfo().timeFuse) {
            if (this.getInfo().explosion <= 0) {
                for (int i = 0; i < this.getInfo().smokeNum; ++i) {
                    final float y = (this.getInfo().smokeVelocityVertical >= 0.0f) ? 0.2f : -0.2f;
                    final float r = this.getInfo().smokeColor.r * 0.9f + this.rand.nextFloat() * 0.1f;
                    float g = this.getInfo().smokeColor.g * 0.9f + this.rand.nextFloat() * 0.1f;
                    float b = this.getInfo().smokeColor.b * 0.9f + this.rand.nextFloat() * 0.1f;
                    if (this.getInfo().smokeColor.r == this.getInfo().smokeColor.g) {
                        g = r;
                    }
                    if (this.getInfo().smokeColor.r == this.getInfo().smokeColor.b) {
                        b = r;
                    }
                    if (this.getInfo().smokeColor.g == this.getInfo().smokeColor.b) {
                        b = g;
                    }
                    this.spawnParticle("explode", 4, this.getInfo().smokeSize + this.rand.nextFloat() * this.getInfo().smokeSize / 3.0f, r, g, b, this.getInfo().smokeVelocityHorizontal * (this.rand.nextFloat() - 0.5f), this.getInfo().smokeVelocityVertical * this.rand.nextFloat(), this.getInfo().smokeVelocityHorizontal * (this.rand.nextFloat() - 0.5f));
                }
            }
        }
    }
    
    public void spawnParticle(final String name, final int num, final float size, final float r, final float g, final float b, final float mx, final float my, final float mz) {
        if (this.worldObj.isRemote) {
            if (name.isEmpty() || num < 1) {
                return;
            }
            final double x = (this.posX - this.prevPosX) / num;
            final double y = (this.posY - this.prevPosY) / num;
            final double z = (this.posZ - this.prevPosZ) / num;
            for (int i = 0; i < num; ++i) {
                final MCH_ParticleParam prm = new MCH_ParticleParam(this.worldObj, "smoke", this.prevPosX + x * i, 1.0 + this.prevPosY + y * i, this.prevPosZ + z * i);
                prm.setMotion((double)mx, (double)my, (double)mz);
                prm.size = size;
                prm.setColor(1.0f, r, g, b);
                prm.isEffectWind = true;
                prm.toWhite = true;
                MCH_ParticlesUtil.spawnParticle(prm);
            }
        }
    }
    
    protected float getGravityVelocity() {
        return 0.0f;
    }
    
    public void boundBullet(final MovingObjectPosition m) {
        final float bound = this.getInfo().bound;
        switch (m.sideHit) {
            case 0:
            case 1: {
                this.motionX *= 0.8999999761581421;
                this.motionZ *= 0.8999999761581421;
                this.boundPosY = m.hitVec.yCoord;
                if ((m.sideHit == 0 && this.motionY > 0.0) || (m.sideHit == 1 && this.motionY < 0.0)) {
                    this.motionY = -this.motionY * bound;
                    break;
                }
                this.motionY = 0.0;
                break;
            }
            case 2: {
                if (this.motionZ > 0.0) {
                    this.motionZ = -this.motionZ * bound;
                    break;
                }
                break;
            }
            case 3: {
                if (this.motionZ < 0.0) {
                    this.motionZ = -this.motionZ * bound;
                    break;
                }
                break;
            }
            case 4: {
                if (this.motionX > 0.0) {
                    this.motionX = -this.motionX * bound;
                    break;
                }
                break;
            }
            case 5: {
                if (this.motionX < 0.0) {
                    this.motionX = -this.motionX * bound;
                    break;
                }
                break;
            }
        }
    }
    
    protected void onImpact(final MovingObjectPosition m) {
        if (this.getInfo() != null) {
            this.lastOnImpact = m;
        }
    }
    
    public MCH_ThrowableInfo getInfo() {
        return this.throwableInfo;
    }
    
    public void setInfo(final MCH_ThrowableInfo info) {
        this.throwableInfo = info;
        if (info != null && !this.worldObj.isRemote) {
            this.getDataWatcher().updateObject(31, (Object)new String(info.name));
        }
    }
}
