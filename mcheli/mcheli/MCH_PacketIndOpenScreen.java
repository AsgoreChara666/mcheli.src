//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli;

import com.google.common.io.*;
import java.io.*;
import mcheli.wrapper.*;

public class MCH_PacketIndOpenScreen extends MCH_Packet
{
    public int guiID;
    
    public MCH_PacketIndOpenScreen() {
        this.guiID = -1;
    }
    
    public int getMessageID() {
        return 536872992;
    }
    
    public void readData(final ByteArrayDataInput data) {
        try {
            this.guiID = data.readInt();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void writeData(final DataOutputStream dos) {
        try {
            dos.writeInt(this.guiID);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void send(final int gui_id) {
        if (gui_id < 0) {
            return;
        }
        final MCH_PacketIndOpenScreen s = new MCH_PacketIndOpenScreen();
        s.guiID = gui_id;
        W_Network.sendToServer((W_PacketBase)s);
    }
}
