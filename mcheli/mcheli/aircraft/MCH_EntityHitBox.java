//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.aircraft;

import mcheli.wrapper.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.entity.player.*;

public class MCH_EntityHitBox extends W_Entity
{
    public MCH_EntityAircraft parent;
    public int debugId;
    
    public MCH_EntityHitBox(final World world) {
        super(world);
        this.setSize(1.0f, 1.0f);
        this.yOffset = 0.0f;
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.parent = null;
        this.ignoreFrustumCheck = true;
        this.isImmuneToFire = true;
    }
    
    public MCH_EntityHitBox(final World world, final MCH_EntityAircraft ac, final float w, final float h) {
        this(world);
        this.setPosition(ac.posX, ac.posY + 1.0, ac.posZ);
        this.prevPosX = ac.posX;
        this.prevPosY = ac.posY + 1.0;
        this.prevPosZ = ac.posZ;
        this.parent = ac;
        this.setSize(w, h);
    }
    
    protected boolean canTriggerWalking() {
        return false;
    }
    
    public AxisAlignedBB getCollisionBox(final Entity par1Entity) {
        return par1Entity.boundingBox;
    }
    
    public AxisAlignedBB getBoundingBox() {
        return this.boundingBox;
    }
    
    public boolean canBePushed() {
        return false;
    }
    
    public double getMountedYOffset() {
        return -0.3;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource par1DamageSource, final float par2) {
        return this.parent != null && this.parent.attackEntityFrom(par1DamageSource, par2);
    }
    
    public boolean canBeCollidedWith() {
        return !this.isDead;
    }
    
    public void setDead() {
        super.setDead();
    }
    
    public void onUpdate() {
        super.onUpdate();
    }
    
    protected void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
    }
    
    protected void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
    }
    
    @SideOnly(Side.CLIENT)
    public float getShadowSize() {
        return 0.0f;
    }
    
    @Override
    public boolean interactFirst(final EntityPlayer player) {
        return this.parent != null && this.parent.interactFirst(player);
    }
}
