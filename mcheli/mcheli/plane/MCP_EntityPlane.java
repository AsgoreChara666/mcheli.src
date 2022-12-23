//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.plane;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import mcheli.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import mcheli.aircraft.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import mcheli.particles.*;
import java.util.*;
import mcheli.wrapper.*;

public class MCP_EntityPlane extends MCH_EntityAircraft
{
    private MCP_PlaneInfo planeInfo;
    public float soundVolume;
    public MCH_Parts partNozzle;
    public MCH_Parts partWing;
    public float rotationRotor;
    public float prevRotationRotor;
    public float addkeyRotValue;
    
    public MCP_EntityPlane(final World world) {
        super(world);
        this.planeInfo = null;
        this.currentSpeed = 0.07;
        this.preventEntitySpawning = true;
        this.setSize(2.0f, 0.7f);
        this.yOffset = this.height / 2.0f;
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.weapons = this.createWeapon(0);
        this.soundVolume = 0.0f;
        this.partNozzle = null;
        this.partWing = null;
        this.stepHeight = 0.6f;
        this.rotationRotor = 0.0f;
        this.prevRotationRotor = 0.0f;
    }
    
    public String getKindName() {
        return "planes";
    }
    
    public String getEntityType() {
        return "Plane";
    }
    
    public MCP_PlaneInfo getPlaneInfo() {
        return this.planeInfo;
    }
    
    public void changeType(final String type) {
        MCH_Lib.DbgLog(this.worldObj, "MCP_EntityPlane.changeType " + type + " : " + this.toString(), new Object[0]);
        if (!type.isEmpty()) {
            this.planeInfo = MCP_PlaneInfoManager.get(type);
        }
        if (this.planeInfo == null) {
            MCH_Lib.Log((Entity)this, "##### MCP_EntityPlane changePlaneType() Plane info null %d, %s, %s", new Object[] { W_Entity.getEntityId((Entity)this), type, this.getEntityName() });
            this.setDead();
        }
        else {
            this.setAcInfo((MCH_AircraftInfo)this.planeInfo);
            this.newSeats(this.getAcInfo().getNumSeatAndRack());
            this.partNozzle = this.createNozzle(this.planeInfo);
            this.partWing = this.createWing(this.planeInfo);
            this.weapons = this.createWeapon(1 + this.getSeatNum());
            this.initPartRotation(this.getRotYaw(), this.getRotPitch());
        }
    }
    
    public Item getItem() {
        return (Item)((this.getPlaneInfo() != null) ? this.getPlaneInfo().item : null);
    }
    
    public boolean canMountWithNearEmptyMinecart() {
        final MCH_Config config = MCH_MOD.config;
        return MCH_Config.MountMinecartPlane.prmBool;
    }
    
    protected void entityInit() {
        super.entityInit();
    }
    
    protected void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
    }
    
    protected void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        if (this.planeInfo == null) {
            this.planeInfo = MCP_PlaneInfoManager.get(this.getTypeName());
            if (this.planeInfo == null) {
                MCH_Lib.Log((Entity)this, "##### MCP_EntityPlane readEntityFromNBT() Plane info null %d, %s", new Object[] { W_Entity.getEntityId((Entity)this), this.getEntityName() });
                this.setDead();
            }
            else {
                this.setAcInfo((MCH_AircraftInfo)this.planeInfo);
            }
        }
    }
    
    public void setDead() {
        super.setDead();
    }
    
    public int getNumEjectionSeat() {
        if (this.getAcInfo() != null && this.getAcInfo().isEnableEjectionSeat) {
            final int n = this.getSeatNum() + 1;
            return (n <= 2) ? n : 0;
        }
        return 0;
    }
    
    public void onInteractFirst(final EntityPlayer player) {
        this.addkeyRotValue = 0.0f;
    }
    
    public boolean canSwitchGunnerMode() {
        if (!super.canSwitchGunnerMode()) {
            return false;
        }
        final float roll = MathHelper.abs(MathHelper.wrapAngleTo180_float(this.getRotRoll()));
        final float pitch = MathHelper.abs(MathHelper.wrapAngleTo180_float(this.getRotPitch()));
        return roll <= 40.0f && pitch <= 40.0f && this.getCurrentThrottle() > 0.6000000238418579 && MCH_Lib.getBlockIdY((Entity)this, 3, -5) == 0;
    }
    
    public void onUpdateAircraft() {
        if (this.planeInfo == null) {
            this.changeType(this.getTypeName());
            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;
            return;
        }
        if (!this.isRequestedSyncStatus) {
            this.isRequestedSyncStatus = true;
            if (this.worldObj.isRemote) {
                MCH_PacketStatusRequest.requestStatus((MCH_EntityAircraft)this);
            }
        }
        if (this.lastRiddenByEntity == null && this.getRiddenByEntity() != null) {
            this.initCurrentWeapon(this.getRiddenByEntity());
        }
        this.updateWeapons();
        this.onUpdate_Seats();
        this.onUpdate_Control();
        this.prevRotationRotor = this.rotationRotor;
        this.rotationRotor += (float)(this.getCurrentThrottle() * this.getAcInfo().rotorSpeed);
        if (this.rotationRotor > 360.0f) {
            this.rotationRotor -= 360.0f;
            this.prevRotationRotor -= 360.0f;
        }
        if (this.rotationRotor < 0.0f) {
            this.rotationRotor += 360.0f;
            this.prevRotationRotor += 360.0f;
        }
        if (this.onGround && this.getVtolMode() == 0 && this.planeInfo.isDefaultVtol) {
            this.swithVtolMode(true);
        }
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (!this.isDestroyed() && this.isHovering() && MathHelper.abs(this.getRotPitch()) < 70.0f) {
            this.setRotPitch(this.getRotPitch() * 0.95f, "isHovering()");
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
    
    public boolean canUpdateYaw(final Entity player) {
        return super.canUpdateYaw(player) && !this.isHovering();
    }
    
    public boolean canUpdatePitch(final Entity player) {
        return super.canUpdatePitch(player) && !this.isHovering();
    }
    
    public boolean canUpdateRoll(final Entity player) {
        return super.canUpdateRoll(player) && !this.isHovering();
    }
    
    public float getYawFactor() {
        final float yaw = (this.getVtolMode() > 0) ? this.getPlaneInfo().vtolYaw : super.getYawFactor();
        return yaw * 0.8f;
    }
    
    public float getPitchFactor() {
        final float pitch = (this.getVtolMode() > 0) ? this.getPlaneInfo().vtolPitch : super.getPitchFactor();
        return pitch * 0.8f;
    }
    
    public float getRollFactor() {
        final float roll = (this.getVtolMode() > 0) ? this.getPlaneInfo().vtolYaw : super.getRollFactor();
        return roll * 0.8f;
    }
    
    public boolean isOverridePlayerPitch() {
        return super.isOverridePlayerPitch() && !this.isHovering();
    }
    
    public boolean isOverridePlayerYaw() {
        return super.isOverridePlayerYaw() && !this.isHovering();
    }
    
    public float getControlRotYaw(final float mouseX, final float mouseY, final float tick) {
        final MCH_Config config = MCH_MOD.config;
        if (MCH_Config.MouseControlFlightSimMode.prmBool) {
            this.rotationByKey(tick);
            return this.addkeyRotValue * 20.0f;
        }
        return mouseX;
    }
    
    public float getControlRotPitch(final float mouseX, final float mouseY, final float tick) {
        return mouseY;
    }
    
    public float getControlRotRoll(final float mouseX, final float mouseY, final float tick) {
        final MCH_Config config = MCH_MOD.config;
        if (MCH_Config.MouseControlFlightSimMode.prmBool) {
            return mouseX * 2.0f;
        }
        if (this.getVtolMode() == 0) {
            return mouseX * 0.5f;
        }
        return mouseX;
    }
    
    private void rotationByKey(final float partialTicks) {
        float rot = 0.2f;
        final MCH_Config config = MCH_MOD.config;
        if (!MCH_Config.MouseControlFlightSimMode.prmBool && this.getVtolMode() != 0) {
            rot *= 0.0f;
        }
        if (this.moveLeft && !this.moveRight) {
            this.addkeyRotValue -= rot * partialTicks;
        }
        if (this.moveRight && !this.moveLeft) {
            this.addkeyRotValue += rot * partialTicks;
        }
    }
    
    public void onUpdateAngles(final float partialTicks) {
        if (this.isDestroyed()) {
            return;
        }
        if (this.isGunnerMode) {
            this.setRotPitch(this.getRotPitch() * 0.95f);
            this.setRotYaw(this.getRotYaw() + this.getAcInfo().autoPilotRot * 0.2f);
            if (MathHelper.abs(this.getRotRoll()) > 20.0f) {
                this.setRotRoll(this.getRotRoll() * 0.95f);
            }
        }
        final boolean isFly = MCH_Lib.getBlockIdY((Entity)this, 3, -3) == 0;
        if (!isFly || this.isFreeLookMode() || this.isGunnerMode || (this.getAcInfo().isFloat && this.getWaterDepth() > 0.0)) {
            float gmy = 1.0f;
            if (!isFly) {
                gmy = this.getAcInfo().mobilityYawOnGround;
                if (!this.getAcInfo().canRotOnGround) {
                    final Block block = MCH_Lib.getBlockY((Entity)this, 3, -2, false);
                    if (!W_Block.isEqual(block, W_Block.getWater()) && !W_Block.isEqual(block, W_Blocks.air)) {
                        gmy = 0.0f;
                    }
                }
            }
            if (this.moveLeft && !this.moveRight) {
                this.setRotYaw(this.getRotYaw() - 0.6f * gmy * partialTicks);
            }
            if (this.moveRight && !this.moveLeft) {
                this.setRotYaw(this.getRotYaw() + 0.6f * gmy * partialTicks);
            }
        }
        else if (isFly) {
            final MCH_Config config = MCH_MOD.config;
            if (!MCH_Config.MouseControlFlightSimMode.prmBool) {
                this.rotationByKey(partialTicks);
                this.setRotRoll(this.getRotRoll() + this.addkeyRotValue * 0.5f * this.getAcInfo().mobilityRoll);
            }
        }
        this.addkeyRotValue *= (float)(1.0 - 0.1f * partialTicks);
        if (!isFly && MathHelper.abs(this.getRotPitch()) < 40.0f) {
            this.applyOnGroundPitch(0.97f);
        }
        if (this.getNozzleRotation() > 0.001f) {
            float rot = 1.0f - 0.03f * partialTicks;
            this.setRotPitch(this.getRotPitch() * rot);
            rot = 1.0f - 0.1f * partialTicks;
            this.setRotRoll(this.getRotRoll() * rot);
        }
    }
    
    protected void onUpdate_Control() {
        if (this.isGunnerMode && !this.canUseFuel()) {
            this.switchGunnerMode(false);
        }
        this.throttleBack *= (float)0.8;
        if (this.getRiddenByEntity() != null && !this.getRiddenByEntity().isDead && this.isCanopyClose() && this.canUseWing() && this.canUseFuel() && !this.isDestroyed()) {
            this.onUpdate_ControlNotHovering();
        }
        else if (this.isTargetDrone() && this.canUseFuel() && !this.isDestroyed()) {
            this.throttleUp = true;
            this.onUpdate_ControlNotHovering();
        }
        else if (this.getCurrentThrottle() > 0.0) {
            this.addCurrentThrottle(-0.0025 * this.getAcInfo().throttleUpDown);
        }
        else {
            this.setCurrentThrottle(0.0);
        }
        if (this.getCurrentThrottle() < 0.0) {
            this.setCurrentThrottle(0.0);
        }
        if (this.worldObj.isRemote) {
            if (!W_Lib.isClientPlayer(this.getRiddenByEntity())) {
                final double ct = this.getThrottle();
                if (this.getCurrentThrottle() > ct) {
                    this.addCurrentThrottle(-0.005);
                }
                if (this.getCurrentThrottle() < ct) {
                    this.addCurrentThrottle(0.005);
                }
            }
        }
        else {
            this.setThrottle(this.getCurrentThrottle());
        }
    }
    
    protected void onUpdate_ControlNotHovering() {
        if (!this.isGunnerMode) {
            float throttleUpDown = this.getAcInfo().throttleUpDown;
            final boolean turn = (this.moveLeft && !this.moveRight) || (!this.moveLeft && this.moveRight);
            final float pivotTurnThrottle = this.getAcInfo().pivotTurnThrottle;
            boolean localThrottleUp = this.throttleUp;
            if (turn && this.getCurrentThrottle() < this.getAcInfo().pivotTurnThrottle && !localThrottleUp && !this.throttleDown) {
                localThrottleUp = true;
                throttleUpDown *= 2.0f;
            }
            if (localThrottleUp) {
                float f = throttleUpDown;
                if (this.getRidingEntity() != null) {
                    final double mx = this.getRidingEntity().motionX;
                    final double mz = this.getRidingEntity().motionZ;
                    f *= MathHelper.sqrt_double(mx * mx + mz * mz) * this.getAcInfo().throttleUpDownOnEntity;
                }
                if (this.getAcInfo().enableBack && this.throttleBack > 0.0f) {
                    this.throttleBack -= (float)(0.01 * f);
                }
                else {
                    this.throttleBack = 0.0f;
                    if (this.getCurrentThrottle() < 1.0) {
                        this.addCurrentThrottle(0.01 * f);
                    }
                    else {
                        this.setCurrentThrottle(1.0);
                    }
                }
            }
            else if (this.throttleDown) {
                if (this.getCurrentThrottle() > 0.0) {
                    this.addCurrentThrottle(-0.01 * throttleUpDown);
                }
                else {
                    this.setCurrentThrottle(0.0);
                    if (this.getAcInfo().enableBack) {
                        this.throttleBack += (float)(0.0025 * throttleUpDown);
                        if (this.throttleBack > 0.6f) {
                            this.throttleBack = 0.6f;
                        }
                    }
                }
            }
            else if (this.cs_planeAutoThrottleDown && this.getCurrentThrottle() > 0.0) {
                this.addCurrentThrottle(-0.005 * throttleUpDown);
                if (this.getCurrentThrottle() <= 0.0) {
                    this.setCurrentThrottle(0.0);
                }
            }
        }
    }
    
    protected void onUpdate_Particle() {
        if (this.worldObj.isRemote) {
            this.onUpdate_ParticleLandingGear();
            this.onUpdate_ParticleNozzle();
        }
    }
    
    protected void onUpdate_Particle2() {
        if (!this.worldObj.isRemote) {
            return;
        }
        if (this.getHP() >= this.getMaxHP() * 0.5) {
            return;
        }
        if (this.getPlaneInfo() == null) {
            return;
        }
        int rotorNum = this.getPlaneInfo().rotorList.size();
        if (rotorNum < 0) {
            rotorNum = 0;
        }
        if (this.isFirstDamageSmoke) {
            this.prevDamageSmokePos = new Vec3[rotorNum + 1];
        }
        final float yaw = this.getRotYaw();
        final float pitch = this.getRotPitch();
        final float roll = this.getRotRoll();
        boolean spawnSmoke = true;
        for (int ri = 0; ri < rotorNum; ++ri) {
            if (this.getHP() >= this.getMaxHP() * 0.2 && this.getMaxHP() > 0) {
                final int d = (int)((this.getHP() / (double)this.getMaxHP() - 0.2) / 0.3 * 15.0);
                if (d > 0 && this.rand.nextInt(d) > 0) {
                    spawnSmoke = false;
                }
            }
            final Vec3 rotor_pos = this.getPlaneInfo().rotorList.get(ri).pos;
            final Vec3 pos = MCH_Lib.RotVec3(rotor_pos, -yaw, -pitch, -roll);
            final double x = this.posX + pos.xCoord;
            final double y = this.posY + pos.yCoord;
            final double z = this.posZ + pos.zCoord;
            this.onUpdate_Particle2SpawnSmoke(ri, x, y, z, 1.0f, spawnSmoke);
        }
        spawnSmoke = true;
        if (this.getHP() >= this.getMaxHP() * 0.2 && this.getMaxHP() > 0) {
            final int d2 = (int)((this.getHP() / (double)this.getMaxHP() - 0.2) / 0.3 * 15.0);
            if (d2 > 0 && this.rand.nextInt(d2) > 0) {
                spawnSmoke = false;
            }
        }
        double px = this.posX;
        double py = this.posY;
        double pz = this.posZ;
        if (this.getSeatInfo(0) != null && this.getSeatInfo(0).pos != null) {
            final Vec3 pos2 = MCH_Lib.RotVec3(0.0, this.getSeatInfo(0).pos.yCoord, -2.0, -yaw, -pitch, -roll);
            px += pos2.xCoord;
            py += pos2.yCoord;
            pz += pos2.zCoord;
        }
        this.onUpdate_Particle2SpawnSmoke(rotorNum, px, py, pz, (rotorNum == 0) ? 2.0f : 1.0f, spawnSmoke);
        this.isFirstDamageSmoke = false;
    }
    
    public void onUpdate_Particle2SpawnSmoke(final int ri, final double x, final double y, final double z, final float size, final boolean spawnSmoke) {
        if (this.isFirstDamageSmoke || this.prevDamageSmokePos[ri] == null) {
            this.prevDamageSmokePos[ri] = Vec3.createVectorHelper(x, y, z);
        }
        final Vec3 prev = this.prevDamageSmokePos[ri];
        final double dx = x - prev.xCoord;
        final double dy = y - prev.yCoord;
        final double dz = z - prev.zCoord;
        for (int num = (int)(MathHelper.sqrt_double(dx * dx + dy * dy + dz * dz) / 0.3) + 1, i = 0; i < num; ++i) {
            final float c = 0.2f + this.rand.nextFloat() * 0.3f;
            final MCH_ParticleParam prm = new MCH_ParticleParam(this.worldObj, "smoke", prev.xCoord + (x - prev.xCoord) * i / 3.0, prev.yCoord + (y - prev.yCoord) * i / 3.0, prev.zCoord + (z - prev.zCoord) * i / 3.0);
            prm.motionX = size * (this.rand.nextDouble() - 0.5) * 0.3;
            prm.motionY = size * this.rand.nextDouble() * 0.1;
            prm.motionZ = size * (this.rand.nextDouble() - 0.5) * 0.3;
            prm.size = size * (this.rand.nextInt(5) + 5.0f) * 1.0f;
            prm.setColor(0.7f + this.rand.nextFloat() * 0.1f, c, c, c);
            MCH_ParticlesUtil.spawnParticle(prm);
        }
        this.prevDamageSmokePos[ri].xCoord = x;
        this.prevDamageSmokePos[ri].yCoord = y;
        this.prevDamageSmokePos[ri].zCoord = z;
    }
    
    public void onUpdate_ParticleLandingGear() {
        final double d = this.motionX * this.motionX + this.motionZ * this.motionZ;
        if (d > 0.01) {
            final int x = MathHelper.floor_double(this.posX + 0.5);
            final int y = MathHelper.floor_double(this.posY - 0.5);
            final int z = MathHelper.floor_double(this.posZ + 0.5);
            MCH_ParticlesUtil.spawnParticleTileCrack(this.worldObj, x, y, z, this.posX + (this.rand.nextFloat() - 0.5) * this.width, this.boundingBox.minY + 0.1, this.posZ + (this.rand.nextFloat() - 0.5) * this.width, -this.motionX * 4.0, 1.5, -this.motionZ * 4.0);
        }
    }
    
    private void onUpdate_ParticleSplash() {
        if (this.getAcInfo() == null) {
            return;
        }
        if (!this.worldObj.isRemote) {
            return;
        }
        final double mx = this.posX - this.prevPosX;
        final double mz = this.posZ - this.prevPosZ;
        double dist = mx * mx + mz * mz;
        if (dist > 1.0) {
            dist = 1.0;
        }
        for (final MCH_AircraftInfo.ParticleSplash p : this.getAcInfo().particleSplashs) {
            for (int i = 0; i < p.num; ++i) {
                if (dist > 0.03 + this.rand.nextFloat() * 0.1) {
                    this.setParticleSplash(p.pos, -mx * p.acceleration, p.motionY, -mz * p.acceleration, p.gravity, p.size * (0.5 + dist * 0.5), p.age);
                }
            }
        }
    }
    
    private void setParticleSplash(final Vec3 pos, final double mx, final double my, final double mz, final float gravity, final double size, final int age) {
        Vec3 v = this.getTransformedPosition(pos);
        v = v.addVector(this.rand.nextDouble() - 0.5, (this.rand.nextDouble() - 0.5) * 0.5, this.rand.nextDouble() - 0.5);
        final int x = (int)(v.xCoord + 0.5);
        final int y = (int)(v.yCoord + 0.0);
        final int z = (int)(v.zCoord + 0.5);
        if (W_WorldFunc.isBlockWater(this.worldObj, x, y, z)) {
            final float c = this.rand.nextFloat() * 0.3f + 0.7f;
            final MCH_ParticleParam prm = new MCH_ParticleParam(this.worldObj, "smoke", v.xCoord, v.yCoord, v.zCoord);
            prm.motionX = mx + (this.rand.nextFloat() - 0.5) * 0.7;
            prm.motionY = my;
            prm.motionZ = mz + (this.rand.nextFloat() - 0.5) * 0.7;
            prm.size = (float)size * (this.rand.nextFloat() * 0.2f + 0.8f);
            prm.setColor(0.9f, c, c, c);
            prm.age = age + (int)(this.rand.nextFloat() * 0.5 * age);
            prm.gravity = gravity;
            MCH_ParticlesUtil.spawnParticle(prm);
        }
    }
    
    public void onUpdate_ParticleNozzle() {
        if (this.planeInfo == null || !this.planeInfo.haveNozzle()) {
            return;
        }
        if (this.getCurrentThrottle() <= 0.10000000149011612) {
            return;
        }
        final float yaw = this.getRotYaw();
        final float pitch = this.getRotPitch();
        final float roll = this.getRotRoll();
        final Vec3 nozzleRot = MCH_Lib.RotVec3(0.0, 0.0, 1.0, -yaw - 180.0f, pitch - this.getNozzleRotation(), roll);
        for (final MCH_AircraftInfo.DrawnPart nozzle : this.planeInfo.nozzles) {
            if (this.rand.nextFloat() > this.getCurrentThrottle() * 1.5) {
                continue;
            }
            final Vec3 nozzlePos = MCH_Lib.RotVec3(nozzle.pos, -yaw, -pitch, -roll);
            final double x = this.posX + nozzlePos.xCoord + nozzleRot.xCoord;
            final double y = this.posY + nozzlePos.yCoord + nozzleRot.yCoord;
            final double z = this.posZ + nozzlePos.zCoord + nozzleRot.zCoord;
            float a = 0.7f;
            if (W_WorldFunc.getBlockId(this.worldObj, (int)(x + nozzleRot.xCoord * 3.0), (int)(y + nozzleRot.yCoord * 3.0), (int)(z + nozzleRot.zCoord * 3.0)) != 0) {
                a = 2.0f;
            }
            final MCH_ParticleParam prm = new MCH_ParticleParam(this.worldObj, "smoke", x, y, z, nozzleRot.xCoord + (this.rand.nextFloat() - 0.5f) * a, nozzleRot.yCoord, nozzleRot.zCoord + (this.rand.nextFloat() - 0.5f) * a, 5.0f * this.getAcInfo().particlesScale);
            MCH_ParticlesUtil.spawnParticle(prm);
        }
    }
    
    public void destroyAircraft() {
        super.destroyAircraft();
        int inv = 1;
        if (this.getRotRoll() >= 0.0f) {
            if (this.getRotRoll() > 90.0f) {
                inv = -1;
            }
        }
        else if (this.getRotRoll() > -90.0f) {
            inv = -1;
        }
        this.rotDestroyedRoll = (0.5f + this.rand.nextFloat()) * inv;
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
            if (MCH_Lib.getBlockIdY((Entity)this, 3, -3) == 0) {
                if (MathHelper.abs(this.getRotPitch()) < 10.0f) {
                    this.setRotPitch(this.getRotPitch() + this.rotDestroyedPitch);
                }
                final float roll = MathHelper.abs(this.getRotRoll());
                if (roll < 45.0f || roll > 135.0f) {
                    this.setRotRoll(this.getRotRoll() + this.rotDestroyedRoll);
                }
            }
            else if (MathHelper.abs(this.getRotPitch()) > 20.0f) {
                this.setRotPitch(this.getRotPitch() * 0.99f);
            }
        }
        if (this.getRiddenByEntity() != null) {}
        this.updateSound();
        this.onUpdate_Particle();
        this.onUpdate_Particle2();
        this.onUpdate_ParticleSplash();
        this.onUpdate_ParticleSandCloud(true);
        this.updateCamera(this.posX, this.posY, this.posZ);
    }
    
    private void onUpdate_Server() {
        final Entity rdnEnt = this.getRiddenByEntity();
        final double prevMotion = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        double dp = 0.0;
        if (this.canFloatWater()) {
            dp = this.getWaterDepth();
        }
        boolean levelOff = this.isGunnerMode;
        if (dp == 0.0) {
            if (this.isTargetDrone() && this.canUseFuel() && !this.isDestroyed()) {
                Block block = MCH_Lib.getBlockY((Entity)this, 3, -40, true);
                if (block == null || W_Block.isEqual(block, W_Blocks.air)) {
                    this.setRotYaw(this.getRotYaw() + this.getAcInfo().autoPilotRot * 1.0f);
                    this.setRotPitch(this.getRotPitch() * 0.95f);
                    if (this.canFoldLandingGear()) {
                        this.foldLandingGear();
                    }
                    levelOff = true;
                }
                else {
                    block = MCH_Lib.getBlockY((Entity)this, 3, -5, true);
                    if (block == null || W_Block.isEqual(block, W_Blocks.air)) {
                        this.setRotYaw(this.getRotYaw() + this.getAcInfo().autoPilotRot * 2.0f);
                        if (this.getRotPitch() > -20.0f) {
                            this.setRotPitch(this.getRotPitch() - 0.5f);
                        }
                    }
                }
            }
            if (!levelOff) {
                this.motionY += 0.04 + (this.isInWater() ? this.getAcInfo().gravityInWater : this.getAcInfo().gravity);
                this.motionY += -0.047 * (1.0 - this.getCurrentThrottle());
            }
            else {
                this.motionY *= 0.8;
            }
        }
        else {
            this.setRotPitch(this.getRotPitch() * 0.8f, "getWaterDepth != 0");
            if (MathHelper.abs(this.getRotRoll()) < 40.0f) {
                this.setRotRoll(this.getRotRoll() * 0.9f);
            }
            if (dp < 1.0) {
                this.motionY -= 1.0E-4;
                this.motionY += 0.007 * this.getCurrentThrottle();
            }
            else {
                if (this.motionY < 0.0) {
                    this.motionY /= 2.0;
                }
                this.motionY += 0.007;
            }
        }
        final float throttle = (float)(this.getCurrentThrottle() / 10.0);
        Vec3 v;
        if (this.getNozzleRotation() > 0.001f) {
            this.setRotPitch(this.getRotPitch() * 0.95f);
            v = MCH_Lib.Rot2Vec3(this.getRotYaw(), this.getRotPitch() - this.getNozzleRotation());
            if (this.getNozzleRotation() >= 90.0f) {
                final Vec3 vec3 = v;
                vec3.xCoord *= 0.800000011920929;
                final Vec3 vec4 = v;
                vec4.zCoord *= 0.800000011920929;
            }
        }
        else {
            v = MCH_Lib.Rot2Vec3(this.getRotYaw(), this.getRotPitch() - 10.0f);
        }
        if (!levelOff) {
            if (this.getNozzleRotation() <= 0.01f) {
                this.motionY += v.yCoord * throttle / 2.0;
            }
            else {
                this.motionY += v.yCoord * throttle / 8.0;
            }
        }
        boolean canMove = true;
        if (!this.getAcInfo().canMoveOnGround) {
            final Block block2 = MCH_Lib.getBlockY((Entity)this, 3, -2, false);
            if (!W_Block.isEqual(block2, W_Block.getWater()) && !W_Block.isEqual(block2, W_Blocks.air)) {
                canMove = false;
            }
        }
        if (canMove) {
            if (this.getAcInfo().enableBack && this.throttleBack > 0.0f) {
                this.motionX -= v.xCoord * this.throttleBack;
                this.motionZ -= v.zCoord * this.throttleBack;
            }
            else {
                this.motionX += v.xCoord * throttle;
                this.motionZ += v.zCoord * throttle;
            }
        }
        double motion = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        final float speedLimit = this.getMaxSpeed();
        if (motion > speedLimit) {
            this.motionX *= speedLimit / motion;
            this.motionZ *= speedLimit / motion;
            motion = speedLimit;
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
        if (this.onGround || MCH_Lib.getBlockIdY((Entity)this, 1, -2) > 0) {
            this.motionX *= this.getAcInfo().motionFactor;
            this.motionZ *= this.getAcInfo().motionFactor;
            if (MathHelper.abs(this.getRotPitch()) < 40.0f) {
                this.applyOnGroundPitch(0.8f);
            }
        }
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionY *= 0.95;
        this.motionX *= this.getAcInfo().motionFactor;
        this.motionZ *= this.getAcInfo().motionFactor;
        this.setRotation(this.getRotYaw(), this.getRotPitch());
        this.onUpdate_updateBlock();
        if (this.getRiddenByEntity() != null && this.getRiddenByEntity().isDead) {
            this.unmountEntity();
            this.riddenByEntity = null;
        }
    }
    
    public float getMaxSpeed() {
        float f = 0.0f;
        if (this.partWing != null && this.getPlaneInfo().isVariableSweepWing) {
            f = (this.getPlaneInfo().sweepWingSpeed - this.getPlaneInfo().speed) * this.partWing.getFactor();
        }
        else if (this.partHatch != null && this.getPlaneInfo().isVariableSweepWing) {
            f = (this.getPlaneInfo().sweepWingSpeed - this.getPlaneInfo().speed) * this.partHatch.getFactor();
        }
        return this.getPlaneInfo().speed + f;
    }
    
    public float getSoundVolume() {
        if (this.getAcInfo() != null && this.getAcInfo().throttleUpDown <= 0.0f) {
            return 0.0f;
        }
        return this.soundVolume * 0.7f;
    }
    
    public void updateSound() {
        float target = (float)this.getCurrentThrottle();
        if (this.getRiddenByEntity() != null && (this.partCanopy == null || this.getCanopyRotation() < 1.0f)) {
            target += 0.1f;
        }
        if (this.soundVolume < target) {
            this.soundVolume += 0.02f;
            if (this.soundVolume >= target) {
                this.soundVolume = target;
            }
        }
        else if (this.soundVolume > target) {
            this.soundVolume -= 0.02f;
            if (this.soundVolume <= target) {
                this.soundVolume = target;
            }
        }
    }
    
    public float getSoundPitch() {
        return (float)(0.6 + this.getCurrentThrottle() * 0.4);
    }
    
    public String getDefaultSoundName() {
        return "plane";
    }
    
    public void updateParts(final int stat) {
        super.updateParts(stat);
        if (this.isDestroyed()) {
            return;
        }
        final MCH_Parts[] arr$;
        final MCH_Parts[] parts = arr$ = new MCH_Parts[] { this.partNozzle, this.partWing };
        for (final MCH_Parts p : arr$) {
            if (p != null) {
                p.updateStatusClient(stat);
                p.update();
            }
        }
        if (!this.worldObj.isRemote && this.partWing != null && this.getPlaneInfo().isVariableSweepWing && this.partWing.isON()) {
            if (this.getCurrentThrottle() >= 0.20000000298023224) {
                if (this.getCurrentThrottle() < 0.5 || MCH_Lib.getBlockIdY((Entity)this, 1, -10) != 0) {
                    this.partWing.setStatusServer(false);
                }
            }
        }
    }
    
    public float getUnfoldLandingGearThrottle() {
        return 0.7f;
    }
    
    public boolean canSwitchVtol() {
        if (this.planeInfo == null || !this.planeInfo.isEnableVtol) {
            return false;
        }
        if (this.getModeSwitchCooldown() > 0) {
            return false;
        }
        if (this.getVtolMode() == 1) {
            return false;
        }
        if (MathHelper.abs(this.getRotRoll()) > 30.0f) {
            return false;
        }
        if (this.onGround && this.planeInfo.isDefaultVtol) {
            return false;
        }
        this.setModeSwitchCooldown(20);
        return true;
    }
    
    public boolean getNozzleStat() {
        return this.partNozzle != null && this.partNozzle.getStatus();
    }
    
    public int getVtolMode() {
        if (!this.getNozzleStat()) {
            return (this.getNozzleRotation() > 0.005f) ? 1 : 0;
        }
        return (this.getNozzleRotation() >= 89.995f) ? 2 : 1;
    }
    
    public float getFuleConsumptionFactor() {
        return super.getFuelConsumptionFactor() * ((this.getVtolMode() != 2 || true) ? 1 : 0);
    }
    
    public float getNozzleRotation() {
        return (this.partNozzle != null) ? this.partNozzle.rotation : 0.0f;
    }
    
    public float getPrevNozzleRotation() {
        return (this.partNozzle != null) ? this.partNozzle.prevRotation : 0.0f;
    }
    
    public void swithVtolMode(final boolean mode) {
        if (this.partNozzle != null) {
            if (this.planeInfo.isDefaultVtol && this.onGround && !mode) {
                return;
            }
            if (!this.worldObj.isRemote) {
                this.partNozzle.setStatusServer(mode);
            }
            if (this.getRiddenByEntity() != null && !this.getRiddenByEntity().isDead) {
                final Entity riddenByEntity = this.getRiddenByEntity();
                final Entity riddenByEntity2 = this.getRiddenByEntity();
                final float n = 0.0f;
                riddenByEntity2.prevRotationPitch = n;
                riddenByEntity.rotationPitch = n;
            }
        }
    }
    
    protected MCH_Parts createNozzle(final MCP_PlaneInfo info) {
        MCH_Parts nozzle = null;
        if (info.haveNozzle() || info.haveRotor() || info.isEnableVtol) {
            nozzle = new MCH_Parts((Entity)this, 1, 31, "Nozzle");
            nozzle.rotationMax = 90.0f;
            nozzle.rotationInv = 1.5f;
            nozzle.soundStartSwichOn.setPrm("plane_cc", 1.0f, 0.5f);
            nozzle.soundEndSwichOn.setPrm("plane_cc", 1.0f, 0.5f);
            nozzle.soundStartSwichOff.setPrm("plane_cc", 1.0f, 0.5f);
            nozzle.soundEndSwichOff.setPrm("plane_cc", 1.0f, 0.5f);
            nozzle.soundSwitching.setPrm("plane_cv", 1.0f, 0.5f);
            if (info.isDefaultVtol) {
                nozzle.forceSwitch(true);
            }
        }
        return nozzle;
    }
    
    protected MCH_Parts createWing(final MCP_PlaneInfo info) {
        MCH_Parts wing = null;
        if (this.planeInfo.haveWing()) {
            wing = new MCH_Parts((Entity)this, 3, 31, "Wing");
            wing.rotationMax = 90.0f;
            wing.rotationInv = 2.5f;
            wing.soundStartSwichOn.setPrm("plane_cc", 1.0f, 0.5f);
            wing.soundEndSwichOn.setPrm("plane_cc", 1.0f, 0.5f);
            wing.soundStartSwichOff.setPrm("plane_cc", 1.0f, 0.5f);
            wing.soundEndSwichOff.setPrm("plane_cc", 1.0f, 0.5f);
        }
        return wing;
    }
    
    public boolean canUseWing() {
        if (this.partWing == null) {
            return true;
        }
        if (this.getPlaneInfo().isVariableSweepWing) {
            return this.getCurrentThrottle() >= 0.2 || this.partWing.isOFF();
        }
        return this.partWing.isOFF();
    }
    
    public boolean canFoldWing() {
        if (this.partWing == null || this.getModeSwitchCooldown() > 0) {
            return false;
        }
        if (this.getPlaneInfo().isVariableSweepWing) {
            if (this.onGround || MCH_Lib.getBlockIdY((Entity)this, 3, -20) != 0) {
                if (this.getCurrentThrottle() > 0.10000000149011612) {
                    return false;
                }
            }
            else if (this.getCurrentThrottle() < 0.699999988079071) {
                return false;
            }
        }
        else {
            if (!this.onGround && MCH_Lib.getBlockIdY((Entity)this, 3, -3) == 0) {
                return false;
            }
            if (this.getCurrentThrottle() > 0.009999999776482582) {
                return false;
            }
        }
        return this.partWing.isOFF();
    }
    
    public boolean canUnfoldWing() {
        return this.partWing != null && this.getModeSwitchCooldown() <= 0 && this.partWing.isON();
    }
    
    public void foldWing(final boolean fold) {
        if (this.partWing == null || this.getModeSwitchCooldown() > 0) {
            return;
        }
        this.partWing.setStatusServer(fold);
        this.setModeSwitchCooldown(20);
    }
    
    public float getWingRotation() {
        return (this.partWing != null) ? this.partWing.rotation : 0.0f;
    }
    
    public float getPrevWingRotation() {
        return (this.partWing != null) ? this.partWing.prevRotation : 0.0f;
    }
}
