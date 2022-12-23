//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.gltd;

import cpw.mods.fml.relauncher.*;
import java.util.*;
import net.minecraftforge.client.model.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.*;
import mcheli.wrapper.*;
import mcheli.*;
import net.minecraft.util.*;

@SideOnly(Side.CLIENT)
public class MCH_RenderGLTD extends W_Render
{
    public static final Random rand;
    public static IModelCustom model;
    
    public MCH_RenderGLTD() {
        this.shadowSize = 0.5f;
        MCH_RenderGLTD.model = null;
    }
    
    public void doRender(final Entity entity, final double posX, final double posY, final double posZ, final float par8, final float tickTime) {
        if (!(entity instanceof MCH_EntityGLTD)) {
            return;
        }
        final MCH_EntityGLTD gltd = (MCH_EntityGLTD)entity;
        GL11.glPushMatrix();
        GL11.glTranslated(posX, posY, posZ);
        this.setCommonRenderParam(true, entity.getBrightnessForRender(tickTime));
        this.bindTexture("textures/gltd.png");
        final Minecraft mc = Minecraft.getMinecraft();
        boolean isNotRenderHead = false;
        if (gltd.riddenByEntity != null) {
            gltd.isUsedPlayer = true;
            gltd.renderRotaionYaw = gltd.riddenByEntity.rotationYaw;
            gltd.renderRotaionPitch = gltd.riddenByEntity.rotationPitch;
            isNotRenderHead = (mc.gameSettings.thirdPersonView == 0 && W_Lib.isClientPlayer(gltd.riddenByEntity));
        }
        if (gltd.isUsedPlayer) {
            GL11.glPushMatrix();
            GL11.glRotatef(-gltd.rotationYaw, 0.0f, 1.0f, 0.0f);
            MCH_RenderGLTD.model.renderPart("$body");
            GL11.glPopMatrix();
        }
        else {
            GL11.glRotatef(-gltd.rotationYaw, 0.0f, 1.0f, 0.0f);
            MCH_RenderGLTD.model.renderPart("$body");
        }
        GL11.glTranslatef(0.0f, 0.45f, 0.0f);
        if (gltd.isUsedPlayer) {
            GL11.glRotatef(gltd.renderRotaionYaw, 0.0f, -1.0f, 0.0f);
            GL11.glRotatef(gltd.renderRotaionPitch, 1.0f, 0.0f, 0.0f);
        }
        GL11.glTranslatef(0.0f, -0.45f, 0.0f);
        if (!isNotRenderHead) {
            MCH_RenderGLTD.model.renderPart("$head");
        }
        GL11.glTranslatef(0.0f, 0.45f, 0.0f);
        this.restoreCommonRenderParam();
        GL11.glDisable(2896);
        final Vec3[] v = { Vec3.createVectorHelper(0.0, 0.2, 0.0), Vec3.createVectorHelper(0.0, 0.2, 100.0) };
        final int a = MCH_RenderGLTD.rand.nextInt(64);
        MCH_RenderLib.drawLine(v, 0x6080FF80 | a << 24);
        GL11.glEnable(2896);
        GL11.glPopMatrix();
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return MCH_RenderGLTD.TEX_DEFAULT;
    }
    
    static {
        rand = new Random();
    }
}
