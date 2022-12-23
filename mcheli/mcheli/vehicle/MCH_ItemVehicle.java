//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.vehicle;

import net.minecraft.world.*;
import net.minecraft.item.*;
import mcheli.*;
import mcheli.aircraft.*;

public class MCH_ItemVehicle extends MCH_ItemAircraft
{
    public MCH_ItemVehicle(final int par1) {
        super(par1);
        this.maxStackSize = 1;
    }
    
    public MCH_AircraftInfo getAircraftInfo() {
        return MCH_VehicleInfoManager.getFromItem((Item)this);
    }
    
    public MCH_EntityVehicle createAircraft(final World world, final double x, final double y, final double z, final ItemStack item) {
        final MCH_VehicleInfo info = MCH_VehicleInfoManager.getFromItem((Item)this);
        if (info == null) {
            MCH_Lib.Log(world, "##### MCH_ItemVehicle Vehicle info null %s", new Object[] { this.getUnlocalizedName() });
            return null;
        }
        final MCH_EntityVehicle vehicle = new MCH_EntityVehicle(world);
        vehicle.setPosition(x, y + vehicle.yOffset, z);
        vehicle.prevPosX = x;
        vehicle.prevPosY = y;
        vehicle.prevPosZ = z;
        vehicle.camera.setPosition(x, y, z);
        vehicle.setTypeName(info.name);
        if (!world.isRemote) {
            vehicle.setTextureName(info.getTextureName());
        }
        return vehicle;
    }
}
