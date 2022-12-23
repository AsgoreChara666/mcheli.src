//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.block;

import net.minecraft.block.material.*;
import net.minecraft.entity.player.*;
import mcheli.*;
import net.minecraft.block.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.texture.*;
import cpw.mods.fml.relauncher.*;
import java.util.*;
import net.minecraft.item.*;
import mcheli.wrapper.*;

public class MCH_DraftingTableBlock extends W_BlockContainer implements ITileEntityProvider
{
    private final boolean isLighting;
    
    public MCH_DraftingTableBlock(final int blockId, final boolean p_i45421_1_) {
        super(blockId, Material.iron);
        this.setStepSound(W_Block.soundTypeMetal);
        this.setHardness(0.2f);
        this.isLighting = p_i45421_1_;
        if (p_i45421_1_) {
            this.setLightLevel(1.0f);
        }
    }
    
    public boolean onBlockActivated(final World world, final int x, final int y, final int z, final EntityPlayer player, final int par6, final float par7, final float par8, final float par9) {
        if (!world.isRemote) {
            if (!player.isSneaking()) {
                MCH_Lib.DbgLog(player.worldObj, "MCH_DraftingTableGui.MCH_DraftingTableGui OPEN GUI (%d, %d, %d)", x, y, z);
                player.openGui((Object)MCH_MOD.instance, 4, world, x, y, z);
            }
            else {
                final int yaw = world.getBlockMetadata(x, y, z);
                MCH_Lib.DbgLog(world, "MCH_DraftingTableBlock.onBlockActivated:yaw=%d Light %s", yaw, this.isLighting ? "OFF->ON" : "ON->OFF");
                if (this.isLighting) {
                    W_WorldFunc.setBlock(world, x, y, z, (Block)MCH_MOD.blockDraftingTable, yaw + 180, 2);
                }
                else {
                    W_WorldFunc.setBlock(world, x, y, z, (Block)MCH_MOD.blockDraftingTableLit, yaw + 180, 2);
                }
                world.setBlockMetadataWithNotify(x, y, z, yaw, 2);
                world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, "random.click", 0.3f, 0.5f);
            }
        }
        return true;
    }
    
    public TileEntity createNewTileEntity(final World world, final int a) {
        return new MCH_DraftingTableTileEntity();
    }
    
    public TileEntity createNewTileEntity(final World world) {
        return new MCH_DraftingTableTileEntity();
    }
    
    public boolean shouldSideBeRendered(final IBlockAccess worldIn, final int x, final int y, final int z, final int side) {
        return true;
    }
    
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    public boolean isOpaqueCube() {
        return false;
    }
    
    public boolean canHarvestBlock(final EntityPlayer player, final int meta) {
        return true;
    }
    
    public boolean canRenderInPass(final int pass) {
        return false;
    }
    
    public int getMobilityFlag() {
        return 1;
    }
    
    public void onBlockPlacedBy(final World world, final int par2, final int par3, final int par4, final EntityLivingBase entity, final ItemStack itemStack) {
        float pyaw = (float)MCH_Lib.getRotate360(entity.rotationYaw);
        pyaw += 22.5f;
        int yaw = (int)(pyaw / 45.0f);
        if (yaw < 0) {
            yaw = yaw % 8 + 8;
        }
        world.setBlockMetadataWithNotify(par2, par3, par4, yaw, 2);
        MCH_Lib.DbgLog(world, "MCH_DraftingTableBlock.onBlockPlacedBy:yaw=%d", yaw);
    }
    
    public boolean getUseNeighborBrightness() {
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon("mcheli:drafting_table");
    }
    
    public void registerIcons(final IconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon("mcheli:drafting_table");
    }
    
    public Item getItemDropped(final int meta, final Random random, final int fortune) {
        return W_Item.getItemFromBlock((Block)MCH_MOD.blockDraftingTable);
    }
    
    @SideOnly(Side.CLIENT)
    public Item getItem(final World world, final int x, final int y, final int z) {
        return W_Item.getItemFromBlock((Block)MCH_MOD.blockDraftingTable);
    }
    
    protected ItemStack createStackedBlock(final int meta) {
        return new ItemStack((Block)MCH_MOD.blockDraftingTable);
    }
}
