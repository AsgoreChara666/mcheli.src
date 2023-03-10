//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.weapon;

import java.util.*;
import net.minecraft.world.*;
import mcheli.aircraft.*;
import net.minecraft.entity.*;
import mcheli.*;
import mcheli.wrapper.*;
import net.minecraft.util.*;

public abstract class MCH_WeaponBase
{
    protected static final Random rand;
    public final World worldObj;
    public final Vec3 position;
    public final float fixRotationYaw;
    public final float fixRotationPitch;
    public final String name;
    public final MCH_WeaponInfo weaponInfo;
    public String displayName;
    public int power;
    public float acceleration;
    public int explosionPower;
    public int explosionPowerInWater;
    public int interval;
    public int delayedInterval;
    public int numMode;
    public int lockTime;
    public int piercing;
    public int heatCount;
    public MCH_Cartridge cartridge;
    public boolean onTurret;
    public MCH_EntityAircraft aircraft;
    public int tick;
    public int optionParameter1;
    public int optionParameter2;
    private int currentMode;
    public boolean canPlaySound;
    
    public MCH_WeaponBase(final World w, final Vec3 v, final float yaw, final float pitch, final String nm, final MCH_WeaponInfo wi) {
        this.worldObj = w;
        this.position = v;
        this.fixRotationYaw = yaw;
        this.fixRotationPitch = pitch;
        this.name = nm;
        this.weaponInfo = wi;
        this.displayName = ((wi != null) ? wi.displayName : "");
        this.power = 0;
        this.acceleration = 0.0f;
        this.explosionPower = 0;
        this.explosionPowerInWater = 0;
        this.interval = 1;
        this.numMode = 0;
        this.lockTime = 0;
        this.heatCount = 0;
        this.cartridge = null;
        this.tick = 0;
        this.optionParameter1 = 0;
        this.setCurrentMode(this.optionParameter2 = 0);
        this.canPlaySound = true;
    }
    
    public MCH_WeaponInfo getInfo() {
        return this.weaponInfo;
    }
    
    public String getName() {
        return this.displayName;
    }
    
    public abstract boolean shot(final MCH_WeaponParam p0);
    
    public void setLockChecker(final MCH_IEntityLockChecker checker) {
    }
    
    public void setLockCountMax(final int n) {
    }
    
    public int getLockCount() {
        return 0;
    }
    
    public int getLockCountMax() {
        return 0;
    }
    
    public final int getNumAmmoMax() {
        return this.getInfo().round;
    }
    
    public int getCurrentMode() {
        return (this.getInfo() != null && this.getInfo().fixMode > 0) ? this.getInfo().fixMode : this.currentMode;
    }
    
    public void setCurrentMode(final int currentMode) {
        this.currentMode = currentMode;
    }
    
    public final int getAllAmmoNum() {
        return this.getInfo().maxAmmo;
    }
    
    public final int getReloadCount() {
        return this.getInfo().reloadTime;
    }
    
    public final MCH_SightType getSightType() {
        return this.getInfo().sight;
    }
    
    public MCH_WeaponGuidanceSystem getGuidanceSystem() {
        return null;
    }
    
    public void update(final int countWait) {
        if (countWait != 0) {
            ++this.tick;
        }
    }
    
    public boolean isCooldownCountReloadTime() {
        return false;
    }
    
    public void modifyCommonParameters() {
        this.modifyParameters();
    }
    
    public void modifyParameters() {
    }
    
    public boolean switchMode() {
        if (this.getInfo() != null && this.getInfo().fixMode > 0) {
            return false;
        }
        final int beforeMode = this.getCurrentMode();
        if (this.numMode > 0) {
            this.setCurrentMode((this.getCurrentMode() + 1) % this.numMode);
        }
        else {
            this.setCurrentMode(0);
        }
        if (beforeMode != this.getCurrentMode()) {
            this.onSwitchMode();
        }
        return beforeMode != this.getCurrentMode();
    }
    
    public void onSwitchMode() {
    }
    
    public boolean use(final MCH_WeaponParam prm) {
        final Vec3 v = this.getShotPos(prm.entity);
        prm.posX += v.xCoord;
        prm.posY += v.yCoord;
        prm.posZ += v.zCoord;
        if (this.shot(prm)) {
            this.tick = 0;
            return true;
        }
        return false;
    }
    
    public Vec3 getShotPos(final Entity entity) {
        if (entity instanceof MCH_EntityAircraft && this.onTurret) {
            return ((MCH_EntityAircraft)entity).calcOnTurretPos(this.position);
        }
        final Vec3 v = Vec3.createVectorHelper(this.position.xCoord, this.position.yCoord, this.position.zCoord);
        final float roll = (entity instanceof MCH_EntityAircraft) ? ((MCH_EntityAircraft)entity).getRotRoll() : 0.0f;
        return MCH_Lib.RotVec3(v, -entity.rotationYaw, -entity.rotationPitch, -roll);
    }
    
    public void playSound(final Entity e) {
        this.playSound(e, this.getInfo().soundFileName);
    }
    
    public void playSound(final Entity e, final String snd) {
        if (!e.worldObj.isRemote && this.canPlaySound && this.getInfo() != null) {
            final float prnd = this.getInfo().soundPitchRandom;
            W_WorldFunc.MOD_playSoundEffect(this.worldObj, e.posX, e.posY, e.posZ, snd, this.getInfo().soundVolume, this.getInfo().soundPitch * (1.0f - prnd) + MCH_WeaponBase.rand.nextFloat() * prnd);
        }
    }
    
    public void playSoundClient(final Entity e, final float volume, final float pitch) {
        if (e.worldObj.isRemote && this.getInfo() != null) {
            W_McClient.MOD_playSoundFX(this.getInfo().soundFileName, volume, pitch);
        }
    }
    
    public double getLandInDistance(final MCH_WeaponParam prm) {
        if (this.weaponInfo == null) {
            return -1.0;
        }
        if (this.weaponInfo.gravity >= 0.0f) {
            return -1.0;
        }
        final Vec3 v = MCH_Lib.RotVec3(0.0, 0.0, 1.0, -prm.rotYaw, -prm.rotPitch, -prm.rotRoll);
        final double s = Math.sqrt(v.xCoord * v.xCoord + v.yCoord * v.yCoord + v.zCoord * v.zCoord);
        final double acc = (this.acceleration < 4.0f) ? this.acceleration : 4.0;
        final double accFac = this.acceleration / acc;
        double my = v.yCoord * this.acceleration / s;
        if (my <= 0.0) {
            return -1.0;
        }
        double mx = v.xCoord * this.acceleration / s;
        double mz = v.zCoord * this.acceleration / s;
        final double ls = my / this.weaponInfo.gravity;
        double gravity = this.weaponInfo.gravity * accFac;
        if (ls < -12.0) {
            final double f = ls / -12.0;
            mx *= f;
            my *= f;
            mz *= f;
            gravity *= f * f * 0.95;
        }
        double spx = prm.posX;
        double spy = prm.posY + 3.0;
        double spz = prm.posZ;
        final Vec3 vs = Vec3.createVectorHelper(0.0, 0.0, 0.0);
        final Vec3 ve = Vec3.createVectorHelper(0.0, 0.0, 0.0);
        for (int i = 0; i < 50; ++i) {
            vs.xCoord = spx;
            vs.yCoord = spy;
            vs.zCoord = spz;
            ve.xCoord = spx + mx;
            ve.yCoord = spy + my;
            ve.zCoord = spz + mz;
            final MovingObjectPosition mop = this.worldObj.rayTraceBlocks(vs, ve);
            if (mop != null && mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                final double dx = mop.blockX - prm.posX;
                final double dz = mop.blockZ - prm.posZ;
                return Math.sqrt(dx * dx + dz * dz);
            }
            my += gravity;
            spx += mx;
            spy += my;
            spz += mz;
            if (spy < prm.posY) {
                final double dx = spx - prm.posX;
                final double dz = spz - prm.posZ;
                return Math.sqrt(dx * dx + dz * dz);
            }
        }
        return -1.0;
    }
    
    static {
        rand = new Random();
    }
}
