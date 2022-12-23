//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.tank;

import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import mcheli.uav.*;
import net.minecraft.world.*;
import mcheli.*;
import net.minecraft.entity.*;
import mcheli.aircraft.*;
import mcheli.wrapper.*;

public class MCH_ClientTankTickHandler extends MCH_AircraftClientTickHandler
{
    public MCH_Key KeySwitchMode;
    public MCH_Key KeyZoom;
    public MCH_Key[] Keys;
    
    public MCH_ClientTankTickHandler(final Minecraft minecraft, final MCH_Config config) {
        super(minecraft, config);
        this.updateKeybind(config);
    }
    
    public void updateKeybind(final MCH_Config config) {
        super.updateKeybind(config);
        this.KeySwitchMode = new MCH_Key(MCH_Config.KeySwitchMode.prmInt);
        this.KeyZoom = new MCH_Key(MCH_Config.KeyZoom.prmInt);
        this.Keys = new MCH_Key[] { this.KeyUp, this.KeyDown, this.KeyRight, this.KeyLeft, this.KeySwitchMode, this.KeyUseWeapon, this.KeySwWeaponMode, this.KeySwitchWeapon1, this.KeySwitchWeapon2, this.KeyZoom, this.KeyCameraMode, this.KeyUnmount, this.KeyUnmountForce, this.KeyFlare, this.KeyExtra, this.KeyFreeLook, this.KeyGUI, this.KeyGearUpDown, this.KeyBrake, this.KeyPutToRack, this.KeyDownFromRack };
    }
    
    protected void update(final EntityPlayer player, final MCH_EntityTank tank) {
        if (tank.getIsGunnerMode((Entity)player)) {
            final MCH_SeatInfo seatInfo = tank.getSeatInfo((Entity)player);
            if (seatInfo != null) {
                setRotLimitPitch(seatInfo.minPitch, seatInfo.maxPitch, (Entity)player);
            }
        }
        tank.updateRadar(10);
        tank.updateCameraRotate(player.rotationYaw, player.rotationPitch);
    }
    
    protected void onTick(final boolean inGUI) {
        for (final MCH_Key k : this.Keys) {
            k.update();
        }
        this.isBeforeRiding = this.isRiding;
        final EntityPlayer player = (EntityPlayer)this.mc.thePlayer;
        MCH_EntityTank tank = null;
        boolean isPilot = true;
        if (player != null) {
            if (player.ridingEntity instanceof MCH_EntityTank) {
                tank = (MCH_EntityTank)player.ridingEntity;
            }
            else if (player.ridingEntity instanceof MCH_EntitySeat) {
                final MCH_EntitySeat seat = (MCH_EntitySeat)player.ridingEntity;
                if (seat.getParent() instanceof MCH_EntityTank) {
                    isPilot = false;
                    tank = (MCH_EntityTank)seat.getParent();
                }
            }
            else if (player.ridingEntity instanceof MCH_EntityUavStation) {
                final MCH_EntityUavStation uavStation = (MCH_EntityUavStation)player.ridingEntity;
                if (uavStation.getControlAircract() instanceof MCH_EntityTank) {
                    tank = (MCH_EntityTank)uavStation.getControlAircract();
                }
            }
        }
        if (tank != null && tank.getAcInfo() != null) {
            this.update(player, tank);
            final MCH_ViewEntityDummy viewEntityDummy = MCH_ViewEntityDummy.getInstance((World)this.mc.theWorld);
            viewEntityDummy.update(tank.camera);
            if (!inGUI) {
                if (!tank.isDestroyed()) {
                    this.playerControl(player, tank, isPilot);
                }
            }
            else {
                this.playerControlInGUI(player, tank, isPilot);
            }
            boolean hideHand = true;
            if ((isPilot && tank.isAlwaysCameraView()) || tank.getIsGunnerMode((Entity)player) || tank.getCameraId() > 0) {
                MCH_Lib.setRenderViewEntity((EntityLivingBase)viewEntityDummy);
            }
            else {
                MCH_Lib.setRenderViewEntity((EntityLivingBase)player);
                if (!isPilot && tank.getCurrentWeaponID((Entity)player) < 0) {
                    hideHand = false;
                }
            }
            if (hideHand) {
                MCH_Lib.disableFirstPersonItemRender(player.getCurrentEquippedItem());
            }
            this.isRiding = true;
        }
        else {
            this.isRiding = false;
        }
        if (!this.isBeforeRiding && this.isRiding && tank != null) {
            W_Reflection.setThirdPersonDistance(tank.thirdPersonDist);
            MCH_ViewEntityDummy.getInstance((World)this.mc.theWorld).setPosition(tank.posX, tank.posY + 0.5, tank.posZ);
        }
        else if (this.isBeforeRiding && !this.isRiding) {
            W_Reflection.restoreDefaultThirdPersonDistance();
            MCH_Lib.enableFirstPersonItemRender();
            MCH_Lib.setRenderViewEntity((EntityLivingBase)player);
            W_Reflection.setCameraRoll(0.0f);
        }
    }
    
    protected void playerControlInGUI(final EntityPlayer player, final MCH_EntityTank tank, final boolean isPilot) {
        this.commonPlayerControlInGUI(player, (MCH_EntityAircraft)tank, isPilot, (MCH_PacketPlayerControlBase)new MCH_TankPacketPlayerControl());
    }
    
    protected void playerControl(final EntityPlayer player, final MCH_EntityTank tank, final boolean isPilot) {
        final MCH_TankPacketPlayerControl pc = new MCH_TankPacketPlayerControl();
        boolean send = false;
        final MCH_EntityAircraft ac = tank;
        send = this.commonPlayerControl(player, (MCH_EntityAircraft)tank, isPilot, (MCH_PacketPlayerControlBase)pc);
        if (ac.getAcInfo().defaultFreelook && pc.switchFreeLook > 0) {
            pc.switchFreeLook = 0;
        }
        if (isPilot) {
            if (this.KeySwitchMode.isKeyDown()) {
                if (ac.getIsGunnerMode((Entity)player) && ac.canSwitchCameraPos()) {
                    pc.switchMode = 0;
                    ac.switchGunnerMode(false);
                    send = true;
                    ac.setCameraId(1);
                }
                else if (ac.getCameraId() > 0) {
                    ac.setCameraId(ac.getCameraId() + 1);
                    if (ac.getCameraId() >= ac.getCameraPosNum()) {
                        ac.setCameraId(0);
                    }
                }
                else if (ac.canSwitchGunnerMode()) {
                    pc.switchMode = (byte)(ac.getIsGunnerMode((Entity)player) ? 0 : 1);
                    ac.switchGunnerMode(!ac.getIsGunnerMode((Entity)player));
                    send = true;
                    ac.setCameraId(0);
                }
                else if (ac.canSwitchCameraPos()) {
                    ac.setCameraId(1);
                }
                else {
                    playSoundNG();
                }
            }
        }
        else if (this.KeySwitchMode.isKeyDown()) {
            if (tank.canSwitchGunnerModeOtherSeat(player)) {
                tank.switchGunnerModeOtherSeat(player);
                send = true;
            }
            else {
                playSoundNG();
            }
        }
        if (this.KeyZoom.isKeyDown()) {
            final boolean isUav = tank.isUAV() && !tank.getAcInfo().haveHatch();
            if (tank.getIsGunnerMode((Entity)player) || isUav) {
                tank.zoomCamera();
                playSound("zoom", 0.5f, 1.0f);
            }
            else if (isPilot && tank.getAcInfo().haveHatch()) {
                if (tank.canFoldHatch()) {
                    pc.switchHatch = 2;
                    send = true;
                }
                else if (tank.canUnfoldHatch()) {
                    pc.switchHatch = 1;
                    send = true;
                }
            }
        }
        if (send) {
            W_Network.sendToServer((W_PacketBase)pc);
        }
    }
}
