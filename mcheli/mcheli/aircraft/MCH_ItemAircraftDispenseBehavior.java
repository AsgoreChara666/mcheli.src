//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.aircraft;

import net.minecraft.dispenser.*;
import net.minecraft.item.*;
import mcheli.wrapper.*;
import net.minecraft.entity.*;
import mcheli.*;
import net.minecraft.util.*;

public class MCH_ItemAircraftDispenseBehavior extends BehaviorDefaultDispenseItem
{
    public ItemStack dispenseStack(final IBlockSource bs, final ItemStack itemStack) {
        final EnumFacing enumfacing = W_BlockDispenser.getFacing(bs.getBlockMetadata());
        final double x = bs.getX() + enumfacing.getFrontOffsetX() * 2.0;
        final double y = bs.getY() + enumfacing.getFrontOffsetY() * 2.0;
        final double z = bs.getZ() + enumfacing.getFrontOffsetZ() * 2.0;
        if (itemStack.getItem() instanceof MCH_ItemAircraft) {
            final MCH_EntityAircraft ac = ((MCH_ItemAircraft)itemStack.getItem()).onTileClick(itemStack, bs.getWorld(), 0.0f, (int)x, (int)y, (int)z);
            if (ac != null && ac.getAcInfo() != null && !ac.getAcInfo().creativeOnly) {
                if (!ac.isUAV()) {
                    if (!bs.getWorld().isRemote) {
                        ac.getAcDataFromItem(itemStack);
                        bs.getWorld().spawnEntityInWorld((Entity)ac);
                    }
                    itemStack.splitStack(1);
                    MCH_Lib.DbgLog(bs.getWorld(), "dispenseStack:x=%.1f,y=%.1f,z=%.1f;dir=%s:item=" + itemStack.getDisplayName(), x, y, z, enumfacing.toString());
                }
            }
        }
        return itemStack;
    }
}
