//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.vehicle;

import cpw.mods.fml.relauncher.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import mcheli.aircraft.*;
import mcheli.*;
import mcheli.weapon.*;

@SideOnly(Side.CLIENT)
public class MCH_GuiVehicle extends MCH_AircraftCommonGui
{
    static final int COLOR1 = -14066;
    static final int COLOR2 = -2161656;
    
    public MCH_GuiVehicle(final Minecraft minecraft) {
        super(minecraft);
    }
    
    public boolean isDrawGui(final EntityPlayer player) {
        return player.ridingEntity != null && player.ridingEntity instanceof MCH_EntityVehicle;
    }
    
    public void drawGui(final EntityPlayer player, final boolean isThirdPersonView) {
        if (player.ridingEntity == null || !(player.ridingEntity instanceof MCH_EntityVehicle)) {
            return;
        }
        final MCH_EntityVehicle vehicle = (MCH_EntityVehicle)player.ridingEntity;
        if (vehicle.isDestroyed()) {
            return;
        }
        final int seatID = vehicle.getSeatIdByEntity((Entity)player);
        GL11.glLineWidth((float)MCH_GuiVehicle.scaleFactor);
        if (vehicle.getCameraMode(player) == 1) {
            this.drawNightVisionNoise();
        }
        if (vehicle.getIsGunnerMode((Entity)player) && vehicle.getTVMissile() != null) {
            this.drawTvMissileNoise((MCH_EntityAircraft)vehicle, vehicle.getTVMissile());
        }
        this.drawDebugtInfo((MCH_EntityAircraft)vehicle);
        Label_0123: {
            if (isThirdPersonView) {
                final MCH_Config config = MCH_MOD.config;
                if (!MCH_Config.DisplayHUDThirdPerson.prmBool) {
                    break Label_0123;
                }
            }
            this.drawHud((MCH_EntityAircraft)vehicle, player, seatID);
            this.drawKeyBind(vehicle, player);
        }
        this.drawHitBullet((MCH_EntityAircraft)vehicle, -14066, seatID);
    }
    
    public void drawKeyBind(final MCH_EntityVehicle vehicle, final EntityPlayer player) {
        final MCH_Config config = MCH_MOD.config;
        if (MCH_Config.HideKeybind.prmBool) {
            return;
        }
        final MCH_VehicleInfo info = vehicle.getVehicleInfo();
        if (info == null) {
            return;
        }
        final int colorActive = -1342177281;
        final int colorInactive = -1349546097;
        final int RX = this.centerX + 120;
        final int LX = this.centerX - 200;
        if (vehicle.haveFlare()) {
            final int c = vehicle.isFlarePreparation() ? colorInactive : colorActive;
            final StringBuilder append = new StringBuilder().append("Flare : ");
            final MCH_Config config2 = MCH_MOD.config;
            final String msg = append.append(MCH_KeyName.getDescOrName(MCH_Config.KeyFlare.prmInt)).toString();
            this.drawString(msg, RX, this.centerY - 50, c);
        }
        final StringBuilder append2 = new StringBuilder().append("Gunner ").append(vehicle.getGunnerStatus() ? "ON" : "OFF").append(" : ");
        final MCH_Config config3 = MCH_MOD.config;
        final StringBuilder append3 = append2.append(MCH_KeyName.getDescOrName(MCH_Config.KeyFreeLook.prmInt)).append(" + ");
        final MCH_Config config4 = MCH_MOD.config;
        String msg = append3.append(MCH_KeyName.getDescOrName(MCH_Config.KeyCameraMode.prmInt)).toString();
        this.drawString(msg, LX, this.centerY - 40, colorActive);
        if (vehicle.getSizeInventory() > 0) {}
        if (vehicle.getTowChainEntity() != null && !vehicle.getTowChainEntity().isDead) {
            final StringBuilder append4 = new StringBuilder().append("Drop  : ");
            final MCH_Config config5 = MCH_MOD.config;
            msg = append4.append(MCH_KeyName.getDescOrName(MCH_Config.KeyExtra.prmInt)).toString();
            this.drawString(msg, RX, this.centerY - 30, colorActive);
        }
        if (vehicle.camera.getCameraZoom() > 1.0f) {
            final StringBuilder append5 = new StringBuilder().append("Zoom : ");
            final MCH_Config config6 = MCH_MOD.config;
            msg = append5.append(MCH_KeyName.getDescOrName(MCH_Config.KeyZoom.prmInt)).toString();
            this.drawString(msg, LX, this.centerY - 80, colorActive);
        }
        final MCH_WeaponSet ws = vehicle.getCurrentWeapon((Entity)player);
        if (vehicle.getWeaponNum() > 1) {
            final StringBuilder append6 = new StringBuilder().append("Weapon : ");
            final MCH_Config config7 = MCH_MOD.config;
            msg = append6.append(MCH_KeyName.getDescOrName(MCH_Config.KeySwitchWeapon2.prmInt)).toString();
            this.drawString(msg, LX, this.centerY - 70, colorActive);
        }
        if (ws.getCurrentWeapon().numMode > 0) {
            final StringBuilder append7 = new StringBuilder().append("WeaponMode : ");
            final MCH_Config config8 = MCH_MOD.config;
            msg = append7.append(MCH_KeyName.getDescOrName(MCH_Config.KeySwWeaponMode.prmInt)).toString();
            this.drawString(msg, LX, this.centerY - 60, colorActive);
        }
        if (info.isEnableNightVision) {
            final StringBuilder append8 = new StringBuilder().append("CameraMode : ");
            final MCH_Config config9 = MCH_MOD.config;
            msg = append8.append(MCH_KeyName.getDescOrName(MCH_Config.KeyCameraMode.prmInt)).toString();
            this.drawString(msg, LX, this.centerY - 50, colorActive);
        }
        msg = "Dismount all : LShift";
        this.drawString(msg, LX, this.centerY - 30, colorActive);
        if (vehicle.getSeatNum() >= 2) {
            final StringBuilder append9 = new StringBuilder().append("Dismount : ");
            final MCH_Config config10 = MCH_MOD.config;
            msg = append9.append(MCH_KeyName.getDescOrName(MCH_Config.KeyUnmount.prmInt)).toString();
            this.drawString(msg, LX, this.centerY - 40, colorActive);
        }
    }
}
