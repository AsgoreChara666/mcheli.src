//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.flare;

import cpw.mods.fml.common.registry.*;
import net.minecraft.world.*;
import cpw.mods.fml.relauncher.*;
import mcheli.particles.*;
import io.netty.buffer.*;
import mcheli.wrapper.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;

public class MCH_EntityFlare extends W_Entity implements IEntityAdditionalSpawnData
{
    public double gravity;
    public double airResistance;
    public float size;
    public int fuseCount;
    
    public MCH_EntityFlare(final World par1World) {
        super(par1World);
        this.gravity = -0.013;
        this.airResistance = 0.992;
        this.setSize(1.0f, 1.0f);
        this.prevRotationYaw = this.rotationYaw;
        this.prevRotationPitch = this.rotationPitch;
        this.size = 6.0f;
        this.fuseCount = 0;
    }
    
    public MCH_EntityFlare(final World par1World, final double pX, final double pY, final double pZ, final double mX, final double mY, final double mZ, final float size, final int fuseCount) {
        this(par1World);
        this.setLocationAndAngles(pX, pY, pZ, 0.0f, 0.0f);
        this.yOffset = 0.0f;
        this.motionX = mX;
        this.motionY = mY;
        this.motionZ = mZ;
        this.size = size;
        this.fuseCount = fuseCount;
    }
    
    public boolean isEntityInvulnerable() {
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(final double par1) {
        double d1 = this.boundingBox.getAverageEdgeLength() * 4.0;
        d1 *= 64.0;
        return par1 < d1 * d1;
    }
    
    public void setDead() {
        super.setDead();
        if (this.fuseCount > 0 && this.worldObj.isRemote) {
            this.fuseCount = 0;
            final int num = 20;
            for (int i = 0; i < 20; ++i) {
                final double x = (this.rand.nextDouble() - 0.5) * 10.0;
                final double y = (this.rand.nextDouble() - 0.5) * 10.0;
                final double z = (this.rand.nextDouble() - 0.5) * 10.0;
                final MCH_ParticleParam prm = new MCH_ParticleParam(this.worldObj, "smoke", this.posX + x, this.posY + y, this.posZ + z);
                prm.age = 200 + this.rand.nextInt(100);
                prm.size = (float)(20 + this.rand.nextInt(25));
                prm.motionX = (this.rand.nextDouble() - 0.5) * 0.45;
                prm.motionY = (this.rand.nextDouble() - 0.5) * 0.01;
                prm.motionZ = (this.rand.nextDouble() - 0.5) * 0.45;
                prm.a = this.rand.nextFloat() * 0.1f + 0.85f;
                prm.b = this.rand.nextFloat() * 0.2f + 0.5f;
                prm.g = prm.b + 0.05f;
                prm.r = prm.b + 0.1f;
                MCH_ParticlesUtil.spawnParticle(prm);
            }
        }
    }
    
    public void writeSpawnData(final ByteBuf buffer) {
        try {
            buffer.writeByte(this.fuseCount);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void readSpawnData(final ByteBuf additionalData) {
        try {
            this.fuseCount = additionalData.readByte();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void onUpdate() {
        if (this.fuseCount > 0 && this.ticksExisted >= this.fuseCount) {
            this.setDead();
        }
        else if (!this.worldObj.isRemote && !this.worldObj.blockExists((int)this.posX, (int)this.posY, (int)this.posZ)) {
            this.setDead();
        }
        else if (this.ticksExisted > 300 && !this.worldObj.isRemote) {
            this.setDead();
        }
        else {
            super.onUpdate();
            if (!this.worldObj.isRemote) {
                this.onUpdateCollided();
            }
            this.posX += this.motionX;
            this.posY += this.motionY;
            this.posZ += this.motionZ;
            if (this.worldObj.isRemote) {
                final int num = 2;
                final double x = (this.posX - this.prevPosX) / 2.0;
                final double y = (this.posY - this.prevPosY) / 2.0;
                final double z = (this.posZ - this.prevPosZ) / 2.0;
                for (int i = 0; i < 2; ++i) {
                    final MCH_ParticleParam prm = new MCH_ParticleParam(this.worldObj, "smoke", this.prevPosX + x * i, this.prevPosY + y * i, this.prevPosZ + z * i);
                    prm.size = 6.0f + this.rand.nextFloat();
                    if (this.size < 5.0f) {
                        final MCH_ParticleParam mch_ParticleParam = prm;
                        mch_ParticleParam.a *= 0.75;
                        if (this.rand.nextInt(2) == 0) {
                            continue;
                        }
                    }
                    if (this.fuseCount > 0) {
                        prm.a = this.rand.nextFloat() * 0.1f + 0.85f;
                        prm.b = this.rand.nextFloat() * 0.1f + 0.5f;
                        prm.g = prm.b + 0.05f;
                        prm.r = prm.b + 0.1f;
                    }
                    MCH_ParticlesUtil.spawnParticle(prm);
                }
            }
            this.motionY += this.gravity;
            this.motionX *= this.airResistance;
            this.motionZ *= this.airResistance;
            if (this.isInWater() && !this.worldObj.isRemote) {
                this.setDead();
            }
            if (this.onGround && !this.worldObj.isRemote) {
                this.setDead();
            }
            this.setPosition(this.posX, this.posY, this.posZ);
        }
    }
    
    protected void onUpdateCollided() {
        Vec3 vec3 = W_WorldFunc.getWorldVec3(this.worldObj, this.posX, this.posY, this.posZ);
        Vec3 vec4 = W_WorldFunc.getWorldVec3(this.worldObj, this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        final MovingObjectPosition mop = W_WorldFunc.clip(this.worldObj, vec3, vec4);
        vec3 = W_WorldFunc.getWorldVec3(this.worldObj, this.posX, this.posY, this.posZ);
        vec4 = W_WorldFunc.getWorldVec3(this.worldObj, this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        if (mop != null) {
            vec4 = W_WorldFunc.getWorldVec3(this.worldObj, mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord);
            this.onImpact(mop);
        }
    }
    
    protected void onImpact(final MovingObjectPosition par1MovingObjectPosition) {
        if (!this.worldObj.isRemote) {
            this.setDead();
        }
    }
    
    public void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
        par1NBTTagCompound.setTag("direction", (NBTBase)this.newDoubleNBTList(new double[] { this.motionX, this.motionY, this.motionZ }));
    }
    
    public void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
        this.setDead();
    }
    
    public boolean canBeCollidedWith() {
        return true;
    }
    
    public float getCollisionBorderSize() {
        return 1.0f;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource par1DamageSource, final float par2) {
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public float getShadowSize() {
        return 0.0f;
    }
}
