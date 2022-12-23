//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.aircraft;

import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import mcheli.uav.*;
import net.minecraft.item.*;
import mcheli.*;
import mcheli.parachute.*;

public class MCH_AircraftGuiContainer extends Container
{
    public final EntityPlayer player;
    public final MCH_EntityAircraft aircraft;
    
    public MCH_AircraftGuiContainer(final EntityPlayer player, final MCH_EntityAircraft ac) {
        this.player = player;
        this.aircraft = ac;
        final MCH_AircraftInventory guiInventory;
        final MCH_AircraftInventory iv = guiInventory = this.aircraft.getGuiInventory();
        iv.getClass();
        this.addSlotToContainer(new Slot((IInventory)guiInventory, 0, 10, 30));
        final MCH_AircraftInventory mch_AircraftInventory = iv;
        iv.getClass();
        this.addSlotToContainer(new Slot((IInventory)mch_AircraftInventory, 1, 10, 48));
        final MCH_AircraftInventory mch_AircraftInventory2 = iv;
        iv.getClass();
        this.addSlotToContainer(new Slot((IInventory)mch_AircraftInventory2, 2, 10, 66));
        for (int num = this.aircraft.getNumEjectionSeat(), i = 0; i < num; ++i) {
            final MCH_AircraftInventory mch_AircraftInventory3 = iv;
            iv.getClass();
            this.addSlotToContainer(new Slot((IInventory)mch_AircraftInventory3, 3 + i, 10 + 18 * i, 105));
        }
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 9; ++x) {
                this.addSlotToContainer(new Slot((IInventory)player.inventory, 9 + x + y * 9, 25 + x * 18, 135 + y * 18));
            }
        }
        for (int x2 = 0; x2 < 9; ++x2) {
            this.addSlotToContainer(new Slot((IInventory)player.inventory, x2, 25 + x2 * 18, 195));
        }
    }
    
    public int getInventoryStartIndex() {
        if (this.aircraft == null) {
            return 3;
        }
        return 3 + this.aircraft.getNumEjectionSeat();
    }
    
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
    }
    
    public boolean canInteractWith(final EntityPlayer player) {
        if (this.aircraft.getGuiInventory().isUseableByPlayer(player)) {
            return true;
        }
        if (this.aircraft.isUAV()) {
            final MCH_EntityUavStation us = this.aircraft.getUavStation();
            if (us != null) {
                final double x = us.posX + us.posUavX;
                final double z = us.posZ + us.posUavZ;
                if (this.aircraft.posX < x + 10.0 && this.aircraft.posX > x - 10.0 && this.aircraft.posZ < z + 10.0 && this.aircraft.posZ > z - 10.0) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public ItemStack transferStackInSlot(final EntityPlayer player, final int slotIndex) {
        final MCH_AircraftInventory iv = this.aircraft.getGuiInventory();
        final Slot slot = this.inventorySlots.get(slotIndex);
        if (slot == null) {
            return null;
        }
        final ItemStack itemStack = slot.getStack();
        MCH_Lib.DbgLog(player.worldObj, "transferStackInSlot : %d :" + itemStack, slotIndex);
        if (itemStack == null) {
            return null;
        }
        if (slotIndex < this.getInventoryStartIndex()) {
            for (int i = this.getInventoryStartIndex(); i < this.inventorySlots.size(); ++i) {
                final Slot playerSlot = this.inventorySlots.get(i);
                if (playerSlot.getStack() == null) {
                    playerSlot.putStack(itemStack);
                    slot.putStack((ItemStack)null);
                    return itemStack;
                }
            }
        }
        else if (itemStack.getItem() instanceof MCH_ItemFuel) {
            for (int i = 0; i < 3; ++i) {
                if (iv.getFuelSlotItemStack(i) == null) {
                    final MCH_AircraftInventory mch_AircraftInventory = iv;
                    iv.getClass();
                    mch_AircraftInventory.setInventorySlotContents(0 + i, itemStack);
                    slot.putStack((ItemStack)null);
                    return itemStack;
                }
            }
        }
        else if (itemStack.getItem() instanceof MCH_ItemParachute) {
            for (int num = this.aircraft.getNumEjectionSeat(), j = 0; j < num; ++j) {
                if (iv.getParachuteSlotItemStack(j) == null) {
                    final MCH_AircraftInventory mch_AircraftInventory2 = iv;
                    iv.getClass();
                    mch_AircraftInventory2.setInventorySlotContents(3 + j, itemStack);
                    slot.putStack((ItemStack)null);
                    return itemStack;
                }
            }
        }
        return null;
    }
    
    public void onContainerClosed(final EntityPlayer player) {
        super.onContainerClosed(player);
        if (!player.worldObj.isRemote) {
            final MCH_AircraftInventory iv = this.aircraft.getGuiInventory();
            for (int i = 0; i < 3; ++i) {
                final ItemStack is = iv.getFuelSlotItemStack(i);
                if (is != null && !(is.getItem() instanceof MCH_ItemFuel)) {
                    iv.getClass();
                    this.dropPlayerItem(player, 0 + i);
                }
            }
            for (int i = 0; i < 2; ++i) {
                final ItemStack is = iv.getParachuteSlotItemStack(i);
                if (is != null && !(is.getItem() instanceof MCH_ItemParachute)) {
                    iv.getClass();
                    this.dropPlayerItem(player, 3 + i);
                }
            }
        }
    }
    
    public void dropPlayerItem(final EntityPlayer player, final int slotID) {
        if (!player.worldObj.isRemote) {
            final ItemStack itemstack = this.aircraft.getGuiInventory().getStackInSlotOnClosing(slotID);
            if (itemstack != null) {
                player.dropPlayerItemWithRandomChoice(itemstack, false);
            }
        }
    }
}
