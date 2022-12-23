//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.vehicle;

import cpw.mods.fml.relauncher.*;
import mcheli.aircraft.*;
import org.lwjgl.opengl.*;
import mcheli.weapon.*;
import java.util.*;
import mcheli.wrapper.*;
import mcheli.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

@SideOnly(Side.CLIENT)
public class MCH_RenderVehicle extends MCH_RenderAircraft
{
    public MCH_RenderVehicle() {
        this.shadowSize = 2.0f;
    }
    
    public void renderAircraft(final MCH_EntityAircraft entity, final double posX, final double posY, final double posZ, final float yaw, final float pitch, final float roll, final float tickTime) {
        MCH_VehicleInfo vehicleInfo = null;
        if (entity == null || !(entity instanceof MCH_EntityVehicle)) {
            return;
        }
        final MCH_EntityVehicle vehicle = (MCH_EntityVehicle)entity;
        vehicleInfo = vehicle.getVehicleInfo();
        if (vehicleInfo == null) {
            return;
        }
        if (vehicle.riddenByEntity != null && !vehicle.isDestroyed()) {
            vehicle.isUsedPlayer = true;
            vehicle.lastRiderYaw = vehicle.riddenByEntity.rotationYaw;
            vehicle.lastRiderPitch = vehicle.riddenByEntity.rotationPitch;
        }
        else if (!vehicle.isUsedPlayer) {
            vehicle.lastRiderYaw = vehicle.rotationYaw;
            vehicle.lastRiderPitch = vehicle.rotationPitch;
        }
        this.renderDebugHitBox((MCH_EntityAircraft)vehicle, posX, posY, posZ, yaw, pitch);
        this.renderDebugPilotSeat((MCH_EntityAircraft)vehicle, posX, posY, posZ, yaw, pitch, roll);
        GL11.glTranslated(posX, posY, posZ);
        GL11.glRotatef(yaw, 0.0f, -1.0f, 0.0f);
        GL11.glRotatef(pitch, 1.0f, 0.0f, 0.0f);
        this.bindTexture("textures/vehicles/" + vehicle.getTextureName() + ".png", (MCH_EntityAircraft)vehicle);
        renderBody(vehicleInfo.model);
        final MCH_WeaponSet ws = vehicle.getFirstSeatWeapon();
        this.drawPart(vehicle, vehicleInfo, yaw, pitch, ws, tickTime);
    }
    
    public void drawPart(final MCH_EntityVehicle vehicle, final MCH_VehicleInfo info, final float yaw, final float pitch, final MCH_WeaponSet ws, final float tickTime) {
        final float rotBrl = ws.prevRotBarrel + (ws.rotBarrel - ws.prevRotBarrel) * tickTime;
        int index = 0;
        for (final MCH_VehicleInfo.VPart vp : info.partList) {
            index = this.drawPart(vp, vehicle, info, yaw, pitch, rotBrl, tickTime, ws, index);
        }
    }
    
    int drawPart(final MCH_VehicleInfo.VPart vp, final MCH_EntityVehicle vehicle, final MCH_VehicleInfo info, final float yaw, final float pitch, final float rotBrl, final float tickTime, final MCH_WeaponSet ws, int index) {
        GL11.glPushMatrix();
        float recoilBuf = 0.0f;
        if (index < ws.getWeaponNum()) {
            final MCH_WeaponSet.Recoil r = ws.recoilBuf[index];
            recoilBuf = r.prevRecoilBuf + (r.recoilBuf - r.prevRecoilBuf) * tickTime;
        }
        final int bkIndex = index;
        if (vp.rotPitch || vp.rotYaw || vp.type == 1) {
            GL11.glTranslated(vp.pos.xCoord, vp.pos.yCoord, vp.pos.zCoord);
            if (vp.rotYaw) {
                GL11.glRotatef(-vehicle.lastRiderYaw + yaw, 0.0f, 1.0f, 0.0f);
            }
            if (vp.rotPitch) {
                final float p = MCH_Lib.RNG(vehicle.lastRiderPitch, info.minRotationPitch, info.maxRotationPitch);
                GL11.glRotatef(p - pitch, 1.0f, 0.0f, 0.0f);
            }
            if (vp.type == 1) {
                GL11.glRotatef(rotBrl, 0.0f, 0.0f, -1.0f);
            }
            GL11.glTranslated(-vp.pos.xCoord, -vp.pos.yCoord, -vp.pos.zCoord);
        }
        if (vp.type == 2) {
            GL11.glTranslated(0.0, 0.0, (double)(-vp.recoilBuf * recoilBuf));
        }
        if (vp.type == 2 || vp.type == 3) {
            ++index;
        }
        if (vp.child != null) {
            for (final MCH_VehicleInfo.VPart vcp : vp.child) {
                index = this.drawPart(vcp, vehicle, info, yaw, pitch, rotBrl, recoilBuf, ws, index);
            }
        }
        if (vp.drawFP || !W_Lib.isClientPlayer(vehicle.riddenByEntity) || !W_Lib.isFirstPerson()) {
            if (vp.type != 3 || !vehicle.isWeaponNotCooldown(ws, bkIndex)) {
                renderPart(vp.model, info.model, vp.modelName);
                MCH_ModelManager.render("vehicles", vp.modelName);
            }
        }
        GL11.glPopMatrix();
        return index;
    }
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return MCH_RenderVehicle.TEX_DEFAULT;
    }
}
