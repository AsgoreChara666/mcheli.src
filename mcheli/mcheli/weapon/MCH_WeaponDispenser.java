//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.weapon;

import net.minecraft.world.*;
import net.minecraft.util.*;
import mcheli.*;
import net.minecraft.entity.*;

public class MCH_WeaponDispenser extends MCH_WeaponBase
{
    public MCH_WeaponDispenser(final World w, final Vec3 v, final float yaw, final float pitch, final String nm, final MCH_WeaponInfo wi) {
        super(w, v, yaw, pitch, nm, wi);
        this.acceleration = 0.5f;
        this.explosionPower = 0;
        this.power = 0;
        this.interval = -90;
        if (w.isRemote) {
            this.interval -= 10;
        }
    }
    
    public boolean shot(final MCH_WeaponParam prm) {
        if (!this.worldObj.isRemote) {
            this.playSound(prm.entity);
            final Vec3 v = MCH_Lib.RotVec3(0.0, 0.0, 1.0, -prm.rotYaw, -prm.rotPitch, -prm.rotRoll);
            final MCH_EntityDispensedItem e = new MCH_EntityDispensedItem(this.worldObj, prm.posX, prm.posY, prm.posZ, v.xCoord, v.yCoord, v.zCoord, prm.rotYaw, prm.rotPitch, (double)this.acceleration);
            e.setName(this.name);
            e.setParameterFromWeapon((MCH_WeaponBase)this, prm.entity, prm.user);
            e.motionX = prm.entity.motionX + e.motionX * 0.5;
            e.motionY = prm.entity.motionY + e.motionY * 0.5;
            e.motionZ = prm.entity.motionZ + e.motionZ * 0.5;
            final MCH_EntityDispensedItem mch_EntityDispensedItem = e;
            mch_EntityDispensedItem.posX += e.motionX * 0.5;
            final MCH_EntityDispensedItem mch_EntityDispensedItem2 = e;
            mch_EntityDispensedItem2.posY += e.motionY * 0.5;
            final MCH_EntityDispensedItem mch_EntityDispensedItem3 = e;
            mch_EntityDispensedItem3.posZ += e.motionZ * 0.5;
            this.worldObj.spawnEntityInWorld((Entity)e);
        }
        return true;
    }
}
