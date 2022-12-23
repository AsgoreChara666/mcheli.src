//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli;

import net.minecraft.util.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.renderer.texture.*;

public class MCH_TextureManagerDummy extends TextureManager
{
    public static ResourceLocation R;
    private TextureManager tm;
    
    public MCH_TextureManagerDummy(final TextureManager t) {
        super((IResourceManager)null);
        this.tm = t;
    }
    
    public void bindTexture(final ResourceLocation resouce) {
        if (MCH_ClientCommonTickHandler.cameraMode == 2) {
            this.tm.bindTexture(MCH_TextureManagerDummy.R);
        }
        else {
            this.tm.bindTexture(resouce);
        }
    }
    
    public ResourceLocation getResourceLocation(final int p_130087_1_) {
        return this.tm.getResourceLocation(p_130087_1_);
    }
    
    public boolean loadTextureMap(final ResourceLocation p_130088_1_, final TextureMap p_130088_2_) {
        return this.tm.loadTextureMap(p_130088_1_, p_130088_2_);
    }
    
    public boolean loadTickableTexture(final ResourceLocation p_110580_1_, final ITickableTextureObject p_110580_2_) {
        return this.tm.loadTickableTexture(p_110580_1_, p_110580_2_);
    }
    
    public boolean loadTexture(final ResourceLocation p_110579_1_, final ITextureObject p_110579_2_) {
        return this.tm.loadTexture(p_110579_1_, p_110579_2_);
    }
    
    public ITextureObject getTexture(final ResourceLocation p_110581_1_) {
        return this.tm.getTexture(p_110581_1_);
    }
    
    public ResourceLocation getDynamicTextureLocation(final String p_110578_1_, final DynamicTexture p_110578_2_) {
        return this.tm.getDynamicTextureLocation(p_110578_1_, p_110578_2_);
    }
    
    public void tick() {
        this.tm.tick();
    }
    
    public void deleteTexture(final ResourceLocation p_147645_1_) {
        this.tm.deleteTexture(p_147645_1_);
    }
    
    public void onResourceManagerReload(final IResourceManager p_110549_1_) {
        this.tm.onResourceManagerReload(p_110549_1_);
    }
    
    static {
        MCH_TextureManagerDummy.R = new ResourceLocation("mcheli", "textures/test.png");
    }
}
