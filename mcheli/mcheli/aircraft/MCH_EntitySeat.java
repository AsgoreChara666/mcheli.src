//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.aircraft;

import mcheli.wrapper.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import cpw.mods.fml.relauncher.*;
import mcheli.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import mcheli.tool.*;
import mcheli.mob.*;
import net.minecraft.item.*;

public class MCH_EntitySeat extends W_Entity
{
    public String parentUniqueID;
    private MCH_EntityAircraft parent;
    public int seatID;
    public int parentSearchCount;
    protected Entity lastRiddenByEntity;
    public static final float BB_SIZE = 1.0f;
    
    public MCH_EntitySeat(final World world) {
        super(world);
        this.setSize(1.0f, 1.0f);
        this.yOffset = 0.0f;
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.seatID = -1;
        this.setParent(null);
        this.parentSearchCount = 0;
        this.lastRiddenByEntity = null;
        this.ignoreFrustumCheck = true;
        this.isImmuneToFire = true;
    }
    
    public MCH_EntitySeat(final World world, final double x, final double y, final double z) {
        this(world);
        this.setPosition(x, y + 1.0, z);
        this.prevPosX = x;
        this.prevPosY = y + 1.0;
        this.prevPosZ = z;
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
        return false;
    }
    
    public double getMountedYOffset() {
        return -0.3;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource par1DamageSource, final float par2) {
        return this.getParent() != null && this.getParent().attackEntityFrom(par1DamageSource, par2);
    }
    
    public boolean canBeCollidedWith() {
        return !this.isDead;
    }
    
    @SideOnly(Side.CLIENT)
    public void setPositionAndRotation2(final double par1, final double par3, final double par5, final float par7, final float par8, final int par9) {
    }
    
    public void setDead() {
        super.setDead();
    }
    
    public void onUpdate() {
        super.onUpdate();
        this.fallDistance = 0.0f;
        if (this.riddenByEntity != null) {
            this.riddenByEntity.fallDistance = 0.0f;
        }
        if (this.lastRiddenByEntity == null && this.riddenByEntity != null) {
            if (this.getParent() != null) {
                MCH_Lib.DbgLog(this.worldObj, "MCH_EntitySeat.onUpdate:SeatID=%d", this.seatID, this.riddenByEntity.toString());
                this.getParent().onMountPlayerSeat(this, this.riddenByEntity);
            }
        }
        else if (this.lastRiddenByEntity != null && this.riddenByEntity == null && this.getParent() != null) {
            MCH_Lib.DbgLog(this.worldObj, "MCH_EntitySeat.onUpdate:SeatID=%d", this.seatID, this.lastRiddenByEntity.toString());
            this.getParent().onUnmountPlayerSeat(this, this.lastRiddenByEntity);
        }
        if (this.worldObj.isRemote) {
            this.onUpdate_Client();
        }
        else {
            this.onUpdate_Server();
        }
        this.lastRiddenByEntity = this.riddenByEntity;
    }
    
    private void onUpdate_Client() {
        this.checkDetachmentAndDelete();
    }
    
    private void onUpdate_Server() {
        this.checkDetachmentAndDelete();
        if (this.riddenByEntity != null && this.riddenByEntity.isDead) {
            this.riddenByEntity = null;
        }
    }
    
    public void updateRiderPosition() {
        this.updatePosition();
    }
    
    public void updatePosition() {
        final Entity ridEnt = this.riddenByEntity;
        if (ridEnt != null) {
            ridEnt.setPosition(this.posX, this.posY, this.posZ);
            final Entity entity = ridEnt;
            final Entity entity2 = ridEnt;
            final Entity entity3 = ridEnt;
            final double motionX = 0.0;
            entity3.motionZ = motionX;
            entity2.motionY = motionX;
            entity.motionX = motionX;
        }
    }
    
    public void updateRotation(final float yaw, final float pitch) {
        final Entity ridEnt = this.riddenByEntity;
        if (ridEnt != null) {
            ridEnt.rotationYaw = yaw;
            ridEnt.rotationPitch = pitch;
        }
    }
    
    protected void checkDetachmentAndDelete() {
        if (!this.isDead && (this.seatID < 0 || this.getParent() == null || this.getParent().isDead)) {
            if (this.getParent() != null && this.getParent().isDead) {
                this.parentSearchCount = 100000000;
            }
            if (this.parentSearchCount >= 1200) {
                this.setDead();
                if (!this.worldObj.isRemote && this.riddenByEntity != null) {
                    this.riddenByEntity.mountEntity((Entity)null);
                }
                this.setParent(null);
                MCH_Lib.DbgLog(this.worldObj, "[Error]\u5ea7\u5e2d\u30a8\u30f3\u30c6\u30a3\u30c6\u30a3\u306f\u672c\u4f53\u304c\u898b\u3064\u304b\u3089\u306a\u3044\u305f\u3081\u524a\u9664 seat=%d, parentUniqueID=%s", this.seatID, this.parentUniqueID);
            }
            else {
                ++this.parentSearchCount;
            }
        }
        else {
            this.parentSearchCount = 0;
        }
    }
    
    protected void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
        par1NBTTagCompound.setInteger("SeatID", this.seatID);
        par1NBTTagCompound.setString("ParentUniqueID", this.parentUniqueID);
    }
    
    protected void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
        this.seatID = par1NBTTagCompound.getInteger("SeatID");
        this.parentUniqueID = par1NBTTagCompound.getString("ParentUniqueID");
    }
    
    @SideOnly(Side.CLIENT)
    public float getShadowSize() {
        return 0.0f;
    }
    
    public boolean canRideMob(final Entity entity) {
        return this.getParent() != null && this.seatID >= 0 && !(this.getParent().getSeatInfo(this.seatID + 1) instanceof MCH_SeatRackInfo);
    }
    
    public boolean isGunnerMode() {
        return this.riddenByEntity != null && this.getParent() != null && this.getParent().getIsGunnerMode(this.riddenByEntity);
    }
    
    @Override
    public boolean interactFirst(final EntityPlayer player) {
        if (this.getParent() == null || this.getParent().isDestroyed()) {
            return false;
        }
        if (!this.getParent().checkTeam(player)) {
            return false;
        }
        final ItemStack itemStack = player.getCurrentEquippedItem();
        if (itemStack != null && itemStack.getItem() instanceof MCH_ItemWrench) {
            return this.getParent().interactFirst(player);
        }
        if (itemStack != null && itemStack.getItem() instanceof MCH_ItemSpawnGunner) {
            return this.getParent().interactFirst(player);
        }
        if (this.riddenByEntity != null) {
            return false;
        }
        if (player.ridingEntity != null) {
            return false;
        }
        if (!this.canRideMob((Entity)player)) {
            return false;
        }
        player.mountEntity((Entity)this);
        return true;
    }
    
    public MCH_EntityAircraft getParent() {
        return this.parent;
    }
    
    public void setParent(final MCH_EntityAircraft parent) {
        this.parent = parent;
        if (this.riddenByEntity != null) {
            MCH_Lib.DbgLog(this.worldObj, "MCH_EntitySeat.setParent:SeatID=%d %s : " + this.getParent(), this.seatID, this.riddenByEntity.toString());
            if (this.getParent() != null) {
                this.getParent().onMountPlayerSeat(this, this.riddenByEntity);
            }
        }
    }
}
