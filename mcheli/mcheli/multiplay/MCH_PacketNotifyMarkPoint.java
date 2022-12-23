//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.multiplay;

import mcheli.*;
import com.google.common.io.*;
import java.io.*;
import net.minecraft.entity.player.*;
import mcheli.wrapper.*;

public class MCH_PacketNotifyMarkPoint extends MCH_Packet
{
    public int px;
    public int py;
    public int pz;
    
    public MCH_PacketNotifyMarkPoint() {
        final int n = 0;
        this.pz = n;
        this.px = n;
        this.py = 0;
    }
    
    public int getMessageID() {
        return 268437762;
    }
    
    public void readData(final ByteArrayDataInput data) {
        try {
            this.px = data.readInt();
            this.py = data.readInt();
            this.pz = data.readInt();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void writeData(final DataOutputStream dos) {
        try {
            dos.writeInt(this.px);
            dos.writeInt(this.py);
            dos.writeInt(this.pz);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void send(final EntityPlayer player, final int x, final int y, final int z) {
        final MCH_PacketNotifyMarkPoint pkt = new MCH_PacketNotifyMarkPoint();
        pkt.px = x;
        pkt.py = y;
        pkt.pz = z;
        W_Network.sendToPlayer((W_PacketBase)pkt, player);
    }
}
