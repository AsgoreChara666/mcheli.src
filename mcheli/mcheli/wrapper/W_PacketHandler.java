//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.wrapper;

import com.google.common.io.*;
import net.minecraft.entity.player.*;
import mcheli.*;
import cpw.mods.fml.common.network.simpleimpl.*;

public class W_PacketHandler implements IPacketHandler, IMessageHandler<W_PacketBase, W_PacketDummy>
{
    public void onPacket(final ByteArrayDataInput data, final EntityPlayer player) {
    }
    
    public W_PacketDummy onMessage(final W_PacketBase message, final MessageContext ctx) {
        try {
            if (message.data != null) {
                if (ctx.side.isClient()) {
                    if (MCH_Lib.getClientPlayer() != null) {
                        W_NetworkRegistry.packetHandler.onPacket(message.data, (EntityPlayer)MCH_Lib.getClientPlayer());
                    }
                }
                else {
                    W_NetworkRegistry.packetHandler.onPacket(message.data, (EntityPlayer)ctx.getServerHandler().playerEntity);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
