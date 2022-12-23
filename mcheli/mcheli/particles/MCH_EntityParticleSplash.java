//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.particles;

import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraft.client.renderer.*;
import mcheli.wrapper.*;

public class MCH_EntityParticleSplash extends MCH_EntityParticleBase
{
    public MCH_EntityParticleSplash(final World par1World, final double x, final double y, final double z, final double mx, final double my, final double mz) {
        super(par1World, x, y, z, mx, my, mz);
        final float particleRed = this.rand.nextFloat() * 0.3f + 0.7f;
        this.particleBlue = particleRed;
        this.particleGreen = particleRed;
        this.particleRed = particleRed;
        this.setParticleScale(this.rand.nextFloat() * 0.5f + 5.0f);
        this.setParticleMaxAge((int)(80.0 / (this.rand.nextFloat() * 0.8 + 0.2)) + 2);
    }
    
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.particleAge < this.particleMaxAge) {
            this.setParticleTextureIndex((int)(8.0 * this.particleAge / this.particleMaxAge));
            ++this.particleAge;
        }
        else {
            this.setDead();
        }
        this.motionY -= 0.05999999865889549;
        Block block = W_WorldFunc.getBlock(this.worldObj, (int)(this.posX + 0.5), (int)(this.posY + 0.5), (int)(this.posZ + 0.5));
        final boolean beforeInWater = W_Block.isEqualTo(block, W_Block.getWater());
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        block = W_WorldFunc.getBlock(this.worldObj, (int)(this.posX + 0.5), (int)(this.posY + 0.5), (int)(this.posZ + 0.5));
        final boolean nowInWater = W_Block.isEqualTo(block, W_Block.getWater());
        if (this.motionY < -0.6 && !beforeInWater && nowInWater) {
            final double p = -this.motionY * 10.0;
            for (int i = 0; i < p; ++i) {
                this.worldObj.spawnParticle("splash", this.posX + 0.5 + (this.rand.nextDouble() - 0.5) * 2.0, this.posY + this.rand.nextDouble(), this.posZ + 0.5 + (this.rand.nextDouble() - 0.5) * 2.0, (this.rand.nextDouble() - 0.5) * 2.0, 4.0, (this.rand.nextDouble() - 0.5) * 2.0);
                this.worldObj.spawnParticle("bubble", this.posX + 0.5 + (this.rand.nextDouble() - 0.5) * 2.0, this.posY - this.rand.nextDouble(), this.posZ + 0.5 + (this.rand.nextDouble() - 0.5) * 2.0, (this.rand.nextDouble() - 0.5) * 2.0, -0.5, (this.rand.nextDouble() - 0.5) * 2.0);
            }
        }
        else if (this.onGround) {
            this.setDead();
        }
        this.motionX *= 0.9;
        this.motionZ *= 0.9;
    }
    
    public void renderParticle(final Tessellator par1Tessellator, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        W_McClient.MOD_bindTexture("textures/particles/smoke.png");
        final float f6 = this.particleTextureIndexX / 8.0f;
        final float f7 = f6 + 0.125f;
        final float f8 = 0.0f;
        final float f9 = 1.0f;
        final float f10 = 0.1f * this.particleScale;
        final float f11 = (float)(this.prevPosX + (this.posX - this.prevPosX) * par2 - MCH_EntityParticleSplash.interpPosX);
        final float f12 = (float)(this.prevPosY + (this.posY - this.prevPosY) * par2 - MCH_EntityParticleSplash.interpPosY);
        final float f13 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * par2 - MCH_EntityParticleSplash.interpPosZ);
        final float f14 = 1.0f;
        par1Tessellator.setColorRGBA_F(this.particleRed * f14, this.particleGreen * f14, this.particleBlue * f14, this.particleAlpha);
        par1Tessellator.addVertexWithUV((double)(f11 - par3 * f10 - par6 * f10), (double)(f12 - par4 * f10), (double)(f13 - par5 * f10 - par7 * f10), (double)f7, (double)f9);
        par1Tessellator.addVertexWithUV((double)(f11 - par3 * f10 + par6 * f10), (double)(f12 + par4 * f10), (double)(f13 - par5 * f10 + par7 * f10), (double)f7, (double)f8);
        par1Tessellator.addVertexWithUV((double)(f11 + par3 * f10 + par6 * f10), (double)(f12 + par4 * f10), (double)(f13 + par5 * f10 + par7 * f10), (double)f6, (double)f8);
        par1Tessellator.addVertexWithUV((double)(f11 + par3 * f10 - par6 * f10), (double)(f12 - par4 * f10), (double)(f13 + par5 * f10 - par7 * f10), (double)f6, (double)f9);
    }
}
