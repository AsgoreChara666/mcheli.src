//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.gui;

import mcheli.wrapper.*;
import net.minecraft.client.*;
import org.lwjgl.opengl.*;
import mcheli.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.*;

public class MCH_GuiSliderVertical extends W_GuiButton
{
    private float currentSlider;
    private boolean isMousePress;
    public float valueMin;
    public float valueMax;
    public float valueStep;
    
    public MCH_GuiSliderVertical(final int gui_id, final int posX, final int posY, final int sliderWidth, final int sliderHeight, final String string, final float defaultSliderPos, final float minVal, final float maxVal, final float step) {
        super(gui_id, posX, posY, sliderWidth, sliderHeight, string);
        this.valueMin = 0.0f;
        this.valueMax = 1.0f;
        this.valueStep = 0.1f;
        this.valueMin = minVal;
        this.valueMax = maxVal;
        this.valueStep = step;
        this.setSliderValue(defaultSliderPos);
    }
    
    public int getHoverState(final boolean mouseOver) {
        return 0;
    }
    
    public void scrollUp(final float a) {
        if (this.isVisible() && !this.isMousePress) {
            this.setSliderValue(this.getSliderValue() + this.valueStep * a);
        }
    }
    
    public void scrollDown(final float a) {
        if (this.isVisible() && !this.isMousePress) {
            this.setSliderValue(this.getSliderValue() - this.valueStep * a);
        }
    }
    
    protected void mouseDragged(final Minecraft mc, final int x, final int y) {
        if (this.isVisible()) {
            if (this.isMousePress) {
                this.currentSlider = (y - (this.yPosition + 4)) / (float)(this.height - 8);
                if (this.currentSlider < 0.0f) {
                    this.currentSlider = 0.0f;
                }
                if (this.currentSlider > 1.0f) {
                    this.currentSlider = 1.0f;
                }
                this.currentSlider = this.normalizeValue(this.denormalizeValue(this.currentSlider));
            }
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.drawTexturedModalRect(this.xPosition, this.yPosition + (int)(this.currentSlider * (this.height - 8)), 66, 0, 20, 4);
            this.drawTexturedModalRect(this.xPosition, this.yPosition + (int)(this.currentSlider * (this.height - 8)) + 4, 66, 196, 20, 4);
            if (!MCH_Key.isKeyDown(-100)) {
                this.mouseReleased(x, y);
            }
        }
    }
    
    public void setSliderValue(final float f) {
        this.currentSlider = this.normalizeValue(f);
    }
    
    public float getSliderValue() {
        return this.denormalizeValue(this.currentSlider);
    }
    
    public float getSliderValueInt(int digit) {
        int d = 1;
        while (digit > 0) {
            d *= 10;
            --digit;
        }
        final int n = (int)(this.denormalizeValue(this.currentSlider) * d);
        return n / (float)d;
    }
    
    public float normalizeValue(final float f) {
        return MathHelper.clamp_float((this.snapToStepClamp(f) - this.valueMin) / (this.valueMax - this.valueMin), 0.0f, 1.0f);
    }
    
    public float denormalizeValue(final float f) {
        return this.snapToStepClamp(this.valueMin + (this.valueMax - this.valueMin) * MathHelper.clamp_float(f, 0.0f, 1.0f));
    }
    
    public float snapToStepClamp(float f) {
        f = this.snapToStep(f);
        return MathHelper.clamp_float(f, this.valueMin, this.valueMax);
    }
    
    protected float snapToStep(float f) {
        if (this.valueStep > 0.0f) {
            f = this.valueStep * Math.round(f / this.valueStep);
        }
        return f;
    }
    
    public boolean mousePressed(final Minecraft mc, final int x, final int y) {
        if (super.mousePressed(mc, x, y)) {
            this.currentSlider = (y - (this.yPosition + 4)) / (float)(this.height - 8);
            if (this.currentSlider < 0.0f) {
                this.currentSlider = 0.0f;
            }
            if (this.currentSlider > 1.0f) {
                this.currentSlider = 1.0f;
            }
            return this.isMousePress = true;
        }
        return false;
    }
    
    public void mouseReleased(final int mouseX, final int mouseY) {
        this.isMousePress = false;
    }
    
    public void drawButton(final Minecraft mc, final int x, final int y) {
        if (this.isVisible()) {
            final FontRenderer fontrenderer = mc.fontRendererObj;
            mc.getTextureManager().bindTexture(new ResourceLocation("mcheli", "textures/gui/widgets.png"));
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.setOnMouseOver(x >= this.xPosition && y >= this.yPosition && x < this.xPosition + this.width && y < this.yPosition + this.height);
            final int k = this.getHoverState(this.isOnMouseOver());
            this.enableBlend();
            this.drawTexturedModalRect(this.xPosition, this.yPosition, 46 + k * 20, 0, this.width, this.height / 2);
            this.drawTexturedModalRect(this.xPosition, this.yPosition + this.height / 2, 46 + k * 20, 200 - this.height / 2, this.width, this.height / 2);
            this.mouseDragged(mc, x, y);
            int l = 14737632;
            if (this.packedFGColour != 0) {
                l = this.packedFGColour;
            }
            else if (!this.enabled) {
                l = 10526880;
            }
            else if (this.isOnMouseOver()) {
                l = 16777120;
            }
            this.drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, l);
            mc.getTextureManager().bindTexture(MCH_GuiSliderVertical.buttonTextures);
        }
    }
}
