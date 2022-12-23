//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.aircraft;

import mcheli.wrapper.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;

public class MCH_ItemFuel extends W_Item
{
    public MCH_ItemFuel(final int itemID) {
        super(itemID);
        this.setMaxDurability(600);
        this.setMaxStackSize(1);
        this.setNoRepair();
        this.setFull3D();
    }
    
    public ItemStack onItemRightClick(final ItemStack stack, final World world, final EntityPlayer player) {
        final int damage = stack.getMetadata();
        if (!world.isRemote && stack.isItemDamaged() && !player.capabilities.isCreativeMode) {
            this.refuel(stack, player, 1);
            this.refuel(stack, player, 0);
        }
        return stack;
    }
    
    private void refuel(final ItemStack stack, final EntityPlayer player, final int coalType) {
        final ItemStack[] list = player.inventory.mainInventory;
        for (int i = 0; i < list.length; ++i) {
            final ItemStack is = list[i];
            if (is != null && is.getItem() instanceof ItemCoal && is.getMetadata() == coalType) {
                for (int j = 0; is.stackSize > 0 && stack.isItemDamaged() && j < 64; ++j) {
                    int damage = stack.getMetadata() - ((coalType == 1) ? 75 : 100);
                    if (damage < 0) {
                        damage = 0;
                    }
                    stack.setMetadata(damage);
                    final ItemStack itemStack = is;
                    --itemStack.stackSize;
                }
                if (is.stackSize <= 0) {
                    list[i] = null;
                }
            }
        }
    }
}
