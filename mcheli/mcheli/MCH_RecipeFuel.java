//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli;

import net.minecraft.item.crafting.*;
import net.minecraft.inventory.*;
import net.minecraft.world.*;
import mcheli.aircraft.*;
import net.minecraft.item.*;

public class MCH_RecipeFuel implements IRecipe
{
    public boolean matches(final InventoryCrafting inv, final World var2) {
        int jcnt = 0;
        int ccnt = 0;
        for (int i = 0; i < inv.getSizeInventory(); ++i) {
            final ItemStack is = inv.getStackInSlot(i);
            if (is != null) {
                if (is.getItem() instanceof MCH_ItemFuel) {
                    if (is.getMetadata() == 0) {
                        return false;
                    }
                    if (++jcnt > 1) {
                        return false;
                    }
                }
                else {
                    if (!(is.getItem() instanceof ItemCoal) || is.stackSize <= 0) {
                        return false;
                    }
                    ++ccnt;
                }
            }
        }
        return jcnt == 1 && ccnt > 0;
    }
    
    public ItemStack getCraftingResult(final InventoryCrafting inv) {
        final ItemStack output = new ItemStack((Item)MCH_MOD.itemFuel);
        for (int i = 0; i < inv.getSizeInventory(); ++i) {
            final ItemStack is = inv.getStackInSlot(i);
            if (is != null && is.getItem() instanceof MCH_ItemFuel) {
                output.setMetadata(is.getMetadata());
                break;
            }
        }
        for (int i = 0; i < inv.getSizeInventory(); ++i) {
            final ItemStack is = inv.getStackInSlot(i);
            if (is != null && is.getItem() instanceof ItemCoal) {
                int sp = 100;
                if (is.getMetadata() == 1) {
                    sp = 75;
                }
                if (output.getMetadata() > sp) {
                    output.setMetadata(output.getMetadata() - sp);
                }
                else {
                    output.setMetadata(0);
                }
            }
        }
        return output;
    }
    
    public int getRecipeSize() {
        return 9;
    }
    
    public ItemStack getRecipeOutput() {
        return null;
    }
}
