//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.container;

import cpw.mods.fml.relauncher.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import mcheli.multiplay.*;
import net.minecraft.entity.player.*;
import mcheli.*;
import net.minecraft.item.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;
import mcheli.wrapper.*;
import java.util.*;
import net.minecraft.nbt.*;
import mcheli.aircraft.*;

public class MCH_EntityContainer extends W_EntityContainer implements MCH_IEntityCanRideAircraft
{
    private boolean isBoatEmpty;
    private double speedMultiplier;
    private int boatPosRotationIncrements;
    private double boatX;
    private double boatY;
    private double boatZ;
    private double boatYaw;
    private double boatPitch;
    @SideOnly(Side.CLIENT)
    private double velocityX;
    @SideOnly(Side.CLIENT)
    private double velocityY;
    @SideOnly(Side.CLIENT)
    private double velocityZ;
    
    public MCH_EntityContainer(final World par1World) {
        super(par1World);
        this.speedMultiplier = 0.07;
        this.preventEntitySpawning = true;
        this.setSize(2.0f, 1.0f);
        this.yOffset = this.height / 2.0f;
        this.stepHeight = 0.6f;
        this.isImmuneToFire = true;
        this.renderDistanceWeight = 2.0;
    }
    
    public MCH_EntityContainer(final World par1World, final double par2, final double par4, final double par6) {
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
        this.dataWatcher.addObject(17, (Object)new Integer(0));
        this.dataWatcher.addObject(18, (Object)new Integer(1));
        this.dataWatcher.addObject(19, (Object)new Integer(0));
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
    
    @Override
    public int getSizeInventory() {
        return 54;
    }
    
    @Override
    public String getInvName() {
        return "Container " + super.getInvName();
    }
    
    public double getMountedYOffset() {
        return -0.3;
    }
    
    public boolean attackEntityFrom(final DamageSource ds, float damage) {
        if (this.isEntityInvulnerable()) {
            return false;
        }
        if (this.worldObj.isRemote || this.isDead) {
            return false;
        }
        final MCH_Config config = MCH_MOD.config;
        damage = MCH_Config.applyDamageByExternal(this, ds, damage);
        if (!MCH_Multiplay.canAttackEntity(ds, this)) {
            return false;
        }
        if (ds.getEntity() instanceof EntityPlayer && ds.getDamageType().equalsIgnoreCase("player")) {
            MCH_Lib.DbgLog(this.worldObj, "MCH_EntityContainer.attackEntityFrom:damage=%.1f:%s", damage, ds.getDamageType());
            W_WorldFunc.MOD_playSoundAtEntity(this, "hit", 1.0f, 1.3f);
            this.setDamageTaken(this.getDamageTaken() + (int)(damage * 20.0f));
            this.setForwardDirection(-this.getForwardDirection());
            this.setTimeSinceHit(10);
            this.setBeenAttacked();
            final boolean flag = ds.getEntity() instanceof EntityPlayer && ((EntityPlayer)ds.getEntity()).capabilities.isCreativeMode;
            if (flag || this.getDamageTaken() > 40.0f) {
                if (!flag) {
                    this.dropItemWithOffset(MCH_MOD.itemContainer, 1, 0.0f);
                }
                this.setDead();
            }
            return true;
        }
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public void performHurtAnimation() {
        this.setForwardDirection(-this.getForwardDirection());
        this.setTimeSinceHit(10);
        this.setDamageTaken(this.getDamageTaken() * 11);
    }
    
    public boolean canBeCollidedWith() {
        return !this.isDead;
    }
    
    @SideOnly(Side.CLIENT)
    public void setPositionAndRotation2(final double par1, final double par3, final double par5, final float par7, final float par8, final int par9) {
        this.boatPosRotationIncrements = par9 + 10;
        this.boatX = par1;
        this.boatY = par3;
        this.boatZ = par5;
        this.boatYaw = par7;
        this.boatPitch = par8;
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
    
    public void onUpdate() {
        super.onUpdate();
        if (this.getTimeSinceHit() > 0) {
            this.setTimeSinceHit(this.getTimeSinceHit() - 1);
        }
        if (this.getDamageTaken() > 0.0f) {
            this.setDamageTaken(this.getDamageTaken() - 1);
        }
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        final byte b0 = 5;
        double d0 = 0.0;
        for (int i = 0; i < b0; ++i) {
            final double d2 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (i + 0) / b0 - 0.125;
            final double d3 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (i + 1) / b0 - 0.125;
            final AxisAlignedBB axisalignedbb = W_AxisAlignedBB.getAABB(this.boundingBox.minX, d2, this.boundingBox.minZ, this.boundingBox.maxX, d3, this.boundingBox.maxZ);
            if (this.worldObj.isAABBInMaterial(axisalignedbb, Material.water)) {
                d0 += 1.0 / b0;
            }
            else if (this.worldObj.isAABBInMaterial(axisalignedbb, Material.lava)) {
                d0 += 1.0 / b0;
            }
        }
        final double d4 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        if (d4 > 0.2625) {
            final double d5 = Math.cos(this.rotationYaw * 3.141592653589793 / 180.0);
            Math.sin(this.rotationYaw * 3.141592653589793 / 180.0);
        }
        if (this.worldObj.isRemote) {
            if (this.boatPosRotationIncrements > 0) {
                final double d5 = this.posX + (this.boatX - this.posX) / this.boatPosRotationIncrements;
                final double d6 = this.posY + (this.boatY - this.posY) / this.boatPosRotationIncrements;
                final double d7 = this.posZ + (this.boatZ - this.posZ) / this.boatPosRotationIncrements;
                final double d8 = MathHelper.wrapAngleTo180_double(this.boatYaw - this.rotationYaw);
                this.rotationYaw += (float)(d8 / this.boatPosRotationIncrements);
                this.rotationPitch += (float)((this.boatPitch - this.rotationPitch) / this.boatPosRotationIncrements);
                --this.boatPosRotationIncrements;
                this.setPosition(d5, d6, d7);
                this.setRotation(this.rotationYaw, this.rotationPitch);
            }
            else {
                final double d5 = this.posX + this.motionX;
                final double d6 = this.posY + this.motionY;
                final double d7 = this.posZ + this.motionZ;
                this.setPosition(d5, d6, d7);
                if (this.onGround) {
                    final float groundSpeed = 0.9f;
                    this.motionX *= 0.8999999761581421;
                    this.motionZ *= 0.8999999761581421;
                }
                this.motionX *= 0.99;
                this.motionY *= 0.95;
                this.motionZ *= 0.99;
            }
        }
        else {
            if (d0 < 1.0) {
                final double d5 = d0 * 2.0 - 1.0;
                this.motionY += 0.04 * d5;
            }
            else {
                if (this.motionY < 0.0) {
                    this.motionY /= 2.0;
                }
                this.motionY += 0.007;
            }
            double d5 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            if (d5 > 0.35) {
                final double d6 = 0.35 / d5;
                this.motionX *= d6;
                this.motionZ *= d6;
                d5 = 0.35;
            }
            if (d5 > d4 && this.speedMultiplier < 0.35) {
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
            if (this.onGround) {
                final float groundSpeed = 0.9f;
                this.motionX *= 0.8999999761581421;
                this.motionZ *= 0.8999999761581421;
            }
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.99;
            this.motionY *= 0.95;
            this.motionZ *= 0.99;
            this.rotationPitch = 0.0f;
            double d6 = this.rotationYaw;
            final double d7 = this.prevPosX - this.posX;
            final double d8 = this.prevPosZ - this.posZ;
            if (d7 * d7 + d8 * d8 > 0.001) {
                d6 = (float)(Math.atan2(d8, d7) * 180.0 / 3.141592653589793);
            }
            double d9 = MathHelper.wrapAngleTo180_double(d6 - this.rotationYaw);
            if (d9 > 5.0) {
                d9 = 5.0;
            }
            if (d9 < -5.0) {
                d9 = -5.0;
            }
            this.setRotation(this.rotationYaw += (float)d9, this.rotationPitch);
            if (!this.worldObj.isRemote) {
                final List list = this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this, this.boundingBox.expand(0.2, 0.0, 0.2));
                if (list != null && !list.isEmpty()) {
                    for (int l = 0; l < list.size(); ++l) {
                        final Entity entity = list.get(l);
                        if (entity.canBePushed() && entity instanceof MCH_EntityContainer) {
                            entity.applyEntityCollision((Entity)this);
                        }
                    }
                }
                final MCH_Config config = MCH_MOD.config;
                if (MCH_Config.Collision_DestroyBlock.prmBool) {
                    for (int l = 0; l < 4; ++l) {
                        final int i2 = MathHelper.floor_double(this.posX + (l % 2 - 0.5) * 0.8);
                        final int j1 = MathHelper.floor_double(this.posZ + (l / 2 - 0.5) * 0.8);
                        for (int k1 = 0; k1 < 2; ++k1) {
                            final int l2 = MathHelper.floor_double(this.posY) + k1;
                            if (W_WorldFunc.isEqualBlock(this.worldObj, i2, l2, j1, W_Block.getSnowLayer())) {
                                this.worldObj.setBlockToAir(i2, l2, j1);
                            }
                            else if (W_WorldFunc.isEqualBlock(this.worldObj, i2, l2, j1, W_Blocks.waterlily)) {
                                W_WorldFunc.destroyBlock(this.worldObj, i2, l2, j1, true);
                            }
                        }
                    }
                }
            }
        }
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
    }
    
    @SideOnly(Side.CLIENT)
    public float getShadowSize() {
        return 2.0f;
    }
    
    public boolean interactFirst(final EntityPlayer player) {
        if (player != null) {
            this.openInventory(player);
        }
        return true;
    }
    
    public void setDamageTaken(final int par1) {
        this.dataWatcher.updateObject(19, (Object)par1);
    }
    
    public int getDamageTaken() {
        return this.dataWatcher.getWatchableObjectInt(19);
    }
    
    public void setTimeSinceHit(final int par1) {
        this.dataWatcher.updateObject(17, (Object)par1);
    }
    
    public int getTimeSinceHit() {
        return this.dataWatcher.getWatchableObjectInt(17);
    }
    
    public void setForwardDirection(final int par1) {
        this.dataWatcher.updateObject(18, (Object)par1);
    }
    
    public int getForwardDirection() {
        return this.dataWatcher.getWatchableObjectInt(18);
    }
    
    public boolean canRideAircraft(final MCH_EntityAircraft ac, final int seatID, final MCH_SeatRackInfo info) {
        for (final String s : info.names) {
            if (s.equalsIgnoreCase("container")) {
                return ac.ridingEntity == null && this.ridingEntity == null;
            }
        }
        return false;
    }
    
    public boolean isSkipNormalRender() {
        return this.ridingEntity instanceof MCH_EntitySeat;
    }
}
