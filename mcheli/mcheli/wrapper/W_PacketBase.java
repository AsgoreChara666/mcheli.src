//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.wrapper;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.*;
import com.google.common.io.*;

public class W_PacketBase implements IMessage
{
    ByteArrayDataInput data;
    
    public byte[] createData() {
        return null;
    }
    
    public void fromBytes(final ByteBuf buf) {
        final byte[] dst = new byte[buf.array().length - 1];
        buf.getBytes(0, dst);
        this.data = ByteStreams.newDataInput(dst);
    }
    
    public void toBytes(final ByteBuf buf) {
        buf.writeBytes(this.createData());
    }
}
