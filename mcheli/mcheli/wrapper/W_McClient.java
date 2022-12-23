//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.wrapper;

import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.client.audio.*;
import net.minecraft.entity.*;

public class W_McClient
{
    public static void DEF_playSoundFX(final String name, final float volume, final float pitch) {
        Minecraft.getMinecraft().getSoundHandler().playSound((ISound)new W_Sound(new ResourceLocation(name), volume, pitch));
    }
    
    public static void MOD_playSoundFX(final String name, final float volume, final float pitch) {
        DEF_playSoundFX(W_MOD.DOMAIN + ":" + name, volume, pitch);
    }
    
    public static void addSound(final String name) {
        final Minecraft mc = Minecraft.getMinecraft();
    }
    
    public static void DEF_bindTexture(final String tex) {
        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(tex));
    }
    
    public static void MOD_bindTexture(final String tex) {
        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(W_MOD.DOMAIN, tex));
    }
    
    public static boolean isGamePaused() {
        final Minecraft mc = Minecraft.getMinecraft();
        return mc.isGamePaused();
    }
    
    public static Entity getRenderEntity() {
        return (Entity)Minecraft.getMinecraft().renderViewEntity;
    }
    
    public static void setRenderEntity(final EntityLivingBase entity) {
        Minecraft.getMinecraft().renderViewEntity = entity;
    }
}
