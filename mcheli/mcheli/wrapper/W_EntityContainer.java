//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.wrapper;

import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.nbt.*;
import mcheli.*;

public abstract class W_EntityContainer extends W_Entity implements IInventory
{
    public static final int MAX_INVENTORY_SIZE = 54;
    private ItemStack[] containerItems;
    public boolean dropContentsWhenDead;
    
    public W_EntityContainer(final World par1World) {
        super(par1World);
        this.dropContentsWhenDead = true;
        this.containerItems = new ItemStack[54];
    }
    
    protected void entityInit() {
    }
    
    public ItemStack getStackInSlot(final int par1) {
        return this.containerItems[par1];
    }
    
    public int getUsingSlotNum() {
        int numUsingSlot = 0;
        if (this.containerItems == null) {
            numUsingSlot = 0;
        }
        else {
            final int n = this.getSizeInventory();
            numUsingSlot = 0;
            for (int i = 0; i < n && i < this.containerItems.length; ++i) {
                if (this.getStackInSlot(i) != null) {
                    ++numUsingSlot;
                }
            }
        }
        return numUsingSlot;
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
        this.markDirty();
    }
    
    public void onInventoryChanged() {
    }
    
    public boolean isUseableByPlayer(final EntityPlayer par1EntityPlayer) {
        return !this.isDead && par1EntityPlayer.getDistanceSqToEntity((Entity)this) <= 64.0;
    }
    
    public void openChest() {
    }
    
    public void closeChest() {
    }
    
    public boolean isItemValidForSlot(final int par1, final ItemStack par2ItemStack) {
        return true;
    }
    
    public boolean isStackValidForSlot(final int par1, final ItemStack par2ItemStack) {
        return true;
    }
    
    public String getInvName() {
        return "Inventory";
    }
    
    public String getInventoryName() {
        return this.getInvName();
    }
    
    public boolean isInvNameLocalized() {
        return false;
    }
    
    public boolean isCustomInventoryName() {
        return this.isInvNameLocalized();
    }
    
    public int getInventoryStackLimit() {
        return 64;
    }
    
    public void setDead() {
        if (this.dropContentsWhenDead && !this.worldObj.isRemote) {
            for (int i = 0; i < this.getSizeInventory(); ++i) {
                final ItemStack itemstack = this.getStackInSlot(i);
                if (itemstack != null) {
                    final float x = this.rand.nextFloat() * 0.8f + 0.1f;
                    final float y = this.rand.nextFloat() * 0.8f + 0.1f;
                    final float z = this.rand.nextFloat() * 0.8f + 0.1f;
                    while (itemstack.stackSize > 0) {
                        int j = this.rand.nextInt(21) + 10;
                        if (j > itemstack.stackSize) {
                            j = itemstack.stackSize;
                        }
                        final ItemStack itemStack = itemstack;
                        itemStack.stackSize -= j;
                        final EntityItem entityitem = new EntityItem(this.worldObj, this.posX + x, this.posY + y, this.posZ + z, new ItemStack(itemstack.getItem(), j, itemstack.getMetadata()));
                        if (itemstack.hasTagCompound()) {
                            entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                        }
                        final float f3 = 0.05f;
                        entityitem.motionX = (float)this.rand.nextGaussian() * f3;
                        entityitem.motionY = (float)this.rand.nextGaussian() * f3 + 0.2f;
                        entityitem.motionZ = (float)this.rand.nextGaussian() * f3;
                        this.worldObj.spawnEntityInWorld((Entity)entityitem);
                    }
                }
            }
        }
        super.setDead();
    }
    
    protected void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
        final NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < this.containerItems.length; ++i) {
            if (this.containerItems[i] != null) {
                final NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.containerItems[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag((NBTBase)nbttagcompound1);
            }
        }
        par1NBTTagCompound.setTag("Items", (NBTBase)nbttaglist);
    }
    
    protected void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
        final NBTTagList nbttaglist = W_NBTTag.getTagList(par1NBTTagCompound, "Items", 10);
        this.containerItems = new ItemStack[this.getSizeInventory()];
        MCH_Lib.DbgLog(this.worldObj, "W_EntityContainer.readEntityFromNBT.InventorySize = %d", new Object[] { this.getSizeInventory() });
        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            final NBTTagCompound nbttagcompound1 = W_NBTTag.tagAt(nbttaglist, i);
            final int j = nbttagcompound1.getByte("Slot") & 0xFF;
            if (j >= 0 && j < this.containerItems.length) {
                this.containerItems[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
    }
    
    public void travelToDimension(final int par1) {
        this.dropContentsWhenDead = false;
        super.travelToDimension(par1);
    }
    
    public boolean openInventory(final EntityPlayer player) {
        if (!this.worldObj.isRemote && this.getSizeInventory() > 0) {
            player.displayGUIChest((IInventory)this);
            return true;
        }
        return false;
    }
    
    public void openChest() {
    }
    
    public void closeChest() {
    }
    
    public void markDirty() {
    }
    
    public int getSizeInventory() {
        return 0;
    }
}
