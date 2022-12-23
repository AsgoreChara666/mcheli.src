//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.aircraft;

import mcheli.*;
import com.google.common.io.*;
import java.io.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import mcheli.wrapper.*;

public class MCH_PacketNotifyHitBullet extends MCH_Packet
{
    public int entityID_Ac;
    
    public MCH_PacketNotifyHitBullet() {
        this.entityID_Ac = -1;
    }
    
    @Override
    public int getMessageID() {
        return 268439602;
    }
    
    @Override
    public void readData(final ByteArrayDataInput data) {
        try {
            this.entityID_Ac = data.readInt();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void writeData(final DataOutputStream dos) {
        try {
            dos.writeInt(this.entityID_Ac);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void send(final MCH_EntityAircraft ac, final EntityPlayer rider) {
        if (rider == null || rider.isDead) {
            return;
        }
        final MCH_PacketNotifyHitBullet s = new MCH_PacketNotifyHitBullet();
        s.entityID_Ac = ((ac != null && !ac.isDead) ? W_Entity.getEntityId((Entity)ac) : -1);
        W_Network.sendToPlayer(s, rider);
    }
}
