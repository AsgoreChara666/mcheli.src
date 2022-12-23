//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.weapon;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import mcheli.wrapper.*;
import net.minecraft.util.*;

public class MCH_WeaponATMissile extends MCH_WeaponEntitySeeker
{
    public MCH_WeaponATMissile(final World w, final Vec3 v, final float yaw, final float pitch, final String nm, final MCH_WeaponInfo wi) {
        super(w, v, yaw, pitch, nm, wi);
        this.power = 32;
        this.acceleration = 2.0f;
        this.explosionPower = 4;
        this.interval = -100;
        if (w.isRemote) {
            this.interval -= 10;
        }
        this.numMode = 2;
        this.guidanceSystem.canLockOnGround = true;
        this.guidanceSystem.ridableOnly = wi.ridableOnly;
    }
    
    @Override
    public boolean isCooldownCountReloadTime() {
        return true;
    }
    
    @Override
    public String getName() {
        String opt = "";
        if (this.getCurrentMode() == 1) {
            opt = " [TA]";
        }
        return super.getName() + opt;
    }
    
    @Override
    public void update(final int countWait) {
        super.update(countWait);
    }
    
    @Override
    public boolean shot(final MCH_WeaponParam prm) {
        if (this.worldObj.isRemote) {
            return this.shotClient(prm.entity, prm.user);
        }
        return this.shotServer(prm);
    }
    
    protected boolean shotClient(final Entity entity, final Entity user) {
        boolean result = false;
        if (this.guidanceSystem.lock(user) && this.guidanceSystem.lastLockEntity != null) {
            result = true;
            this.optionParameter1 = W_Entity.getEntityId(this.guidanceSystem.lastLockEntity);
        }
        this.optionParameter2 = this.getCurrentMode();
        return result;
    }
    
    protected boolean shotServer(final MCH_WeaponParam prm) {
        Entity tgtEnt = null;
        tgtEnt = prm.user.worldObj.getEntityByID(prm.option1);
        if (tgtEnt == null || tgtEnt.isDead) {
            return false;
        }
        final float yaw = prm.user.rotationYaw + this.fixRotationYaw;
        final float pitch = prm.entity.rotationPitch + this.fixRotationPitch;
        final double tX = -MathHelper.sin(yaw / 180.0f * 3.1415927f) * MathHelper.cos(pitch / 180.0f * 3.1415927f);
        final double tZ = MathHelper.cos(yaw / 180.0f * 3.1415927f) * MathHelper.cos(pitch / 180.0f * 3.1415927f);
        final double tY = -MathHelper.sin(pitch / 180.0f * 3.1415927f);
        final MCH_EntityATMissile e = new MCH_EntityATMissile(this.worldObj, prm.posX, prm.posY, prm.posZ, tX, tY, tZ, yaw, pitch, (double)this.acceleration);
        e.setName(this.name);
        e.setParameterFromWeapon((MCH_WeaponBase)this, prm.entity, prm.user);
        e.setTargetEntity(tgtEnt);
        e.guidanceType = prm.option2;
        this.worldObj.spawnEntityInWorld((Entity)e);
        this.playSound(prm.entity);
        return true;
    }
}
