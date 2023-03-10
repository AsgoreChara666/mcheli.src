//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.multiplay;

import mcheli.*;
import com.google.common.io.*;
import java.io.*;
import mcheli.wrapper.*;

public class MCH_PacketLargeData extends MCH_Packet
{
    public int imageDataIndex;
    public int imageDataSize;
    public int imageDataTotalSize;
    public byte[] buf;
    
    public MCH_PacketLargeData() {
        this.imageDataIndex = -1;
        this.imageDataSize = 0;
        this.imageDataTotalSize = 0;
    }
    
    public int getMessageID() {
        return 536873472;
    }
    
    public void readData(final ByteArrayDataInput data) {
        try {
            this.imageDataIndex = data.readInt();
            this.imageDataSize = data.readInt();
            this.imageDataTotalSize = data.readInt();
            data.readFully(this.buf = new byte[this.imageDataSize]);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void writeData(final DataOutputStream dos) {
        try {
            MCH_MultiplayClient.readImageData(dos);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void send() {
        final MCH_PacketLargeData p = new MCH_PacketLargeData();
        W_Network.sendToServer((W_PacketBase)p);
    }
}
