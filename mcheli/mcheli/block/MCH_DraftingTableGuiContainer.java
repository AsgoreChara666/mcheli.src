//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.block;

import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.block.*;
import mcheli.wrapper.*;
import net.minecraft.item.*;
import mcheli.*;
import mcheli.helicopter.*;
import mcheli.plane.*;
import mcheli.vehicle.*;
import mcheli.tank.*;
import net.minecraft.item.crafting.*;
import java.util.*;

public class MCH_DraftingTableGuiContainer extends Container
{
    public final EntityPlayer player;
    public final int posX;
    public final int posY;
    public final int posZ;
    public final int outputSlotIndex;
    private IInventory outputSlot;
    
    public MCH_DraftingTableGuiContainer(final EntityPlayer player, final int posX, final int posY, final int posZ) {
        this.outputSlot = (IInventory)new InventoryCraftResult();
        this.player = player;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 9; ++x) {
                this.addSlotToContainer(new Slot((IInventory)player.inventory, 9 + x + y * 9, 30 + x * 18, 140 + y * 18));
            }
        }
        for (int x2 = 0; x2 < 9; ++x2) {
            this.addSlotToContainer(new Slot((IInventory)player.inventory, x2, 30 + x2 * 18, 198));
        }
        this.outputSlotIndex = this.inventoryItemStacks.size();
        final Slot a = new Slot(this.outputSlot, this.outputSlotIndex, 178, 90) {
            public boolean isItemValid(final ItemStack par1ItemStack) {
                return false;
            }
        };
        this.addSlotToContainer(a);
        MCH_Lib.DbgLog(player.worldObj, "MCH_DraftingTableGuiContainer.MCH_DraftingTableGuiContainer", new Object[0]);
    }
    
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
    }
    
    public boolean canInteractWith(final EntityPlayer player) {
        final Block block = W_WorldFunc.getBlock(player.worldObj, this.posX, this.posY, this.posZ);
        return (W_Block.isEqual(block, (Block)MCH_MOD.blockDraftingTable) || W_Block.isEqual(block, (Block)MCH_MOD.blockDraftingTableLit)) && player.getDistanceSq((double)this.posX, (double)this.posY, (double)this.posZ) <= 144.0;
    }
    
    public ItemStack transferStackInSlot(final EntityPlayer player, final int slotIndex) {
        ItemStack itemstack = null;
        final Slot slot = this.inventorySlots.get(slotIndex);
        if (slot != null && slot.getHasStack()) {
            final ItemStack itemstack2 = slot.getStack();
            itemstack = itemstack2.copy();
            if (slotIndex != this.outputSlotIndex) {
                return null;
            }
            if (!this.mergeItemStack(itemstack2, 0, 36, true)) {
                return null;
            }
            slot.onSlotChange(itemstack2, itemstack);
            if (itemstack2.stackSize == 0) {
                slot.putStack((ItemStack)null);
            }
            else {
                slot.onSlotChanged();
            }
            if (itemstack2.stackSize == itemstack.stackSize) {
                return null;
            }
            slot.onPickupFromSlot(player, itemstack2);
        }
        return itemstack;
    }
    
    public void onContainerClosed(final EntityPlayer player) {
        super.onContainerClosed(player);
        if (!player.worldObj.isRemote) {
            final ItemStack itemstack = this.getSlot(this.outputSlotIndex).getStack();
            if (itemstack != null) {
                W_EntityPlayer.dropPlayerItemWithRandomChoice(player, itemstack, false, false);
            }
        }
        MCH_Lib.DbgLog(player.worldObj, "MCH_DraftingTableGuiContainer.onContainerClosed", new Object[0]);
    }
    
    public void createRecipeItem(final Item outputItem, final Map<Item, Integer> map) {
        final boolean isCreativeMode = this.player.capabilities.isCreativeMode;
        if (this.getSlot(this.outputSlotIndex).getHasStack() && !isCreativeMode) {
            MCH_Lib.DbgLog(this.player.worldObj, "MCH_DraftingTableGuiContainer.createRecipeItem:OutputSlot is not empty", new Object[0]);
            return;
        }
        if (outputItem == null) {
            MCH_Lib.DbgLog(this.player.worldObj, "Error:MCH_DraftingTableGuiContainer.createRecipeItem:outputItem = null", new Object[0]);
            return;
        }
        if (map == null || map.size() <= 0) {
            MCH_Lib.DbgLog(this.player.worldObj, "Error:MCH_DraftingTableGuiContainer.createRecipeItem:map is null : " + map, new Object[0]);
            return;
        }
        final ItemStack itemStack = new ItemStack(outputItem);
        boolean result = false;
        IRecipe recipe = null;
        final MCH_IRecipeList[] arr$;
        final MCH_IRecipeList[] recipeLists = arr$ = new MCH_IRecipeList[] { MCH_ItemRecipe.getInstance(), (MCH_IRecipeList)MCH_HeliInfoManager.getInstance(), (MCH_IRecipeList)MCP_PlaneInfoManager.getInstance(), (MCH_IRecipeList)MCH_VehicleInfoManager.getInstance(), (MCH_IRecipeList)MCH_TankInfoManager.getInstance() };
        for (final MCH_IRecipeList rl : arr$) {
            final int index = this.searchRecipeFromList(rl, itemStack);
            if (index >= 0) {
                recipe = this.isValidRecipe(rl, itemStack, index, map);
                break;
            }
        }
        if (recipe != null && (isCreativeMode || MCH_Lib.canPlayerCreateItem(recipe, this.player.inventory))) {
            for (final Item key : map.keySet()) {
                for (int i = 0; i < map.get(key); ++i) {
                    if (!isCreativeMode) {
                        W_EntityPlayer.consumeInventoryItem(this.player, key);
                    }
                    this.getSlot(this.outputSlotIndex).putStack(recipe.getRecipeOutput().copy());
                    result = true;
                }
            }
        }
        MCH_Lib.DbgLog(this.player.worldObj, "MCH_DraftingTableGuiContainer:Result=" + result + ":Recipe=" + recipe + " :" + outputItem.getUnlocalizedName() + ": map=" + map, new Object[0]);
    }
    
    public IRecipe isValidRecipe(final MCH_IRecipeList list, final ItemStack itemStack, final int startIndex, final Map<Item, Integer> map) {
        for (int index = startIndex; index >= 0 && index < list.getRecipeListSize(); ++index) {
            final IRecipe recipe = list.getRecipe(index);
            if (!itemStack.isItemEqual(recipe.getRecipeOutput())) {
                return null;
            }
            final Map<Item, Integer> mapRecipe = MCH_Lib.getItemMapFromRecipe(recipe);
            boolean isEqual = true;
            for (final Item key : map.keySet()) {
                if (!mapRecipe.containsKey(key) || mapRecipe.get(key) != map.get(key)) {
                    isEqual = false;
                    break;
                }
            }
            if (isEqual) {
                return recipe;
            }
        }
        return null;
    }
    
    public int searchRecipeFromList(final MCH_IRecipeList list, final ItemStack item) {
        for (int i = 0; i < list.getRecipeListSize(); ++i) {
            if (list.getRecipe(i).getRecipeOutput().isItemEqual(item)) {
                return i;
            }
        }
        return -1;
    }
}
