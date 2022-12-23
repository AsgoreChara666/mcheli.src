//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.particles;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.crash.*;
import java.util.*;
import net.minecraft.util.*;
import mcheli.wrapper.*;
import net.minecraft.block.*;

public abstract class MCH_EntityParticleBase extends W_EntityFX
{
    public boolean isEffectedWind;
    public boolean diffusible;
    public boolean toWhite;
    public float particleMaxScale;
    public float gravity;
    public float moutionYUpAge;
    
    public MCH_EntityParticleBase(final World par1World, final double x, final double y, final double z, final double mx, final double my, final double mz) {
        super(par1World, x, y, z, mx, my, mz);
        this.motionX = mx;
        this.motionY = my;
        this.motionZ = mz;
        this.isEffectedWind = false;
        this.particleMaxScale = this.particleScale;
    }
    
    public MCH_EntityParticleBase setParticleScale(final float scale) {
        this.particleScale = scale;
        return this;
    }
    
    public void setParticleMaxAge(final int age) {
        this.particleMaxAge = age;
    }
    
    public void setParticleTextureIndex(final int par1) {
        this.particleTextureIndexX = par1 % 8;
        this.particleTextureIndexY = par1 / 8;
    }
    
    public int getFXLayer() {
        return 2;
    }
    
    public void moveEntity(double par1, double par3, double par5) {
        if (this.noClip) {
            this.boundingBox.offset(par1, par3, par5);
            this.posX = (this.boundingBox.minX + this.boundingBox.maxX) / 2.0;
            this.posY = this.boundingBox.minY + this.yOffset - this.yOffset2;
            this.posZ = (this.boundingBox.minZ + this.boundingBox.maxZ) / 2.0;
        }
        else {
            this.worldObj.theProfiler.startSection("move");
            this.yOffset2 *= 0.4f;
            final double d3 = this.posX;
            final double d4 = this.posY;
            final double d5 = this.posZ;
            final double d6 = par1;
            final double d7 = par3;
            final double d8 = par5;
            final AxisAlignedBB axisalignedbb = this.boundingBox.copy();
            final boolean flag = false;
            final List list = this.worldObj.getCollidingBoundingBoxes((Entity)this, this.boundingBox.addCoord(par1, par3, par5));
            for (int i = 0; i < list.size(); ++i) {
                par3 = list.get(i).calculateYOffset(this.boundingBox, par3);
            }
            this.boundingBox.offset(0.0, par3, 0.0);
            if (!this.field_70135_K && d7 != par3) {
                par5 = 0.0;
                par3 = 0.0;
                par1 = 0.0;
            }
            final boolean flag2 = this.onGround || (d7 != par3 && d7 < 0.0);
            for (int j = 0; j < list.size(); ++j) {
                par1 = list.get(j).calculateXOffset(this.boundingBox, par1);
            }
            this.boundingBox.offset(par1, 0.0, 0.0);
            if (!this.field_70135_K && d6 != par1) {
                par5 = 0.0;
                par3 = 0.0;
                par1 = 0.0;
            }
            for (int j = 0; j < list.size(); ++j) {
                par5 = list.get(j).calculateZOffset(this.boundingBox, par5);
            }
            this.boundingBox.offset(0.0, 0.0, par5);
            if (!this.field_70135_K && d8 != par5) {
                par5 = 0.0;
                par3 = 0.0;
                par1 = 0.0;
            }
            this.worldObj.theProfiler.endSection();
            this.worldObj.theProfiler.startSection("rest");
            this.posX = (this.boundingBox.minX + this.boundingBox.maxX) / 2.0;
            this.posY = this.boundingBox.minY + this.yOffset - this.yOffset2;
            this.posZ = (this.boundingBox.minZ + this.boundingBox.maxZ) / 2.0;
            this.isCollidedHorizontally = (d6 != par1 || d8 != par5);
            this.isCollidedVertically = (d7 != par3);
            this.onGround = (d7 != par3 && d7 < 0.0);
            this.isCollided = (this.isCollidedHorizontally || this.isCollidedVertically);
            this.updateFallState(par3, this.onGround);
            if (d6 != par1) {
                this.motionX = 0.0;
            }
            if (d7 != par3) {
                this.motionY = 0.0;
            }
            if (d8 != par5) {
                this.motionZ = 0.0;
            }
            final double d9 = this.posX - d3;
            final double d10 = this.posY - d4;
            final double d11 = this.posZ - d5;
            try {
                this.doBlockCollisions();
            }
            catch (Throwable throwable) {
                final CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Checking entity block collision");
                final CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being checked for collision");
                this.addEntityCrashInfo(crashreportcategory);
                throw new ReportedException(crashreport);
            }
            this.worldObj.theProfiler.endSection();
        }
    }
    
    public List getCollidingBoundingBoxes(final Entity par1Entity, final AxisAlignedBB par2AxisAlignedBB) {
        final ArrayList collidingBoundingBoxes = new ArrayList();
        final int i = MathHelper.floor_double(par2AxisAlignedBB.minX);
        final int j = MathHelper.floor_double(par2AxisAlignedBB.maxX + 1.0);
        final int k = MathHelper.floor_double(par2AxisAlignedBB.minY);
        final int l = MathHelper.floor_double(par2AxisAlignedBB.maxY + 1.0);
        final int i2 = MathHelper.floor_double(par2AxisAlignedBB.minZ);
        final int j2 = MathHelper.floor_double(par2AxisAlignedBB.maxZ + 1.0);
        for (int k2 = i; k2 < j; ++k2) {
            for (int l2 = i2; l2 < j2; ++l2) {
                if (this.worldObj.blockExists(k2, 64, l2)) {
                    for (int i3 = k - 1; i3 < l; ++i3) {
                        Block block;
                        if (k2 >= -30000000 && k2 < 30000000 && l2 >= -30000000 && l2 < 30000000) {
                            block = W_WorldFunc.getBlock(this.worldObj, k2, i3, l2);
                        }
                        else {
                            block = W_Blocks.stone;
                        }
                        block.addCollisionBoxesToList(this.worldObj, k2, i3, l2, par2AxisAlignedBB, (List)collidingBoundingBoxes, par1Entity);
                    }
                }
            }
        }
        return collidingBoundingBoxes;
    }
}
