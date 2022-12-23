//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.weapon;

import cpw.mods.fml.relauncher.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.*;

@SideOnly(Side.CLIENT)
public class MCH_RenderBullet extends MCH_RenderBulletBase
{
    @Override
    public void renderBullet(final Entity entity, final double posX, final double posY, final double posZ, final float yaw, final float tickTime) {
        final MCH_EntityBaseBullet blt = (MCH_EntityBaseBullet)entity;
        GL11.glPushMatrix();
        final double x = entity.prevPosX + entity.motionX * tickTime;
        final double y = entity.prevPosY + entity.motionY * tickTime;
        final double z = entity.prevPosZ + entity.motionZ * tickTime;
        GL11.glTranslated(posX, posY, posZ);
        GL11.glRotatef(-entity.rotationYaw, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(entity.rotationPitch, 1.0f, 0.0f, 0.0f);
        this.renderModel(blt);
        GL11.glPopMatrix();
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return MCH_RenderBullet.TEX_DEFAULT;
    }
}
