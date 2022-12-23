//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.aircraft;

import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import mcheli.wrapper.*;
import mcheli.*;
import net.minecraft.entity.item.*;

public abstract class MCH_ItemAircraft extends W_Item
{
    private static boolean isRegistedDispenseBehavior;
    
    public MCH_ItemAircraft(final int i) {
        super(i);
    }
    
    public static void registerDispenseBehavior(final Item item) {
        if (MCH_ItemAircraft.isRegistedDispenseBehavior) {
            return;
        }
        BlockDispenser.dispenseBehaviorRegistry.putObject((Object)item, (Object)new MCH_ItemAircraftDispenseBehavior());
    }
    
    public abstract MCH_AircraftInfo getAircraftInfo();
    
    public abstract MCH_EntityAircraft createAircraft(final World p0, final double p1, final double p2, final double p3, final ItemStack p4);
    
    public MCH_EntityAircraft onTileClick(final ItemStack itemStack, final World world, final float rotationYaw, final int x, final int y, final int z) {
        final MCH_EntityAircraft ac = this.createAircraft(world, x + 0.5f, y + 1.0f, z + 0.5f, itemStack);
        if (ac == null) {
            return null;
        }
        ac.initRotationYaw((float)(((MathHelper.floor_double(rotationYaw * 4.0f / 360.0f + 0.5) & 0x3) - 1) * 90));
        if (!world.getCollidingBoundingBoxes((Entity)ac, ac.boundingBox.expand(-0.1, -0.1, -0.1)).isEmpty()) {
            return null;
        }
        return ac;
    }
    
    public String toString() {
        final MCH_AircraftInfo info = this.getAircraftInfo();
        if (info != null) {
            return super.toString() + "(" + info.getDirectoryName() + ":" + info.name + ")";
        }
        return super.toString() + "(null)";
    }
    
    public ItemStack onItemRightClick(final ItemStack par1ItemStack, final World world, final EntityPlayer player) {
        final float f = 1.0f;
        final float f2 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * f;
        final float f3 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * f;
        final double d0 = player.prevPosX + (player.posX - player.prevPosX) * f;
        final double d2 = player.prevPosY + (player.posY - player.prevPosY) * f + 1.62 - player.yOffset;
        final double d3 = player.prevPosZ + (player.posZ - player.prevPosZ) * f;
        final Vec3 vec3 = W_WorldFunc.getWorldVec3(world, d0, d2, d3);
        final float f4 = MathHelper.cos(-f3 * 0.017453292f - 3.1415927f);
        final float f5 = MathHelper.sin(-f3 * 0.017453292f - 3.1415927f);
        final float f6 = -MathHelper.cos(-f2 * 0.017453292f);
        final float f7 = MathHelper.sin(-f2 * 0.017453292f);
        final float f8 = f5 * f6;
        final float f9 = f4 * f6;
        final double d4 = 5.0;
        final Vec3 vec4 = vec3.addVector(f8 * d4, f7 * d4, f9 * d4);
        final MovingObjectPosition mop = W_WorldFunc.clip(world, vec3, vec4, true);
        if (mop == null) {
            return par1ItemStack;
        }
        final Vec3 vec5 = player.getLook(f);
        boolean flag = false;
        final float f10 = 1.0f;
        final List list = world.getEntitiesWithinAABBExcludingEntity((Entity)player, player.boundingBox.addCoord(vec5.xCoord * d4, vec5.yCoord * d4, vec5.zCoord * d4).expand((double)f10, (double)f10, (double)f10));
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
        if (W_MovingObjectPosition.isHitTypeTile(mop)) {
            final MCH_Config config = MCH_MOD.config;
            if (MCH_Config.PlaceableOnSpongeOnly.prmBool) {
                final MCH_AircraftInfo acInfo = this.getAircraftInfo();
                if (acInfo != null && acInfo.isFloat && !acInfo.canMoveOnGround) {
                    if (world.getBlock(mop.blockX, mop.blockY - 1, mop.blockZ) != Blocks.sponge) {
                        return par1ItemStack;
                    }
                    for (int x = -1; x <= 1; ++x) {
                        for (int z = -1; z <= 1; ++z) {
                            if (world.getBlock(mop.blockX + x, mop.blockY, mop.blockZ + z) != Blocks.water) {
                                return par1ItemStack;
                            }
                        }
                    }
                }
                else {
                    final Block block = world.getBlock(mop.blockX, mop.blockY, mop.blockZ);
                    if (!(block instanceof BlockSponge)) {
                        return par1ItemStack;
                    }
                }
            }
            this.spawnAircraft(par1ItemStack, world, player, mop.blockX, mop.blockY, mop.blockZ);
        }
        return par1ItemStack;
    }
    
    public MCH_EntityAircraft spawnAircraft(final ItemStack itemStack, final World world, final EntityPlayer player, final int x, final int y, final int z) {
        MCH_EntityAircraft ac = this.onTileClick(itemStack, world, player.rotationYaw, x, y, z);
        if (ac != null) {
            if (ac.getAcInfo() != null && ac.getAcInfo().creativeOnly && !player.capabilities.isCreativeMode) {
                return null;
            }
            if (ac.isUAV()) {
                if (world.isRemote) {
                    if (ac.isSmallUAV()) {
                        W_EntityPlayer.addChatMessage(player, "Please use the UAV station OR Portable Controller");
                    }
                    else {
                        W_EntityPlayer.addChatMessage(player, "Please use the UAV station");
                    }
                }
                ac = null;
            }
            else {
                if (!world.isRemote) {
                    ac.getAcDataFromItem(itemStack);
                    world.spawnEntityInWorld((Entity)ac);
                    MCH_Achievement.addStat((Entity)player, MCH_Achievement.welcome, 1);
                }
                if (!player.capabilities.isCreativeMode) {
                    --itemStack.stackSize;
                }
            }
        }
        return ac;
    }
    
    public void rideEntity(final ItemStack item, final Entity target, final EntityPlayer player) {
        final MCH_Config config = MCH_MOD.config;
        if (!MCH_Config.PlaceableOnSpongeOnly.prmBool && target instanceof EntityMinecartEmpty && target.riddenByEntity == null) {
            final MCH_EntityAircraft ac = this.spawnAircraft(item, player.worldObj, player, (int)target.posX, (int)target.posY + 2, (int)target.posZ);
            if (!player.worldObj.isRemote && ac != null) {
                ac.mountEntity(target);
            }
        }
    }
    
    static {
        MCH_ItemAircraft.isRegistedDispenseBehavior = false;
    }
}
