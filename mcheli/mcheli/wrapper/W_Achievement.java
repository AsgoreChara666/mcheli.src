//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.wrapper;

import net.minecraft.item.*;
import net.minecraft.stats.*;

public class W_Achievement
{
    public static Achievement registerAchievement(final String par1, final String par2Str, final int par3, final int par4, final Item par5Item, final Achievement par6Achievement) {
        return new Achievement(par1, par2Str, par3, par4, par5Item, par6Achievement).initIndependentStat().registerStat();
    }
}
