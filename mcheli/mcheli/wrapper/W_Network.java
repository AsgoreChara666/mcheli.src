//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.wrapper;

import cpw.mods.fml.common.network.simpleimpl.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import cpw.mods.fml.common.network.*;

public class W_Network
{
    public static final SimpleNetworkWrapper INSTANCE;
    
    public static void sendToServer(final W_PacketBase pkt) {
        W_Network.INSTANCE.sendToServer((IMessage)pkt);
    }
    
    public static void sendToPlayer(final W_PacketBase pkt, final EntityPlayer player) {
        if (player instanceof EntityPlayerMP) {
            W_Network.INSTANCE.sendTo((IMessage)pkt, (EntityPlayerMP)player);
        }
    }
    
    public static void sendToAllAround(final W_PacketBase pkt, final Entity sender, final double renge) {
        final NetworkRegistry.TargetPoint t = new NetworkRegistry.TargetPoint(sender.dimension, sender.posX, sender.posY, sender.posZ, renge);
        W_Network.INSTANCE.sendToAllAround((IMessage)pkt, t);
    }
    
    public static void sendToAllPlayers(final W_PacketBase pkt) {
        W_Network.INSTANCE.sendToAll((IMessage)pkt);
    }
    
    static {
        INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("MCHeli_CH");
    }
}
