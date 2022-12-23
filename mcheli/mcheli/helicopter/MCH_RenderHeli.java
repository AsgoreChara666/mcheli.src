//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.helicopter;

import cpw.mods.fml.relauncher.*;
import org.lwjgl.opengl.*;
import mcheli.aircraft.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

@SideOnly(Side.CLIENT)
public class MCH_RenderHeli extends MCH_RenderAircraft
{
    public MCH_RenderHeli() {
        this.shadowSize = 2.0f;
    }
    
    public void renderAircraft(final MCH_EntityAircraft entity, final double posX, final double posY, final double posZ, final float yaw, final float pitch, final float roll, final float tickTime) {
        MCH_HeliInfo heliInfo = null;
        if (entity == null || !(entity instanceof MCH_EntityHeli)) {
            return;
        }
        final MCH_EntityHeli heli = (MCH_EntityHeli)entity;
        heliInfo = heli.getHeliInfo();
        if (heliInfo == null) {
            return;
        }
        this.renderDebugHitBox((MCH_EntityAircraft)heli, posX, posY, posZ, yaw, pitch);
        this.renderDebugPilotSeat((MCH_EntityAircraft)heli, posX, posY, posZ, yaw, pitch, roll);
        GL11.glTranslated(posX, posY, posZ);
        GL11.glRotatef(yaw, 0.0f, -1.0f, 0.0f);
        GL11.glRotatef(pitch, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(roll, 0.0f, 0.0f, 1.0f);
        this.bindTexture("textures/helicopters/" + heli.getTextureName() + ".png", (MCH_EntityAircraft)heli);
        renderBody(heliInfo.model);
        this.drawModelBlade(heli, heliInfo, tickTime);
    }
    
    public void drawModelBlade(final MCH_EntityHeli heli, final MCH_HeliInfo info, final float tickTime) {
        for (int i = 0; i < heli.rotors.length && i < info.rotorList.size(); ++i) {
            final MCH_HeliInfo.Rotor rotorInfo = info.rotorList.get(i);
            final MCH_Rotor rotor = heli.rotors[i];
            GL11.glPushMatrix();
            if (rotorInfo.oldRenderMethod) {
                GL11.glTranslated(rotorInfo.pos.xCoord, rotorInfo.pos.yCoord, rotorInfo.pos.zCoord);
            }
            for (final MCH_Blade b : rotor.blades) {
                GL11.glPushMatrix();
                final float rot = b.getRotation();
                float prevRot = b.getPrevRotation();
                if (rot - prevRot < -180.0f) {
                    prevRot -= 360.0f;
                }
                else if (prevRot - rot < -180.0f) {
                    prevRot += 360.0f;
                }
                if (!rotorInfo.oldRenderMethod) {
                    GL11.glTranslated(rotorInfo.pos.xCoord, rotorInfo.pos.yCoord, rotorInfo.pos.zCoord);
                }
                GL11.glRotatef(prevRot + (rot - prevRot) * tickTime, (float)rotorInfo.rot.xCoord, (float)rotorInfo.rot.yCoord, (float)rotorInfo.rot.zCoord);
                if (!rotorInfo.oldRenderMethod) {
                    GL11.glTranslated(-rotorInfo.pos.xCoord, -rotorInfo.pos.yCoord, -rotorInfo.pos.zCoord);
                }
                renderPart(rotorInfo.model, info.model, rotorInfo.modelName);
                GL11.glPopMatrix();
            }
            GL11.glPopMatrix();
        }
    }
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return MCH_RenderHeli.TEX_DEFAULT;
    }
}
