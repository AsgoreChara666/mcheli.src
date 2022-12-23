//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.wrapper;

import net.minecraft.client.gui.inventory.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.gui.*;

public abstract class W_GuiContainer extends GuiContainer
{
    public W_GuiContainer(final Container par1Container) {
        super(par1Container);
    }
    
    public void drawItemStack(final ItemStack item, final int x, final int y) {
        if (item == null) {
            return;
        }
        if (item.getItem() == null) {
            return;
        }
        FontRenderer font = item.getItem().getFontRenderer(item);
        if (font == null) {
            font = this.fontRendererObj;
        }
        GL11.glEnable(2929);
        GL11.glEnable(2896);
        W_GuiContainer.itemRender.renderItemAndEffectIntoGUI(font, this.mc.getTextureManager(), item, x, y);
        W_GuiContainer.itemRender.renderItemOverlayIntoGUI(font, this.mc.getTextureManager(), item, x, y, (String)null);
        this.zLevel = 0.0f;
        W_GuiContainer.itemRender.zLevel = 0.0f;
    }
    
    public void drawString(final String s, final int x, final int y, final int color) {
        this.drawString(this.fontRendererObj, s, x, y, color);
    }
    
    public void drawCenteredString(final String s, final int x, final int y, final int color) {
        this.drawCenteredString(this.fontRendererObj, s, x, y, color);
    }
    
    public int getStringWidth(final String s) {
        return this.fontRendererObj.getStringWidth(s);
    }
}
