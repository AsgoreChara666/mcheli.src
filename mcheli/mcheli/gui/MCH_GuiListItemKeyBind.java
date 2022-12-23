//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.gui;

import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import mcheli.*;

public class MCH_GuiListItemKeyBind extends MCH_GuiListItem
{
    public String displayString;
    public GuiButton button;
    public GuiButton buttonReset;
    public int keycode;
    public final int defaultKeycode;
    public MCH_ConfigPrm config;
    public GuiButton lastPushButton;
    
    public MCH_GuiListItemKeyBind(final int id, final int idReset, final int posX, final String dispStr, final MCH_ConfigPrm prm) {
        this.displayString = dispStr;
        this.defaultKeycode = prm.prmIntDefault;
        this.button = new GuiButton(id, posX + 160, 0, 70, 20, "");
        this.buttonReset = new GuiButton(idReset, posX + 240, 0, 40, 20, "Reset");
        this.config = prm;
        this.lastPushButton = null;
        this.setKeycode(prm.prmInt);
    }
    
    public void mouseReleased(final int x, final int y) {
        this.button.mouseReleased(x, y);
        this.buttonReset.mouseReleased(x, y);
    }
    
    public boolean mousePressed(final Minecraft mc, final int x, final int y) {
        if (this.button.mousePressed(mc, x, y)) {
            this.lastPushButton = this.button;
            return true;
        }
        if (this.buttonReset.mousePressed(mc, x, y)) {
            this.lastPushButton = this.buttonReset;
            return true;
        }
        return false;
    }
    
    public void draw(final Minecraft mc, final int mouseX, final int mouseY, final int posX, final int posY) {
        final int y = 6;
        this.button.drawString(mc.fontRendererObj, this.displayString, posX + 10, posY + y, -1);
        this.button.yPosition = posY;
        this.button.drawButton(mc, mouseX, mouseY);
        this.buttonReset.enabled = (this.keycode != this.defaultKeycode);
        this.buttonReset.yPosition = posY;
        this.buttonReset.drawButton(mc, mouseX, mouseY);
    }
    
    public void applyKeycode() {
        this.config.setPrm(this.keycode);
    }
    
    public void resetKeycode() {
        this.setKeycode(this.defaultKeycode);
    }
    
    public void setKeycode(final int k) {
        if (k != 0 && !MCH_KeyName.getDescOrName(k).isEmpty()) {
            this.keycode = k;
            this.button.displayString = MCH_KeyName.getDescOrName(k);
        }
    }
}
