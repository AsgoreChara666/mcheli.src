//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.wrapper;

import net.minecraft.item.*;
import net.minecraftforge.client.*;

public class W_MinecraftForgeClient
{
    public static void registerItemRenderer(final Item item, final IItemRenderer renderer) {
        if (item != null) {
            MinecraftForgeClient.registerItemRenderer(item, renderer);
        }
    }
}
