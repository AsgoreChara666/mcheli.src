//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.lweapon;

import mcheli.wrapper.*;
import net.minecraft.entity.player.*;
import net.minecraft.potion.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.world.*;

public class MCH_ItemLightWeaponBase extends W_Item
{
    public final MCH_ItemLightWeaponBullet bullet;
    
    public MCH_ItemLightWeaponBase(final int par1, final MCH_ItemLightWeaponBullet bullet) {
        super(par1);
        this.setMaxDurability(10);
        this.setMaxStackSize(1);
        this.bullet = bullet;
    }
    
    public static String getName(final ItemStack itemStack) {
        if (itemStack != null && itemStack.getItem() instanceof MCH_ItemLightWeaponBase) {
            String name = itemStack.getUnlocalizedName();
            final int li = name.lastIndexOf(":");
            if (li >= 0) {
                name = name.substring(li + 1);
            }
            return name;
        }
        return "";
    }
    
    public static boolean isHeld(final EntityPlayer player) {
        final ItemStack is = (player != null) ? player.getHeldItem() : null;
        return is != null && is.getItem() instanceof MCH_ItemLightWeaponBase && player.getItemInUseDuration() > 10;
    }
    
    public void onUsingTick(final ItemStack stack, final EntityPlayer player, final int count) {
        final PotionEffect pe = player.getActivePotionEffect(Potion.nightVision);
        if (pe != null && pe.getDuration() < 220) {
            player.addPotionEffect(new PotionEffect(Potion.nightVision.getId(), 250, 0, false));
        }
    }
    
    public boolean onEntitySwing(final EntityLivingBase entityLiving, final ItemStack stack) {
        return true;
    }
    
    public EnumAction getItemUseAction(final ItemStack par1ItemStack) {
        return EnumAction.bow;
    }
    
    public int getMaxItemUseDuration(final ItemStack par1ItemStack) {
        return 72000;
    }
    
    public ItemStack onItemRightClick(final ItemStack par1ItemStack, final World par2World, final EntityPlayer par3EntityPlayer) {
        if (par1ItemStack != null) {
            par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        }
        return par1ItemStack;
    }
}
