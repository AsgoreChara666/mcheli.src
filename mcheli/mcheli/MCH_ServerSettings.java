//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli;

public class MCH_ServerSettings
{
    public static boolean enableCamDistChange;
    public static boolean enableEntityMarker;
    public static boolean enablePVP;
    public static double stingerLockRange;
    public static boolean enableDebugBoundingBox;
    public static boolean enableRotationLimit;
    public static float pitchLimitMin;
    public static float pitchLimitMax;
    public static float rollLimit;
    
    static {
        MCH_ServerSettings.enableCamDistChange = true;
        MCH_ServerSettings.enableEntityMarker = true;
        MCH_ServerSettings.enablePVP = true;
        MCH_ServerSettings.stingerLockRange = 120.0;
        MCH_ServerSettings.enableDebugBoundingBox = true;
        MCH_ServerSettings.enableRotationLimit = false;
        MCH_ServerSettings.pitchLimitMin = 10.0f;
        MCH_ServerSettings.pitchLimitMax = 10.0f;
        MCH_ServerSettings.rollLimit = 35.0f;
    }
}
