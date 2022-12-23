//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.plane;

import net.minecraft.world.*;
import net.minecraft.item.*;
import mcheli.*;
import mcheli.aircraft.*;

public class MCP_ItemPlane extends MCH_ItemAircraft
{
    public MCP_ItemPlane(final int par1) {
        super(par1);
        this.maxStackSize = 1;
    }
    
    public MCH_AircraftInfo getAircraftInfo() {
        return MCP_PlaneInfoManager.getFromItem((Item)this);
    }
    
    public MCP_EntityPlane createAircraft(final World world, final double x, final double y, final double z, final ItemStack itemStack) {
        final MCP_PlaneInfo info = MCP_PlaneInfoManager.getFromItem((Item)this);
        if (info == null) {
            MCH_Lib.Log(world, "##### MCP_EntityPlane Plane info null %s", new Object[] { this.getUnlocalizedName() });
            return null;
        }
        final MCP_EntityPlane plane = new MCP_EntityPlane(world);
        plane.setPosition(x, y + plane.yOffset, z);
        plane.prevPosX = x;
        plane.prevPosY = y;
        plane.prevPosZ = z;
        plane.camera.setPosition(x, y, z);
        plane.setTypeName(info.name);
        if (!world.isRemote) {
            plane.setTextureName(info.getTextureName());
        }
        return plane;
    }
}
