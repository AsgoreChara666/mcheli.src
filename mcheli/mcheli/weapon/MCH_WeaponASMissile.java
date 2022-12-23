//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.weapon;

import net.minecraft.world.*;
import mcheli.wrapper.*;
import mcheli.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class MCH_WeaponASMissile extends MCH_WeaponBase
{
    public MCH_WeaponASMissile(final World w, final Vec3 v, final float yaw, final float pitch, final String nm, final MCH_WeaponInfo wi) {
        super(w, v, yaw, pitch, nm, wi);
        this.acceleration = 3.0f;
        this.explosionPower = 9;
        this.power = 40;
        this.interval = -350;
        if (w.isRemote) {
            this.interval -= 10;
        }
    }
    
    @Override
    public boolean isCooldownCountReloadTime() {
        return true;
    }
    
    @Override
    public void update(final int countWait) {
        super.update(countWait);
    }
    
    @Override
    public boolean shot(final MCH_WeaponParam prm) {
        final float yaw = prm.user.rotationYaw;
        final float pitch = prm.user.rotationPitch;
        double tX = -MathHelper.sin(yaw / 180.0f * 3.1415927f) * MathHelper.cos(pitch / 180.0f * 3.1415927f);
        double tZ = MathHelper.cos(yaw / 180.0f * 3.1415927f) * MathHelper.cos(pitch / 180.0f * 3.1415927f);
        double tY = -MathHelper.sin(pitch / 180.0f * 3.1415927f);
        final double dist = MathHelper.sqrt_double(tX * tX + tY * tY + tZ * tZ);
        if (this.worldObj.isRemote) {
            tX = tX * 200.0 / dist;
            tY = tY * 200.0 / dist;
            tZ = tZ * 200.0 / dist;
        }
        else {
            tX = tX * 250.0 / dist;
            tY = tY * 250.0 / dist;
            tZ = tZ * 250.0 / dist;
        }
        final Vec3 src = W_WorldFunc.getWorldVec3(this.worldObj, prm.entity.posX, prm.entity.posY + 1.62, prm.entity.posZ);
        final Vec3 dst = W_WorldFunc.getWorldVec3(this.worldObj, prm.entity.posX + tX, prm.entity.posY + 1.62 + tY, prm.entity.posZ + tZ);
        final MovingObjectPosition m = W_WorldFunc.clip(this.worldObj, src, dst);
        if (m != null && W_MovingObjectPosition.isHitTypeTile(m) && !MCH_Lib.isBlockInWater(this.worldObj, m.blockX, m.blockY, m.blockZ)) {
            if (!this.worldObj.isRemote) {
                final MCH_EntityASMissile e = new MCH_EntityASMissile(this.worldObj, prm.posX, prm.posY, prm.posZ, tX, tY, tZ, yaw, pitch, (double)this.acceleration);
                e.setName(this.name);
                e.setParameterFromWeapon((MCH_WeaponBase)this, prm.entity, prm.user);
                e.targetPosX = m.hitVec.xCoord;
                e.targetPosY = m.hitVec.yCoord;
                e.targetPosZ = m.hitVec.zCoord;
                this.worldObj.spawnEntityInWorld((Entity)e);
                this.playSound(prm.entity);
            }
            return true;
        }
        return false;
    }
}
