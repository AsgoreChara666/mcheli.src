//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.throwable;

import net.minecraft.dispenser.*;
import net.minecraft.item.*;
import mcheli.wrapper.*;
import mcheli.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class MCH_ItemThrowableDispenseBehavior extends BehaviorDefaultDispenseItem
{
    public ItemStack dispenseStack(final IBlockSource bs, final ItemStack itemStack) {
        final EnumFacing enumfacing = W_BlockDispenser.getFacing(bs.getBlockMetadata());
        final double x = bs.getX() + enumfacing.getFrontOffsetX() * 2.0;
        final double y = bs.getY() + enumfacing.getFrontOffsetY() * 2.0;
        final double z = bs.getZ() + enumfacing.getFrontOffsetZ() * 2.0;
        if (itemStack.getItem() instanceof MCH_ItemThrowable) {
            final MCH_ThrowableInfo info = MCH_ThrowableInfoManager.get(itemStack.getItem());
            if (info != null) {
                bs.getWorld().playSound(x, y, z, "random.bow", 0.5f, 0.4f / (bs.getWorld().rand.nextFloat() * 0.4f + 0.8f), false);
                if (!bs.getWorld().isRemote) {
                    MCH_Lib.DbgLog(bs.getWorld(), "MCH_ItemThrowableDispenseBehavior.dispenseStack(%s)", new Object[] { info.name });
                    final MCH_EntityThrowable entity = new MCH_EntityThrowable(bs.getWorld(), x, y, z);
                    entity.motionX = enumfacing.getFrontOffsetX() * (double)info.dispenseAcceleration;
                    entity.motionY = enumfacing.getFrontOffsetY() * (double)info.dispenseAcceleration;
                    entity.motionZ = enumfacing.getFrontOffsetZ() * (double)info.dispenseAcceleration;
                    entity.setInfo(info);
                    bs.getWorld().spawnEntityInWorld((Entity)entity);
                    itemStack.splitStack(1);
                }
            }
        }
        return itemStack;
    }
}
