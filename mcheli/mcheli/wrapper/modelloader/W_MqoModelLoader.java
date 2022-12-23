//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.wrapper.modelloader;

import net.minecraft.util.*;
import net.minecraftforge.client.model.*;
import java.net.*;

public class W_MqoModelLoader implements IModelCustomLoader
{
    private static final String[] types;
    
    public String getType() {
        return "Metasequoia model";
    }
    
    public String[] getSuffixes() {
        return W_MqoModelLoader.types;
    }
    
    public IModelCustom loadInstance(final ResourceLocation resource) throws ModelFormatException {
        return (IModelCustom)new W_MetasequoiaObject(resource);
    }
    
    public IModelCustom loadInstance(final String resourceName, final URL resource) throws ModelFormatException {
        return (IModelCustom)new W_MetasequoiaObject(resourceName, resource);
    }
    
    static {
        types = new String[] { "mqo" };
    }
}
