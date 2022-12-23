//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.mob;

import mcheli.gui.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import mcheli.aircraft.*;
import java.util.*;

@SideOnly(Side.CLIENT)
public class MCH_GuiSpawnGunner extends MCH_Gui
{
    public MCH_GuiSpawnGunner(final Minecraft minecraft) {
        super(minecraft);
    }
    
    public void initGui() {
        super.initGui();
    }
    
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    public boolean isDrawGui(final EntityPlayer player) {
        return player != null && player.worldObj != null && player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof MCH_ItemSpawnGunner;
    }
    
    public void drawGui(final EntityPlayer player, final boolean isThirdPersonView) {
        if (isThirdPersonView) {
            return;
        }
        if (!this.isDrawGui(player)) {
            return;
        }
        GL11.glLineWidth((float)MCH_GuiSpawnGunner.scaleFactor);
        GL11.glDisable(3042);
        this.draw(player, this.searchTarget(player));
    }
    
    private Entity searchTarget(final EntityPlayer player) {
        final float f = 1.0f;
        final float pitch = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * f;
        final float yaw = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * f;
        final double dx = player.prevPosX + (player.posX - player.prevPosX) * f;
        final double dy = player.prevPosY + (player.posY - player.prevPosY) * f + 1.62 - player.yOffset;
        final double dz = player.prevPosZ + (player.posZ - player.prevPosZ) * f;
        final Vec3 vec3 = Vec3.createVectorHelper(dx, dy, dz);
        final float f2 = MathHelper.cos(-yaw * 0.017453292f - 3.1415927f);
        final float f3 = MathHelper.sin(-yaw * 0.017453292f - 3.1415927f);
        final float f4 = -MathHelper.cos(-pitch * 0.017453292f);
        final float f5 = MathHelper.sin(-pitch * 0.017453292f);
        final float f6 = f3 * f4;
        final float f7 = f2 * f4;
        final double d3 = 5.0;
        final Vec3 vec4 = vec3.addVector(f6 * d3, f5 * d3, f7 * d3);
        Entity target = null;
        List list = player.worldObj.getEntitiesWithinAABB((Class)MCH_EntityGunner.class, player.boundingBox.expand(5.0, 5.0, 5.0));
        for (int i = 0; i < list.size(); ++i) {
            final MCH_EntityGunner gunner = list.get(i);
            if (gunner.boundingBox.calculateIntercept(vec3, vec4) != null && (target == null || player.getDistanceSqToEntity((Entity)gunner) < player.getDistanceSqToEntity(target))) {
                target = (Entity)gunner;
            }
        }
        if (target != null) {
            return target;
        }
        final MCH_ItemSpawnGunner item = (MCH_ItemSpawnGunner)player.getCurrentEquippedItem().getItem();
        if (item.targetType == 1 && !player.worldObj.isRemote && player.getTeam() == null) {
            return null;
        }
        list = player.worldObj.getEntitiesWithinAABB((Class)MCH_EntitySeat.class, player.boundingBox.expand(5.0, 5.0, 5.0));
        for (int j = 0; j < list.size(); ++j) {
            final MCH_EntitySeat seat = list.get(j);
            if (seat.getParent() != null && seat.getParent().getAcInfo() != null && seat.boundingBox.calculateIntercept(vec3, vec4) != null && (target == null || player.getDistanceSqToEntity((Entity)seat) < player.getDistanceSqToEntity(target))) {
                if (seat.riddenByEntity instanceof MCH_EntityGunner) {
                    target = seat.riddenByEntity;
                }
                else {
                    target = (Entity)seat;
                }
            }
        }
        if (target == null) {
            list = player.worldObj.getEntitiesWithinAABB((Class)MCH_EntityAircraft.class, player.boundingBox.expand(5.0, 5.0, 5.0));
            for (int j = 0; j < list.size(); ++j) {
                final MCH_EntityAircraft ac = list.get(j);
                if (!ac.isUAV() && ac.getAcInfo() != null && ac.boundingBox.calculateIntercept(vec3, vec4) != null && (target == null || player.getDistanceSqToEntity((Entity)ac) < player.getDistanceSqToEntity(target))) {
                    if (ac.getRiddenByEntity() instanceof MCH_EntityGunner) {
                        target = ac.getRiddenByEntity();
                    }
                    else {
                        target = (Entity)ac;
                    }
                }
            }
        }
        return target;
    }
    
    void draw(final EntityPlayer player, final Entity entity) {
        if (entity == null) {
            return;
        }
        GL11.glEnable(3042);
        GL11.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
        final int srcBlend = GL11.glGetInteger(3041);
        final int dstBlend = GL11.glGetInteger(3040);
        GL11.glBlendFunc(770, 771);
        double size;
        for (size = 512.0; size < this.width || size < this.height; size *= 2.0) {}
        GL11.glBlendFunc(srcBlend, dstBlend);
        GL11.glDisable(3042);
        final double factor = size / 512.0;
        final double SCALE_FACTOR = MCH_GuiSpawnGunner.scaleFactor * factor;
        final double CX = this.mc.displayWidth / 2;
        final double CY = this.mc.displayHeight / 2;
        final double px = (CX - 0.0) / SCALE_FACTOR;
        final double py = (CY + 0.0) / SCALE_FACTOR;
        GL11.glPushMatrix();
        if (entity instanceof MCH_EntityGunner) {
            final MCH_EntityGunner gunner = (MCH_EntityGunner)entity;
            String seatName = "";
            if (gunner.ridingEntity instanceof MCH_EntitySeat) {
                seatName = "(seat " + (((MCH_EntitySeat)gunner.ridingEntity).seatID + 2) + ")";
            }
            else if (gunner.ridingEntity instanceof MCH_EntityAircraft) {
                seatName = "(seat 1)";
            }
            this.drawCenteredString(gunner.getTeamName() + " Gunner " + seatName, (int)px, (int)py + 20, -8355840);
            final int S = 10;
            this.drawLine(new double[] { px - S, py - S, px + S, py - S, px + S, py + S, px - S, py + S }, -8355840, 2);
        }
        else if (entity instanceof MCH_EntitySeat) {
            final MCH_EntitySeat seat = (MCH_EntitySeat)entity;
            if (seat.riddenByEntity == null) {
                this.drawCenteredString("seat " + (seat.seatID + 2), (int)px, (int)py + 20, -16711681);
                final int S2 = 10;
                this.drawLine(new double[] { px - S2, py - S2, px + S2, py - S2, px + S2, py + S2, px - S2, py + S2 }, -16711681, 2);
            }
            else {
                this.drawCenteredString("seat " + (seat.seatID + 2), (int)px, (int)py + 20, -65536);
                final int S2 = 10;
                this.drawLine(new double[] { px - S2, py - S2, px + S2, py - S2, px + S2, py + S2, px - S2, py + S2 }, -65536, 2);
                this.drawLine(new double[] { px - S2, py - S2, px + S2, py + S2 }, -65536);
                this.drawLine(new double[] { px + S2, py - S2, px - S2, py + S2 }, -65536);
            }
        }
        else if (entity instanceof MCH_EntityAircraft) {
            final MCH_EntityAircraft ac = (MCH_EntityAircraft)entity;
            if (ac.getRiddenByEntity() == null) {
                this.drawCenteredString("seat 1", (int)px, (int)py + 20, -16711681);
                final int S2 = 10;
                this.drawLine(new double[] { px - S2, py - S2, px + S2, py - S2, px + S2, py + S2, px - S2, py + S2 }, -16711681, 2);
            }
            else {
                this.drawCenteredString("seat 1", (int)px, (int)py + 20, -65536);
                final int S2 = 10;
                this.drawLine(new double[] { px - S2, py - S2, px + S2, py - S2, px + S2, py + S2, px - S2, py + S2 }, -65536, 2);
                this.drawLine(new double[] { px - S2, py - S2, px + S2, py + S2 }, -65536);
                this.drawLine(new double[] { px + S2, py - S2, px - S2, py + S2 }, -65536);
            }
        }
        GL11.glPopMatrix();
    }
}
