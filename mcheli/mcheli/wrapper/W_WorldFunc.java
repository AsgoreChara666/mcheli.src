//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.wrapper;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;

public class W_WorldFunc
{
    public static void DEF_playSoundEffect(final World w, final double x, final double y, final double z, final String name, final float volume, final float pitch) {
        w.playSoundEffect(x, y, z, name, volume, pitch);
    }
    
    public static void MOD_playSoundEffect(final World w, final double x, final double y, final double z, final String name, final float volume, final float pitch) {
        DEF_playSoundEffect(w, x, y, z, W_MOD.DOMAIN + ":" + name, volume, pitch);
    }
    
    private static void playSoundAtEntity(final Entity e, final String name, final float volume, final float pitch) {
        e.worldObj.playSoundAtEntity(e, name, volume, pitch);
    }
    
    public static void MOD_playSoundAtEntity(final Entity e, final String name, final float volume, final float pitch) {
        playSoundAtEntity(e, W_MOD.DOMAIN + ":" + name, volume, pitch);
    }
    
    public static int getBlockId(final World w, final int x, final int y, final int z) {
        return Block.getIdFromBlock(w.getBlock(x, y, z));
    }
    
    public static Block getBlock(final World w, final int x, final int y, final int z) {
        return w.getBlock(x, y, z);
    }
    
    public static Material getBlockMaterial(final World w, final int x, final int y, final int z) {
        return w.getBlock(x, y, z).getMaterial();
    }
    
    public static boolean isBlockWater(final World w, final int x, final int y, final int z) {
        return isEqualBlock(w, x, y, z, W_Block.getWater());
    }
    
    public static boolean isEqualBlock(final World w, final int x, final int y, final int z, final Block block) {
        return Block.isEqualTo(w.getBlock(x, y, z), block);
    }
    
    public static MovingObjectPosition clip(final World w, final Vec3 par1Vec3, final Vec3 par2Vec3) {
        return w.rayTraceBlocks(par1Vec3, par2Vec3);
    }
    
    public static MovingObjectPosition clip(final World w, final Vec3 par1Vec3, final Vec3 par2Vec3, final boolean b) {
        return w.rayTraceBlocks(par1Vec3, par2Vec3, b);
    }
    
    public static MovingObjectPosition clip(final World w, final Vec3 par1Vec3, final Vec3 par2Vec3, final boolean b1, final boolean b2, final boolean b3) {
        return w.rayTraceBlocks(par1Vec3, par2Vec3, b1, b2, b3);
    }
    
    public static boolean setBlock(final World w, final int a, final int b, final int c, final Block d) {
        return w.setBlock(a, b, c, d);
    }
    
    public static void setBlock(final World w, final int x, final int y, final int z, final Block b, final int i, final int j) {
        w.setBlock(x, y, z, b, i, j);
    }
    
    public static boolean destroyBlock(final World w, final int x, final int y, final int z, final boolean par4) {
        return w.breakBlock(x, y, z, par4);
    }
    
    public static Vec3 getWorldVec3(final World w, final double x, final double y, final double z) {
        return Vec3.createVectorHelper(x, y, z);
    }
    
    public static Vec3 getWorldVec3EntityPos(final Entity e) {
        return getWorldVec3(e.worldObj, e.posX, e.posY, e.posZ);
    }
}
