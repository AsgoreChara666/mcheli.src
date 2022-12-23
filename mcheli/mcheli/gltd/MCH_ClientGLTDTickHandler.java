//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.gltd;

import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import mcheli.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import mcheli.wrapper.*;

public class MCH_ClientGLTDTickHandler extends MCH_ClientTickHandlerBase
{
    protected boolean isRiding;
    protected boolean isBeforeRiding;
    public MCH_Key KeyUseWeapon;
    public MCH_Key KeySwitchWeapon1;
    public MCH_Key KeySwitchWeapon2;
    public MCH_Key KeySwWeaponMode;
    public MCH_Key KeyZoom;
    public MCH_Key KeyCameraMode;
    public MCH_Key KeyUnmount;
    public MCH_Key KeyUnmount_1_6;
    public MCH_Key[] Keys;
    
    public MCH_ClientGLTDTickHandler(final Minecraft minecraft, final MCH_Config config) {
        super(minecraft);
        this.isRiding = false;
        this.isBeforeRiding = false;
        this.updateKeybind(config);
    }
    
    @Override
    public void updateKeybind(final MCH_Config config) {
        this.KeyUseWeapon = new MCH_Key(MCH_Config.KeyUseWeapon.prmInt);
        this.KeySwitchWeapon1 = new MCH_Key(MCH_Config.KeySwitchWeapon1.prmInt);
        this.KeySwitchWeapon2 = new MCH_Key(MCH_Config.KeySwitchWeapon2.prmInt);
        this.KeySwWeaponMode = new MCH_Key(MCH_Config.KeySwWeaponMode.prmInt);
        this.KeyZoom = new MCH_Key(MCH_Config.KeyZoom.prmInt);
        this.KeyCameraMode = new MCH_Key(MCH_Config.KeyCameraMode.prmInt);
        this.KeyUnmount = new MCH_Key(MCH_Config.KeyUnmount.prmInt);
        this.KeyUnmount_1_6 = new MCH_Key(42);
        this.Keys = new MCH_Key[] { this.KeyUseWeapon, this.KeySwWeaponMode, this.KeySwitchWeapon1, this.KeySwitchWeapon2, this.KeyZoom, this.KeyCameraMode, this.KeyUnmount, this.KeyUnmount_1_6 };
    }
    
    protected void updateGLTD(final EntityPlayer player, final MCH_EntityGLTD gltd) {
        if (player.rotationPitch < -70.0f) {
            player.rotationPitch = -70.0f;
        }
        if (player.rotationPitch > 70.0f) {
            player.rotationPitch = 70.0f;
        }
        final float yaw = gltd.rotationYaw;
        if (player.rotationYaw < yaw - 70.0f) {
            player.rotationYaw = yaw - 70.0f;
        }
        if (player.rotationYaw > yaw + 70.0f) {
            player.rotationYaw = yaw + 70.0f;
        }
        gltd.camera.rotationYaw = player.rotationYaw;
        gltd.camera.rotationPitch = player.rotationPitch;
    }
    
    @Override
    protected void onTick(final boolean inGUI) {
        for (final MCH_Key k : this.Keys) {
            k.update();
        }
        this.isBeforeRiding = this.isRiding;
        final EntityPlayer player = (EntityPlayer)this.mc.thePlayer;
        MCH_ViewEntityDummy viewEntityDummy = null;
        if (player != null && player.ridingEntity instanceof MCH_EntityGLTD) {
            final MCH_EntityGLTD gltd = (MCH_EntityGLTD)player.ridingEntity;
            this.updateGLTD(player, gltd);
            MCH_Lib.disableFirstPersonItemRender(player.getCurrentEquippedItem());
            viewEntityDummy = MCH_ViewEntityDummy.getInstance((World)this.mc.theWorld);
            viewEntityDummy.update(gltd.camera);
            if (!inGUI) {
                this.playerControl(player, gltd);
            }
            MCH_Lib.setRenderViewEntity((EntityLivingBase)viewEntityDummy);
            this.isRiding = true;
        }
        else {
            this.isRiding = false;
        }
        if (this.isBeforeRiding != this.isRiding) {
            if (this.isRiding) {
                if (viewEntityDummy != null) {
                    viewEntityDummy.prevPosX = viewEntityDummy.posX;
                    viewEntityDummy.prevPosY = viewEntityDummy.posY;
                    viewEntityDummy.prevPosZ = viewEntityDummy.posZ;
                }
            }
            else {
                MCH_Lib.enableFirstPersonItemRender();
                MCH_Lib.setRenderViewEntity((EntityLivingBase)player);
            }
        }
    }
    
    protected void playerControl(final EntityPlayer player, final MCH_EntityGLTD gltd) {
        final MCH_PacketGLTDPlayerControl pc = new MCH_PacketGLTDPlayerControl();
        boolean send = false;
        if (this.KeyUnmount.isKeyDown()) {
            pc.unmount = true;
            send = true;
        }
        if (!this.KeySwitchWeapon1.isKeyDown() || !this.KeySwitchWeapon2.isKeyDown()) {
            if (this.KeyUseWeapon.isKeyPress()) {
                if (gltd.useCurrentWeapon(0, 0)) {
                    pc.useWeapon = true;
                    send = true;
                }
                else if (this.KeyUseWeapon.isKeyDown()) {
                    playSoundNG();
                }
            }
        }
        final float prevZoom = gltd.camera.getCameraZoom();
        if (this.KeyZoom.isKeyPress() && !this.KeySwWeaponMode.isKeyPress()) {
            gltd.zoomCamera(0.1f * gltd.camera.getCameraZoom());
        }
        if (!this.KeyZoom.isKeyPress() && this.KeySwWeaponMode.isKeyPress()) {
            gltd.zoomCamera(-0.1f * gltd.camera.getCameraZoom());
        }
        if (prevZoom != gltd.camera.getCameraZoom()) {
            MCH_ClientTickHandlerBase.playSound("zoom", 0.1f, (prevZoom < gltd.camera.getCameraZoom()) ? 1.0f : 0.85f);
        }
        if (this.KeyCameraMode.isKeyDown()) {
            final int beforeMode = gltd.camera.getMode(0);
            gltd.camera.setMode(0, gltd.camera.getMode(0) + 1);
            final int mode = gltd.camera.getMode(0);
            if (mode != beforeMode) {
                pc.switchCameraMode = (byte)mode;
                playSoundOK();
                send = true;
            }
        }
        if (send) {
            W_Network.sendToServer(pc);
        }
    }
}
