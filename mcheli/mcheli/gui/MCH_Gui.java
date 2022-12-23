//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.gui;

import cpw.mods.fml.relauncher.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import mcheli.wrapper.*;
import java.util.*;

@SideOnly(Side.CLIENT)
public abstract class MCH_Gui extends GuiScreen
{
    protected int centerX;
    protected int centerY;
    protected Random rand;
    protected float smoothCamPartialTicks;
    public static int scaleFactor;
    
    public MCH_Gui(final Minecraft minecraft) {
        this.centerX = 0;
        this.centerY = 0;
        this.rand = new Random();
        this.mc = minecraft;
        this.smoothCamPartialTicks = 0.0f;
        this.zLevel = -110.0f;
    }
    
    public void initGui() {
        super.initGui();
    }
    
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    public void onTick() {
    }
    
    public abstract boolean isDrawGui(final EntityPlayer p0);
    
    public abstract void drawGui(final EntityPlayer p0, final boolean p1);
    
    public void drawScreen(final int par1, final int par2, final float partialTicks) {
        this.smoothCamPartialTicks = partialTicks;
        final ScaledResolution scaledresolution = new W_ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        MCH_Gui.scaleFactor = scaledresolution.getScaleFactor();
        if (!this.mc.gameSettings.hideGUI) {
            this.width = this.mc.displayWidth / MCH_Gui.scaleFactor;
            this.height = this.mc.displayHeight / MCH_Gui.scaleFactor;
            this.centerX = this.width / 2;
            this.centerY = this.height / 2;
            GL11.glPushMatrix();
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            if (this.mc.thePlayer != null) {
                this.drawGui((EntityPlayer)this.mc.thePlayer, this.mc.gameSettings.thirdPersonView != 0);
            }
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glPopMatrix();
        }
    }
    
    public void drawTexturedModalRectRotate(final double left, final double top, final double width, final double height, final double uLeft, final double vTop, final double uWidth, final double vHeight, final float rot) {
        GL11.glPushMatrix();
        GL11.glTranslated(left + width / 2.0, top + height / 2.0, 0.0);
        GL11.glRotatef(rot, 0.0f, 0.0f, 1.0f);
        final float f = 0.00390625f;
        final Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(-width / 2.0, height / 2.0, (double)this.zLevel, uLeft * 0.00390625, (vTop + vHeight) * 0.00390625);
        tessellator.addVertexWithUV(width / 2.0, height / 2.0, (double)this.zLevel, (uLeft + uWidth) * 0.00390625, (vTop + vHeight) * 0.00390625);
        tessellator.addVertexWithUV(width / 2.0, -height / 2.0, (double)this.zLevel, (uLeft + uWidth) * 0.00390625, vTop * 0.00390625);
        tessellator.addVertexWithUV(-width / 2.0, -height / 2.0, (double)this.zLevel, uLeft * 0.00390625, vTop * 0.00390625);
        tessellator.draw();
        GL11.glPopMatrix();
    }
    
    public void drawTexturedRect(final double left, final double top, final double width, final double height, final double uLeft, final double vTop, final double uWidth, final double vHeight, final double textureWidth, final double textureHeight) {
        final float fx = (float)(1.0 / textureWidth);
        final float fy = (float)(1.0 / textureHeight);
        final Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(left, top + height, (double)this.zLevel, uLeft * fx, (vTop + vHeight) * fy);
        tessellator.addVertexWithUV(left + width, top + height, (double)this.zLevel, (uLeft + uWidth) * fx, (vTop + vHeight) * fy);
        tessellator.addVertexWithUV(left + width, top, (double)this.zLevel, (uLeft + uWidth) * fx, vTop * fy);
        tessellator.addVertexWithUV(left, top, (double)this.zLevel, uLeft * fx, vTop * fy);
        tessellator.draw();
    }
    
    public void drawLineStipple(final double[] line, final int color, final int factor, final int pattern) {
        GL11.glEnable(2852);
        GL11.glLineStipple(factor, (short)pattern);
        this.drawLine(line, color);
        GL11.glDisable(2852);
    }
    
    public void drawLine(final double[] line, final int color) {
        this.drawLine(line, color, 1);
    }
    
    public void drawString(final String s, final int x, final int y, final int color) {
        this.drawString(this.mc.fontRendererObj, s, x, y, color);
    }
    
    public void drawDigit(final String s, final int x, final int y, final int interval, final int color) {
        GL11.glEnable(3042);
        GL11.glColor4ub((byte)(color >> 16 & 0xFF), (byte)(color >> 8 & 0xFF), (byte)(color & 0xFF), (byte)(color >> 24 & 0xFF));
        final int srcBlend = GL11.glGetInteger(3041);
        final int dstBlend = GL11.glGetInteger(3040);
        GL11.glBlendFunc(770, 771);
        W_McClient.MOD_bindTexture("textures/gui/digit.png");
        for (int i = 0; i < s.length(); ++i) {
            final char c = s.charAt(i);
            if (c >= '0' && c <= '9') {
                this.drawTexturedModalRect(x + interval * i, y, 16 * (c - '0'), 0, 16, 16);
            }
            if (c == '-') {
                this.drawTexturedModalRect(x + interval * i, y, 160, 0, 16, 16);
            }
        }
        GL11.glBlendFunc(srcBlend, dstBlend);
        GL11.glDisable(3042);
    }
    
    public void drawCenteredString(final String s, final int x, final int y, final int color) {
        this.drawCenteredString(this.mc.fontRendererObj, s, x, y, color);
    }
    
    public void drawLine(final double[] line, final int color, final int mode) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4ub((byte)(color >> 16 & 0xFF), (byte)(color >> 8 & 0xFF), (byte)(color >> 0 & 0xFF), (byte)(color >> 24 & 0xFF));
        final Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawing(mode);
        for (int i = 0; i < line.length; i += 2) {
            tessellator.addVertex(line[i + 0], line[i + 1], (double)this.zLevel);
        }
        tessellator.draw();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glColor4b((byte)(-1), (byte)(-1), (byte)(-1), (byte)(-1));
        GL11.glPopMatrix();
    }
    
    public void drawPoints(final double[] points, final int color, final int pointWidth) {
        final int prevWidth = GL11.glGetInteger(2833);
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4ub((byte)(color >> 16 & 0xFF), (byte)(color >> 8 & 0xFF), (byte)(color >> 0 & 0xFF), (byte)(color >> 24 & 0xFF));
        GL11.glPointSize((float)pointWidth);
        final Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawing(0);
        for (int i = 0; i < points.length; i += 2) {
            tessellator.addVertex(points[i], points[i + 1], 0.0);
        }
        tessellator.draw();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
        GL11.glColor4b((byte)(-1), (byte)(-1), (byte)(-1), (byte)(-1));
        GL11.glPointSize((float)prevWidth);
    }
    
    public void drawPoints(final ArrayList<Double> points, final int color, final int pointWidth) {
        final int prevWidth = GL11.glGetInteger(2833);
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4ub((byte)(color >> 16 & 0xFF), (byte)(color >> 8 & 0xFF), (byte)(color >> 0 & 0xFF), (byte)(color >> 24 & 0xFF));
        GL11.glPointSize((float)pointWidth);
        final Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawing(0);
        for (int i = 0; i < points.size(); i += 2) {
            tessellator.addVertex((double)points.get(i), (double)points.get(i + 1), 0.0);
        }
        tessellator.draw();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
        GL11.glColor4b((byte)(-1), (byte)(-1), (byte)(-1), (byte)(-1));
        GL11.glPointSize((float)prevWidth);
    }
}
