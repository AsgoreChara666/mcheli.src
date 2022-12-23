//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.helicopter;

import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import mcheli.uav.*;
import net.minecraft.world.*;
import mcheli.*;
import net.minecraft.entity.*;
import mcheli.aircraft.*;
import mcheli.wrapper.*;

public class MCH_ClientHeliTickHandler extends MCH_AircraftClientTickHandler
{
    public MCH_Key KeySwitchMode;
    public MCH_Key KeySwitchHovering;
    public MCH_Key KeyZoom;
    public MCH_Key[] Keys;
    
    public MCH_ClientHeliTickHandler(final Minecraft minecraft, final MCH_Config config) {
        super(minecraft, config);
        this.updateKeybind(config);
    }
    
    public void updateKeybind(final MCH_Config config) {
        super.updateKeybind(config);
        this.KeySwitchMode = new MCH_Key(MCH_Config.KeySwitchMode.prmInt);
        this.KeySwitchHovering = new MCH_Key(MCH_Config.KeySwitchHovering.prmInt);
        this.KeyZoom = new MCH_Key(MCH_Config.KeyZoom.prmInt);
        this.Keys = new MCH_Key[] { this.KeyUp, this.KeyDown, this.KeyRight, this.KeyLeft, this.KeySwitchMode, this.KeySwitchHovering, this.KeyUseWeapon, this.KeySwWeaponMode, this.KeySwitchWeapon1, this.KeySwitchWeapon2, this.KeyZoom, this.KeyCameraMode, this.KeyUnmount, this.KeyUnmountForce, this.KeyFlare, this.KeyExtra, this.KeyFreeLook, this.KeyGUI, this.KeyGearUpDown, this.KeyPutToRack, this.KeyDownFromRack };
    }
    
    protected void update(final EntityPlayer player, final MCH_EntityHeli heli, final boolean isPilot) {
        if (heli.getIsGunnerMode((Entity)player)) {
            final MCH_SeatInfo seatInfo = heli.getSeatInfo((Entity)player);
            if (seatInfo != null) {
                setRotLimitPitch(seatInfo.minPitch, seatInfo.maxPitch, (Entity)player);
            }
        }
        heli.updateCameraRotate(player.rotationYaw, player.rotationPitch);
        heli.updateRadar(5);
    }
    
    protected void onTick(final boolean inGUI) {
        for (final MCH_Key k : this.Keys) {
            k.update();
        }
        this.isBeforeRiding = this.isRiding;
        final EntityPlayer player = (EntityPlayer)this.mc.thePlayer;
        MCH_EntityHeli heli = null;
        boolean isPilot = true;
        if (player != null) {
            if (player.ridingEntity instanceof MCH_EntityHeli) {
                heli = (MCH_EntityHeli)player.ridingEntity;
            }
            else if (player.ridingEntity instanceof MCH_EntitySeat) {
                final MCH_EntitySeat seat = (MCH_EntitySeat)player.ridingEntity;
                if (seat.getParent() instanceof MCH_EntityHeli) {
                    isPilot = false;
                    heli = (MCH_EntityHeli)seat.getParent();
                }
            }
            else if (player.ridingEntity instanceof MCH_EntityUavStation) {
                final MCH_EntityUavStation uavStation = (MCH_EntityUavStation)player.ridingEntity;
                if (uavStation.getControlAircract() instanceof MCH_EntityHeli) {
                    heli = (MCH_EntityHeli)uavStation.getControlAircract();
                }
            }
        }
        if (heli != null && heli.getAcInfo() != null) {
            this.update(player, heli, isPilot);
            final MCH_ViewEntityDummy viewEntityDummy = MCH_ViewEntityDummy.getInstance((World)this.mc.theWorld);
            viewEntityDummy.update(heli.camera);
            if (!inGUI) {
                if (!heli.isDestroyed()) {
                    this.playerControl(player, heli, isPilot);
                }
            }
            else {
                this.playerControlInGUI(player, heli, isPilot);
            }
            boolean hideHand = true;
            if ((isPilot && heli.isAlwaysCameraView()) || heli.getIsGunnerMode((Entity)player)) {
                MCH_Lib.setRenderViewEntity((EntityLivingBase)viewEntityDummy);
            }
            else {
                MCH_Lib.setRenderViewEntity((EntityLivingBase)player);
                if (!isPilot && heli.getCurrentWeaponID((Entity)player) < 0) {
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
        if (!this.isBeforeRiding && this.isRiding) {
            W_Reflection.setThirdPersonDistance(heli.thirdPersonDist);
        }
        else if (this.isBeforeRiding && !this.isRiding) {
            W_Reflection.restoreDefaultThirdPersonDistance();
            W_Reflection.setCameraRoll(0.0f);
            MCH_Lib.enableFirstPersonItemRender();
            MCH_Lib.setRenderViewEntity((EntityLivingBase)player);
        }
    }
    
    protected void playerControlInGUI(final EntityPlayer player, final MCH_EntityHeli heli, final boolean isPilot) {
        this.commonPlayerControlInGUI(player, (MCH_EntityAircraft)heli, isPilot, (MCH_PacketPlayerControlBase)new MCH_HeliPacketPlayerControl());
    }
    
    protected void playerControl(final EntityPlayer player, final MCH_EntityHeli heli, final boolean isPilot) {
        final MCH_HeliPacketPlayerControl pc = new MCH_HeliPacketPlayerControl();
        boolean send = false;
        send = this.commonPlayerControl(player, (MCH_EntityAircraft)heli, isPilot, (MCH_PacketPlayerControlBase)pc);
        if (isPilot) {
            if (this.KeyExtra.isKeyDown()) {
                if (heli.getTowChainEntity() != null) {
                    playSoundOK();
                    pc.unhitchChainId = W_Entity.getEntityId((Entity)heli.getTowChainEntity());
                    send = true;
                }
                else if (heli.canSwitchFoldBlades()) {
                    if (heli.isFoldBlades()) {
                        heli.unfoldBlades();
                        pc.switchFold = 0;
                    }
                    else {
                        heli.foldBlades();
                        pc.switchFold = 1;
                    }
                    send = true;
                    playSoundOK();
                }
                else {
                    playSoundNG();
                }
            }
            if (this.KeySwitchHovering.isKeyDown()) {
                if (heli.canSwitchHoveringMode()) {
                    pc.switchMode = (byte)(heli.isHoveringMode() ? 2 : 3);
                    heli.switchHoveringMode(!heli.isHoveringMode());
                    send = true;
                }
                else {
                    playSoundNG();
                }
            }
            else if (this.KeySwitchMode.isKeyDown()) {
                if (heli.canSwitchGunnerMode()) {
                    pc.switchMode = (byte)(heli.getIsGunnerMode((Entity)player) ? 0 : 1);
                    heli.switchGunnerMode(!heli.getIsGunnerMode((Entity)player));
                    send = true;
                }
                else {
                    playSoundNG();
                }
            }
        }
        else if (this.KeySwitchMode.isKeyDown()) {
            if (heli.canSwitchGunnerModeOtherSeat(player)) {
                heli.switchGunnerModeOtherSeat(player);
                send = true;
            }
            else {
                playSoundNG();
            }
        }
        if (this.KeyZoom.isKeyDown()) {
            final boolean isUav = heli.isUAV() && !heli.getAcInfo().haveHatch();
            if (heli.getIsGunnerMode((Entity)player) || isUav) {
                heli.zoomCamera();
                playSound("zoom", 0.5f, 1.0f);
            }
            else if (isPilot && heli.getAcInfo().haveHatch()) {
                if (heli.canFoldHatch()) {
                    pc.switchHatch = 2;
                    send = true;
                }
                else if (heli.canUnfoldHatch()) {
                    pc.switchHatch = 1;
                    send = true;
                }
                else {
                    playSoundNG();
                }
            }
        }
        if (send) {
            W_Network.sendToServer((W_PacketBase)pc);
        }
    }
}
