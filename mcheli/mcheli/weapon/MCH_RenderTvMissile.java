//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.weapon;

import cpw.mods.fml.relauncher.*;
import net.minecraft.entity.*;
import net.minecraft.client.*;
import mcheli.aircraft.*;
import mcheli.uav.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.*;

@SideOnly(Side.CLIENT)
public class MCH_RenderTvMissile extends MCH_RenderBulletBase
{
    public MCH_RenderTvMissile() {
        this.shadowSize = 0.5f;
    }
    
    public void renderBullet(final Entity entity, final double posX, final double posY, final double posZ, final float par8, final float par9) {
        MCH_EntityAircraft ac = null;
        final Entity ridingEntity = Minecraft.getMinecraft().thePlayer.ridingEntity;
        if (ridingEntity instanceof MCH_EntityAircraft) {
            ac = (MCH_EntityAircraft)ridingEntity;
        }
        else if (ridingEntity instanceof MCH_EntitySeat) {
            ac = ((MCH_EntitySeat)ridingEntity).getParent();
        }
        else if (ridingEntity instanceof MCH_EntityUavStation) {
            ac = ((MCH_EntityUavStation)ridingEntity).getControlAircract();
        }
        if (ac != null && !ac.isRenderBullet(entity, (Entity)Minecraft.getMinecraft().thePlayer)) {
            return;
        }
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
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return MCH_RenderTvMissile.TEX_DEFAULT;
    }
}
