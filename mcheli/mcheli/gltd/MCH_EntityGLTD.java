//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.gltd;

import cpw.mods.fml.relauncher.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import mcheli.weapon.*;
import mcheli.*;
import mcheli.multiplay.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import mcheli.wrapper.*;
import net.minecraft.nbt.*;

public class MCH_EntityGLTD extends W_Entity
{
    private boolean isBoatEmpty;
    private double speedMultiplier;
    private int gltdPosRotInc;
    private double gltdX;
    private double gltdY;
    private double gltdZ;
    private double gltdYaw;
    private double gltdPitch;
    @SideOnly(Side.CLIENT)
    private double velocityX;
    @SideOnly(Side.CLIENT)
    private double velocityY;
    @SideOnly(Side.CLIENT)
    private double velocityZ;
    public final MCH_Camera camera;
    public boolean zoomDir;
    public final MCH_WeaponCAS weaponCAS;
    public int countWait;
    public boolean isUsedPlayer;
    public float renderRotaionYaw;
    public float renderRotaionPitch;
    public int retryRiddenByEntityCheck;
    public Entity lastRiddenByEntity;
    
    public MCH_EntityGLTD(final World world) {
        super(world);
        this.isBoatEmpty = true;
        this.speedMultiplier = 0.07;
        this.preventEntitySpawning = true;
        this.setSize(0.5f, 0.5f);
        this.yOffset = this.height / 2.0f;
        this.camera = new MCH_Camera(world, this);
        final MCH_WeaponInfo wi = MCH_WeaponInfoManager.get("a10gau8");
        this.weaponCAS = new MCH_WeaponCAS(world, Vec3.createVectorHelper(0.0, 0.0, 0.0), 0.0f, 0.0f, "a10gau8", wi);
        final MCH_WeaponCAS weaponCAS = this.weaponCAS;
        weaponCAS.interval += ((this.weaponCAS.interval > 0) ? 150 : -150);
        this.weaponCAS.displayName = "A-10 GAU-8 Avenger";
        this.ignoreFrustumCheck = true;
        this.countWait = 0;
        this.retryRiddenByEntityCheck = 0;
        this.lastRiddenByEntity = null;
        this.isUsedPlayer = false;
        this.renderRotaionYaw = 0.0f;
        this.renderRotaionYaw = 0.0f;
        this.renderRotaionPitch = 0.0f;
        this.zoomDir = true;
        this.renderDistanceWeight = 2.0;
    }
    
    public MCH_EntityGLTD(final World par1World, final double x, final double y, final double z) {
        this(par1World);
        this.setPosition(x, y + this.yOffset, z);
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
        this.camera.setPosition(x, y, z);
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
        return false;
    }
    
    public double getMountedYOffset() {
        return this.height * 0.0 - 0.3;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource ds, float damage) {
        if (this.isEntityInvulnerable()) {
            return false;
        }
        if (this.worldObj.isRemote || this.isDead) {
            return true;
        }
        final MCH_Config config = MCH_MOD.config;
        damage = MCH_Config.applyDamageByExternal(this, ds, damage);
        if (!MCH_Multiplay.canAttackEntity(ds, this)) {
            return false;
        }
        this.setForwardDirection(-this.getForwardDirection());
        this.setTimeSinceHit(10);
        this.setDamageTaken((int)(this.getDamageTaken() + damage * 100.0f));
        this.setBeenAttacked();
        final boolean flag = ds.getEntity() instanceof EntityPlayer && ((EntityPlayer)ds.getEntity()).capabilities.isCreativeMode;
        if (flag || this.getDamageTaken() > 40.0f) {
            this.camera.initCamera(0, this.riddenByEntity);
            if (this.riddenByEntity != null) {
                this.riddenByEntity.mountEntity((Entity)this);
            }
            if (!flag) {
                this.dropItemWithOffset(MCH_MOD.itemGLTD, 1, 0.0f);
            }
            W_WorldFunc.MOD_playSoundEffect(this.worldObj, this.posX, this.posY, this.posZ, "hit", 1.0f, 1.0f);
            this.setDead();
        }
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public void performHurtAnimation() {
    }
    
    public boolean canBeCollidedWith() {
        return !this.isDead;
    }
    
    @SideOnly(Side.CLIENT)
    public void setPositionAndRotation2(final double par1, final double par3, final double par5, final float par7, final float par8, final int par9) {
        if (this.isBoatEmpty) {
            this.gltdPosRotInc = par9 + 5;
        }
        else {
            final double x = par1 - this.posX;
            final double y = par3 - this.posY;
            final double z = par5 - this.posZ;
            if (x * x + y * y + z * z <= 1.0) {
                return;
            }
            this.gltdPosRotInc = 3;
        }
        this.gltdX = par1;
        this.gltdY = par3;
        this.gltdZ = par5;
        this.gltdYaw = par7;
        this.gltdPitch = par8;
        this.motionX = this.velocityX;
        this.motionY = this.velocityY;
        this.motionZ = this.velocityZ;
    }
    
    @SideOnly(Side.CLIENT)
    public void setVelocity(final double x, final double y, final double z) {
        this.motionX = x;
        this.velocityX = x;
        this.motionY = y;
        this.velocityY = y;
        this.motionZ = z;
        this.velocityZ = z;
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
        final double d3 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        if (this.riddenByEntity != null) {
            this.camera.updateViewer(0, this.riddenByEntity);
        }
        if (this.worldObj.isRemote && this.isBoatEmpty) {
            if (this.gltdPosRotInc > 0) {
                final double d4 = this.posX + (this.gltdX - this.posX) / this.gltdPosRotInc;
                final double d5 = this.posY + (this.gltdY - this.posY) / this.gltdPosRotInc;
                final double d6 = this.posZ + (this.gltdZ - this.posZ) / this.gltdPosRotInc;
                final double d7 = MathHelper.wrapAngleTo180_double(this.gltdYaw - this.rotationYaw);
                this.rotationYaw += (float)(d7 / this.gltdPosRotInc);
                this.rotationPitch += (float)((this.gltdPitch - this.rotationPitch) / this.gltdPosRotInc);
                --this.gltdPosRotInc;
                this.setPosition(d4, d5, d6);
                this.setRotation(this.rotationYaw, this.rotationPitch);
            }
            else {
                final double d4 = this.posX + this.motionX;
                final double d5 = this.posY + this.motionY;
                final double d6 = this.posZ + this.motionZ;
                this.setPosition(d4, d5, d6);
                if (this.onGround) {
                    this.motionX *= 0.5;
                    this.motionY *= 0.5;
                    this.motionZ *= 0.5;
                }
                this.motionX *= 0.99;
                this.motionY *= 0.95;
                this.motionZ *= 0.99;
            }
        }
        else {
            this.motionY -= 0.04;
            double d4 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            if (d4 > 0.35) {
                final double d5 = 0.35 / d4;
                this.motionX *= d5;
                this.motionZ *= d5;
                d4 = 0.35;
            }
            if (d4 > d3 && this.speedMultiplier < 0.35) {
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
                this.motionX *= 0.5;
                this.motionY *= 0.5;
                this.motionZ *= 0.5;
            }
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.99;
            this.motionY *= 0.95;
            this.motionZ *= 0.99;
            this.rotationPitch = 0.0f;
            double d5 = this.rotationYaw;
            final double d6 = this.prevPosX - this.posX;
            final double d7 = this.prevPosZ - this.posZ;
            if (d6 * d6 + d7 * d7 > 0.001) {
                d5 = (float)(Math.atan2(d7, d6) * 180.0 / 3.141592653589793);
            }
            double d8 = MathHelper.wrapAngleTo180_double(d5 - this.rotationYaw);
            if (d8 > 20.0) {
                d8 = 20.0;
            }
            if (d8 < -20.0) {
                d8 = -20.0;
            }
            this.setRotation(this.rotationYaw += (float)d8, this.rotationPitch);
            if (!this.worldObj.isRemote) {
                final MCH_Config config = MCH_MOD.config;
                if (MCH_Config.Collision_DestroyBlock.prmBool) {
                    for (int l = 0; l < 4; ++l) {
                        final int i1 = MathHelper.floor_double(this.posX + (l % 2 - 0.5) * 0.8);
                        final int j1 = MathHelper.floor_double(this.posZ + (l / 2 - 0.5) * 0.8);
                        for (int k1 = 0; k1 < 2; ++k1) {
                            final int l2 = MathHelper.floor_double(this.posY) + k1;
                            if (W_WorldFunc.isEqualBlock(this.worldObj, i1, l2, j1, W_Block.getSnowLayer())) {
                                this.worldObj.setBlockToAir(i1, l2, j1);
                            }
                        }
                    }
                }
                if (this.riddenByEntity != null && this.riddenByEntity.isDead) {
                    this.riddenByEntity = null;
                }
            }
        }
        this.updateCameraPosition(false);
        if (this.countWait > 0) {
            --this.countWait;
        }
        if (this.countWait < 0) {
            ++this.countWait;
        }
        this.weaponCAS.update(this.countWait);
        if (this.lastRiddenByEntity != null && this.riddenByEntity == null) {
            if (this.retryRiddenByEntityCheck < 3) {
                ++this.retryRiddenByEntityCheck;
                this.setUnmoundPosition(this.lastRiddenByEntity);
            }
            else {
                this.unmountEntity();
            }
        }
        else {
            this.retryRiddenByEntityCheck = 0;
        }
        if (this.riddenByEntity != null) {
            this.lastRiddenByEntity = this.riddenByEntity;
        }
    }
    
    public void setUnmoundPosition(final Entity e) {
        if (e == null) {
            return;
        }
        final float yaw = this.rotationYaw;
        final double d0 = Math.sin(yaw * 3.141592653589793 / 180.0) * 1.2;
        final double d2 = -Math.cos(yaw * 3.141592653589793 / 180.0) * 1.2;
        e.setPosition(this.posX + d0, this.posY + this.getMountedYOffset() + e.getYOffset() + 1.0, this.posZ + d2);
        final double posX = e.posX;
        e.prevPosX = posX;
        e.lastTickPosX = posX;
        final double posY = e.posY;
        e.prevPosY = posY;
        e.lastTickPosY = posY;
        final double posZ = e.posZ;
        e.prevPosZ = posZ;
        e.lastTickPosZ = posZ;
    }
    
    public void unmountEntity() {
        this.camera.setMode(0, 0);
        this.camera.setCameraZoom(1.0f);
        if (!this.worldObj.isRemote) {
            if (this.riddenByEntity != null) {
                if (!this.riddenByEntity.isDead) {
                    this.riddenByEntity.mountEntity((Entity)null);
                }
            }
            else if (this.lastRiddenByEntity != null && !this.lastRiddenByEntity.isDead) {
                this.camera.updateViewer(0, this.lastRiddenByEntity);
                this.setUnmoundPosition(this.lastRiddenByEntity);
            }
        }
        this.riddenByEntity = null;
        this.lastRiddenByEntity = null;
    }
    
    public void updateCameraPosition(final boolean foreceUpdate) {
        if (foreceUpdate || (this.riddenByEntity != null && this.camera != null)) {
            final double x = -Math.sin(this.rotationYaw * 3.141592653589793 / 180.0) * 0.6;
            final double z = Math.cos(this.rotationYaw * 3.141592653589793 / 180.0) * 0.6;
            this.camera.setPosition(this.posX + x, this.posY + 0.7, this.posZ + z);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void zoomCamera(final float f) {
        float z = this.camera.getCameraZoom();
        z += f;
        if (z < 1.0f) {
            z = 1.0f;
        }
        if (z > 10.0f) {
            z = 10.0f;
        }
        this.camera.setCameraZoom(z);
    }
    
    public void updateRiderPosition() {
        if (this.riddenByEntity != null) {
            final double x = Math.sin(this.rotationYaw * 3.141592653589793 / 180.0) * 0.5;
            final double z = -Math.cos(this.rotationYaw * 3.141592653589793 / 180.0) * 0.5;
            this.riddenByEntity.setPosition(this.posX + x, this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ + z);
        }
    }
    
    public void switchWeapon(final int id) {
    }
    
    public boolean useCurrentWeapon(final int option1, final int option2) {
        if (this.countWait == 0 && this.riddenByEntity != null && this.weaponCAS.shot(this.riddenByEntity, this.camera.posX, this.camera.posY, this.camera.posZ, option1, option2)) {
            this.countWait = this.weaponCAS.interval;
            if (this.worldObj.isRemote) {
                this.countWait += ((this.countWait > 0) ? 10 : -10);
            }
            else {
                W_WorldFunc.MOD_playSoundEffect(this.worldObj, this.posX, this.posY, this.posZ, "gltd", 0.5f, 1.0f);
            }
            return true;
        }
        return false;
    }
    
    protected void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
    }
    
    protected void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
    }
    
    @SideOnly(Side.CLIENT)
    public float getShadowSize() {
        return 0.0f;
    }
    
    @Override
    public boolean interactFirst(final EntityPlayer player) {
        if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer && this.riddenByEntity != player) {
            return true;
        }
        player.rotationYaw = MathHelper.wrapAngleTo180_float(this.rotationYaw);
        player.rotationPitch = MathHelper.wrapAngleTo180_float(this.rotationPitch);
        if (!this.worldObj.isRemote) {
            player.mountEntity((Entity)this);
        }
        else {
            this.zoomDir = true;
            this.camera.setCameraZoom(1.0f);
            if (this.countWait > 0) {
                this.countWait = -this.countWait;
            }
            if (this.countWait > -60) {
                this.countWait = -60;
            }
        }
        this.updateCameraPosition(true);
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
        return 0;
    }
    
    @SideOnly(Side.CLIENT)
    public void setIsBoatEmpty(final boolean par1) {
        this.isBoatEmpty = par1;
    }
}
