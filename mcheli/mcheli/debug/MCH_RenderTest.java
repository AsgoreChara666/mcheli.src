//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.debug;

import mcheli.wrapper.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.entity.*;
import mcheli.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.*;

@SideOnly(Side.CLIENT)
public class MCH_RenderTest extends W_Render
{
    protected MCH_ModelTest model;
    private float offsetX;
    private float offsetY;
    private float offsetZ;
    private String textureName;
    
    public MCH_RenderTest(final float x, final float y, final float z, final String texture_name) {
        this.offsetX = x;
        this.offsetY = y;
        this.offsetZ = z;
        this.textureName = texture_name;
        this.model = new MCH_ModelTest();
    }
    
    public void doRender(final Entity e, final double posX, final double posY, final double posZ, final float par8, final float par9) {
        final MCH_Config config = MCH_MOD.config;
        if (!MCH_Config.TestMode.prmBool) {
            return;
        }
        GL11.glPushMatrix();
        GL11.glTranslated(posX + this.offsetX, posY + this.offsetY, posZ + this.offsetZ);
        GL11.glScalef(e.width, e.height, e.width);
        GL11.glColor4f(0.5f, 0.5f, 0.5f, 1.0f);
        float prevYaw;
        if (e.rotationYaw - e.prevRotationYaw < -180.0f) {
            prevYaw = e.prevRotationYaw - 360.0f;
        }
        else if (e.prevRotationYaw - e.rotationYaw < -180.0f) {
            prevYaw = e.prevRotationYaw + 360.0f;
        }
        else {
            prevYaw = e.prevRotationYaw;
        }
        final float yaw = -(prevYaw + (e.rotationYaw - prevYaw) * par9) - 180.0f;
        final float pitch = -(e.prevRotationPitch + (e.rotationPitch - e.prevRotationPitch) * par9);
        GL11.glRotatef(yaw, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(pitch, 1.0f, 0.0f, 0.0f);
        this.bindTexture("textures/" + this.textureName + ".png");
        this.model.renderModel(0.0, 0.0, 0.1f);
        GL11.glPopMatrix();
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return MCH_RenderTest.TEX_DEFAULT;
    }
}
