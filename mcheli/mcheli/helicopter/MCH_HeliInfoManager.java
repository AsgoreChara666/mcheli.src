//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.helicopter;

import mcheli.*;
import net.minecraft.item.*;
import mcheli.aircraft.*;
import java.util.*;

public class MCH_HeliInfoManager extends MCH_AircraftInfoManager
{
    private static final MCH_HeliInfoManager instance;
    public static final HashMap<String, MCH_HeliInfo> map;
    
    private MCH_HeliInfoManager() {
    }
    
    public static MCH_HeliInfoManager getInstance() {
        return MCH_HeliInfoManager.instance;
    }
    
    public static MCH_HeliInfo get(final String name) {
        return MCH_HeliInfoManager.map.get(name);
    }
    
    public MCH_BaseInfo newInfo(final String name) {
        return (MCH_BaseInfo)new MCH_HeliInfo(name);
    }
    
    public Map getMap() {
        return MCH_HeliInfoManager.map;
    }
    
    public static MCH_HeliInfo getFromItem(final Item item) {
        return getInstance().getAcInfoFromItem(item);
    }
    
    public MCH_HeliInfo getAcInfoFromItem(final Item item) {
        if (item == null) {
            return null;
        }
        for (final MCH_HeliInfo info : MCH_HeliInfoManager.map.values()) {
            if (info.item == item) {
                return info;
            }
        }
        return null;
    }
    
    static {
        instance = new MCH_HeliInfoManager();
        map = new LinkedHashMap<String, MCH_HeliInfo>();
    }
}
