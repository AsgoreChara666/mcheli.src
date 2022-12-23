//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.gui;

import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;

public class MCH_ConfigGuiContainer extends Container
{
    public final EntityPlayer player;
    
    public MCH_ConfigGuiContainer(final EntityPlayer player) {
        this.player = player;
    }
    
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
    }
    
    public boolean canInteractWith(final EntityPlayer player) {
        return true;
    }
    
    public ItemStack transferStackInSlot(final EntityPlayer par1EntityPlayer, final int par2) {
        return null;
    }
}
