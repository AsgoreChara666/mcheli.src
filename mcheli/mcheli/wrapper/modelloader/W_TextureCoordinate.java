//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.wrapper.modelloader;

import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class W_TextureCoordinate
{
    public float u;
    public float v;
    public float w;
    
    public W_TextureCoordinate(final float u, final float v) {
        this(u, v, 0.0f);
    }
    
    public W_TextureCoordinate(final float u, final float v, final float w) {
        this.u = u;
        this.v = v;
        this.w = w;
    }
}
