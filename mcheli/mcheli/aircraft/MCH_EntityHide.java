//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.aircraft;

import net.minecraft.entity.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.world.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import java.util.*;
import mcheli.wrapper.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.crash.*;

public class MCH_EntityHide extends W_Entity
{
    private MCH_EntityAircraft ac;
    private Entity user;
    private int paraPosRotInc;
    private double paraX;
    private double paraY;
    private double paraZ;
    private double paraYaw;
    private double paraPitch;
    @SideOnly(Side.CLIENT)
    private double velocityX;
    @SideOnly(Side.CLIENT)
    private double velocityY;
    @SideOnly(Side.CLIENT)
    private double velocityZ;
    
    public MCH_EntityHide(final World par1World) {
        super(par1World);
        this.setSize(1.0f, 1.0f);
        this.preventEntitySpawning = true;
        this.yOffset = this.height / 2.0f;
        this.user = null;
        final double motionX = 0.0;
        this.motionZ = motionX;
        this.motionY = motionX;
        this.motionX = motionX;
    }
    
    public MCH_EntityHide(final World par1World, final double x, final double y, final double z) {
        this(par1World);
        this.posX = x;
        this.posY = y;
        this.posZ = z;
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.createRopeIndex(-1);
        this.getDataWatcher().addObject(31, (Object)new Integer(0));
    }
    
    public void setParent(final MCH_EntityAircraft ac, final Entity user, final int ropeIdx) {
        this.ac = ac;
        this.setRopeIndex(ropeIdx);
        this.user = user;
    }
    
    protected boolean canTriggerWalking() {
        return false;
    }
    
    public AxisAlignedBB getCollisionBox(final Entity par1Entity) {
        return par1Entity.boundingBox;
    }
    
    public AxisAlignedBB getBoundingBox() {
        return this.boundingBox;
    }
    
    public boolean canBePushed() {
        return true;
    }
    
    public double getMountedYOffset() {
        return this.height * 0.0 - 0.3;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource par1DamageSource, final float par2) {
        return false;
    }
    
    public boolean canBeCollidedWith() {
        return !this.isDead;
    }
    
    protected void writeEntityToNBT(final NBTTagCompound nbt) {
    }
    
    protected void readEntityFromNBT(final NBTTagCompound nbt) {
    }
    
    @SideOnly(Side.CLIENT)
    public float getShadowSize() {
        return 0.0f;
    }
    
    @Override
    public boolean interactFirst(final EntityPlayer par1EntityPlayer) {
        return false;
    }
    
    public void createRopeIndex(final int defaultValue) {
        this.getDataWatcher().addObject(30, (Object)new Integer(defaultValue));
    }
    
    public int getRopeIndex() {
        return this.getDataWatcher().getWatchableObjectInt(30);
    }
    
    public void setRopeIndex(final int value) {
        this.getDataWatcher().updateObject(30, (Object)new Integer(value));
    }
    
    @SideOnly(Side.CLIENT)
    public void setPositionAndRotation2(final double par1, final double par3, final double par5, final float par7, final float par8, final int par9) {
        this.paraPosRotInc = par9 + 10;
        this.paraX = par1;
        this.paraY = par3;
        this.paraZ = par5;
        this.paraYaw = par7;
        this.paraPitch = par8;
        this.motionX = this.velocityX;
        this.motionY = this.velocityY;
        this.motionZ = this.velocityZ;
    }
    
    @SideOnly(Side.CLIENT)
    public void setVelocity(final double par1, final double par3, final double par5) {
        this.motionX = par1;
        this.velocityX = par1;
        this.motionY = par3;
        this.velocityY = par3;
        this.motionZ = par5;
        this.velocityZ = par5;
    }
    
    public void setDead() {
        super.setDead();
    }
    
    public void onUpdate() {
        super.onUpdate();
        if (this.user != null && !this.worldObj.isRemote) {
            if (this.ac != null) {
                this.getDataWatcher().updateObject(31, (Object)new Integer(this.ac.getEntityId()));
            }
            this.user.mountEntity((Entity)this);
            this.user = null;
        }
        if (this.ac == null && this.worldObj.isRemote) {
            final int id = this.getDataWatcher().getWatchableObjectInt(31);
            if (id > 0) {
                final Entity entity = this.worldObj.getEntityByID(id);
                if (entity instanceof MCH_EntityAircraft) {
                    this.ac = (MCH_EntityAircraft)entity;
                }
            }
        }
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.fallDistance = 0.0f;
        if (this.riddenByEntity != null) {
            this.riddenByEntity.fallDistance = 0.0f;
        }
        if (this.ac != null) {
            if (!this.ac.isRepelling()) {
                this.setDead();
            }
            final int id = this.getRopeIndex();
            if (id >= 0) {
                final Vec3 v = this.ac.getRopePos(id);
                this.posX = v.xCoord;
                this.posZ = v.zCoord;
            }
        }
        this.setPosition(this.posX, this.posY, this.posZ);
        if (this.worldObj.isRemote) {
            this.onUpdateClient();
        }
        else {
            this.onUpdateServer();
        }
    }
    
    public void onUpdateClient() {
        if (this.paraPosRotInc > 0) {
            final double x = this.posX + (this.paraX - this.posX) / this.paraPosRotInc;
            final double y = this.posY + (this.paraY - this.posY) / this.paraPosRotInc;
            final double z = this.posZ + (this.paraZ - this.posZ) / this.paraPosRotInc;
            final double yaw = MathHelper.wrapAngleTo180_double(this.paraYaw - this.rotationYaw);
            this.rotationYaw += (float)(yaw / this.paraPosRotInc);
            this.rotationPitch += (float)((this.paraPitch - this.rotationPitch) / this.paraPosRotInc);
            --this.paraPosRotInc;
            this.setPosition(x, y, z);
            this.setRotation(this.rotationYaw, this.rotationPitch);
            if (this.riddenByEntity != null) {
                this.setRotation(this.riddenByEntity.prevRotationYaw, this.rotationPitch);
            }
        }
        else {
            this.setPosition(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            this.motionX *= 0.99;
            this.motionY *= 0.95;
            this.motionZ *= 0.99;
        }
    }
    
    public void onUpdateServer() {
        this.motionY -= (this.onGround ? 0.01 : 0.03);
        if (this.onGround) {
            this.onGroundAndDead();
            return;
        }
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionY *= 0.9;
        this.motionX *= 0.95;
        this.motionZ *= 0.95;
        final int id = this.getRopeIndex();
        if (this.ac != null && id >= 0) {
            final Vec3 v = this.ac.getRopePos(id);
            if (Math.abs(this.posY - v.yCoord) > Math.abs(this.ac.ropesLength) + 5.0f) {
                this.onGroundAndDead();
            }
        }
        if (this.riddenByEntity != null && this.riddenByEntity.isDead) {
            this.riddenByEntity = null;
            this.setDead();
        }
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
                if (this.worldObj.blockExists(k2, 64, l2)) {
                    for (int i3 = k - 1; i3 < l; ++i3) {
                        final Block block = W_WorldFunc.getBlock(this.worldObj, k2, i3, l2);
                        if (block != null) {
                            block.addCollisionBoxesToList(this.worldObj, k2, i3, l2, par2AxisAlignedBB, (List)collidingBoundingBoxes, par1Entity);
                        }
                    }
                }
            }
        }
        final double d0 = 0.25;
        final List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(par1Entity, par2AxisAlignedBB.expand(d0, d0, d0));
        for (int j3 = 0; j3 < list.size(); ++j3) {
            final Entity entity = list.get(j3);
            if (!W_Lib.isEntityLivingBase(entity)) {
                if (!(entity instanceof MCH_EntitySeat)) {
                    if (!(entity instanceof MCH_EntityHitBox)) {
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
        return collidingBoundingBoxes;
    }
    
    public void moveEntity(double par1, double par3, double par5) {
        this.worldObj.theProfiler.startSection("move");
        this.yOffset2 *= 0.4f;
        final double d3 = this.posX;
        final double d4 = this.posY;
        final double d5 = this.posZ;
        final double d6 = par1;
        final double d7 = par3;
        final double d8 = par5;
        final AxisAlignedBB axisalignedbb = this.boundingBox.copy();
        List list = this.getCollidingBoundingBoxes(this, this.boundingBox.addCoord(par1, par3, par5));
        for (int i = 0; i < list.size(); ++i) {
            par3 = list.get(i).calculateYOffset(this.boundingBox, par3);
        }
        this.boundingBox.offset(0.0, par3, 0.0);
        if (!this.field_70135_K && d7 != par3) {
            par5 = 0.0;
            par3 = 0.0;
            par1 = 0.0;
        }
        final boolean flag1 = this.onGround || (d7 != par3 && d7 < 0.0);
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
        if (this.stepHeight > 0.0f && flag1 && this.yOffset2 < 0.05f && (d6 != par1 || d8 != par5)) {
            final double d9 = par1;
            final double d10 = par3;
            final double d11 = par5;
            par1 = d6;
            par3 = this.stepHeight;
            par5 = d8;
            final AxisAlignedBB axisalignedbb2 = this.boundingBox.copy();
            this.boundingBox.setBB(axisalignedbb);
            list = this.getCollidingBoundingBoxes(this, this.boundingBox.addCoord(d6, par3, d8));
            for (int k = 0; k < list.size(); ++k) {
                par3 = list.get(k).calculateYOffset(this.boundingBox, par3);
            }
            this.boundingBox.offset(0.0, par3, 0.0);
            if (!this.field_70135_K && d7 != par3) {
                par5 = 0.0;
                par3 = 0.0;
                par1 = 0.0;
            }
            for (int k = 0; k < list.size(); ++k) {
                par1 = list.get(k).calculateXOffset(this.boundingBox, par1);
            }
            this.boundingBox.offset(par1, 0.0, 0.0);
            if (!this.field_70135_K && d6 != par1) {
                par5 = 0.0;
                par3 = 0.0;
                par1 = 0.0;
            }
            for (int k = 0; k < list.size(); ++k) {
                par5 = list.get(k).calculateZOffset(this.boundingBox, par5);
            }
            this.boundingBox.offset(0.0, 0.0, par5);
            if (!this.field_70135_K && d8 != par5) {
                par5 = 0.0;
                par3 = 0.0;
                par1 = 0.0;
            }
            if (!this.field_70135_K && d7 != par3) {
                par5 = 0.0;
                par3 = 0.0;
                par1 = 0.0;
            }
            else {
                par3 = -this.stepHeight;
                for (int k = 0; k < list.size(); ++k) {
                    par3 = list.get(k).calculateYOffset(this.boundingBox, par3);
                }
                this.boundingBox.offset(0.0, par3, 0.0);
            }
            if (d9 * d9 + d11 * d11 >= par1 * par1 + par5 * par5) {
                par1 = d9;
                par3 = d10;
                par5 = d11;
                this.boundingBox.setBB(axisalignedbb2);
            }
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
            final CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Checking entity tile collision");
            final CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being checked for collision");
            this.addEntityCrashInfo(crashreportcategory);
            throw new ReportedException(crashreport);
        }
        this.worldObj.theProfiler.endSection();
    }
    
    public void onGroundAndDead() {
        this.posY += 0.5;
        this.updateRiderPosition();
        this.setDead();
    }
    
    public void _updateRiderPosition() {
        if (this.riddenByEntity != null) {
            final double x = -Math.sin(this.rotationYaw * 3.141592653589793 / 180.0) * 0.1;
            final double z = Math.cos(this.rotationYaw * 3.141592653589793 / 180.0) * 0.1;
            this.riddenByEntity.setPosition(this.posX + x, this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ + z);
        }
    }
}
