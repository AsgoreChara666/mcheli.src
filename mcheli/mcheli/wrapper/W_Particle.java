//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.wrapper;

import net.minecraft.world.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;

public class W_Particle
{
    public static String getParticleTileCrackName(final World w, final int blockX, final int blockY, final int blockZ) {
        final Block block = w.getBlock(blockX, blockY, blockZ);
        if (block.getMaterial() != Material.air) {
            return "blockcrack_" + Block.getIdFromBlock(block) + "_" + w.getBlockMetadata(blockX, blockY, blockZ);
        }
        return "";
    }
    
    public static String getParticleTileDustName(final World w, final int blockX, final int blockY, final int blockZ) {
        final Block block = w.getBlock(blockX, blockY, blockZ);
        if (block.getMaterial() != Material.air) {
            return "blockdust_" + Block.getIdFromBlock(block) + "_" + w.getBlockMetadata(blockX, blockY, blockZ);
        }
        return "";
    }
}
