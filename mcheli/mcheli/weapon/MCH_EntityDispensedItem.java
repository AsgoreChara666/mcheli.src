//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.weapon;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import mcheli.wrapper.*;
import mcheli.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;

public class MCH_EntityDispensedItem extends MCH_EntityBaseBullet
{
    public MCH_EntityDispensedItem(final World par1World) {
        super(par1World);
    }
    
    public MCH_EntityDispensedItem(final World par1World, final double posX, final double posY, final double posZ, final double targetX, final double targetY, final double targetZ, final float yaw, final float pitch, final double acceleration) {
        super(par1World, posX, posY, posZ, targetX, targetY, targetZ, yaw, pitch, acceleration);
    }
    
    public void onUpdate() {
        super.onUpdate();
        if (this.getInfo() != null && !this.getInfo().disableSmoke) {
            this.spawnParticle(this.getInfo().trajectoryParticleName, 3, 7.0f * this.getInfo().smokeSize);
        }
        if (!this.worldObj.isRemote && this.getInfo() != null) {
            if (this.acceleration < 1.0E-4) {
                this.motionX *= 0.999;
                this.motionZ *= 0.999;
            }
            if (this.isInWater()) {
                this.motionX *= this.getInfo().velocityInWater;
                this.motionY *= this.getInfo().velocityInWater;
                this.motionZ *= this.getInfo().velocityInWater;
            }
        }
        this.onUpdateBomblet();
    }
    
    protected void onImpact(final MovingObjectPosition m, final float damageFactor) {
        if (!this.worldObj.isRemote) {
            final AxisAlignedBB boundingBox = this.boundingBox;
            boundingBox.maxY += 2000.0;
            final AxisAlignedBB boundingBox2 = this.boundingBox;
            boundingBox2.minY += 2000.0;
            EntityPlayer player = null;
            Item item = null;
            int itemDamage = 0;
            if (m != null && this.getInfo() != null) {
                if (this.shootingAircraft instanceof EntityPlayer) {
                    player = (EntityPlayer)this.shootingAircraft;
                }
                if (this.shootingEntity instanceof EntityPlayer) {
                    player = (EntityPlayer)this.shootingEntity;
                }
                item = this.getInfo().dispenseItem;
                itemDamage = this.getInfo().dispenseDamege;
            }
            if (player != null && !player.isDead && item != null) {
                final EntityPlayer dummyPlayer = (EntityPlayer)new MCH_DummyEntityPlayer(this.worldObj, player);
                dummyPlayer.rotationPitch = 90.0f;
                for (int RNG = this.getInfo().dispenseRange - 1, x = -RNG; x <= RNG; ++x) {
                    for (int y = -RNG; y <= RNG; ++y) {
                        if (y >= 0 && y < 256) {
                            for (int z = -RNG; z <= RNG; ++z) {
                                final int dist = x * x + y * y + z * z;
                                if (dist <= RNG * RNG) {
                                    if (dist <= 0.5 * RNG * RNG) {
                                        this.useItemToBlock(m.blockX + x, m.blockY + y, m.blockZ + z, item, itemDamage, dummyPlayer);
                                    }
                                    else if (this.rand.nextInt(2) == 0) {
                                        this.useItemToBlock(m.blockX + x, m.blockY + y, m.blockZ + z, item, itemDamage, dummyPlayer);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            this.setDead();
        }
    }
    
    private void useItemToBlock(final int x, final int y, final int z, final Item item, final int itemDamage, final EntityPlayer dummyPlayer) {
        dummyPlayer.posX = x + 0.5;
        dummyPlayer.posY = y + 2.5;
        dummyPlayer.posZ = z + 0.5;
        dummyPlayer.rotationYaw = (float)this.rand.nextInt(360);
        final Block block = W_WorldFunc.getBlock(this.worldObj, x, y, z);
        final Material blockMat = W_WorldFunc.getBlockMaterial(this.worldObj, x, y, z);
        if (block != W_Blocks.air && blockMat != Material.air) {
            if (item == W_Item.getItemByName("water_bucket")) {
                final MCH_Config config = MCH_MOD.config;
                if (MCH_Config.Collision_DestroyBlock.prmBool) {
                    if (blockMat == Material.fire) {
                        this.worldObj.setBlockToAir(x, y, z);
                    }
                    else if (blockMat == Material.lava) {
                        final int metadata = this.worldObj.getBlockMetadata(x, y, z);
                        if (metadata == 0) {
                            W_WorldFunc.setBlock(this.worldObj, x, y, z, W_Blocks.obsidian);
                        }
                        else if (metadata <= 4) {
                            W_WorldFunc.setBlock(this.worldObj, x, y, z, W_Blocks.cobblestone);
                        }
                    }
                }
            }
            else if (!item.onItemUseFirst(new ItemStack(item, 1, itemDamage), dummyPlayer, this.worldObj, x, y, z, 1, (float)x, (float)y, (float)z) && !item.onItemUse(new ItemStack(item, 1, itemDamage), dummyPlayer, this.worldObj, x, y, z, 1, (float)x, (float)y, (float)z)) {
                item.onItemRightClick(new ItemStack(item, 1, itemDamage), this.worldObj, dummyPlayer);
            }
        }
    }
    
    public void sprinkleBomblet() {
        if (!this.worldObj.isRemote) {
            final MCH_EntityDispensedItem e = new MCH_EntityDispensedItem(this.worldObj, this.posX, this.posY, this.posZ, this.motionX, this.motionY, this.motionZ, (float)this.rand.nextInt(360), 0.0f, this.acceleration);
            e.setParameterFromWeapon((MCH_EntityBaseBullet)this, this.shootingAircraft, this.shootingEntity);
            e.setName(this.getName());
            final float MOTION = 1.0f;
            final float RANDOM = this.getInfo().bombletDiff;
            e.motionX = this.motionX * 1.0 + (this.rand.nextFloat() - 0.5f) * RANDOM;
            e.motionY = this.motionY * 1.0 / 2.0 + (this.rand.nextFloat() - 0.5f) * RANDOM / 2.0f;
            e.motionZ = this.motionZ * 1.0 + (this.rand.nextFloat() - 0.5f) * RANDOM;
            e.setBomblet();
            this.worldObj.spawnEntityInWorld((Entity)e);
        }
    }
    
    public MCH_BulletModel getDefaultBulletModel() {
        return MCH_DefaultBulletModels.Bomb;
    }
}
