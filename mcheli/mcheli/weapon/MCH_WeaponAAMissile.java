//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.weapon;

import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import mcheli.wrapper.*;

public class MCH_WeaponAAMissile extends MCH_WeaponEntitySeeker
{
    public MCH_WeaponAAMissile(final World w, final Vec3 v, final float yaw, final float pitch, final String nm, final MCH_WeaponInfo wi) {
        super(w, v, yaw, pitch, nm, wi);
        this.power = 12;
        this.acceleration = 2.5f;
        this.explosionPower = 4;
        this.interval = 5;
        if (w.isRemote) {
            this.interval += 5;
        }
        this.guidanceSystem.canLockInAir = true;
        this.guidanceSystem.ridableOnly = wi.ridableOnly;
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
        boolean result = false;
        if (!this.worldObj.isRemote) {
            final Entity tgtEnt = prm.user.worldObj.getEntityByID(prm.option1);
            if (tgtEnt != null && !tgtEnt.isDead) {
                this.playSound(prm.entity);
                final float yaw = prm.entity.rotationYaw + this.fixRotationYaw;
                final float pitch = prm.entity.rotationPitch + this.fixRotationPitch;
                final double tX = -MathHelper.sin(yaw / 180.0f * 3.1415927f) * MathHelper.cos(pitch / 180.0f * 3.1415927f);
                final double tZ = MathHelper.cos(yaw / 180.0f * 3.1415927f) * MathHelper.cos(pitch / 180.0f * 3.1415927f);
                final double tY = -MathHelper.sin(pitch / 180.0f * 3.1415927f);
                final MCH_EntityAAMissile e = new MCH_EntityAAMissile(this.worldObj, prm.posX, prm.posY, prm.posZ, tX, tY, tZ, yaw, pitch, (double)this.acceleration);
                e.setName(this.name);
                e.setParameterFromWeapon((MCH_WeaponBase)this, prm.entity, prm.user);
                e.setTargetEntity(tgtEnt);
                this.worldObj.spawnEntityInWorld((Entity)e);
                result = true;
            }
        }
        else if (this.guidanceSystem.lock(prm.user) && this.guidanceSystem.lastLockEntity != null) {
            result = true;
            this.optionParameter1 = W_Entity.getEntityId(this.guidanceSystem.lastLockEntity);
        }
        return result;
    }
}
