//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.weapon;

import net.minecraftforge.client.model.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import cpw.mods.fml.relauncher.*;
import mcheli.*;
import mcheli.wrapper.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;

public class MCH_EntityCartridge extends W_Entity
{
    public final String texture_name;
    public final IModelCustom model;
    private final float bound;
    private final float gravity;
    private final float scale;
    private int countOnUpdate;
    public float targetYaw;
    public float targetPitch;
    
    @SideOnly(Side.CLIENT)
    public static void spawnCartridge(final World world, final MCH_Cartridge cartridge, final double x, final double y, final double z, final double mx, final double my, final double mz, final float yaw, final float pitch) {
        if (cartridge != null) {
            final MCH_EntityCartridge entityFX = new MCH_EntityCartridge(world, cartridge, x, y, z, mx + (world.rand.nextFloat() - 0.5) * 0.07, my, mz + (world.rand.nextFloat() - 0.5) * 0.07);
            entityFX.prevRotationYaw = yaw;
            entityFX.rotationYaw = yaw;
            entityFX.targetYaw = yaw;
            entityFX.prevRotationPitch = pitch;
            entityFX.rotationPitch = pitch;
            entityFX.targetPitch = pitch;
            final float cy = yaw + cartridge.yaw;
            final float cp = pitch + cartridge.pitch;
            final double tX = -MathHelper.sin(cy / 180.0f * 3.1415927f) * MathHelper.cos(cp / 180.0f * 3.1415927f);
            final double tZ = MathHelper.cos(cy / 180.0f * 3.1415927f) * MathHelper.cos(cp / 180.0f * 3.1415927f);
            final double tY = -MathHelper.sin(cp / 180.0f * 3.1415927f);
            final double d = MathHelper.sqrt_double(tX * tX + tY * tY + tZ * tZ);
            if (Math.abs(d) > 0.001) {
                final MCH_EntityCartridge mch_EntityCartridge = entityFX;
                mch_EntityCartridge.motionX += tX * cartridge.acceleration / d;
                final MCH_EntityCartridge mch_EntityCartridge2 = entityFX;
                mch_EntityCartridge2.motionY += tY * cartridge.acceleration / d;
                final MCH_EntityCartridge mch_EntityCartridge3 = entityFX;
                mch_EntityCartridge3.motionZ += tZ * cartridge.acceleration / d;
            }
            world.spawnEntityInWorld((Entity)entityFX);
        }
    }
    
    public MCH_EntityCartridge(final World par1World, final MCH_Cartridge c, final double x, final double y, final double z, final double mx, final double my, final double mz) {
        super(par1World);
        this.setPositionAndRotation(x, y, z, 0.0f, 0.0f);
        this.motionX = mx;
        this.motionY = my;
        this.motionZ = mz;
        this.texture_name = c.name;
        this.model = c.model;
        this.bound = c.bound;
        this.gravity = c.gravity;
        this.scale = c.scale;
        this.countOnUpdate = 0;
    }
    
    public float getScale() {
        return this.scale;
    }
    
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.prevRotationYaw = this.rotationYaw;
        this.prevRotationPitch = this.rotationPitch;
        final int countOnUpdate = this.countOnUpdate;
        final MCH_Config config = MCH_MOD.config;
        if (countOnUpdate < MCH_Config.AliveTimeOfCartridge.prmInt) {
            ++this.countOnUpdate;
        }
        else {
            this.setDead();
        }
        this.motionX *= 0.98;
        this.motionZ *= 0.98;
        this.motionY += this.gravity;
        this.move();
    }
    
    public void rotation() {
        if (this.rotationYaw < this.targetYaw - 3.0f) {
            this.rotationYaw += 10.0f;
            if (this.rotationYaw > this.targetYaw) {
                this.rotationYaw = this.targetYaw;
            }
        }
        else if (this.rotationYaw > this.targetYaw + 3.0f) {
            this.rotationYaw -= 10.0f;
            if (this.rotationYaw < this.targetYaw) {
                this.rotationYaw = this.targetYaw;
            }
        }
        if (this.rotationPitch < this.targetPitch) {
            this.rotationPitch += 10.0f;
            if (this.rotationPitch > this.targetPitch) {
                this.rotationPitch = this.targetPitch;
            }
        }
        else if (this.rotationPitch > this.targetPitch) {
            this.rotationPitch -= 10.0f;
            if (this.rotationPitch < this.targetPitch) {
                this.rotationPitch = this.targetPitch;
            }
        }
    }
    
    public void move() {
        final Vec3 vec1 = W_WorldFunc.getWorldVec3(this.worldObj, this.posX, this.posY, this.posZ);
        final Vec3 vec2 = W_WorldFunc.getWorldVec3(this.worldObj, this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        final MovingObjectPosition m = W_WorldFunc.clip(this.worldObj, vec1, vec2);
        double d = Math.max(Math.abs(this.motionX), Math.abs(this.motionY));
        d = Math.max(d, Math.abs(this.motionZ));
        if (W_MovingObjectPosition.isHitTypeTile(m)) {
            this.setPosition(m.hitVec.xCoord, m.hitVec.yCoord, m.hitVec.zCoord);
            this.motionX += d * (this.rand.nextFloat() - 0.5f) * 0.10000000149011612;
            this.motionY += d * (this.rand.nextFloat() - 0.5f) * 0.10000000149011612;
            this.motionZ += d * (this.rand.nextFloat() - 0.5f) * 0.10000000149011612;
            if (d > 0.10000000149011612) {
                this.targetYaw += (float)(d * (this.rand.nextFloat() - 0.5f) * 720.0);
                this.targetPitch = (float)(d * (this.rand.nextFloat() - 0.5f) * 720.0);
            }
            else {
                this.targetPitch = 0.0f;
            }
            switch (m.sideHit) {
                case 0: {
                    if (this.motionY > 0.0) {
                        this.motionY = -this.motionY * this.bound;
                        break;
                    }
                    break;
                }
                case 1: {
                    if (this.motionY < 0.0) {
                        this.motionY = -this.motionY * this.bound;
                    }
                    this.targetPitch *= 0.3f;
                    break;
                }
                case 2: {
                    if (this.motionZ > 0.0) {
                        this.motionZ = -this.motionZ * this.bound;
                        break;
                    }
                    this.posZ += this.motionZ;
                    break;
                }
                case 3: {
                    if (this.motionZ < 0.0) {
                        this.motionZ = -this.motionZ * this.bound;
                        break;
                    }
                    this.posZ += this.motionZ;
                    break;
                }
                case 4: {
                    if (this.motionX > 0.0) {
                        this.motionX = -this.motionX * this.bound;
                        break;
                    }
                    this.posX += this.motionX;
                    break;
                }
                case 5: {
                    if (this.motionX < 0.0) {
                        this.motionX = -this.motionX * this.bound;
                        break;
                    }
                    this.posX += this.motionX;
                    break;
                }
            }
        }
        else {
            this.posX += this.motionX;
            this.posY += this.motionY;
            this.posZ += this.motionZ;
            if (d > 0.05000000074505806) {
                this.rotation();
            }
        }
    }
    
    protected void readEntityFromNBT(final NBTTagCompound var1) {
    }
    
    protected void writeEntityToNBT(final NBTTagCompound var1) {
    }
}
