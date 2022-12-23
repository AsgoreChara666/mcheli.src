//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.tool.rangefinder;

import net.minecraft.entity.player.*;
import mcheli.aircraft.*;
import mcheli.multiplay.*;
import net.minecraft.entity.*;
import mcheli.wrapper.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.world.*;
import net.minecraft.item.*;

public class MCH_ItemRangeFinder extends W_Item
{
    public static int rangeFinderUseCooldown;
    public static boolean continueUsingItem;
    public static float zoom;
    public static int mode;
    
    public MCH_ItemRangeFinder(final int itemId) {
        super(itemId);
        this.maxStackSize = 1;
        this.setMaxDurability(10);
    }
    
    public static boolean canUse(final EntityPlayer player) {
        if (player == null) {
            return false;
        }
        if (player.worldObj == null) {
            return false;
        }
        if (player.getCurrentEquippedItem() == null) {
            return false;
        }
        if (!(player.getCurrentEquippedItem().getItem() instanceof MCH_ItemRangeFinder)) {
            return false;
        }
        if (player.ridingEntity instanceof MCH_EntityAircraft) {
            return false;
        }
        if (player.ridingEntity instanceof MCH_EntitySeat) {
            final MCH_EntityAircraft ac = ((MCH_EntitySeat)player.ridingEntity).getParent();
            if (ac != null && (ac.getIsGunnerMode((Entity)player) || ac.getWeaponIDBySeatID(ac.getSeatIdByEntity((Entity)player)) >= 0)) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isUsingScope(final EntityPlayer player) {
        return player.getItemInUseDuration() > 8 || MCH_ItemRangeFinder.continueUsingItem;
    }
    
    public static void onStartUseItem() {
        W_Reflection.setCameraZoom(MCH_ItemRangeFinder.zoom = 2.0f);
        MCH_ItemRangeFinder.continueUsingItem = true;
    }
    
    public static void onStopUseItem() {
        W_Reflection.restoreCameraZoom();
        MCH_ItemRangeFinder.continueUsingItem = false;
    }
    
    @SideOnly(Side.CLIENT)
    public void spotEntity(final EntityPlayer player, final ItemStack itemStack) {
        if (player != null && player.worldObj.isRemote && MCH_ItemRangeFinder.rangeFinderUseCooldown == 0 && player.getItemInUseDuration() > 8) {
            if (MCH_ItemRangeFinder.mode == 2) {
                MCH_ItemRangeFinder.rangeFinderUseCooldown = 60;
                MCH_PacketIndSpotEntity.send((EntityLivingBase)player, 0);
            }
            else if (itemStack.getMetadata() < itemStack.getMaxDurability()) {
                MCH_ItemRangeFinder.rangeFinderUseCooldown = 60;
                MCH_PacketIndSpotEntity.send((EntityLivingBase)player, (MCH_ItemRangeFinder.mode == 0) ? 60 : 3);
            }
            else {
                W_McClient.MOD_playSoundFX("ng", 1.0f, 1.0f);
            }
        }
    }
    
    public void onPlayerStoppedUsing(final ItemStack p_77615_1_, final World p_77615_2_, final EntityPlayer p_77615_3_, final int p_77615_4_) {
        if (p_77615_2_.isRemote) {
            onStopUseItem();
        }
    }
    
    public ItemStack onItemUseFinish(final ItemStack p_77654_1_, final World p_77654_2_, final EntityPlayer p_77654_3_) {
        return p_77654_1_;
    }
    
    @SideOnly(Side.CLIENT)
    public boolean isFull3D() {
        return true;
    }
    
    public EnumAction getItemUseAction(final ItemStack itemStack) {
        return EnumAction.bow;
    }
    
    public int getMaxItemUseDuration(final ItemStack itemStack) {
        return 72000;
    }
    
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer player) {
        if (canUse(player)) {
            player.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));
        }
        return itemStack;
    }
    
    static {
        MCH_ItemRangeFinder.rangeFinderUseCooldown = 0;
        MCH_ItemRangeFinder.continueUsingItem = false;
        MCH_ItemRangeFinder.zoom = 2.0f;
        MCH_ItemRangeFinder.mode = 0;
    }
}
