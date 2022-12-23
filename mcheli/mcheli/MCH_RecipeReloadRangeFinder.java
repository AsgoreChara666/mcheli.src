//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli;

import net.minecraft.item.crafting.*;
import net.minecraft.inventory.*;
import net.minecraft.world.*;
import mcheli.tool.rangefinder.*;
import net.minecraft.item.*;

public class MCH_RecipeReloadRangeFinder implements IRecipe
{
    public boolean matches(final InventoryCrafting inv, final World var2) {
        int jcnt = 0;
        int ccnt = 0;
        for (int i = 0; i < inv.getSizeInventory(); ++i) {
            final ItemStack is = inv.getStackInSlot(i);
            if (is != null) {
                if (is.getItem() instanceof MCH_ItemRangeFinder) {
                    if (is.getMetadata() == 0) {
                        return false;
                    }
                    if (++jcnt > 1) {
                        return false;
                    }
                }
                else {
                    if (!(is.getItem() instanceof ItemRedstone) || is.stackSize <= 0) {
                        return false;
                    }
                    if (++ccnt > 1) {
                        return false;
                    }
                }
            }
        }
        return jcnt == 1 && ccnt > 0;
    }
    
    public ItemStack getCraftingResult(final InventoryCrafting inv) {
        final ItemStack output = new ItemStack((Item)MCH_MOD.itemRangeFinder);
        return output;
    }
    
    public int getRecipeSize() {
        return 9;
    }
    
    public ItemStack getRecipeOutput() {
        return null;
    }
}
