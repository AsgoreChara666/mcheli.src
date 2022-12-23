//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.uav;

import net.minecraft.entity.player.*;
import com.google.common.io.*;
import net.minecraft.entity.*;

public class MCH_UavPacketHandler
{
    public static void onPacketUavStatus(final EntityPlayer player, final ByteArrayDataInput data) {
        if (!player.worldObj.isRemote) {
            final MCH_UavPacketStatus status = new MCH_UavPacketStatus();
            status.readData(data);
            if (player.ridingEntity instanceof MCH_EntityUavStation) {
                ((MCH_EntityUavStation)player.ridingEntity).setUavPosition((int)status.posUavX, (int)status.posUavY, (int)status.posUavZ);
                if (status.continueControl) {
                    ((MCH_EntityUavStation)player.ridingEntity).controlLastAircraft((Entity)player);
                }
            }
        }
    }
}
