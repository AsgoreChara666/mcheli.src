//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.aircraft;

import net.minecraft.inventory.*;
import net.minecraft.item.*;
import mcheli.parachute.*;
import java.util.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.nbt.*;
import mcheli.wrapper.*;

public class MCH_AircraftInventory implements IInventory
{
    public final int SLOT_FUEL0 = 0;
    public final int SLOT_FUEL1 = 1;
    public final int SLOT_FUEL2 = 2;
    public final int SLOT_PARACHUTE0 = 3;
    public final int SLOT_PARACHUTE1 = 4;
    private ItemStack[] containerItems;
    final MCH_EntityAircraft aircraft;
    
    public MCH_AircraftInventory(final MCH_EntityAircraft ac) {
        this.containerItems = new ItemStack[this.getSizeInventory()];
        this.aircraft = ac;
    }
    
    public ItemStack getFuelSlotItemStack(final int i) {
        return this.getStackInSlot(0 + i);
    }
    
    public ItemStack getParachuteSlotItemStack(final int i) {
        return this.getStackInSlot(3 + i);
    }
    
    public boolean haveParachute() {
        for (int i = 0; i < 2; ++i) {
            final ItemStack item = this.getParachuteSlotItemStack(i);
            if (item != null && item.getItem() instanceof MCH_ItemParachute) {
                return true;
            }
        }
        return false;
    }
    
    public void consumeParachute() {
        for (int i = 0; i < 2; ++i) {
            final ItemStack item = this.getParachuteSlotItemStack(i);
            if (item != null && item.getItem() instanceof MCH_ItemParachute) {
                this.setInventorySlotContents(3 + i, null);
                break;
            }
        }
    }
    
    public int getSizeInventory() {
        return 10;
    }
    
    public ItemStack getStackInSlot(final int var1) {
        return this.containerItems[var1];
    }
    
    public void setDead() {
        final Random rand = new Random();
        if (this.aircraft.dropContentsWhenDead && !this.aircraft.worldObj.isRemote) {
            for (int i = 0; i < this.getSizeInventory(); ++i) {
                final ItemStack itemstack = this.getStackInSlot(i);
                if (itemstack != null) {
                    final float x = rand.nextFloat() * 0.8f + 0.1f;
                    final float y = rand.nextFloat() * 0.8f + 0.1f;
                    final float z = rand.nextFloat() * 0.8f + 0.1f;
                    while (itemstack.stackSize > 0) {
                        int j = rand.nextInt(21) + 10;
                        if (j > itemstack.stackSize) {
                            j = itemstack.stackSize;
                        }
                        final ItemStack itemStack = itemstack;
                        itemStack.stackSize -= j;
                        final EntityItem entityitem = new EntityItem(this.aircraft.worldObj, this.aircraft.posX + x, this.aircraft.posY + y, this.aircraft.posZ + z, new ItemStack(itemstack.getItem(), j, itemstack.getMetadata()));
                        if (itemstack.hasTagCompound()) {
                            entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                        }
                        final float f3 = 0.05f;
                        entityitem.motionX = (float)rand.nextGaussian() * f3;
                        entityitem.motionY = (float)rand.nextGaussian() * f3 + 0.2f;
                        entityitem.motionZ = (float)rand.nextGaussian() * f3;
                        this.aircraft.worldObj.spawnEntityInWorld((Entity)entityitem);
                    }
                }
            }
        }
    }
    
    public ItemStack decrStackSize(final int par1, final int par2) {
        if (this.containerItems[par1] == null) {
            return null;
        }
        if (this.containerItems[par1].stackSize <= par2) {
            final ItemStack itemstack = this.containerItems[par1];
            this.containerItems[par1] = null;
            return itemstack;
        }
        final ItemStack itemstack = this.containerItems[par1].splitStack(par2);
        if (this.containerItems[par1].stackSize == 0) {
            this.containerItems[par1] = null;
        }
        return itemstack;
    }
    
    public ItemStack getStackInSlotOnClosing(final int par1) {
        if (this.containerItems[par1] != null) {
            final ItemStack itemstack = this.containerItems[par1];
            this.containerItems[par1] = null;
            return itemstack;
        }
        return null;
    }
    
    public void setInventorySlotContents(final int par1, final ItemStack par2ItemStack) {
        this.containerItems[par1] = par2ItemStack;
        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit()) {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
    }
    
    public String getInventoryName() {
        return this.getInvName();
    }
    
    public String getInvName() {
        if (this.aircraft.getAcInfo() == null) {
            return "";
        }
        final String s = this.aircraft.getAcInfo().displayName;
        return (s.length() <= 32) ? s : s.substring(0, 31);
    }
    
    public boolean isInvNameLocalized() {
        return this.aircraft.getAcInfo() != null;
    }
    
    public boolean isCustomInventoryName() {
        return this.isInvNameLocalized();
    }
    
    public int getInventoryStackLimit() {
        return 64;
    }
    
    public void markDirty() {
    }
    
    public boolean isUseableByPlayer(final EntityPlayer player) {
        return player.getDistanceSqToEntity((Entity)this.aircraft) <= 144.0;
    }
    
    public boolean isItemValidForSlot(final int par1, final ItemStack par2ItemStack) {
        return true;
    }
    
    public boolean isStackValidForSlot(final int par1, final ItemStack par2ItemStack) {
        return true;
    }
    
    public void openChest() {
    }
    
    public void closeChest() {
    }
    
    protected void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
        final NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < this.containerItems.length; ++i) {
            if (this.containerItems[i] != null) {
                final NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("SlotAC", (byte)i);
                this.containerItems[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag((NBTBase)nbttagcompound1);
            }
        }
        par1NBTTagCompound.setTag("ItemsAC", (NBTBase)nbttaglist);
    }
    
    protected void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
        final NBTTagList nbttaglist = W_NBTTag.getTagList(par1NBTTagCompound, "ItemsAC", 10);
        this.containerItems = new ItemStack[this.getSizeInventory()];
        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            final NBTTagCompound nbttagcompound1 = W_NBTTag.tagAt(nbttaglist, i);
            final int j = nbttagcompound1.getByte("SlotAC") & 0xFF;
            if (j >= 0 && j < this.containerItems.length) {
                this.containerItems[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
    }
    
    public void onInventoryChanged() {
    }
    
    public void openChest() {
    }
    
    public void closeChest() {
    }
}
