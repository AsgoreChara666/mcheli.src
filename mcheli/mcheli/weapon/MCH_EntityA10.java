//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.weapon;

import net.minecraft.entity.*;
import net.minecraft.world.*;
import mcheli.wrapper.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;
import cpw.mods.fml.relauncher.*;

public class MCH_EntityA10 extends W_Entity
{
    public static final int DATAWT_NAME = 29;
    public final int DESPAWN_COUNT = 70;
    public int despawnCount;
    public Entity shootingAircraft;
    public Entity shootingEntity;
    public int shotCount;
    public int direction;
    public int power;
    public float acceleration;
    public int explosionPower;
    public boolean isFlaming;
    public String name;
    public MCH_WeaponInfo weaponInfo;
    static int snd_num;
    
    public MCH_EntityA10(final World world) {
        super(world);
        this.despawnCount = 0;
        this.shotCount = 0;
        this.direction = 0;
        this.ignoreFrustumCheck = true;
        this.preventEntitySpawning = false;
        this.setSize(5.0f, 3.0f);
        this.yOffset = this.height / 2.0f;
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.power = 32;
        this.acceleration = 4.0f;
        this.explosionPower = 1;
        this.isFlaming = false;
        this.shootingEntity = null;
        this.shootingAircraft = null;
        this.isImmuneToFire = true;
        this.renderDistanceWeight *= 10.0;
    }
    
    public MCH_EntityA10(final World world, final double x, final double y, final double z) {
        this(world);
        this.posX = x;
        this.prevPosX = x;
        this.lastTickPosX = x;
        this.posY = y;
        this.prevPosY = y;
        this.lastTickPosY = y;
        this.posZ = z;
        this.prevPosZ = z;
        this.lastTickPosZ = z;
    }
    
    protected boolean canTriggerWalking() {
        return false;
    }
    
    @Override
    protected void entityInit() {
        this.getDataWatcher().addObject(29, (Object)String.valueOf(""));
    }
    
    public void setWeaponName(final String s) {
        if (s != null && !s.isEmpty()) {
            this.weaponInfo = MCH_WeaponInfoManager.get(s);
            if (this.weaponInfo != null && !this.worldObj.isRemote) {
                this.getDataWatcher().updateObject(29, (Object)String.valueOf(s));
            }
        }
    }
    
    public String getWeaponName() {
        return this.getDataWatcher().getWatchableObjectString(29);
    }
    
    public MCH_WeaponInfo getInfo() {
        return this.weaponInfo;
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
    
    @Override
    public boolean attackEntityFrom(final DamageSource par1DamageSource, final float par2) {
        return false;
    }
    
    public boolean canBeCollidedWith() {
        return false;
    }
    
    public void setDead() {
        super.setDead();
    }
    
    public void onUpdate() {
        super.onUpdate();
        if (!this.isDead) {
            ++this.despawnCount;
        }
        if (this.weaponInfo == null) {
            this.setWeaponName(this.getWeaponName());
            if (this.weaponInfo == null) {
                this.setDead();
                return;
            }
        }
        if (this.worldObj.isRemote) {
            this.onUpdate_Client();
        }
        else {
            this.onUpdate_Server();
        }
        if (!this.isDead) {
            if (this.despawnCount <= 20) {
                this.motionY = -0.3;
            }
            else {
                this.setPosition(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
                this.motionY += 0.02;
            }
        }
    }
    
    public boolean isRender() {
        return this.despawnCount > 20;
    }
    
    private void onUpdate_Client() {
        this.shotCount += 4;
    }
    
    private void onUpdate_Server() {
        if (!this.isDead) {
            if (this.despawnCount > 70) {
                this.setDead();
            }
            else if (this.despawnCount > 0 && this.shotCount < 40) {
                for (int i = 0; i < 2; ++i) {
                    this.shotGAU8(true, this.shotCount);
                    ++this.shotCount;
                }
                if (this.shotCount == 38) {
                    W_WorldFunc.MOD_playSoundEffect(this.worldObj, this.posX, this.posY, this.posZ, "gau-8_snd", 150.0f, 1.0f);
                }
            }
        }
    }
    
    protected void shotGAU8(final boolean playSound, final int cnt) {
        final float yaw = (float)(90 * this.direction);
        final float pitch = 30.0f;
        double x = this.posX;
        final double y = this.posY;
        double z = this.posZ;
        double tX = this.rand.nextDouble() - 0.5;
        double tY = -2.6;
        double tZ = this.rand.nextDouble() - 0.5;
        if (this.direction == 0) {
            tZ += 10.0;
            z += cnt * 0.6;
        }
        if (this.direction == 1) {
            tX -= 10.0;
            x -= cnt * 0.6;
        }
        if (this.direction == 2) {
            tZ -= 10.0;
            z -= cnt * 0.6;
        }
        if (this.direction == 3) {
            tX += 10.0;
            x += cnt * 0.6;
        }
        final double dist = MathHelper.sqrt_double(tX * tX + tY * tY + tZ * tZ);
        tX = tX * 4.0 / dist;
        tY = tY * 4.0 / dist;
        tZ = tZ * 4.0 / dist;
        final MCH_EntityBullet e = new MCH_EntityBullet(this.worldObj, x, y, z, tX, tY, tZ, yaw, pitch, this.acceleration);
        e.setName(this.getWeaponName());
        e.explosionPower = ((this.shotCount % 4 == 0) ? this.explosionPower : 0);
        e.setPower(this.power);
        e.shootingEntity = this.shootingEntity;
        e.shootingAircraft = this.shootingAircraft;
        this.worldObj.spawnEntityInWorld((Entity)e);
    }
    
    protected void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
        par1NBTTagCompound.setString("WeaponName", this.getWeaponName());
    }
    
    protected void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
        this.despawnCount = 200;
        if (par1NBTTagCompound.hasKey("WeaponName")) {
            this.setWeaponName(par1NBTTagCompound.getString("WeaponName"));
        }
    }
    
    @SideOnly(Side.CLIENT)
    public float getShadowSize() {
        return 10.0f;
    }
    
    static {
        MCH_EntityA10.snd_num = 0;
    }
}
