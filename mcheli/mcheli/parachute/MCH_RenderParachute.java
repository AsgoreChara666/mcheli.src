//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.parachute;

import mcheli.wrapper.*;
import cpw.mods.fml.relauncher.*;
import java.util.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import mcheli.*;
import net.minecraft.util.*;

@SideOnly(Side.CLIENT)
public class MCH_RenderParachute extends W_Render
{
    public static final Random rand;
    
    public MCH_RenderParachute() {
        this.shadowSize = 0.5f;
    }
    
    public void doRender(final Entity entity, final double posX, final double posY, final double posZ, final float par8, final float tickTime) {
        if (!(entity instanceof MCH_EntityParachute)) {
            return;
        }
        final MCH_EntityParachute parachute = (MCH_EntityParachute)entity;
        final int type = parachute.getType();
        if (type <= 0) {
            return;
        }
        GL11.glPushMatrix();
        GL11.glEnable(2884);
        GL11.glTranslated(posX, posY, posZ);
        float prevYaw = entity.prevRotationYaw;
        if (entity.rotationYaw - prevYaw < -180.0f) {
            prevYaw -= 360.0f;
        }
        else if (prevYaw - entity.rotationYaw < -180.0f) {
            prevYaw += 360.0f;
        }
        final float yaw = prevYaw + (entity.rotationYaw - prevYaw) * tickTime;
        GL11.glRotatef(yaw, 0.0f, -1.0f, 0.0f);
        GL11.glColor4f(0.75f, 0.75f, 0.75f, 1.0f);
        GL11.glEnable(3042);
        final int srcBlend = GL11.glGetInteger(3041);
        final int dstBlend = GL11.glGetInteger(3040);
        GL11.glBlendFunc(770, 771);
        final MCH_Config config = MCH_MOD.config;
        if (MCH_Config.SmoothShading.prmBool) {
            GL11.glShadeModel(7425);
        }
        switch (type) {
            case 1: {
                this.bindTexture("textures/parachute1.png");
                MCH_ModelManager.render("parachute1");
                break;
            }
            case 2: {
                this.bindTexture("textures/parachute2.png");
                if (parachute.isOpenParachute()) {
                    MCH_ModelManager.renderPart("parachute2", "$parachute");
                    break;
                }
                MCH_ModelManager.renderPart("parachute2", "$seat");
                break;
            }
            case 3: {
                this.bindTexture("textures/parachute2.png");
                MCH_ModelManager.renderPart("parachute2", "$parachute");
                break;
            }
        }
        GL11.glBlendFunc(srcBlend, dstBlend);
        GL11.glDisable(3042);
        GL11.glShadeModel(7424);
        GL11.glPopMatrix();
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return MCH_RenderParachute.TEX_DEFAULT;
    }
    
    static {
        rand = new Random();
    }
}
