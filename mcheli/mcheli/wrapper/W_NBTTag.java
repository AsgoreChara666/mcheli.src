//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.wrapper;

import net.minecraft.nbt.*;

public class W_NBTTag
{
    public static final int TAG_COMPOUND = 10;
    
    public static NBTTagCompound tagAt(final NBTTagList list, final int i) {
        return (list != null) ? list.getCompoundTagAt(i) : null;
    }
    
    public static NBTTagList getTagList(final NBTTagCompound nbt, final String s, final int i) {
        return nbt.getTagList(s, i);
    }
    
    public static NBTTagIntArray newTagIntArray(final String s, final int[] n) {
        return new NBTTagIntArray(n);
    }
}
