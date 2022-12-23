//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.weapon;

import cpw.mods.fml.relauncher.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import mcheli.*;
import net.minecraft.util.*;

@SideOnly(Side.CLIENT)
public class MCH_RenderAAMissile extends MCH_RenderBulletBase
{
    public MCH_RenderAAMissile() {
        this.shadowSize = 0.5f;
    }
    
    @Override
    public void renderBullet(final Entity entity, final double posX, final double posY, final double posZ, final float par8, final float par9) {
        if (!(entity instanceof MCH_EntityAAMissile)) {
            return;
        }
        final MCH_EntityAAMissile aam = (MCH_EntityAAMissile)entity;
        final double mx = aam.prevMotionX + (aam.motionX - aam.prevMotionX) * par9;
        final double my = aam.prevMotionY + (aam.motionY - aam.prevMotionY) * par9;
        final double mz = aam.prevMotionZ + (aam.motionZ - aam.prevMotionZ) * par9;
        GL11.glPushMatrix();
        GL11.glTranslated(posX, posY, posZ);
        final Vec3 v = MCH_Lib.getYawPitchFromVec(mx, my, mz);
        GL11.glRotatef((float)v.yCoord - 90.0f, 0.0f, -1.0f, 0.0f);
        GL11.glRotatef((float)v.zCoord, -1.0f, 0.0f, 0.0f);
        this.renderModel((MCH_EntityBaseBullet)aam);
        GL11.glPopMatrix();
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return MCH_RenderAAMissile.TEX_DEFAULT;
    }
}
