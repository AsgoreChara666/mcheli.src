//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.weapon;

import cpw.mods.fml.relauncher.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.*;

@SideOnly(Side.CLIENT)
public class MCH_RenderBomb extends MCH_RenderBulletBase
{
    public MCH_RenderBomb() {
        this.shadowSize = 0.0f;
    }
    
    @Override
    public void renderBullet(final Entity entity, final double posX, final double posY, final double posZ, final float yaw, final float partialTickTime) {
        if (!(entity instanceof MCH_EntityBomb)) {
            return;
        }
        final MCH_EntityBomb bomb = (MCH_EntityBomb)entity;
        if (bomb.getInfo() == null) {
            return;
        }
        GL11.glPushMatrix();
        GL11.glTranslated(posX, posY, posZ);
        GL11.glRotatef(-entity.rotationYaw, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-entity.rotationPitch, -1.0f, 0.0f, 0.0f);
        if (bomb.isBomblet > 0 || bomb.getInfo().bomblet <= 0 || bomb.getInfo().bombletSTime > 0) {
            this.renderModel((MCH_EntityBaseBullet)bomb);
        }
        GL11.glPopMatrix();
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return MCH_RenderBomb.TEX_DEFAULT;
    }
}
