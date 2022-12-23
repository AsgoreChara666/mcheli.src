//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli;

import com.google.common.io.*;
import java.io.*;
import net.minecraft.entity.*;
import mcheli.wrapper.*;
import net.minecraft.entity.player.*;

public class MCH_PacketNotifyLock extends MCH_Packet
{
    public int entityID;
    
    public MCH_PacketNotifyLock() {
        this.entityID = -1;
    }
    
    public int getMessageID() {
        return 536873984;
    }
    
    public void readData(final ByteArrayDataInput data) {
        try {
            this.entityID = data.readInt();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void writeData(final DataOutputStream dos) {
        try {
            dos.writeInt(this.entityID);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void send(final Entity target) {
        if (target != null) {
            final MCH_PacketNotifyLock s = new MCH_PacketNotifyLock();
            s.entityID = target.getEntityId();
            W_Network.sendToServer((W_PacketBase)s);
        }
    }
    
    public static void sendToPlayer(final EntityPlayer entity) {
        if (entity instanceof EntityPlayerMP) {
            final MCH_PacketNotifyLock s = new MCH_PacketNotifyLock();
            W_Network.sendToPlayer((W_PacketBase)s, entity);
        }
    }
}
