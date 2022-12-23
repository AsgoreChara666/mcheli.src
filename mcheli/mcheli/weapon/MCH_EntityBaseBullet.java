//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.weapon;

import net.minecraft.entity.*;
import net.minecraft.world.*;
import cpw.mods.fml.relauncher.*;
import mcheli.particles.*;
import net.minecraft.block.*;
import mcheli.chain.*;
import java.util.*;
import mcheli.wrapper.*;
import net.minecraft.entity.player.*;
import mcheli.aircraft.*;
import net.minecraft.util.*;
import net.minecraft.entity.passive.*;
import mcheli.*;
import net.minecraft.nbt.*;

public abstract class MCH_EntityBaseBullet extends W_Entity
{
    public static final int DATAWT_RESERVE1 = 26;
    public static final int DATAWT_TARGET_ENTITY = 27;
    public static final int DATAWT_MARKER_STAT = 28;
    public static final int DATAWT_NAME = 29;
    public static final int DATAWT_BULLET_MODEL = 30;
    public static final int DATAWT_BOMBLET_FLAG = 31;
    public Entity shootingEntity;
    public Entity shootingAircraft;
    private int countOnUpdate;
    public int explosionPower;
    public int explosionPowerInWater;
    private int power;
    public double acceleration;
    public double accelerationFactor;
    public Entity targetEntity;
    public int piercing;
    public int delayFuse;
    public int sprinkleTime;
    public byte isBomblet;
    private MCH_WeaponInfo weaponInfo;
    private MCH_BulletModel model;
    public double prevPosX2;
    public double prevPosY2;
    public double prevPosZ2;
    public double prevMotionX;
    public double prevMotionY;
    public double prevMotionZ;
    
    public MCH_EntityBaseBullet(final World par1World) {
        super(par1World);
        this.countOnUpdate = 0;
        this.setSize(1.0f, 1.0f);
        this.prevRotationYaw = this.rotationYaw;
        this.prevRotationPitch = this.rotationPitch;
        this.targetEntity = null;
        this.setPower(1);
        this.acceleration = 1.0;
        this.accelerationFactor = 1.0;
        this.piercing = 0;
        this.explosionPower = 0;
        this.explosionPowerInWater = 0;
        this.delayFuse = 0;
        this.sprinkleTime = 0;
        this.isBomblet = -1;
        this.weaponInfo = null;
        this.ignoreFrustumCheck = true;
        if (par1World.isRemote) {
            this.model = null;
        }
    }
    
    public MCH_EntityBaseBullet(final World par1World, final double px, final double py, final double pz, final double mx, final double my, final double mz, final float yaw, final float pitch, double acceleration) {
        this(par1World);
        this.setSize(1.0f, 1.0f);
        this.setLocationAndAngles(px, py, pz, yaw, pitch);
        this.setPosition(px, py, pz);
        this.prevRotationYaw = yaw;
        this.prevRotationPitch = pitch;
        this.yOffset = 0.0f;
        if (acceleration > 3.9) {
            acceleration = 3.9;
        }
        final double d = MathHelper.sqrt_double(mx * mx + my * my + mz * mz);
        this.motionX = mx * acceleration / d;
        this.motionY = my * acceleration / d;
        this.motionZ = mz * acceleration / d;
        this.prevMotionX = this.motionX;
        this.prevMotionY = this.motionY;
        this.prevMotionZ = this.motionZ;
        this.acceleration = acceleration;
    }
    
    public void setLocationAndAngles(final double par1, final double par3, final double par5, final float par7, final float par8) {
        super.setLocationAndAngles(par1, par3, par5, par7, par8);
        this.prevPosX2 = par1;
        this.prevPosY2 = par3;
        this.prevPosZ2 = par5;
    }
    
    public void setPositionAndRotation(final double x, final double y, final double z, final float yaw, final float pitch) {
        super.setPositionAndRotation(x, y, z, yaw, pitch);
    }
    
    protected void setRotation(final float yaw, final float pitch) {
        super.setRotation(yaw, this.rotationPitch);
    }
    
    @SideOnly(Side.CLIENT)
    public void setPositionAndRotation2(final double x, final double y, final double z, final float yaw, final float pitch, final int rotationIncrements) {
        this.setPosition(x, (y + this.posY * 2.0) / 3.0, z);
        this.setRotation(yaw, pitch);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.getDataWatcher().addObject(27, (Object)0);
        this.getDataWatcher().addObject(29, (Object)String.valueOf(""));
        this.getDataWatcher().addObject(30, (Object)String.valueOf(""));
        this.getDataWatcher().addObject(31, (Object)0);
    }
    
    public void setName(final String s) {
        if (s != null && !s.isEmpty()) {
            this.weaponInfo = MCH_WeaponInfoManager.get(s);
            if (this.weaponInfo != null) {
                if (!this.worldObj.isRemote) {
                    this.getDataWatcher().updateObject(29, (Object)String.valueOf(s));
                }
                this.onSetWeasponInfo();
            }
        }
    }
    
    public String getName() {
        return this.getDataWatcher().getWatchableObjectString(29);
    }
    
    public MCH_WeaponInfo getInfo() {
        return this.weaponInfo;
    }
    
    public void onSetWeasponInfo() {
        if (!this.worldObj.isRemote) {
            this.isBomblet = 0;
        }
        if (this.getInfo().bomblet > 0) {
            this.sprinkleTime = this.getInfo().bombletSTime;
        }
        this.piercing = this.getInfo().piercing;
        if (this instanceof MCH_EntityBullet) {
            if (this.getInfo().acceleration > 4.0f) {
                this.accelerationFactor = this.getInfo().acceleration / 4.0f;
            }
        }
        else if (this instanceof MCH_EntityRocket && this.isBomblet == 0 && this.getInfo().acceleration > 4.0f) {
            this.accelerationFactor = this.getInfo().acceleration / 4.0f;
        }
    }
    
    public void setDead() {
        super.setDead();
    }
    
    public void setBomblet() {
        this.isBomblet = 1;
        this.sprinkleTime = 0;
        this.dataWatcher.updateObject(31, (Object)1);
    }
    
    public byte getBomblet() {
        return this.dataWatcher.getWatchableObjectByte(31);
    }
    
    public void setTargetEntity(final Entity entity) {
        this.targetEntity = entity;
        if (!this.worldObj.isRemote) {
            if (this.targetEntity instanceof EntityPlayerMP) {
                MCH_Lib.DbgLog(this.worldObj, "MCH_EntityBaseBullet.setTargetEntity alert" + this.targetEntity + " / " + this.targetEntity.ridingEntity, new Object[0]);
                if (this.targetEntity.ridingEntity != null && !(this.targetEntity.ridingEntity instanceof MCH_EntityAircraft) && !(this.targetEntity.ridingEntity instanceof MCH_EntitySeat)) {
                    W_WorldFunc.MOD_playSoundAtEntity(this.targetEntity, "alert", 2.0f, 1.0f);
                }
            }
            if (entity != null) {
                this.getDataWatcher().updateObject(27, (Object)W_Entity.getEntityId(entity));
            }
            else {
                this.getDataWatcher().updateObject(27, (Object)0);
            }
        }
    }
    
    public int getTargetEntityID() {
        if (this.targetEntity != null) {
            return W_Entity.getEntityId(this.targetEntity);
        }
        return this.getDataWatcher().getWatchableObjectInt(27);
    }
    
    public MCH_BulletModel getBulletModel() {
        if (this.getInfo() == null) {
            return null;
        }
        if (this.isBomblet < 0) {
            return null;
        }
        if (this.model == null) {
            if (this.isBomblet == 1) {
                this.model = this.getInfo().bombletModel;
            }
            else {
                this.model = this.getInfo().bulletModel;
            }
            if (this.model == null) {
                this.model = this.getDefaultBulletModel();
            }
        }
        return this.model;
    }
    
    public abstract MCH_BulletModel getDefaultBulletModel();
    
    public void sprinkleBomblet() {
    }
    
    public void spawnParticle(final String name, final int num, final float size) {
        if (this.worldObj.isRemote) {
            if (name.isEmpty() || num < 1 || num > 50) {
                return;
            }
            final double x = (this.posX - this.prevPosX) / num;
            final double y = (this.posY - this.prevPosY) / num;
            final double z = (this.posZ - this.prevPosZ) / num;
            final double x2 = (this.prevPosX - this.prevPosX2) / num;
            final double y2 = (this.prevPosY - this.prevPosY2) / num;
            final double z2 = (this.prevPosZ - this.prevPosZ2) / num;
            if (name.equals("explode")) {
                for (int i = 0; i < num; ++i) {
                    final MCH_ParticleParam prm = new MCH_ParticleParam(this.worldObj, "smoke", (this.prevPosX + x * i + (this.prevPosX2 + x2 * i)) / 2.0, (this.prevPosY + y * i + (this.prevPosY2 + y2 * i)) / 2.0, (this.prevPosZ + z * i + (this.prevPosZ2 + z2 * i)) / 2.0);
                    prm.size = size + this.rand.nextFloat();
                    MCH_ParticlesUtil.spawnParticle(prm);
                }
            }
            else {
                for (int i = 0; i < num; ++i) {
                    MCH_ParticlesUtil.DEF_spawnParticle(name, (this.prevPosX + x * i + (this.prevPosX2 + x2 * i)) / 2.0, (this.prevPosY + y * i + (this.prevPosY2 + y2 * i)) / 2.0, (this.prevPosZ + z * i + (this.prevPosZ2 + z2 * i)) / 2.0, 0.0, 0.0, 0.0, 50.0f);
                }
            }
        }
    }
    
    public void DEF_spawnParticle(final String name, final int num, final float size) {
        if (this.worldObj.isRemote) {
            if (name.isEmpty() || num < 1 || num > 50) {
                return;
            }
            final double x = (this.posX - this.prevPosX) / num;
            final double y = (this.posY - this.prevPosY) / num;
            final double z = (this.posZ - this.prevPosZ) / num;
            final double x2 = (this.prevPosX - this.prevPosX2) / num;
            final double y2 = (this.prevPosY - this.prevPosY2) / num;
            final double z2 = (this.prevPosZ - this.prevPosZ2) / num;
            for (int i = 0; i < num; ++i) {
                MCH_ParticlesUtil.DEF_spawnParticle(name, (this.prevPosX + x * i + (this.prevPosX2 + x2 * i)) / 2.0, (this.prevPosY + y * i + (this.prevPosY2 + y2 * i)) / 2.0, (this.prevPosZ + z * i + (this.prevPosZ2 + z2 * i)) / 2.0, 0.0, 0.0, 0.0, 150.0f);
            }
        }
    }
    
    public int getCountOnUpdate() {
        return this.countOnUpdate;
    }
    
    public void clearCountOnUpdate() {
        this.countOnUpdate = 0;
    }
    
    @SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(final double par1) {
        double d1 = this.boundingBox.getAverageEdgeLength() * 4.0;
        d1 *= 64.0;
        return par1 < d1 * d1;
    }
    
    public void setParameterFromWeapon(final MCH_WeaponBase w, final Entity entity, final Entity user) {
        this.explosionPower = w.explosionPower;
        this.explosionPowerInWater = w.explosionPowerInWater;
        this.setPower(w.power);
        this.piercing = w.piercing;
        this.shootingAircraft = entity;
        this.shootingEntity = user;
    }
    
    public void setParameterFromWeapon(final MCH_EntityBaseBullet b, final Entity entity, final Entity user) {
        this.explosionPower = b.explosionPower;
        this.explosionPowerInWater = b.explosionPowerInWater;
        this.setPower(b.getPower());
        this.piercing = b.piercing;
        this.shootingAircraft = entity;
        this.shootingEntity = user;
    }
    
    public void setMotion(final double targetX, final double targetY, final double targetZ) {
        final double d6 = MathHelper.sqrt_double(targetX * targetX + targetY * targetY + targetZ * targetZ);
        this.motionX = targetX * this.acceleration / d6;
        this.motionY = targetY * this.acceleration / d6;
        this.motionZ = targetZ * this.acceleration / d6;
    }
    
    public boolean usingFlareOfTarget(final Entity entity) {
        if (this.getCountOnUpdate() % 3 == 0) {
            final List list = this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this, entity.boundingBox.expand(15.0, 15.0, 15.0));
            for (int i = 0; i < list.size(); ++i) {
                if (list.get(i).getEntityData().getBoolean("FlareUsing")) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public void guidanceToTarget(final double targetPosX, final double targetPosY, final double targetPosZ) {
        this.guidanceToTarget(targetPosX, targetPosY, targetPosZ, 1.0f);
    }
    
    public void guidanceToTarget(final double targetPosX, final double targetPosY, final double targetPosZ, final float accelerationFactor) {
        final double tx = targetPosX - this.posX;
        final double ty = targetPosY - this.posY;
        final double tz = targetPosZ - this.posZ;
        final double d = MathHelper.sqrt_double(tx * tx + ty * ty + tz * tz);
        final double mx = tx * this.acceleration / d;
        final double my = ty * this.acceleration / d;
        final double mz = tz * this.acceleration / d;
        this.motionX = (this.motionX * 6.0 + mx) / 7.0;
        this.motionY = (this.motionY * 6.0 + my) / 7.0;
        this.motionZ = (this.motionZ * 6.0 + mz) / 7.0;
        final double a = (float)Math.atan2(this.motionZ, this.motionX);
        this.rotationYaw = (float)(a * 180.0 / 3.141592653589793) - 90.0f;
        final double r = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationPitch = -(float)(Math.atan2(this.motionY, r) * 180.0 / 3.141592653589793);
    }
    
    public boolean checkValid() {
        if (this.shootingEntity == null && this.shootingAircraft == null) {
            return false;
        }
        if (this.shootingAircraft == null || this.shootingAircraft.isDead) {}
        final Entity shooter = (this.shootingEntity != null) ? this.shootingEntity : this.shootingAircraft;
        final double x = this.posX - shooter.posX;
        final double z = this.posZ - shooter.posZ;
        return x * x + z * z < 3.38724E7 && this.posY > -10.0;
    }
    
    public float getGravity() {
        return (this.getInfo() != null) ? this.getInfo().gravity : 0.0f;
    }
    
    public float getGravityInWater() {
        return (this.getInfo() != null) ? this.getInfo().gravityInWater : 0.0f;
    }
    
    public void onUpdate() {
        if (this.worldObj.isRemote && this.countOnUpdate == 0) {
            final int tgtEttId = this.getTargetEntityID();
            if (tgtEttId > 0) {
                this.setTargetEntity(this.worldObj.getEntityByID(tgtEttId));
            }
        }
        if (!this.worldObj.isRemote && this.getCountOnUpdate() % 20 == 19 && this.targetEntity instanceof EntityPlayerMP) {
            MCH_Lib.DbgLog(this.worldObj, "MCH_EntityBaseBullet.onUpdate alert" + this.targetEntity + " / " + this.targetEntity.ridingEntity, new Object[0]);
            if (this.targetEntity.ridingEntity != null && !(this.targetEntity.ridingEntity instanceof MCH_EntityAircraft) && !(this.targetEntity.ridingEntity instanceof MCH_EntitySeat)) {
                W_WorldFunc.MOD_playSoundAtEntity(this.targetEntity, "alert", 2.0f, 1.0f);
            }
        }
        this.prevMotionX = this.motionX;
        this.prevMotionY = this.motionY;
        this.prevMotionZ = this.motionZ;
        ++this.countOnUpdate;
        if (this.countOnUpdate > 10000000) {
            this.clearCountOnUpdate();
        }
        this.prevPosX2 = this.prevPosX;
        this.prevPosY2 = this.prevPosY;
        this.prevPosZ2 = this.prevPosZ;
        super.onUpdate();
        if ((this.prevMotionX != this.motionX || this.prevMotionY != this.motionY || this.prevMotionZ != this.motionZ) && this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ > 0.1) {
            final double a = (float)Math.atan2(this.motionZ, this.motionX);
            this.rotationYaw = (float)(a * 180.0 / 3.141592653589793) - 90.0f;
            final double r = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.rotationPitch = -(float)(Math.atan2(this.motionY, r) * 180.0 / 3.141592653589793);
        }
        if (this.getInfo() == null) {
            if (this.countOnUpdate >= 2) {
                MCH_Lib.Log((Entity)this, "##### MCH_EntityBaseBullet onUpdate() Weapon info null %d, %s, Name=%s", new Object[] { W_Entity.getEntityId(this), this.getEntityName(), this.getName() });
                this.setDead();
                return;
            }
            this.setName(this.getName());
            if (this.getInfo() == null) {
                return;
            }
        }
        if (this.getInfo().bound <= 0.0f && this.onGround) {
            this.motionX *= 0.9;
            this.motionZ *= 0.9;
        }
        if (this.worldObj.isRemote && this.isBomblet < 0) {
            this.isBomblet = this.getBomblet();
        }
        if (!this.worldObj.isRemote) {
            if ((int)this.posY <= 255 && !this.worldObj.blockExists((int)this.posX, (int)this.posY, (int)this.posZ)) {
                if (this.getInfo().delayFuse <= 0) {
                    this.setDead();
                    return;
                }
                if (this.delayFuse == 0) {
                    this.delayFuse = this.getInfo().delayFuse;
                }
            }
            if (this.delayFuse > 0) {
                --this.delayFuse;
                if (this.delayFuse == 0) {
                    this.onUpdateTimeout();
                    this.setDead();
                    return;
                }
            }
            if (!this.checkValid()) {
                this.setDead();
                return;
            }
            if (this.getInfo().timeFuse > 0 && this.getCountOnUpdate() > this.getInfo().timeFuse) {
                this.onUpdateTimeout();
                this.setDead();
                return;
            }
            if (this.getInfo().explosionAltitude > 0 && MCH_Lib.getBlockIdY((Entity)this, 3, -this.getInfo().explosionAltitude) != 0) {
                final MovingObjectPosition mop = new MovingObjectPosition((int)this.posX, (int)this.posY, (int)this.posZ, 0, Vec3.createVectorHelper(this.posX, this.posY, this.posZ));
                this.onImpact(mop, 1.0f);
            }
        }
        if (!this.isInWater()) {
            this.motionY += this.getGravity();
        }
        else {
            this.motionY += this.getGravityInWater();
        }
        if (!this.isDead) {
            this.onUpdateCollided();
        }
        this.posX += this.motionX * this.accelerationFactor;
        this.posY += this.motionY * this.accelerationFactor;
        this.posZ += this.motionZ * this.accelerationFactor;
        if (this.worldObj.isRemote) {
            this.updateSplash();
        }
        if (this.isInWater()) {
            final float f3 = 0.25f;
            this.worldObj.spawnParticle("bubble", this.posX - this.motionX * f3, this.posY - this.motionY * f3, this.posZ - this.motionZ * f3, this.motionX, this.motionY, this.motionZ);
        }
        this.setPosition(this.posX, this.posY, this.posZ);
    }
    
    public void updateSplash() {
        if (this.getInfo() == null) {
            return;
        }
        if (this.getInfo().power <= 0) {
            return;
        }
        if (!W_WorldFunc.isBlockWater(this.worldObj, (int)(this.prevPosX + 0.5), (int)(this.prevPosY + 0.5), (int)(this.prevPosZ + 0.5)) && W_WorldFunc.isBlockWater(this.worldObj, (int)(this.posX + 0.5), (int)(this.posY + 0.5), (int)(this.posZ + 0.5))) {
            double x = this.posX - this.prevPosX;
            double y = this.posY - this.prevPosY;
            double z = this.posZ - this.prevPosZ;
            final double d = Math.sqrt(x * x + y * y + z * z);
            if (d <= 0.15) {
                return;
            }
            x /= d;
            y /= d;
            z /= d;
            double px = this.prevPosX;
            double py = this.prevPosY;
            double pz = this.prevPosZ;
            for (int i = 0; i <= d; ++i) {
                px += x;
                py += y;
                pz += z;
                if (W_WorldFunc.isBlockWater(this.worldObj, (int)(px + 0.5), (int)(py + 0.5), (int)(pz + 0.5))) {
                    float pwr = (this.getInfo().power < 20) ? ((float)this.getInfo().power) : 20.0f;
                    final int n = this.rand.nextInt(1 + (int)pwr / 3) + (int)pwr / 2 + 1;
                    pwr *= 0.03f;
                    for (int j = 0; j < n; ++j) {
                        final MCH_ParticleParam prm = new MCH_ParticleParam(this.worldObj, "splash", px, py + 0.5, pz, pwr * (this.rand.nextDouble() - 0.5) * 0.3, pwr * (this.rand.nextDouble() * 0.5 + 0.5) * 1.8, pwr * (this.rand.nextDouble() - 0.5) * 0.3, pwr * 5.0f);
                        MCH_ParticlesUtil.spawnParticle(prm);
                    }
                    break;
                }
            }
        }
    }
    
    public void onUpdateTimeout() {
        if (this.isInWater()) {
            if (this.explosionPowerInWater > 0) {
                this.newExplosion(this.posX, this.posY, this.posZ, (float)this.explosionPowerInWater, (float)this.explosionPowerInWater, true);
            }
        }
        else if (this.explosionPower > 0) {
            this.newExplosion(this.posX, this.posY, this.posZ, (float)this.explosionPower, (float)this.getInfo().explosionBlock, false);
        }
        else if (this.explosionPower < 0) {
            this.playExplosionSound();
        }
    }
    
    public void onUpdateBomblet() {
        if (!this.worldObj.isRemote && this.sprinkleTime > 0 && !this.isDead) {
            --this.sprinkleTime;
            if (this.sprinkleTime == 0) {
                for (int i = 0; i < this.getInfo().bomblet; ++i) {
                    this.sprinkleBomblet();
                }
                this.setDead();
            }
        }
    }
    
    public void boundBullet(final int sideHit) {
        switch (sideHit) {
            case 0: {
                if (this.motionY > 0.0) {
                    this.motionY = -this.motionY * this.getInfo().bound;
                    break;
                }
                break;
            }
            case 1: {
                if (this.motionY < 0.0) {
                    this.motionY = -this.motionY * this.getInfo().bound;
                    break;
                }
                break;
            }
            case 2: {
                if (this.motionZ > 0.0) {
                    this.motionZ = -this.motionZ * this.getInfo().bound;
                    break;
                }
                this.posZ += this.motionZ;
                break;
            }
            case 3: {
                if (this.motionZ < 0.0) {
                    this.motionZ = -this.motionZ * this.getInfo().bound;
                    break;
                }
                this.posZ += this.motionZ;
                break;
            }
            case 4: {
                if (this.motionX > 0.0) {
                    this.motionX = -this.motionX * this.getInfo().bound;
                    break;
                }
                this.posX += this.motionX;
                break;
            }
            case 5: {
                if (this.motionX < 0.0) {
                    this.motionX = -this.motionX * this.getInfo().bound;
                    break;
                }
                this.posX += this.motionX;
                break;
            }
        }
        if (this.getInfo().bound <= 0.0f) {
            this.motionX *= 0.25;
            this.motionY *= 0.25;
            this.motionZ *= 0.25;
        }
    }
    
    protected void onUpdateCollided() {
        final float damageFator = 1.0f;
        final double mx = this.motionX * this.accelerationFactor;
        final double my = this.motionY * this.accelerationFactor;
        final double mz = this.motionZ * this.accelerationFactor;
        MovingObjectPosition m = null;
        for (int i = 0; i < 5; ++i) {
            final Vec3 vec3 = W_WorldFunc.getWorldVec3(this.worldObj, this.posX, this.posY, this.posZ);
            final Vec3 vec4 = W_WorldFunc.getWorldVec3(this.worldObj, this.posX + mx, this.posY + my, this.posZ + mz);
            m = W_WorldFunc.clip(this.worldObj, vec3, vec4);
            boolean continueClip = false;
            if (this.shootingEntity != null && W_MovingObjectPosition.isHitTypeTile(m)) {
                final Block block = W_WorldFunc.getBlock(this.worldObj, m.blockX, m.blockY, m.blockZ);
                final MCH_Config config = MCH_MOD.config;
                if (MCH_Config.bulletBreakableBlocks.contains(block)) {
                    W_WorldFunc.destroyBlock(this.worldObj, m.blockX, m.blockY, m.blockZ, true);
                    continueClip = true;
                }
            }
            if (!continueClip) {
                break;
            }
        }
        final Vec3 vec3 = W_WorldFunc.getWorldVec3(this.worldObj, this.posX, this.posY, this.posZ);
        Vec3 vec4 = W_WorldFunc.getWorldVec3(this.worldObj, this.posX + mx, this.posY + my, this.posZ + mz);
        if (this.getInfo().delayFuse > 0) {
            if (m != null) {
                this.boundBullet(m.sideHit);
                if (this.delayFuse == 0) {
                    this.delayFuse = this.getInfo().delayFuse;
                }
            }
            return;
        }
        if (m != null) {
            vec4 = W_WorldFunc.getWorldVec3(this.worldObj, m.hitVec.xCoord, m.hitVec.yCoord, m.hitVec.zCoord);
        }
        Entity entity = null;
        final List list = this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this, this.boundingBox.addCoord(mx, my, mz).expand(21.0, 21.0, 21.0));
        double d0 = 0.0;
        for (int j = 0; j < list.size(); ++j) {
            final Entity entity2 = list.get(j);
            if (this.canBeCollidedEntity(entity2)) {
                final float f = 0.3f;
                final AxisAlignedBB axisalignedbb = entity2.boundingBox.expand((double)f, (double)f, (double)f);
                final MovingObjectPosition m2 = axisalignedbb.calculateIntercept(vec3, vec4);
                if (m2 != null) {
                    final double d2 = vec3.distanceTo(m2.hitVec);
                    if (d2 < d0 || d0 == 0.0) {
                        entity = entity2;
                        d0 = d2;
                    }
                }
            }
        }
        if (entity != null) {
            m = new MovingObjectPosition(entity);
        }
        if (m != null) {
            this.onImpact(m, damageFator);
        }
    }
    
    public boolean canBeCollidedEntity(final Entity entity) {
        if (entity instanceof MCH_EntityChain) {
            return false;
        }
        if (!entity.canBeCollidedWith()) {
            return false;
        }
        if (entity instanceof MCH_EntityBaseBullet) {
            if (this.worldObj.isRemote) {
                return false;
            }
            final MCH_EntityBaseBullet blt = (MCH_EntityBaseBullet)entity;
            if (W_Entity.isEqual(blt.shootingAircraft, this.shootingAircraft)) {
                return false;
            }
            if (W_Entity.isEqual(blt.shootingEntity, this.shootingEntity)) {
                return false;
            }
        }
        if (entity instanceof MCH_EntitySeat) {
            return false;
        }
        if (entity instanceof MCH_EntityHitBox) {
            return false;
        }
        if (W_Entity.isEqual(entity, this.shootingEntity)) {
            return false;
        }
        if (this.shootingAircraft instanceof MCH_EntityAircraft) {
            if (W_Entity.isEqual(entity, this.shootingAircraft)) {
                return false;
            }
            if (((MCH_EntityAircraft)this.shootingAircraft).isMountedEntity(entity)) {
                return false;
            }
        }
        final MCH_Config config = MCH_MOD.config;
        for (final String s : MCH_Config.IgnoreBulletHitList) {
            if (entity.getClass().getName().toLowerCase().indexOf(s.toLowerCase()) >= 0) {
                return false;
            }
        }
        return true;
    }
    
    public void notifyHitBullet() {
        if (this.shootingAircraft instanceof MCH_EntityAircraft && W_EntityPlayer.isPlayer(this.shootingEntity)) {
            MCH_PacketNotifyHitBullet.send((MCH_EntityAircraft)this.shootingAircraft, (EntityPlayer)this.shootingEntity);
        }
        if (W_EntityPlayer.isPlayer(this.shootingEntity)) {
            MCH_PacketNotifyHitBullet.send((MCH_EntityAircraft)null, (EntityPlayer)this.shootingEntity);
        }
    }
    
    protected void onImpact(final MovingObjectPosition m, final float damageFactor) {
        if (!this.worldObj.isRemote) {
            if (m.entityHit != null) {
                this.onImpactEntity(m.entityHit, damageFactor);
                this.piercing = 0;
            }
            final float expPower = this.explosionPower * damageFactor;
            final float expPowerInWater = this.explosionPowerInWater * damageFactor;
            final double dx = 0.0;
            final double dy = 0.0;
            final double dz = 0.0;
            if (this.piercing > 0) {
                --this.piercing;
                if (expPower > 0.0f) {
                    this.newExplosion(m.hitVec.xCoord + dx, m.hitVec.yCoord + dy, m.hitVec.zCoord + dz, 1.0f, 1.0f, false);
                }
            }
            else {
                if (expPowerInWater == 0.0f) {
                    if (this.getInfo().isFAE) {
                        this.newFAExplosion(this.posX, this.posY, this.posZ, expPower, (float)this.getInfo().explosionBlock);
                    }
                    else if (expPower > 0.0f) {
                        this.newExplosion(m.hitVec.xCoord + dx, m.hitVec.yCoord + dy, m.hitVec.zCoord + dz, expPower, (float)this.getInfo().explosionBlock, false);
                    }
                    else if (expPower < 0.0f) {
                        this.playExplosionSound();
                    }
                }
                else if (m.entityHit != null) {
                    if (this.isInWater()) {
                        this.newExplosion(m.hitVec.xCoord + dx, m.hitVec.yCoord + dy, m.hitVec.zCoord + dz, expPowerInWater, expPowerInWater, true);
                    }
                    else {
                        this.newExplosion(m.hitVec.xCoord + dx, m.hitVec.yCoord + dy, m.hitVec.zCoord + dz, expPower, (float)this.getInfo().explosionBlock, false);
                    }
                }
                else if (this.isInWater() || MCH_Lib.isBlockInWater(this.worldObj, m.blockX, m.blockY, m.blockZ)) {
                    this.newExplosion(m.blockX, m.blockY, m.blockZ, expPowerInWater, expPowerInWater, true);
                }
                else if (expPower > 0.0f) {
                    this.newExplosion(m.hitVec.xCoord + dx, m.hitVec.yCoord + dy, m.hitVec.zCoord + dz, expPower, (float)this.getInfo().explosionBlock, false);
                }
                else if (expPower < 0.0f) {
                    this.playExplosionSound();
                }
                this.setDead();
            }
        }
        else if (this.getInfo() != null && (this.getInfo().explosion == 0 || this.getInfo().modeNum >= 2) && W_MovingObjectPosition.isHitTypeTile(m)) {
            final float p = (float)this.getInfo().power;
            for (int i = 0; i < p / 3.0f; ++i) {
                MCH_ParticlesUtil.spawnParticleTileCrack(this.worldObj, m.blockX, m.blockY, m.blockZ, m.hitVec.xCoord + (this.rand.nextFloat() - 0.5) * p / 10.0, m.hitVec.yCoord + 0.1, m.hitVec.zCoord + (this.rand.nextFloat() - 0.5) * p / 10.0, -this.motionX * p / 2.0, (double)(p / 2.0f), -this.motionZ * p / 2.0);
            }
        }
    }
    
    public void onImpactEntity(final Entity entity, final float damageFactor) {
        if (!entity.isDead) {
            MCH_Lib.DbgLog(this.worldObj, "MCH_EntityBaseBullet.onImpactEntity:Damage=%d:" + entity.getClass(), new Object[] { this.getPower() });
            MCH_Lib.applyEntityHurtResistantTimeConfig(entity);
            final DamageSource ds = DamageSource.causeThrownDamage((Entity)this, this.shootingEntity);
            final MCH_Config config = MCH_MOD.config;
            float damage = MCH_Config.applyDamageVsEntity(entity, ds, this.getPower() * damageFactor);
            damage *= ((this.getInfo() != null) ? this.getInfo().getDamageFactor(entity) : 1.0f);
            entity.attackEntityFrom(ds, damage);
            if (this instanceof MCH_EntityBullet && entity instanceof EntityVillager && this.shootingEntity != null && this.shootingEntity.ridingEntity instanceof MCH_EntitySeat) {
                MCH_Achievement.addStat(this.shootingEntity, MCH_Achievement.aintWarHell, 1);
            }
            if (entity.isDead) {}
        }
        this.notifyHitBullet();
    }
    
    public void newFAExplosion(final double x, final double y, final double z, final float exp, final float expBlock) {
        final MCH_Explosion.ExplosionResult result = MCH_Explosion.newExplosion(this.worldObj, (Entity)this, this.shootingEntity, x, y, z, exp, expBlock, true, true, this.getInfo().flaming, false, 15);
        if (result != null && result.hitEntity) {
            this.notifyHitBullet();
        }
    }
    
    public void newExplosion(final double x, final double y, final double z, final float exp, final float expBlock, final boolean inWater) {
        MCH_Explosion.ExplosionResult result;
        if (!inWater) {
            result = MCH_Explosion.newExplosion(this.worldObj, (Entity)this, this.shootingEntity, x, y, z, exp, expBlock, this.isBomblet != 1 || this.rand.nextInt(3) == 0, true, this.getInfo().flaming, true, 0, (this.getInfo() != null) ? this.getInfo().damageFactor : null);
        }
        else {
            result = MCH_Explosion.newExplosionInWater(this.worldObj, (Entity)this, this.shootingEntity, x, y, z, exp, expBlock, this.isBomblet != 1 || this.rand.nextInt(3) == 0, true, this.getInfo().flaming, true, 0, (this.getInfo() != null) ? this.getInfo().damageFactor : null);
        }
        if (result != null && result.hitEntity) {
            this.notifyHitBullet();
        }
    }
    
    public void playExplosionSound() {
        MCH_Explosion.playExplosionSound(this.worldObj, this.posX, this.posY, this.posZ);
    }
    
    public void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
        par1NBTTagCompound.setTag("direction", (NBTBase)this.newDoubleNBTList(new double[] { this.motionX, this.motionY, this.motionZ }));
        par1NBTTagCompound.setString("WeaponName", this.getName());
    }
    
    public void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
        this.setDead();
    }
    
    public boolean canBeCollidedWith() {
        return true;
    }
    
    public float getCollisionBorderSize() {
        return 1.0f;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource ds, final float par2) {
        if (this.isEntityInvulnerable()) {
            return false;
        }
        if (!this.worldObj.isRemote && par2 > 0.0f && ds.getDamageType().equalsIgnoreCase("thrown")) {
            this.setBeenAttacked();
            final MovingObjectPosition m = new MovingObjectPosition((int)(this.posX + 0.5), (int)(this.posY + 0.5), (int)(this.posZ + 0.5), 0, Vec3.createVectorHelper(this.posX + 0.5, this.posY + 0.5, this.posZ + 0.5));
            this.onImpact(m, 1.0f);
            return true;
        }
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public float getShadowSize() {
        return 0.0f;
    }
    
    public float getBrightness(final float par1) {
        return 1.0f;
    }
    
    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender(final float par1) {
        return 15728880;
    }
    
    public int getPower() {
        return this.power;
    }
    
    public void setPower(final int power) {
        this.power = power;
    }
}
