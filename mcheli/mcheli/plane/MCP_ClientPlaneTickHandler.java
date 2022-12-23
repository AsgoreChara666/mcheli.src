//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.plane;

import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import mcheli.uav.*;
import net.minecraft.world.*;
import mcheli.*;
import net.minecraft.entity.*;
import mcheli.aircraft.*;
import mcheli.wrapper.*;

public class MCP_ClientPlaneTickHandler extends MCH_AircraftClientTickHandler
{
    public MCH_Key KeySwitchMode;
    public MCH_Key KeyEjectSeat;
    public MCH_Key KeyZoom;
    public MCH_Key[] Keys;
    
    public MCP_ClientPlaneTickHandler(final Minecraft minecraft, final MCH_Config config) {
        super(minecraft, config);
        this.updateKeybind(config);
    }
    
    public void updateKeybind(final MCH_Config config) {
        super.updateKeybind(config);
        this.KeySwitchMode = new MCH_Key(MCH_Config.KeySwitchMode.prmInt);
        this.KeyEjectSeat = new MCH_Key(MCH_Config.KeySwitchHovering.prmInt);
        this.KeyZoom = new MCH_Key(MCH_Config.KeyZoom.prmInt);
        this.Keys = new MCH_Key[] { this.KeyUp, this.KeyDown, this.KeyRight, this.KeyLeft, this.KeySwitchMode, this.KeyEjectSeat, this.KeyUseWeapon, this.KeySwWeaponMode, this.KeySwitchWeapon1, this.KeySwitchWeapon2, this.KeyZoom, this.KeyCameraMode, this.KeyUnmount, this.KeyUnmountForce, this.KeyFlare, this.KeyExtra, this.KeyFreeLook, this.KeyGUI, this.KeyGearUpDown, this.KeyPutToRack, this.KeyDownFromRack };
    }
    
    protected void update(final EntityPlayer player, final MCP_EntityPlane plane) {
        if (plane.getIsGunnerMode((Entity)player)) {
            final MCH_SeatInfo seatInfo = plane.getSeatInfo((Entity)player);
            if (seatInfo != null) {
                setRotLimitPitch(seatInfo.minPitch, seatInfo.maxPitch, (Entity)player);
            }
        }
        plane.updateRadar(10);
        plane.updateCameraRotate(player.rotationYaw, player.rotationPitch);
    }
    
    protected void onTick(final boolean inGUI) {
        for (final MCH_Key k : this.Keys) {
            k.update();
        }
        this.isBeforeRiding = this.isRiding;
        final EntityPlayer player = (EntityPlayer)this.mc.thePlayer;
        MCP_EntityPlane plane = null;
        boolean isPilot = true;
        if (player != null) {
            if (player.ridingEntity instanceof MCP_EntityPlane) {
                plane = (MCP_EntityPlane)player.ridingEntity;
            }
            else if (player.ridingEntity instanceof MCH_EntitySeat) {
                final MCH_EntitySeat seat = (MCH_EntitySeat)player.ridingEntity;
                if (seat.getParent() instanceof MCP_EntityPlane) {
                    isPilot = false;
                    plane = (MCP_EntityPlane)seat.getParent();
                }
            }
            else if (player.ridingEntity instanceof MCH_EntityUavStation) {
                final MCH_EntityUavStation uavStation = (MCH_EntityUavStation)player.ridingEntity;
                if (uavStation.getControlAircract() instanceof MCP_EntityPlane) {
                    plane = (MCP_EntityPlane)uavStation.getControlAircract();
                }
            }
        }
        if (plane != null && plane.getAcInfo() != null) {
            this.update(player, plane);
            final MCH_ViewEntityDummy viewEntityDummy = MCH_ViewEntityDummy.getInstance((World)this.mc.theWorld);
            viewEntityDummy.update(plane.camera);
            if (!inGUI) {
                if (!plane.isDestroyed()) {
                    this.playerControl(player, plane, isPilot);
                }
            }
            else {
                this.playerControlInGUI(player, plane, isPilot);
            }
            boolean hideHand = true;
            if ((isPilot && plane.isAlwaysCameraView()) || plane.getIsGunnerMode((Entity)player) || plane.getCameraId() > 0) {
                MCH_Lib.setRenderViewEntity((EntityLivingBase)viewEntityDummy);
            }
            else {
                MCH_Lib.setRenderViewEntity((EntityLivingBase)player);
                if (!isPilot && plane.getCurrentWeaponID((Entity)player) < 0) {
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
        if (!this.isBeforeRiding && this.isRiding && plane != null) {
            W_Reflection.setThirdPersonDistance(plane.thirdPersonDist);
            MCH_ViewEntityDummy.getInstance((World)this.mc.theWorld).setPosition(plane.posX, plane.posY + 0.5, plane.posZ);
        }
        else if (this.isBeforeRiding && !this.isRiding) {
            W_Reflection.restoreDefaultThirdPersonDistance();
            MCH_Lib.enableFirstPersonItemRender();
            MCH_Lib.setRenderViewEntity((EntityLivingBase)player);
            W_Reflection.setCameraRoll(0.0f);
        }
    }
    
    protected void playerControlInGUI(final EntityPlayer player, final MCP_EntityPlane plane, final boolean isPilot) {
        this.commonPlayerControlInGUI(player, (MCH_EntityAircraft)plane, isPilot, (MCH_PacketPlayerControlBase)new MCP_PlanePacketPlayerControl());
    }
    
    protected void playerControl(final EntityPlayer player, final MCP_EntityPlane plane, final boolean isPilot) {
        final MCP_PlanePacketPlayerControl pc = new MCP_PlanePacketPlayerControl();
        boolean send = false;
        final MCH_EntityAircraft ac = plane;
        send = this.commonPlayerControl(player, (MCH_EntityAircraft)plane, isPilot, (MCH_PacketPlayerControlBase)pc);
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
            if (this.KeyExtra.isKeyDown()) {
                if (plane.canSwitchVtol()) {
                    final boolean currentMode = plane.getNozzleStat();
                    if (!currentMode) {
                        pc.switchVtol = 1;
                    }
                    else {
                        pc.switchVtol = 0;
                    }
                    plane.swithVtolMode(!currentMode);
                    send = true;
                }
                else {
                    playSoundNG();
                }
            }
        }
        else if (this.KeySwitchMode.isKeyDown()) {
            if (plane.canSwitchGunnerModeOtherSeat(player)) {
                plane.switchGunnerModeOtherSeat(player);
                send = true;
            }
            else {
                playSoundNG();
            }
        }
        if (this.KeyZoom.isKeyDown()) {
            final boolean isUav = plane.isUAV() && !plane.getAcInfo().haveHatch() && !plane.getPlaneInfo().haveWing();
            if (plane.getIsGunnerMode((Entity)player) || isUav) {
                plane.zoomCamera();
                playSound("zoom", 0.5f, 1.0f);
            }
            else if (isPilot) {
                if (plane.getAcInfo().haveHatch()) {
                    if (plane.canFoldHatch()) {
                        pc.switchHatch = 2;
                        send = true;
                    }
                    else if (plane.canUnfoldHatch()) {
                        pc.switchHatch = 1;
                        send = true;
                    }
                }
                else if (plane.canFoldWing()) {
                    pc.switchHatch = 2;
                    send = true;
                }
                else if (plane.canUnfoldWing()) {
                    pc.switchHatch = 1;
                    send = true;
                }
            }
        }
        if (this.KeyEjectSeat.isKeyDown() && plane.canEjectSeat((Entity)player)) {
            pc.ejectSeat = true;
            send = true;
        }
        if (send) {
            W_Network.sendToServer((W_PacketBase)pc);
        }
    }
}
