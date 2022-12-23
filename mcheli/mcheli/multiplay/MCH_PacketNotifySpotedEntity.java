//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.multiplay;

import mcheli.*;
import com.google.common.io.*;
import java.io.*;
import net.minecraft.entity.player.*;
import mcheli.wrapper.*;

public class MCH_PacketNotifySpotedEntity extends MCH_Packet
{
    public int count;
    public int num;
    public int[] entityId;
    
    public MCH_PacketNotifySpotedEntity() {
        this.count = 0;
        this.num = 0;
        this.entityId = null;
    }
    
    public int getMessageID() {
        return 268437761;
    }
    
    public void readData(final ByteArrayDataInput data) {
        try {
            this.count = data.readShort();
            this.num = data.readShort();
            if (this.num > 0) {
                this.entityId = new int[this.num];
                for (int i = 0; i < this.num; ++i) {
                    this.entityId[i] = data.readInt();
                }
            }
            else {
                this.num = 0;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void writeData(final DataOutputStream dos) {
        try {
            dos.writeShort(this.count);
            dos.writeShort(this.num);
            for (int i = 0; i < this.num; ++i) {
                dos.writeInt(this.entityId[i]);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void send(final EntityPlayer player, int count, final int[] entityId) {
        if (player == null || entityId == null || entityId.length <= 0 || count <= 0) {
            return;
        }
        if (count > 30000) {
            count = 30000;
        }
        final MCH_PacketNotifySpotedEntity pkt = new MCH_PacketNotifySpotedEntity();
        pkt.count = count;
        pkt.num = entityId.length;
        if (pkt.num > 300) {
            pkt.num = 300;
        }
        if (pkt.num > entityId.length) {
            pkt.num = entityId.length;
        }
        pkt.entityId = entityId;
        W_Network.sendToPlayer((W_PacketBase)pkt, player);
    }
}
