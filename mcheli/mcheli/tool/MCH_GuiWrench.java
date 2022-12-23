//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.tool;

import mcheli.gui.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import org.lwjgl.opengl.*;
import mcheli.aircraft.*;

@SideOnly(Side.CLIENT)
public class MCH_GuiWrench extends MCH_Gui
{
    public MCH_GuiWrench(final Minecraft minecraft) {
        super(minecraft);
    }
    
    public void initGui() {
        super.initGui();
    }
    
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    public boolean isDrawGui(final EntityPlayer player) {
        return player != null && player.worldObj != null && player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof MCH_ItemWrench;
    }
    
    public void drawGui(final EntityPlayer player, final boolean isThirdPersonView) {
        if (isThirdPersonView) {
            return;
        }
        GL11.glLineWidth((float)MCH_GuiWrench.scaleFactor);
        if (!this.isDrawGui(player)) {
            return;
        }
        GL11.glDisable(3042);
        final MCH_EntityAircraft ac = ((MCH_ItemWrench)player.getCurrentEquippedItem().getItem()).getMouseOverAircraft(player);
        if (ac != null && ac.getMaxHP() > 0) {
            final int color = (ac.getHP() / (double)ac.getMaxHP() > 0.3) ? -14101432 : -2161656;
            this.drawHP(color, -15433180, ac.getHP(), ac.getMaxHP());
        }
    }
    
    void drawHP(final int color, final int colorBG, int hp, final int hpmax) {
        final int posX = this.centerX;
        final int posY = this.centerY + 20;
        final int WID = 20;
        final int INV = 10;
        drawRect(posX - 20, posY + 20 + 1, posX - 20 + 40, posY + 20 + 1 + 1 + 3 + 1, colorBG);
        if (hp > hpmax) {
            hp = hpmax;
        }
        final float hpp = hp / (float)hpmax;
        drawRect(posX - 20 + 1, posY + 20 + 1 + 1, posX - 20 + 1 + (int)(38.0 * hpp), posY + 20 + 1 + 1 + 3, color);
        int hppn = (int)(hpp * 100.0f);
        if (hp < hpmax && hppn >= 100) {
            hppn = 99;
        }
        this.drawCenteredString(String.format("%d %%", hppn), posX, posY + 30, color);
    }
}
