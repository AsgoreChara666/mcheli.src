//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.tool;

import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import mcheli.wrapper.*;
import mcheli.*;
import mcheli.aircraft.*;
import java.util.*;
import net.minecraft.util.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.item.*;
import com.google.common.collect.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.*;

public class MCH_ItemWrench extends W_Item
{
    private float damageVsEntity;
    private final Item.ToolMaterial toolMaterial;
    private static Random rand;
    
    public MCH_ItemWrench(final int itemId, final Item.ToolMaterial material) {
        super(itemId);
        this.toolMaterial = material;
        this.maxStackSize = 1;
        this.setMaxDurability(material.getMaxUses());
        this.damageVsEntity = 4.0f + material.getDamageVsEntity();
    }
    
    public boolean canItemHarvestBlock(final Block b) {
        final Material material = b.getMaterial();
        return material == Material.iron || material instanceof MaterialLogic;
    }
    
    public float getStrVsBlock(final ItemStack itemStack, final Block block) {
        final Material material = block.getMaterial();
        if (material == Material.iron) {
            return 20.5f;
        }
        if (material instanceof MaterialLogic) {
            return 5.5f;
        }
        return 2.0f;
    }
    
    public static int getUseAnimCount(final ItemStack stack) {
        return getAnimCount(stack, "MCH_WrenchAnim");
    }
    
    public static void setUseAnimCount(final ItemStack stack, final int n) {
        setAnimCount(stack, "MCH_WrenchAnim", n);
    }
    
    public static int getAnimCount(final ItemStack stack, final String name) {
        if (!stack.hasTagCompound()) {
            stack.stackTagCompound = new NBTTagCompound();
        }
        if (stack.stackTagCompound.hasKey(name)) {
            return stack.stackTagCompound.getInteger(name);
        }
        stack.stackTagCompound.setInteger(name, 0);
        return 0;
    }
    
    public static void setAnimCount(final ItemStack stack, final String name, final int n) {
        if (!stack.hasTagCompound()) {
            stack.stackTagCompound = new NBTTagCompound();
        }
        stack.stackTagCompound.setInteger(name, n);
    }
    
    public boolean hitEntity(final ItemStack itemStack, final EntityLivingBase entity, final EntityLivingBase player) {
        if (!player.worldObj.isRemote) {
            if (MCH_ItemWrench.rand.nextInt(40) == 0) {
                entity.entityDropItem(new ItemStack(W_Item.getItemByName("iron_ingot"), 1, 0), 0.0f);
            }
            else if (MCH_ItemWrench.rand.nextInt(20) == 0) {
                entity.entityDropItem(new ItemStack(W_Item.getItemByName("gunpowder"), 1, 0), 0.0f);
            }
        }
        itemStack.damageItem(2, player);
        return true;
    }
    
    public void onPlayerStoppedUsing(final ItemStack stack, final World world, final EntityPlayer player, final int count) {
        setUseAnimCount(stack, 0);
    }
    
    public void onUsingTick(final ItemStack stack, final EntityPlayer player, final int count) {
        if (player.worldObj.isRemote) {
            final MCH_EntityAircraft ac = this.getMouseOverAircraft(player);
            if (ac != null) {
                int cnt = getUseAnimCount(stack);
                if (cnt <= 0) {
                    cnt = 16;
                }
                else {
                    --cnt;
                }
                setUseAnimCount(stack, cnt);
            }
        }
        if (!player.worldObj.isRemote && count < this.getMaxItemUseDuration(stack) && count % 20 == 0) {
            final MCH_EntityAircraft ac = this.getMouseOverAircraft(player);
            if (ac != null && ac.getHP() > 0 && ac.repair(10)) {
                stack.damageItem(1, (EntityLivingBase)player);
                W_WorldFunc.MOD_playSoundEffect(player.worldObj, (int)ac.posX, (int)ac.posY, (int)ac.posZ, "wrench", 1.0f, 0.9f + MCH_ItemWrench.rand.nextFloat() * 0.2f);
            }
        }
    }
    
    public void onUpdate(final ItemStack item, final World world, final Entity entity, final int n, final boolean b) {
        if (entity instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer)entity;
            final ItemStack itemStack = player.getCurrentEquippedItem();
            if (itemStack == item) {
                MCH_MOD.proxy.setCreativeDigDelay(0);
            }
        }
    }
    
    public MCH_EntityAircraft getMouseOverAircraft(final EntityPlayer player) {
        final MovingObjectPosition m = this.getMouseOver((EntityLivingBase)player, 1.0f);
        MCH_EntityAircraft ac = null;
        if (m != null) {
            if (m.entityHit instanceof MCH_EntityAircraft) {
                ac = (MCH_EntityAircraft)m.entityHit;
            }
            else if (m.entityHit instanceof MCH_EntitySeat) {
                final MCH_EntitySeat seat = (MCH_EntitySeat)m.entityHit;
                if (seat.getParent() != null) {
                    ac = seat.getParent();
                }
            }
        }
        return ac;
    }
    
    private static MovingObjectPosition rayTrace(final EntityLivingBase entity, final double dist, final float tick) {
        final Vec3 vec3 = Vec3.createVectorHelper(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ);
        final Vec3 vec4 = entity.getLook(tick);
        final Vec3 vec5 = vec3.addVector(vec4.xCoord * dist, vec4.yCoord * dist, vec4.zCoord * dist);
        return entity.worldObj.rayTraceBlocks(vec3, vec5, false, false, true);
    }
    
    private MovingObjectPosition getMouseOver(final EntityLivingBase user, final float tick) {
        Entity pointedEntity = null;
        final double d0 = 4.0;
        MovingObjectPosition objectMouseOver = rayTrace(user, d0, tick);
        double d2 = d0;
        final Vec3 vec3 = Vec3.createVectorHelper(user.posX, user.posY + user.getEyeHeight(), user.posZ);
        if (objectMouseOver != null) {
            d2 = objectMouseOver.hitVec.distanceTo(vec3);
        }
        final Vec3 vec4 = user.getLook(tick);
        final Vec3 vec5 = vec3.addVector(vec4.xCoord * d0, vec4.yCoord * d0, vec4.zCoord * d0);
        pointedEntity = null;
        Vec3 vec6 = null;
        final float f1 = 1.0f;
        final List list = user.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)user, user.boundingBox.addCoord(vec4.xCoord * d0, vec4.yCoord * d0, vec4.zCoord * d0).expand((double)f1, (double)f1, (double)f1));
        double d3 = d2;
        for (int i = 0; i < list.size(); ++i) {
            final Entity entity = list.get(i);
            if (entity.canBeCollidedWith()) {
                final float f2 = entity.getCollisionBorderSize();
                final AxisAlignedBB axisalignedbb = entity.boundingBox.expand((double)f2, (double)f2, (double)f2);
                final MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec5);
                if (axisalignedbb.isVecInside(vec3)) {
                    if (0.0 < d3 || d3 == 0.0) {
                        pointedEntity = entity;
                        vec6 = ((movingobjectposition == null) ? vec3 : movingobjectposition.hitVec);
                        d3 = 0.0;
                    }
                }
                else if (movingobjectposition != null) {
                    final double d4 = vec3.distanceTo(movingobjectposition.hitVec);
                    if (d4 < d3 || d3 == 0.0) {
                        if (entity == user.ridingEntity && !entity.canRiderInteract()) {
                            if (d3 == 0.0) {
                                pointedEntity = entity;
                                vec6 = movingobjectposition.hitVec;
                            }
                        }
                        else {
                            pointedEntity = entity;
                            vec6 = movingobjectposition.hitVec;
                            d3 = d4;
                        }
                    }
                }
            }
        }
        if (pointedEntity != null && (d3 < d2 || objectMouseOver == null)) {
            objectMouseOver = new MovingObjectPosition(pointedEntity, vec6);
        }
        return objectMouseOver;
    }
    
    public boolean onBlockDestroyed(final ItemStack itemStack, final World world, final Block block, final int x, final int y, final int z, final EntityLivingBase entity) {
        if (block.getBlockHardness(world, x, y, z) != 0.0) {
            itemStack.damageItem(2, entity);
        }
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public boolean isFull3D() {
        return true;
    }
    
    public EnumAction getItemUseAction(final ItemStack itemStack) {
        return EnumAction.block;
    }
    
    public int getMaxItemUseDuration(final ItemStack itemStack) {
        return 72000;
    }
    
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer player) {
        player.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));
        return itemStack;
    }
    
    public int getItemEnchantability() {
        return this.toolMaterial.getEnchantability();
    }
    
    public String getToolMaterialName() {
        return this.toolMaterial.toString();
    }
    
    public boolean getIsRepairable(final ItemStack item1, final ItemStack item2) {
        return this.toolMaterial.getBaseItemForRepair() == item2.getItem() || super.getIsRepairable(item1, item2);
    }
    
    public Multimap getItemAttributeModifiers() {
        final Multimap multimap = super.getItemAttributeModifiers();
        multimap.put((Object)SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), (Object)new AttributeModifier(MCH_ItemWrench.itemModifierUUID, "Weapon modifier", (double)this.damageVsEntity, 0));
        return multimap;
    }
    
    static {
        MCH_ItemWrench.rand = new Random();
    }
}
