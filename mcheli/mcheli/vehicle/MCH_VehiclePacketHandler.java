//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.vehicle;

import net.minecraft.entity.player.*;
import com.google.common.io.*;
import net.minecraft.entity.*;
import mcheli.weapon.*;
import mcheli.chain.*;

public class MCH_VehiclePacketHandler
{
    public static void onPacket_PlayerControl(final EntityPlayer player, final ByteArrayDataInput data) {
        if (!(player.ridingEntity instanceof MCH_EntityVehicle)) {
            return;
        }
        if (player.worldObj.isRemote) {
            return;
        }
        final MCH_PacketVehiclePlayerControl pc = new MCH_PacketVehiclePlayerControl();
        pc.readData(data);
        final MCH_EntityVehicle vehicle = (MCH_EntityVehicle)player.ridingEntity;
        if (pc.isUnmount == 1) {
            vehicle.unmountEntity();
        }
        else if (pc.isUnmount == 2) {
            vehicle.unmountCrew();
        }
        else {
            if (pc.switchSearchLight) {
                vehicle.setSearchLight(!vehicle.isSearchLightON());
            }
            if (pc.switchCameraMode > 0) {
                vehicle.switchCameraMode(player, pc.switchCameraMode - 1);
            }
            if (pc.switchWeapon >= 0) {
                vehicle.switchWeapon((Entity)player, (int)pc.switchWeapon);
            }
            if (pc.useWeapon) {
                final MCH_WeaponParam prm = new MCH_WeaponParam();
                prm.entity = (Entity)vehicle;
                prm.user = (Entity)player;
                prm.setPosAndRot(pc.useWeaponPosX, pc.useWeaponPosY, pc.useWeaponPosZ, 0.0f, 0.0f);
                prm.option1 = pc.useWeaponOption1;
                prm.option2 = pc.useWeaponOption2;
                vehicle.useCurrentWeapon(prm);
            }
            if (vehicle.isPilot((Entity)player)) {
                vehicle.throttleUp = pc.throttleUp;
                vehicle.throttleDown = pc.throttleDown;
                vehicle.moveLeft = pc.moveLeft;
                vehicle.moveRight = pc.moveRight;
            }
            if (pc.useFlareType > 0) {
                vehicle.useFlare((int)pc.useFlareType);
            }
            if (pc.unhitchChainId >= 0) {
                final Entity e = player.worldObj.getEntityByID(pc.unhitchChainId);
                if (e instanceof MCH_EntityChain) {
                    e.setDead();
                }
            }
            if (pc.openGui) {
                vehicle.openGui(player);
            }
            if (pc.switchHatch > 0) {
                vehicle.foldHatch(pc.switchHatch == 2);
            }
            if (pc.isUnmount == 3) {
                vehicle.unmountAircraft();
            }
            if (pc.switchGunnerStatus) {
                vehicle.setGunnerStatus(!vehicle.getGunnerStatus());
            }
        }
    }
}
