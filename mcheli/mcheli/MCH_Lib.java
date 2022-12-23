//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli;

import net.minecraft.block.material.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import java.text.*;
import java.util.*;
import java.io.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import mcheli.wrapper.*;
import net.minecraft.item.crafting.*;

public class MCH_Lib
{
    private static HashMap<String, Material> mapMaterial;
    public static final String[] AZIMUTH_8;
    public static final int AZIMUTH_8_ANG;
    
    private MCH_Lib() {
    }
    
    public static void init() {
        MCH_Lib.mapMaterial.clear();
        MCH_Lib.mapMaterial.put("air", Material.air);
        MCH_Lib.mapMaterial.put("grass", Material.grass);
        MCH_Lib.mapMaterial.put("ground", Material.ground);
        MCH_Lib.mapMaterial.put("wood", Material.wood);
        MCH_Lib.mapMaterial.put("rock", Material.rock);
        MCH_Lib.mapMaterial.put("iron", Material.iron);
        MCH_Lib.mapMaterial.put("anvil", Material.anvil);
        MCH_Lib.mapMaterial.put("water", Material.water);
        MCH_Lib.mapMaterial.put("lava", Material.lava);
        MCH_Lib.mapMaterial.put("leaves", Material.leaves);
        MCH_Lib.mapMaterial.put("plants", Material.plants);
        MCH_Lib.mapMaterial.put("vine", Material.vine);
        MCH_Lib.mapMaterial.put("sponge", Material.sponge);
        MCH_Lib.mapMaterial.put("cloth", Material.cloth);
        MCH_Lib.mapMaterial.put("fire", Material.fire);
        MCH_Lib.mapMaterial.put("sand", Material.sand);
        MCH_Lib.mapMaterial.put("circuits", Material.circuits);
        MCH_Lib.mapMaterial.put("carpet", Material.carpet);
        MCH_Lib.mapMaterial.put("glass", Material.glass);
        MCH_Lib.mapMaterial.put("redstoneLight", Material.redstoneLight);
        MCH_Lib.mapMaterial.put("tnt", Material.tnt);
        MCH_Lib.mapMaterial.put("coral", Material.coral);
        MCH_Lib.mapMaterial.put("ice", Material.ice);
        MCH_Lib.mapMaterial.put("packedIce", Material.packedIce);
        MCH_Lib.mapMaterial.put("snow", Material.snow);
        MCH_Lib.mapMaterial.put("craftedSnow", Material.craftedSnow);
        MCH_Lib.mapMaterial.put("cactus", Material.cactus);
        MCH_Lib.mapMaterial.put("clay", Material.clay);
        MCH_Lib.mapMaterial.put("gourd", Material.gourd);
        MCH_Lib.mapMaterial.put("dragonEgg", Material.dragonEgg);
        MCH_Lib.mapMaterial.put("portal", Material.portal);
        MCH_Lib.mapMaterial.put("cake", Material.cake);
        MCH_Lib.mapMaterial.put("web", Material.web);
        MCH_Lib.mapMaterial.put("piston", Material.piston);
    }
    
    public static Material getMaterialFromName(final String name) {
        if (MCH_Lib.mapMaterial.containsKey(name)) {
            return MCH_Lib.mapMaterial.get(name);
        }
        return null;
    }
    
    public static Vec3 calculateFaceNormal(final Vec3[] vertices) {
        final Vec3 v1 = Vec3.createVectorHelper(vertices[1].xCoord - vertices[0].xCoord, vertices[1].yCoord - vertices[0].yCoord, vertices[1].zCoord - vertices[0].zCoord);
        final Vec3 v2 = Vec3.createVectorHelper(vertices[2].xCoord - vertices[0].xCoord, vertices[2].yCoord - vertices[0].yCoord, vertices[2].zCoord - vertices[0].zCoord);
        return v1.crossProduct(v2).normalize();
    }
    
    public static double parseDouble(final String s) {
        return (s == null) ? 0.0 : Double.parseDouble(s.replace(',', '.'));
    }
    
    public static float RNG(final float a, final float min, final float max) {
        return (a < min) ? min : ((a > max) ? max : a);
    }
    
    public static double RNG(final double a, final double min, final double max) {
        return (a < min) ? min : ((a > max) ? max : a);
    }
    
    public static float smooth(final float rot, final float prevRot, final float tick) {
        return prevRot + (rot - prevRot) * tick;
    }
    
    public static float smoothRot(final float rot, float prevRot, final float tick) {
        if (rot - prevRot < -180.0f) {
            prevRot -= 360.0f;
        }
        else if (prevRot - rot < -180.0f) {
            prevRot += 360.0f;
        }
        return prevRot + (rot - prevRot) * tick;
    }
    
    public static double getRotateDiff(double base, double target) {
        base = getRotate360(base);
        target = getRotate360(target);
        if (target - base < -180.0) {
            target += 360.0;
        }
        else if (target - base > 180.0) {
            base += 360.0;
        }
        return target - base;
    }
    
    public static float getPosAngle(final double tx, final double tz, final double cx, final double cz) {
        final double length_A = Math.sqrt(tx * tx + tz * tz);
        final double length_B = Math.sqrt(cx * cx + cz * cz);
        final double cos_sita = (tx * cx + tz * cz) / (length_A * length_B);
        final double sita = Math.acos(cos_sita);
        return (float)(sita * 180.0 / 3.141592653589793);
    }
    
    public static boolean canPlayerCreateItem(final IRecipe recipe, final InventoryPlayer inventory) {
        if (recipe != null) {
            final Map<Item, Integer> map = getItemMapFromRecipe(recipe);
            for (int i = 0; i < inventory.getSizeInventory(); ++i) {
                final ItemStack is = inventory.getStackInSlot(i);
                if (is != null) {
                    final Item item = is.getItem();
                    if (map.containsKey(item)) {
                        map.put(item, map.get(item) - is.stackSize);
                    }
                }
            }
            for (final int j : map.values()) {
                if (j > 0) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    
    public static void applyEntityHurtResistantTimeConfig(final Entity entity) {
        if (entity instanceof EntityLivingBase) {
            final EntityLivingBase elb = (EntityLivingBase)entity;
            final MCH_Config config = MCH_MOD.config;
            final double h_time = MCH_Config.HurtResistantTime.prmDouble * elb.hurtResistantTime;
            elb.hurtResistantTime = (int)h_time;
        }
    }
    
    public static int round(final double d) {
        return (int)(d + 0.5);
    }
    
    public static Vec3 Rot2Vec3(final float yaw, final float pitch) {
        return Vec3.createVectorHelper((double)(-MathHelper.sin(yaw / 180.0f * 3.1415927f) * MathHelper.cos(pitch / 180.0f * 3.1415927f)), (double)(-MathHelper.sin(pitch / 180.0f * 3.1415927f)), (double)(MathHelper.cos(yaw / 180.0f * 3.1415927f) * MathHelper.cos(pitch / 180.0f * 3.1415927f)));
    }
    
    public static Vec3 RotVec3(final double x, final double y, final double z, final float yaw, final float pitch) {
        final Vec3 v = Vec3.createVectorHelper(x, y, z);
        v.rotateAroundX(pitch / 180.0f * 3.1415927f);
        v.rotateAroundY(yaw / 180.0f * 3.1415927f);
        return v;
    }
    
    public static Vec3 RotVec3(final double x, final double y, final double z, final float yaw, final float pitch, final float roll) {
        final Vec3 v = Vec3.createVectorHelper(x, y, z);
        W_Vec3.rotateAroundZ(roll / 180.0f * 3.1415927f, v);
        v.rotateAroundX(pitch / 180.0f * 3.1415927f);
        v.rotateAroundY(yaw / 180.0f * 3.1415927f);
        return v;
    }
    
    public static Vec3 RotVec3(final Vec3 vin, final float yaw, final float pitch) {
        final Vec3 v = Vec3.createVectorHelper(vin.xCoord, vin.yCoord, vin.zCoord);
        v.rotateAroundX(pitch / 180.0f * 3.1415927f);
        v.rotateAroundY(yaw / 180.0f * 3.1415927f);
        return v;
    }
    
    public static Vec3 RotVec3(final Vec3 vin, final float yaw, final float pitch, final float roll) {
        final Vec3 v = Vec3.createVectorHelper(vin.xCoord, vin.yCoord, vin.zCoord);
        W_Vec3.rotateAroundZ(roll / 180.0f * 3.1415927f, v);
        v.rotateAroundX(pitch / 180.0f * 3.1415927f);
        v.rotateAroundY(yaw / 180.0f * 3.1415927f);
        return v;
    }
    
    public static Vec3 _Rot2Vec3(final float yaw, final float pitch, final float roll) {
        return Vec3.createVectorHelper((double)(-MathHelper.sin(yaw / 180.0f * 3.1415927f) * MathHelper.cos(pitch / 180.0f * 3.1415927f)), (double)(-MathHelper.sin(pitch / 180.0f * 3.1415927f)), (double)(MathHelper.cos(yaw / 180.0f * 3.1415927f) * MathHelper.cos(pitch / 180.0f * 3.1415927f)));
    }
    
    public static double getRotate360(double r) {
        r %= 360.0;
        return (r >= 0.0) ? r : (r + 360.0);
    }
    
    public static void Log(final String format, final Object... data) {
        final String side = MCH_MOD.proxy.isRemote() ? "[Client]" : "[Server]";
        System.out.printf("[" + getTime() + "][" + "mcheli" + "]" + side + " " + format + "\n", data);
    }
    
    public static void Log(final World world, final String format, final Object... data) {
        if (world != null) {
            Log((world.isRemote ? "[ClientWorld]" : "[ServerWorld]") + " " + format, data);
        }
        else {
            Log("[UnknownWorld]" + format, data);
        }
    }
    
    public static void Log(final Entity entity, final String format, final Object... data) {
        if (entity != null) {
            Log(entity.worldObj, format, data);
        }
        else {
            Log((World)null, format, data);
        }
    }
    
    public static void DbgLog(final boolean isRemote, final String format, final Object... data) {
        final MCH_Config config = MCH_MOD.config;
        if (MCH_Config.DebugLog) {
            final String t = getTime();
            if (isRemote) {
                String playerName = "null";
                if (getClientPlayer() instanceof EntityPlayer) {
                    playerName = ((EntityPlayer)getClientPlayer()).getDisplayName();
                }
                System.out.println(String.format(format, data));
            }
            else {
                System.out.println(String.format(format, data));
            }
        }
    }
    
    public static void DbgLog(final World w, final String format, final Object... data) {
        DbgLog(w.isRemote, format, data);
    }
    
    public static String getTime() {
        final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SSS");
        return sdf.format(new Date());
    }
    
    public static String getAzimuthStr8(int dir) {
        dir %= 360;
        if (dir < 0) {
            dir += 360;
        }
        dir /= MCH_Lib.AZIMUTH_8_ANG;
        return MCH_Lib.AZIMUTH_8[dir];
    }
    
    public static void rotatePoints(final double[] points, float r) {
        r = r / 180.0f * 3.1415927f;
        for (int i = 0; i + 1 < points.length; i += 2) {
            final double x = points[i + 0];
            final double y = points[i + 1];
            points[i + 0] = x * MathHelper.cos(r) - y * MathHelper.sin(r);
            points[i + 1] = x * MathHelper.sin(r) + y * MathHelper.cos(r);
        }
    }
    
    public static void rotatePoints(final ArrayList<MCH_Vector2> points, float r) {
        r = r / 180.0f * 3.1415927f;
        for (int i = 0; i + 1 < points.size(); i += 2) {
            final double x = points.get(i + 0).x;
            final double y = points.get(i + 0).y;
            points.get(i + 0).x = x * MathHelper.cos(r) - y * MathHelper.sin(r);
            points.get(i + 0).y = x * MathHelper.sin(r) + y * MathHelper.cos(r);
        }
    }
    
    public static String[] listupFileNames(final String path) {
        final File dir = new File(path);
        return dir.list();
    }
    
    public static boolean isBlockInWater(final World w, final int x, final int y, final int z) {
        final int[][] offset = { { 0, -1, 0 }, { 0, 0, 0 }, { 0, 0, -1 }, { 0, 0, 1 }, { -1, 0, 0 }, { 1, 0, 0 }, { 0, 1, 0 } };
        if (y <= 0) {
            return false;
        }
        for (final int[] o : offset) {
            if (W_WorldFunc.isBlockWater(w, x + o[0], y + o[1], z + o[2])) {
                return true;
            }
        }
        return false;
    }
    
    public static int getBlockIdY(final World w, final double posX, final double posY, final double posZ, final int size, final int lenY, final boolean canColliableOnly) {
        final Block block = getBlockY(w, posX, posY, posZ, size, lenY, canColliableOnly);
        if (block == null) {
            return 0;
        }
        return W_Block.getIdFromBlock(block);
    }
    
    public static int getBlockIdY(final Entity entity, final int size, final int lenY) {
        return getBlockIdY(entity, size, lenY, true);
    }
    
    public static int getBlockIdY(final Entity entity, final int size, final int lenY, final boolean canColliableOnly) {
        final Block block = getBlockY(entity, size, lenY, canColliableOnly);
        if (block == null) {
            return 0;
        }
        return W_Block.getIdFromBlock(block);
    }
    
    public static Block getBlockY(final Entity entity, final int size, final int lenY, final boolean canColliableOnly) {
        return getBlockY(entity.worldObj, entity.posX, entity.posY, entity.posZ, size, lenY, canColliableOnly);
    }
    
    public static Block getBlockY(final World world, final Vec3 pos, final int size, final int lenY, final boolean canColliableOnly) {
        return getBlockY(world, pos.xCoord, pos.yCoord, pos.zCoord, size, lenY, canColliableOnly);
    }
    
    public static Block getBlockY(final World world, final double posX, final double posY, final double posZ, final int size, final int lenY, final boolean canColliableOnly) {
        if (lenY == 0) {
            return W_Blocks.air;
        }
        final int px = (int)(posX + 0.5);
        final int py = (int)(posY + 0.5);
        final int pz = (int)(posZ + 0.5);
        for (int cntY = (lenY > 0) ? lenY : (-lenY), y = 0; y < cntY; ++y) {
            if (py + y < 0 || py + y > 255) {
                return W_Blocks.air;
            }
            for (int x = -size / 2; x <= size / 2; ++x) {
                for (int z = -size / 2; z <= size / 2; ++z) {
                    final Block block = W_WorldFunc.getBlock(world, px + x, py + ((lenY > 0) ? y : (-y)), pz + z);
                    if (block != null && block != W_Blocks.air) {
                        if (!canColliableOnly) {
                            return block;
                        }
                        if (block.canStopRayTrace(0, true)) {
                            return block;
                        }
                    }
                }
            }
        }
        return W_Blocks.air;
    }
    
    public static Vec3 getYawPitchFromVec(final Vec3 v) {
        return getYawPitchFromVec(v.xCoord, v.yCoord, v.zCoord);
    }
    
    public static Vec3 getYawPitchFromVec(final double x, final double y, final double z) {
        final double p = MathHelper.sqrt_double(x * x + z * z);
        final float yaw = (float)(Math.atan2(z, x) * 180.0 / 3.141592653589793);
        final float roll = (float)(Math.atan2(y, p) * 180.0 / 3.141592653589793);
        return Vec3.createVectorHelper(0.0, (double)yaw, (double)roll);
    }
    
    public static float getAlpha(final int argb) {
        return (argb >> 24) / 255.0f;
    }
    
    public static float getRed(final int argb) {
        return (argb >> 16 & 0xFF) / 255.0f;
    }
    
    public static float getGreen(final int argb) {
        return (argb >> 8 & 0xFF) / 255.0f;
    }
    
    public static float getBlue(final int argb) {
        return (argb & 0xFF) / 255.0f;
    }
    
    public static void enableFirstPersonItemRender() {
        final MCH_Config config = MCH_MOD.config;
        switch (MCH_Config.DisableItemRender.prmInt) {
            case 2: {
                MCH_ItemRendererDummy.disableDummyItemRenderer();
                break;
            }
            case 3: {
                W_Reflection.restoreCameraZoom();
                break;
            }
        }
    }
    
    public static void disableFirstPersonItemRender(final ItemStack itemStack) {
        if (itemStack != null && itemStack.getItem() instanceof ItemMapBase && !(W_McClient.getRenderEntity() instanceof MCH_ViewEntityDummy)) {
            return;
        }
        disableFirstPersonItemRender();
    }
    
    public static void disableFirstPersonItemRender() {
        final MCH_Config config = MCH_MOD.config;
        switch (MCH_Config.DisableItemRender.prmInt) {
            case 1: {
                W_Reflection.setItemRenderer_ItemToRender(new ItemStack((Item)MCH_MOD.invisibleItem));
                break;
            }
            case 2: {
                MCH_ItemRendererDummy.enableDummyItemRenderer();
                break;
            }
            case 3: {
                W_Reflection.setCameraZoom(1.01f);
                break;
            }
        }
    }
    
    public static Entity getClientPlayer() {
        return MCH_MOD.proxy.getClientPlayer();
    }
    
    public static void setRenderViewEntity(final EntityLivingBase entity) {
        final MCH_Config config = MCH_MOD.config;
        if (MCH_Config.ReplaceRenderViewEntity.prmBool) {
            W_McClient.setRenderEntity(entity);
        }
    }
    
    public static Map<Item, Integer> getItemMapFromRecipe(final IRecipe recipe) {
        final Map<Item, Integer> map = new HashMap<Item, Integer>();
        if (recipe instanceof ShapedRecipes) {
            for (final ItemStack is : ((ShapedRecipes)recipe).recipeItems) {
                if (is != null) {
                    final Item item = is.getItem();
                    if (map.containsKey(item)) {
                        map.put(item, map.get(item) + 1);
                    }
                    else {
                        map.put(item, 1);
                    }
                }
            }
        }
        else if (recipe instanceof ShapelessRecipes) {
            for (final Object o : ((ShapelessRecipes)recipe).recipeItems) {
                final ItemStack is2 = (ItemStack)o;
                if (is2 != null) {
                    final Item item2 = is2.getItem();
                    if (map.containsKey(item2)) {
                        map.put(item2, map.get(item2) + 1);
                    }
                    else {
                        map.put(item2, 1);
                    }
                }
            }
        }
        return map;
    }
    
    public static boolean isFlansModEntity(final Entity entity) {
        if (entity == null) {
            return false;
        }
        final String className = entity.getClass().getName();
        return entity != null && (className.indexOf("EntityVehicle") >= 0 || className.indexOf("EntityPlane") >= 0 || className.indexOf("EntityMecha") >= 0 || className.indexOf("EntityAAGun") >= 0);
    }
    
    static {
        MCH_Lib.mapMaterial = new HashMap<String, Material>();
        AZIMUTH_8 = new String[] { "S", "SW", "W", "NW", "N", "NE", "E", "SE" };
        AZIMUTH_8_ANG = 360 / MCH_Lib.AZIMUTH_8.length;
    }
}
