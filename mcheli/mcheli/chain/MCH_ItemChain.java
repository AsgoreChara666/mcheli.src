//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.chain;

import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.entity.item.*;
import mcheli.aircraft.*;
import mcheli.uav.*;
import mcheli.parachute.*;
import mcheli.*;
import mcheli.vehicle.*;
import mcheli.wrapper.*;
import java.util.*;
import net.minecraft.nbt.*;

public class MCH_ItemChain extends W_Item
{
    public MCH_ItemChain(final int par1) {
        super(par1);
        this.setMaxStackSize(1);
    }
    
    public static void interactEntity(final ItemStack item, final Entity entity, final EntityPlayer player, final World world) {
        if (!world.isRemote && entity != null && !entity.isDead) {
            if (entity instanceof EntityItem) {
                return;
            }
            if (entity instanceof MCH_EntityChain) {
                return;
            }
            if (entity instanceof MCH_EntityHitBox) {
                return;
            }
            if (entity instanceof MCH_EntitySeat) {
                return;
            }
            if (entity instanceof MCH_EntityUavStation) {
                return;
            }
            if (entity instanceof MCH_EntityParachute) {
                return;
            }
            if (W_Lib.isEntityLivingBase(entity)) {
                return;
            }
            final MCH_Config config = MCH_MOD.config;
            if (MCH_Config.FixVehicleAtPlacedPoint.prmBool && entity instanceof MCH_EntityVehicle) {
                return;
            }
            final MCH_EntityChain towingChain = getTowedEntityChain(entity);
            if (towingChain != null) {
                towingChain.setDead();
                return;
            }
            final Entity entityTowed = getTowedEntity(item, world);
            if (entityTowed == null) {
                playConnectTowedEntity(entity);
                setTowedEntity(item, entity);
            }
            else {
                if (W_Entity.isEqual(entityTowed, entity)) {
                    return;
                }
                final double diff = entity.getDistanceToEntity(entityTowed);
                if (diff < 2.0 || diff > 16.0) {
                    return;
                }
                final MCH_EntityChain chain = new MCH_EntityChain(world, (entityTowed.posX + entity.posX) / 2.0, (entityTowed.posY + entity.posY) / 2.0, (entityTowed.posZ + entity.posZ) / 2.0);
                chain.setChainLength((int)diff);
                chain.setTowEntity(entityTowed, entity);
                chain.prevPosX = chain.posX;
                chain.prevPosY = chain.posY;
                chain.prevPosZ = chain.posZ;
                world.spawnEntityInWorld((Entity)chain);
                playConnectTowingEntity(entity);
                setTowedEntity(item, null);
            }
        }
    }
    
    public static void playConnectTowingEntity(final Entity e) {
        W_WorldFunc.MOD_playSoundEffect(e.worldObj, e.posX, e.posY, e.posZ, "chain_ct", 1.0f, 1.0f);
    }
    
    public static void playConnectTowedEntity(final Entity e) {
        W_WorldFunc.MOD_playSoundEffect(e.worldObj, e.posX, e.posY, e.posZ, "chain", 1.0f, 1.0f);
    }
    
    public void onCreated(final ItemStack par1ItemStack, final World par2World, final EntityPlayer par3EntityPlayer) {
    }
    
    public static MCH_EntityChain getTowedEntityChain(final Entity entity) {
        final List list = entity.worldObj.getEntitiesWithinAABB((Class)MCH_EntityChain.class, entity.boundingBox.expand(25.0, 25.0, 25.0));
        if (list == null) {
            return null;
        }
        for (int i = 0; i < list.size(); ++i) {
            final MCH_EntityChain chain = list.get(i);
            if (chain.isTowingEntity()) {
                if (W_Entity.isEqual(chain.towEntity, entity)) {
                    return chain;
                }
                if (W_Entity.isEqual(chain.towedEntity, entity)) {
                    return chain;
                }
            }
        }
        return null;
    }
    
    public static void setTowedEntity(final ItemStack item, final Entity entity) {
        NBTTagCompound nbt = item.getTagCompound();
        if (nbt == null) {
            nbt = new NBTTagCompound();
            item.setTagCompound(nbt);
        }
        if (entity != null && !entity.isDead) {
            nbt.setInteger("TowedEntityId", W_Entity.getEntityId(entity));
            nbt.setString("TowedEntityUUID", entity.getPersistentID().toString());
        }
        else {
            nbt.setInteger("TowedEntityId", 0);
            nbt.setString("TowedEntityUUID", "");
        }
    }
    
    public static Entity getTowedEntity(final ItemStack item, final World world) {
        NBTTagCompound nbt = item.getTagCompound();
        if (nbt == null) {
            nbt = new NBTTagCompound();
            item.setTagCompound(nbt);
        }
        else if (nbt.hasKey("TowedEntityId") && nbt.hasKey("TowedEntityUUID")) {
            final int id = nbt.getInteger("TowedEntityId");
            final String uuid = nbt.getString("TowedEntityUUID");
            final Entity entity = world.getEntityByID(id);
            if (entity != null && !entity.isDead && uuid.compareTo(entity.getPersistentID().toString()) == 0) {
                return entity;
            }
        }
        return null;
    }
}
