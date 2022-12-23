//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.gui;

import mcheli.wrapper.*;
import net.minecraft.client.*;
import org.lwjgl.opengl.*;
import mcheli.*;
import net.minecraft.util.*;

public class MCH_GuiSlider extends W_GuiButton
{
    private float currentSlider;
    private boolean isMousePress;
    public String stringFormat;
    public float valueMin;
    public float valueMax;
    public float valueStep;
    
    public MCH_GuiSlider(final int gui_id, final int posX, final int posY, final int sliderWidth, final int sliderHeight, final String string_format, final float defaultSliderPos, final float minVal, final float maxVal, final float step) {
        super(gui_id, posX, posY, sliderWidth, sliderHeight, "");
        this.valueMin = 0.0f;
        this.valueMax = 1.0f;
        this.valueStep = 0.1f;
        this.stringFormat = string_format;
        this.valueMin = minVal;
        this.valueMax = maxVal;
        this.valueStep = step;
        this.setSliderValue(defaultSliderPos);
    }
    
    public int getHoverState(final boolean mouseOver) {
        return 0;
    }
    
    protected void mouseDragged(final Minecraft mc, final int x, final int y) {
        if (this.isVisible()) {
            if (this.isMousePress) {
                this.currentSlider = (x - (this.xPosition + 4)) / (float)(this.width - 8);
                if (this.currentSlider < 0.0f) {
                    this.currentSlider = 0.0f;
                }
                if (this.currentSlider > 1.0f) {
                    this.currentSlider = 1.0f;
                }
                this.currentSlider = this.normalizeValue(this.denormalizeValue(this.currentSlider));
                this.updateDisplayString();
            }
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.drawTexturedModalRect(this.xPosition + (int)(this.currentSlider * (this.width - 8)), this.yPosition, 0, 66, 4, 20);
            this.drawTexturedModalRect(this.xPosition + (int)(this.currentSlider * (this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
            if (!MCH_Key.isKeyDown(-100)) {
                this.mouseReleased(x, y);
            }
        }
    }
    
    public void updateDisplayString() {
        this.displayString = String.format(this.stringFormat, this.denormalizeValue(this.currentSlider));
    }
    
    public void setSliderValue(final float f) {
        this.currentSlider = this.normalizeValue(f);
        this.updateDisplayString();
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
            this.currentSlider = (x - (this.xPosition + 4)) / (float)(this.width - 8);
            if (this.currentSlider < 0.0f) {
                this.currentSlider = 0.0f;
            }
            if (this.currentSlider > 1.0f) {
                this.currentSlider = 1.0f;
            }
            this.updateDisplayString();
            return this.isMousePress = true;
        }
        return false;
    }
    
    public void mouseReleased(final int mouseX, final int mouseY) {
        this.isMousePress = false;
    }
}
