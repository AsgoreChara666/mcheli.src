//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli;

import mcheli.aircraft.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import org.lwjgl.opengl.*;

@SideOnly(Side.CLIENT)
public class MCH_GuiCommon extends MCH_AircraftCommonGui
{
    public int hitCount;
    
    public MCH_GuiCommon(final Minecraft minecraft) {
        super(minecraft);
        this.hitCount = 0;
    }
    
    public boolean isDrawGui(final EntityPlayer player) {
        return true;
    }
    
    public void drawGui(final EntityPlayer player, final boolean isThirdPersonView) {
        GL11.glLineWidth((float)MCH_GuiCommon.scaleFactor);
        this.drawHitBullet(this.hitCount, 15, -805306369);
    }
    
    public void onTick() {
        super.onTick();
        if (this.hitCount > 0) {
            --this.hitCount;
        }
    }
    
    public void hitBullet() {
        this.hitCount = 15;
    }
}
