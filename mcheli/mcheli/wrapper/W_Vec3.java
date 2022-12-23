//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.wrapper;

import net.minecraft.util.*;

public class W_Vec3
{
    public static void rotateAroundZ(final float par1, final Vec3 vOut) {
        final float f1 = MathHelper.cos(par1);
        final float f2 = MathHelper.sin(par1);
        final double d0 = vOut.xCoord * f1 + vOut.yCoord * f2;
        final double d2 = vOut.yCoord * f1 - vOut.xCoord * f2;
        final double d3 = vOut.zCoord;
        vOut.xCoord = d0;
        vOut.yCoord = d2;
        vOut.zCoord = d3;
    }
}
