//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.helicopter;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import java.util.*;
import mcheli.aircraft.*;
import mcheli.*;
import mcheli.wrapper.*;
import mcheli.particles.*;
import net.minecraft.util.*;

public class MCH_EntityHeli extends MCH_EntityAircraft
{
    public static final byte FOLD_STAT_FOLDED = 0;
    public static final byte FOLD_STAT_FOLDING = 1;
    public static final byte FOLD_STAT_UNFOLDED = 2;
    public static final byte FOLD_STAT_UNFOLDING = 3;
    private MCH_HeliInfo heliInfo;
    public double prevRotationRotor;
    public double rotationRotor;
    public MCH_Rotor[] rotors;
    public byte lastFoldBladeStat;
    public int foldBladesCooldown;
    public float prevRollFactor;
    
    public MCH_EntityHeli(final World world) {
        super(world);
        this.prevRotationRotor = 0.0;
        this.rotationRotor = 0.0;
        this.prevRollFactor = 0.0f;
        this.heliInfo = null;
        this.currentSpeed = 0.07;
        this.preventEntitySpawning = true;
        this.setSize(2.0f, 0.7f);
        this.yOffset = this.height / 2.0f;
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.weapons = this.createWeapon(0);
        this.rotors = new MCH_Rotor[0];
        this.lastFoldBladeStat = -1;
        if (this.worldObj.isRemote) {
            this.foldBladesCooldown = 40;
        }
    }
    
    public String getKindName() {
        return "helicopters";
    }
    
    public String getEntityType() {
        return "Plane";
    }
    
    public MCH_HeliInfo getHeliInfo() {
        return this.heliInfo;
    }
    
    public void changeType(final String type) {
        MCH_Lib.DbgLog(this.worldObj, "MCH_EntityHeli.changeType " + type + " : " + this.toString(), new Object[0]);
        if (!type.isEmpty()) {
            this.heliInfo = MCH_HeliInfoManager.get(type);
        }
        if (this.heliInfo == null) {
            MCH_Lib.Log((Entity)this, "##### MCH_EntityHeli changeHeliType() Heli info null %d, %s, %s", W_Entity.getEntityId((Entity)this), type, this.getEntityName());
            this.setDead(true);
        }
        else {
            this.setAcInfo((MCH_AircraftInfo)this.heliInfo);
            this.newSeats(this.getAcInfo().getNumSeatAndRack());
            this.createRotors();
            this.weapons = this.createWeapon(1 + this.getSeatNum());
            this.initPartRotation(this.getRotYaw(), this.getRotPitch());
        }
    }
    
    public Item getItem() {
        return (Item)((this.getHeliInfo() != null) ? this.getHeliInfo().item : null);
    }
    
    public boolean canMountWithNearEmptyMinecart() {
        final MCH_Config config = MCH_MOD.config;
        return MCH_Config.MountMinecartHeli.prmBool;
    }
    
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(30, (Object)2);
    }
    
    protected void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setDouble("RotorSpeed", this.getCurrentThrottle());
        par1NBTTagCompound.setDouble("rotetionRotor", this.rotationRotor);
        par1NBTTagCompound.setBoolean("FoldBlade", this.getFoldBladeStat() == 0);
    }
    
    protected void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        final boolean beforeFoldBlade = this.getFoldBladeStat() == 0;
        if (this.getCommonUniqueId().isEmpty()) {
            this.setCommonUniqueId(par1NBTTagCompound.getString("HeliUniqueId"));
            MCH_Lib.Log((Entity)this, "# MCH_EntityHeli readEntityFromNBT() " + W_Entity.getEntityId((Entity)this) + ", " + this.getEntityName() + ", AircraftUniqueId=null, HeliUniqueId=" + this.getCommonUniqueId(), new Object[0]);
        }
        if (this.getTypeName().isEmpty()) {
            this.setTypeName(par1NBTTagCompound.getString("HeliType"));
            MCH_Lib.Log((Entity)this, "# MCH_EntityHeli readEntityFromNBT() " + W_Entity.getEntityId((Entity)this) + ", " + this.getEntityName() + ", TypeName=null, HeliType=" + this.getTypeName(), new Object[0]);
        }
        this.setCurrentThrottle(par1NBTTagCompound.getDouble("RotorSpeed"));
        this.rotationRotor = par1NBTTagCompound.getDouble("rotetionRotor");
        this.setFoldBladeStat((byte)(par1NBTTagCompound.getBoolean("FoldBlade") ? 0 : 2));
        if (this.heliInfo == null) {
            this.heliInfo = MCH_HeliInfoManager.get(this.getTypeName());
            if (this.heliInfo == null) {
                MCH_Lib.Log((Entity)this, "##### MCH_EntityHeli readEntityFromNBT() Heli info null %d, %s", W_Entity.getEntityId((Entity)this), this.getEntityName());
                this.setDead(true);
            }
            else {
                this.setAcInfo((MCH_AircraftInfo)this.heliInfo);
            }
        }
        if (!beforeFoldBlade && this.getFoldBladeStat() == 0) {
            this.forceFoldBlade();
        }
        this.prevRotationRotor = this.rotationRotor;
    }
    
    public float getSoundVolume() {
        if (this.getAcInfo() != null && this.getAcInfo().throttleUpDown <= 0.0f) {
            return 0.0f;
        }
        return (float)this.getCurrentThrottle() * 2.0f;
    }
    
    public float getSoundPitch() {
        return (float)(0.2 + this.getCurrentThrottle() * 0.2);
    }
    
    public String getDefaultSoundName() {
        return "heli";
    }
    
    public float getUnfoldLandingGearThrottle() {
        final double x = this.posX - this.prevPosX;
        final double y = this.posY - this.prevPosY;
        final double z = this.posZ - this.prevPosZ;
        final float s = this.getAcInfo().speed / 3.5f;
        return (x * x + y * y + z * z <= s) ? 0.8f : 0.3f;
    }
    
    protected void createRotors() {
        if (this.heliInfo == null) {
            return;
        }
        this.rotors = new MCH_Rotor[this.heliInfo.rotorList.size()];
        int i = 0;
        for (final MCH_HeliInfo.Rotor r : this.heliInfo.rotorList) {
            this.rotors[i] = new MCH_Rotor(r.bladeNum, r.bladeRot, this.worldObj.isRemote ? 2 : 2, (float)r.pos.xCoord, (float)r.pos.yCoord, (float)r.pos.zCoord, (float)r.rot.xCoord, (float)r.rot.yCoord, (float)r.rot.zCoord, r.haveFoldFunc);
            ++i;
        }
    }
    
    protected void forceFoldBlade() {
        if (this.heliInfo != null && this.rotors.length > 0 && this.heliInfo.isEnableFoldBlade) {
            for (final MCH_Rotor r : this.rotors) {
                r.update((float)this.rotationRotor);
                this.foldBlades();
                r.forceFold();
            }
        }
    }
    
    public boolean isFoldBlades() {
        return this.heliInfo != null && this.rotors.length > 0 && this.getFoldBladeStat() == 0;
    }
    
    protected boolean canSwitchFoldBlades() {
        return this.heliInfo != null && this.rotors.length > 0 && this.heliInfo.isEnableFoldBlade && this.getCurrentThrottle() <= 0.01 && this.foldBladesCooldown == 0 && (this.getFoldBladeStat() == 2 || this.getFoldBladeStat() == 0);
    }
    
    protected boolean canUseBlades() {
        if (this.heliInfo == null) {
            return false;
        }
        if (this.rotors.length <= 0) {
            return true;
        }
        if (this.getFoldBladeStat() == 2) {
            for (final MCH_Rotor r : this.rotors) {
                if (r.isFoldingOrUnfolding()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    
    protected void foldBlades() {
        if (this.heliInfo == null || this.rotors.length <= 0) {
            return;
        }
        this.setCurrentThrottle(0.0);
        for (final MCH_Rotor r : this.rotors) {
            r.startFold();
        }
    }
    
    public void unfoldBlades() {
        if (this.heliInfo == null || this.rotors.length <= 0) {
            return;
        }
        for (final MCH_Rotor r : this.rotors) {
            r.startUnfold();
        }
    }
    
    public void onRideEntity(final Entity ridingEntity) {
        if (ridingEntity instanceof MCH_EntitySeat) {
            if (this.heliInfo == null || this.rotors.length <= 0) {
                return;
            }
            if (this.heliInfo.isEnableFoldBlade) {
                this.forceFoldBlade();
                this.setFoldBladeStat((byte)0);
            }
        }
    }
    
    protected byte getFoldBladeStat() {
        return this.dataWatcher.getWatchableObjectByte(30);
    }
    
    public void setFoldBladeStat(final byte b) {
        if (!this.worldObj.isRemote && b >= 0 && b <= 3) {
            this.dataWatcher.updateObject(30, (Object)b);
        }
    }
    
    public boolean canSwitchGunnerMode() {
        if (super.canSwitchGunnerMode() && this.canUseBlades()) {
            final float roll = MathHelper.abs(MathHelper.wrapAngleTo180_float(this.getRotRoll()));
            final float pitch = MathHelper.abs(MathHelper.wrapAngleTo180_float(this.getRotPitch()));
            if (roll < 40.0f && pitch < 40.0f) {
                return true;
            }
        }
        return false;
    }
    
    public boolean canSwitchHoveringMode() {
        if (super.canSwitchHoveringMode() && this.canUseBlades()) {
            final float roll = MathHelper.abs(MathHelper.wrapAngleTo180_float(this.getRotRoll()));
            final float pitch = MathHelper.abs(MathHelper.wrapAngleTo180_float(this.getRotPitch()));
            if (roll < 40.0f && pitch < 40.0f) {
                return true;
            }
        }
        return false;
    }
    
    public void onUpdateAircraft() {
        if (this.heliInfo == null) {
            this.changeType(this.getTypeName());
            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;
            return;
        }
        if (!this.isRequestedSyncStatus) {
            this.isRequestedSyncStatus = true;
            if (this.worldObj.isRemote) {
                final int stat = this.getFoldBladeStat();
                if (stat == 1 || stat == 0) {
                    this.forceFoldBlade();
                }
                MCH_PacketStatusRequest.requestStatus((MCH_EntityAircraft)this);
            }
        }
        if (this.lastRiddenByEntity == null && this.getRiddenByEntity() != null) {
            this.initCurrentWeapon(this.getRiddenByEntity());
        }
        this.updateWeapons();
        this.onUpdate_Seats();
        this.onUpdate_Control();
        this.onUpdate_Rotor();
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (!this.isDestroyed() && this.isHovering() && MathHelper.abs(this.getRotPitch()) < 70.0f) {
            this.setRotPitch(this.getRotPitch() * 0.95f);
        }
        if (this.isDestroyed() && this.getCurrentThrottle() > 0.0) {
            if (MCH_Lib.getBlockIdY((Entity)this, 3, -2) > 0) {
                this.setCurrentThrottle(this.getCurrentThrottle() * 0.8);
            }
            if (this.isExploded()) {
                this.setCurrentThrottle(this.getCurrentThrottle() * 0.98);
            }
        }
        this.updateCameraViewers();
        if (this.worldObj.isRemote) {
            this.onUpdate_Client();
        }
        else {
            this.onUpdate_Server();
        }
    }
    
    public boolean canMouseRot() {
        return super.canMouseRot();
    }
    
    public boolean canUpdatePitch(final Entity player) {
        return super.canUpdatePitch(player) && !this.isHovering();
    }
    
    public boolean canUpdateRoll(final Entity player) {
        return super.canUpdateRoll(player) && !this.isHovering();
    }
    
    public boolean isOverridePlayerPitch() {
        return super.isOverridePlayerPitch() && !this.isHovering();
    }
    
    public float getRollFactor() {
        final float roll = super.getRollFactor();
        double d = this.getDistanceSq(this.prevPosX, this.posY, this.prevPosZ);
        final double s = this.getAcInfo().speed;
        d = ((s > 0.1) ? (d / s) : 0.0);
        final float f = this.prevRollFactor;
        return ((this.prevRollFactor = roll) + f) / 2.0f;
    }
    
    public float getControlRotYaw(final float mouseX, final float mouseY, final float tick) {
        return mouseX;
    }
    
    public float getControlRotPitch(final float mouseX, final float mouseY, final float tick) {
        return mouseY;
    }
    
    public float getControlRotRoll(final float mouseX, final float mouseY, final float tick) {
        return mouseX;
    }
    
    public void onUpdateAngles(final float partialTicks) {
        if (this.isDestroyed()) {
            return;
        }
        float rotRoll = this.isHovering() ? 0.07f : 0.04f;
        rotRoll = 1.0f - rotRoll * partialTicks;
        if (MCH_ServerSettings.enableRotationLimit) {
            if (this.getRotPitch() > MCH_ServerSettings.pitchLimitMax) {
                this.setRotPitch(this.getRotPitch() - Math.abs((this.getRotPitch() - MCH_ServerSettings.pitchLimitMax) * 0.1f * partialTicks));
            }
            if (this.getRotPitch() < MCH_ServerSettings.pitchLimitMin) {
                this.setRotPitch(this.getRotPitch() + Math.abs((this.getRotPitch() - MCH_ServerSettings.pitchLimitMin) * 0.2f * partialTicks));
            }
            if (this.getRotRoll() > MCH_ServerSettings.rollLimit) {
                this.setRotRoll(this.getRotRoll() - Math.abs((this.getRotRoll() - MCH_ServerSettings.rollLimit) * 0.03f * partialTicks));
            }
            if (this.getRotRoll() < -MCH_ServerSettings.rollLimit) {
                this.setRotRoll(this.getRotRoll() + Math.abs((this.getRotRoll() + MCH_ServerSettings.rollLimit) * 0.03f * partialTicks));
            }
        }
        if (this.getRotRoll() > 0.1 && this.getRotRoll() < 65.0f) {
            this.setRotRoll(this.getRotRoll() * rotRoll);
        }
        if (this.getRotRoll() < -0.1 && this.getRotRoll() > -65.0f) {
            this.setRotRoll(this.getRotRoll() * rotRoll);
        }
        if (MCH_Lib.getBlockIdY((Entity)this, 3, -3) == 0) {
            if (this.moveLeft && !this.moveRight) {
                this.setRotRoll(this.getRotRoll() - 1.2f * partialTicks);
            }
            if (this.moveRight && !this.moveLeft) {
                this.setRotRoll(this.getRotRoll() + 1.2f * partialTicks);
            }
        }
        else {
            if (MathHelper.abs(this.getRotPitch()) < 40.0f) {
                this.applyOnGroundPitch(0.97f);
            }
            if (this.heliInfo.isEnableFoldBlade && this.rotors.length > 0 && this.getFoldBladeStat() == 0 && !this.isDestroyed()) {
                if (this.moveLeft && !this.moveRight) {
                    this.setRotYaw(this.getRotYaw() - 0.5f * partialTicks);
                }
                if (this.moveRight && !this.moveLeft) {
                    this.setRotYaw(this.getRotYaw() + 0.5f * partialTicks);
                }
            }
        }
    }
    
    protected void onUpdate_Rotor() {
        final byte stat = this.getFoldBladeStat();
        boolean isEndSwitch = true;
        if (stat != this.lastFoldBladeStat) {
            if (stat == 1) {
                this.foldBlades();
            }
            else if (stat == 3) {
                this.unfoldBlades();
            }
            if (this.worldObj.isRemote) {
                this.foldBladesCooldown = 40;
            }
            this.lastFoldBladeStat = stat;
        }
        else if (this.foldBladesCooldown > 0) {
            --this.foldBladesCooldown;
        }
        for (final MCH_Rotor r : this.rotors) {
            r.update((float)this.rotationRotor);
            if (r.isFoldingOrUnfolding()) {
                isEndSwitch = false;
            }
        }
        if (isEndSwitch) {
            if (stat == 1) {
                this.setFoldBladeStat((byte)0);
            }
            else if (stat == 3) {
                this.setFoldBladeStat((byte)2);
            }
        }
    }
    
    protected void onUpdate_Control() {
        if (this.isHoveringMode() && !this.canUseFuel(true)) {
            this.switchHoveringMode(false);
        }
        if (this.isGunnerMode && !this.canUseFuel()) {
            this.switchGunnerMode(false);
        }
        if (!this.isDestroyed() && (this.getRiddenByEntity() != null || this.isHoveringMode()) && this.canUseBlades() && this.isCanopyClose() && this.canUseFuel(true)) {
            if (!this.isHovering()) {
                this.onUpdate_ControlNotHovering();
            }
            else {
                this.onUpdate_ControlHovering();
            }
        }
        else {
            if (this.getCurrentThrottle() > 0.0) {
                this.addCurrentThrottle(-0.00125);
            }
            else {
                this.setCurrentThrottle(0.0);
            }
            if (this.heliInfo.isEnableFoldBlade && this.rotors.length > 0 && this.getFoldBladeStat() == 0 && this.onGround && !this.isDestroyed()) {
                this.onUpdate_ControlFoldBladeAndOnGround();
            }
        }
        if (this.worldObj.isRemote) {
            if (!W_Lib.isClientPlayer(this.getRiddenByEntity())) {
                final double ct = this.getThrottle();
                if (this.getCurrentThrottle() >= ct - 0.02) {
                    this.addCurrentThrottle(-0.01);
                }
                else if (this.getCurrentThrottle() < ct) {
                    this.addCurrentThrottle(0.01);
                }
            }
        }
        else {
            this.setThrottle(this.getCurrentThrottle());
        }
        if (this.getCurrentThrottle() < 0.0) {
            this.setCurrentThrottle(0.0);
        }
        this.prevRotationRotor = this.rotationRotor;
        this.rotationRotor += (1.0 - Math.pow(1.0 - this.getCurrentThrottle(), 5.0)) * this.getAcInfo().rotorSpeed;
        this.rotationRotor %= 360.0;
    }
    
    protected void onUpdate_ControlNotHovering() {
        final float throttleUpDown = this.getAcInfo().throttleUpDown;
        if (this.throttleUp) {
            if (this.getCurrentThrottle() < 1.0) {
                this.addCurrentThrottle(0.02 * throttleUpDown);
            }
            else {
                this.setCurrentThrottle(1.0);
            }
        }
        else if (this.throttleDown) {
            if (this.getCurrentThrottle() > 0.0) {
                this.addCurrentThrottle(-0.014285714285714285 * throttleUpDown);
            }
            else {
                this.setCurrentThrottle(0.0);
            }
        }
        else if ((!this.worldObj.isRemote || W_Lib.isClientPlayer(this.getRiddenByEntity())) && this.cs_heliAutoThrottleDown) {
            if (this.getCurrentThrottle() > 0.52) {
                this.addCurrentThrottle(-0.01 * throttleUpDown);
            }
            else if (this.getCurrentThrottle() < 0.48) {
                this.addCurrentThrottle(0.01 * throttleUpDown);
            }
        }
        if (!this.worldObj.isRemote) {
            boolean move = false;
            float yaw = this.getRotYaw();
            double x = 0.0;
            double z = 0.0;
            if (this.moveLeft && !this.moveRight) {
                yaw = this.getRotYaw() - 90.0f;
                x += Math.sin(yaw * 3.141592653589793 / 180.0);
                z += Math.cos(yaw * 3.141592653589793 / 180.0);
                move = true;
            }
            if (this.moveRight && !this.moveLeft) {
                yaw = this.getRotYaw() + 90.0f;
                x += Math.sin(yaw * 3.141592653589793 / 180.0);
                z += Math.cos(yaw * 3.141592653589793 / 180.0);
                move = true;
            }
            if (move) {
                final double f = 1.0;
                final double d = Math.sqrt(x * x + z * z);
                this.motionX -= x / d * 0.019999999552965164 * f * this.getAcInfo().speed;
                this.motionZ += z / d * 0.019999999552965164 * f * this.getAcInfo().speed;
            }
        }
    }
    
    protected void onUpdate_ControlHovering() {
        if (this.getCurrentThrottle() < 1.0) {
            this.addCurrentThrottle(0.03333333333333333);
        }
        else {
            this.setCurrentThrottle(1.0);
        }
        if (!this.worldObj.isRemote) {
            boolean move = false;
            float yaw = this.getRotYaw();
            double x = 0.0;
            double z = 0.0;
            if (this.throttleUp) {
                yaw = this.getRotYaw();
                x += Math.sin(yaw * 3.141592653589793 / 180.0);
                z += Math.cos(yaw * 3.141592653589793 / 180.0);
                move = true;
            }
            if (this.throttleDown) {
                yaw = this.getRotYaw() - 180.0f;
                x += Math.sin(yaw * 3.141592653589793 / 180.0);
                z += Math.cos(yaw * 3.141592653589793 / 180.0);
                move = true;
            }
            if (this.moveLeft && !this.moveRight) {
                yaw = this.getRotYaw() - 90.0f;
                x += Math.sin(yaw * 3.141592653589793 / 180.0);
                z += Math.cos(yaw * 3.141592653589793 / 180.0);
                move = true;
            }
            if (this.moveRight && !this.moveLeft) {
                yaw = this.getRotYaw() + 90.0f;
                x += Math.sin(yaw * 3.141592653589793 / 180.0);
                z += Math.cos(yaw * 3.141592653589793 / 180.0);
                move = true;
            }
            if (move) {
                final double d = Math.sqrt(x * x + z * z);
                this.motionX -= x / d * 0.009999999776482582 * this.getAcInfo().speed;
                this.motionZ += z / d * 0.009999999776482582 * this.getAcInfo().speed;
            }
        }
    }
    
    protected void onUpdate_ControlFoldBladeAndOnGround() {
        if (!this.worldObj.isRemote) {
            boolean move = false;
            float yaw = this.getRotYaw();
            double x = 0.0;
            double z = 0.0;
            if (this.throttleUp) {
                yaw = this.getRotYaw();
                x += Math.sin(yaw * 3.141592653589793 / 180.0);
                z += Math.cos(yaw * 3.141592653589793 / 180.0);
                move = true;
            }
            if (this.throttleDown) {
                yaw = this.getRotYaw() - 180.0f;
                x += Math.sin(yaw * 3.141592653589793 / 180.0);
                z += Math.cos(yaw * 3.141592653589793 / 180.0);
                move = true;
            }
            if (move) {
                final double d = Math.sqrt(x * x + z * z);
                this.motionX -= x / d * 0.029999999329447746;
                this.motionZ += z / d * 0.029999999329447746;
            }
        }
    }
    
    protected void onUpdate_Particle2() {
        if (!this.worldObj.isRemote) {
            return;
        }
        if (this.getHP() > this.getMaxHP() * 0.5) {
            return;
        }
        if (this.getHeliInfo() == null) {
            return;
        }
        final int rotorNum = this.getHeliInfo().rotorList.size();
        if (rotorNum <= 0) {
            return;
        }
        if (this.isFirstDamageSmoke) {
            this.prevDamageSmokePos = new Vec3[rotorNum];
        }
        for (int ri = 0; ri < rotorNum; ++ri) {
            final Vec3 rotor_pos = this.getHeliInfo().rotorList.get(ri).pos;
            final float yaw = this.getRotYaw();
            final float pitch = this.getRotPitch();
            final Vec3 pos = MCH_Lib.RotVec3(rotor_pos, -yaw, -pitch, -this.getRotRoll());
            final double x = this.posX + pos.xCoord;
            final double y = this.posY + pos.yCoord;
            final double z = this.posZ + pos.zCoord;
            if (this.isFirstDamageSmoke) {
                this.prevDamageSmokePos[ri] = Vec3.createVectorHelper(x, y, z);
            }
            final Vec3 prev = this.prevDamageSmokePos[ri];
            final double dx = x - prev.xCoord;
            final double dy = y - prev.yCoord;
            final double dz = z - prev.zCoord;
            final int num = (int)(MathHelper.sqrt_double(dx * dx + dy * dy + dz * dz) * 2.0f) + 1;
            for (double i = 0.0; i < num; ++i) {
                final double p = this.getHP() / (double)this.getMaxHP();
                if (p < this.rand.nextFloat() / 2.0f) {
                    final float c = 0.2f + this.rand.nextFloat() * 0.3f;
                    final MCH_ParticleParam prm = new MCH_ParticleParam(this.worldObj, "smoke", prev.xCoord + (x - prev.xCoord) * (i / num), prev.yCoord + (y - prev.yCoord) * (i / num), prev.zCoord + (z - prev.zCoord) * (i / num));
                    prm.motionX = (this.rand.nextDouble() - 0.5) * 0.3;
                    prm.motionY = this.rand.nextDouble() * 0.1;
                    prm.motionZ = (this.rand.nextDouble() - 0.5) * 0.3;
                    prm.size = (this.rand.nextInt(5) + 5.0f) * 1.0f;
                    prm.setColor(0.7f + this.rand.nextFloat() * 0.1f, c, c, c);
                    MCH_ParticlesUtil.spawnParticle(prm);
                    final int ebi = this.rand.nextInt(1 + this.extraBoundingBox.length);
                    if (p < 0.3 && ebi > 0) {
                        final AxisAlignedBB bb = this.extraBoundingBox[ebi - 1].boundingBox;
                        final double bx = (bb.maxX + bb.minX) / 2.0;
                        final double by = (bb.maxY + bb.minY) / 2.0;
                        final double bz = (bb.maxZ + bb.minZ) / 2.0;
                        prm.posX = bx;
                        prm.posY = by;
                        prm.posZ = bz;
                        MCH_ParticlesUtil.spawnParticle(prm);
                    }
                }
            }
            this.prevDamageSmokePos[ri].xCoord = x;
            this.prevDamageSmokePos[ri].yCoord = y;
            this.prevDamageSmokePos[ri].zCoord = z;
        }
        this.isFirstDamageSmoke = false;
    }
    
    protected void onUpdate_Client() {
        if (this.getRiddenByEntity() != null && W_Lib.isClientPlayer(this.getRiddenByEntity())) {
            this.getRiddenByEntity().rotationPitch = this.getRiddenByEntity().prevRotationPitch;
        }
        if (this.aircraftPosRotInc > 0) {
            this.applyServerPositionAndRotation();
        }
        else {
            this.setPosition(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            if (!this.isDestroyed() && (this.onGround || MCH_Lib.getBlockIdY((Entity)this, 1, -2) > 0)) {
                this.motionX *= 0.95;
                this.motionZ *= 0.95;
                this.applyOnGroundPitch(0.95f);
            }
            if (this.isInWater()) {
                this.motionX *= 0.99;
                this.motionZ *= 0.99;
            }
        }
        if (this.isDestroyed()) {
            if (this.rotDestroyedYaw < 15.0f) {
                this.rotDestroyedYaw += 0.3f;
            }
            this.setRotYaw(this.getRotYaw() + this.rotDestroyedYaw * (float)this.getCurrentThrottle());
            if (MCH_Lib.getBlockIdY((Entity)this, 3, -3) == 0) {
                if (MathHelper.abs(this.getRotPitch()) < 10.0f) {
                    this.setRotPitch(this.getRotPitch() + this.rotDestroyedPitch);
                }
                this.setRotRoll(this.getRotRoll() + this.rotDestroyedRoll);
            }
        }
        if (this.getRiddenByEntity() != null) {}
        this.onUpdate_ParticleSandCloud(false);
        this.onUpdate_Particle2();
        this.updateCamera(this.posX, this.posY, this.posZ);
    }
    
    private void onUpdate_Server() {
        final double prevMotion = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        final float ogp = this.getAcInfo().onGroundPitch;
        if (!this.isHovering()) {
            double dp = 0.0;
            if (this.canFloatWater()) {
                dp = this.getWaterDepth();
            }
            if (dp == 0.0) {
                this.motionY += (this.isInWater() ? this.getAcInfo().gravityInWater : ((double)this.getAcInfo().gravity));
                final float yaw = this.getRotYaw() / 180.0f * 3.1415927f;
                float pitch = this.getRotPitch();
                if (MCH_Lib.getBlockIdY((Entity)this, 3, -3) > 0) {
                    pitch -= ogp;
                }
                this.motionX += 0.1 * MathHelper.sin(yaw) * this.currentSpeed * -(pitch * pitch * pitch / 20000.0f) * this.getCurrentThrottle();
                this.motionZ += 0.1 * MathHelper.cos(yaw) * this.currentSpeed * (pitch * pitch * pitch / 20000.0f) * this.getCurrentThrottle();
                double y = 0.0;
                if (MathHelper.abs(this.getRotPitch()) + MathHelper.abs(this.getRotRoll() * 0.9f) <= 40.0f) {
                    y = 1.0 - y / 40.0;
                }
                double throttle = this.getCurrentThrottle();
                if (this.isDestroyed()) {
                    throttle *= -0.65;
                }
                this.motionY += (y * 0.025 + 0.03) * throttle;
            }
            else {
                if (MathHelper.abs(this.getRotPitch()) < 40.0f) {
                    float pitch2 = this.getRotPitch();
                    pitch2 -= ogp;
                    pitch2 *= 0.9f;
                    pitch2 += ogp;
                    this.setRotPitch(pitch2);
                }
                if (MathHelper.abs(this.getRotRoll()) < 40.0f) {
                    this.setRotRoll(this.getRotRoll() * 0.9f);
                }
                if (dp < 1.0) {
                    this.motionY -= 1.0E-4;
                    this.motionY += 0.007 * this.getCurrentThrottle();
                }
                else {
                    if (this.motionY < 0.0) {
                        this.motionY *= 0.7;
                    }
                    this.motionY += 0.007;
                }
            }
        }
        else {
            if (this.rand.nextInt(50) == 0) {
                this.motionX += (this.rand.nextDouble() - 0.5) / 30.0;
            }
            if (this.rand.nextInt(50) == 0) {
                this.motionY += (this.rand.nextDouble() - 0.5) / 50.0;
            }
            if (this.rand.nextInt(50) == 0) {
                this.motionZ += (this.rand.nextDouble() - 0.5) / 30.0;
            }
        }
        double motion = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        final float speedLimit = this.getAcInfo().speed;
        if (motion > speedLimit) {
            this.motionX *= speedLimit / motion;
            this.motionZ *= speedLimit / motion;
            motion = speedLimit;
        }
        if (this.isDestroyed()) {
            this.motionX *= 0.0;
            this.motionZ *= 0.0;
            this.currentSpeed = 0.0;
        }
        if (motion > prevMotion && this.currentSpeed < speedLimit) {
            this.currentSpeed += (speedLimit - this.currentSpeed) / 35.0;
            if (this.currentSpeed > speedLimit) {
                this.currentSpeed = speedLimit;
            }
        }
        else {
            this.currentSpeed -= (this.currentSpeed - 0.07) / 35.0;
            if (this.currentSpeed < 0.07) {
                this.currentSpeed = 0.07;
            }
        }
        if (this.onGround) {
            this.motionX *= 0.5;
            this.motionZ *= 0.5;
            if (MathHelper.abs(this.getRotPitch()) < 40.0f) {
                float pitch = this.getRotPitch();
                pitch -= ogp;
                pitch *= 0.9f;
                pitch += ogp;
                this.setRotPitch(pitch);
            }
            if (MathHelper.abs(this.getRotRoll()) < 40.0f) {
                this.setRotRoll(this.getRotRoll() * 0.9f);
            }
        }
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionY *= 0.95;
        this.motionX *= 0.99;
        this.motionZ *= 0.99;
        this.setRotation(this.getRotYaw(), this.getRotPitch());
        this.onUpdate_updateBlock();
        if (this.getRiddenByEntity() != null && this.getRiddenByEntity().isDead) {
            this.unmountEntity();
            this.riddenByEntity = null;
        }
    }
}
