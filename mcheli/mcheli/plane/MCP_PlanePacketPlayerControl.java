//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.plane;

import mcheli.aircraft.*;
import com.google.common.io.*;
import java.io.*;

public class MCP_PlanePacketPlayerControl extends MCH_PacketPlayerControlBase
{
    public byte switchVtol;
    
    public MCP_PlanePacketPlayerControl() {
        this.switchVtol = -1;
    }
    
    public int getMessageID() {
        return 536903696;
    }
    
    public void readData(final ByteArrayDataInput data) {
        super.readData(data);
        try {
            this.switchVtol = data.readByte();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void writeData(final DataOutputStream dos) {
        super.writeData(dos);
        try {
            dos.writeByte(this.switchVtol);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
