//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.flare;

import mcheli.wrapper.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.*;

@SideOnly(Side.CLIENT)
public class MCH_RenderFlare extends W_Render
{
    protected MCH_ModelFlare model;
    
    public MCH_RenderFlare() {
        this.model = new MCH_ModelFlare();
    }
    
    public void doRender(final Entity entity, final double posX, final double posY, final double posZ, final float yaw, final float partialTickTime) {
        GL11.glPushMatrix();
        GL11.glEnable(2884);
        final double x = entity.prevPosX + entity.motionX * partialTickTime;
        final double y = entity.prevPosY + entity.motionY * partialTickTime;
        final double z = entity.prevPosZ + entity.motionZ * partialTickTime;
        GL11.glTranslated(posX, posY, posZ);
        GL11.glRotatef(-entity.rotationYaw, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(entity.rotationPitch, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(45.0f, 0.0f, 0.0f, 1.0f);
        GL11.glColor4f(1.0f, 1.0f, 0.5f, 1.0f);
        this.bindTexture("textures/flare.png");
        this.model.renderModel(0.0, 0.0, 0.0625f);
        GL11.glPopMatrix();
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return MCH_RenderFlare.TEX_DEFAULT;
    }
}
