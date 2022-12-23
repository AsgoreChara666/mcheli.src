//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.parachute;

import mcheli.wrapper.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;

public class MCH_ItemParachute extends W_Item
{
    public MCH_ItemParachute(final int par1) {
        super(par1);
        this.maxStackSize = 1;
    }
    
    public ItemStack onItemRightClick(final ItemStack item, final World world, final EntityPlayer player) {
        if (!world.isRemote && player.ridingEntity == null && !player.onGround) {
            final double x = player.posX + 0.5;
            final double y = player.posY + 3.5;
            final double z = player.posZ + 0.5;
            final MCH_EntityParachute entity = new MCH_EntityParachute(world, x, y, z);
            entity.rotationYaw = player.rotationYaw;
            entity.motionX = player.motionX;
            entity.motionY = player.motionY;
            entity.motionZ = player.motionZ;
            entity.fallDistance = player.fallDistance;
            player.fallDistance = 0.0f;
            entity.user = (Entity)player;
            entity.setType(1);
            world.spawnEntityInWorld((Entity)entity);
        }
        if (!player.capabilities.isCreativeMode) {
            --item.stackSize;
        }
        return item;
    }
}
