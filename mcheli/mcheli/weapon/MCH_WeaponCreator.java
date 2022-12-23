//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.weapon;

import net.minecraft.world.*;
import net.minecraft.util.*;

public class MCH_WeaponCreator
{
    public static MCH_WeaponBase createWeapon(final World w, final String weaponName, final Vec3 v, final float yaw, final float pitch, final MCH_IEntityLockChecker lockChecker, final boolean onTurret) {
        final MCH_WeaponInfo info = MCH_WeaponInfoManager.get(weaponName);
        if (info == null || info.type == "") {
            return null;
        }
        MCH_WeaponBase weapon = null;
        if (info.type.compareTo("machinegun1") == 0) {
            weapon = new MCH_WeaponMachineGun1(w, v, yaw, pitch, weaponName, info);
        }
        if (info.type.compareTo("machinegun2") == 0) {
            weapon = new MCH_WeaponMachineGun2(w, v, yaw, pitch, weaponName, info);
        }
        if (info.type.compareTo("tvmissile") == 0) {
            weapon = new MCH_WeaponTvMissile(w, v, yaw, pitch, weaponName, info);
        }
        if (info.type.compareTo("torpedo") == 0) {
            weapon = new MCH_WeaponTorpedo(w, v, yaw, pitch, weaponName, info);
        }
        if (info.type.compareTo("cas") == 0) {
            weapon = (MCH_WeaponBase)new MCH_WeaponCAS(w, v, yaw, pitch, weaponName, info);
        }
        if (info.type.compareTo("rocket") == 0) {
            weapon = new MCH_WeaponRocket(w, v, yaw, pitch, weaponName, info);
        }
        if (info.type.compareTo("asmissile") == 0) {
            weapon = (MCH_WeaponBase)new MCH_WeaponASMissile(w, v, yaw, pitch, weaponName, info);
        }
        if (info.type.compareTo("aamissile") == 0) {
            weapon = (MCH_WeaponBase)new MCH_WeaponAAMissile(w, v, yaw, pitch, weaponName, info);
        }
        if (info.type.compareTo("atmissile") == 0) {
            weapon = (MCH_WeaponBase)new MCH_WeaponATMissile(w, v, yaw, pitch, weaponName, info);
        }
        if (info.type.compareTo("bomb") == 0) {
            weapon = (MCH_WeaponBase)new MCH_WeaponBomb(w, v, yaw, pitch, weaponName, info);
        }
        if (info.type.compareTo("mkrocket") == 0) {
            weapon = new MCH_WeaponMarkerRocket(w, v, yaw, pitch, weaponName, info);
        }
        if (info.type.compareTo("dummy") == 0) {
            weapon = new MCH_WeaponDummy(w, v, yaw, pitch, weaponName, info);
        }
        if (info.type.compareTo("smoke") == 0) {
            weapon = new MCH_WeaponSmoke(w, v, yaw, pitch, weaponName, info);
        }
        if (info.type.compareTo("dispenser") == 0) {
            weapon = new MCH_WeaponDispenser(w, v, yaw, pitch, weaponName, info);
        }
        if (info.type.compareTo("targetingpod") == 0) {
            weapon = new MCH_WeaponTargetingPod(w, v, yaw, pitch, weaponName, info);
        }
        if (weapon != null) {
            weapon.displayName = info.displayName;
            weapon.power = info.power;
            weapon.acceleration = info.acceleration;
            weapon.explosionPower = info.explosion;
            weapon.explosionPowerInWater = info.explosionInWater;
            int interval = info.delay;
            weapon.interval = info.delay;
            weapon.delayedInterval = info.delay;
            weapon.setLockCountMax(info.lockTime);
            weapon.setLockChecker(lockChecker);
            weapon.numMode = info.modeNum;
            weapon.piercing = info.piercing;
            weapon.heatCount = info.heatCount;
            weapon.onTurret = onTurret;
            if (info.maxHeatCount > 0 && weapon.heatCount < 2) {
                weapon.heatCount = 2;
            }
            if (interval < 4) {
                ++interval;
            }
            else if (interval < 7) {
                interval += 2;
            }
            else if (interval < 10) {
                interval += 3;
            }
            else if (interval < 20) {
                interval += 6;
            }
            else {
                interval += 10;
                if (interval >= 40) {
                    interval = -interval;
                }
            }
            weapon.delayedInterval = interval;
            if (w.isRemote) {
                weapon.interval = interval;
                final MCH_WeaponBase mch_WeaponBase = weapon;
                ++mch_WeaponBase.heatCount;
                weapon.cartridge = info.cartridge;
            }
            weapon.modifyCommonParameters();
        }
        return weapon;
    }
}
