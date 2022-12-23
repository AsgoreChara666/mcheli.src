//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.block;

import net.minecraft.entity.player.*;
import com.google.common.io.*;
import mcheli.*;

public class MCH_DraftingTablePacketHandler
{
    public static void onPacketCreate(final EntityPlayer player, final ByteArrayDataInput data) {
        if (!player.worldObj.isRemote) {
            final MCH_DraftingTableCreatePacket packet = new MCH_DraftingTableCreatePacket();
            packet.readData(data);
            final boolean openScreen = player.openContainer instanceof MCH_DraftingTableGuiContainer;
            MCH_Lib.DbgLog(false, "MCH_DraftingTablePacketHandler.onPacketCreate : " + openScreen, new Object[0]);
            if (openScreen) {
                ((MCH_DraftingTableGuiContainer)player.openContainer).createRecipeItem(packet.outputItem, packet.map);
            }
        }
    }
}
