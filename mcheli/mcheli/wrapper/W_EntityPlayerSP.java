//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.wrapper;

import net.minecraft.entity.*;
import net.minecraft.client.entity.*;

public class W_EntityPlayerSP
{
    public static void closeScreen(final Entity p) {
        if (p instanceof EntityPlayerSP) {
            ((EntityPlayerSP)p).closeScreen();
        }
    }
}
