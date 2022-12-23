//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.tank;

import net.minecraft.world.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.*;
import net.minecraft.crash.*;
import java.util.*;
import net.minecraft.util.*;
import mcheli.wrapper.*;
import mcheli.aircraft.*;
import net.minecraft.block.*;

public class MCH_EntityWheel extends W_Entity
{
    private MCH_EntityAircraft parents;
    public Vec3 pos;
    boolean isPlus;
    
    public MCH_EntityWheel(final World w) {
        super(w);
        this.setSize(1.0f, 1.0f);
        this.stepHeight = 1.5f;
        this.isImmuneToFire = true;
        this.isPlus = false;
    }
    
    public void setWheelPos(final Vec3 pos, final Vec3 weightedCenter) {
        this.pos = pos;
        this.isPlus = (pos.zCoord >= weightedCenter.zCoord);
    }
    
    public void travelToDimension(final int dimensionId) {
    }
    
    public MCH_EntityAircraft getParents() {
        return this.parents;
    }
    
    public void setParents(final MCH_EntityAircraft parents) {
        this.parents = parents;
    }
    
    protected void readEntityFromNBT(final NBTTagCompound tagCompund) {
        this.setDead();
    }
    
    protected void writeEntityToNBT(final NBTTagCompound tagCompound) {
    }
    
    public void moveEntity(double parX, double parY, double parZ) {
        this.worldObj.theProfiler.startSection("move");
        this.yOffset2 *= 0.4f;
        final double nowPosX = this.posX;
        final double nowPosY = this.posY;
        final double nowPosZ = this.posZ;
        final double mx = parX;
        final double my = parY;
        final double mz = parZ;
        final AxisAlignedBB axisalignedbb = this.boundingBox.copy();
        List list = this.getCollidingBoundingBoxes(this, this.boundingBox.addCoord(parX, parY, parZ));
        for (int i = 0; i < list.size(); ++i) {
            parY = list.get(i).calculateYOffset(this.boundingBox, parY);
        }
        this.boundingBox.offset(0.0, parY, 0.0);
        final boolean flag1 = this.onGround || (my != parY && my < 0.0);
        for (int j = 0; j < list.size(); ++j) {
            parX = list.get(j).calculateXOffset(this.boundingBox, parX);
        }
        this.boundingBox.offset(parX, 0.0, 0.0);
        for (int k = 0; k < list.size(); ++k) {
            parZ = list.get(k).calculateZOffset(this.boundingBox, parZ);
        }
        this.boundingBox.offset(0.0, 0.0, parZ);
        if (this.stepHeight > 0.0f && flag1 && this.yOffset2 < 0.05f && (mx != parX || mz != parZ)) {
            final double bkParX = parX;
            final double bkParY = parY;
            final double bkParZ = parZ;
            parX = mx;
            parY = this.stepHeight;
            parZ = mz;
            final AxisAlignedBB axisalignedbb2 = this.boundingBox.copy();
            this.boundingBox.setBB(axisalignedbb);
            list = this.getCollidingBoundingBoxes(this, this.boundingBox.addCoord(mx, parY, mz));
            for (int l = 0; l < list.size(); ++l) {
                parY = list.get(l).calculateYOffset(this.boundingBox, parY);
            }
            this.boundingBox.offset(0.0, parY, 0.0);
            for (int l = 0; l < list.size(); ++l) {
                parX = list.get(l).calculateXOffset(this.boundingBox, parX);
            }
            this.boundingBox.offset(parX, 0.0, 0.0);
            for (int l = 0; l < list.size(); ++l) {
                parZ = list.get(l).calculateZOffset(this.boundingBox, parZ);
            }
            this.boundingBox.offset(0.0, 0.0, parZ);
            parY = -this.stepHeight;
            for (int l = 0; l < list.size(); ++l) {
                parY = list.get(l).calculateYOffset(this.boundingBox, parY);
            }
            this.boundingBox.offset(0.0, parY, 0.0);
            if (bkParX * bkParX + bkParZ * bkParZ >= parX * parX + parZ * parZ) {
                parX = bkParX;
                parY = bkParY;
                parZ = bkParZ;
                this.boundingBox.setBB(axisalignedbb2);
            }
        }
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("rest");
        this.posX = (this.boundingBox.minX + this.boundingBox.maxX) / 2.0;
        this.posY = this.boundingBox.minY + this.yOffset - this.yOffset2;
        this.posZ = (this.boundingBox.minZ + this.boundingBox.maxZ) / 2.0;
        this.isCollidedHorizontally = (mx != parX || mz != parZ);
        this.isCollidedVertically = (my != parY);
        this.onGround = (my != parY && my < 0.0);
        this.isCollided = (this.isCollidedHorizontally || this.isCollidedVertically);
        this.updateFallState(parY, this.onGround);
        if (mx != parX) {
            this.motionX = 0.0;
        }
        if (my != parY) {
            this.motionY = 0.0;
        }
        if (mz != parZ) {
            this.motionZ = 0.0;
        }
        try {
            this.doBlockCollisions();
        }
        catch (Throwable throwable) {
            final CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Checking entity tile collision");
            final CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being checked for collision");
            this.addEntityCrashInfo(crashreportcategory);
        }
        this.worldObj.theProfiler.endSection();
    }
    
    public List getCollidingBoundingBoxes(final Entity par1Entity, final AxisAlignedBB par2AxisAlignedBB) {
        final ArrayList collidingBoundingBoxes = new ArrayList();
        collidingBoundingBoxes.clear();
        final int i = MathHelper.floor_double(par2AxisAlignedBB.minX);
        final int j = MathHelper.floor_double(par2AxisAlignedBB.maxX + 1.0);
        final int k = MathHelper.floor_double(par2AxisAlignedBB.minY);
        final int l = MathHelper.floor_double(par2AxisAlignedBB.maxY + 1.0);
        final int i2 = MathHelper.floor_double(par2AxisAlignedBB.minZ);
        final int j2 = MathHelper.floor_double(par2AxisAlignedBB.maxZ + 1.0);
        for (int k2 = i; k2 < j; ++k2) {
            for (int l2 = i2; l2 < j2; ++l2) {
                if (par1Entity.worldObj.blockExists(k2, 64, l2)) {
                    for (int i3 = k - 1; i3 < l; ++i3) {
                        final Block block = W_WorldFunc.getBlock(par1Entity.worldObj, k2, i3, l2);
                        if (block != null) {
                            block.addCollisionBoxesToList(par1Entity.worldObj, k2, i3, l2, par2AxisAlignedBB, (List)collidingBoundingBoxes, par1Entity);
                        }
                    }
                }
            }
        }
        final double d0 = 0.25;
        final List list = par1Entity.worldObj.getEntitiesWithinAABBExcludingEntity(par1Entity, par2AxisAlignedBB.expand(d0, d0, d0));
        for (int j3 = 0; j3 < list.size(); ++j3) {
            final Entity entity = list.get(j3);
            if (!W_Lib.isEntityLivingBase(entity)) {
                if (!(entity instanceof MCH_EntitySeat)) {
                    if (!(entity instanceof MCH_EntityHitBox)) {
                        if (entity != this.parents) {
                            AxisAlignedBB axisalignedbb1 = entity.getBoundingBox();
                            if (axisalignedbb1 != null && axisalignedbb1.intersectsWith(par2AxisAlignedBB)) {
                                collidingBoundingBoxes.add(axisalignedbb1);
                            }
                            axisalignedbb1 = par1Entity.getCollisionBox(entity);
                            if (axisalignedbb1 != null && axisalignedbb1.intersectsWith(par2AxisAlignedBB)) {
                                collidingBoundingBoxes.add(axisalignedbb1);
                            }
                        }
                    }
                }
            }
        }
        return collidingBoundingBoxes;
    }
}
