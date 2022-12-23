//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.gltd;

import mcheli.gui.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import org.lwjgl.opengl.*;
import mcheli.*;
import mcheli.wrapper.*;
import net.minecraft.world.*;
import net.minecraft.util.*;

@SideOnly(Side.CLIENT)
public class MCH_GuiGLTD extends MCH_Gui
{
    public MCH_GuiGLTD(final Minecraft minecraft) {
        super(minecraft);
    }
    
    @Override
    public void initGui() {
        super.initGui();
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    @Override
    public boolean isDrawGui(final EntityPlayer player) {
        return player.ridingEntity != null && player.ridingEntity instanceof MCH_EntityGLTD;
    }
    
    @Override
    public void drawGui(final EntityPlayer player, final boolean isThirdPersonView) {
        if (isThirdPersonView) {
            final MCH_Config config = MCH_MOD.config;
            if (!MCH_Config.DisplayHUDThirdPerson.prmBool) {
                return;
            }
        }
        GL11.glLineWidth((float)MCH_GuiGLTD.scaleFactor);
        if (!this.isDrawGui(player)) {
            return;
        }
        final MCH_EntityGLTD gltd = (MCH_EntityGLTD)player.ridingEntity;
        if (gltd.camera.getMode(0) == 1) {
            GL11.glEnable(3042);
            GL11.glColor4f(0.0f, 1.0f, 0.0f, 0.3f);
            final int srcBlend = GL11.glGetInteger(3041);
            final int dstBlend = GL11.glGetInteger(3040);
            GL11.glBlendFunc(1, 1);
            W_McClient.MOD_bindTexture("textures/gui/alpha.png");
            this.drawTexturedModalRectRotate(0.0, 0.0, this.width, this.height, this.rand.nextInt(256), this.rand.nextInt(256), 256.0, 256.0, 0.0f);
            GL11.glBlendFunc(srcBlend, dstBlend);
            GL11.glDisable(3042);
        }
        this.drawString(String.format("x%.1f", gltd.camera.getCameraZoom()), this.centerX - 70, this.centerY + 10, -805306369);
        this.drawString(gltd.weaponCAS.getName(), this.centerX - 200, this.centerY + 65, (gltd.countWait == 0) ? -819986657 : -807468024);
        this.drawCommonPosition(gltd, -819986657);
        this.drawString(gltd.camera.getModeName(0), this.centerX + 30, this.centerY - 50, -819986657);
        this.drawSight(gltd.camera, -819986657);
        this.drawTargetPosition(gltd, -819986657, -807468024);
        this.drawKeyBind(gltd.camera, -805306369, -813727873);
    }
    
    public void drawKeyBind(final MCH_Camera camera, final int color, final int colorCannotUse) {
        int OffX = this.centerX + 55;
        final int OffY = this.centerY + 40;
        this.drawString("DISMOUNT :", OffX, OffY + 0, color);
        this.drawString("CAM MODE :", OffX, OffY + 10, color);
        this.drawString("ZOOM IN   :", OffX, OffY + 20, (camera.getCameraZoom() < 10.0f) ? color : colorCannotUse);
        this.drawString("ZOOM OUT :", OffX, OffY + 30, (camera.getCameraZoom() > 1.0f) ? color : colorCannotUse);
        OffX += 60;
        final StringBuilder append = new StringBuilder().append(MCH_KeyName.getDescOrName(42)).append(" or ");
        final MCH_Config config = MCH_MOD.config;
        this.drawString(append.append(MCH_KeyName.getDescOrName(MCH_Config.KeyUnmount.prmInt)).toString(), OffX, OffY + 0, color);
        final MCH_Config config2 = MCH_MOD.config;
        this.drawString(MCH_KeyName.getDescOrName(MCH_Config.KeyCameraMode.prmInt), OffX, OffY + 10, color);
        final MCH_Config config3 = MCH_MOD.config;
        this.drawString(MCH_KeyName.getDescOrName(MCH_Config.KeyZoom.prmInt), OffX, OffY + 20, (camera.getCameraZoom() < 10.0f) ? color : colorCannotUse);
        final MCH_Config config4 = MCH_MOD.config;
        this.drawString(MCH_KeyName.getDescOrName(MCH_Config.KeySwWeaponMode.prmInt), OffX, OffY + 30, (camera.getCameraZoom() > 1.0f) ? color : colorCannotUse);
    }
    
    public void drawCommonPosition(final MCH_EntityGLTD gltd, final int color) {
        final int OFFSETX = 145;
        this.drawString(String.format("X: %+.1f", gltd.posX), this.centerX - 145, this.centerY + 0, color);
        this.drawString(String.format("Y: %+.1f", gltd.posY), this.centerX - 145, this.centerY + 10, color);
        this.drawString(String.format("Z: %+.1f", gltd.posZ), this.centerX - 145, this.centerY + 20, color);
        this.drawString(String.format("AX: %+.1f", gltd.riddenByEntity.rotationYaw), this.centerX - 145, this.centerY + 40, color);
        this.drawString(String.format("AY: %+.1f", gltd.riddenByEntity.rotationPitch), this.centerX - 145, this.centerY + 50, color);
    }
    
    public void drawTargetPosition(final MCH_EntityGLTD gltd, final int color, final int colorDanger) {
        if (gltd.riddenByEntity == null) {
            return;
        }
        final World w = gltd.riddenByEntity.worldObj;
        final float yaw = gltd.riddenByEntity.rotationYaw;
        final float pitch = gltd.riddenByEntity.rotationPitch;
        double tX = -MathHelper.sin(yaw / 180.0f * 3.1415927f) * MathHelper.cos(pitch / 180.0f * 3.1415927f);
        double tZ = MathHelper.cos(yaw / 180.0f * 3.1415927f) * MathHelper.cos(pitch / 180.0f * 3.1415927f);
        double tY = -MathHelper.sin(pitch / 180.0f * 3.1415927f);
        final double dist = MathHelper.sqrt_double(tX * tX + tY * tY + tZ * tZ);
        tX = tX * 80.0 / dist;
        tY = tY * 80.0 / dist;
        tZ = tZ * 80.0 / dist;
        final MCH_Camera c = gltd.camera;
        final Vec3 src = W_WorldFunc.getWorldVec3(w, c.posX, c.posY, c.posZ);
        final Vec3 dst = W_WorldFunc.getWorldVec3(w, c.posX + tX, c.posY + tY, c.posZ + tZ);
        final MovingObjectPosition m = W_WorldFunc.clip(w, src, dst);
        final int OS_X = 50;
        if (m != null) {
            this.drawString(String.format("X: %+.2fm", m.hitVec.xCoord), this.centerX + 50, this.centerY - 5 - 15, color);
            this.drawString(String.format("Y: %+.2fm", m.hitVec.yCoord), this.centerX + 50, this.centerY - 5, color);
            this.drawString(String.format("Z: %+.2fm", m.hitVec.zCoord), this.centerX + 50, this.centerY - 5 + 15, color);
            final double x = m.hitVec.xCoord - c.posX;
            final double y = m.hitVec.yCoord - c.posY;
            final double z = m.hitVec.zCoord - c.posZ;
            final double len = Math.sqrt(x * x + y * y + z * z);
            this.drawCenteredString(String.format("[%.2fm]", len), this.centerX, this.centerY + 30, (len > 20.0) ? color : colorDanger);
        }
        else {
            this.drawString("X: --.--m", this.centerX + 50, this.centerY - 5 - 15, color);
            this.drawString("Y: --.--m", this.centerX + 50, this.centerY - 5, color);
            this.drawString("Z: --.--m", this.centerX + 50, this.centerY - 5 + 15, color);
            this.drawCenteredString("[--.--m]", this.centerX, this.centerY + 30, colorDanger);
        }
    }
    
    private void drawSight(final MCH_Camera camera, final int color) {
        final double posX = this.centerX;
        final double posY = this.centerY;
        final int SW = 30;
        final int SH = 20;
        final int SINV = 10;
        final double[] line2 = { posX - 30.0, posY - 10.0, posX - 30.0, posY - 20.0, posX - 30.0, posY - 20.0, posX - 10.0, posY - 20.0, posX - 30.0, posY + 10.0, posX - 30.0, posY + 20.0, posX - 30.0, posY + 20.0, posX - 10.0, posY + 20.0, posX + 30.0, posY - 10.0, posX + 30.0, posY - 20.0, posX + 30.0, posY - 20.0, posX + 10.0, posY - 20.0, posX + 30.0, posY + 10.0, posX + 30.0, posY + 20.0, posX + 30.0, posY + 20.0, posX + 10.0, posY + 20.0 };
        this.drawLine(line2, color);
    }
}
