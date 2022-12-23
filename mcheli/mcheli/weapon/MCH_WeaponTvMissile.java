//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.weapon;

import net.minecraft.entity.*;
import net.minecraft.world.*;
import mcheli.wrapper.*;
import mcheli.aircraft.*;
import net.minecraft.util.*;

public class MCH_WeaponTvMissile extends MCH_WeaponBase
{
    protected MCH_EntityTvMissile lastShotTvMissile;
    protected Entity lastShotEntity;
    protected boolean isTVGuided;
    
    public MCH_WeaponTvMissile(final World w, final Vec3 v, final float yaw, final float pitch, final String nm, final MCH_WeaponInfo wi) {
        super(w, v, yaw, pitch, nm, wi);
        this.lastShotTvMissile = null;
        this.lastShotEntity = null;
        this.isTVGuided = false;
        this.power = 32;
        this.acceleration = 2.0f;
        this.explosionPower = 4;
        this.interval = -100;
        if (w.isRemote) {
            this.interval -= 10;
        }
        this.numMode = 2;
        this.lastShotEntity = null;
        this.lastShotTvMissile = null;
        this.isTVGuided = false;
    }
    
    public String getName() {
        String opt = "";
        if (this.getCurrentMode() == 0) {
            opt = " [TV]";
        }
        if (this.getCurrentMode() == 2) {
            opt = " [TA]";
        }
        return super.getName() + opt;
    }
    
    public void update(final int countWait) {
        super.update(countWait);
        if (!this.worldObj.isRemote) {
            if (this.isTVGuided && this.tick <= 9) {
                if (this.tick % 3 == 0 && this.lastShotTvMissile != null && !this.lastShotTvMissile.isDead && this.lastShotEntity != null && !this.lastShotEntity.isDead) {
                    MCH_PacketNotifyTVMissileEntity.send(W_Entity.getEntityId(this.lastShotEntity), W_Entity.getEntityId((Entity)this.lastShotTvMissile));
                }
                if (this.tick == 9) {
                    this.lastShotEntity = null;
                    this.lastShotTvMissile = null;
                }
            }
            if (this.tick <= 2 && this.lastShotEntity instanceof MCH_EntityAircraft) {
                ((MCH_EntityAircraft)this.lastShotEntity).setTVMissile(this.lastShotTvMissile);
            }
        }
    }
    
    public boolean shot(final MCH_WeaponParam prm) {
        if (this.worldObj.isRemote) {
            return this.shotClient(prm.entity, prm.user);
        }
        return this.shotServer(prm);
    }
    
    protected boolean shotClient(final Entity entity, final Entity user) {
        this.optionParameter2 = 0;
        this.optionParameter1 = this.getCurrentMode();
        return true;
    }
    
    protected boolean shotServer(final MCH_WeaponParam prm) {
        final float yaw = prm.user.rotationYaw + this.fixRotationYaw;
        final float pitch = prm.user.rotationPitch + this.fixRotationPitch;
        final double tX = -MathHelper.sin(yaw / 180.0f * 3.1415927f) * MathHelper.cos(pitch / 180.0f * 3.1415927f);
        final double tZ = MathHelper.cos(yaw / 180.0f * 3.1415927f) * MathHelper.cos(pitch / 180.0f * 3.1415927f);
        final double tY = -MathHelper.sin(pitch / 180.0f * 3.1415927f);
        this.isTVGuided = (prm.option1 == 0);
        float acr = this.acceleration;
        if (!this.isTVGuided) {
            acr *= 1.5;
        }
        final MCH_EntityTvMissile e = new MCH_EntityTvMissile(this.worldObj, prm.posX, prm.posY, prm.posZ, tX, tY, tZ, yaw, pitch, (double)acr);
        e.setName(this.name);
        e.setParameterFromWeapon((MCH_WeaponBase)this, prm.entity, prm.user);
        this.lastShotEntity = prm.entity;
        this.lastShotTvMissile = e;
        this.worldObj.spawnEntityInWorld((Entity)e);
        this.playSound(prm.entity);
        return true;
    }
}
