//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.wrapper;

import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.client.audio.*;

public class W_SoundManagerFunc
{
    public static void DEF_playEntitySound(final SoundManager sm, final String name, final Entity entity, final float volume, final float pitch, final boolean par5) {
        sm.playSound((ISound)new W_Sound(new ResourceLocation(name), volume, pitch, entity.posX, entity.posY, entity.posZ));
    }
    
    public static void MOD_playEntitySound(final SoundManager sm, final String name, final Entity entity, final float volume, final float pitch, final boolean par5) {
        DEF_playEntitySound(sm, W_MOD.DOMAIN + ":" + name, entity, volume, pitch, par5);
    }
}
