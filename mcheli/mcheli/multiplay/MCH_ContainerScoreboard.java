//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.multiplay;

import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;

public class MCH_ContainerScoreboard extends Container
{
    public final EntityPlayer thePlayer;
    
    public MCH_ContainerScoreboard(final EntityPlayer player) {
        this.thePlayer = player;
    }
    
    public boolean canInteractWith(final EntityPlayer player) {
        return true;
    }
}
