//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.block;

import net.minecraft.tileentity.*;
import mcheli.*;

public class MCH_DraftingTableTileEntity extends TileEntity
{
    public int getBlockMetadata() {
        if (this.blockMetadata == -1) {
            this.blockMetadata = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
            MCH_Lib.DbgLog(this.worldObj, "MCH_DraftingTableTileEntity.getBlockMetadata : %d(0x%08X)", this.blockMetadata, this.blockMetadata);
        }
        return this.blockMetadata;
    }
}
