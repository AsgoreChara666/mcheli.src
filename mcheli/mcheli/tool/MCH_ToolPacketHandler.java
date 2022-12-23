//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.tool;

import net.minecraft.entity.player.*;
import com.google.common.io.*;
import mcheli.tool.rangefinder.*;
import mcheli.multiplay.*;
import mcheli.wrapper.*;
import mcheli.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import mcheli.aircraft.*;

public class MCH_ToolPacketHandler
{
    public static void onPacket_IndSpotEntity(final EntityPlayer player, final ByteArrayDataInput data) {
        if (player.worldObj.isRemote) {
            return;
        }
        final MCH_PacketIndSpotEntity pc = new MCH_PacketIndSpotEntity();
        pc.readData(data);
        final ItemStack itemStack = player.getHeldItem();
        if (itemStack != null && itemStack.getItem() instanceof MCH_ItemRangeFinder) {
            if (pc.targetFilter == 0) {
                if (MCH_Multiplay.markPoint(player, player.posX, player.posY + player.getEyeHeight(), player.posZ)) {
                    W_WorldFunc.MOD_playSoundAtEntity((Entity)player, "pi", 1.0f, 1.0f);
                }
                else {
                    W_WorldFunc.MOD_playSoundAtEntity((Entity)player, "ng", 1.0f, 1.0f);
                }
            }
            else if (itemStack.getMetadata() < itemStack.getMaxDurability()) {
                final MCH_Config config = MCH_MOD.config;
                if (MCH_Config.RangeFinderConsume.prmBool) {
                    itemStack.damageItem(1, (EntityLivingBase)player);
                }
                int prmInt;
                if ((pc.targetFilter & 0xFC) == 0x0) {
                    prmInt = 60;
                }
                else {
                    final MCH_Config config2 = MCH_MOD.config;
                    prmInt = MCH_Config.RangeFinderSpotTime.prmInt;
                }
                final int time = prmInt;
                final MCH_EntityAircraft mch_EntityAircraft = null;
                final double posX = player.posX;
                final double n = player.posY + player.getEyeHeight();
                final double posZ = player.posZ;
                final int targetFilter = pc.targetFilter;
                final MCH_Config config3 = MCH_MOD.config;
                if (MCH_Multiplay.spotEntity((EntityLivingBase)player, mch_EntityAircraft, posX, n, posZ, targetFilter, (float)MCH_Config.RangeFinderSpotDist.prmInt, time, 20.0f)) {
                    W_WorldFunc.MOD_playSoundAtEntity((Entity)player, "pi", 1.0f, 1.0f);
                }
                else {
                    W_WorldFunc.MOD_playSoundAtEntity((Entity)player, "ng", 1.0f, 1.0f);
                }
            }
        }
    }
}
