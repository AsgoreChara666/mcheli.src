//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.weapon;

import cpw.mods.fml.relauncher.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import mcheli.*;
import net.minecraft.util.*;

@SideOnly(Side.CLIENT)
public class MCH_RenderA10 extends MCH_RenderBulletBase
{
    public MCH_RenderA10() {
        this.shadowSize = 10.5f;
    }
    
    @Override
    public void renderBullet(final Entity e, final double posX, final double posY, final double posZ, final float par8, final float tickTime) {
        if (!(e instanceof MCH_EntityA10)) {
            return;
        }
        if (!((MCH_EntityA10)e).isRender()) {
            return;
        }
        GL11.glPushMatrix();
        GL11.glTranslated(posX, posY, posZ);
        final float yaw = -(e.prevRotationYaw + (e.rotationYaw - e.prevRotationYaw) * tickTime);
        final float pitch = -(e.prevRotationPitch + (e.rotationPitch - e.prevRotationPitch) * tickTime);
        GL11.glRotatef(yaw, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(pitch, 1.0f, 0.0f, 0.0f);
        this.bindTexture("textures/bullets/a10.png");
        MCH_ModelManager.render("a-10");
        GL11.glPopMatrix();
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return MCH_RenderA10.TEX_DEFAULT;
    }
}
