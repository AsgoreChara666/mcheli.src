//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.weapon;

import net.minecraft.world.*;
import net.minecraft.util.*;
import mcheli.*;
import net.minecraft.entity.*;

public class MCH_WeaponRocket extends MCH_WeaponBase
{
    public MCH_WeaponRocket(final World w, final Vec3 v, final float yaw, final float pitch, final String nm, final MCH_WeaponInfo wi) {
        super(w, v, yaw, pitch, nm, wi);
        this.acceleration = 4.0f;
        this.explosionPower = 3;
        this.power = 22;
        this.interval = 5;
        if (w.isRemote) {
            this.interval += 2;
        }
    }
    
    public String getName() {
        return super.getName() + ((this.getCurrentMode() == 0) ? "" : " [HEIAP]");
    }
    
    public boolean shot(final MCH_WeaponParam prm) {
        if (!this.worldObj.isRemote) {
            this.playSound(prm.entity);
            final Vec3 v = MCH_Lib.RotVec3(0.0, 0.0, 1.0, -prm.rotYaw, -prm.rotPitch, -prm.rotRoll);
            final MCH_EntityRocket e = new MCH_EntityRocket(this.worldObj, prm.posX, prm.posY, prm.posZ, v.xCoord, v.yCoord, v.zCoord, prm.rotYaw, prm.rotPitch, (double)this.acceleration);
            e.setName(this.name);
            e.setParameterFromWeapon((MCH_WeaponBase)this, prm.entity, prm.user);
            if (prm.option1 == 0 && this.numMode > 1) {
                e.piercing = 0;
            }
            this.worldObj.spawnEntityInWorld((Entity)e);
        }
        else {
            this.optionParameter1 = this.getCurrentMode();
        }
        return true;
    }
}
