//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.hud;

import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import mcheli.aircraft.*;
import net.minecraft.entity.*;
import mcheli.eval.eval.var.*;
import mcheli.eval.eval.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import mcheli.helicopter.*;
import java.util.*;
import mcheli.weapon.*;
import mcheli.plane.*;
import mcheli.*;
import mcheli.wrapper.*;

public abstract class MCH_HudItem extends Gui
{
    public final int fileLine;
    public static Minecraft mc;
    public static EntityPlayer player;
    public static MCH_EntityAircraft ac;
    protected static double centerX;
    protected static double centerY;
    public static double width;
    public static double height;
    protected static Random rand;
    public static int scaleFactor;
    public static int colorSetting;
    protected static int altitudeUpdateCount;
    protected static int Altitude;
    protected static float prevRadarRot;
    protected static String WeaponName;
    protected static String WeaponAmmo;
    protected static String WeaponAllAmmo;
    protected static MCH_WeaponSet CurrentWeapon;
    protected static float ReloadPer;
    protected static float ReloadSec;
    protected static float MortarDist;
    protected static MCH_LowPassFilterFloat StickX_LPF;
    protected static MCH_LowPassFilterFloat StickY_LPF;
    protected static double StickX;
    protected static double StickY;
    protected static double TVM_PosX;
    protected static double TVM_PosY;
    protected static double TVM_PosZ;
    protected static double TVM_Diff;
    protected static double UAV_Dist;
    protected static int countFuelWarn;
    protected static ArrayList<MCH_Vector2> EntityList;
    protected static ArrayList<MCH_Vector2> EnemyList;
    protected static Map<String, Double> varMap;
    protected MCH_Hud parent;
    protected static float partialTicks;
    private static MCH_HudItemExit dummy;
    
    public MCH_HudItem(final int fileLine) {
        this.fileLine = fileLine;
        this.zLevel = -110.0f;
    }
    
    public abstract void execute();
    
    public boolean canExecute() {
        return !this.parent.isIfFalse;
    }
    
    public static void update() {
        final MCH_WeaponSet ws = MCH_HudItem.ac.getCurrentWeapon((Entity)MCH_HudItem.player);
        updateRadar(MCH_HudItem.ac);
        updateStick();
        updateAltitude(MCH_HudItem.ac);
        updateTvMissile(MCH_HudItem.ac);
        updateUAV(MCH_HudItem.ac);
        updateWeapon(MCH_HudItem.ac, ws);
        updateVarMap(MCH_HudItem.ac, ws);
    }
    
    public static String toFormula(final String s) {
        return s.toLowerCase().replaceAll("#", "0x").replace("\t", " ").replace(" ", "");
    }
    
    public static double calc(final String s) {
        final Expression exp = ExpRuleFactory.getDefaultRule().parse(s);
        exp.setVariable((Variable)new MapVariable((Map)MCH_HudItem.varMap));
        return exp.evalDouble();
    }
    
    public static long calcLong(final String s) {
        final Expression exp = ExpRuleFactory.getDefaultRule().parse(s);
        exp.setVariable((Variable)new MapVariable((Map)MCH_HudItem.varMap));
        return exp.evalLong();
    }
    
    public void drawCenteredString(final String s, final int x, final int y, final int color) {
        this.drawCenteredString(MCH_HudItem.mc.fontRendererObj, s, x, y, color);
    }
    
    public void drawString(final String s, final int x, final int y, final int color) {
        this.drawString(MCH_HudItem.mc.fontRendererObj, s, x, y, color);
    }
    
    public void drawTexture(final String name, final double left, final double top, final double width, final double height, final double uLeft, final double vTop, final double uWidth, final double vHeight, final float rot, final int textureWidth, final int textureHeight) {
        W_McClient.MOD_bindTexture("textures/gui/" + name + ".png");
        GL11.glPushMatrix();
        GL11.glTranslated(left + width / 2.0, top + height / 2.0, 0.0);
        GL11.glRotatef(rot, 0.0f, 0.0f, 1.0f);
        final float fx = (float)(1.0 / textureWidth);
        final float fy = (float)(1.0 / textureHeight);
        final Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(-width / 2.0, height / 2.0, (double)this.zLevel, uLeft * fx, (vTop + vHeight) * fy);
        tessellator.addVertexWithUV(width / 2.0, height / 2.0, (double)this.zLevel, (uLeft + uWidth) * fx, (vTop + vHeight) * fy);
        tessellator.addVertexWithUV(width / 2.0, -height / 2.0, (double)this.zLevel, (uLeft + uWidth) * fx, vTop * fy);
        tessellator.addVertexWithUV(-width / 2.0, -height / 2.0, (double)this.zLevel, uLeft * fx, vTop * fy);
        tessellator.draw();
        GL11.glPopMatrix();
    }
    
    public static void drawRect(double par0, double par1, double par2, double par3, final int par4) {
        if (par0 < par2) {
            final double j1 = par0;
            par0 = par2;
            par2 = j1;
        }
        if (par1 < par3) {
            final double j1 = par1;
            par1 = par3;
            par3 = j1;
        }
        final float f3 = (par4 >> 24 & 0xFF) / 255.0f;
        final float f4 = (par4 >> 16 & 0xFF) / 255.0f;
        final float f5 = (par4 >> 8 & 0xFF) / 255.0f;
        final float f6 = (par4 & 0xFF) / 255.0f;
        final Tessellator tessellator = Tessellator.instance;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        W_OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(f4, f5, f6, f3);
        tessellator.startDrawingQuads();
        tessellator.addVertex(par0, par3, 0.0);
        tessellator.addVertex(par2, par3, 0.0);
        tessellator.addVertex(par2, par1, 0.0);
        tessellator.addVertex(par0, par1, 0.0);
        tessellator.draw();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
    }
    
    public void drawLine(final double[] line, final int color) {
        this.drawLine(line, color, 1);
    }
    
    public void drawLine(final double[] line, final int color, final int mode) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4ub((byte)(color >> 16 & 0xFF), (byte)(color >> 8 & 0xFF), (byte)(color >> 0 & 0xFF), (byte)(color >> 24 & 0xFF));
        final Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawing(mode);
        for (int i = 0; i < line.length; i += 2) {
            tessellator.addVertex(line[i + 0], line[i + 1], (double)this.zLevel);
        }
        tessellator.draw();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glColor4b((byte)(-1), (byte)(-1), (byte)(-1), (byte)(-1));
        GL11.glPopMatrix();
    }
    
    public void drawLineStipple(final double[] line, final int color, final int factor, final int pattern) {
        GL11.glEnable(2852);
        GL11.glLineStipple(factor * MCH_HudItem.scaleFactor, (short)pattern);
        this.drawLine(line, color);
        GL11.glDisable(2852);
    }
    
    public void drawPoints(final ArrayList<Double> points, final int color, final int pointWidth) {
        final int prevWidth = GL11.glGetInteger(2833);
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4ub((byte)(color >> 16 & 0xFF), (byte)(color >> 8 & 0xFF), (byte)(color >> 0 & 0xFF), (byte)(color >> 24 & 0xFF));
        GL11.glPointSize((float)pointWidth);
        final Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawing(0);
        for (int i = 0; i < points.size(); i += 2) {
            tessellator.addVertex((double)points.get(i), (double)points.get(i + 1), 0.0);
        }
        tessellator.draw();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
        GL11.glColor4b((byte)(-1), (byte)(-1), (byte)(-1), (byte)(-1));
        GL11.glPointSize((float)prevWidth);
    }
    
    public static void updateVarMap(final MCH_EntityAircraft ac, final MCH_WeaponSet ws) {
        if (MCH_HudItem.varMap == null) {
            MCH_HudItem.varMap = new LinkedHashMap<String, Double>();
        }
        updateVarMapItem("color", getColor());
        updateVarMapItem("center_x", MCH_HudItem.centerX);
        updateVarMapItem("center_y", MCH_HudItem.centerY);
        updateVarMapItem("width", MCH_HudItem.width);
        updateVarMapItem("height", MCH_HudItem.height);
        updateVarMapItem("time", (double)(MCH_HudItem.player.worldObj.getWorldTime() % 24000L));
        final String key = "test_mode";
        final MCH_Config config = MCH_MOD.config;
        updateVarMapItem(key, MCH_Config.TestMode.prmBool ? 1.0 : 0.0);
        updateVarMapItem("plyr_yaw", MathHelper.wrapAngleTo180_float(MCH_HudItem.player.rotationYaw));
        updateVarMapItem("plyr_pitch", MCH_HudItem.player.rotationPitch);
        updateVarMapItem("yaw", MathHelper.wrapAngleTo180_float(ac.getRotYaw()));
        updateVarMapItem("pitch", ac.getRotPitch());
        updateVarMapItem("roll", MathHelper.wrapAngleTo180_float(ac.getRotRoll()));
        updateVarMapItem("altitude", MCH_HudItem.Altitude);
        updateVarMapItem("sea_alt", getSeaAltitude(ac));
        updateVarMapItem("have_radar", ac.isEntityRadarMounted() ? 1.0 : 0.0);
        updateVarMapItem("radar_rot", getRadarRot(ac));
        updateVarMapItem("hp", ac.getHP());
        updateVarMapItem("max_hp", ac.getMaxHP());
        updateVarMapItem("hp_rto", (ac.getMaxHP() > 0) ? (ac.getHP() / (double)ac.getMaxHP()) : 0.0);
        updateVarMapItem("throttle", ac.getCurrentThrottle());
        updateVarMapItem("pos_x", ac.posX);
        updateVarMapItem("pos_y", ac.posY);
        updateVarMapItem("pos_z", ac.posZ);
        updateVarMapItem("motion_x", ac.motionX);
        updateVarMapItem("motion_y", ac.motionY);
        updateVarMapItem("motion_z", ac.motionZ);
        updateVarMapItem("speed", Math.sqrt(ac.motionX * ac.motionX + ac.motionY * ac.motionY + ac.motionZ * ac.motionZ));
        updateVarMapItem("fuel", ac.getFuelP());
        updateVarMapItem("low_fuel", isLowFuel(ac));
        updateVarMapItem("stick_x", MCH_HudItem.StickX);
        updateVarMapItem("stick_y", MCH_HudItem.StickY);
        updateVarMap_Weapon(ws);
        updateVarMapItem("vtol_stat", getVtolStat(ac));
        updateVarMapItem("free_look", getFreeLook(ac, MCH_HudItem.player));
        updateVarMapItem("gunner_mode", ac.getIsGunnerMode((Entity)MCH_HudItem.player) ? 1.0 : 0.0);
        updateVarMapItem("cam_mode", ac.getCameraMode(MCH_HudItem.player));
        updateVarMapItem("cam_zoom", ac.camera.getCameraZoom());
        updateVarMapItem("auto_pilot", getAutoPilot(ac, MCH_HudItem.player));
        updateVarMapItem("have_flare", ac.haveFlare() ? 1.0 : 0.0);
        updateVarMapItem("can_flare", ac.canUseFlare() ? 1.0 : 0.0);
        updateVarMapItem("inventory", ac.getSizeInventory());
        updateVarMapItem("hovering", (ac instanceof MCH_EntityHeli && ac.isHoveringMode()) ? 1.0 : 0.0);
        updateVarMapItem("is_uav", ac.isUAV() ? 1.0 : 0.0);
        updateVarMapItem("uav_fs", getUAV_Fs(ac));
    }
    
    public static void updateVarMapItem(final String key, final double value) {
        MCH_HudItem.varMap.put(key, value);
    }
    
    public static void drawVarMap() {
        final MCH_Config config = MCH_MOD.config;
        if (MCH_Config.TestMode.prmBool) {
            int i = 0;
            int x = (int)(-300.0 + MCH_HudItem.centerX);
            int y = (int)(-100.0 + MCH_HudItem.centerY);
            for (final String key : MCH_HudItem.varMap.keySet()) {
                MCH_HudItem.dummy.drawString(key, x, y, -12544);
                final Double d = MCH_HudItem.varMap.get(key);
                final String fmt = key.equalsIgnoreCase("color") ? String.format(": 0x%08X", d.intValue()) : String.format(": %.2f", d);
                MCH_HudItem.dummy.drawString(fmt, x + 50, y, -12544);
                ++i;
                y += 8;
                if (i == MCH_HudItem.varMap.size() / 2) {
                    x = (int)(200.0 + MCH_HudItem.centerX);
                    y = (int)(-100.0 + MCH_HudItem.centerY);
                }
            }
        }
    }
    
    private static double getUAV_Fs(final MCH_EntityAircraft ac) {
        double uav_fs = 0.0;
        if (ac.isUAV() && ac.getUavStation() != null) {
            final double dx = ac.posX - ac.getUavStation().posX;
            final double dz = ac.posZ - ac.getUavStation().posZ;
            float dist = (float)Math.sqrt(dx * dx + dz * dz);
            final float distMax = 120.0f;
            if (dist > 120.0f) {
                dist = 120.0f;
            }
            uav_fs = 1.0f - dist / 120.0f;
        }
        return uav_fs;
    }
    
    private static void updateVarMap_Weapon(final MCH_WeaponSet ws) {
        int reloading = 0;
        double wpn_heat = 0.0;
        int is_heat_wpn = 0;
        int sight_type = 0;
        double lock = 0.0;
        float rel_time = 0.0f;
        int display_mortar_dist = 0;
        if (ws != null) {
            final MCH_WeaponBase wb = ws.getCurrentWeapon();
            final MCH_WeaponInfo wi = wb.getInfo();
            if (wi == null) {
                return;
            }
            is_heat_wpn = ((wi.maxHeatCount > 0) ? 1 : 0);
            reloading = (ws.isInPreparation() ? 1 : 0);
            display_mortar_dist = (wi.displayMortarDistance ? 1 : 0);
            if (wi.delay > wi.reloadTime) {
                rel_time = ws.countWait / (float)((wi.delay > 0) ? wi.delay : 1);
                if (rel_time < 0.0f) {
                    rel_time = -rel_time;
                }
                if (rel_time > 1.0f) {
                    rel_time = 1.0f;
                }
            }
            else {
                rel_time = ws.countReloadWait / (float)((wi.reloadTime > 0) ? wi.reloadTime : 1);
            }
            if (wi.maxHeatCount > 0) {
                final double hpp = ws.currentHeat / (double)wi.maxHeatCount;
                wpn_heat = ((hpp > 1.0) ? 1.0 : hpp);
            }
            final int cntLockMax = wb.getLockCountMax();
            final MCH_SightType sight = wb.getSightType();
            if (sight == MCH_SightType.LOCK && cntLockMax > 0) {
                lock = wb.getLockCount() / (double)cntLockMax;
                sight_type = 2;
            }
            if (sight == MCH_SightType.ROCKET) {
                sight_type = 1;
            }
        }
        updateVarMapItem("reloading", reloading);
        updateVarMapItem("reload_time", rel_time);
        updateVarMapItem("wpn_heat", wpn_heat);
        updateVarMapItem("is_heat_wpn", is_heat_wpn);
        updateVarMapItem("sight_type", sight_type);
        updateVarMapItem("lock", lock);
        updateVarMapItem("dsp_mt_dist", display_mortar_dist);
        updateVarMapItem("mt_dist", MCH_HudItem.MortarDist);
    }
    
    public static int isLowFuel(final MCH_EntityAircraft ac) {
        int is_low_fuel = 0;
        if (MCH_HudItem.countFuelWarn <= 0) {
            MCH_HudItem.countFuelWarn = 280;
        }
        --MCH_HudItem.countFuelWarn;
        if (MCH_HudItem.countFuelWarn < 160 && ac.getMaxFuel() > 0 && ac.getFuelP() < 0.1f && !ac.isInfinityFuel((Entity)MCH_HudItem.player, false)) {
            is_low_fuel = 1;
        }
        return is_low_fuel;
    }
    
    public static double getSeaAltitude(final MCH_EntityAircraft ac) {
        final double a = ac.posY - ac.worldObj.getHorizon();
        return (a >= 0.0) ? a : 0.0;
    }
    
    public static float getRadarRot(final MCH_EntityAircraft ac) {
        float rot = (float)ac.getRadarRotate();
        final float prevRot = MCH_HudItem.prevRadarRot;
        if (rot < prevRot) {
            rot += 360.0f;
        }
        MCH_HudItem.prevRadarRot = (float)ac.getRadarRotate();
        return MCH_Lib.smooth(rot, prevRot, MCH_HudItem.partialTicks);
    }
    
    public static int getVtolStat(final MCH_EntityAircraft ac) {
        if (ac instanceof MCP_EntityPlane) {
            return ((MCP_EntityPlane)ac).getVtolMode();
        }
        return 0;
    }
    
    public static int getFreeLook(final MCH_EntityAircraft ac, final EntityPlayer player) {
        if (ac.isPilot((Entity)player) && ac.canSwitchFreeLook() && ac.isFreeLookMode()) {
            return 1;
        }
        return 0;
    }
    
    public static int getAutoPilot(final MCH_EntityAircraft ac, final EntityPlayer player) {
        if (ac instanceof MCP_EntityPlane && ac.isPilot((Entity)player) && ac.getIsGunnerMode((Entity)player)) {
            return 1;
        }
        return 0;
    }
    
    public static double getColor() {
        long l = MCH_HudItem.colorSetting;
        l &= 0xFFFFFFFFL;
        return (double)l;
    }
    
    private static void updateStick() {
        MCH_HudItem.StickX_LPF.put((float)(MCH_ClientCommonTickHandler.getCurrentStickX() / MCH_ClientCommonTickHandler.getMaxStickLength()));
        MCH_HudItem.StickY_LPF.put((float)(-MCH_ClientCommonTickHandler.getCurrentStickY() / MCH_ClientCommonTickHandler.getMaxStickLength()));
        MCH_HudItem.StickX = MCH_HudItem.StickX_LPF.getAvg();
        MCH_HudItem.StickY = MCH_HudItem.StickY_LPF.getAvg();
    }
    
    private static void updateRadar(final MCH_EntityAircraft ac) {
        MCH_HudItem.EntityList = (ArrayList<MCH_Vector2>)ac.getRadarEntityList();
        MCH_HudItem.EnemyList = (ArrayList<MCH_Vector2>)ac.getRadarEnemyList();
    }
    
    private static void updateAltitude(final MCH_EntityAircraft ac) {
        if (MCH_HudItem.altitudeUpdateCount <= 0) {
            int heliY = (int)ac.posY;
            if (heliY > 256) {
                heliY = 256;
            }
            int i = 0;
            while (i < 256) {
                if (heliY - i <= 0) {
                    break;
                }
                final int id = W_WorldFunc.getBlockId(ac.worldObj, (int)ac.posX, heliY - i, (int)ac.posZ);
                if (id != 0) {
                    MCH_HudItem.Altitude = i;
                    if (ac.posY > 256.0) {
                        MCH_HudItem.Altitude += (int)(ac.posY - 256.0);
                        break;
                    }
                    break;
                }
                else {
                    ++i;
                }
            }
            MCH_HudItem.altitudeUpdateCount = 30;
        }
        else {
            --MCH_HudItem.altitudeUpdateCount;
        }
    }
    
    public static void updateWeapon(final MCH_EntityAircraft ac, final MCH_WeaponSet ws) {
        if (ac.getWeaponNum() <= 0) {
            return;
        }
        if (ws == null) {
            return;
        }
        MCH_HudItem.CurrentWeapon = ws;
        MCH_HudItem.WeaponName = (ac.isPilotReloading() ? "-- Reloading --" : ws.getName());
        if (ws.getAmmoNumMax() > 0) {
            MCH_HudItem.WeaponAmmo = (ac.isPilotReloading() ? "----" : String.format("%4d", ws.getAmmoNum()));
            MCH_HudItem.WeaponAllAmmo = (ac.isPilotReloading() ? "----" : String.format("%4d", ws.getRestAllAmmoNum()));
        }
        else {
            MCH_HudItem.WeaponAmmo = "";
            MCH_HudItem.WeaponAllAmmo = "";
        }
        final MCH_WeaponInfo wi = ws.getInfo();
        if (wi.displayMortarDistance) {
            MCH_HudItem.MortarDist = (float)ac.getLandInDistance((Entity)MCH_HudItem.player);
        }
        else {
            MCH_HudItem.MortarDist = -1.0f;
        }
        if (wi.delay > wi.reloadTime) {
            MCH_HudItem.ReloadSec = ((ws.countWait >= 0) ? ((float)ws.countWait) : ((float)(-ws.countWait)));
            MCH_HudItem.ReloadPer = ws.countWait / (float)((wi.delay > 0) ? wi.delay : 1);
            if (MCH_HudItem.ReloadPer < 0.0f) {
                MCH_HudItem.ReloadPer = -MCH_HudItem.ReloadPer;
            }
            if (MCH_HudItem.ReloadPer > 1.0f) {
                MCH_HudItem.ReloadPer = 1.0f;
            }
        }
        else {
            MCH_HudItem.ReloadSec = (float)ws.countReloadWait;
            MCH_HudItem.ReloadPer = ws.countReloadWait / (float)((wi.reloadTime > 0) ? wi.reloadTime : 1);
        }
        MCH_HudItem.ReloadSec /= 20.0f;
        MCH_HudItem.ReloadPer = (1.0f - MCH_HudItem.ReloadPer) * 100.0f;
    }
    
    public static void updateUAV(final MCH_EntityAircraft ac) {
        if (ac.isUAV() && ac.getUavStation() != null) {
            final double dx = ac.posX - ac.getUavStation().posX;
            final double dz = ac.posZ - ac.getUavStation().posZ;
            MCH_HudItem.UAV_Dist = (float)Math.sqrt(dx * dx + dz * dz);
        }
        else {
            MCH_HudItem.UAV_Dist = 0.0;
        }
    }
    
    private static void updateTvMissile(final MCH_EntityAircraft ac) {
        final Entity tvmissile = ac.getTVMissile();
        if (tvmissile != null) {
            MCH_HudItem.TVM_PosX = tvmissile.posX;
            MCH_HudItem.TVM_PosY = tvmissile.posY;
            MCH_HudItem.TVM_PosZ = tvmissile.posZ;
            final double dx = tvmissile.posX - ac.posX;
            final double dy = tvmissile.posY - ac.posY;
            final double dz = tvmissile.posZ - ac.posZ;
            MCH_HudItem.TVM_Diff = Math.sqrt(dx * dx + dy * dy + dz * dz);
        }
        else {
            MCH_HudItem.TVM_PosX = 0.0;
            MCH_HudItem.TVM_PosY = 0.0;
            MCH_HudItem.TVM_PosZ = 0.0;
            MCH_HudItem.TVM_Diff = 0.0;
        }
    }
    
    static {
        MCH_HudItem.centerX = 0.0;
        MCH_HudItem.centerY = 0.0;
        MCH_HudItem.rand = new Random();
        MCH_HudItem.colorSetting = -16777216;
        MCH_HudItem.altitudeUpdateCount = 0;
        MCH_HudItem.Altitude = 0;
        MCH_HudItem.WeaponName = "";
        MCH_HudItem.WeaponAmmo = "";
        MCH_HudItem.WeaponAllAmmo = "";
        MCH_HudItem.CurrentWeapon = null;
        MCH_HudItem.ReloadPer = 0.0f;
        MCH_HudItem.ReloadSec = 0.0f;
        MCH_HudItem.MortarDist = 0.0f;
        MCH_HudItem.StickX_LPF = new MCH_LowPassFilterFloat(4);
        MCH_HudItem.StickY_LPF = new MCH_LowPassFilterFloat(4);
        MCH_HudItem.varMap = null;
        MCH_HudItem.dummy = new MCH_HudItemExit(0);
    }
}
