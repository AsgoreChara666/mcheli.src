//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.weapon;

import cpw.mods.fml.relauncher.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.*;

@SideOnly(Side.CLIENT)
public class MCH_RenderASMissile extends MCH_RenderBulletBase
{
    public MCH_RenderASMissile() {
        this.shadowSize = 0.5f;
    }
    
    @Override
    public void renderBullet(final Entity entity, final double posX, final double posY, final double posZ, final float yaw, final float partialTickTime) {
        if (entity instanceof MCH_EntityBaseBullet) {
            final MCH_EntityBaseBullet bullet = (MCH_EntityBaseBullet)entity;
            GL11.glPushMatrix();
            GL11.glTranslated(posX, posY, posZ);
            GL11.glRotatef(-entity.rotationYaw, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(-entity.rotationPitch, -1.0f, 0.0f, 0.0f);
            this.renderModel(bullet);
            GL11.glPopMatrix();
        }
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return MCH_RenderASMissile.TEX_DEFAULT;
    }
}
