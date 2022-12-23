//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.wrapper;

import net.minecraft.block.*;
import net.minecraft.block.material.*;

public abstract class W_Block extends Block
{
    protected W_Block(final Material materialIn) {
        super(materialIn);
    }
    
    public static Block getBlockFromName(final String name) {
        return Block.getBlockFromName(name);
    }
    
    public static Block getSnowLayer() {
        return W_Blocks.snow_layer;
    }
    
    public static boolean isNull(final Block block) {
        return block == null || block == W_Blocks.air;
    }
    
    public static boolean isEqual(final int blockId, final Block block) {
        return Block.isEqualTo(Block.getBlockById(blockId), block);
    }
    
    public static boolean isEqual(final Block block1, final Block block2) {
        return Block.isEqualTo(block1, block2);
    }
    
    public static Block getWater() {
        return W_Blocks.water;
    }
    
    public static Block getBlockById(final int i) {
        return Block.getBlockById(i);
    }
}
