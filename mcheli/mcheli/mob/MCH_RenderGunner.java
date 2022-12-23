//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.mob;

import net.minecraft.client.renderer.entity.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.item.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.entity.*;
import net.minecraft.entity.*;
import mcheli.aircraft.*;
import net.minecraft.entity.player.*;

@SideOnly(Side.CLIENT)
public class MCH_RenderGunner extends RendererLivingEntity
{
    private static final ResourceLocation steveTextures;
    public ModelBiped modelBipedMain;
    public ModelBiped modelArmorChestplate;
    public ModelBiped modelArmor;
    
    public MCH_RenderGunner() {
        super((ModelBase)new ModelBiped(0.0f), 0.5f);
        this.modelBipedMain = (ModelBiped)this.mainModel;
        this.modelArmorChestplate = new ModelBiped(1.0f);
        this.modelArmor = new ModelBiped(0.5f);
    }
    
    protected int shouldRenderPass(final EntityLivingBase p_77032_1_, final int p_77032_2_, final float p_77032_3_) {
        final ItemStack itemstack = null;
        return -1;
    }
    
    protected boolean canRenderName(final EntityLivingBase targetEntity) {
        return targetEntity.getTeam() != null;
    }
    
    public void doRender(final EntityLivingBase p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        final ModelBiped modelArmorChestplate = this.modelArmorChestplate;
        final ModelBiped modelArmor = this.modelArmor;
        final ModelBiped modelBipedMain = this.modelBipedMain;
        final boolean isSneaking = p_76986_1_.isSneaking();
        modelBipedMain.isSneak = isSneaking;
        modelArmor.isSneak = isSneaking;
        modelArmorChestplate.isSneak = isSneaking;
        double d3 = p_76986_4_ - p_76986_1_.yOffset;
        if (p_76986_1_.isSneaking() && !(p_76986_1_ instanceof EntityPlayerSP)) {
            d3 -= 0.125;
        }
        final MCH_EntityAircraft ac = ((MCH_EntityGunner)p_76986_1_).getAc();
        if (ac != null && ac.getAcInfo() != null) {
            if (!ac.getAcInfo().hideEntity || !ac.isPilot((Entity)p_76986_1_)) {
                super.doRender(p_76986_1_, p_76986_2_, d3, p_76986_6_, p_76986_8_, p_76986_9_);
            }
        }
        final ModelBiped modelArmorChestplate2 = this.modelArmorChestplate;
        final ModelBiped modelArmor2 = this.modelArmor;
        final ModelBiped modelBipedMain2 = this.modelBipedMain;
        final boolean aimedBow = false;
        modelBipedMain2.aimedBow = aimedBow;
        modelArmor2.aimedBow = aimedBow;
        modelArmorChestplate2.aimedBow = aimedBow;
        final ModelBiped modelArmorChestplate3 = this.modelArmorChestplate;
        final ModelBiped modelArmor3 = this.modelArmor;
        final ModelBiped modelBipedMain3 = this.modelBipedMain;
        final boolean isSneak = false;
        modelBipedMain3.isSneak = isSneak;
        modelArmor3.isSneak = isSneak;
        modelArmorChestplate3.isSneak = isSneak;
        final ModelBiped modelArmorChestplate4 = this.modelArmorChestplate;
        final ModelBiped modelArmor4 = this.modelArmor;
        final ModelBiped modelBipedMain4 = this.modelBipedMain;
        final int heldItemRight = 0;
        modelBipedMain4.heldItemRight = heldItemRight;
        modelArmor4.heldItemRight = heldItemRight;
        modelArmorChestplate4.heldItemRight = heldItemRight;
    }
    
    protected void renderEquippedItems(final EntityLivingBase p_77029_1_, final float p_77029_2_) {
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        super.renderEquippedItems(p_77029_1_, p_77029_2_);
        super.renderArrowsStuckInEntity(p_77029_1_, p_77029_2_);
        final ItemStack itemstack = null;
        final boolean flag = false;
        final ItemStack itemstack2 = null;
    }
    
    protected void preRenderCallback(final EntityLivingBase p_77041_1_, final float p_77041_2_) {
        final float f1 = 0.9375f;
        GL11.glScalef(f1, f1, f1);
    }
    
    public void renderFirstPersonArm(final EntityPlayer p_82441_1_) {
        final float f = 1.0f;
        GL11.glColor3f(f, f, f);
        this.modelBipedMain.swingProgress = 0.0f;
        this.modelBipedMain.setRotationAngles(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f, (Entity)p_82441_1_);
        this.modelBipedMain.bipedRightArm.render(0.0625f);
    }
    
    protected void _renderOffsetLivingLabel(final EntityLivingBase p_96449_1_, final double p_96449_2_, final double p_96449_4_, final double p_96449_6_, final String p_96449_8_, final float p_96449_9_, final double p_96449_10_) {
        this.renderOffsetLivingLabel(p_96449_1_, p_96449_2_, p_96449_4_, p_96449_6_, p_96449_8_, p_96449_9_, p_96449_10_);
    }
    
    protected void _preRenderCallback(final EntityLivingBase p_77041_1_, final float p_77041_2_) {
        this.preRenderCallback(p_77041_1_, p_77041_2_);
    }
    
    protected void _func_82408_c(final EntityLivingBase p_82408_1_, final int p_82408_2_, final float p_82408_3_) {
        this.func_82408_c(p_82408_1_, p_82408_2_, p_82408_3_);
    }
    
    protected int _shouldRenderPass(final EntityLivingBase p_77032_1_, final int p_77032_2_, final float p_77032_3_) {
        return this.shouldRenderPass(p_77032_1_, p_77032_2_, p_77032_3_);
    }
    
    protected void _renderEquippedItems(final EntityLivingBase p_77029_1_, final float p_77029_2_) {
        this.renderEquippedItems(p_77029_1_, p_77029_2_);
    }
    
    protected void _rotateCorpse(final EntityLivingBase p_77043_1_, final float p_77043_2_, final float p_77043_3_, final float p_77043_4_) {
        this.rotateCorpse(p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
    }
    
    protected void _renderLivingAt(final EntityLivingBase p_77039_1_, final double p_77039_2_, final double p_77039_4_, final double p_77039_6_) {
        this.renderLivingAt(p_77039_1_, p_77039_2_, p_77039_4_, p_77039_6_);
    }
    
    public void _doRender(final EntityLivingBase p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.doRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    protected ResourceLocation getEntityTexture(final Entity p_110775_1_) {
        return MCH_RenderGunner.steveTextures;
    }
    
    public void _doRender(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.doRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    static {
        steveTextures = new ResourceLocation("mcheli", "textures/mob/heligunner.png");
    }
}
