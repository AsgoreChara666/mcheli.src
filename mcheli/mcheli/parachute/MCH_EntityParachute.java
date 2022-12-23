//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.parachute;

import cpw.mods.fml.relauncher.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import mcheli.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;
import mcheli.particles.*;
import java.util.*;
import mcheli.wrapper.*;
import net.minecraft.entity.item.*;
import net.minecraft.block.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;

public class MCH_EntityParachute extends W_Entity
{
    private double speedMultiplier;
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
    public Entity user;
    public int onGroundCount;
    
    public MCH_EntityParachute(final World par1World) {
        super(par1World);
        this.speedMultiplier = 0.07;
        this.preventEntitySpawning = true;
        this.setSize(1.5f, 0.6f);
        this.yOffset = this.height / 2.0f;
        this.user = null;
        this.onGroundCount = 0;
    }
    
    public MCH_EntityParachute(final World par1World, final double par2, final double par4, final double par6) {
        this(par1World);
        this.setPosition(par2, par4 + this.yOffset, par6);
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.prevPosX = par2;
        this.prevPosY = par4;
        this.prevPosZ = par6;
    }
    
    protected boolean canTriggerWalking() {
        return false;
    }
    
    @Override
    protected void entityInit() {
        this.getDataWatcher().addObject(31, (Object)0);
    }
    
    public void setType(final int n) {
        this.getDataWatcher().updateObject(31, (Object)(byte)n);
    }
    
    public int getType() {
        return this.getDataWatcher().getWatchableObjectByte(31);
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
        return this.height * 0.0 - 0.30000001192092896;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource par1DamageSource, final float par2) {
        return false;
    }
    
    public boolean canBeCollidedWith() {
        return !this.isDead;
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
        if (!this.worldObj.isRemote && this.ticksExisted % 10 == 0) {
            MCH_Lib.DbgLog(this.worldObj, "MCH_EntityParachute.onUpdate %d, %.3f", new Object[] { this.ticksExisted, this.motionY });
        }
        if (this.isOpenParachute() && this.motionY > -0.3 && this.ticksExisted > 20) {
            this.fallDistance *= (float)0.85;
        }
        if (!this.worldObj.isRemote && this.user != null && this.user.ridingEntity == null) {
            this.user.mountEntity((Entity)this);
            final float rotationYaw = this.user.rotationYaw;
            this.prevRotationYaw = rotationYaw;
            this.rotationYaw = rotationYaw;
            this.user = null;
        }
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        final double d1 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * 0.0 / 5.0 - 0.125;
        final double d2 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * 1.0 / 5.0 - 0.125;
        final AxisAlignedBB axisalignedbb = W_AxisAlignedBB.getAABB(this.boundingBox.minX, d1, this.boundingBox.minZ, this.boundingBox.maxX, d2, this.boundingBox.maxZ);
        if (this.worldObj.isAABBInMaterial(axisalignedbb, Material.water)) {
            this.onWaterSetBoat();
            this.setDead();
        }
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
            if (this.onGround) {}
            this.motionX *= 0.99;
            this.motionY *= 0.95;
            this.motionZ *= 0.99;
        }
        if (!this.isOpenParachute() && this.motionY > 0.01) {
            final float color = 0.6f + this.rand.nextFloat() * 0.2f;
            final double dx = this.prevPosX - this.posX;
            final double dy = this.prevPosY - this.posY;
            final double dz = this.prevPosZ - this.posZ;
            final int num = 1 + (int)(MathHelper.sqrt_double(dx * dx + dy * dy + dz * dz) * 2.0);
            for (double i = 0.0; i < num; ++i) {
                final MCH_ParticleParam prm = new MCH_ParticleParam(this.worldObj, "smoke", this.prevPosX + (this.posX - this.prevPosX) * (i / num) * 0.8, this.prevPosY + (this.posY - this.prevPosY) * (i / num) * 0.8, this.prevPosZ + (this.posZ - this.prevPosZ) * (i / num) * 0.8);
                prm.motionX = this.motionX * 0.5 + (this.rand.nextDouble() - 0.5) * 0.5;
                prm.motionX = this.motionY * -0.5 + (this.rand.nextDouble() - 0.5) * 0.5;
                prm.motionX = this.motionZ * 0.5 + (this.rand.nextDouble() - 0.5) * 0.5;
                prm.size = 5.0f;
                prm.setColor(0.8f + this.rand.nextFloat(), color, color, color);
                MCH_ParticlesUtil.spawnParticle(prm);
            }
        }
    }
    
    public void onUpdateServer() {
        final double prevSpeed = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        double gravity = this.onGround ? 0.01 : 0.03;
        if (this.getType() == 2 && this.ticksExisted < 20) {
            gravity = 0.01;
        }
        this.motionY -= gravity;
        if (this.isOpenParachute()) {
            if (W_Lib.isEntityLivingBase(this.riddenByEntity)) {
                double mv = W_Lib.getEntityMoveDist(this.riddenByEntity);
                if (!this.isOpenParachute()) {
                    mv = 0.0;
                }
                if (mv > 0.0) {
                    final double mx = -Math.sin(this.riddenByEntity.rotationYaw * 3.1415927f / 180.0f);
                    final double mz = Math.cos(this.riddenByEntity.rotationYaw * 3.1415927f / 180.0f);
                    this.motionX += mx * this.speedMultiplier * 0.05;
                    this.motionZ += mz * this.speedMultiplier * 0.05;
                }
            }
            double speed = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            if (speed > 0.35) {
                this.motionX *= 0.35 / speed;
                this.motionZ *= 0.35 / speed;
                speed = 0.35;
            }
            if (speed > prevSpeed && this.speedMultiplier < 0.35) {
                this.speedMultiplier += (0.35 - this.speedMultiplier) / 35.0;
                if (this.speedMultiplier > 0.35) {
                    this.speedMultiplier = 0.35;
                }
            }
            else {
                this.speedMultiplier -= (this.speedMultiplier - 0.07) / 35.0;
                if (this.speedMultiplier < 0.07) {
                    this.speedMultiplier = 0.07;
                }
            }
        }
        if (this.onGround) {
            ++this.onGroundCount;
            if (this.onGroundCount > 5) {
                this.onGroundAndDead();
                return;
            }
        }
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        if (this.getType() == 2 && this.ticksExisted < 20) {
            this.motionY *= 0.95;
        }
        else {
            this.motionY *= 0.9;
        }
        if (this.isOpenParachute()) {
            this.motionX *= 0.95;
            this.motionZ *= 0.95;
        }
        else {
            this.motionX *= 0.99;
            this.motionZ *= 0.99;
        }
        this.rotationPitch = 0.0f;
        double yaw = this.rotationYaw;
        final double dx = this.prevPosX - this.posX;
        final double dz = this.prevPosZ - this.posZ;
        if (dx * dx + dz * dz > 0.001) {
            yaw = (float)(Math.atan2(dx, dz) * 180.0 / 3.141592653589793);
        }
        double yawDiff = MathHelper.wrapAngleTo180_double(yaw - this.rotationYaw);
        if (yawDiff > 20.0) {
            yawDiff = 20.0;
        }
        if (yawDiff < -20.0) {
            yawDiff = -20.0;
        }
        if (this.riddenByEntity != null) {
            this.setRotation(this.riddenByEntity.rotationYaw, this.rotationPitch);
        }
        else {
            this.setRotation(this.rotationYaw += (float)yawDiff, this.rotationPitch);
        }
        final List list = this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this, this.boundingBox.expand(0.2, 0.0, 0.2));
        if (list != null && !list.isEmpty()) {
            for (int l = 0; l < list.size(); ++l) {
                final Entity entity = list.get(l);
                if (entity != this.riddenByEntity && entity.canBePushed() && entity instanceof MCH_EntityParachute) {
                    entity.applyEntityCollision((Entity)this);
                }
            }
        }
        if (this.riddenByEntity != null && this.riddenByEntity.isDead) {
            this.riddenByEntity = null;
            this.setDead();
        }
    }
    
    public void onGroundAndDead() {
        this.posY += 1.2;
        this.updateRiderPosition();
        this.setDead();
    }
    
    public void onWaterSetBoat() {
        if (this.worldObj.isRemote) {
            return;
        }
        if (this.getType() != 2) {
            return;
        }
        if (this.riddenByEntity == null) {
            return;
        }
        final int px = (int)(this.posX + 0.5);
        int py = (int)(this.posY + 0.5);
        final int pz = (int)(this.posZ + 0.5);
        boolean foundBlock = false;
        for (int y = 0; y < 5 && py + y >= 0; ++y) {
            if (py + y > 255) {
                break;
            }
            final Block block = W_WorldFunc.getBlock(this.worldObj, px, py - y, pz);
            if (block == W_Block.getWater()) {
                py -= y;
                foundBlock = true;
                break;
            }
        }
        if (!foundBlock) {
            return;
        }
        int countWater = 0;
        final int size = 5;
        for (int y2 = 0; y2 < 3 && py + y2 >= 0 && py + y2 <= 255; ++y2) {
            for (int x = -2; x <= 2; ++x) {
                for (int z = -2; z <= 2; ++z) {
                    final Block block2 = W_WorldFunc.getBlock(this.worldObj, px + x, py - y2, pz + z);
                    if (block2 == W_Block.getWater() && ++countWater > 37) {
                        break;
                    }
                }
            }
        }
        if (countWater > 37) {
            final EntityBoat entityboat = new EntityBoat(this.worldObj, (double)px, (double)(py + 1.0f), (double)pz);
            entityboat.rotationYaw = this.rotationYaw - 90.0f;
            this.worldObj.spawnEntityInWorld((Entity)entityboat);
            this.riddenByEntity.mountEntity((Entity)entityboat);
        }
    }
    
    public boolean isOpenParachute() {
        return this.getType() != 2 || this.motionY < -0.1;
    }
    
    public void updateRiderPosition() {
        if (this.riddenByEntity != null) {
            final double x = -Math.sin(this.rotationYaw * 3.141592653589793 / 180.0) * 0.1;
            final double z = Math.cos(this.rotationYaw * 3.141592653589793 / 180.0) * 0.1;
            this.riddenByEntity.setPosition(this.posX + x, this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ + z);
        }
    }
    
    protected void writeEntityToNBT(final NBTTagCompound nbt) {
        nbt.setByte("ParachuteModelType", (byte)this.getType());
    }
    
    protected void readEntityFromNBT(final NBTTagCompound nbt) {
        this.setType(nbt.getByte("ParachuteModelType"));
    }
    
    @SideOnly(Side.CLIENT)
    public float getShadowSize() {
        return 4.0f;
    }
    
    @Override
    public boolean interactFirst(final EntityPlayer par1EntityPlayer) {
        return false;
    }
}
