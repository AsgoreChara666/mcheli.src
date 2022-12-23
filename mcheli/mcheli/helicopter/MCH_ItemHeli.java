//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.helicopter;

import net.minecraft.world.*;
import net.minecraft.item.*;
import mcheli.*;
import mcheli.aircraft.*;

public class MCH_ItemHeli extends MCH_ItemAircraft
{
    public MCH_ItemHeli(final int par1) {
        super(par1);
        this.maxStackSize = 1;
    }
    
    public MCH_AircraftInfo getAircraftInfo() {
        return (MCH_AircraftInfo)MCH_HeliInfoManager.getFromItem((Item)this);
    }
    
    public MCH_EntityHeli createAircraft(final World world, final double x, final double y, final double z, final ItemStack itemStack) {
        final MCH_HeliInfo info = MCH_HeliInfoManager.getFromItem((Item)this);
        if (info == null) {
            MCH_Lib.Log(world, "##### MCH_ItemHeli Heli info null %s", this.getUnlocalizedName());
            return null;
        }
        final MCH_EntityHeli heli = new MCH_EntityHeli(world);
        heli.setPosition(x, y + heli.yOffset, z);
        heli.prevPosX = x;
        heli.prevPosY = y;
        heli.prevPosZ = z;
        heli.camera.setPosition(x, y, z);
        heli.setTypeName(info.name);
        if (!world.isRemote) {
            heli.setTextureName(info.getTextureName());
        }
        return heli;
    }
}
