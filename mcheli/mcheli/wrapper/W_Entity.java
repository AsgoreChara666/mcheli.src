//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.wrapper;

import net.minecraft.entity.item.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.crash.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraft.potion.*;
import net.minecraft.entity.*;

public abstract class W_Entity extends Entity
{
    public W_Entity(final World par1World) {
        super(par1World);
    }
    
    protected void entityInit() {
    }
    
    public static boolean isEntityFallingBlock(final Entity entity) {
        return entity instanceof EntityFallingBlock;
    }
    
    public static int getEntityId(final Entity entity) {
        return (entity != null) ? entity.getEntityId() : -1;
    }
    
    public static boolean isEqual(final Entity e1, final Entity e2) {
        final int i1 = getEntityId(e1);
        final int i2 = getEntityId(e2);
        return i1 == i2;
    }
    
    public EntityItem dropItemWithOffset(final Item item, final int par2, final float par3) {
        return this.entityDropItem(new ItemStack(item, par2, 0), par3);
    }
    
    public String getEntityName() {
        return super.getEntityString();
    }
    
    public boolean interactFirst(final EntityPlayer par1EntityPlayer) {
        return this.interact(par1EntityPlayer);
    }
    
    public boolean interact(final EntityPlayer par1EntityPlayer) {
        return false;
    }
    
    public boolean attackEntityFrom(final DamageSource par1DamageSource, final int par2) {
        return this.attackEntityFrom(par1DamageSource, (float)par2);
    }
    
    public boolean attackEntityFrom(final DamageSource par1DamageSource, final float par2) {
        return false;
    }
    
    public static boolean attackEntityFrom(final Entity entity, final DamageSource ds, final float par2) {
        return entity.attackEntityFrom(ds, par2);
    }
    
    public void addEntityCrashInfo(final CrashReportCategory par1CrashReportCategory) {
        super.addEntityCrashInfo(par1CrashReportCategory);
    }
    
    public static float getBlockExplosionResistance(final Entity entity, final Explosion par1Explosion, final World par2World, final int par3, final int par4, final int par5, final Block par6Block) {
        if (par6Block != null) {
            try {
                return entity.getExplosionResistance(par1Explosion, par2World, par3, par4, par5, par6Block);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0.0f;
    }
    
    public static boolean shouldExplodeBlock(final Entity entity, final Explosion par1Explosion, final World par2World, final int par3, final int par4, final int par5, final int par6, final float par7) {
        return entity.func_145774_a(par1Explosion, par2World, par3, par4, par5, W_Block.getBlockById(par6), par7);
    }
    
    public static PotionEffect getActivePotionEffect(final Entity entity, final Potion par1Potion) {
        return (entity instanceof EntityLivingBase) ? ((EntityLivingBase)entity).getActivePotionEffect(par1Potion) : null;
    }
    
    public static void removePotionEffectClient(final Entity entity, final int id) {
        if (entity instanceof EntityLivingBase) {
            ((EntityLivingBase)entity).removePotionEffectClient(id);
        }
    }
    
    public static void removePotionEffect(final Entity entity, final int id) {
        if (entity instanceof EntityLivingBase) {
            ((EntityLivingBase)entity).removePotionEffect(id);
        }
    }
    
    public static void addPotionEffect(final Entity entity, final PotionEffect pe) {
        if (entity instanceof EntityLivingBase) {
            ((EntityLivingBase)entity).addPotionEffect(pe);
        }
    }
    
    protected void doBlockCollisions() {
        super.doBlockCollisions();
    }
}
