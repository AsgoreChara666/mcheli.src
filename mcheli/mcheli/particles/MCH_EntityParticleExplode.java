//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.particles;

import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.world.*;
import net.minecraft.client.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;

public class MCH_EntityParticleExplode extends MCH_EntityParticleBase
{
    private static final ResourceLocation texture;
    private int nowCount;
    private int endCount;
    private TextureManager theRenderEngine;
    private float size;
    
    public MCH_EntityParticleExplode(final World w, final double x, final double y, final double z, final double size, final double age, final double mz) {
        super(w, x, y, z, 0.0, 0.0, 0.0);
        this.theRenderEngine = Minecraft.getMinecraft().renderEngine;
        this.endCount = 1 + (int)age;
        this.size = (float)size;
    }
    
    public void renderParticle(final Tessellator tessellator, final float p_70539_2_, final float p_70539_3_, final float p_70539_4_, final float p_70539_5_, final float p_70539_6_, final float p_70539_7_) {
        final int i = (int)((this.nowCount + p_70539_2_) * 15.0f / this.endCount);
        if (i <= 15) {
            GL11.glEnable(3042);
            final int srcBlend = GL11.glGetInteger(3041);
            final int dstBlend = GL11.glGetInteger(3040);
            GL11.glBlendFunc(770, 771);
            GL11.glDisable(2884);
            this.theRenderEngine.bindTexture(MCH_EntityParticleExplode.texture);
            final float f6 = i % 4 / 4.0f;
            final float f7 = f6 + 0.24975f;
            final float f8 = i / 4 / 4.0f;
            final float f9 = f8 + 0.24975f;
            final float f10 = 2.0f * this.size;
            final float f11 = (float)(this.prevPosX + (this.posX - this.prevPosX) * p_70539_2_ - MCH_EntityParticleExplode.interpPosX);
            final float f12 = (float)(this.prevPosY + (this.posY - this.prevPosY) * p_70539_2_ - MCH_EntityParticleExplode.interpPosY);
            final float f13 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * p_70539_2_ - MCH_EntityParticleExplode.interpPosZ);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            RenderHelper.disableStandardItemLighting();
            tessellator.startDrawingQuads();
            tessellator.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);
            tessellator.setNormal(0.0f, 1.0f, 0.0f);
            tessellator.setBrightness(15728880);
            tessellator.addVertexWithUV((double)(f11 - p_70539_3_ * f10 - p_70539_6_ * f10), (double)(f12 - p_70539_4_ * f10), (double)(f13 - p_70539_5_ * f10 - p_70539_7_ * f10), (double)f7, (double)f9);
            tessellator.addVertexWithUV((double)(f11 - p_70539_3_ * f10 + p_70539_6_ * f10), (double)(f12 + p_70539_4_ * f10), (double)(f13 - p_70539_5_ * f10 + p_70539_7_ * f10), (double)f7, (double)f8);
            tessellator.addVertexWithUV((double)(f11 + p_70539_3_ * f10 + p_70539_6_ * f10), (double)(f12 + p_70539_4_ * f10), (double)(f13 + p_70539_5_ * f10 + p_70539_7_ * f10), (double)f6, (double)f8);
            tessellator.addVertexWithUV((double)(f11 + p_70539_3_ * f10 - p_70539_6_ * f10), (double)(f12 - p_70539_4_ * f10), (double)(f13 + p_70539_5_ * f10 - p_70539_7_ * f10), (double)f6, (double)f9);
            tessellator.draw();
            GL11.glPolygonOffset(0.0f, 0.0f);
            GL11.glEnable(2896);
            GL11.glEnable(2884);
            GL11.glBlendFunc(srcBlend, dstBlend);
            GL11.glDisable(3042);
        }
    }
    
    public int getBrightnessForRender(final float p_70070_1_) {
        return 15728880;
    }
    
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        ++this.nowCount;
        if (this.nowCount == this.endCount) {
            this.setDead();
        }
    }
    
    public int getFXLayer() {
        return 3;
    }
    
    static {
        texture = new ResourceLocation("textures/entity/explosion.png");
    }
}
