//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.mob;

import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.scoreboard.*;
import net.minecraft.entity.player.*;
import mcheli.wrapper.*;
import net.minecraft.entity.monster.*;
import mcheli.weapon.*;
import net.minecraft.util.*;
import java.util.*;
import mcheli.vehicle.*;
import mcheli.aircraft.*;
import net.minecraft.nbt.*;
import mcheli.*;
import net.minecraft.item.*;

public class MCH_EntityGunner extends EntityLivingBase
{
    public boolean isCreative;
    public String ownerUUID;
    public int targetType;
    public int despawnCount;
    public int switchTargetCount;
    public Entity targetEntity;
    public double targetPrevPosX;
    public double targetPrevPosY;
    public double targetPrevPosZ;
    public boolean waitCooldown;
    public int idleCount;
    public int idleRotation;
    
    public MCH_EntityGunner(final World world) {
        super(world);
        this.isCreative = false;
        this.ownerUUID = "";
        this.targetType = 0;
        this.despawnCount = 0;
        this.switchTargetCount = 0;
        this.targetEntity = null;
        this.targetPrevPosX = 0.0;
        this.targetPrevPosY = 0.0;
        this.targetPrevPosZ = 0.0;
        this.waitCooldown = false;
        this.idleCount = 0;
        this.idleRotation = 0;
    }
    
    public MCH_EntityGunner(final World world, final double x, final double y, final double z) {
        this(world);
        this.setPosition(x, y, z);
    }
    
    protected void entityInit() {
        super.entityInit();
        this.getDataWatcher().addObject(17, (Object)"");
    }
    
    public String getTeamName() {
        return this.getDataWatcher().getWatchableObjectString(17);
    }
    
    public void setTeamName(final String name) {
        this.getDataWatcher().updateObject(17, (Object)name);
    }
    
    public Team getTeam() {
        return (Team)this.worldObj.getScoreboard().getTeam(this.getTeamName());
    }
    
    public boolean isOnSameTeam(final EntityLivingBase p_142014_1_) {
        return super.isOnSameTeam(p_142014_1_);
    }
    
    public IChatComponent getFormattedCommandSenderName() {
        final Team team = this.getTeam();
        if (team != null) {
            return (IChatComponent)new ChatComponentText(ScorePlayerTeam.formatPlayerName(team, team.getRegisteredName() + " Gunner"));
        }
        return (IChatComponent)new ChatComponentText("");
    }
    
    public boolean isEntityInvulnerable() {
        return this.isCreative;
    }
    
    public void onDeath(final DamageSource source) {
        super.onDeath(source);
    }
    
    public boolean interactFirst(final EntityPlayer player) {
        if (this.worldObj.isRemote) {
            return false;
        }
        if (this.ridingEntity == null) {
            return false;
        }
        if (player.capabilities.isCreativeMode) {
            this.removeFromAircraft(player);
            return true;
        }
        if (this.isCreative) {
            player.addChatMessage((IChatComponent)new ChatComponentText("Creative mode only."));
            return false;
        }
        if (this.getTeam() == null || this.isOnSameTeam((EntityLivingBase)player)) {
            this.removeFromAircraft(player);
            return true;
        }
        player.addChatMessage((IChatComponent)new ChatComponentText("You are other team."));
        return false;
    }
    
    public void removeFromAircraft(final EntityPlayer player) {
        if (!this.worldObj.isRemote) {
            W_WorldFunc.MOD_playSoundAtEntity((Entity)player, "wrench", 1.0f, 1.0f);
            this.setDead();
            MCH_EntityAircraft ac = null;
            if (this.ridingEntity instanceof MCH_EntityAircraft) {
                ac = (MCH_EntityAircraft)this.ridingEntity;
            }
            else if (this.ridingEntity instanceof MCH_EntitySeat) {
                ac = ((MCH_EntitySeat)this.ridingEntity).getParent();
            }
            String name = "";
            if (ac != null && ac.getAcInfo() != null) {
                name = " on " + ac.getAcInfo().displayName + " seat " + (ac.getSeatIdByEntity((Entity)this) + 1);
            }
            player.addChatMessage((IChatComponent)new ChatComponentText("Remove gunner" + name + " by " + ScorePlayerTeam.formatPlayerName(player.getTeam(), player.getDisplayName()) + "."));
            this.mountEntity((Entity)null);
        }
    }
    
    public void onUpdate() {
        super.onUpdate();
        if (!this.worldObj.isRemote && !this.isDead) {
            if (this.ridingEntity != null && this.ridingEntity.isDead) {
                this.ridingEntity = null;
            }
            if (this.ridingEntity instanceof MCH_EntityAircraft) {
                this.shotTarget((MCH_EntityAircraft)this.ridingEntity);
            }
            else if (this.ridingEntity instanceof MCH_EntitySeat && ((MCH_EntitySeat)this.ridingEntity).getParent() != null) {
                this.shotTarget(((MCH_EntitySeat)this.ridingEntity).getParent());
            }
            else if (this.despawnCount < 20) {
                ++this.despawnCount;
            }
            else if (this.ridingEntity == null || this.ticksExisted > 100) {
                this.setDead();
            }
            if (this.targetEntity == null) {
                if (this.idleCount == 0) {
                    this.idleCount = (3 + this.rand.nextInt(5)) * 20;
                    this.idleRotation = this.rand.nextInt(5) - 2;
                }
                this.rotationYaw += this.idleRotation / 2.0f;
            }
            else {
                this.idleCount = 60;
            }
        }
        if (this.switchTargetCount > 0) {
            --this.switchTargetCount;
        }
        if (this.idleCount > 0) {
            --this.idleCount;
        }
    }
    
    public boolean canAttackEntity(final EntityLivingBase entity, final MCH_EntityAircraft ac, final MCH_WeaponSet ws) {
        boolean ret = false;
        if (this.targetType == 0) {
            ret = (entity != this && !(entity instanceof EntityEnderman) && !entity.isDead && !this.isOnSameTeam(entity) && entity.getHealth() > 0.0f && !ac.isMountedEntity((Entity)entity));
        }
        else {
            ret = (entity != this && !((EntityPlayer)entity).capabilities.isCreativeMode && !entity.isDead && !this.getTeamName().isEmpty() && !this.isOnSameTeam(entity) && entity.getHealth() > 0.0f && !ac.isMountedEntity((Entity)entity));
        }
        if (ret && ws.getCurrentWeapon().getGuidanceSystem() != null) {
            ret = ws.getCurrentWeapon().getGuidanceSystem().canLockEntity((Entity)entity);
        }
        return ret;
    }
    
    public void shotTarget(final MCH_EntityAircraft ac) {
        if (ac.isDestroyed()) {
            return;
        }
        if (!ac.getGunnerStatus()) {
            return;
        }
        final MCH_WeaponSet ws = ac.getCurrentWeapon((Entity)this);
        if (ws == null || ws.getInfo() == null || ws.getCurrentWeapon() == null) {
            return;
        }
        final MCH_WeaponBase cw = ws.getCurrentWeapon();
        if (this.targetEntity != null && (this.targetEntity.isDead || ((EntityLivingBase)this.targetEntity).getHealth() <= 0.0f) && this.switchTargetCount > 20) {
            this.switchTargetCount = 20;
        }
        final Vec3 pos = this.getGunnerWeaponPos(ac, ws);
        if ((this.targetEntity == null && this.switchTargetCount <= 0) || this.switchTargetCount <= 0) {
            this.switchTargetCount = 20;
            EntityLivingBase nextTarget = null;
            List list;
            if (this.targetType == 0) {
                final MCH_Config config = MCH_MOD.config;
                final int rh = MCH_Config.RangeOfGunner_VsMonster_Horizontal.prmInt;
                final MCH_Config config2 = MCH_MOD.config;
                final int rv = MCH_Config.RangeOfGunner_VsMonster_Vertical.prmInt;
                list = this.worldObj.getEntitiesWithinAABB((Class)IMob.class, this.boundingBox.expand((double)rh, (double)rv, (double)rh));
            }
            else {
                final MCH_Config config3 = MCH_MOD.config;
                final int rh = MCH_Config.RangeOfGunner_VsPlayer_Horizontal.prmInt;
                final MCH_Config config4 = MCH_MOD.config;
                final int rv = MCH_Config.RangeOfGunner_VsPlayer_Vertical.prmInt;
                list = this.worldObj.getEntitiesWithinAABB((Class)EntityPlayer.class, this.boundingBox.expand((double)rh, (double)rv, (double)rh));
            }
            for (int i = 0; i < list.size(); ++i) {
                final EntityLivingBase entity = list.get(i);
                if (this.canAttackEntity(entity, ac, ws) && this.checkPitch(entity, ac, pos) && (nextTarget == null || this.getDistanceToEntity((Entity)entity) < this.getDistanceToEntity((Entity)nextTarget)) && this.canEntityBeSeen((Entity)entity) && this.isInAttackable(entity, ac, ws, pos)) {
                    nextTarget = entity;
                    this.switchTargetCount = 60;
                }
            }
            if (nextTarget != null && this.targetEntity != nextTarget) {
                this.targetPrevPosX = nextTarget.posX;
                this.targetPrevPosY = nextTarget.posY;
                this.targetPrevPosZ = nextTarget.posZ;
            }
            this.targetEntity = (Entity)nextTarget;
        }
        if (this.targetEntity != null) {
            float rotSpeed = 10.0f;
            if (ac.isPilot((Entity)this)) {
                rotSpeed = ac.getAcInfo().cameraRotationSpeed / 10.0f;
            }
            this.rotationPitch = MathHelper.wrapAngleTo180_float(this.rotationPitch);
            this.rotationYaw = MathHelper.wrapAngleTo180_float(this.rotationYaw);
            final double dist = this.getDistanceToEntity(this.targetEntity);
            double tick = 1.0;
            if (dist >= 10.0 && ws.getInfo().acceleration > 1.0f) {
                tick = dist / ws.getInfo().acceleration;
            }
            if (this.targetEntity.ridingEntity instanceof MCH_EntitySeat || this.targetEntity.ridingEntity instanceof MCH_EntityAircraft) {
                final double n = tick;
                final MCH_Config config5 = MCH_MOD.config;
                tick = n - MCH_Config.HitBoxDelayTick.prmInt;
            }
            final double dx = (this.targetEntity.posX - this.targetPrevPosX) * tick;
            final double dy = (this.targetEntity.posY - this.targetPrevPosY) * tick + this.targetEntity.height * this.rand.nextDouble();
            final double dz = (this.targetEntity.posZ - this.targetPrevPosZ) * tick;
            final double d0 = this.targetEntity.posX + dx - pos.xCoord;
            final double d2 = this.targetEntity.posY + dy - pos.yCoord;
            final double d3 = this.targetEntity.posZ + dz - pos.zCoord;
            final double d4 = MathHelper.sqrt_double(d0 * d0 + d3 * d3);
            final float yaw = MathHelper.wrapAngleTo180_float((float)(Math.atan2(d3, d0) * 180.0 / 3.141592653589793) - 90.0f);
            final float pitch = (float)(-(Math.atan2(d2, d4) * 180.0 / 3.141592653589793));
            if (Math.abs(this.rotationPitch - pitch) < rotSpeed && Math.abs(this.rotationYaw - yaw) < rotSpeed) {
                final float r = ac.isPilot((Entity)this) ? 0.1f : 0.5f;
                this.rotationPitch = pitch + (this.rand.nextFloat() - 0.5f) * r - cw.fixRotationPitch;
                this.rotationYaw = yaw + (this.rand.nextFloat() - 0.5f) * r;
                if (!this.waitCooldown || ws.currentHeat <= 0 || ws.getInfo().maxHeatCount <= 0) {
                    this.waitCooldown = false;
                    final MCH_WeaponParam prm = new MCH_WeaponParam();
                    prm.setPosition(ac.posX, ac.posY, ac.posZ);
                    prm.user = (Entity)this;
                    prm.entity = (Entity)ac;
                    prm.option1 = ((cw instanceof MCH_WeaponEntitySeeker) ? this.targetEntity.getEntityId() : 0);
                    if (ac.useCurrentWeapon(prm) && ws.getInfo().maxHeatCount > 0 && ws.currentHeat > ws.getInfo().maxHeatCount * 4 / 5) {
                        this.waitCooldown = true;
                    }
                }
            }
            if (Math.abs(pitch - this.rotationPitch) >= rotSpeed) {
                this.rotationPitch += ((pitch > this.rotationPitch) ? rotSpeed : (-rotSpeed));
            }
            if (Math.abs(yaw - this.rotationYaw) >= rotSpeed) {
                if (Math.abs(yaw - this.rotationYaw) <= 180.0f) {
                    this.rotationYaw += ((yaw > this.rotationYaw) ? rotSpeed : (-rotSpeed));
                }
                else {
                    this.rotationYaw += ((yaw > this.rotationYaw) ? (-rotSpeed) : rotSpeed);
                }
            }
            this.rotationYawHead = this.rotationYaw;
            this.targetPrevPosX = this.targetEntity.posX;
            this.targetPrevPosY = this.targetEntity.posY;
            this.targetPrevPosZ = this.targetEntity.posZ;
        }
        else {
            this.rotationPitch *= 0.95f;
        }
    }
    
    private boolean checkPitch(final EntityLivingBase entity, final MCH_EntityAircraft ac, final Vec3 pos) {
        try {
            final double d0 = entity.posX - pos.xCoord;
            final double d2 = entity.posY - pos.yCoord;
            final double d3 = entity.posZ - pos.zCoord;
            final double d4 = MathHelper.sqrt_double(d0 * d0 + d3 * d3);
            final float pitch = (float)(-(Math.atan2(d2, d4) * 180.0 / 3.141592653589793));
            final MCH_AircraftInfo ai = ac.getAcInfo();
            if (ac instanceof MCH_EntityVehicle && ac.isPilot((Entity)this) && Math.abs(ai.minRotationPitch) + Math.abs(ai.maxRotationPitch) > 0.0f) {
                if (pitch < ai.minRotationPitch) {
                    return false;
                }
                if (pitch > ai.maxRotationPitch) {
                    return false;
                }
            }
            final MCH_WeaponBase cw = ac.getCurrentWeapon((Entity)this).getCurrentWeapon();
            if (!(cw instanceof MCH_WeaponEntitySeeker)) {
                final MCH_AircraftInfo.Weapon wi = ai.getWeaponById(ac.getCurrentWeaponID((Entity)this));
                if (Math.abs(wi.minPitch) + Math.abs(wi.maxPitch) > 0.0f) {
                    if (pitch < wi.minPitch) {
                        return false;
                    }
                    if (pitch > wi.maxPitch) {
                        return false;
                    }
                }
            }
        }
        catch (Exception ex) {}
        return true;
    }
    
    public Vec3 getGunnerWeaponPos(final MCH_EntityAircraft ac, final MCH_WeaponSet ws) {
        final MCH_SeatInfo seatInfo = ac.getSeatInfo((Entity)this);
        if ((seatInfo != null && seatInfo.rotSeat) || ac instanceof MCH_EntityVehicle) {
            return ac.calcOnTurretPos(ws.getCurrentWeapon().position).addVector(ac.posX, ac.posY, ac.posZ);
        }
        return ac.getTransformedPosition(ws.getCurrentWeapon().position);
    }
    
    private boolean isInAttackable(final EntityLivingBase entity, final MCH_EntityAircraft ac, final MCH_WeaponSet ws, final Vec3 pos) {
        if (ac instanceof MCH_EntityVehicle) {
            return true;
        }
        try {
            if (ac.getCurrentWeapon((Entity)this).getCurrentWeapon() instanceof MCH_WeaponEntitySeeker) {
                return true;
            }
            final MCH_AircraftInfo.Weapon wi = ac.getAcInfo().getWeaponById(ac.getCurrentWeaponID((Entity)this));
            final Vec3 v1 = Vec3.createVectorHelper(0.0, 0.0, 1.0);
            final float yaw = -ac.getRotYaw() + (wi.maxYaw + wi.minYaw) / 2.0f - wi.defaultYaw;
            v1.rotateAroundY(yaw * 3.1415927f / 180.0f);
            final Vec3 v2 = Vec3.createVectorHelper(entity.posX - pos.xCoord, 0.0, entity.posZ - pos.zCoord).normalize();
            final double dot = v1.dotProduct(v2);
            final double rad = Math.acos(dot);
            final double deg = rad * 180.0 / 3.141592653589793;
            return deg < Math.abs(wi.maxYaw - wi.minYaw) / 2.0f;
        }
        catch (Exception e) {
            return false;
        }
    }
    
    public MCH_EntityAircraft getAc() {
        if (this.ridingEntity == null) {
            return null;
        }
        return (this.ridingEntity instanceof MCH_EntitySeat) ? ((MCH_EntitySeat)this.ridingEntity).getParent() : ((this.ridingEntity instanceof MCH_EntityAircraft) ? this.ridingEntity : null);
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setBoolean("Creative", this.isCreative);
        nbt.setString("OwnerUUID", this.ownerUUID);
        nbt.setString("TeamName", this.getTeamName());
        nbt.setInteger("TargetType", this.targetType);
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.isCreative = nbt.getBoolean("Creative");
        this.ownerUUID = nbt.getString("OwnerUUID");
        this.setTeamName(nbt.getString("TeamName"));
        this.targetType = nbt.getInteger("TargetType");
    }
    
    public void travelToDimension(final int dim) {
    }
    
    public void setDead() {
        if (!this.worldObj.isRemote && !this.isDead && !this.isCreative) {
            if (this.targetType == 0) {
                this.dropItem((Item)MCH_MOD.itemSpawnGunnerVsMonster, 1);
            }
            else {
                this.dropItem((Item)MCH_MOD.itemSpawnGunnerVsPlayer, 1);
            }
        }
        super.setDead();
        MCH_Lib.DbgLog(this.worldObj, "MCH_EntityGunner.setDead type=%d :" + this.toString(), new Object[] { this.targetType });
    }
    
    public boolean attackEntityFrom(final DamageSource ds, final float amount) {
        if (ds == DamageSource.outOfWorld) {
            this.setDead();
        }
        return super.attackEntityFrom(ds, amount);
    }
    
    public ItemStack getHeldItem() {
        return null;
    }
    
    public ItemStack getEquipmentInSlot(final int p_71124_1_) {
        return null;
    }
    
    public void setCurrentItemOrArmor(final int slotIn, final ItemStack itemStackIn) {
    }
    
    public ItemStack[] getInventory() {
        return new ItemStack[0];
    }
}
