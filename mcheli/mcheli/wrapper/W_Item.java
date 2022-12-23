//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.wrapper;

import net.minecraft.item.*;
import net.minecraft.block.*;

public class W_Item extends Item
{
    public W_Item(final int par1) {
    }
    
    public W_Item() {
    }
    
    public static int getIdFromItem(final Item i) {
        return (i == null) ? 0 : W_Item.itemRegistry.getIDForObject((Object)i);
    }
    
    public Item setTexture(final String par1Str) {
        this.setTextureName(W_MOD.DOMAIN + ":" + par1Str);
        return this;
    }
    
    public static Item getItemById(final int i) {
        return Item.getItemById(i);
    }
    
    public static Item getItemByName(String nm) {
        if (nm.indexOf(58) < 0) {
            nm = "minecraft:" + nm;
        }
        return (Item)Item.itemRegistry.getObject(nm);
    }
    
    public static String getNameForItem(final Item item) {
        return Item.itemRegistry.getNameForObject((Object)item);
    }
    
    public static Item getItemFromBlock(final Block block) {
        return Item.getItemFromBlock(block);
    }
}
