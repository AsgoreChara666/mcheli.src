//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.helicopter;

import mcheli.aircraft.*;
import com.google.common.io.*;
import java.io.*;

public class MCH_HeliPacketPlayerControl extends MCH_PacketPlayerControlBase
{
    public byte switchFold;
    public int unhitchChainId;
    
    public MCH_HeliPacketPlayerControl() {
        this.switchFold = -1;
        this.unhitchChainId = -1;
    }
    
    public int getMessageID() {
        return 536879120;
    }
    
    public void readData(final ByteArrayDataInput data) {
        super.readData(data);
        try {
            this.switchFold = data.readByte();
            this.unhitchChainId = data.readInt();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void writeData(final DataOutputStream dos) {
        super.writeData(dos);
        try {
            dos.writeByte(this.switchFold);
            dos.writeInt(this.unhitchChainId);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
