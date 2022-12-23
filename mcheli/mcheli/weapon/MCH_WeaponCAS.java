//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.weapon;

import net.minecraft.entity.*;
import net.minecraft.world.*;
import mcheli.wrapper.*;
import mcheli.*;
import net.minecraft.util.*;

public class MCH_WeaponCAS extends MCH_WeaponBase
{
    private double targetPosX;
    private double targetPosY;
    private double targetPosZ;
    public int direction;
    private int startTick;
    private int cntAtk;
    private Entity shooter;
    public Entity user;
    
    public MCH_WeaponCAS(final World w, final Vec3 v, final float yaw, final float pitch, final String nm, final MCH_WeaponInfo wi) {
        super(w, v, yaw, pitch, nm, wi);
        this.acceleration = 4.0f;
        this.explosionPower = 2;
        this.power = 32;
        this.interval = -300;
        if (w.isRemote) {
            this.interval -= 10;
        }
        this.targetPosX = 0.0;
        this.targetPosY = 0.0;
        this.targetPosZ = 0.0;
        this.direction = 0;
        this.startTick = 0;
        this.cntAtk = 3;
        this.shooter = null;
        this.user = null;
    }
    
    public void update(final int countWait) {
        super.update(countWait);
        if (!this.worldObj.isRemote && this.cntAtk < 3 && countWait != 0 && this.tick == this.startTick) {
            double x = 0.0;
            double z = 0.0;
            if (this.cntAtk >= 1) {
                final double sign = (this.cntAtk == 1) ? 1.0 : -1.0;
                if (this.direction == 0 || this.direction == 2) {
                    x = MCH_WeaponCAS.rand.nextDouble() * 10.0 * sign;
                    z = (MCH_WeaponCAS.rand.nextDouble() - 0.5) * 10.0;
                }
                if (this.direction == 1 || this.direction == 3) {
                    z = MCH_WeaponCAS.rand.nextDouble() * 10.0 * sign;
                    x = (MCH_WeaponCAS.rand.nextDouble() - 0.5) * 10.0;
                }
            }
            this.spawnA10(this.targetPosX + x, this.targetPosY + 20.0, this.targetPosZ + z);
            this.startTick = this.tick + 45;
            ++this.cntAtk;
        }
    }
    
    public void modifyParameters() {
        if (this.interval > -250) {
            this.interval = -250;
        }
    }
    
    public void setTargetPosition(final double x, final double y, final double z) {
        this.targetPosX = x;
        this.targetPosY = y;
        this.targetPosZ = z;
    }
    
    public void spawnA10(double x, final double y, double z) {
        double mX = 0.0;
        final double mY = 0.0;
        double mZ = 0.0;
        final int SPEED = 3;
        if (this.direction == 0) {
            mZ += 3.0;
            z -= 90.0;
        }
        if (this.direction == 1) {
            mX -= 3.0;
            x += 90.0;
        }
        if (this.direction == 2) {
            mZ -= 3.0;
            z += 90.0;
        }
        if (this.direction == 3) {
            mX += 3.0;
            x -= 90.0;
        }
        final MCH_EntityA10 a10 = new MCH_EntityA10(this.worldObj, x, y, z);
        a10.setWeaponName(this.name);
        final MCH_EntityA10 mch_EntityA10 = a10;
        final MCH_EntityA10 mch_EntityA11 = a10;
        final float n = (float)(90 * this.direction);
        mch_EntityA11.rotationYaw = n;
        mch_EntityA10.prevRotationYaw = n;
        a10.motionX = mX;
        a10.motionY = mY;
        a10.motionZ = mZ;
        a10.direction = this.direction;
        a10.shootingEntity = this.user;
        a10.shootingAircraft = this.shooter;
        a10.explosionPower = this.explosionPower;
        a10.power = this.power;
        a10.acceleration = this.acceleration;
        this.worldObj.spawnEntityInWorld((Entity)a10);
        W_WorldFunc.MOD_playSoundEffect(this.worldObj, x, y, z, "a-10_snd", 150.0f, 1.0f);
    }
    
    public boolean shot(final MCH_WeaponParam prm) {
        final float yaw = prm.user.rotationYaw;
        final float pitch = prm.user.rotationPitch;
        double tX = -MathHelper.sin(yaw / 180.0f * 3.1415927f) * MathHelper.cos(pitch / 180.0f * 3.1415927f);
        double tZ = MathHelper.cos(yaw / 180.0f * 3.1415927f) * MathHelper.cos(pitch / 180.0f * 3.1415927f);
        double tY = -MathHelper.sin(pitch / 180.0f * 3.1415927f);
        final double dist = MathHelper.sqrt_double(tX * tX + tY * tY + tZ * tZ);
        if (this.worldObj.isRemote) {
            tX = tX * 80.0 / dist;
            tY = tY * 80.0 / dist;
            tZ = tZ * 80.0 / dist;
        }
        else {
            tX = tX * 150.0 / dist;
            tY = tY * 150.0 / dist;
            tZ = tZ * 150.0 / dist;
        }
        final Vec3 src = W_WorldFunc.getWorldVec3(this.worldObj, prm.entity.posX, prm.entity.posY + 2.0, prm.entity.posZ);
        final Vec3 dst = W_WorldFunc.getWorldVec3(this.worldObj, prm.entity.posX + tX, prm.entity.posY + tY + 2.0, prm.entity.posZ + tZ);
        final MovingObjectPosition m = W_WorldFunc.clip(this.worldObj, src, dst);
        if (m != null && W_MovingObjectPosition.isHitTypeTile(m)) {
            this.targetPosX = m.hitVec.xCoord;
            this.targetPosY = m.hitVec.yCoord;
            this.targetPosZ = m.hitVec.zCoord;
            this.direction = (int)MCH_Lib.getRotate360((double)(yaw + 45.0f)) / 90;
            this.direction += (MCH_WeaponCAS.rand.nextBoolean() ? -1 : 1);
            this.direction %= 4;
            if (this.direction < 0) {
                this.direction += 4;
            }
            this.user = prm.user;
            this.shooter = prm.entity;
            if (prm.entity != null) {
                this.playSoundClient(prm.entity, 1.0f, 1.0f);
            }
            this.startTick = 50;
            this.cntAtk = 0;
            return true;
        }
        return false;
    }
    
    public boolean shot(final Entity user, final double px, final double py, final double pz, final int option1, final int option2) {
        final float yaw = user.rotationYaw;
        final float pitch = user.rotationPitch;
        double tX = -MathHelper.sin(yaw / 180.0f * 3.1415927f) * MathHelper.cos(pitch / 180.0f * 3.1415927f);
        double tZ = MathHelper.cos(yaw / 180.0f * 3.1415927f) * MathHelper.cos(pitch / 180.0f * 3.1415927f);
        double tY = -MathHelper.sin(pitch / 180.0f * 3.1415927f);
        final double dist = MathHelper.sqrt_double(tX * tX + tY * tY + tZ * tZ);
        if (this.worldObj.isRemote) {
            tX = tX * 80.0 / dist;
            tY = tY * 80.0 / dist;
            tZ = tZ * 80.0 / dist;
        }
        else {
            tX = tX * 120.0 / dist;
            tY = tY * 120.0 / dist;
            tZ = tZ * 120.0 / dist;
        }
        final Vec3 src = W_WorldFunc.getWorldVec3(this.worldObj, px, py, pz);
        final Vec3 dst = W_WorldFunc.getWorldVec3(this.worldObj, px + tX, py + tY, pz + tZ);
        final MovingObjectPosition m = W_WorldFunc.clip(this.worldObj, src, dst);
        if (W_MovingObjectPosition.isHitTypeTile(m)) {
            if (this.worldObj.isRemote) {
                final double dx = m.hitVec.xCoord - px;
                final double dy = m.hitVec.yCoord - py;
                final double dz = m.hitVec.zCoord - pz;
                if (Math.sqrt(dx * dx + dz * dz) < 20.0) {
                    return false;
                }
            }
            this.targetPosX = m.hitVec.xCoord;
            this.targetPosY = m.hitVec.yCoord;
            this.targetPosZ = m.hitVec.zCoord;
            this.direction = (int)MCH_Lib.getRotate360((double)(yaw + 45.0f)) / 90;
            this.direction += (MCH_WeaponCAS.rand.nextBoolean() ? -1 : 1);
            this.direction %= 4;
            if (this.direction < 0) {
                this.direction += 4;
            }
            this.user = user;
            this.shooter = null;
            this.tick = 0;
            this.startTick = 50;
            this.cntAtk = 0;
            return true;
        }
        return false;
    }
}
