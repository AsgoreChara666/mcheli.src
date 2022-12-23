//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.particles;

import net.minecraft.world.*;
import cpw.mods.fml.client.*;
import mcheli.wrapper.*;
import net.minecraft.client.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.player.*;

public class MCH_ParticlesUtil
{
    public static MCH_EntityParticleMarkPoint markPoint;
    
    public static void spawnParticleExplode(final World w, final double x, final double y, final double z, final float size, final float r, final float g, final float b, final float a, final int age) {
        final MCH_EntityParticleExplode epe = new MCH_EntityParticleExplode(w, x, y, z, (double)size, (double)age, 0.0);
        epe.setParticleMaxAge(age);
        epe.setRBGColorF(r, g, b);
        epe.setAlphaF(a);
        FMLClientHandler.instance().getClient().effectRenderer.addEffect((EntityFX)epe);
    }
    
    public static void spawnParticleTileCrack(final World w, final int blockX, final int blockY, final int blockZ, final double x, final double y, final double z, final double mx, final double my, final double mz) {
        final String name = W_Particle.getParticleTileCrackName(w, blockX, blockY, blockZ);
        if (!name.isEmpty()) {
            DEF_spawnParticle(name, x, y, z, mx, my, mz, 20.0f);
        }
    }
    
    public static boolean spawnParticleTileDust(final World w, final int blockX, final int blockY, final int blockZ, final double x, final double y, final double z, final double mx, final double my, final double mz, final float scale) {
        boolean ret = false;
        final int[][] offset = { { 0, 0, 0 }, { 0, 0, -1 }, { 0, 0, 1 }, { 1, 0, 0 }, { -1, 0, 0 } };
        for (int len = offset.length, i = 0; i < len; ++i) {
            final String name = W_Particle.getParticleTileDustName(w, blockX + offset[i][0], blockY + offset[i][1], blockZ + offset[i][2]);
            if (!name.isEmpty()) {
                final EntityFX e = DEF_spawnParticle(name, x, y, z, mx, my, mz, 20.0f);
                if (e instanceof MCH_EntityBlockDustFX) {
                    ((MCH_EntityBlockDustFX)e).setScale(scale * 2.0f);
                    ret = true;
                    break;
                }
            }
        }
        return ret;
    }
    
    public static EntityFX DEF_spawnParticle(final String s, final double x, final double y, final double z, final double mx, final double my, final double mz, final float dist) {
        final EntityFX e = doSpawnParticle(s, x, y, z, mx, my, mz);
        if (e != null) {
            final EntityFX entityFX = e;
            entityFX.renderDistanceWeight *= dist;
        }
        return e;
    }
    
    public static EntityFX doSpawnParticle(final String p_72726_1_, final double p_72726_2_, final double p_72726_4_, final double p_72726_6_, final double p_72726_8_, final double p_72726_10_, final double p_72726_12_) {
        final Minecraft mc = Minecraft.getMinecraft();
        final RenderGlobal renderGlobal = mc.renderGlobal;
        if (mc == null || mc.renderViewEntity == null || mc.effectRenderer == null) {
            return null;
        }
        int i = mc.gameSettings.particleSetting;
        if (i == 1 && mc.theWorld.rand.nextInt(3) == 0) {
            i = 2;
        }
        final double d6 = mc.renderViewEntity.posX - p_72726_2_;
        final double d7 = mc.renderViewEntity.posY - p_72726_4_;
        final double d8 = mc.renderViewEntity.posZ - p_72726_6_;
        EntityFX entityfx = null;
        if (p_72726_1_.equalsIgnoreCase("hugeexplosion")) {
            mc.effectRenderer.addEffect(entityfx = (EntityFX)new EntityHugeExplodeFX((World)mc.theWorld, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_));
        }
        else if (p_72726_1_.equalsIgnoreCase("largeexplode")) {
            mc.effectRenderer.addEffect(entityfx = (EntityFX)new EntityLargeExplodeFX(mc.renderEngine, (World)mc.theWorld, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_));
        }
        else if (p_72726_1_.equalsIgnoreCase("fireworksSpark")) {
            mc.effectRenderer.addEffect(entityfx = (EntityFX)new EntityFireworkSparkFX((World)mc.theWorld, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_, mc.effectRenderer));
        }
        if (entityfx != null) {
            return entityfx;
        }
        final double d9 = 300.0;
        if (d6 * d6 + d7 * d7 + d8 * d8 > d9 * d9) {
            return null;
        }
        if (i > 1) {
            return null;
        }
        if (p_72726_1_.equalsIgnoreCase("bubble")) {
            entityfx = (EntityFX)new EntityBubbleFX((World)mc.theWorld, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
        }
        else if (p_72726_1_.equalsIgnoreCase("suspended")) {
            entityfx = (EntityFX)new EntitySuspendFX((World)mc.theWorld, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
        }
        else if (p_72726_1_.equalsIgnoreCase("depthsuspend")) {
            entityfx = (EntityFX)new EntityAuraFX((World)mc.theWorld, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
        }
        else if (p_72726_1_.equalsIgnoreCase("townaura")) {
            entityfx = (EntityFX)new EntityAuraFX((World)mc.theWorld, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
        }
        else if (p_72726_1_.equalsIgnoreCase("crit")) {
            entityfx = (EntityFX)new EntityCritFX((World)mc.theWorld, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
        }
        else if (p_72726_1_.equalsIgnoreCase("magicCrit")) {
            entityfx = (EntityFX)new EntityCritFX((World)mc.theWorld, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
            entityfx.setRBGColorF(entityfx.getRedColorF() * 0.3f, entityfx.getGreenColorF() * 0.8f, entityfx.getBlueColorF());
            entityfx.nextTextureIndexX();
        }
        else if (p_72726_1_.equalsIgnoreCase("smoke")) {
            entityfx = (EntityFX)new EntitySmokeFX((World)mc.theWorld, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
        }
        else if (p_72726_1_.equalsIgnoreCase("mobSpell")) {
            entityfx = (EntityFX)new EntitySpellParticleFX((World)mc.theWorld, p_72726_2_, p_72726_4_, p_72726_6_, 0.0, 0.0, 0.0);
            entityfx.setRBGColorF((float)p_72726_8_, (float)p_72726_10_, (float)p_72726_12_);
        }
        else if (p_72726_1_.equalsIgnoreCase("mobSpellAmbient")) {
            entityfx = (EntityFX)new EntitySpellParticleFX((World)mc.theWorld, p_72726_2_, p_72726_4_, p_72726_6_, 0.0, 0.0, 0.0);
            entityfx.setAlphaF(0.15f);
            entityfx.setRBGColorF((float)p_72726_8_, (float)p_72726_10_, (float)p_72726_12_);
        }
        else if (p_72726_1_.equalsIgnoreCase("spell")) {
            entityfx = (EntityFX)new EntitySpellParticleFX((World)mc.theWorld, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
        }
        else if (p_72726_1_.equalsIgnoreCase("instantSpell")) {
            entityfx = (EntityFX)new EntitySpellParticleFX((World)mc.theWorld, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
            ((EntitySpellParticleFX)entityfx).setBaseSpellTextureIndex(144);
        }
        else if (p_72726_1_.equalsIgnoreCase("witchMagic")) {
            entityfx = (EntityFX)new EntitySpellParticleFX((World)mc.theWorld, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
            ((EntitySpellParticleFX)entityfx).setBaseSpellTextureIndex(144);
            final float f = mc.theWorld.rand.nextFloat() * 0.5f + 0.35f;
            entityfx.setRBGColorF(1.0f * f, 0.0f * f, 1.0f * f);
        }
        else if (p_72726_1_.equalsIgnoreCase("note")) {
            entityfx = (EntityFX)new EntityNoteFX((World)mc.theWorld, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
        }
        else if (p_72726_1_.equalsIgnoreCase("portal")) {
            entityfx = (EntityFX)new EntityPortalFX((World)mc.theWorld, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
        }
        else if (p_72726_1_.equalsIgnoreCase("enchantmenttable")) {
            entityfx = (EntityFX)new EntityEnchantmentTableParticleFX((World)mc.theWorld, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
        }
        else if (p_72726_1_.equalsIgnoreCase("explode")) {
            entityfx = (EntityFX)new EntityExplodeFX((World)mc.theWorld, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
        }
        else if (p_72726_1_.equalsIgnoreCase("flame")) {
            entityfx = (EntityFX)new EntityFlameFX((World)mc.theWorld, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
        }
        else if (p_72726_1_.equalsIgnoreCase("lava")) {
            entityfx = (EntityFX)new EntityLavaFX((World)mc.theWorld, p_72726_2_, p_72726_4_, p_72726_6_);
        }
        else if (p_72726_1_.equalsIgnoreCase("footstep")) {
            entityfx = (EntityFX)new EntityFootStepFX(mc.renderEngine, (World)mc.theWorld, p_72726_2_, p_72726_4_, p_72726_6_);
        }
        else if (p_72726_1_.equalsIgnoreCase("splash")) {
            entityfx = (EntityFX)new EntitySplashFX((World)mc.theWorld, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
        }
        else if (p_72726_1_.equalsIgnoreCase("wake")) {
            entityfx = (EntityFX)new EntityFishWakeFX((World)mc.theWorld, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
        }
        else if (p_72726_1_.equalsIgnoreCase("largesmoke")) {
            entityfx = (EntityFX)new EntitySmokeFX((World)mc.theWorld, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_, 2.5f);
        }
        else if (p_72726_1_.equalsIgnoreCase("cloud")) {
            entityfx = (EntityFX)new EntityCloudFX((World)mc.theWorld, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
        }
        else if (p_72726_1_.equalsIgnoreCase("reddust")) {
            entityfx = (EntityFX)new EntityReddustFX((World)mc.theWorld, p_72726_2_, p_72726_4_, p_72726_6_, (float)p_72726_8_, (float)p_72726_10_, (float)p_72726_12_);
        }
        else if (p_72726_1_.equalsIgnoreCase("snowballpoof")) {
            entityfx = (EntityFX)new EntityBreakingFX((World)mc.theWorld, p_72726_2_, p_72726_4_, p_72726_6_, Items.snowball);
        }
        else if (p_72726_1_.equalsIgnoreCase("dripWater")) {
            entityfx = (EntityFX)new EntityDropParticleFX((World)mc.theWorld, p_72726_2_, p_72726_4_, p_72726_6_, Material.water);
        }
        else if (p_72726_1_.equalsIgnoreCase("dripLava")) {
            entityfx = (EntityFX)new EntityDropParticleFX((World)mc.theWorld, p_72726_2_, p_72726_4_, p_72726_6_, Material.lava);
        }
        else if (p_72726_1_.equalsIgnoreCase("snowshovel")) {
            entityfx = (EntityFX)new EntitySnowShovelFX((World)mc.theWorld, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
        }
        else if (p_72726_1_.equalsIgnoreCase("slime")) {
            entityfx = (EntityFX)new EntityBreakingFX((World)mc.theWorld, p_72726_2_, p_72726_4_, p_72726_6_, Items.slime_ball);
        }
        else if (p_72726_1_.equalsIgnoreCase("heart")) {
            entityfx = (EntityFX)new EntityHeartFX((World)mc.theWorld, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
        }
        else if (p_72726_1_.equalsIgnoreCase("angryVillager")) {
            entityfx = (EntityFX)new EntityHeartFX((World)mc.theWorld, p_72726_2_, p_72726_4_ + 0.5, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
            entityfx.setParticleTextureIndex(81);
            entityfx.setRBGColorF(1.0f, 1.0f, 1.0f);
        }
        else if (p_72726_1_.equalsIgnoreCase("happyVillager")) {
            entityfx = (EntityFX)new EntityAuraFX((World)mc.theWorld, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
            entityfx.setParticleTextureIndex(82);
            entityfx.setRBGColorF(1.0f, 1.0f, 1.0f);
        }
        else if (p_72726_1_.startsWith("iconcrack_")) {
            final String[] astring = p_72726_1_.split("_", 3);
            final int j = Integer.parseInt(astring[1]);
            if (astring.length > 2) {
                final int k = Integer.parseInt(astring[2]);
                entityfx = (EntityFX)new EntityBreakingFX((World)mc.theWorld, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_, Item.getItemById(j), k);
            }
            else {
                entityfx = (EntityFX)new EntityBreakingFX((World)mc.theWorld, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_, Item.getItemById(j), 0);
            }
        }
        else if (p_72726_1_.startsWith("blockcrack_")) {
            final String[] astring = p_72726_1_.split("_", 3);
            final Block block = Block.getBlockById(Integer.parseInt(astring[1]));
            final int k = Integer.parseInt(astring[2]);
            entityfx = (EntityFX)new EntityDiggingFX((World)mc.theWorld, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_, block, k).applyRenderColor(k);
        }
        else if (p_72726_1_.startsWith("blockdust_")) {
            final String[] astring = p_72726_1_.split("_", 3);
            final Block block = Block.getBlockById(Integer.parseInt(astring[1]));
            final int k = Integer.parseInt(astring[2]);
            entityfx = (EntityFX)new MCH_EntityBlockDustFX((World)mc.theWorld, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_, block, k).applyRenderColor(k);
        }
        if (entityfx != null) {
            mc.effectRenderer.addEffect(entityfx);
        }
        return entityfx;
    }
    
    public static void spawnParticle(final MCH_ParticleParam p) {
        if (p.world.isRemote) {
            MCH_EntityParticleBase entityFX = null;
            if (p.name.equalsIgnoreCase("Splash")) {
                entityFX = (MCH_EntityParticleBase)new MCH_EntityParticleSplash(p.world, p.posX, p.posY, p.posZ, p.motionX, p.motionY, p.motionZ);
            }
            else {
                entityFX = (MCH_EntityParticleBase)new MCH_EntityParticleSmoke(p.world, p.posX, p.posY, p.posZ, p.motionX, p.motionY, p.motionZ);
            }
            entityFX.setRBGColorF(p.r, p.g, p.b);
            entityFX.setAlphaF(p.a);
            if (p.age > 0) {
                entityFX.setParticleMaxAge(p.age);
            }
            entityFX.moutionYUpAge = p.motionYUpAge;
            entityFX.gravity = p.gravity;
            entityFX.isEffectedWind = p.isEffectWind;
            entityFX.diffusible = p.diffusible;
            entityFX.toWhite = p.toWhite;
            if (p.diffusible) {
                entityFX.setParticleScale(p.size * 0.2f);
                entityFX.particleMaxScale = p.size * 2.0f;
            }
            else {
                entityFX.setParticleScale(p.size);
            }
            FMLClientHandler.instance().getClient().effectRenderer.addEffect((EntityFX)entityFX);
        }
    }
    
    public static void spawnMarkPoint(final EntityPlayer player, final double x, final double y, final double z) {
        clearMarkPoint();
        MCH_ParticlesUtil.markPoint = new MCH_EntityParticleMarkPoint(player.worldObj, x, y, z, player.getTeam());
        FMLClientHandler.instance().getClient().effectRenderer.addEffect((EntityFX)MCH_ParticlesUtil.markPoint);
    }
    
    public static void clearMarkPoint() {
        if (MCH_ParticlesUtil.markPoint != null) {
            MCH_ParticlesUtil.markPoint.setDead();
            MCH_ParticlesUtil.markPoint = null;
        }
    }
    
    static {
        MCH_ParticlesUtil.markPoint = null;
    }
}
