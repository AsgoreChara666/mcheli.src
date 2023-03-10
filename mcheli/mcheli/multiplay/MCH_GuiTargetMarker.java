//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.multiplay;

import mcheli.gui.*;
import cpw.mods.fml.relauncher.*;
import java.nio.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.entity.*;
import mcheli.aircraft.*;
import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.*;
import mcheli.*;
import net.minecraft.client.renderer.*;
import mcheli.particles.*;
import org.lwjgl.*;

@SideOnly(Side.CLIENT)
public class MCH_GuiTargetMarker extends MCH_Gui
{
    private static FloatBuffer matModel;
    private static FloatBuffer matProjection;
    private static IntBuffer matViewport;
    private static ArrayList<MCH_MarkEntityPos> entityPos;
    private static HashMap<Integer, Integer> spotedEntity;
    private static Minecraft s_minecraft;
    private static int spotedEntityCountdown;
    
    public MCH_GuiTargetMarker(final Minecraft minecraft) {
        super(minecraft);
        MCH_GuiTargetMarker.s_minecraft = minecraft;
    }
    
    public void initGui() {
        super.initGui();
    }
    
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    public boolean isDrawGui(final EntityPlayer player) {
        return player != null && player.worldObj != null;
    }
    
    public static void onClientTick() {
        if (!Minecraft.getMinecraft().isGamePaused()) {
            ++MCH_GuiTargetMarker.spotedEntityCountdown;
        }
        if (MCH_GuiTargetMarker.spotedEntityCountdown >= 20) {
            MCH_GuiTargetMarker.spotedEntityCountdown = 0;
            for (final Integer key : MCH_GuiTargetMarker.spotedEntity.keySet()) {
                final int count = MCH_GuiTargetMarker.spotedEntity.get(key);
                if (count > 0) {
                    MCH_GuiTargetMarker.spotedEntity.put(key, count - 1);
                }
            }
            final Iterator<Integer> i = MCH_GuiTargetMarker.spotedEntity.values().iterator();
            while (i.hasNext()) {
                if (i.next() <= 0) {
                    i.remove();
                }
            }
        }
    }
    
    public static boolean isSpotedEntity(final Entity entity) {
        final int entityId = entity.getEntityId();
        for (final int key : MCH_GuiTargetMarker.spotedEntity.keySet()) {
            if (key == entityId) {
                return true;
            }
        }
        return false;
    }
    
    public static void addSpotedEntity(final int entityId, final int count) {
        if (MCH_GuiTargetMarker.spotedEntity.containsKey(entityId)) {
            final int now = MCH_GuiTargetMarker.spotedEntity.get(entityId);
            if (count > now) {
                MCH_GuiTargetMarker.spotedEntity.put(entityId, count);
            }
        }
        else {
            MCH_GuiTargetMarker.spotedEntity.put(entityId, count);
        }
    }
    
    public static void addMarkEntityPos(final int reserve, final Entity entity, final double x, final double y, final double z) {
        addMarkEntityPos(reserve, entity, x, y, z, false);
    }
    
    public static void addMarkEntityPos(final int reserve, final Entity entity, final double x, final double y, final double z, final boolean nazo) {
        if (!isEnableEntityMarker()) {
            return;
        }
        MCH_TargetType spotType = MCH_TargetType.NONE;
        final EntityPlayer clientPlayer = (EntityPlayer)MCH_GuiTargetMarker.s_minecraft.thePlayer;
        if (entity instanceof MCH_EntityAircraft) {
            final MCH_EntityAircraft ac = (MCH_EntityAircraft)entity;
            if (ac.isMountedEntity((Entity)clientPlayer)) {
                return;
            }
            if (ac.isMountedSameTeamEntity((EntityLivingBase)clientPlayer)) {
                spotType = MCH_TargetType.SAME_TEAM_PLAYER;
            }
        }
        else if (entity instanceof EntityPlayer) {
            if (entity == clientPlayer || entity.ridingEntity instanceof MCH_EntitySeat || entity.ridingEntity instanceof MCH_EntityAircraft) {
                return;
            }
            if (clientPlayer.getTeam() != null && clientPlayer.isOnSameTeam((EntityLivingBase)entity)) {
                spotType = MCH_TargetType.SAME_TEAM_PLAYER;
            }
        }
        if (spotType == MCH_TargetType.NONE && isSpotedEntity(entity)) {
            spotType = MCH_Multiplay.canSpotEntity((Entity)clientPlayer, clientPlayer.posX, clientPlayer.posY + clientPlayer.getEyeHeight(), clientPlayer.posZ, entity, false);
        }
        if (reserve == 100) {
            spotType = MCH_TargetType.POINT;
        }
        if (spotType != MCH_TargetType.NONE) {
            final MCH_MarkEntityPos e = new MCH_MarkEntityPos(spotType.ordinal(), entity);
            GL11.glGetFloat(2982, MCH_GuiTargetMarker.matModel);
            GL11.glGetFloat(2983, MCH_GuiTargetMarker.matProjection);
            GL11.glGetInteger(2978, MCH_GuiTargetMarker.matViewport);
            if (nazo) {
                GLU.gluProject((float)z, (float)y, (float)x, MCH_GuiTargetMarker.matModel, MCH_GuiTargetMarker.matProjection, MCH_GuiTargetMarker.matViewport, e.pos);
                final float yy = e.pos.get(1);
                GLU.gluProject((float)x, (float)y, (float)z, MCH_GuiTargetMarker.matModel, MCH_GuiTargetMarker.matProjection, MCH_GuiTargetMarker.matViewport, e.pos);
                e.pos.put(1, yy);
            }
            else {
                GLU.gluProject((float)x, (float)y, (float)z, MCH_GuiTargetMarker.matModel, MCH_GuiTargetMarker.matProjection, MCH_GuiTargetMarker.matViewport, e.pos);
            }
            MCH_GuiTargetMarker.entityPos.add(e);
        }
    }
    
    public static void clearMarkEntityPos() {
        MCH_GuiTargetMarker.entityPos.clear();
    }
    
    public static boolean isEnableEntityMarker() {
        final MCH_Config config = MCH_MOD.config;
        if (MCH_Config.DisplayEntityMarker.prmBool && (Minecraft.getMinecraft().isSingleplayer() || MCH_ServerSettings.enableEntityMarker)) {
            final MCH_Config config2 = MCH_MOD.config;
            if (MCH_Config.EntityMarkerSize.prmDouble > 0.0) {
                return true;
            }
        }
        return false;
    }
    
    public void drawGui(final EntityPlayer player, final boolean isThirdPersonView) {
        GL11.glLineWidth((float)(MCH_GuiTargetMarker.scaleFactor * 2));
        if (!this.isDrawGui(player)) {
            return;
        }
        GL11.glDisable(3042);
        if (isEnableEntityMarker()) {
            this.drawMark();
        }
    }
    
    void drawMark() {
        final int[] COLOR_TABLE = { 0, -808464433, -805371904, -805306624, -822018049, -805351649, -65536, 0 };
        final int scale = (MCH_GuiTargetMarker.scaleFactor > 0) ? MCH_GuiTargetMarker.scaleFactor : 2;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4b((byte)(-1), (byte)(-1), (byte)(-1), (byte)(-1));
        GL11.glDepthMask(false);
        final int DW = this.mc.displayWidth;
        final int DH = this.mc.displayHeight;
        final int DSW = this.mc.displayWidth / scale;
        final int DSH = this.mc.displayHeight / scale;
        double x = 9999.0;
        double z = 9999.0;
        double y = 9999.0;
        final Tessellator tessellator = Tessellator.instance;
        for (int i = 0; i < 2; ++i) {
            if (i == 0) {
                tessellator.startDrawing((i == 0) ? 4 : 1);
            }
            for (final MCH_MarkEntityPos e : MCH_GuiTargetMarker.entityPos) {
                final int color = COLOR_TABLE[e.type];
                x = e.pos.get(0) / scale;
                z = e.pos.get(2);
                y = e.pos.get(1) / scale;
                if (z < 1.0) {
                    y = DSH - y;
                }
                else if (x < DW / 2) {
                    x = 10000.0;
                }
                else if (x >= DW / 2) {
                    x = -10000.0;
                }
                if (i == 0) {
                    final MCH_Config config = MCH_MOD.config;
                    final double size = MCH_Config.EntityMarkerSize.prmDouble;
                    if (e.type >= MCH_TargetType.POINT.ordinal() || z >= 1.0 || x < 0.0 || x > DSW || y < 0.0 || y > DSH) {
                        continue;
                    }
                    this.drawTriangle1(tessellator, x, y, size, color);
                }
                else {
                    if (e.type != MCH_TargetType.POINT.ordinal() || e.entity == null) {
                        continue;
                    }
                    final MCH_Config config2 = MCH_MOD.config;
                    final double MARK_SIZE = MCH_Config.BlockMarkerSize.prmDouble;
                    if (z < 1.0 && x >= 0.0 && x <= DSW - 20 && y >= 0.0 && y <= DSH - 40) {
                        final double dist = this.mc.thePlayer.getDistanceToEntity(e.entity);
                        GL11.glEnable(3553);
                        this.drawCenteredString(String.format("%.0fm", dist), (int)x, (int)(y + MARK_SIZE * 1.1 + 16.0), color);
                        if (x >= DSW / 2 - 20 && x <= DSW / 2 + 20 && y >= DSH / 2 - 20 && y <= DSH / 2 + 20) {
                            this.drawString(String.format("x : %.0f", e.entity.posX), (int)(x + MARK_SIZE + 18.0), (int)y - 12, color);
                            this.drawString(String.format("y : %.0f", e.entity.posY), (int)(x + MARK_SIZE + 18.0), (int)y - 4, color);
                            this.drawString(String.format("z : %.0f", e.entity.posZ), (int)(x + MARK_SIZE + 18.0), (int)y + 4, color);
                        }
                        GL11.glDisable(3553);
                        tessellator.startDrawing(1);
                        drawRhombus(tessellator, 15, x, y, this.zLevel, MARK_SIZE, color);
                    }
                    else {
                        tessellator.startDrawing(1);
                        final double S = 30.0;
                        if (x < S) {
                            drawRhombus(tessellator, 1, S, DSH / 2, this.zLevel, MARK_SIZE, color);
                        }
                        else if (x > DSW - S) {
                            drawRhombus(tessellator, 4, DSW - S, DSH / 2, this.zLevel, MARK_SIZE, color);
                        }
                        if (y < S) {
                            drawRhombus(tessellator, 8, DSW / 2, S, this.zLevel, MARK_SIZE, color);
                        }
                        else if (y > DSH - S * 2.0) {
                            drawRhombus(tessellator, 2, DSW / 2, DSH - S * 2.0, this.zLevel, MARK_SIZE, color);
                        }
                    }
                    tessellator.draw();
                }
            }
            if (i == 0) {
                tessellator.draw();
            }
        }
        GL11.glDepthMask(true);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
    }
    
    public static void drawRhombus(final Tessellator tessellator, final int dir, final double x, final double y, final double z, double size, final int color) {
        size *= 2.0;
        tessellator.setColorRGBA_I(0xFFFFFF & color, color >> 24 & 0xFF);
        final double M = size / 3.0;
        if ((dir & 0x1) != 0x0) {
            tessellator.addVertex(x - size, y, z);
            tessellator.addVertex(x - size + M, y - M, z);
            tessellator.addVertex(x - size, y, z);
            tessellator.addVertex(x - size + M, y + M, z);
        }
        if ((dir & 0x4) != 0x0) {
            tessellator.addVertex(x + size, y, z);
            tessellator.addVertex(x + size - M, y - M, z);
            tessellator.addVertex(x + size, y, z);
            tessellator.addVertex(x + size - M, y + M, z);
        }
        if ((dir & 0x8) != 0x0) {
            tessellator.addVertex(x, y - size, z);
            tessellator.addVertex(x + M, y - size + M, z);
            tessellator.addVertex(x, y - size, z);
            tessellator.addVertex(x - M, y - size + M, z);
        }
        if ((dir & 0x2) != 0x0) {
            tessellator.addVertex(x, y + size, z);
            tessellator.addVertex(x + M, y + size - M, z);
            tessellator.addVertex(x, y + size, z);
            tessellator.addVertex(x - M, y + size - M, z);
        }
    }
    
    public void drawTriangle1(final Tessellator tessellator, final double x, final double y, final double size, final int color) {
        tessellator.setColorRGBA_I(0xFFFFFF & color, color >> 24 & 0xFF);
        tessellator.addVertex(x + size / 2.0, y - 10.0 - size, (double)this.zLevel);
        tessellator.addVertex(x - size / 2.0, y - 10.0 - size, (double)this.zLevel);
        tessellator.addVertex(x + 0.0, y - 10.0, (double)this.zLevel);
    }
    
    public void drawTriangle2(final Tessellator tessellator, final double x, final double y, final double size, final int color) {
        tessellator.setColorRGBA_I(0x7F7F7F & color, color >> 24 & 0xFF);
        tessellator.addVertex(x + size / 2.0, y - 10.0 - size, (double)this.zLevel);
        tessellator.addVertex(x - size / 2.0, y - 10.0 - size, (double)this.zLevel);
        tessellator.addVertex(x - size / 2.0, y - 10.0 - size, (double)this.zLevel);
        tessellator.addVertex(x + 0.0, y - 10.0, (double)this.zLevel);
        tessellator.addVertex(x + 0.0, y - 10.0, (double)this.zLevel);
        tessellator.addVertex(x + size / 2.0, y - 10.0 - size, (double)this.zLevel);
    }
    
    public static void markPoint(final int px, final int py, final int pz) {
        final EntityPlayer player = (EntityPlayer)Minecraft.getMinecraft().thePlayer;
        if (player != null && player.worldObj != null) {
            if (py < 1000) {
                MCH_ParticlesUtil.spawnMarkPoint(player, 0.5 + px, 1.0 + py, 0.5 + pz);
            }
            else {
                MCH_ParticlesUtil.clearMarkPoint();
            }
        }
    }
    
    static {
        MCH_GuiTargetMarker.matModel = BufferUtils.createFloatBuffer(16);
        MCH_GuiTargetMarker.matProjection = BufferUtils.createFloatBuffer(16);
        MCH_GuiTargetMarker.matViewport = BufferUtils.createIntBuffer(16);
        MCH_GuiTargetMarker.entityPos = new ArrayList<MCH_MarkEntityPos>();
        MCH_GuiTargetMarker.spotedEntity = new HashMap<Integer, Integer>();
        MCH_GuiTargetMarker.spotedEntityCountdown = 0;
    }
}
