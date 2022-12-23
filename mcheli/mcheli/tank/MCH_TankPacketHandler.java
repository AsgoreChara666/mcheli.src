//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.tank;

import net.minecraft.entity.player.*;
import com.google.common.io.*;
import mcheli.uav.*;
import net.minecraft.entity.*;
import mcheli.weapon.*;
import mcheli.aircraft.*;

public class MCH_TankPacketHandler
{
    public static void onPacket_PlayerControl(final EntityPlayer player, final ByteArrayDataInput data) {
        if (player.worldObj.isRemote) {
            return;
        }
        final MCH_TankPacketPlayerControl pc = new MCH_TankPacketPlayerControl();
        pc.readData(data);
        final boolean isPilot = true;
        MCH_EntityTank tank = null;
        if (player.ridingEntity instanceof MCH_EntityTank) {
            tank = (MCH_EntityTank)player.ridingEntity;
        }
        else if (player.ridingEntity instanceof MCH_EntitySeat) {
            if (((MCH_EntitySeat)player.ridingEntity).getParent() instanceof MCH_EntityTank) {
                tank = (MCH_EntityTank)((MCH_EntitySeat)player.ridingEntity).getParent();
            }
        }
        else if (player.ridingEntity instanceof MCH_EntityUavStation) {
            final MCH_EntityUavStation uavStation = (MCH_EntityUavStation)player.ridingEntity;
            if (uavStation.getControlAircract() instanceof MCH_EntityTank) {
                tank = (MCH_EntityTank)uavStation.getControlAircract();
            }
        }
        if (tank == null) {
            return;
        }
        final MCH_EntityAircraft ac = (MCH_EntityAircraft)tank;
        if (pc.isUnmount == 1) {
            ac.unmountEntity();
        }
        else if (pc.isUnmount == 2) {
            ac.unmountCrew();
        }
        else if (pc.ejectSeat) {
            ac.ejectSeat((Entity)player);
        }
        else {
            if (pc.switchMode == 0) {
                ac.switchGunnerMode(false);
            }
            if (pc.switchMode == 1) {
                ac.switchGunnerMode(true);
            }
            if (pc.switchMode == 2) {
                ac.switchHoveringMode(false);
            }
            if (pc.switchMode == 3) {
                ac.switchHoveringMode(true);
            }
            if (pc.switchSearchLight) {
                ac.setSearchLight(!ac.isSearchLightON());
            }
            if (pc.switchCameraMode > 0) {
                ac.switchCameraMode(player, pc.switchCameraMode - 1);
            }
            if (pc.switchWeapon >= 0) {
                ac.switchWeapon((Entity)player, (int)pc.switchWeapon);
            }
            if (pc.useWeapon) {
                final MCH_WeaponParam prm = new MCH_WeaponParam();
                prm.entity = (Entity)tank;
                prm.user = (Entity)player;
                prm.setPosAndRot(pc.useWeaponPosX, pc.useWeaponPosY, pc.useWeaponPosZ, 0.0f, 0.0f);
                prm.option1 = pc.useWeaponOption1;
                prm.option2 = pc.useWeaponOption2;
                ac.useCurrentWeapon(prm);
            }
            if (ac.isPilot((Entity)player)) {
                ac.throttleUp = pc.throttleUp;
                ac.throttleDown = pc.throttleDown;
                final double dx = ac.posX - ac.prevPosX;
                final double dz = ac.posZ - ac.prevPosZ;
                final double dist = dx * dx + dz * dz;
                if (pc.useBrake && ac.getCurrentThrottle() <= 0.03 && dist < 0.01) {
                    ac.moveLeft = false;
                    ac.moveRight = false;
                }
                ac.setBrake(pc.useBrake);
            }
            if (pc.useFlareType > 0) {
                ac.useFlare((int)pc.useFlareType);
            }
            if (pc.openGui) {
                ac.openGui(player);
            }
            if (pc.switchHatch > 0 && ac.getAcInfo().haveHatch()) {
                ac.foldHatch(pc.switchHatch == 2);
            }
            if (pc.switchFreeLook > 0) {
                ac.switchFreeLookMode(pc.switchFreeLook == 1);
            }
            if (pc.switchGear == 1) {
                ac.foldLandingGear();
            }
            if (pc.switchGear == 2) {
                ac.unfoldLandingGear();
            }
            if (pc.putDownRack == 1) {
                ac.mountEntityToRack();
            }
            if (pc.putDownRack == 2) {
                ac.unmountEntityFromRack();
            }
            if (pc.putDownRack == 3) {
                ac.rideRack();
            }
            if (pc.isUnmount == 3) {
                ac.unmountAircraft();
            }
            if (pc.switchGunnerStatus) {
                ac.setGunnerStatus(!ac.getGunnerStatus());
            }
        }
    }
}
