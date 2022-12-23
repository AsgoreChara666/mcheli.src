//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.weapon;

import net.minecraft.world.*;
import net.minecraft.util.*;
import mcheli.helicopter.*;
import mcheli.aircraft.*;
import mcheli.*;
import net.minecraft.entity.*;

public class MCH_WeaponBomb extends MCH_WeaponBase
{
    public MCH_WeaponBomb(final World w, final Vec3 v, final float yaw, final float pitch, final String nm, final MCH_WeaponInfo wi) {
        super(w, v, yaw, pitch, nm, wi);
        this.acceleration = 0.5f;
        this.explosionPower = 9;
        this.power = 35;
        this.interval = -90;
        if (w.isRemote) {
            this.interval -= 10;
        }
    }
    
    public boolean shot(final MCH_WeaponParam prm) {
        if (this.getInfo() != null && this.getInfo().destruct) {
            if (prm.entity instanceof MCH_EntityHeli) {
                final MCH_EntityAircraft ac = (MCH_EntityAircraft)prm.entity;
                if (ac.isUAV() && ac.getSeatNum() == 0) {
                    if (!this.worldObj.isRemote) {
                        MCH_Explosion.newExplosion(this.worldObj, (Entity)null, prm.user, ac.posX, ac.posY, ac.posZ, (float)this.getInfo().explosion, (float)this.getInfo().explosionBlock, true, true, this.getInfo().flaming, true, 0);
                        this.playSound(prm.entity);
                    }
                    ac.destruct();
                }
            }
        }
        else if (!this.worldObj.isRemote) {
            this.playSound(prm.entity);
            final MCH_EntityBomb e = new MCH_EntityBomb(this.worldObj, prm.posX, prm.posY, prm.posZ, prm.entity.motionX, prm.entity.motionY, prm.entity.motionZ, prm.entity.rotationYaw, 0.0f, (double)this.acceleration);
            e.setName(this.name);
            e.setParameterFromWeapon((MCH_WeaponBase)this, prm.entity, prm.user);
            e.motionX = prm.entity.motionX;
            e.motionY = prm.entity.motionY;
            e.motionZ = prm.entity.motionZ;
            this.worldObj.spawnEntityInWorld((Entity)e);
        }
        return true;
    }
}
