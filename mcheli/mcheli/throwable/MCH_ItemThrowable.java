//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.throwable;

import mcheli.wrapper.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import mcheli.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;

public class MCH_ItemThrowable extends W_Item
{
    public MCH_ItemThrowable(final int par1) {
        super(par1);
        this.setMaxStackSize(1);
    }
    
    public static void registerDispenseBehavior(final Item item) {
        BlockDispenser.dispenseBehaviorRegistry.putObject((Object)item, (Object)new MCH_ItemThrowableDispenseBehavior());
    }
    
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer player) {
        player.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));
        return itemStack;
    }
    
    public void onPlayerStoppedUsing(final ItemStack itemStack, final World world, final EntityPlayer player, int par4) {
        if (itemStack != null && itemStack.stackSize > 0) {
            final MCH_ThrowableInfo info = MCH_ThrowableInfoManager.get(itemStack.getItem());
            if (info != null) {
                if (!player.capabilities.isCreativeMode) {
                    --itemStack.stackSize;
                    if (itemStack.stackSize <= 0) {
                        player.inventory.mainInventory[player.inventory.currentItem] = null;
                    }
                }
                world.playSoundAtEntity((Entity)player, "random.bow", 0.5f, 0.4f / (MCH_ItemThrowable.itemRand.nextFloat() * 0.4f + 0.8f));
                if (!world.isRemote) {
                    float acceleration = 1.0f;
                    par4 = itemStack.getMaxItemUseDuration() - par4;
                    if (par4 <= 35) {
                        if (par4 < 5) {
                            par4 = 5;
                        }
                        acceleration = par4 / 25.0f;
                    }
                    MCH_Lib.DbgLog(world, "MCH_ItemThrowable.onPlayerStoppedUsing(%d)", new Object[] { par4 });
                    final MCH_EntityThrowable entity = new MCH_EntityThrowable(world, (EntityLivingBase)player, acceleration);
                    entity.setInfo(info);
                    world.spawnEntityInWorld((Entity)entity);
                }
            }
        }
    }
    
    public int getMaxItemUseDuration(final ItemStack par1ItemStack) {
        return 72000;
    }
    
    public EnumAction getItemUseAction(final ItemStack par1ItemStack) {
        return EnumAction.bow;
    }
}
