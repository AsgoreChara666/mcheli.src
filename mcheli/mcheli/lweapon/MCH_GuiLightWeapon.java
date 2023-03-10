//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.lweapon;

import mcheli.gui.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import mcheli.aircraft.*;
import mcheli.gltd.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import mcheli.wrapper.*;
import mcheli.weapon.*;
import net.minecraft.potion.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import mcheli.*;

@SideOnly(Side.CLIENT)
public class MCH_GuiLightWeapon extends MCH_Gui
{
    public MCH_GuiLightWeapon(final Minecraft minecraft) {
        super(minecraft);
    }
    
    public void initGui() {
        super.initGui();
    }
    
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    public boolean isDrawGui(final EntityPlayer player) {
        if (MCH_ItemLightWeaponBase.isHeld(player)) {
            final Entity re = player.ridingEntity;
            if (!(re instanceof MCH_EntityAircraft) && !(re instanceof MCH_EntityGLTD)) {
                return true;
            }
        }
        return false;
    }
    
    public void drawGui(final EntityPlayer player, final boolean isThirdPersonView) {
        if (isThirdPersonView) {
            return;
        }
        GL11.glLineWidth((float)MCH_GuiLightWeapon.scaleFactor);
        if (!this.isDrawGui(player)) {
            return;
        }
        final MCH_ItemLightWeaponBase item = (MCH_ItemLightWeaponBase)player.getHeldItem().getItem();
        final MCH_WeaponGuidanceSystem gs = MCH_ClientLightWeaponTickHandler.gs;
        if (gs != null && MCH_ClientLightWeaponTickHandler.weapon != null && MCH_ClientLightWeaponTickHandler.weapon.getInfo() != null) {
            final PotionEffect pe = player.getActivePotionEffect(Potion.nightVision);
            if (pe != null) {
                this.drawNightVisionNoise();
            }
            GL11.glEnable(3042);
            GL11.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
            final int srcBlend = GL11.glGetInteger(3041);
            final int dstBlend = GL11.glGetInteger(3040);
            GL11.glBlendFunc(770, 771);
            double dist = 0.0;
            if (gs.getTargetEntity() != null) {
                final double dx = gs.getTargetEntity().posX - player.posX;
                final double dz = gs.getTargetEntity().posZ - player.posZ;
                dist = Math.sqrt(dx * dx + dz * dz);
            }
            final boolean canFire = MCH_ClientLightWeaponTickHandler.weaponMode == 0 || dist >= 40.0 || gs.getLockCount() <= 0;
            if ("fgm148".equalsIgnoreCase(MCH_ItemLightWeaponBase.getName(player.getHeldItem()))) {
                this.drawGuiFGM148(player, gs, canFire, player.getHeldItem());
                this.drawKeyBind(-805306369, true);
            }
            else {
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                W_McClient.MOD_bindTexture("textures/gui/stinger.png");
                double size;
                for (size = 512.0; size < this.width || size < this.height; size *= 2.0) {}
                this.drawTexturedModalRectRotate(-(size - this.width) / 2.0, -(size - this.height) / 2.0 - 20.0, size, size, 0.0, 0.0, 256.0, 256.0, 0.0f);
                this.drawKeyBind(-805306369, false);
            }
            GL11.glBlendFunc(srcBlend, dstBlend);
            GL11.glDisable(3042);
            this.drawLock(-14101432, -2161656, gs.getLockCount(), gs.getLockCountMax());
            this.drawRange(player, gs, canFire, -14101432, -2161656);
        }
    }
    
    public void drawNightVisionNoise() {
        GL11.glEnable(3042);
        GL11.glColor4f(0.0f, 1.0f, 0.0f, 0.3f);
        final int srcBlend = GL11.glGetInteger(3041);
        final int dstBlend = GL11.glGetInteger(3040);
        GL11.glBlendFunc(1, 1);
        W_McClient.MOD_bindTexture("textures/gui/alpha.png");
        this.drawTexturedModalRectRotate(0.0, 0.0, (double)this.width, (double)this.height, (double)this.rand.nextInt(256), (double)this.rand.nextInt(256), 256.0, 256.0, 0.0f);
        GL11.glBlendFunc(srcBlend, dstBlend);
        GL11.glDisable(3042);
    }
    
    void drawLock(final int color, final int colorLock, final int cntLock, final int cntMax) {
        final int posX = this.centerX;
        final int posY = this.centerY + 20;
        final int WID = 20;
        final int INV = 10;
        final double[] line = { posX - 20, posY - 10, posX - 20, posY - 20, posX - 20, posY - 20, posX - 10, posY - 20, posX - 20, posY + 10, posX - 20, posY + 20, posX - 20, posY + 20, posX - 10, posY + 20, posX + 20, posY - 10, posX + 20, posY - 20, posX + 20, posY - 20, posX + 10, posY - 20, posX + 20, posY + 10, posX + 20, posY + 20, posX + 20, posY + 20, posX + 10, posY + 20 };
        drawRect(posX - 20, posY + 20 + 1, posX - 20 + 40, posY + 20 + 1 + 1 + 3 + 1, color);
        final float lock = cntLock / (float)cntMax;
        drawRect(posX - 20 + 1, posY + 20 + 1 + 1, posX - 20 + 1 + (int)(38.0 * lock), posY + 20 + 1 + 1 + 3, -2161656);
    }
    
    void drawRange(final EntityPlayer player, final MCH_WeaponGuidanceSystem gs, final boolean canFire, final int color1, final int color2) {
        String msgLockDist = "[--.--]";
        int color3 = color2;
        if (gs.getLockCount() > 0) {
            final Entity target = gs.getLockingEntity();
            if (target != null) {
                final double dx = target.posX - player.posX;
                final double dz = target.posZ - player.posZ;
                msgLockDist = String.format("[%.2f]", Math.sqrt(dx * dx + dz * dz));
                color3 = (canFire ? color1 : color2);
                final MCH_Config config = MCH_MOD.config;
                if (!MCH_Config.HideKeybind.prmBool && gs.isLockComplete()) {
                    final MCH_Config config2 = MCH_MOD.config;
                    final String k = MCH_KeyName.getDescOrName(MCH_Config.KeyAttack.prmInt);
                    this.drawCenteredString("Shot : " + k, this.centerX, this.centerY + 65, -805306369);
                }
            }
        }
        this.drawCenteredString(msgLockDist, this.centerX, this.centerY + 50, color3);
    }
    
    void drawGuiFGM148(final EntityPlayer player, final MCH_WeaponGuidanceSystem gs, final boolean canFire, final ItemStack itemStack) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        double fac = (this.width / 800.0 < this.height / 700.0) ? (this.width / 800.0) : (this.height / 700.0);
        int size = (int)(1024.0 * fac);
        size = size / 64 * 64;
        fac = size / 1024.0;
        final double left = -(size - this.width) / 2;
        final double top = -(size - this.height) / 2 - 20;
        final double right = left + size;
        final double bottom = top + size;
        Vec3 pos = MCH_ClientLightWeaponTickHandler.getMartEntityPos();
        if (gs.getLockCount() > 0) {
            final int scale = (MCH_GuiLightWeapon.scaleFactor > 0) ? MCH_GuiLightWeapon.scaleFactor : 2;
            if (pos == null) {
                pos = Vec3.createVectorHelper((double)(this.width / 2 * scale), (double)(this.height / 2 * scale), 0.0);
            }
            final double IX = 280.0 * fac;
            final double IY = 370.0 * fac;
            final double cx = pos.xCoord / scale;
            final double cy = this.height - pos.yCoord / scale;
            final double sx = MCH_Lib.RNG(cx, left + IX, right - IX);
            final double sy = MCH_Lib.RNG(cy, top + IY, bottom - IY);
            if (gs.getLockCount() >= gs.getLockCountMax() / 2) {
                this.drawLine(new double[] { -1.0, sy, this.width + 1, sy, sx, -1.0, sx, this.height + 1 }, -1593835521);
            }
            if (player.ticksExisted % 6 >= 3) {
                pos = MCH_ClientLightWeaponTickHandler.getMartEntityBBPos();
                if (pos == null) {
                    pos = Vec3.createVectorHelper((double)((this.width / 2 - 65) * scale), (double)((this.height / 2 + 50) * scale), 0.0);
                }
                final double bx = pos.xCoord / scale;
                final double by = this.height - pos.yCoord / scale;
                double dx = Math.abs(cx - bx);
                double dy = Math.abs(cy - by);
                final double p = 1.0 - gs.getLockCount() / (double)gs.getLockCountMax();
                dx = MCH_Lib.RNG(dx, 25.0, 70.0);
                dy = MCH_Lib.RNG(dy, 15.0, 70.0);
                dx += (70.0 - dx) * p;
                dy += (70.0 - dy) * p;
                final int lx = 10;
                final int ly = 6;
                this.drawLine(new double[] { sx - dx, sy - dy + ly, sx - dx, sy - dy, sx - dx + lx, sy - dy }, -1593835521, 3);
                this.drawLine(new double[] { sx + dx, sy - dy + ly, sx + dx, sy - dy, sx + dx - lx, sy - dy }, -1593835521, 3);
                dy /= 6.0;
                this.drawLine(new double[] { sx - dx, sy + dy - ly, sx - dx, sy + dy, sx - dx + lx, sy + dy }, -1593835521, 3);
                this.drawLine(new double[] { sx + dx, sy + dy - ly, sx + dx, sy + dy, sx + dx - lx, sy + dy }, -1593835521, 3);
            }
        }
        drawRect(-1, -1, (int)left + 1, this.height + 1, -16777216);
        drawRect((int)right - 1, -1, this.width + 1, this.height + 1, -16777216);
        drawRect(-1, -1, this.width + 1, (int)top + 1, -16777216);
        drawRect(-1, (int)bottom - 1, this.width + 1, this.height + 1, -16777216);
        GL11.glEnable(3042);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        W_McClient.MOD_bindTexture("textures/gui/javelin.png");
        this.drawTexturedModalRectRotate(left, top, (double)size, (double)size, 0.0, 0.0, 256.0, 256.0, 0.0f);
        W_McClient.MOD_bindTexture("textures/gui/javelin2.png");
        final PotionEffect pe = player.getActivePotionEffect(Potion.nightVision);
        if (pe == null) {
            final double x = 247.0;
            final double y = 211.0;
            final double w = 380.0;
            final double h = 350.0;
            this.drawTexturedRect(left + x * fac, top + y * fac, (w - x) * fac, (h - y) * fac, x, y, w - x, h - y, 1024.0, 1024.0);
        }
        if (player.getItemInUseDuration() <= 60) {
            final double x = 130.0;
            final double y = 334.0;
            final double w = 257.0;
            final double h = 455.0;
            this.drawTexturedRect(left + x * fac, top + y * fac, (w - x) * fac, (h - y) * fac, x, y, w - x, h - y, 1024.0, 1024.0);
        }
        if (MCH_ClientLightWeaponTickHandler.selectedZoom == 0) {
            final double x = 387.0;
            final double y = 211.0;
            final double w = 510.0;
            final double h = 350.0;
            this.drawTexturedRect(left + x * fac, top + y * fac, (w - x) * fac, (h - y) * fac, x, y, w - x, h - y, 1024.0, 1024.0);
        }
        if (MCH_ClientLightWeaponTickHandler.selectedZoom == MCH_ClientLightWeaponTickHandler.weapon.getInfo().zoom.length - 1) {
            final double x = 511.0;
            final double y = 211.0;
            final double w = 645.0;
            final double h = 350.0;
            this.drawTexturedRect(left + x * fac, top + y * fac, (w - x) * fac, (h - y) * fac, x, y, w - x, h - y, 1024.0, 1024.0);
        }
        if (gs.getLockCount() > 0) {
            final double x = 643.0;
            final double y = 211.0;
            final double w = 775.0;
            final double h = 350.0;
            this.drawTexturedRect(left + x * fac, top + y * fac, (w - x) * fac, (h - y) * fac, x, y, w - x, h - y, 1024.0, 1024.0);
        }
        if (MCH_ClientLightWeaponTickHandler.weaponMode == 1) {
            final double x = 768.0;
            final double y = 340.0;
            final double w = 890.0;
            final double h = 455.0;
            this.drawTexturedRect(left + x * fac, top + y * fac, (w - x) * fac, (h - y) * fac, x, y, w - x, h - y, 1024.0, 1024.0);
        }
        else {
            final double x = 768.0;
            final double y = 456.0;
            final double w = 890.0;
            final double h = 565.0;
            this.drawTexturedRect(left + x * fac, top + y * fac, (w - x) * fac, (h - y) * fac, x, y, w - x, h - y, 1024.0, 1024.0);
        }
        if (!canFire) {
            final double x = 379.0;
            final double y = 670.0;
            final double w = 511.0;
            final double h = 810.0;
            this.drawTexturedRect(left + x * fac, top + y * fac, (w - x) * fac, (h - y) * fac, x, y, w - x, h - y, 1024.0, 1024.0);
        }
        if (itemStack.getMetadata() >= itemStack.getMaxDurability()) {
            final double x = 512.0;
            final double y = 670.0;
            final double w = 645.0;
            final double h = 810.0;
            this.drawTexturedRect(left + x * fac, top + y * fac, (w - x) * fac, (h - y) * fac, x, y, w - x, h - y, 1024.0, 1024.0);
        }
        if (gs.getLockCount() < gs.getLockCountMax()) {
            final double x = 646.0;
            final double y = 670.0;
            final double w = 776.0;
            final double h = 810.0;
            this.drawTexturedRect(left + x * fac, top + y * fac, (w - x) * fac, (h - y) * fac, x, y, w - x, h - y, 1024.0, 1024.0);
        }
        if (pe != null) {
            final double x = 768.0;
            final double y = 562.0;
            final double w = 890.0;
            final double h = 694.0;
            this.drawTexturedRect(left + x * fac, top + y * fac, (w - x) * fac, (h - y) * fac, x, y, w - x, h - y, 1024.0, 1024.0);
        }
    }
    
    public void drawKeyBind(final int color, final boolean canSwitchMode) {
        int OffX = this.centerX + 55;
        final int OffY = this.centerY + 40;
        this.drawString("CAM MODE :", OffX, OffY + 10, color);
        this.drawString("ZOOM      :", OffX, OffY + 20, color);
        if (canSwitchMode) {
            this.drawString("MODE      :", OffX, OffY + 30, color);
        }
        OffX += 60;
        final MCH_Config config = MCH_MOD.config;
        this.drawString(MCH_KeyName.getDescOrName(MCH_Config.KeyCameraMode.prmInt), OffX, OffY + 10, color);
        final MCH_Config config2 = MCH_MOD.config;
        this.drawString(MCH_KeyName.getDescOrName(MCH_Config.KeyZoom.prmInt), OffX, OffY + 20, color);
        if (canSwitchMode) {
            final MCH_Config config3 = MCH_MOD.config;
            this.drawString(MCH_KeyName.getDescOrName(MCH_Config.KeySwWeaponMode.prmInt), OffX, OffY + 30, color);
        }
    }
}
