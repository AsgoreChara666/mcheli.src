//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.throwable;

import mcheli.wrapper.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.*;

@SideOnly(Side.CLIENT)
public class MCH_RenderThrowable extends W_Render
{
    public MCH_RenderThrowable() {
        this.shadowSize = 0.0f;
    }
    
    public void doRender(final Entity entity, final double posX, final double posY, final double posZ, final float par8, final float tickTime) {
        final MCH_EntityThrowable throwable = (MCH_EntityThrowable)entity;
        final MCH_ThrowableInfo info = throwable.getInfo();
        if (info == null) {
            return;
        }
        GL11.glPushMatrix();
        GL11.glTranslated(posX, posY, posZ);
        GL11.glRotatef(entity.rotationYaw, 0.0f, -1.0f, 0.0f);
        GL11.glRotatef(entity.rotationPitch, 1.0f, 0.0f, 0.0f);
        this.setCommonRenderParam(true, entity.getBrightnessForRender(tickTime));
        if (info.model != null) {
            this.bindTexture("textures/throwable/" + info.name + ".png");
            info.model.renderAll();
        }
        this.restoreCommonRenderParam();
        GL11.glPopMatrix();
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return MCH_RenderThrowable.TEX_DEFAULT;
    }
}
