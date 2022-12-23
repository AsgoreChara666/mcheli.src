//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.tank;

import cpw.mods.fml.relauncher.*;
import mcheli.aircraft.*;
import org.lwjgl.opengl.*;
import mcheli.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

@SideOnly(Side.CLIENT)
public class MCH_RenderTank extends MCH_RenderAircraft
{
    public MCH_RenderTank() {
        this.shadowSize = 2.0f;
    }
    
    public void renderAircraft(final MCH_EntityAircraft entity, final double posX, final double posY, final double posZ, final float yaw, final float pitch, final float roll, final float tickTime) {
        MCH_TankInfo tankInfo = null;
        if (entity == null || !(entity instanceof MCH_EntityTank)) {
            return;
        }
        final MCH_EntityTank tank = (MCH_EntityTank)entity;
        tankInfo = tank.getTankInfo();
        if (tankInfo == null) {
            return;
        }
        this.renderWheel(tank, posX, posY, posZ);
        this.renderDebugHitBox((MCH_EntityAircraft)tank, posX, posY, posZ, yaw, pitch);
        this.renderDebugPilotSeat((MCH_EntityAircraft)tank, posX, posY, posZ, yaw, pitch, roll);
        GL11.glTranslated(posX, posY, posZ);
        GL11.glRotatef(yaw, 0.0f, -1.0f, 0.0f);
        GL11.glRotatef(pitch, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(roll, 0.0f, 0.0f, 1.0f);
        this.bindTexture("textures/tanks/" + tank.getTextureName() + ".png", (MCH_EntityAircraft)tank);
        renderBody(tankInfo.model);
    }
    
    public void renderWheel(final MCH_EntityTank tank, final double posX, final double posY, final double posZ) {
        final MCH_Config config = MCH_MOD.config;
        if (!MCH_Config.TestMode.prmBool) {
            return;
        }
        if (MCH_RenderTank.debugModel == null) {
            return;
        }
        GL11.glColor4f(0.75f, 0.75f, 0.75f, 0.5f);
        for (final MCH_EntityWheel w : tank.WheelMng.wheels) {
            GL11.glPushMatrix();
            GL11.glTranslated(w.posX - tank.posX + posX, w.posY - tank.posY + posY + 0.25, w.posZ - tank.posZ + posZ);
            GL11.glScalef(w.width, w.height / 2.0f, w.width);
            this.bindTexture("textures/seat_pilot.png");
            MCH_RenderTank.debugModel.renderAll();
            GL11.glPopMatrix();
        }
        GL11.glColor4f(0.75f, 0.75f, 0.75f, 1.0f);
        final Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawing(1);
        final Vec3 transformedPosition;
        final Vec3 wp = transformedPosition = tank.getTransformedPosition(tank.WheelMng.weightedCenter);
        transformedPosition.xCoord -= tank.posX;
        final Vec3 vec3 = wp;
        vec3.yCoord -= tank.posY;
        final Vec3 vec4 = wp;
        vec4.zCoord -= tank.posZ;
        for (int i = 0; i < tank.WheelMng.wheels.length / 2; ++i) {
            tessellator.setColorRGBA_I((((i & 0x4) > 0) ? 16711680 : 0) | (((i & 0x2) > 0) ? 65280 : 0) | (((i & 0x1) > 0) ? 255 : 0), 192);
            final MCH_EntityWheel w2 = tank.WheelMng.wheels[i * 2 + 0];
            final MCH_EntityWheel w3 = tank.WheelMng.wheels[i * 2 + 1];
            if (w2.isPlus) {
                tessellator.addVertex(w3.posX - tank.posX + posX, w3.posY - tank.posY + posY, w3.posZ - tank.posZ + posZ);
                tessellator.addVertex(w2.posX - tank.posX + posX, w2.posY - tank.posY + posY, w2.posZ - tank.posZ + posZ);
                tessellator.addVertex(w2.posX - tank.posX + posX, w2.posY - tank.posY + posY, w2.posZ - tank.posZ + posZ);
                tessellator.addVertex(posX + wp.xCoord, posY + wp.yCoord, posZ + wp.zCoord);
                tessellator.addVertex(posX + wp.xCoord, posY + wp.yCoord, posZ + wp.zCoord);
                tessellator.addVertex(w3.posX - tank.posX + posX, w3.posY - tank.posY + posY, w3.posZ - tank.posZ + posZ);
            }
            else {
                tessellator.addVertex(w2.posX - tank.posX + posX, w2.posY - tank.posY + posY, w2.posZ - tank.posZ + posZ);
                tessellator.addVertex(w3.posX - tank.posX + posX, w3.posY - tank.posY + posY, w3.posZ - tank.posZ + posZ);
                tessellator.addVertex(w3.posX - tank.posX + posX, w3.posY - tank.posY + posY, w3.posZ - tank.posZ + posZ);
                tessellator.addVertex(posX + wp.xCoord, posY + wp.yCoord, posZ + wp.zCoord);
                tessellator.addVertex(posX + wp.xCoord, posY + wp.yCoord, posZ + wp.zCoord);
                tessellator.addVertex(w2.posX - tank.posX + posX, w2.posY - tank.posY + posY, w2.posZ - tank.posZ + posZ);
            }
        }
        tessellator.draw();
    }
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return MCH_RenderTank.TEX_DEFAULT;
    }
}
