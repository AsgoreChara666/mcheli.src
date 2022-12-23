//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.uav;

import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;

public class MCH_ContainerUavStation extends Container
{
    protected MCH_EntityUavStation uavStation;
    
    public MCH_ContainerUavStation(final InventoryPlayer inventoryPlayer, final MCH_EntityUavStation te) {
        this.uavStation = te;
        this.addSlotToContainer(new Slot((IInventory)this.uavStation, 0, 20, 20));
        this.bindPlayerInventory(inventoryPlayer);
    }
    
    public boolean canInteractWith(final EntityPlayer player) {
        return this.uavStation.isUseableByPlayer(player);
    }
    
    public void onCraftMatrixChanged(final IInventory par1IInventory) {
        super.onCraftMatrixChanged(par1IInventory);
    }
    
    protected void bindPlayerInventory(final InventoryPlayer inventoryPlayer) {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot((IInventory)inventoryPlayer, 9 + (j + i * 9), 8 + j * 18, 84 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot((IInventory)inventoryPlayer, i, 8 + i * 18, 142));
        }
    }
    
    public ItemStack transferStackInSlot(final EntityPlayer player, final int slot) {
        return null;
    }
}
