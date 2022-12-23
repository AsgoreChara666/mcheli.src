//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli;

import com.google.common.io.*;
import java.io.*;
import net.minecraft.server.*;
import mcheli.wrapper.*;
import net.minecraft.entity.player.*;

public class MCH_PacketNotifyServerSettings extends MCH_Packet
{
    public boolean enableCamDistChange;
    public boolean enableEntityMarker;
    public boolean enablePVP;
    public double stingerLockRange;
    public boolean enableDebugBoundingBox;
    public boolean enableRotationLimit;
    public byte pitchLimitMax;
    public byte pitchLimitMin;
    public byte rollLimit;
    
    public MCH_PacketNotifyServerSettings() {
        this.enableCamDistChange = true;
        this.enableEntityMarker = true;
        this.enablePVP = true;
        this.stingerLockRange = 120.0;
        this.enableDebugBoundingBox = true;
        this.enableRotationLimit = false;
        this.pitchLimitMax = 10;
        this.pitchLimitMin = 10;
        this.rollLimit = 35;
    }
    
    public int getMessageID() {
        return 268437568;
    }
    
    public void readData(final ByteArrayDataInput data) {
        try {
            final byte b = data.readByte();
            this.enableCamDistChange = this.getBit(b, 0);
            this.enableEntityMarker = this.getBit(b, 1);
            this.enablePVP = this.getBit(b, 2);
            this.enableDebugBoundingBox = this.getBit(b, 3);
            this.enableRotationLimit = this.getBit(b, 4);
            this.stingerLockRange = data.readFloat();
            this.pitchLimitMax = data.readByte();
            this.pitchLimitMin = data.readByte();
            this.rollLimit = data.readByte();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void writeData(final DataOutputStream dos) {
        try {
            byte b = 0;
            b = this.setBit(b, 0, this.enableCamDistChange);
            b = this.setBit(b, 1, this.enableEntityMarker);
            b = this.setBit(b, 2, this.enablePVP);
            b = this.setBit(b, 3, this.enableDebugBoundingBox);
            b = this.setBit(b, 4, this.enableRotationLimit);
            dos.writeByte(b);
            dos.writeFloat((float)this.stingerLockRange);
            dos.writeByte(this.pitchLimitMax);
            dos.writeByte(this.pitchLimitMin);
            dos.writeByte(this.rollLimit);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void send(final EntityPlayerMP player) {
        final MCH_PacketNotifyServerSettings mch_PacketNotifyServerSettings;
        final MCH_PacketNotifyServerSettings s = mch_PacketNotifyServerSettings = new MCH_PacketNotifyServerSettings();
        final MCH_Config config = MCH_MOD.config;
        mch_PacketNotifyServerSettings.enableCamDistChange = !MCH_Config.DisableCameraDistChange.prmBool;
        final MCH_PacketNotifyServerSettings mch_PacketNotifyServerSettings2 = s;
        final MCH_Config config2 = MCH_MOD.config;
        mch_PacketNotifyServerSettings2.enableEntityMarker = MCH_Config.DisplayEntityMarker.prmBool;
        s.enablePVP = MinecraftServer.getServer().isPVPEnabled();
        final MCH_PacketNotifyServerSettings mch_PacketNotifyServerSettings3 = s;
        final MCH_Config config3 = MCH_MOD.config;
        mch_PacketNotifyServerSettings3.stingerLockRange = MCH_Config.StingerLockRange.prmDouble;
        final MCH_PacketNotifyServerSettings mch_PacketNotifyServerSettings4 = s;
        final MCH_Config config4 = MCH_MOD.config;
        mch_PacketNotifyServerSettings4.enableDebugBoundingBox = MCH_Config.EnableDebugBoundingBox.prmBool;
        final MCH_PacketNotifyServerSettings mch_PacketNotifyServerSettings5 = s;
        final MCH_Config config5 = MCH_MOD.config;
        mch_PacketNotifyServerSettings5.enableRotationLimit = MCH_Config.EnableRotationLimit.prmBool;
        final MCH_PacketNotifyServerSettings mch_PacketNotifyServerSettings6 = s;
        final MCH_Config config6 = MCH_MOD.config;
        mch_PacketNotifyServerSettings6.pitchLimitMax = (byte)MCH_Config.PitchLimitMax.prmInt;
        final MCH_PacketNotifyServerSettings mch_PacketNotifyServerSettings7 = s;
        final MCH_Config config7 = MCH_MOD.config;
        mch_PacketNotifyServerSettings7.pitchLimitMin = (byte)MCH_Config.PitchLimitMin.prmInt;
        final MCH_PacketNotifyServerSettings mch_PacketNotifyServerSettings8 = s;
        final MCH_Config config8 = MCH_MOD.config;
        mch_PacketNotifyServerSettings8.rollLimit = (byte)MCH_Config.RollLimit.prmInt;
        if (player != null) {
            W_Network.sendToPlayer((W_PacketBase)s, (EntityPlayer)player);
        }
        else {
            W_Network.sendToAllPlayers((W_PacketBase)s);
        }
    }
    
    public static void sendAll() {
        send(null);
    }
}
