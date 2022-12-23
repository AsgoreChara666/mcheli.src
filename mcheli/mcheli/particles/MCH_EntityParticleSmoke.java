//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.particles;

import net.minecraft.world.*;
import mcheli.aircraft.*;
import net.minecraft.entity.*;
import java.util.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.client.renderer.*;
import mcheli.wrapper.*;
import org.lwjgl.opengl.*;

public class MCH_EntityParticleSmoke extends MCH_EntityParticleBase
{
    public MCH_EntityParticleSmoke(final World par1World, final double x, final double y, final double z, final double mx, final double my, final double mz) {
        super(par1World, x, y, z, mx, my, mz);
        final float particleRed = this.rand.nextFloat() * 0.3f + 0.7f;
        this.particleBlue = particleRed;
        this.particleGreen = particleRed;
        this.particleRed = particleRed;
        this.setParticleScale(this.rand.nextFloat() * 0.5f + 5.0f);
        this.setParticleMaxAge((int)(16.0 / (this.rand.nextFloat() * 0.8 + 0.2)) + 2);
    }
    
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.particleAge < this.particleMaxAge) {
            this.setParticleTextureIndex((int)(8.0 * this.particleAge / this.particleMaxAge));
            ++this.particleAge;
            if (this.diffusible && this.particleScale < this.particleMaxScale) {
                this.particleScale += 0.8f;
            }
            if (this.toWhite) {
                final float mn = this.getMinColor();
                final float mx = this.getMaxColor();
                final float dist = mx - mn;
                if (dist > 0.2) {
                    this.particleRed += (mx - this.particleRed) * 0.016f;
                    this.particleGreen += (mx - this.particleGreen) * 0.016f;
                    this.particleBlue += (mx - this.particleBlue) * 0.016f;
                }
            }
            this.effectWind();
            if (this.particleAge / (double)this.particleMaxAge > this.moutionYUpAge) {
                this.motionY += 0.02;
            }
            else {
                this.motionY += this.gravity;
            }
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            if (this.diffusible) {
                this.motionX *= 0.96;
                this.motionZ *= 0.96;
                this.motionY *= 0.96;
            }
            else {
                this.motionX *= 0.9;
                this.motionZ *= 0.9;
            }
            return;
        }
        this.setDead();
    }
    
    public float getMinColor() {
        return this.min(this.min(this.particleBlue, this.particleGreen), this.particleRed);
    }
    
    public float getMaxColor() {
        return this.max(this.max(this.particleBlue, this.particleGreen), this.particleRed);
    }
    
    public float min(final float a, final float b) {
        return (a < b) ? a : b;
    }
    
    public float max(final float a, final float b) {
        return (a > b) ? a : b;
    }
    
    public void effectWind() {
        if (this.isEffectedWind) {
            final int range = 15;
            final List list = this.worldObj.getEntitiesWithinAABB((Class)MCH_EntityAircraft.class, this.getBoundingBox().expand(15.0, 15.0, 15.0));
            for (int i = 0; i < list.size(); ++i) {
                final MCH_EntityAircraft ac = list.get(i);
                if (ac.getThrottle() > 0.10000000149011612) {
                    final float dist = this.getDistanceToEntity((Entity)ac);
                    final double vel = (23.0 - dist) * 0.009999999776482582 * ac.getThrottle();
                    final double mx = ac.posX - this.posX;
                    final double mz = ac.posZ - this.posZ;
                    this.motionX -= mx * vel;
                    this.motionZ -= mz * vel;
                }
            }
        }
    }
    
    public int getFXLayer() {
        return 3;
    }
    
    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender(final float p_70070_1_) {
        final double y = this.posY;
        this.posY += 3000.0;
        final int i = super.getBrightnessForRender(p_70070_1_);
        this.posY = y;
        return i;
    }
    
    public void renderParticle(final Tessellator par1Tessellator, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        W_McClient.MOD_bindTexture("textures/particles/smoke.png");
        GL11.glEnable(3042);
        final int srcBlend = GL11.glGetInteger(3041);
        final int dstBlend = GL11.glGetInteger(3040);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(2896);
        GL11.glDisable(2884);
        final float f6 = this.particleTextureIndexX / 8.0f;
        final float f7 = f6 + 0.125f;
        final float f8 = 0.0f;
        final float f9 = 1.0f;
        final float f10 = 0.1f * this.particleScale;
        final float f11 = (float)(this.prevPosX + (this.posX - this.prevPosX) * par2 - MCH_EntityParticleSmoke.interpPosX);
        final float f12 = (float)(this.prevPosY + (this.posY - this.prevPosY) * par2 - MCH_EntityParticleSmoke.interpPosY);
        final float f13 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * par2 - MCH_EntityParticleSmoke.interpPosZ);
        par1Tessellator.startDrawingQuads();
        par1Tessellator.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);
        par1Tessellator.setBrightness(this.getBrightnessForRender(par2));
        par1Tessellator.setNormal(0.0f, 1.0f, 0.0f);
        par1Tessellator.addVertexWithUV((double)(f11 - par3 * f10 - par6 * f10), (double)(f12 - par4 * f10), (double)(f13 - par5 * f10 - par7 * f10), (double)f7, (double)f9);
        par1Tessellator.addVertexWithUV((double)(f11 - par3 * f10 + par6 * f10), (double)(f12 + par4 * f10), (double)(f13 - par5 * f10 + par7 * f10), (double)f7, (double)f8);
        par1Tessellator.addVertexWithUV((double)(f11 + par3 * f10 + par6 * f10), (double)(f12 + par4 * f10), (double)(f13 + par5 * f10 + par7 * f10), (double)f6, (double)f8);
        par1Tessellator.addVertexWithUV((double)(f11 + par3 * f10 - par6 * f10), (double)(f12 - par4 * f10), (double)(f13 + par5 * f10 - par7 * f10), (double)f6, (double)f9);
        par1Tessellator.draw();
        GL11.glEnable(2884);
        GL11.glEnable(2896);
        GL11.glBlendFunc(srcBlend, dstBlend);
        GL11.glDisable(3042);
    }
}
