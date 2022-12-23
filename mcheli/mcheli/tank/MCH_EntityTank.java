//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.tank;

import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.crash.*;
import net.minecraft.block.*;
import mcheli.particles.*;
import java.util.*;
import mcheli.wrapper.*;
import net.minecraft.command.*;
import net.minecraft.entity.item.*;
import mcheli.chain.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import mcheli.*;
import mcheli.weapon.*;
import mcheli.aircraft.*;

public class MCH_EntityTank extends MCH_EntityAircraft
{
    private MCH_TankInfo tankInfo;
    public float soundVolume;
    public float soundVolumeTarget;
    public float rotationRotor;
    public float prevRotationRotor;
    public float addkeyRotValue;
    public final MCH_WheelManager WheelMng;
    
    public MCH_EntityTank(final World world) {
        super(world);
        this.tankInfo = null;
        this.currentSpeed = 0.07;
        this.preventEntitySpawning = true;
        this.setSize(2.0f, 0.7f);
        this.yOffset = this.height / 2.0f;
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.weapons = this.createWeapon(0);
        this.soundVolume = 0.0f;
        this.stepHeight = 0.6f;
        this.rotationRotor = 0.0f;
        this.prevRotationRotor = 0.0f;
        this.WheelMng = new MCH_WheelManager(this);
    }
    
    public String getKindName() {
        return "tanks";
    }
    
    public String getEntityType() {
        return "Vehicle";
    }
    
    public MCH_TankInfo getTankInfo() {
        return this.tankInfo;
    }
    
    public void changeType(final String type) {
        MCH_Lib.DbgLog(this.worldObj, "MCH_EntityTank.changeType " + type + " : " + this.toString(), new Object[0]);
        if (!type.isEmpty()) {
            this.tankInfo = MCH_TankInfoManager.get(type);
        }
        if (this.tankInfo == null) {
            MCH_Lib.Log((Entity)this, "##### MCH_EntityTank changeTankType() Tank info null %d, %s, %s", new Object[] { W_Entity.getEntityId((Entity)this), type, this.getEntityName() });
            this.setDead();
        }
        else {
            this.setAcInfo((MCH_AircraftInfo)this.tankInfo);
            this.newSeats(this.getAcInfo().getNumSeatAndRack());
            this.switchFreeLookModeClient(this.getAcInfo().defaultFreelook);
            this.weapons = this.createWeapon(1 + this.getSeatNum());
            this.initPartRotation(this.getRotYaw(), this.getRotPitch());
            this.WheelMng.createWheels(this.worldObj, this.getAcInfo().wheels, Vec3.createVectorHelper(0.0, -0.35, (double)this.getTankInfo().weightedCenterZ));
        }
    }
    
    public Item getItem() {
        return (Item)((this.getTankInfo() != null) ? this.getTankInfo().item : null);
    }
    
    public boolean canMountWithNearEmptyMinecart() {
        final MCH_Config config = MCH_MOD.config;
        return MCH_Config.MountMinecartTank.prmBool;
    }
    
    protected void entityInit() {
        super.entityInit();
    }
    
    public float getGiveDamageRot() {
        return 91.0f;
    }
    
    protected void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
    }
    
    protected void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        if (this.tankInfo == null) {
            this.tankInfo = MCH_TankInfoManager.get(this.getTypeName());
            if (this.tankInfo == null) {
                MCH_Lib.Log((Entity)this, "##### MCH_EntityTank readEntityFromNBT() Tank info null %d, %s", new Object[] { W_Entity.getEntityId((Entity)this), this.getEntityName() });
                this.setDead();
            }
            else {
                this.setAcInfo((MCH_AircraftInfo)this.tankInfo);
            }
        }
    }
    
    public void setDead() {
        super.setDead();
    }
    
    public void onInteractFirst(final EntityPlayer player) {
        this.addkeyRotValue = 0.0f;
        final float lastRiderYaw = this.getLastRiderYaw();
        player.prevRotationYawHead = lastRiderYaw;
        player.rotationYawHead = lastRiderYaw;
        final float lastRiderYaw2 = this.getLastRiderYaw();
        player.rotationYaw = lastRiderYaw2;
        player.prevRotationYaw = lastRiderYaw2;
        player.rotationPitch = this.getLastRiderPitch();
    }
    
    public boolean canSwitchGunnerMode() {
        return !super.canSwitchGunnerMode() && false;
    }
    
    public void onUpdateAircraft() {
        if (this.tankInfo == null) {
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
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
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
    
    @SideOnly(Side.CLIENT)
    public boolean canRenderOnFire() {
        return this.isDestroyed() || super.canRenderOnFire();
    }
    
    public void updateExtraBoundingBox() {
        if (this.worldObj.isRemote) {
            super.updateExtraBoundingBox();
        }
        else if (this.getCountOnUpdate() <= 1) {
            super.updateExtraBoundingBox();
            super.updateExtraBoundingBox();
        }
    }
    
    public double calculateXOffset(final List list, final AxisAlignedBB bb, double parX) {
        for (int i = 0; i < list.size(); ++i) {
            parX = list.get(i).calculateXOffset(bb, parX);
        }
        bb.offset(parX, 0.0, 0.0);
        return parX;
    }
    
    public double calculateYOffset(final List list, final AxisAlignedBB bb, double parY) {
        for (int i = 0; i < list.size(); ++i) {
            parY = list.get(i).calculateYOffset(bb, parY);
        }
        bb.offset(0.0, parY, 0.0);
        return parY;
    }
    
    public double calculateZOffset(final List list, final AxisAlignedBB bb, double parZ) {
        for (int i = 0; i < list.size(); ++i) {
            parZ = list.get(i).calculateZOffset(bb, parZ);
        }
        bb.offset(0.0, 0.0, parZ);
        return parZ;
    }
    
    public void moveEntity(double parX, double parY, double parZ) {
        this.worldObj.theProfiler.startSection("move");
        this.yOffset2 *= 0.4f;
        final double nowPosX = this.posX;
        final double nowPosY = this.posY;
        final double nowPosZ = this.posZ;
        final double mx = parX;
        final double my = parY;
        final double mz = parZ;
        final AxisAlignedBB backUpAxisalignedBB = this.boundingBox.copy();
        List list = getCollidingBoundingBoxes((Entity)this, this.boundingBox.addCoord(parX, parY, parZ));
        parY = this.calculateYOffset(list, this.boundingBox, parY);
        final boolean flag1 = this.onGround || (my != parY && my < 0.0);
        for (final MCH_BoundingBox ebb : this.extraBoundingBox) {
            ebb.updatePosition(this.posX, this.posY, this.posZ, this.getRotYaw(), this.getRotPitch(), this.getRotRoll());
        }
        parX = this.calculateXOffset(list, this.boundingBox, parX);
        parZ = this.calculateZOffset(list, this.boundingBox, parZ);
        if (this.stepHeight > 0.0f && flag1 && this.yOffset2 < 0.05f && (mx != parX || mz != parZ)) {
            final double bkParX = parX;
            final double bkParY = parY;
            final double bkParZ = parZ;
            parX = mx;
            parY = this.stepHeight;
            parZ = mz;
            final AxisAlignedBB axisalignedbb1 = this.boundingBox.copy();
            this.boundingBox.setBB(backUpAxisalignedBB);
            list = getCollidingBoundingBoxes((Entity)this, this.boundingBox.addCoord(mx, parY, mz));
            parY = this.calculateYOffset(list, this.boundingBox, parY);
            parX = this.calculateXOffset(list, this.boundingBox, parX);
            parZ = this.calculateZOffset(list, this.boundingBox, parZ);
            parY = this.calculateYOffset(list, this.boundingBox, -this.stepHeight);
            if (bkParX * bkParX + bkParZ * bkParZ >= parX * parX + parZ * parZ) {
                parX = bkParX;
                parY = bkParY;
                parZ = bkParZ;
                this.boundingBox.setBB(axisalignedbb1);
            }
        }
        final double prevPX = this.posX;
        final double prevPZ = this.posZ;
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("rest");
        final double minX = this.boundingBox.minX;
        final double minZ = this.boundingBox.minZ;
        final double maxX = this.boundingBox.maxX;
        final double maxZ = this.boundingBox.maxZ;
        this.posX = (minX + maxX) / 2.0;
        this.posY = this.boundingBox.minY + this.yOffset - this.yOffset2;
        this.posZ = (minZ + maxZ) / 2.0;
        this.isCollidedHorizontally = (mx != parX || mz != parZ);
        this.isCollidedVertically = (my != parY);
        this.onGround = (my != parY && my < 0.0);
        this.isCollided = (this.isCollidedHorizontally || this.isCollidedVertically);
        this.updateFallState(parY, this.onGround);
        if (mx != parX) {
            this.motionX = 0.0;
        }
        if (my != parY) {
            this.motionY = 0.0;
        }
        if (mz != parZ) {
            this.motionZ = 0.0;
        }
        try {
            this.doBlockCollisions();
        }
        catch (Throwable throwable) {
            final CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Checking entity tile collision");
            final CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being checked for collision");
            this.addEntityCrashInfo(crashreportcategory);
        }
        this.worldObj.theProfiler.endSection();
    }
    
    private void rotationByKey(final float partialTicks) {
        final float rot = 0.2f;
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
        this.updateRecoil(partialTicks);
        this.setRotPitch(this.getRotPitch() + (this.WheelMng.targetPitch - this.getRotPitch()) * partialTicks);
        this.setRotRoll(this.getRotRoll() + (this.WheelMng.targetRoll - this.getRotRoll()) * partialTicks);
        final boolean isFly = MCH_Lib.getBlockIdY((Entity)this, 3, -3) == 0;
        if (!isFly || (this.getAcInfo().isFloat && this.getWaterDepth() > 0.0)) {
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
            final float pivotTurnThrottle = this.getAcInfo().pivotTurnThrottle;
            final double dx = this.posX - this.prevPosX;
            final double dz = this.posZ - this.prevPosZ;
            final double dist = dx * dx + dz * dz;
            if (pivotTurnThrottle <= 0.0f || this.getCurrentThrottle() >= pivotTurnThrottle || this.throttleBack >= pivotTurnThrottle / 10.0f || dist > this.throttleBack * 0.01) {
                float sf = (float)Math.sqrt((dist <= 1.0) ? dist : 1.0);
                if (pivotTurnThrottle <= 0.0f) {
                    sf = 1.0f;
                }
                final float flag = (!this.throttleUp && this.throttleDown && this.getCurrentThrottle() < pivotTurnThrottle + 0.05) ? -1.0f : 1.0f;
                if (this.moveLeft && !this.moveRight) {
                    this.setRotYaw(this.getRotYaw() - 0.6f * gmy * partialTicks * flag * sf);
                }
                if (this.moveRight && !this.moveLeft) {
                    this.setRotYaw(this.getRotYaw() + 0.6f * gmy * partialTicks * flag * sf);
                }
            }
        }
        this.addkeyRotValue *= (float)(1.0 - 0.1f * partialTicks);
    }
    
    protected void onUpdate_Control() {
        if (this.isGunnerMode && !this.canUseFuel()) {
            this.switchGunnerMode(false);
        }
        this.throttleBack *= (float)0.8;
        if (this.getBrake()) {
            this.throttleBack *= 0.5;
            if (this.getCurrentThrottle() > 0.0) {
                this.addCurrentThrottle(-0.02 * this.getAcInfo().throttleUpDown);
            }
            else {
                this.setCurrentThrottle(0.0);
            }
        }
        if (this.getRiddenByEntity() != null && !this.getRiddenByEntity().isDead && this.isCanopyClose() && this.canUseFuel() && !this.isDestroyed()) {
            this.onUpdate_ControlSub();
        }
        else if (this.isTargetDrone() && this.canUseFuel() && !this.isDestroyed()) {
            this.throttleUp = true;
            this.onUpdate_ControlSub();
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
            if (!W_Lib.isClientPlayer(this.getRiddenByEntity()) || this.getCountOnUpdate() % 200 == 0) {
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
    
    protected void onUpdate_ControlSub() {
        if (!this.isGunnerMode) {
            final float throttleUpDown = this.getAcInfo().throttleUpDown;
            if (this.throttleUp) {
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
            else if (this.cs_tankAutoThrottleDown && this.getCurrentThrottle() > 0.0) {
                this.addCurrentThrottle(-0.005 * throttleUpDown);
                if (this.getCurrentThrottle() <= 0.0) {
                    this.setCurrentThrottle(0.0);
                }
            }
        }
    }
    
    protected void onUpdate_Particle2() {
        if (!this.worldObj.isRemote) {
            return;
        }
        if (this.getHP() >= this.getMaxHP() * 0.5) {
            return;
        }
        if (this.getTankInfo() == null) {
            return;
        }
        int bbNum = this.getTankInfo().extraBoundingBox.size();
        if (bbNum < 0) {
            bbNum = 0;
        }
        if (this.isFirstDamageSmoke || this.prevDamageSmokePos.length != bbNum + 1) {
            this.prevDamageSmokePos = new Vec3[bbNum + 1];
        }
        final float yaw = this.getRotYaw();
        final float pitch = this.getRotPitch();
        final float roll = this.getRotRoll();
        for (int ri = 0; ri < bbNum; ++ri) {
            if (this.getHP() >= this.getMaxHP() * 0.2 && this.getMaxHP() > 0) {
                final int d = (int)((this.getHP() / (double)this.getMaxHP() - 0.2) / 0.3 * 15.0);
                if (d > 0 && this.rand.nextInt(d) > 0) {
                    continue;
                }
            }
            final MCH_BoundingBox bb = this.getTankInfo().extraBoundingBox.get(ri);
            final Vec3 pos = this.getTransformedPosition(bb.offsetX, bb.offsetY, bb.offsetZ);
            final double x = pos.xCoord;
            final double y = pos.yCoord;
            final double z = pos.zCoord;
            this.onUpdate_Particle2SpawnSmoke(ri, x, y, z, 1.0f);
        }
        boolean b = true;
        if (this.getHP() >= this.getMaxHP() * 0.2 && this.getMaxHP() > 0) {
            final int d = (int)((this.getHP() / (double)this.getMaxHP() - 0.2) / 0.3 * 15.0);
            if (d > 0 && this.rand.nextInt(d) > 0) {
                b = false;
            }
        }
        if (b) {
            double px = this.posX;
            double py = this.posY;
            double pz = this.posZ;
            if (this.getSeatInfo(0) != null && this.getSeatInfo(0).pos != null) {
                final Vec3 pos2 = MCH_Lib.RotVec3(0.0, this.getSeatInfo(0).pos.yCoord, -2.0, -yaw, -pitch, -roll);
                px += pos2.xCoord;
                py += pos2.yCoord;
                pz += pos2.zCoord;
            }
            this.onUpdate_Particle2SpawnSmoke(bbNum, px, py, pz, (bbNum == 0) ? 2.0f : 1.0f);
        }
        this.isFirstDamageSmoke = false;
    }
    
    public void onUpdate_Particle2SpawnSmoke(final int ri, final double x, final double y, final double z, final float size) {
        if (this.isFirstDamageSmoke || this.prevDamageSmokePos[ri] == null) {
            this.prevDamageSmokePos[ri] = Vec3.createVectorHelper(x, y, z);
        }
        final Vec3 prev = this.prevDamageSmokePos[ri];
        final double dx = x - prev.xCoord;
        final double dy = y - prev.yCoord;
        final double dz = z - prev.zCoord;
        for (int num = 1, i = 0; i < num; ++i) {
            final float c = 0.2f + this.rand.nextFloat() * 0.3f;
            final MCH_ParticleParam prm = new MCH_ParticleParam(this.worldObj, "smoke", x, y, z);
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
    
    public void onUpdate_Particle2SpawnSmode(final int ri, final double x, final double y, final double z, final float size) {
        if (this.isFirstDamageSmoke) {
            this.prevDamageSmokePos[ri] = Vec3.createVectorHelper(x, y, z);
        }
        final Vec3 prev = this.prevDamageSmokePos[ri];
        final double dx = x - prev.xCoord;
        final double dy = y - prev.yCoord;
        final double dz = z - prev.zCoord;
        for (int num = (int)(MathHelper.sqrt_double(dx * dx + dy * dy + dz * dz) / 0.3) + 1, i = 0; i < num; ++i) {
            final float c = 0.2f + this.rand.nextFloat() * 0.3f;
            final MCH_ParticleParam prm = new MCH_ParticleParam(this.worldObj, "smoke", x, y, z);
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
        this.WheelMng.particleLandingGear();
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
    
    public void destroyAircraft() {
        super.destroyAircraft();
        this.rotDestroyedPitch = 0.0f;
        this.rotDestroyedRoll = 0.0f;
        this.rotDestroyedYaw = 0.0f;
    }
    
    public int getClientPositionDelayCorrection() {
        return (this.getTankInfo() == null) ? 7 : ((this.getTankInfo().weightType == 1) ? 2 : 7);
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
        this.updateWheels();
        this.onUpdate_Particle2();
        this.updateSound();
        if (this.worldObj.isRemote) {
            this.onUpdate_ParticleLandingGear();
            this.onUpdate_ParticleSplash();
            this.onUpdate_ParticleSandCloud(true);
        }
        this.updateCamera(this.posX, this.posY, this.posZ);
    }
    
    public void applyOnGroundPitch(final float factor) {
    }
    
    private void onUpdate_Server() {
        final Entity rdnEnt = this.getRiddenByEntity();
        final double prevMotion = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        double dp = 0.0;
        if (this.canFloatWater()) {
            dp = this.getWaterDepth();
        }
        final boolean levelOff = this.isGunnerMode;
        if (dp == 0.0) {
            if (!levelOff) {
                this.motionY += 0.04 + (this.isInWater() ? this.getAcInfo().gravityInWater : this.getAcInfo().gravity);
                this.motionY += -0.047 * (1.0 - this.getCurrentThrottle());
            }
            else {
                this.motionY *= 0.8;
            }
        }
        else {
            if (MathHelper.abs(this.getRotRoll()) < 40.0f) {}
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
        final Vec3 v = MCH_Lib.Rot2Vec3(this.getRotYaw(), this.getRotPitch() - 10.0f);
        if (!levelOff) {
            this.motionY += v.yCoord * throttle / 8.0;
        }
        boolean canMove = true;
        if (!this.getAcInfo().canMoveOnGround) {
            final Block block = MCH_Lib.getBlockY((Entity)this, 3, -2, false);
            if (!W_Block.isEqual(block, W_Block.getWater()) && !W_Block.isEqual(block, W_Blocks.air)) {
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
        this.updateWheels();
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionY *= 0.95;
        this.motionX *= this.getAcInfo().motionFactor;
        this.motionZ *= this.getAcInfo().motionFactor;
        this.setRotation(this.getRotYaw(), this.getRotPitch());
        this.onUpdate_updateBlock();
        this.updateCollisionBox();
        if (this.getRiddenByEntity() != null && this.getRiddenByEntity().isDead) {
            this.unmountEntity();
            this.riddenByEntity = null;
        }
    }
    
    private void collisionEntity(final AxisAlignedBB bb) {
        if (bb == null) {
            return;
        }
        final double speed = Math.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
        if (speed <= 0.05) {
            return;
        }
        final Entity rider = this.getRiddenByEntity();
        float damage = (float)(speed * 15.0);
        final MCH_EntityAircraft rideAc = (this.ridingEntity instanceof MCH_EntityAircraft) ? this.ridingEntity : ((this.ridingEntity instanceof MCH_EntitySeat) ? ((MCH_EntitySeat)this.ridingEntity).getParent() : null);
        final List list = this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this, bb.expand(0.3, 0.3, 0.3), (IEntitySelector)new IEntitySelector() {
            public boolean isEntityApplicable(final Entity e) {
                if (e == rideAc || e instanceof EntityItem || e instanceof EntityXPOrb || e instanceof MCH_EntityBaseBullet || e instanceof MCH_EntityChain || e instanceof MCH_EntitySeat) {
                    return false;
                }
                if (e instanceof MCH_EntityTank) {
                    final MCH_EntityTank tank = (MCH_EntityTank)e;
                    if (tank.getTankInfo() != null && tank.getTankInfo().weightType == 2) {
                        final MCH_Config config = MCH_MOD.config;
                        return MCH_Config.Collision_EntityTankDamage.prmBool;
                    }
                }
                final MCH_Config config2 = MCH_MOD.config;
                return MCH_Config.Collision_EntityDamage.prmBool;
            }
        });
        for (int i = 0; i < list.size(); ++i) {
            final Entity e = list.get(i);
            if (this.shouldCollisionDamage(e)) {
                final double dx = e.posX - this.posX;
                final double dz = e.posZ - this.posZ;
                double dist = Math.sqrt(dx * dx + dz * dz);
                if (dist > 5.0) {
                    dist = 5.0;
                }
                damage += (float)(5.0 - dist);
                DamageSource ds;
                if (rider instanceof EntityLivingBase) {
                    ds = DamageSource.causeMobDamage((EntityLivingBase)rider);
                }
                else {
                    ds = DamageSource.generic;
                }
                MCH_Lib.applyEntityHurtResistantTimeConfig(e);
                e.attackEntityFrom(ds, damage);
                if (e instanceof MCH_EntityAircraft) {
                    final Entity entity = e;
                    entity.motionX += this.motionX * 0.05;
                    final Entity entity2 = e;
                    entity2.motionZ += this.motionZ * 0.05;
                }
                else if (e instanceof EntityArrow) {
                    e.setDead();
                }
                else {
                    final Entity entity3 = e;
                    entity3.motionX += this.motionX * 1.5;
                    final Entity entity4 = e;
                    entity4.motionZ += this.motionZ * 1.5;
                }
                if (this.getTankInfo().weightType != 2 && (e.width >= 1.0f || e.height >= 1.5)) {
                    if (e instanceof EntityLivingBase) {
                        ds = DamageSource.causeMobDamage((EntityLivingBase)e);
                    }
                    else {
                        ds = DamageSource.generic;
                    }
                    this.attackEntityFrom(ds, damage / 3.0f);
                }
                MCH_Lib.DbgLog(this.worldObj, "MCH_EntityTank.collisionEntity damage=%.1f %s", new Object[] { damage, e.toString() });
            }
        }
    }
    
    private boolean shouldCollisionDamage(final Entity e) {
        if (this.getSeatIdByEntity(e) >= 0) {
            return false;
        }
        if (this.noCollisionEntities.containsKey(e)) {
            return false;
        }
        if (e instanceof MCH_EntityHitBox && ((MCH_EntityHitBox)e).parent != null) {
            final MCH_EntityAircraft ac = ((MCH_EntityHitBox)e).parent;
            if (this.noCollisionEntities.containsKey(ac)) {
                return false;
            }
        }
        return (!(e.ridingEntity instanceof MCH_EntityAircraft) || !this.noCollisionEntities.containsKey(e.ridingEntity)) && (!(e.ridingEntity instanceof MCH_EntitySeat) || ((MCH_EntitySeat)e.ridingEntity).getParent() == null || !this.noCollisionEntities.containsKey(((MCH_EntitySeat)e.ridingEntity).getParent()));
    }
    
    public void updateCollisionBox() {
        if (this.getAcInfo() == null) {
            return;
        }
        this.WheelMng.updateBlock();
        for (final MCH_BoundingBox bb : this.extraBoundingBox) {
            if (this.rand.nextInt(3) == 0) {
                final MCH_Config config = MCH_MOD.config;
                if (MCH_Config.Collision_DestroyBlock.prmBool) {
                    final Vec3 v = this.getTransformedPosition(bb.offsetX, bb.offsetY, bb.offsetZ);
                    this.destoryBlockRange(v, bb.width, bb.height);
                }
                this.collisionEntity(bb.boundingBox);
            }
        }
        final MCH_Config config2 = MCH_MOD.config;
        if (MCH_Config.Collision_DestroyBlock.prmBool) {
            this.destoryBlockRange(this.getTransformedPosition(0.0, 0.0, 0.0), this.width * 1.5, this.height * 2.0f);
        }
        this.collisionEntity(this.getBoundingBox());
    }
    
    public void destoryBlockRange(final Vec3 v, final double w, final double h) {
        if (this.getAcInfo() == null) {
            return;
        }
        final MCH_Config config = MCH_MOD.config;
        final List<Block> destroyBlocks = (List<Block>)MCH_Config.getBreakableBlockListFromType(this.getTankInfo().weightType);
        final MCH_Config config2 = MCH_MOD.config;
        final List<Block> noDestroyBlocks = (List<Block>)MCH_Config.getNoBreakableBlockListFromType(this.getTankInfo().weightType);
        final MCH_Config config3 = MCH_MOD.config;
        final List<Material> destroyMaterials = (List<Material>)MCH_Config.getBreakableMaterialListFromType(this.getTankInfo().weightType);
        final int ws = (int)(w + 2.0) / 2;
        final int hs = (int)(h + 2.0) / 2;
        for (int x = -ws; x <= ws; ++x) {
            for (int z = -ws; z <= ws; ++z) {
                for (int y = -hs; y <= hs + 1; ++y) {
                    final int bx = (int)(v.xCoord + x - 0.5);
                    final int by = (int)(v.yCoord + y - 1.0);
                    final int bz = (int)(v.zCoord + z - 0.5);
                    Block block = (by >= 0 && by < 256) ? this.worldObj.getBlock(bx, by, bz) : Blocks.air;
                    Material mat = block.getMaterial();
                    if (!Block.isEqualTo(block, Blocks.air)) {
                        for (final Block c : noDestroyBlocks) {
                            if (Block.isEqualTo(block, c)) {
                                block = null;
                                break;
                            }
                        }
                        if (block == null) {
                            break;
                        }
                        for (final Block c : destroyBlocks) {
                            if (Block.isEqualTo(block, c)) {
                                this.destroyBlock(bx, by, bz);
                                mat = null;
                                break;
                            }
                        }
                        if (mat == null) {
                            break;
                        }
                        for (final Material m : destroyMaterials) {
                            if (block.getMaterial() == m) {
                                this.destroyBlock(bx, by, bz);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
    
    public void destroyBlock(final int bx, final int by, final int bz) {
        if (this.rand.nextInt(8) == 0) {
            W_WorldFunc.destroyBlock(this.worldObj, bx, by, bz, true);
        }
        else {
            this.worldObj.setBlockToAir(bx, by, bz);
        }
    }
    
    private void updateWheels() {
        this.WheelMng.move(this.motionX, this.motionY, this.motionZ);
    }
    
    public float getMaxSpeed() {
        return this.getTankInfo().speed + 0.0f;
    }
    
    public void setAngles(final Entity player, final boolean fixRot, final float fixYaw, final float fixPitch, float deltaX, float deltaY, float x, float y, float partialTicks) {
        if (partialTicks < 0.03f) {
            partialTicks = 0.4f;
        }
        if (partialTicks > 0.9f) {
            partialTicks = 0.6f;
        }
        this.lowPassPartialTicks.put(partialTicks);
        partialTicks = this.lowPassPartialTicks.getAvg();
        final float ac_pitch = this.getRotPitch();
        final float ac_yaw = this.getRotYaw();
        final float ac_roll = this.getRotRoll();
        if (this.isFreeLookMode()) {
            y = (x = 0.0f);
        }
        final float yaw = 0.0f;
        final float pitch = 0.0f;
        final float roll = 0.0f;
        final MCH_Math.FMatrix m_add = MCH_Math.newMatrix();
        MCH_Math.MatTurnZ(m_add, roll / 180.0f * 3.1415927f);
        MCH_Math.MatTurnX(m_add, pitch / 180.0f * 3.1415927f);
        MCH_Math.MatTurnY(m_add, yaw / 180.0f * 3.1415927f);
        MCH_Math.MatTurnZ(m_add, (float)(this.getRotRoll() / 180.0f * 3.141592653589793));
        MCH_Math.MatTurnX(m_add, (float)(this.getRotPitch() / 180.0f * 3.141592653589793));
        MCH_Math.MatTurnY(m_add, (float)(this.getRotYaw() / 180.0f * 3.141592653589793));
        final MCH_Math.FVector3D v = MCH_Math.MatrixToEuler(m_add);
        v.x = MCH_Lib.RNG(v.x, -90.0f, 90.0f);
        v.z = MCH_Lib.RNG(v.z, -90.0f, 90.0f);
        if (v.z > 180.0f) {
            final MCH_Math.FVector3D fVector3D = v;
            fVector3D.z -= 360.0f;
        }
        if (v.z < -180.0f) {
            final MCH_Math.FVector3D fVector3D2 = v;
            fVector3D2.z += 360.0f;
        }
        this.setRotYaw(v.y);
        this.setRotPitch(v.x);
        this.setRotRoll(v.z);
        this.onUpdateAngles(partialTicks);
        if (this.getAcInfo().limitRotation) {
            v.x = MCH_Lib.RNG(this.getRotPitch(), -90.0f, 90.0f);
            v.z = MCH_Lib.RNG(this.getRotRoll(), -90.0f, 90.0f);
            this.setRotPitch(v.x);
            this.setRotRoll(v.z);
        }
        final float RV = 180.0f;
        if (MathHelper.abs(this.getRotPitch()) > 90.0f) {
            MCH_Lib.DbgLog(true, "MCH_EntityAircraft.setAngles Error:Pitch=%.1f", new Object[] { this.getRotPitch() });
            this.setRotPitch(0.0f);
        }
        if (this.getRotRoll() > 180.0f) {
            this.setRotRoll(this.getRotRoll() - 360.0f);
        }
        if (this.getRotRoll() < -180.0f) {
            this.setRotRoll(this.getRotRoll() + 360.0f);
        }
        this.prevRotationRoll = this.getRotRoll();
        this.prevRotationPitch = this.getRotPitch();
        if (this.getRidingEntity() == null) {
            this.prevRotationYaw = this.getRotYaw();
        }
        float deltaLimit = this.getAcInfo().cameraRotationSpeed * partialTicks;
        final MCH_WeaponSet ws = this.getCurrentWeapon(player);
        deltaLimit *= ((ws != null && ws.getInfo() != null) ? ws.getInfo().cameraRotationSpeedPitch : 1.0f);
        if (deltaX > deltaLimit) {
            deltaX = deltaLimit;
        }
        if (deltaX < -deltaLimit) {
            deltaX = -deltaLimit;
        }
        if (deltaY > deltaLimit) {
            deltaY = deltaLimit;
        }
        if (deltaY < -deltaLimit) {
            deltaY = -deltaLimit;
        }
        if (this.isOverridePlayerYaw() || fixRot) {
            if (this.getRidingEntity() == null) {
                player.prevRotationYaw = this.getRotYaw() + fixYaw;
            }
            else {
                if (this.getRotYaw() - player.rotationYaw > 180.0f) {
                    player.prevRotationYaw += 360.0f;
                }
                if (this.getRotYaw() - player.rotationYaw < -180.0f) {
                    player.prevRotationYaw -= 360.0f;
                }
            }
            player.rotationYaw = this.getRotYaw() + fixYaw;
        }
        else {
            player.setAngles(deltaX, 0.0f);
        }
        if (this.isOverridePlayerPitch() || fixRot) {
            player.prevRotationPitch = this.getRotPitch() + fixPitch;
            player.rotationPitch = this.getRotPitch() + fixPitch;
        }
        else {
            player.setAngles(0.0f, deltaY);
        }
        final float playerYaw = MathHelper.wrapAngleTo180_float(this.getRotYaw() - player.rotationYaw);
        final float playerPitch = this.getRotPitch() * MathHelper.cos((float)(playerYaw * 3.141592653589793 / 180.0)) + -this.getRotRoll() * MathHelper.sin((float)(playerYaw * 3.141592653589793 / 180.0));
        if (MCH_MOD.proxy.isFirstPerson()) {
            player.rotationPitch = MCH_Lib.RNG(player.rotationPitch, playerPitch + this.getAcInfo().minRotationPitch, playerPitch + this.getAcInfo().maxRotationPitch);
            player.rotationPitch = MCH_Lib.RNG(player.rotationPitch, -90.0f, 90.0f);
        }
        player.prevRotationPitch = player.rotationPitch;
        if ((this.getRidingEntity() == null && ac_yaw != this.getRotYaw()) || ac_pitch != this.getRotPitch() || ac_roll != this.getRotRoll()) {
            this.aircraftRotChanged = true;
        }
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
        if (this.moveLeft || this.moveRight || this.throttleDown) {
            this.soundVolumeTarget += 0.1f;
            if (this.soundVolumeTarget > 0.75f) {
                this.soundVolumeTarget = 0.75f;
            }
        }
        else {
            this.soundVolumeTarget *= 0.8f;
        }
        if (target < this.soundVolumeTarget) {
            target = this.soundVolumeTarget;
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
        final float target1 = (float)(0.5 + this.getCurrentThrottle() * 0.5);
        final float target2 = (float)(0.5 + this.soundVolumeTarget * 0.5);
        return (target1 > target2) ? target1 : target2;
    }
    
    public String getDefaultSoundName() {
        return "prop";
    }
    
    public boolean hasBrake() {
        return true;
    }
    
    public void updateParts(final int stat) {
        super.updateParts(stat);
        if (this.isDestroyed()) {
            return;
        }
        final MCH_Parts[] arr$;
        final MCH_Parts[] parts = arr$ = new MCH_Parts[0];
        for (final MCH_Parts p : arr$) {
            if (p != null) {
                p.updateStatusClient(stat);
                p.update();
            }
        }
    }
    
    public float getUnfoldLandingGearThrottle() {
        return 0.7f;
    }
}
