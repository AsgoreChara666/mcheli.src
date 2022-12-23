//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.plane;

import cpw.mods.fml.relauncher.*;
import org.lwjgl.opengl.*;
import java.util.*;
import mcheli.aircraft.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

@SideOnly(Side.CLIENT)
public class MCP_RenderPlane extends MCH_RenderAircraft
{
    public MCP_RenderPlane() {
        this.shadowSize = 2.0f;
    }
    
    public void renderAircraft(final MCH_EntityAircraft entity, final double posX, final double posY, final double posZ, final float yaw, final float pitch, final float roll, final float tickTime) {
        MCP_PlaneInfo planeInfo = null;
        if (entity == null || !(entity instanceof MCP_EntityPlane)) {
            return;
        }
        final MCP_EntityPlane plane = (MCP_EntityPlane)entity;
        planeInfo = plane.getPlaneInfo();
        if (planeInfo == null) {
            return;
        }
        this.renderDebugHitBox((MCH_EntityAircraft)plane, posX, posY, posZ, yaw, pitch);
        this.renderDebugPilotSeat((MCH_EntityAircraft)plane, posX, posY, posZ, yaw, pitch, roll);
        GL11.glTranslated(posX, posY, posZ);
        GL11.glRotatef(yaw, 0.0f, -1.0f, 0.0f);
        GL11.glRotatef(pitch, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(roll, 0.0f, 0.0f, 1.0f);
        this.bindTexture("textures/planes/" + plane.getTextureName() + ".png", (MCH_EntityAircraft)plane);
        if (planeInfo.haveNozzle() && plane.partNozzle != null) {
            this.renderNozzle(plane, planeInfo, tickTime);
        }
        if (planeInfo.haveWing() && plane.partWing != null) {
            this.renderWing(plane, planeInfo, tickTime);
        }
        if (planeInfo.haveRotor() && plane.partNozzle != null) {
            this.renderRotor(plane, planeInfo, tickTime);
        }
        renderBody(planeInfo.model);
    }
    
    public void renderRotor(final MCP_EntityPlane plane, final MCP_PlaneInfo planeInfo, final float tickTime) {
        final float rot = plane.getNozzleRotation();
        final float prevRot = plane.getPrevNozzleRotation();
        for (final MCP_PlaneInfo.Rotor r : planeInfo.rotorList) {
            GL11.glPushMatrix();
            GL11.glTranslated(r.pos.xCoord, r.pos.yCoord, r.pos.zCoord);
            GL11.glRotatef((prevRot + (rot - prevRot) * tickTime) * r.maxRotFactor, (float)r.rot.xCoord, (float)r.rot.yCoord, (float)r.rot.zCoord);
            GL11.glTranslated(-r.pos.xCoord, -r.pos.yCoord, -r.pos.zCoord);
            renderPart(r.model, planeInfo.model, r.modelName);
            for (final MCP_PlaneInfo.Blade b : r.blades) {
                float br = plane.prevRotationRotor;
                br += (plane.rotationRotor - plane.prevRotationRotor) * tickTime;
                GL11.glPushMatrix();
                GL11.glTranslated(b.pos.xCoord, b.pos.yCoord, b.pos.zCoord);
                GL11.glRotatef(br, (float)b.rot.xCoord, (float)b.rot.yCoord, (float)b.rot.zCoord);
                GL11.glTranslated(-b.pos.xCoord, -b.pos.yCoord, -b.pos.zCoord);
                for (int i = 0; i < b.numBlade; ++i) {
                    GL11.glTranslated(b.pos.xCoord, b.pos.yCoord, b.pos.zCoord);
                    GL11.glRotatef((float)b.rotBlade, (float)b.rot.xCoord, (float)b.rot.yCoord, (float)b.rot.zCoord);
                    GL11.glTranslated(-b.pos.xCoord, -b.pos.yCoord, -b.pos.zCoord);
                    renderPart(b.model, planeInfo.model, b.modelName);
                }
                GL11.glPopMatrix();
            }
            GL11.glPopMatrix();
        }
    }
    
    public void renderWing(final MCP_EntityPlane plane, final MCP_PlaneInfo planeInfo, final float tickTime) {
        final float rot = plane.getWingRotation();
        final float prevRot = plane.getPrevWingRotation();
        for (final MCP_PlaneInfo.Wing w : planeInfo.wingList) {
            GL11.glPushMatrix();
            GL11.glTranslated(w.pos.xCoord, w.pos.yCoord, w.pos.zCoord);
            GL11.glRotatef((prevRot + (rot - prevRot) * tickTime) * w.maxRotFactor, (float)w.rot.xCoord, (float)w.rot.yCoord, (float)w.rot.zCoord);
            GL11.glTranslated(-w.pos.xCoord, -w.pos.yCoord, -w.pos.zCoord);
            renderPart(w.model, planeInfo.model, w.modelName);
            if (w.pylonList != null) {
                for (final MCP_PlaneInfo.Pylon p : w.pylonList) {
                    GL11.glPushMatrix();
                    GL11.glTranslated(p.pos.xCoord, p.pos.yCoord, p.pos.zCoord);
                    GL11.glRotatef((prevRot + (rot - prevRot) * tickTime) * p.maxRotFactor, (float)p.rot.xCoord, (float)p.rot.yCoord, (float)p.rot.zCoord);
                    GL11.glTranslated(-p.pos.xCoord, -p.pos.yCoord, -p.pos.zCoord);
                    renderPart(p.model, planeInfo.model, p.modelName);
                    GL11.glPopMatrix();
                }
            }
            GL11.glPopMatrix();
        }
    }
    
    public void renderNozzle(final MCP_EntityPlane plane, final MCP_PlaneInfo planeInfo, final float tickTime) {
        final float rot = plane.getNozzleRotation();
        final float prevRot = plane.getPrevNozzleRotation();
        for (final MCH_AircraftInfo.DrawnPart n : planeInfo.nozzles) {
            GL11.glPushMatrix();
            GL11.glTranslated(n.pos.xCoord, n.pos.yCoord, n.pos.zCoord);
            GL11.glRotatef(prevRot + (rot - prevRot) * tickTime, (float)n.rot.xCoord, (float)n.rot.yCoord, (float)n.rot.zCoord);
            GL11.glTranslated(-n.pos.xCoord, -n.pos.yCoord, -n.pos.zCoord);
            renderPart(n.model, planeInfo.model, n.modelName);
            GL11.glPopMatrix();
        }
    }
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return MCP_RenderPlane.TEX_DEFAULT;
    }
}
