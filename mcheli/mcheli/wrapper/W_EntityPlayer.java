//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.wrapper;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.entity.item.*;

public abstract class W_EntityPlayer extends EntityPlayer
{
    public W_EntityPlayer(final World par1World, final EntityPlayer player) {
        super(par1World, player.getGameProfile());
    }
    
    public static void closeScreen(final Entity p) {
        if (p != null) {
            if (p.worldObj.isRemote) {
                W_EntityPlayerSP.closeScreen(p);
            }
            else if (p instanceof EntityPlayerMP) {
                ((EntityPlayerMP)p).closeScreen();
            }
        }
    }
    
    public static boolean hasItem(final EntityPlayer player, final Item item) {
        return item != null && player.inventory.hasItem(item);
    }
    
    public static boolean consumeInventoryItem(final EntityPlayer player, final Item item) {
        return item != null && player.inventory.consumeInventoryItem(item);
    }
    
    public static void addChatMessage(final EntityPlayer player, final String s) {
        player.addChatMessage((IChatComponent)new ChatComponentText(s));
    }
    
    public static EntityItem dropPlayerItemWithRandomChoice(final EntityPlayer player, final ItemStack item, final boolean b1, final boolean b2) {
        return player.func_146097_a(item, b1, b2);
    }
    
    public static boolean isPlayer(final Entity entity) {
        return entity instanceof EntityPlayer;
    }
}
