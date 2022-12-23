//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.weapon;

import net.minecraft.world.*;
import net.minecraft.util.*;
import mcheli.*;
import net.minecraft.entity.*;

public class MCH_WeaponMachineGun2 extends MCH_WeaponBase
{
    public MCH_WeaponMachineGun2(final World w, final Vec3 v, final float yaw, final float pitch, final String nm, final MCH_WeaponInfo wi) {
        super(w, v, yaw, pitch, nm, wi);
        this.power = 16;
        this.acceleration = 4.0f;
        this.explosionPower = 1;
        this.interval = 2;
        this.numMode = 2;
    }
    
    public void modifyParameters() {
        if (this.explosionPower == 0) {
            this.numMode = 0;
        }
    }
    
    public String getName() {
        return super.getName() + ((this.getCurrentMode() == 0) ? "" : " [HE]");
    }
    
    public boolean shot(final MCH_WeaponParam prm) {
        if (!this.worldObj.isRemote) {
            final Vec3 v = MCH_Lib.RotVec3(0.0, 0.0, 1.0, -prm.rotYaw, -prm.rotPitch, -prm.rotRoll);
            final MCH_EntityBullet e = new MCH_EntityBullet(this.worldObj, prm.posX, prm.posY, prm.posZ, v.xCoord, v.yCoord, v.zCoord, prm.rotYaw, prm.rotPitch, (double)this.acceleration);
            e.setName(this.name);
            e.setParameterFromWeapon((MCH_WeaponBase)this, prm.entity, prm.user);
            if (this.getInfo().modeNum < 2) {
                e.explosionPower = this.explosionPower;
            }
            else {
                e.explosionPower = ((prm.option1 == 0) ? (-this.explosionPower) : this.explosionPower);
            }
            final MCH_EntityBullet mch_EntityBullet = e;
            mch_EntityBullet.posX += e.motionX * 0.5;
            final MCH_EntityBullet mch_EntityBullet2 = e;
            mch_EntityBullet2.posY += e.motionY * 0.5;
            final MCH_EntityBullet mch_EntityBullet3 = e;
            mch_EntityBullet3.posZ += e.motionZ * 0.5;
            this.worldObj.spawnEntityInWorld((Entity)e);
            this.playSound(prm.entity);
        }
        else {
            this.optionParameter1 = this.getCurrentMode();
        }
        return true;
    }
}
