//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.uav;

import net.minecraft.entity.*;
import cpw.mods.fml.relauncher.*;
import mcheli.aircraft.*;
import net.minecraft.world.*;
import net.minecraft.nbt.*;
import mcheli.multiplay.*;
import net.minecraft.entity.player.*;
import mcheli.*;
import net.minecraft.block.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import mcheli.wrapper.*;
import mcheli.plane.*;
import mcheli.helicopter.*;
import mcheli.tank.*;

public class MCH_EntityUavStation extends W_EntityContainer
{
    protected static final int DATAWT_ID_KIND = 27;
    protected static final int DATAWT_ID_LAST_AC = 28;
    protected static final int DATAWT_ID_UAV_X = 29;
    protected static final int DATAWT_ID_UAV_Y = 30;
    protected static final int DATAWT_ID_UAV_Z = 31;
    protected Entity lastRiddenByEntity;
    public boolean isRequestedSyncStatus;
    @SideOnly(Side.CLIENT)
    protected double velocityX;
    @SideOnly(Side.CLIENT)
    protected double velocityY;
    @SideOnly(Side.CLIENT)
    protected double velocityZ;
    protected int aircraftPosRotInc;
    protected double aircraftX;
    protected double aircraftY;
    protected double aircraftZ;
    protected double aircraftYaw;
    protected double aircraftPitch;
    private MCH_EntityAircraft controlAircraft;
    private MCH_EntityAircraft lastControlAircraft;
    private String loadedLastControlAircraftGuid;
    public int posUavX;
    public int posUavY;
    public int posUavZ;
    public float rotCover;
    public float prevRotCover;
    
    public MCH_EntityUavStation(final World world) {
        super(world);
        this.dropContentsWhenDead = false;
        this.preventEntitySpawning = true;
        this.setSize(2.0f, 0.7f);
        this.yOffset = this.height / 2.0f;
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.ignoreFrustumCheck = true;
        this.lastRiddenByEntity = null;
        this.aircraftPosRotInc = 0;
        this.aircraftX = 0.0;
        this.aircraftY = 0.0;
        this.aircraftZ = 0.0;
        this.aircraftYaw = 0.0;
        this.aircraftPitch = 0.0;
        this.posUavX = 0;
        this.posUavY = 0;
        this.posUavZ = 0;
        this.rotCover = 0.0f;
        this.prevRotCover = 0.0f;
        this.setControlAircract(null);
        this.setLastControlAircraft(null);
        this.loadedLastControlAircraftGuid = "";
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.getDataWatcher().addObject(27, (Object)0);
        this.getDataWatcher().addObject(28, (Object)0);
        this.getDataWatcher().addObject(29, (Object)0);
        this.getDataWatcher().addObject(30, (Object)0);
        this.getDataWatcher().addObject(31, (Object)0);
        this.setOpen(true);
    }
    
    public int getStatus() {
        return this.getDataWatcher().getWatchableObjectByte(27);
    }
    
    public void setStatus(final int n) {
        if (!this.worldObj.isRemote) {
            MCH_Lib.DbgLog(this.worldObj, "MCH_EntityUavStation.setStatus(%d)", new Object[] { n });
            this.getDataWatcher().updateObject(27, (Object)(byte)n);
        }
    }
    
    public int getKind() {
        return 0x7F & this.getStatus();
    }
    
    public void setKind(final int n) {
        this.setStatus((this.getStatus() & 0x80) | n);
    }
    
    public boolean isOpen() {
        return (this.getStatus() & 0x80) != 0x0;
    }
    
    public void setOpen(final boolean b) {
        this.setStatus((b ? 128 : 0) | (this.getStatus() & 0x7F));
    }
    
    public MCH_EntityAircraft getControlAircract() {
        return this.controlAircraft;
    }
    
    public void setControlAircract(final MCH_EntityAircraft ac) {
        this.controlAircraft = ac;
        if (ac != null && !ac.isDead) {
            this.setLastControlAircraft(ac);
        }
    }
    
    public void setUavPosition(final int x, final int y, final int z) {
        if (!this.worldObj.isRemote) {
            this.posUavX = x;
            this.posUavY = y;
            this.posUavZ = z;
            this.getDataWatcher().updateObject(29, (Object)x);
            this.getDataWatcher().updateObject(30, (Object)y);
            this.getDataWatcher().updateObject(31, (Object)z);
        }
    }
    
    public void updateUavPosition() {
        this.posUavX = this.getDataWatcher().getWatchableObjectInt(29);
        this.posUavY = this.getDataWatcher().getWatchableObjectInt(30);
        this.posUavZ = this.getDataWatcher().getWatchableObjectInt(31);
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setInteger("UavStatus", this.getStatus());
        nbt.setInteger("PosUavX", this.posUavX);
        nbt.setInteger("PosUavY", this.posUavY);
        nbt.setInteger("PosUavZ", this.posUavZ);
        String s = "";
        if (this.getLastControlAircraft() != null && !this.getLastControlAircraft().isDead) {
            s = this.getLastControlAircraft().getCommonUniqueId();
        }
        if (s.isEmpty()) {
            s = this.loadedLastControlAircraftGuid;
        }
        nbt.setString("LastCtrlAc", s);
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.setUavPosition(nbt.getInteger("PosUavX"), nbt.getInteger("PosUavY"), nbt.getInteger("PosUavZ"));
        if (nbt.hasKey("UavStatus")) {
            this.setStatus(nbt.getInteger("UavStatus"));
        }
        else {
            this.setKind(1);
        }
        this.loadedLastControlAircraftGuid = nbt.getString("LastCtrlAc");
    }
    
    public void initUavPostion() {
        final int rt = (int)(MCH_Lib.getRotate360((double)(this.rotationYaw + 45.0f)) / 90.0);
        final int D = 12;
        this.posUavX = ((rt == 0 || rt == 3) ? 12 : -12);
        this.posUavZ = ((rt == 0 || rt == 1) ? 12 : -12);
        this.posUavY = 2;
        this.setUavPosition(this.posUavX, this.posUavY, this.posUavZ);
    }
    
    @Override
    public void setDead() {
        super.setDead();
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, float damage) {
        if (this.isEntityInvulnerable()) {
            return false;
        }
        if (this.isDead) {
            return true;
        }
        if (this.worldObj.isRemote) {
            return true;
        }
        final String dmt = damageSource.getDamageType();
        final MCH_Config config = MCH_MOD.config;
        damage = MCH_Config.applyDamageByExternal((Entity)this, damageSource, damage);
        if (!MCH_Multiplay.canAttackEntity(damageSource, (Entity)this)) {
            return false;
        }
        boolean isCreative = false;
        final Entity entity = damageSource.getEntity();
        boolean isDamegeSourcePlayer = false;
        if (entity instanceof EntityPlayer) {
            isCreative = ((EntityPlayer)entity).capabilities.isCreativeMode;
            if (dmt.compareTo("player") == 0) {
                isDamegeSourcePlayer = true;
            }
            W_WorldFunc.MOD_playSoundAtEntity(this, "hit", 1.0f, 1.0f);
        }
        else {
            W_WorldFunc.MOD_playSoundAtEntity(this, "helidmg", 1.0f, 0.9f + this.rand.nextFloat() * 0.1f);
        }
        this.setBeenAttacked();
        if (damage > 0.0f) {
            if (this.riddenByEntity != null) {
                this.riddenByEntity.mountEntity((Entity)this);
            }
            this.dropContentsWhenDead = true;
            this.setDead();
            if (!isDamegeSourcePlayer) {
                MCH_Explosion.newExplosion(this.worldObj, (Entity)null, this.riddenByEntity, this.posX, this.posY, this.posZ, 1.0f, 0.0f, true, true, false, false, 0);
            }
            if (!isCreative) {
                final int kind = this.getKind();
                if (kind > 0) {
                    this.dropItemWithOffset(MCH_MOD.itemUavStation[kind - 1], 1, 0.0f);
                }
            }
        }
        return true;
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
        if (this.getKind() == 2 && this.riddenByEntity != null) {
            final double px = -Math.sin(this.rotationYaw * 3.141592653589793 / 180.0) * 0.9;
            final double pz = Math.cos(this.rotationYaw * 3.141592653589793 / 180.0) * 0.9;
            final int x = (int)(this.posX + px);
            final int y = (int)(this.posY - 0.5);
            final int z = (int)(this.posZ + pz);
            final Block block = this.worldObj.getBlock(x, y, z);
            return block.isOpaqueCube() ? -0.4 : -0.9;
        }
        return 0.35;
    }
    
    @SideOnly(Side.CLIENT)
    public float getShadowSize() {
        return 2.0f;
    }
    
    public boolean canBeCollidedWith() {
        return !this.isDead;
    }
    
    public void applyEntityCollision(final Entity par1Entity) {
    }
    
    public void addVelocity(final double par1, final double par3, final double par5) {
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
        this.prevRotCover = this.rotCover;
        if (this.isOpen()) {
            if (this.rotCover < 1.0f) {
                this.rotCover += 0.1f;
            }
            else {
                this.rotCover = 1.0f;
            }
        }
        else if (this.rotCover > 0.0f) {
            this.rotCover -= 0.1f;
        }
        else {
            this.rotCover = 0.0f;
        }
        if (this.riddenByEntity == null) {
            if (this.lastRiddenByEntity != null) {
                this.unmountEntity(true);
            }
            this.setControlAircract(null);
        }
        final int uavStationKind = this.getKind();
        if (this.ticksExisted < 30 && uavStationKind > 0) {
            if (uavStationKind != 1) {
                if (uavStationKind == 2) {}
            }
        }
        if (this.worldObj.isRemote && !this.isRequestedSyncStatus) {
            this.isRequestedSyncStatus = true;
        }
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.getControlAircract() != null && this.getControlAircract().isDead) {
            this.setControlAircract(null);
        }
        if (this.getLastControlAircraft() != null && this.getLastControlAircraft().isDead) {
            this.setLastControlAircraft(null);
        }
        if (this.worldObj.isRemote) {
            this.onUpdate_Client();
        }
        else {
            this.onUpdate_Server();
        }
        this.lastRiddenByEntity = this.riddenByEntity;
    }
    
    public MCH_EntityAircraft getLastControlAircraft() {
        return this.lastControlAircraft;
    }
    
    public MCH_EntityAircraft getAndSearchLastControlAircraft() {
        if (this.getLastControlAircraft() == null) {
            final int id = this.getLastControlAircraftEntityId();
            if (id > 0) {
                final Entity entity = this.worldObj.getEntityByID(id);
                if (entity instanceof MCH_EntityAircraft) {
                    final MCH_EntityAircraft ac = (MCH_EntityAircraft)entity;
                    if (ac.isUAV()) {
                        this.setLastControlAircraft(ac);
                    }
                }
            }
        }
        return this.getLastControlAircraft();
    }
    
    public void setLastControlAircraft(final MCH_EntityAircraft ac) {
        MCH_Lib.DbgLog(this.worldObj, "MCH_EntityUavStation.setLastControlAircraft:" + ac, new Object[0]);
        this.lastControlAircraft = ac;
    }
    
    public Integer getLastControlAircraftEntityId() {
        return this.getDataWatcher().getWatchableObjectInt(28);
    }
    
    public void setLastControlAircraftEntityId(final int s) {
        if (!this.worldObj.isRemote) {
            this.getDataWatcher().updateObject(28, (Object)s);
        }
    }
    
    public void searchLastControlAircraft() {
        if (this.loadedLastControlAircraftGuid.isEmpty()) {
            return;
        }
        final List list = this.worldObj.getEntitiesWithinAABB((Class)MCH_EntityAircraft.class, this.getBoundingBox().expand(120.0, 120.0, 120.0));
        if (list == null) {
            return;
        }
        for (int i = 0; i < list.size(); ++i) {
            final MCH_EntityAircraft ac = list.get(i);
            if (ac.getCommonUniqueId().equals(this.loadedLastControlAircraftGuid)) {
                final String n = (ac.getAcInfo() != null) ? ac.getAcInfo().displayName : ("no info : " + ac);
                MCH_Lib.DbgLog(this.worldObj, "MCH_EntityUavStation.searchLastControlAircraft:found" + n, new Object[0]);
                this.setLastControlAircraft(ac);
                this.setLastControlAircraftEntityId(W_Entity.getEntityId((Entity)ac));
                this.loadedLastControlAircraftGuid = "";
                return;
            }
        }
    }
    
    protected void onUpdate_Client() {
        if (this.aircraftPosRotInc > 0) {
            final double rpinc = this.aircraftPosRotInc;
            final double yaw = MathHelper.wrapAngleTo180_double(this.aircraftYaw - this.rotationYaw);
            this.rotationYaw += (float)(yaw / rpinc);
            this.rotationPitch += (float)((this.aircraftPitch - this.rotationPitch) / rpinc);
            this.setPosition(this.posX + (this.aircraftX - this.posX) / rpinc, this.posY + (this.aircraftY - this.posY) / rpinc, this.posZ + (this.aircraftZ - this.posZ) / rpinc);
            this.setRotation(this.rotationYaw, this.rotationPitch);
            --this.aircraftPosRotInc;
        }
        else {
            this.setPosition(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            this.motionY *= 0.96;
            this.motionX = 0.0;
            this.motionZ = 0.0;
        }
        this.updateUavPosition();
    }
    
    private void onUpdate_Server() {
        this.moveEntity(0.0, this.motionY -= 0.03, 0.0);
        this.motionY *= 0.96;
        this.motionX = 0.0;
        this.motionZ = 0.0;
        this.setRotation(this.rotationYaw, this.rotationPitch);
        if (this.riddenByEntity != null) {
            if (this.riddenByEntity.isDead) {
                this.unmountEntity(true);
                this.riddenByEntity = null;
            }
            else {
                final ItemStack item = this.getStackInSlot(0);
                if (item != null && item.stackSize > 0) {
                    this.handleItem(this.riddenByEntity, item);
                    if (item.stackSize == 0) {
                        this.setInventorySlotContents(0, null);
                    }
                }
            }
        }
        if (this.getLastControlAircraft() == null && this.ticksExisted % 40 == 0) {
            this.searchLastControlAircraft();
        }
    }
    
    public void setPositionAndRotation2(final double par1, final double par3, final double par5, final float par7, final float par8, final int par9) {
        this.aircraftPosRotInc = par9 + 8;
        this.aircraftX = par1;
        this.aircraftY = par3;
        this.aircraftZ = par5;
        this.aircraftYaw = par7;
        this.aircraftPitch = par8;
        this.motionX = this.velocityX;
        this.motionY = this.velocityY;
        this.motionZ = this.velocityZ;
    }
    
    public void updateRiderPosition() {
        if (this.riddenByEntity != null) {
            final double x = -Math.sin(this.rotationYaw * 3.141592653589793 / 180.0) * 0.9;
            final double z = Math.cos(this.rotationYaw * 3.141592653589793 / 180.0) * 0.9;
            this.riddenByEntity.setPosition(this.posX + x, this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ + z);
        }
    }
    
    public void controlLastAircraft(final Entity user) {
        if (this.getLastControlAircraft() != null && !this.getLastControlAircraft().isDead) {
            this.getLastControlAircraft().setUavStation(this);
            this.setControlAircract(this.getLastControlAircraft());
            W_EntityPlayer.closeScreen(user);
        }
    }
    
    public void handleItem(final Entity user, final ItemStack itemStack) {
        if (user == null || user.isDead || itemStack == null || itemStack.stackSize != 1) {
            return;
        }
        if (this.worldObj.isRemote) {
            return;
        }
        MCH_EntityAircraft ac = null;
        final double x = this.posX + this.posUavX;
        double y = this.posY + this.posUavY;
        final double z = this.posZ + this.posUavZ;
        if (y <= 1.0) {
            y = 2.0;
        }
        final Item item = itemStack.getItem();
        if (item instanceof MCP_ItemPlane) {
            final MCP_PlaneInfo pi = MCP_PlaneInfoManager.getFromItem(item);
            if (pi != null && pi.isUAV) {
                if (!pi.isSmallUAV && this.getKind() == 2) {
                    ac = null;
                }
                else {
                    ac = (MCH_EntityAircraft)((MCP_ItemPlane)item).createAircraft(this.worldObj, x, y, z, itemStack);
                }
            }
        }
        if (item instanceof MCH_ItemHeli) {
            final MCH_HeliInfo hi = MCH_HeliInfoManager.getFromItem(item);
            if (hi != null && hi.isUAV) {
                if (!hi.isSmallUAV && this.getKind() == 2) {
                    ac = null;
                }
                else {
                    ac = (MCH_EntityAircraft)((MCH_ItemHeli)item).createAircraft(this.worldObj, x, y, z, itemStack);
                }
            }
        }
        if (item instanceof MCH_ItemTank) {
            final MCH_TankInfo hi2 = MCH_TankInfoManager.getFromItem(item);
            if (hi2 != null && hi2.isUAV) {
                if (!hi2.isSmallUAV && this.getKind() == 2) {
                    ac = null;
                }
                else {
                    ac = (MCH_EntityAircraft)((MCH_ItemTank)item).createAircraft(this.worldObj, x, y, z, itemStack);
                }
            }
        }
        if (ac == null) {
            return;
        }
        ac.rotationYaw = this.rotationYaw - 180.0f;
        ac.prevRotationYaw = ac.rotationYaw;
        user.rotationYaw = this.rotationYaw - 180.0f;
        if (this.worldObj.getCollidingBoundingBoxes((Entity)ac, ac.boundingBox.expand(-0.1, -0.1, -0.1)).isEmpty()) {
            --itemStack.stackSize;
            MCH_Lib.DbgLog(this.worldObj, "Create UAV: %s : %s", new Object[] { item.getUnlocalizedName(), item });
            user.rotationYaw = this.rotationYaw - 180.0f;
            if (!ac.isTargetDrone()) {
                ac.setUavStation(this);
                this.setControlAircract(ac);
            }
            this.worldObj.spawnEntityInWorld((Entity)ac);
            if (!ac.isTargetDrone()) {
                ac.setFuel((int)(ac.getMaxFuel() * 0.05f));
                W_EntityPlayer.closeScreen(user);
            }
            else {
                ac.setFuel(ac.getMaxFuel());
            }
        }
        else {
            ac.setDead();
        }
    }
    
    public void _setInventorySlotContents(final int par1, final ItemStack itemStack) {
        super.setInventorySlotContents(par1, itemStack);
    }
    
    @Override
    public boolean interactFirst(final EntityPlayer player) {
        final int kind = this.getKind();
        if (kind <= 0) {
            return false;
        }
        if (this.riddenByEntity != null) {
            return false;
        }
        if (kind == 2) {
            if (player.isSneaking()) {
                this.setOpen(!this.isOpen());
                return false;
            }
            if (!this.isOpen()) {
                return false;
            }
        }
        this.riddenByEntity = null;
        this.lastRiddenByEntity = null;
        if (!this.worldObj.isRemote) {
            player.mountEntity((Entity)this);
            player.openGui((Object)MCH_MOD.instance, 0, player.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ);
        }
        return true;
    }
    
    @Override
    public int getSizeInventory() {
        return 1;
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 1;
    }
    
    public void unmountEntity(final boolean unmountAllEntity) {
        Entity rByEntity = null;
        if (this.riddenByEntity != null) {
            if (!this.worldObj.isRemote) {
                rByEntity = this.riddenByEntity;
                this.riddenByEntity.mountEntity((Entity)null);
            }
        }
        else if (this.lastRiddenByEntity != null) {
            rByEntity = this.lastRiddenByEntity;
        }
        if (this.getControlAircract() != null) {
            this.getControlAircract().setUavStation((MCH_EntityUavStation)null);
        }
        this.setControlAircract(null);
        if (this.worldObj.isRemote) {
            W_EntityPlayer.closeScreen(rByEntity);
        }
        this.riddenByEntity = null;
        this.lastRiddenByEntity = null;
    }
}
