//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.weapon;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import mcheli.*;
import mcheli.wrapper.*;
import net.minecraft.util.*;

public class MCH_WeaponTorpedo extends MCH_WeaponBase
{
    public MCH_WeaponTorpedo(final World w, final Vec3 v, final float yaw, final float pitch, final String nm, final MCH_WeaponInfo wi) {
        super(w, v, yaw, pitch, nm, wi);
        this.acceleration = 0.5f;
        this.explosionPower = 8;
        this.power = 35;
        this.interval = -100;
        if (w.isRemote) {
            this.interval -= 10;
        }
    }
    
    public boolean shot(final MCH_WeaponParam prm) {
        if (this.getInfo() == null) {
            return false;
        }
        if (this.getInfo().isGuidedTorpedo) {
            return this.shotGuided(prm);
        }
        return this.shotNoGuided(prm);
    }
    
    protected boolean shotNoGuided(final MCH_WeaponParam prm) {
        if (this.worldObj.isRemote) {
            return true;
        }
        final float yaw = prm.rotYaw;
        final float pitch = prm.rotPitch;
        double mx = -MathHelper.sin(yaw / 180.0f * 3.1415927f) * MathHelper.cos(pitch / 180.0f * 3.1415927f);
        double mz = MathHelper.cos(yaw / 180.0f * 3.1415927f) * MathHelper.cos(pitch / 180.0f * 3.1415927f);
        double my = -MathHelper.sin(pitch / 180.0f * 3.1415927f);
        mx = mx * this.getInfo().acceleration + prm.entity.motionX;
        my = my * this.getInfo().acceleration + prm.entity.motionY;
        mz = mz * this.getInfo().acceleration + prm.entity.motionZ;
        this.acceleration = MathHelper.sqrt_double(mx * mx + my * my + mz * mz);
        final MCH_EntityTorpedo e = new MCH_EntityTorpedo(this.worldObj, prm.posX, prm.posY, prm.posZ, mx, my, mz, yaw, 0.0f, (double)this.acceleration);
        e.setName(this.name);
        e.setParameterFromWeapon((MCH_WeaponBase)this, prm.entity, prm.user);
        e.motionX = mx;
        e.motionY = my;
        e.motionZ = mz;
        e.accelerationInWater = ((this.getInfo() != null) ? this.getInfo().accelerationInWater : 1.0);
        this.worldObj.spawnEntityInWorld((Entity)e);
        this.playSound(prm.entity);
        return true;
    }
    
    protected boolean shotGuided(final MCH_WeaponParam prm) {
        final float yaw = prm.user.rotationYaw;
        final float pitch = prm.user.rotationPitch;
        final Vec3 v = MCH_Lib.RotVec3(0.0, 0.0, 1.0, -yaw, -pitch, -prm.rotRoll);
        double tX = v.xCoord;
        double tZ = v.zCoord;
        double tY = v.yCoord;
        final double dist = MathHelper.sqrt_double(tX * tX + tY * tY + tZ * tZ);
        if (this.worldObj.isRemote) {
            tX = tX * 100.0 / dist;
            tY = tY * 100.0 / dist;
            tZ = tZ * 100.0 / dist;
        }
        else {
            tX = tX * 150.0 / dist;
            tY = tY * 150.0 / dist;
            tZ = tZ * 150.0 / dist;
        }
        final Vec3 src = W_WorldFunc.getWorldVec3(this.worldObj, prm.user.posX, prm.user.posY, prm.user.posZ);
        final Vec3 dst = W_WorldFunc.getWorldVec3(this.worldObj, prm.user.posX + tX, prm.user.posY + tY, prm.user.posZ + tZ);
        final MovingObjectPosition m = W_WorldFunc.clip(this.worldObj, src, dst);
        if (m != null && W_MovingObjectPosition.isHitTypeTile(m) && MCH_Lib.isBlockInWater(this.worldObj, m.blockX, m.blockY, m.blockZ)) {
            if (!this.worldObj.isRemote) {
                double mx = -MathHelper.sin(yaw / 180.0f * 3.1415927f) * MathHelper.cos(pitch / 180.0f * 3.1415927f);
                double mz = MathHelper.cos(yaw / 180.0f * 3.1415927f) * MathHelper.cos(pitch / 180.0f * 3.1415927f);
                double my = -MathHelper.sin(pitch / 180.0f * 3.1415927f);
                mx = mx * this.getInfo().acceleration + prm.entity.motionX;
                my = my * this.getInfo().acceleration + prm.entity.motionY;
                mz = mz * this.getInfo().acceleration + prm.entity.motionZ;
                this.acceleration = MathHelper.sqrt_double(mx * mx + my * my + mz * mz);
                final MCH_EntityTorpedo e = new MCH_EntityTorpedo(this.worldObj, prm.posX, prm.posY, prm.posZ, prm.entity.motionX, prm.entity.motionY, prm.entity.motionZ, yaw, 0.0f, (double)this.acceleration);
                e.setName(this.name);
                e.setParameterFromWeapon((MCH_WeaponBase)this, prm.entity, prm.user);
                e.targetPosX = m.hitVec.xCoord;
                e.targetPosY = m.hitVec.yCoord;
                e.targetPosZ = m.hitVec.zCoord;
                e.motionX = mx;
                e.motionY = my;
                e.motionZ = mz;
                e.accelerationInWater = ((this.getInfo() != null) ? this.getInfo().accelerationInWater : 1.0);
                this.worldObj.spawnEntityInWorld((Entity)e);
                this.playSound(prm.entity);
            }
            return true;
        }
        return false;
    }
}
