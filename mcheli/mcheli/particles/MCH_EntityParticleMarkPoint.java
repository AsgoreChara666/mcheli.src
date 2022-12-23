//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.particles;

import net.minecraft.scoreboard.*;
import net.minecraft.world.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import mcheli.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import mcheli.wrapper.*;
import net.minecraft.util.*;
import mcheli.multiplay.*;
import net.minecraft.entity.*;

public class MCH_EntityParticleMarkPoint extends MCH_EntityParticleBase
{
    final Team taem;
    
    public MCH_EntityParticleMarkPoint(final World par1World, final double x, final double y, final double z, final Team team) {
        super(par1World, x, y, z, 0.0, 0.0, 0.0);
        this.setParticleMaxAge(30);
        this.taem = team;
    }
    
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        final EntityPlayer player = (EntityPlayer)Minecraft.getMinecraft().thePlayer;
        if (player == null) {
            this.setDead();
        }
        else if (player.getTeam() == null && this.taem != null) {
            this.setDead();
        }
        else if (player.getTeam() != null && !player.isOnTeam(this.taem)) {
            this.setDead();
        }
    }
    
    public void setDead() {
        super.setDead();
        MCH_Lib.DbgLog(true, "MCH_EntityParticleMarkPoint.setDead : " + this, new Object[0]);
    }
    
    public int getFXLayer() {
        return 3;
    }
    
    public void renderParticle(final Tessellator par1Tessellator, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        GL11.glPushMatrix();
        final Minecraft mc = Minecraft.getMinecraft();
        final EntityPlayer player = (EntityPlayer)mc.thePlayer;
        if (player == null) {
            return;
        }
        double ix = MCH_EntityParticleMarkPoint.interpPosX;
        double iy = MCH_EntityParticleMarkPoint.interpPosY;
        double iz = MCH_EntityParticleMarkPoint.interpPosZ;
        if (mc.gameSettings.thirdPersonView > 0 && mc.renderViewEntity != null) {
            final Entity viewer = (Entity)mc.renderViewEntity;
            final double dist = W_Reflection.getThirdPersonDistance();
            final float yaw = (mc.gameSettings.thirdPersonView != 2) ? (-viewer.rotationYaw) : (-viewer.rotationYaw);
            final float pitch = (mc.gameSettings.thirdPersonView != 2) ? (-viewer.rotationPitch) : (-viewer.rotationPitch);
            final Vec3 v = MCH_Lib.RotVec3(0.0, 0.0, -dist, yaw, pitch);
            if (mc.gameSettings.thirdPersonView == 2) {
                v.xCoord = -v.xCoord;
                v.yCoord = -v.yCoord;
                v.zCoord = -v.zCoord;
            }
            final Vec3 vs = Vec3.createVectorHelper(viewer.posX, viewer.posY + viewer.getEyeHeight(), viewer.posZ);
            final MovingObjectPosition mop = mc.renderViewEntity.worldObj.rayTraceBlocks(vs.addVector(0.0, 0.0, 0.0), vs.addVector(v.xCoord, v.yCoord, v.zCoord));
            double block_dist = dist;
            if (mop != null && mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                block_dist = vs.distanceTo(mop.hitVec) - 0.4;
                if (block_dist < 0.0) {
                    block_dist = 0.0;
                }
            }
            GL11.glTranslated(v.xCoord * (block_dist / dist), v.yCoord * (block_dist / dist), v.zCoord * (block_dist / dist));
            ix += v.xCoord * (block_dist / dist);
            iy += v.yCoord * (block_dist / dist);
            iz += v.zCoord * (block_dist / dist);
        }
        final double px = (float)(this.prevPosX + (this.posX - this.prevPosX) * par2 - ix);
        final double py = (float)(this.prevPosY + (this.posY - this.prevPosY) * par2 - iy);
        final double pz = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * par2 - iz);
        double scale = Math.sqrt(px * px + py * py + pz * pz) / 10.0;
        if (scale < 1.0) {
            scale = 1.0;
        }
        MCH_GuiTargetMarker.addMarkEntityPos(100, (Entity)this, px / scale, py / scale, pz / scale, false);
        GL11.glPopMatrix();
    }
}
