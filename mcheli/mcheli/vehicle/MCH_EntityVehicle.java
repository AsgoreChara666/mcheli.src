//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.vehicle;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import mcheli.*;
import net.minecraft.nbt.*;
import net.minecraft.item.*;
import cpw.mods.fml.relauncher.*;
import mcheli.weapon.*;
import mcheli.aircraft.*;
import mcheli.wrapper.*;
import net.minecraft.util.*;

public class MCH_EntityVehicle extends MCH_EntityAircraft
{
    private MCH_VehicleInfo vehicleInfo;
    public boolean isUsedPlayer;
    public float lastRiderYaw;
    public float lastRiderPitch;
    public double fixPosX;
    public double fixPosY;
    public double fixPosZ;
    
    public MCH_EntityVehicle(final World world) {
        super(world);
        this.vehicleInfo = null;
        this.currentSpeed = 0.07;
        this.preventEntitySpawning = true;
        this.setSize(2.0f, 0.7f);
        this.yOffset = this.height / 2.0f;
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.isUsedPlayer = false;
        this.lastRiderYaw = 0.0f;
        this.lastRiderPitch = 0.0f;
        this.weapons = this.createWeapon(0);
    }
    
    public String getKindName() {
        return "vehicles";
    }
    
    public String getEntityType() {
        return "Vehicle";
    }
    
    public MCH_VehicleInfo getVehicleInfo() {
        return this.vehicleInfo;
    }
    
    public void changeType(final String type) {
        MCH_Lib.DbgLog(this.worldObj, "MCH_EntityVehicle.changeType " + type + " : " + this.toString(), new Object[0]);
        if (!type.isEmpty()) {
            this.vehicleInfo = MCH_VehicleInfoManager.get(type);
        }
        if (this.vehicleInfo == null) {
            MCH_Lib.Log((Entity)this, "##### MCH_EntityVehicle changeVehicleType() Vehicle info null %d, %s, %s", new Object[] { W_Entity.getEntityId((Entity)this), type, this.getEntityName() });
            this.setDead();
        }
        else {
            this.setAcInfo((MCH_AircraftInfo)this.vehicleInfo);
            this.newSeats(this.getAcInfo().getNumSeatAndRack());
            this.weapons = this.createWeapon(1 + this.getSeatNum());
            this.initPartRotation(this.rotationYaw, this.rotationPitch);
        }
    }
    
    public boolean canMountWithNearEmptyMinecart() {
        final MCH_Config config = MCH_MOD.config;
        return MCH_Config.MountMinecartVehicle.prmBool;
    }
    
    protected void entityInit() {
        super.entityInit();
    }
    
    protected void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
    }
    
    protected void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        if (this.vehicleInfo == null) {
            this.vehicleInfo = MCH_VehicleInfoManager.get(this.getTypeName());
            if (this.vehicleInfo == null) {
                MCH_Lib.Log((Entity)this, "##### MCH_EntityVehicle readEntityFromNBT() Vehicle info null %d, %s", new Object[] { W_Entity.getEntityId((Entity)this), this.getEntityName() });
                this.setDead();
            }
            else {
                this.setAcInfo((MCH_AircraftInfo)this.vehicleInfo);
            }
        }
    }
    
    public Item getItem() {
        return (Item)((this.getVehicleInfo() != null) ? this.getVehicleInfo().item : null);
    }
    
    public void setDead() {
        super.setDead();
    }
    
    public float getSoundVolume() {
        return (float)this.getCurrentThrottle() * 2.0f;
    }
    
    public float getSoundPitch() {
        return (float)(this.getCurrentThrottle() * 0.5);
    }
    
    public String getDefaultSoundName() {
        return "";
    }
    
    @SideOnly(Side.CLIENT)
    public void zoomCamera() {
        if (this.canZoom()) {
            float z = this.camera.getCameraZoom();
            ++z;
            this.camera.setCameraZoom((z <= this.getZoomMax() + 0.01) ? z : 1.0f);
        }
    }
    
    public void _updateCameraRotate(final float yaw, float pitch) {
        this.camera.prevRotationYaw = this.camera.rotationYaw;
        this.camera.prevRotationPitch = this.camera.rotationPitch;
        if (pitch > 89.0f) {
            pitch = 89.0f;
        }
        if (pitch < -89.0f) {
            pitch = -89.0f;
        }
        this.camera.rotationYaw = yaw;
        this.camera.rotationPitch = pitch;
    }
    
    public boolean isCameraView(final Entity entity) {
        return true;
    }
    
    public boolean useCurrentWeapon(final MCH_WeaponParam prm) {
        if (prm.user != null) {
            final MCH_WeaponSet currentWs = this.getCurrentWeapon(prm.user);
            if (currentWs != null) {
                final MCH_AircraftInfo.Weapon w = this.getAcInfo().getWeaponByName(currentWs.getInfo().name);
                if (w != null && w.maxYaw != 0.0f && w.minYaw != 0.0f) {
                    return super.useCurrentWeapon(prm);
                }
            }
        }
        final float breforeUseWeaponPitch = this.rotationPitch;
        final float breforeUseWeaponYaw = this.rotationYaw;
        this.rotationPitch = prm.user.rotationPitch;
        this.rotationYaw = prm.user.rotationYaw;
        final boolean result = super.useCurrentWeapon(prm);
        this.rotationPitch = breforeUseWeaponPitch;
        this.rotationYaw = breforeUseWeaponYaw;
        return result;
    }
    
    protected void mountWithNearEmptyMinecart() {
        final MCH_Config config = MCH_MOD.config;
        if (!MCH_Config.FixVehicleAtPlacedPoint.prmBool) {
            super.mountWithNearEmptyMinecart();
        }
    }
    
    public void onUpdateAircraft() {
        if (this.vehicleInfo == null) {
            this.changeType(this.getTypeName());
            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;
            return;
        }
        Label_0180: {
            if (this.ticksExisted >= 200) {
                final MCH_Config config = MCH_MOD.config;
                if (MCH_Config.FixVehicleAtPlacedPoint.prmBool) {
                    this.mountEntity((Entity)null);
                    this.motionX = 0.0;
                    this.motionY = 0.0;
                    this.motionZ = 0.0;
                    if (this.worldObj.isRemote && this.ticksExisted % 4 == 0) {
                        this.fixPosY = this.posY;
                    }
                    this.setPosition((this.posX + this.fixPosX) / 2.0, (this.posY + this.fixPosY) / 2.0, (this.posZ + this.fixPosZ) / 2.0);
                    break Label_0180;
                }
            }
            this.fixPosX = this.posX;
            this.fixPosY = this.posY;
            this.fixPosZ = this.posZ;
        }
        if (!this.isRequestedSyncStatus) {
            this.isRequestedSyncStatus = true;
            if (this.worldObj.isRemote) {
                MCH_PacketStatusRequest.requestStatus((MCH_EntityAircraft)this);
            }
        }
        if (this.lastRiddenByEntity == null && this.getRiddenByEntity() != null) {
            this.getRiddenByEntity().rotationPitch = 0.0f;
            this.getRiddenByEntity().prevRotationPitch = 0.0f;
            this.initCurrentWeapon(this.getRiddenByEntity());
        }
        this.updateWeapons();
        this.onUpdate_Seats();
        this.onUpdate_Control();
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.isInWater()) {
            this.rotationPitch *= 0.9f;
        }
        if (this.worldObj.isRemote) {
            this.onUpdate_Client();
        }
        else {
            this.onUpdate_Server();
        }
    }
    
    protected void onUpdate_Control() {
        final double max_y = 1.0;
        if (this.riddenByEntity != null && !this.riddenByEntity.isDead) {
            if (this.getVehicleInfo().isEnableMove || this.getVehicleInfo().isEnableRot) {
                this.onUpdate_ControlOnGround();
            }
        }
        else if (this.getCurrentThrottle() > 0.0) {
            this.addCurrentThrottle(-0.00125);
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
    
    protected void onUpdate_ControlOnGround() {
        if (!this.worldObj.isRemote) {
            boolean move = false;
            float yaw = this.rotationYaw;
            double x = 0.0;
            double z = 0.0;
            if (this.getVehicleInfo().isEnableMove) {
                if (this.throttleUp) {
                    yaw = this.rotationYaw;
                    x += Math.sin(yaw * 3.141592653589793 / 180.0);
                    z += Math.cos(yaw * 3.141592653589793 / 180.0);
                    move = true;
                }
                if (this.throttleDown) {
                    yaw = this.rotationYaw - 180.0f;
                    x += Math.sin(yaw * 3.141592653589793 / 180.0);
                    z += Math.cos(yaw * 3.141592653589793 / 180.0);
                    move = true;
                }
            }
            if (this.getVehicleInfo().isEnableMove) {
                if (this.moveLeft && !this.moveRight) {
                    this.rotationYaw -= 0.5;
                }
                if (this.moveRight && !this.moveLeft) {
                    this.rotationYaw += 0.5;
                }
            }
            if (move) {
                final double d = Math.sqrt(x * x + z * z);
                this.motionX -= x / d * 0.029999999329447746;
                this.motionZ += z / d * 0.029999999329447746;
            }
        }
    }
    
    protected void onUpdate_Particle() {
        double particlePosY = this.posY;
        boolean b;
        int y;
        int x;
        int z;
        int block;
        int i;
        for (b = false, y = 0; y < 5 && !b; ++y) {
            for (x = -1; x <= 1; ++x) {
                for (z = -1; z <= 1; ++z) {
                    block = W_WorldFunc.getBlockId(this.worldObj, (int)(this.posX + 0.5) + x, (int)(this.posY + 0.5) - y, (int)(this.posZ + 0.5) + z);
                    if (block != 0 && !b) {
                        particlePosY = (int)(this.posY + 1.0) - y;
                        b = true;
                    }
                }
            }
            for (x = -3; b && x <= 3; ++x) {
                for (z = -3; z <= 3; ++z) {
                    if (W_WorldFunc.isBlockWater(this.worldObj, (int)(this.posX + 0.5) + x, (int)(this.posY + 0.5) - y, (int)(this.posZ + 0.5) + z)) {
                        for (i = 0; i < 7.0 * this.getCurrentThrottle(); ++i) {
                            this.worldObj.spawnParticle("splash", this.posX + 0.5 + x + (this.rand.nextDouble() - 0.5) * 2.0, particlePosY + this.rand.nextDouble(), this.posZ + 0.5 + z + (this.rand.nextDouble() - 0.5) * 2.0, x + (this.rand.nextDouble() - 0.5) * 2.0, -0.3, z + (this.rand.nextDouble() - 0.5) * 2.0);
                        }
                    }
                }
            }
        }
        final double pn = (5 - y + 1) / 5.0;
        if (b) {
            for (int k = 0; k < (int)(this.getCurrentThrottle() * 6.0 * pn); ++k) {
                final float f3 = 0.25f;
                this.worldObj.spawnParticle("explode", this.posX + (this.rand.nextDouble() - 0.5), particlePosY + (this.rand.nextDouble() - 0.5), this.posZ + (this.rand.nextDouble() - 0.5), (this.rand.nextDouble() - 0.5) * 2.0, -0.4, (this.rand.nextDouble() - 0.5) * 2.0);
            }
        }
    }
    
    protected void onUpdate_Client() {
        this.updateCameraViewers();
        if (this.riddenByEntity != null && W_Lib.isClientPlayer(this.getRiddenByEntity())) {
            this.getRiddenByEntity().rotationPitch = this.getRiddenByEntity().prevRotationPitch;
        }
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
            if (this.onGround) {
                this.motionX *= 0.95;
                this.motionZ *= 0.95;
            }
            if (this.isInWater()) {
                this.motionX *= 0.99;
                this.motionZ *= 0.99;
            }
        }
        if (this.riddenByEntity != null) {}
        this.updateCamera(this.posX, this.posY, this.posZ);
    }
    
    private void onUpdate_Server() {
        final double prevMotion = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.updateCameraViewers();
        double dp = 0.0;
        if (this.canFloatWater()) {
            dp = this.getWaterDepth();
        }
        if (dp == 0.0) {
            this.motionY += (this.isInWater() ? this.getAcInfo().gravityInWater : this.getAcInfo().gravity);
        }
        else if (dp < 1.0) {
            this.motionY -= 1.0E-4;
            this.motionY += 0.007 * this.getCurrentThrottle();
        }
        else {
            if (this.motionY < 0.0) {
                this.motionY /= 2.0;
            }
            this.motionY += 0.007;
        }
        double motion = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        final float speedLimit = this.getAcInfo().speed;
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
        if (this.onGround) {
            this.motionX *= 0.5;
            this.motionZ *= 0.5;
        }
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionY *= 0.95;
        this.motionX *= 0.99;
        this.motionZ *= 0.99;
        this.onUpdate_updateBlock();
        if (this.riddenByEntity != null && this.riddenByEntity.isDead) {
            this.unmountEntity();
            this.riddenByEntity = null;
        }
    }
    
    public void onUpdateAngles(final float partialTicks) {
    }
    
    public void _updateRiderPosition() {
        final float yaw = this.rotationYaw;
        if (this.riddenByEntity != null) {
            this.rotationYaw = this.riddenByEntity.rotationYaw;
        }
        super.updateRiderPosition();
        this.rotationYaw = yaw;
    }
    
    public boolean canSwitchFreeLook() {
        return false;
    }
}
