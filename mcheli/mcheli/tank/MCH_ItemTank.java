//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.tank;

import net.minecraft.world.*;
import net.minecraft.item.*;
import mcheli.*;
import mcheli.aircraft.*;

public class MCH_ItemTank extends MCH_ItemAircraft
{
    public MCH_ItemTank(final int par1) {
        super(par1);
        this.maxStackSize = 1;
    }
    
    public MCH_AircraftInfo getAircraftInfo() {
        return MCH_TankInfoManager.getFromItem((Item)this);
    }
    
    public MCH_EntityTank createAircraft(final World world, final double x, final double y, final double z, final ItemStack itemStack) {
        final MCH_TankInfo info = MCH_TankInfoManager.getFromItem((Item)this);
        if (info == null) {
            MCH_Lib.Log(world, "##### MCH_EntityTank Tank info null %s", new Object[] { this.getUnlocalizedName() });
            return null;
        }
        final MCH_EntityTank tank = new MCH_EntityTank(world);
        tank.setPosition(x, y + tank.yOffset, z);
        tank.prevPosX = x;
        tank.prevPosY = y;
        tank.prevPosZ = z;
        tank.camera.setPosition(x, y, z);
        tank.setTypeName(info.name);
        if (!world.isRemote) {
            tank.setTextureName(info.getTextureName());
        }
        return tank;
    }
}
