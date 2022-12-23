//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.gltd;

import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import mcheli.wrapper.*;
import java.util.*;
import net.minecraft.util.*;

public class MCH_ItemGLTD extends W_Item
{
    public MCH_ItemGLTD(final int par1) {
        super(par1);
        this.maxStackSize = 1;
    }
    
    public ItemStack onItemRightClick(final ItemStack par1ItemStack, final World par2World, final EntityPlayer par3EntityPlayer) {
        final float f = 1.0f;
        final float f2 = par3EntityPlayer.prevRotationPitch + (par3EntityPlayer.rotationPitch - par3EntityPlayer.prevRotationPitch) * f;
        final float f3 = par3EntityPlayer.prevRotationYaw + (par3EntityPlayer.rotationYaw - par3EntityPlayer.prevRotationYaw) * f;
        final double d0 = par3EntityPlayer.prevPosX + (par3EntityPlayer.posX - par3EntityPlayer.prevPosX) * f;
        final double d2 = par3EntityPlayer.prevPosY + (par3EntityPlayer.posY - par3EntityPlayer.prevPosY) * f + 1.62 - par3EntityPlayer.yOffset;
        final double d3 = par3EntityPlayer.prevPosZ + (par3EntityPlayer.posZ - par3EntityPlayer.prevPosZ) * f;
        final Vec3 vec3 = W_WorldFunc.getWorldVec3(par2World, d0, d2, d3);
        final float f4 = MathHelper.cos(-f3 * 0.017453292f - 3.1415927f);
        final float f5 = MathHelper.sin(-f3 * 0.017453292f - 3.1415927f);
        final float f6 = -MathHelper.cos(-f2 * 0.017453292f);
        final float f7 = MathHelper.sin(-f2 * 0.017453292f);
        final float f8 = f5 * f6;
        final float f9 = f4 * f6;
        final double d4 = 5.0;
        final Vec3 vec4 = vec3.addVector(f8 * d4, f7 * d4, f9 * d4);
        final MovingObjectPosition movingobjectposition = W_WorldFunc.clip(par2World, vec3, vec4, true);
        if (movingobjectposition == null) {
            return par1ItemStack;
        }
        final Vec3 vec5 = par3EntityPlayer.getLook(f);
        boolean flag = false;
        final float f10 = 1.0f;
        final List list = par2World.getEntitiesWithinAABBExcludingEntity((Entity)par3EntityPlayer, par3EntityPlayer.boundingBox.addCoord(vec5.xCoord * d4, vec5.yCoord * d4, vec5.zCoord * d4).expand((double)f10, (double)f10, (double)f10));
        for (int i = 0; i < list.size(); ++i) {
            final Entity entity = list.get(i);
            if (entity.canBeCollidedWith()) {
                final float f11 = entity.getCollisionBorderSize();
                final AxisAlignedBB axisalignedbb = entity.boundingBox.expand((double)f11, (double)f11, (double)f11);
                if (axisalignedbb.isVecInside(vec3)) {
                    flag = true;
                }
            }
        }
        if (flag) {
            return par1ItemStack;
        }
        if (W_MovingObjectPosition.isHitTypeTile(movingobjectposition)) {
            final int i = movingobjectposition.blockX;
            final int j = movingobjectposition.blockY;
            final int k = movingobjectposition.blockZ;
            final MCH_EntityGLTD entityboat = new MCH_EntityGLTD(par2World, (double)(i + 0.5f), (double)(j + 1.0f), (double)(k + 0.5f));
            entityboat.rotationYaw = par3EntityPlayer.rotationYaw;
            if (!par2World.getCollidingBoundingBoxes((Entity)entityboat, entityboat.boundingBox.expand(-0.1, -0.1, -0.1)).isEmpty()) {
                return par1ItemStack;
            }
            if (!par2World.isRemote) {
                par2World.spawnEntityInWorld((Entity)entityboat);
            }
            if (!par3EntityPlayer.capabilities.isCreativeMode) {
                --par1ItemStack.stackSize;
            }
        }
        return par1ItemStack;
    }
}
