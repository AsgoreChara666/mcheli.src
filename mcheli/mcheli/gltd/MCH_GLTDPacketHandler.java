//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.gltd;

import net.minecraft.entity.player.*;
import com.google.common.io.*;
import net.minecraft.entity.*;

public class MCH_GLTDPacketHandler
{
    public static void onPacket_GLTDPlayerControl(final EntityPlayer player, final ByteArrayDataInput data) {
        if (!(player.ridingEntity instanceof MCH_EntityGLTD)) {
            return;
        }
        if (player.worldObj.isRemote) {
            return;
        }
        final MCH_PacketGLTDPlayerControl pc = new MCH_PacketGLTDPlayerControl();
        pc.readData(data);
        final MCH_EntityGLTD gltd = (MCH_EntityGLTD)player.ridingEntity;
        if (pc.unmount) {
            if (gltd.riddenByEntity != null) {
                gltd.riddenByEntity.mountEntity((Entity)null);
            }
        }
        else {
            if (pc.switchCameraMode >= 0) {
                gltd.camera.setMode(0, pc.switchCameraMode);
            }
            if (pc.switchWeapon >= 0) {
                gltd.switchWeapon((int)pc.switchWeapon);
            }
            if (pc.useWeapon) {
                gltd.useCurrentWeapon(pc.useWeaponOption1, pc.useWeaponOption2);
            }
        }
    }
}
