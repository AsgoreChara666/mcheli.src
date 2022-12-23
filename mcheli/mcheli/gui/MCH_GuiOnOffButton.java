//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.gui;

import mcheli.wrapper.*;
import net.minecraft.client.*;

public class MCH_GuiOnOffButton extends W_GuiButton
{
    private boolean statOnOff;
    private final String dispOnOffString;
    
    public MCH_GuiOnOffButton(final int par1, final int par2, final int par3, final int par4, final int par5, final String par6Str) {
        super(par1, par2, par3, par4, par5, "");
        this.dispOnOffString = par6Str;
        this.setOnOff(false);
    }
    
    public void setOnOff(final boolean b) {
        this.statOnOff = b;
        this.displayString = this.dispOnOffString + (this.getOnOff() ? "ON" : "OFF");
    }
    
    public boolean getOnOff() {
        return this.statOnOff;
    }
    
    public void switchOnOff() {
        this.setOnOff(!this.getOnOff());
    }
    
    public boolean mousePressed(final Minecraft mc, final int x, final int y) {
        if (super.mousePressed(mc, x, y)) {
            this.switchOnOff();
            return true;
        }
        return false;
    }
}
