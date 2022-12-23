//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.chain;

import net.minecraft.entity.*;
import net.minecraft.world.*;
import mcheli.aircraft.*;
import java.util.*;
import net.minecraft.util.*;
import mcheli.wrapper.*;
import net.minecraft.nbt.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.entity.player.*;

public class MCH_EntityChain extends W_Entity
{
    private int isServerTowEntitySearchCount;
    public Entity towEntity;
    public Entity towedEntity;
    public String towEntityUUID;
    public String towedEntityUUID;
    private int chainLength;
    private boolean isTowing;
    
    public MCH_EntityChain(final World world) {
        super(world);
        this.preventEntitySpawning = true;
        this.setSize(1.0f, 1.0f);
        this.yOffset = this.height / 2.0f;
        this.towEntity = null;
        this.towedEntity = null;
        this.towEntityUUID = "";
        this.towedEntityUUID = "";
        this.isTowing = false;
        this.ignoreFrustumCheck = true;
        this.setChainLength(4);
        this.isServerTowEntitySearchCount = 50;
    }
    
    public MCH_EntityChain(final World par1World, final double par2, final double par4, final double par6) {
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
        this.getDataWatcher().addObject(30, (Object)0);
        this.getDataWatcher().addObject(31, (Object)0);
    }
    
    public AxisAlignedBB getCollisionBox(final Entity par1Entity) {
        return par1Entity.boundingBox;
    }
    
    public AxisAlignedBB getBoundingBox() {
        return null;
    }
    
    public boolean canBePushed() {
        return true;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource d, final float par2) {
        return false;
    }
    
    public void setChainLength(int n) {
        if (n > 15) {
            n = 15;
        }
        if (n < 3) {
            n = 3;
        }
        this.chainLength = n;
    }
    
    public int getChainLength() {
        return this.chainLength;
    }
    
    public void setDead() {
        super.setDead();
        this.playDisconnectTowingEntity();
        this.isTowing = false;
        this.towEntity = null;
        this.towedEntity = null;
    }
    
    public boolean isTowingEntity() {
        return this.isTowing && !this.isDead && this.towedEntity != null && this.towEntity != null;
    }
    
    public boolean canBeCollidedWith() {
        return false;
    }
    
    public void setTowEntity(final Entity towedEntity, final Entity towEntity) {
        if (towedEntity != null && towEntity != null && !towedEntity.isDead && !towEntity.isDead && !W_Entity.isEqual(towedEntity, towEntity)) {
            this.isTowing = true;
            this.towedEntity = towedEntity;
            this.towEntity = towEntity;
            if (!this.worldObj.isRemote) {
                this.getDataWatcher().updateObject(30, (Object)W_Entity.getEntityId(towedEntity));
                this.getDataWatcher().updateObject(31, (Object)W_Entity.getEntityId(towEntity));
                this.isServerTowEntitySearchCount = 0;
            }
            if (towEntity instanceof MCH_EntityAircraft) {
                ((MCH_EntityAircraft)towEntity).setTowChainEntity(this);
            }
            if (towedEntity instanceof MCH_EntityAircraft) {
                ((MCH_EntityAircraft)towedEntity).setTowedChainEntity(this);
            }
        }
        else {
            this.isTowing = false;
            this.towedEntity = null;
            this.towEntity = null;
        }
    }
    
    public void searchTowingEntity() {
        if ((this.towedEntity == null || this.towEntity == null) && !this.towedEntityUUID.isEmpty() && !this.towEntityUUID.isEmpty() && this.boundingBox != null) {
            final List list = this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this, this.boundingBox.expand(32.0, 32.0, 32.0));
            if (list != null) {
                for (int i = 0; i < list.size(); ++i) {
                    final Entity entity = list.get(i);
                    final String uuid = entity.getPersistentID().toString();
                    if (this.towedEntity == null && uuid.compareTo(this.towedEntityUUID) == 0) {
                        this.towedEntity = entity;
                    }
                    else if (this.towEntity == null && uuid.compareTo(this.towEntityUUID) == 0) {
                        this.towEntity = entity;
                    }
                    else if (this.towEntity != null && this.towedEntity != null) {
                        this.setTowEntity(this.towedEntity, this.towEntity);
                        break;
                    }
                }
            }
        }
    }
    
    public void onUpdate() {
        super.onUpdate();
        if (this.towedEntity == null || this.towedEntity.isDead || this.towEntity == null || this.towEntity.isDead) {
            this.towedEntity = null;
            this.towEntity = null;
            this.isTowing = false;
        }
        if (this.towedEntity != null) {
            this.towedEntity.fallDistance = 0.0f;
        }
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.worldObj.isRemote) {
            this.onUpdate_Client();
        }
        else {
            this.onUpdate_Server();
        }
    }
    
    public void onUpdate_Client() {
        if (!this.isTowingEntity()) {
            this.setTowEntity(this.worldObj.getEntityByID(this.getDataWatcher().getWatchableObjectInt(30)), this.worldObj.getEntityByID(this.getDataWatcher().getWatchableObjectInt(31)));
        }
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
    
    public void onUpdate_Server() {
        if (this.isServerTowEntitySearchCount > 0) {
            this.searchTowingEntity();
            if (this.towEntity != null && this.towedEntity != null) {
                this.isServerTowEntitySearchCount = 0;
            }
            else {
                --this.isServerTowEntitySearchCount;
            }
        }
        else if (this.towEntity == null || this.towedEntity == null) {
            this.setDead();
        }
        this.motionY -= 0.01;
        if (!this.isTowing) {
            this.motionX *= 0.8;
            this.motionY *= 0.8;
            this.motionZ *= 0.8;
        }
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        if (this.isTowingEntity()) {
            this.setPosition(this.towEntity.posX, this.towEntity.posY + 2.0, this.towEntity.posZ);
            this.updateTowingEntityPosRot();
        }
        this.motionX *= 0.99;
        this.motionY *= 0.95;
        this.motionZ *= 0.99;
    }
    
    public void updateTowingEntityPosRot() {
        final double dx = this.towedEntity.posX - this.towEntity.posX;
        final double dy = this.towedEntity.posY - this.towEntity.posY;
        final double dz = this.towedEntity.posZ - this.towEntity.posZ;
        final double dist = MathHelper.sqrt_double(dx * dx + dy * dy + dz * dz);
        final float DIST = (float)this.getChainLength();
        final float MAX_DIST = (float)(this.getChainLength() + 2);
        if (dist > DIST) {
            this.towedEntity.rotationYaw = (float)(Math.atan2(dz, dx) * 180.0 / 3.141592653589793) + 90.0f;
            this.towedEntity.prevRotationYaw = this.towedEntity.rotationYaw;
            final double tmp = dist - DIST;
            final float accl = 0.001f;
            final Entity towedEntity = this.towedEntity;
            towedEntity.motionX -= dx * accl / tmp;
            final Entity towedEntity2 = this.towedEntity;
            towedEntity2.motionY -= dy * accl / tmp;
            final Entity towedEntity3 = this.towedEntity;
            towedEntity3.motionZ -= dz * accl / tmp;
            if (dist > MAX_DIST) {
                this.towedEntity.setPosition(this.towEntity.posX + dx * MAX_DIST / dist, this.towEntity.posY + dy * MAX_DIST / dist, this.towEntity.posZ + dz * MAX_DIST / dist);
            }
        }
    }
    
    public void playDisconnectTowingEntity() {
        W_WorldFunc.MOD_playSoundEffect(this.worldObj, this.posX, this.posY, this.posZ, "chain_ct", 1.0f, 1.0f);
    }
    
    protected void writeEntityToNBT(final NBTTagCompound nbt) {
        if (this.isTowing && this.towEntity != null && !this.towEntity.isDead && this.towedEntity != null && !this.towedEntity.isDead) {
            nbt.setString("TowEntityUUID", this.towEntity.getPersistentID().toString());
            nbt.setString("TowedEntityUUID", this.towedEntity.getPersistentID().toString());
            nbt.setInteger("ChainLength", this.getChainLength());
        }
    }
    
    protected void readEntityFromNBT(final NBTTagCompound nbt) {
        this.towEntityUUID = nbt.getString("TowEntityUUID");
        this.towedEntityUUID = nbt.getString("TowedEntityUUID");
        this.setChainLength(nbt.getInteger("ChainLength"));
    }
    
    @SideOnly(Side.CLIENT)
    public float getShadowSize() {
        return 0.0f;
    }
    
    @Override
    public boolean interactFirst(final EntityPlayer player) {
        return false;
    }
}
