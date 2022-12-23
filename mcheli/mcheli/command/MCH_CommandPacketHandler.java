//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.command;

import net.minecraft.entity.player.*;
import com.google.common.io.*;
import mcheli.*;
import mcheli.aircraft.*;
import net.minecraft.entity.*;

public class MCH_CommandPacketHandler
{
    public static void onPacketTitle(final EntityPlayer player, final ByteArrayDataInput data) {
        if (player == null || !player.worldObj.isRemote) {
            return;
        }
        final MCH_PacketTitle req = new MCH_PacketTitle();
        req.readData(data);
        MCH_MOD.proxy.printChatMessage(req.chatComponent, req.showTime, req.position);
    }
    
    public static void onPacketSave(final EntityPlayer player, final ByteArrayDataInput data) {
        if (player == null || player.worldObj.isRemote) {
            return;
        }
        final MCH_PacketCommandSave req = new MCH_PacketCommandSave();
        req.readData(data);
        final MCH_EntityAircraft ac = MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)player);
        if (ac != null) {
            ac.setCommand(req.str, player);
        }
    }
}
