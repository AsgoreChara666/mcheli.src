//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.aircraft;

import cpw.mods.fml.common.registry.*;
import mcheli.flare.*;
import mcheli.chain.*;
import mcheli.uav.*;
import net.minecraft.entity.player.*;
import mcheli.command.*;
import cpw.mods.fml.relauncher.*;
import io.netty.buffer.*;
import net.minecraft.nbt.*;
import mcheli.multiplay.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;
import net.minecraft.entity.item.*;
import net.minecraft.potion.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;
import net.minecraft.crash.*;
import net.minecraft.block.*;
import mcheli.particles.*;
import net.minecraft.init.*;
import mcheli.*;
import mcheli.parachute.*;
import mcheli.tool.*;
import mcheli.mob.*;
import java.util.*;
import mcheli.wrapper.*;
import mcheli.weapon.*;
import net.minecraft.item.*;

public abstract class MCH_EntityAircraft extends W_EntityContainer implements MCH_IEntityLockChecker, MCH_IEntityCanRideAircraft, IEntityAdditionalSpawnData
{
    private static final int DATAWT_ID_DAMAGE = 19;
    private static final int DATAWT_ID_TYPE = 20;
    private static final int DATAWT_ID_TEXTURE_NAME = 21;
    private static final int DATAWT_ID_UAV_STATION = 22;
    private static final int DATAWT_ID_STATUS = 23;
    private static final int CMN_ID_FLARE = 0;
    private static final int CMN_ID_FREE_LOOK = 1;
    private static final int CMN_ID_RELOADING = 2;
    private static final int CMN_ID_INGINITY_AMMO = 3;
    private static final int CMN_ID_INGINITY_FUEL = 4;
    private static final int CMN_ID_RAPELLING = 5;
    private static final int CMN_ID_SEARCHLIGHT = 6;
    private static final int CMN_ID_CNTRL_LEFT = 7;
    private static final int CMN_ID_CNTRL_RIGHT = 8;
    private static final int CMN_ID_CNTRL_UP = 9;
    private static final int CMN_ID_CNTRL_DOWN = 10;
    private static final int CMN_ID_CNTRL_BRAKE = 11;
    private static final int CMN_ID_GUNNER = 12;
    private static final int DATAWT_ID_USE_WEAPON = 24;
    private static final int DATAWT_ID_FUEL = 25;
    private static final int DATAWT_ID_ROT_ROLL = 26;
    private static final int DATAWT_ID_COMMAND = 27;
    private static final int DATAWT_ID_THROTTLE = 29;
    protected static final int DATAWT_ID_FOLD_STAT = 30;
    protected static final int DATAWT_ID_PART_STAT = 31;
    protected static final int PART_ID_CANOPY = 0;
    protected static final int PART_ID_NOZZLE = 1;
    protected static final int PART_ID_LANDINGGEAR = 2;
    protected static final int PART_ID_WING = 3;
    protected static final int PART_ID_HATCH = 4;
    public static final byte LIMIT_GROUND_PITCH = 40;
    public static final byte LIMIT_GROUND_ROLL = 40;
    public boolean isRequestedSyncStatus;
    private MCH_AircraftInfo acInfo;
    private int commonStatus;
    private Entity[] partEntities;
    private MCH_EntityHitBox pilotSeat;
    private MCH_EntitySeat[] seats;
    private MCH_SeatInfo[] seatsInfo;
    private String commonUniqueId;
    private int seatSearchCount;
    protected double velocityX;
    protected double velocityY;
    protected double velocityZ;
    public boolean keepOnRideRotation;
    protected int aircraftPosRotInc;
    protected double aircraftX;
    protected double aircraftY;
    protected double aircraftZ;
    protected double aircraftYaw;
    protected double aircraftPitch;
    public boolean aircraftRollRev;
    public boolean aircraftRotChanged;
    public float rotationRoll;
    public float prevRotationRoll;
    private double currentThrottle;
    private double prevCurrentThrottle;
    public double currentSpeed;
    public int currentFuel;
    public float throttleBack;
    public double beforeHoverThrottle;
    public int waitMountEntity;
    public boolean throttleUp;
    public boolean throttleDown;
    public boolean moveLeft;
    public boolean moveRight;
    public MCH_LowPassFilterFloat lowPassPartialTicks;
    private MCH_Radar entityRadar;
    private int radarRotate;
    private MCH_Flare flareDv;
    private int currentFlareIndex;
    protected MCH_WeaponSet[] weapons;
    protected int[] currentWeaponID;
    public float lastRiderYaw;
    public float prevLastRiderYaw;
    public float lastRiderPitch;
    public float prevLastRiderPitch;
    protected MCH_WeaponSet dummyWeapon;
    protected int useWeaponStat;
    protected int hitStatus;
    protected final MCH_SoundUpdater soundUpdater;
    protected Entity lastRiddenByEntity;
    protected Entity lastRidingEntity;
    public List<UnmountReserve> listUnmountReserve;
    private int countOnUpdate;
    private MCH_EntityChain towChainEntity;
    private MCH_EntityChain towedChainEntity;
    public MCH_Camera camera;
    private int cameraId;
    protected boolean isGunnerMode;
    protected boolean isGunnerModeOtherSeat;
    private boolean isHoveringMode;
    public static final int CAMERA_PITCH_MIN = -30;
    public static final int CAMERA_PITCH_MAX = 70;
    private MCH_EntityTvMissile TVmissile;
    protected boolean isGunnerFreeLookMode;
    public final MCH_MissileDetector missileDetector;
    public int serverNoMoveCount;
    public int repairCount;
    public int beforeDamageTaken;
    public int timeSinceHit;
    private int despawnCount;
    public float rotDestroyedYaw;
    public float rotDestroyedPitch;
    public float rotDestroyedRoll;
    public int damageSinceDestroyed;
    public boolean isFirstDamageSmoke;
    public Vec3[] prevDamageSmokePos;
    private MCH_EntityUavStation uavStation;
    public boolean cs_dismountAll;
    public boolean cs_heliAutoThrottleDown;
    public boolean cs_planeAutoThrottleDown;
    public boolean cs_tankAutoThrottleDown;
    public MCH_Parts partHatch;
    public MCH_Parts partCanopy;
    public MCH_Parts partLandingGear;
    public double prevRidingEntityPosX;
    public double prevRidingEntityPosY;
    public double prevRidingEntityPosZ;
    public boolean canRideRackStatus;
    private int modeSwitchCooldown;
    public MCH_BoundingBox[] extraBoundingBox;
    public float lastBBDamageFactor;
    private final MCH_AircraftInventory inventory;
    private double fuelConsumption;
    private int fuelSuppliedCount;
    private int supplyAmmoWait;
    private boolean beforeSupplyAmmo;
    public WeaponBay[] weaponBays;
    public float[] rotPartRotation;
    public float[] prevRotPartRotation;
    public float[] rotCrawlerTrack;
    public float[] prevRotCrawlerTrack;
    public float[] throttleCrawlerTrack;
    public float[] rotTrackRoller;
    public float[] prevRotTrackRoller;
    public float rotWheel;
    public float prevRotWheel;
    public float rotYawWheel;
    public float prevRotYawWheel;
    private boolean isParachuting;
    public float ropesLength;
    private MCH_Queue<Vec3> prevPosition;
    private int tickRepelling;
    private int lastUsedRopeIndex;
    private boolean dismountedUserCtrl;
    public float lastSearchLightYaw;
    public float lastSearchLightPitch;
    public float rotLightHatch;
    public float prevRotLightHatch;
    public int recoilCount;
    public float recoilYaw;
    public float recoilValue;
    public int brightnessHigh;
    public int brightnessLow;
    public final HashMap<Entity, Integer> noCollisionEntities;
    private double lastCalcLandInDistanceCount;
    private double lastLandInDistance;
    public float thirdPersonDist;
    public Entity lastAttackedEntity;
    private static final MCH_EntitySeat[] seatsDummy;
    private boolean switchSeat;
    
    public MCH_EntityAircraft(final World world) {
        super(world);
        this.throttleBack = 0.0f;
        this.waitMountEntity = 0;
        this.throttleUp = false;
        this.throttleDown = false;
        this.moveLeft = false;
        this.moveRight = false;
        this.listUnmountReserve = new ArrayList<UnmountReserve>();
        this.isGunnerMode = false;
        this.isGunnerModeOtherSeat = false;
        this.isHoveringMode = false;
        this.isGunnerFreeLookMode = false;
        this.serverNoMoveCount = 0;
        this.isFirstDamageSmoke = true;
        this.prevDamageSmokePos = new Vec3[0];
        this.rotCrawlerTrack = new float[2];
        this.prevRotCrawlerTrack = new float[2];
        this.throttleCrawlerTrack = new float[2];
        this.rotTrackRoller = new float[2];
        this.prevRotTrackRoller = new float[2];
        this.rotWheel = 0.0f;
        this.prevRotWheel = 0.0f;
        this.rotYawWheel = 0.0f;
        this.prevRotYawWheel = 0.0f;
        this.ropesLength = 0.0f;
        this.rotLightHatch = 0.0f;
        this.prevRotLightHatch = 0.0f;
        this.recoilCount = 0;
        this.recoilYaw = 0.0f;
        this.recoilValue = 0.0f;
        this.brightnessHigh = 240;
        this.brightnessLow = 240;
        this.noCollisionEntities = new HashMap<Entity, Integer>();
        this.thirdPersonDist = 4.0f;
        this.lastAttackedEntity = null;
        this.switchSeat = false;
        MCH_Lib.DbgLog(world, "MCH_EntityAircraft : " + this.toString(), new Object[0]);
        this.isRequestedSyncStatus = false;
        this.setAcInfo(null);
        this.dropContentsWhenDead = false;
        this.ignoreFrustumCheck = true;
        this.flareDv = new MCH_Flare(world, this);
        this.currentFlareIndex = 0;
        this.entityRadar = new MCH_Radar(world);
        this.radarRotate = 0;
        this.currentWeaponID = new int[0];
        this.aircraftPosRotInc = 0;
        this.aircraftX = 0.0;
        this.aircraftY = 0.0;
        this.aircraftZ = 0.0;
        this.aircraftYaw = 0.0;
        this.aircraftPitch = 0.0;
        this.setCurrentThrottle(this.currentSpeed = 0.0);
        this.currentFuel = 0;
        this.cs_dismountAll = false;
        this.cs_heliAutoThrottleDown = true;
        this.cs_planeAutoThrottleDown = false;
        final double n = 2.0;
        final MCH_Config config = MCH_MOD.config;
        this.renderDistanceWeight = n * MCH_Config.RenderDistanceWeight.prmDouble;
        this.setCommonUniqueId("");
        this.seatSearchCount = 0;
        this.seatsInfo = null;
        this.seats = new MCH_EntitySeat[0];
        this.pilotSeat = new MCH_EntityHitBox(world, this, 1.0f, 1.0f);
        this.pilotSeat.parent = this;
        this.partEntities = new Entity[] { this.pilotSeat };
        this.setTextureName("");
        this.camera = new MCH_Camera(world, this, this.posX, this.posY, this.posZ);
        this.setCameraId(0);
        this.lastRiddenByEntity = null;
        this.lastRidingEntity = null;
        this.soundUpdater = MCH_MOD.proxy.CreateSoundUpdater(this);
        this.countOnUpdate = 0;
        this.setTowChainEntity(null);
        this.dummyWeapon = new MCH_WeaponSet(new MCH_WeaponDummy(this.worldObj, Vec3.createVectorHelper(0.0, 0.0, 0.0), 0.0f, 0.0f, "", null));
        this.useWeaponStat = 0;
        this.hitStatus = 0;
        this.repairCount = 0;
        this.beforeDamageTaken = 0;
        this.setDespawnCount(this.timeSinceHit = 0);
        this.missileDetector = new MCH_MissileDetector(this, world);
        this.uavStation = null;
        this.modeSwitchCooldown = 0;
        this.partHatch = null;
        this.partCanopy = null;
        this.partLandingGear = null;
        this.weaponBays = new WeaponBay[0];
        this.rotPartRotation = new float[0];
        this.prevRotPartRotation = new float[0];
        this.lastRiderYaw = 0.0f;
        this.prevLastRiderYaw = 0.0f;
        this.lastRiderPitch = 0.0f;
        this.prevLastRiderPitch = 0.0f;
        this.rotationRoll = 0.0f;
        this.prevRotationRoll = 0.0f;
        this.lowPassPartialTicks = new MCH_LowPassFilterFloat(10);
        this.extraBoundingBox = new MCH_BoundingBox[0];
        W_Reflection.setBoundingBox(this, (AxisAlignedBB)new MCH_AircraftBoundingBox(this));
        this.lastBBDamageFactor = 1.0f;
        this.inventory = new MCH_AircraftInventory(this);
        this.fuelConsumption = 0.0;
        this.fuelSuppliedCount = 0;
        this.canRideRackStatus = false;
        this.isParachuting = false;
        this.prevPosition = new MCH_Queue<Vec3>(10, Vec3.createVectorHelper(0.0, 0.0, 0.0));
        final float n2 = 0.0f;
        this.lastSearchLightPitch = n2;
        this.lastSearchLightYaw = n2;
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.getDataWatcher().addObject(20, (Object)"");
        this.getDataWatcher().addObject(19, (Object)new Integer(0));
        this.getDataWatcher().addObject(23, (Object)new Integer(0));
        this.getDataWatcher().addObject(24, (Object)new Integer(0));
        this.getDataWatcher().addObject(25, (Object)new Integer(0));
        this.getDataWatcher().addObject(21, (Object)"");
        this.getDataWatcher().addObject(22, (Object)new Integer(0));
        this.getDataWatcher().addObject(26, (Object)new Short((short)0));
        this.getDataWatcher().addObject(27, (Object)new String(""));
        this.getDataWatcher().addObject(29, (Object)new Integer(0));
        this.getDataWatcher().addObject(31, (Object)new Integer(0));
        if (!this.worldObj.isRemote) {
            final int bit = 3;
            final MCH_Config config = MCH_MOD.config;
            this.setCommonStatus(bit, MCH_Config.InfinityAmmo.prmBool);
            final int bit2 = 4;
            final MCH_Config config2 = MCH_MOD.config;
            this.setCommonStatus(bit2, MCH_Config.InfinityFuel.prmBool);
            this.setGunnerStatus(true);
        }
        this.getEntityData().setString("EntityType", this.getEntityType());
    }
    
    public float getServerRoll() {
        return this.getDataWatcher().getWatchableObjectShort(26);
    }
    
    public float getRotYaw() {
        return this.rotationYaw;
    }
    
    public float getRotPitch() {
        return this.rotationPitch;
    }
    
    public float getRotRoll() {
        return this.rotationRoll;
    }
    
    public void setRotYaw(final float f) {
        this.rotationYaw = f;
    }
    
    public void setRotPitch(final float f) {
        this.rotationPitch = f;
    }
    
    public void setRotPitch(final float f, final String msg) {
        this.setRotPitch(f);
    }
    
    public void setRotRoll(final float f) {
        this.rotationRoll = f;
    }
    
    public void applyOnGroundPitch(final float factor) {
        if (this.getAcInfo() != null) {
            final float ogp = this.getAcInfo().onGroundPitch;
            float pitch = this.getRotPitch();
            pitch -= ogp;
            pitch *= factor;
            pitch += ogp;
            this.setRotPitch(pitch, "applyOnGroundPitch");
        }
        this.setRotRoll(this.getRotRoll() * factor);
    }
    
    public float calcRotYaw(final float partialTicks) {
        return this.prevRotationYaw + (this.getRotYaw() - this.prevRotationYaw) * partialTicks;
    }
    
    public float calcRotPitch(final float partialTicks) {
        return this.prevRotationPitch + (this.getRotPitch() - this.prevRotationPitch) * partialTicks;
    }
    
    public float calcRotRoll(final float partialTicks) {
        return this.prevRotationRoll + (this.getRotRoll() - this.prevRotationRoll) * partialTicks;
    }
    
    protected void setRotation(final float y, final float p) {
        this.setRotYaw(y % 360.0f);
        this.setRotPitch(p % 360.0f);
    }
    
    public boolean isInfinityAmmo(final Entity player) {
        return this.isCreative(player) || this.getCommonStatus(3);
    }
    
    public boolean isInfinityFuel(final Entity player, final boolean checkOtherSeet) {
        if (this.isCreative(player) || this.getCommonStatus(4)) {
            return true;
        }
        if (checkOtherSeet) {
            for (final MCH_EntitySeat seat : this.getSeats()) {
                if (seat != null && this.isCreative(seat.riddenByEntity)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public void setCommand(final String s, final EntityPlayer player) {
        if (!this.worldObj.isRemote && MCH_Command.canUseCommand((Entity)player)) {
            this.setCommandForce(s);
        }
    }
    
    public void setCommandForce(final String s) {
        if (!this.worldObj.isRemote) {
            this.getDataWatcher().updateObject(27, (Object)s);
        }
    }
    
    public String getCommand() {
        return this.getDataWatcher().getWatchableObjectString(27);
    }
    
    public String getKindName() {
        return "";
    }
    
    public String getEntityType() {
        return "";
    }
    
    public void setTypeName(final String s) {
        final String beforeType = this.getTypeName();
        if (s != null && !s.isEmpty() && s.compareTo(beforeType) != 0) {
            this.getDataWatcher().updateObject(20, (Object)String.valueOf(s));
            this.changeType(s);
            this.initRotationYaw(this.getRotYaw());
        }
    }
    
    public String getTypeName() {
        return this.getDataWatcher().getWatchableObjectString(20);
    }
    
    public abstract void changeType(final String p0);
    
    public boolean isTargetDrone() {
        return this.getAcInfo() != null && this.getAcInfo().isTargetDrone;
    }
    
    public boolean isUAV() {
        return this.getAcInfo() != null && this.getAcInfo().isUAV;
    }
    
    public boolean isSmallUAV() {
        return this.getAcInfo() != null && this.getAcInfo().isSmallUAV;
    }
    
    public boolean isAlwaysCameraView() {
        return this.getAcInfo() != null && this.getAcInfo().alwaysCameraView;
    }
    
    public void setUavStation(final MCH_EntityUavStation uavSt) {
        this.uavStation = uavSt;
        if (!this.worldObj.isRemote) {
            if (uavSt != null) {
                this.getDataWatcher().updateObject(22, (Object)W_Entity.getEntityId(uavSt));
            }
            else {
                this.getDataWatcher().updateObject(22, (Object)0);
            }
        }
    }
    
    public float getStealth() {
        return (this.getAcInfo() != null) ? this.getAcInfo().stealth : 0.0f;
    }
    
    public MCH_AircraftInventory getGuiInventory() {
        return this.inventory;
    }
    
    public void openGui(final EntityPlayer player) {
        if (!this.worldObj.isRemote) {
            player.openGui((Object)MCH_MOD.instance, 1, this.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ);
        }
    }
    
    public MCH_EntityUavStation getUavStation() {
        return this.isUAV() ? this.uavStation : null;
    }
    
    public static MCH_EntityAircraft getAircraft_RiddenOrControl(final Entity rider) {
        if (rider != null) {
            if (rider.ridingEntity instanceof MCH_EntityAircraft) {
                return (MCH_EntityAircraft)rider.ridingEntity;
            }
            if (rider.ridingEntity instanceof MCH_EntitySeat) {
                return ((MCH_EntitySeat)rider.ridingEntity).getParent();
            }
            if (rider.ridingEntity instanceof MCH_EntityUavStation) {
                final MCH_EntityUavStation uavStation = (MCH_EntityUavStation)rider.ridingEntity;
                return uavStation.getControlAircract();
            }
        }
        return null;
    }
    
    public boolean isCreative(final Entity entity) {
        return (entity instanceof EntityPlayer && ((EntityPlayer)entity).capabilities.isCreativeMode) || (entity instanceof MCH_EntityGunner && ((MCH_EntityGunner)entity).isCreative);
    }
    
    public Entity getRiddenByEntity() {
        if (this.isUAV() && this.uavStation != null) {
            return this.uavStation.riddenByEntity;
        }
        return this.riddenByEntity;
    }
    
    public boolean getCommonStatus(final int bit) {
        return (this.commonStatus >> bit & 0x1) != 0x0;
    }
    
    public void setCommonStatus(final int bit, final boolean b) {
        this.setCommonStatus(bit, b, false);
    }
    
    public void setCommonStatus(final int bit, final boolean b, final boolean writeClient) {
        if (!this.worldObj.isRemote || writeClient) {
            final int bofore = this.commonStatus;
            final int mask = 1 << bit;
            if (b) {
                this.commonStatus |= mask;
            }
            else {
                this.commonStatus &= ~mask;
            }
            if (bofore != this.commonStatus) {
                MCH_Lib.DbgLog(this.worldObj, "setCommonStatus : %08X -> %08X ", this.getDataWatcher().getWatchableObjectInt(23), this.commonStatus);
                this.getDataWatcher().updateObject(23, (Object)this.commonStatus);
            }
        }
    }
    
    public double getThrottle() {
        return 0.05 * this.getDataWatcher().getWatchableObjectInt(29);
    }
    
    public void setThrottle(final double t) {
        int n = (int)(t * 20.0);
        if (n == 0 && t > 0.0) {
            n = 1;
        }
        this.getDataWatcher().updateObject(29, (Object)n);
    }
    
    public int getMaxHP() {
        return (this.getAcInfo() != null) ? this.getAcInfo().maxHp : 100;
    }
    
    public int getHP() {
        return (this.getMaxHP() - this.getDamageTaken() >= 0) ? (this.getMaxHP() - this.getDamageTaken()) : 0;
    }
    
    public void setDamageTaken(int par1) {
        if (par1 < 0) {
            par1 = 0;
        }
        if (par1 > this.getMaxHP()) {
            par1 = this.getMaxHP();
        }
        this.getDataWatcher().updateObject(19, (Object)par1);
    }
    
    public int getDamageTaken() {
        return this.getDataWatcher().getWatchableObjectInt(19);
    }
    
    public void destroyAircraft() {
        this.setSearchLight(false);
        this.switchHoveringMode(false);
        this.switchGunnerMode(false);
        for (int i = 0; i < this.getSeatNum() + 1; ++i) {
            final Entity e = this.getEntityBySeatId(i);
            if (e instanceof EntityPlayer) {
                this.switchCameraMode((EntityPlayer)e, 0);
            }
        }
        if (this.isTargetDrone()) {
            final int n = 20;
            final MCH_Config config = MCH_MOD.config;
            this.setDespawnCount(n * MCH_Config.DespawnCount.prmInt / 10);
        }
        else {
            final int n2 = 20;
            final MCH_Config config2 = MCH_MOD.config;
            this.setDespawnCount(n2 * MCH_Config.DespawnCount.prmInt);
        }
        this.rotDestroyedPitch = this.rand.nextFloat() - 0.5f;
        this.rotDestroyedRoll = (this.rand.nextFloat() - 0.5f) * 0.5f;
        this.rotDestroyedYaw = 0.0f;
        if (this.isUAV() && this.getRiddenByEntity() != null) {
            this.getRiddenByEntity().mountEntity((Entity)null);
        }
        if (!this.worldObj.isRemote) {
            this.ejectSeat(this.getRiddenByEntity());
            final Entity entity = this.getEntityBySeatId(1);
            if (entity != null) {
                this.ejectSeat(entity);
            }
            final MCH_Config config3 = MCH_MOD.config;
            final float dmg = MCH_Config.KillPassengersWhenDestroyed.prmBool ? 100000.0f : 0.001f;
            DamageSource dse = DamageSource.generic;
            if (this.worldObj.difficultySetting.getDifficultyId() == 0) {
                if (this.lastAttackedEntity instanceof EntityPlayer) {
                    dse = DamageSource.causePlayerDamage((EntityPlayer)this.lastAttackedEntity);
                }
            }
            else {
                dse = DamageSource.setExplosionSource(new Explosion(this.worldObj, this.lastAttackedEntity, this.posX, this.posY, this.posZ, 1.0f));
            }
            if (this.riddenByEntity != null) {
                this.riddenByEntity.attackEntityFrom(dse, dmg);
            }
            for (final MCH_EntitySeat seat : this.getSeats()) {
                if (seat != null && seat.riddenByEntity != null) {
                    seat.riddenByEntity.attackEntityFrom(dse, dmg);
                }
            }
        }
    }
    
    public boolean isDestroyed() {
        return this.getDespawnCount() > 0;
    }
    
    public int getDespawnCount() {
        return this.despawnCount;
    }
    
    public void setDespawnCount(final int despawnCount) {
        this.despawnCount = despawnCount;
    }
    
    public boolean isEntityRadarMounted() {
        return this.getAcInfo() != null && this.getAcInfo().isEnableEntityRadar;
    }
    
    public boolean canFloatWater() {
        return this.getAcInfo() != null && this.getAcInfo().isFloat && !this.isDestroyed();
    }
    
    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender(final float par1) {
        if (this.haveSearchLight() && this.isSearchLightON()) {
            return 15728880;
        }
        final int i = MathHelper.floor_double(this.posX);
        final int j = MathHelper.floor_double(this.posZ);
        if (this.worldObj.blockExists(i, 0, j)) {
            final double d0 = (this.boundingBox.maxY - this.boundingBox.minY) * 0.66;
            float fo = (this.getAcInfo() != null) ? this.getAcInfo().submergedDamageHeight : 0.0f;
            if (this.canFloatWater()) {
                fo = this.getAcInfo().floatOffset;
                if (fo < 0.0f) {
                    fo = -fo;
                }
                ++fo;
            }
            final int k = MathHelper.floor_double(this.posY + fo - this.yOffset + d0);
            final int val = this.worldObj.getLightBrightnessForSkyBlocks(i, k, j, 0);
            final int low = val & 0xFFFF;
            final int high = val >> 16 & 0xFFFF;
            if (high < this.brightnessHigh) {
                if (this.brightnessHigh > 0 && this.getCountOnUpdate() % 2 == 0) {
                    --this.brightnessHigh;
                }
            }
            else if (high > this.brightnessHigh) {
                this.brightnessHigh += 4;
                if (this.brightnessHigh > 240) {
                    this.brightnessHigh = 240;
                }
            }
            return this.brightnessHigh << 16 | low;
        }
        return 0;
    }
    
    public MCH_AircraftInfo.CameraPosition getCameraPosInfo() {
        if (this.getAcInfo() == null) {
            return null;
        }
        final Entity player = MCH_Lib.getClientPlayer();
        final int sid = this.getSeatIdByEntity(player);
        if (sid == 0 && this.canSwitchCameraPos() && this.getCameraId() > 0 && this.getCameraId() < this.getAcInfo().cameraPosition.size()) {
            return this.getAcInfo().cameraPosition.get(this.getCameraId());
        }
        if (sid > 0 && sid < this.getSeatsInfo().length && this.getSeatsInfo()[sid].invCamPos) {
            return this.getSeatsInfo()[sid].getCamPos();
        }
        return this.getAcInfo().cameraPosition.get(0);
    }
    
    public int getCameraId() {
        return this.cameraId;
    }
    
    public void setCameraId(final int cameraId) {
        MCH_Lib.DbgLog(true, "MCH_EntityAircraft.setCameraId %d -> %d", this.cameraId, cameraId);
        this.cameraId = cameraId;
    }
    
    public boolean canSwitchCameraPos() {
        return this.getCameraPosNum() >= 2;
    }
    
    public int getCameraPosNum() {
        if (this.getAcInfo() != null) {
            return this.getAcInfo().cameraPosition.size();
        }
        return 1;
    }
    
    public void onAcInfoReloaded() {
        if (this.getAcInfo() == null) {
            return;
        }
        this.setSize(this.getAcInfo().bodyWidth, this.getAcInfo().bodyHeight);
    }
    
    public void writeSpawnData(final ByteBuf buffer) {
        if (this.getAcInfo() != null) {
            buffer.writeFloat(this.getAcInfo().bodyHeight);
            buffer.writeFloat(this.getAcInfo().bodyWidth);
            buffer.writeFloat(this.getAcInfo().thirdPersonDist);
            final byte[] name = this.getTypeName().getBytes();
            buffer.writeShort(name.length);
            buffer.writeBytes(name);
        }
        else {
            buffer.writeFloat(this.height);
            buffer.writeFloat(this.width);
            buffer.writeFloat(4.0f);
            buffer.writeShort(0);
        }
    }
    
    public void readSpawnData(final ByteBuf data) {
        try {
            final float height = data.readFloat();
            final float width = data.readFloat();
            this.setSize(width, height);
            this.thirdPersonDist = data.readFloat();
            final int len = data.readShort();
            if (len > 0) {
                final byte[] dst = new byte[len];
                data.readBytes(dst);
                this.changeType(new String(dst));
            }
        }
        catch (Exception e) {
            MCH_Lib.Log(this, "readSpawnData error!", new Object[0]);
            e.printStackTrace();
        }
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound nbt) {
        this.setDespawnCount(nbt.getInteger("AcDespawnCount"));
        this.setTextureName(nbt.getString("TextureName"));
        this.setCommonUniqueId(nbt.getString("AircraftUniqueId"));
        this.setRotRoll(nbt.getFloat("AcRoll"));
        this.prevRotationRoll = this.getRotRoll();
        final float getFloat = nbt.getFloat("AcLastRYaw");
        this.lastRiderYaw = getFloat;
        this.prevLastRiderYaw = getFloat;
        final float getFloat2 = nbt.getFloat("AcLastRPitch");
        this.lastRiderPitch = getFloat2;
        this.prevLastRiderPitch = getFloat2;
        this.setPartStatus(nbt.getInteger("PartStatus"));
        this.setTypeName(nbt.getString("TypeName"));
        super.readEntityFromNBT(nbt);
        this.getGuiInventory().readEntityFromNBT(nbt);
        this.setCommandForce(nbt.getString("AcCommand"));
        this.setGunnerStatus(nbt.getBoolean("AcGunnerStatus"));
        this.setFuel(nbt.getInteger("AcFuel"));
        final int[] wa_list = nbt.getIntArray("AcWeaponsAmmo");
        for (int i = 0; i < wa_list.length; ++i) {
            this.getWeapon(i).setRestAllAmmoNum(wa_list[i]);
            this.getWeapon(i).reloadMag();
        }
        if (this.getDespawnCount() > 0) {
            this.setDamageTaken(this.getMaxHP());
        }
        else if (nbt.hasKey("AcDamage")) {
            this.setDamageTaken(nbt.getInteger("AcDamage"));
        }
        if (this.haveSearchLight() && nbt.hasKey("SearchLight")) {
            this.setSearchLight(nbt.getBoolean("SearchLight"));
        }
        this.dismountedUserCtrl = nbt.getBoolean("AcDismounted");
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound nbt) {
        nbt.setString("TextureName", this.getTextureName());
        nbt.setString("AircraftUniqueId", this.getCommonUniqueId());
        nbt.setString("TypeName", this.getTypeName());
        nbt.setInteger("PartStatus", this.getPartStatus() & this.getLastPartStatusMask());
        nbt.setInteger("AcFuel", this.getFuel());
        nbt.setInteger("AcDespawnCount", this.getDespawnCount());
        nbt.setFloat("AcRoll", this.getRotRoll());
        nbt.setBoolean("SearchLight", this.isSearchLightON());
        nbt.setFloat("AcLastRYaw", this.getLastRiderYaw());
        nbt.setFloat("AcLastRPitch", this.getLastRiderPitch());
        nbt.setString("AcCommand", this.getCommand());
        if (!nbt.hasKey("AcGunnerStatus")) {
            this.setGunnerStatus(true);
        }
        nbt.setBoolean("AcGunnerStatus", this.getGunnerStatus());
        super.writeEntityToNBT(nbt);
        this.getGuiInventory().writeEntityToNBT(nbt);
        final int[] wa_list = new int[this.getWeaponNum()];
        for (int i = 0; i < wa_list.length; ++i) {
            wa_list[i] = this.getWeapon(i).getRestAllAmmoNum() + this.getWeapon(i).getAmmoNum();
        }
        nbt.setTag("AcWeaponsAmmo", (NBTBase)W_NBTTag.newTagIntArray("AcWeaponsAmmo", wa_list));
        nbt.setInteger("AcDamage", this.getDamageTaken());
        nbt.setBoolean("AcDismounted", this.dismountedUserCtrl);
    }
    
    public boolean attackEntityFrom(final DamageSource damageSource, final float org_damage) {
        float damage = org_damage;
        final float damageFactor = this.lastBBDamageFactor;
        this.lastBBDamageFactor = 1.0f;
        if (this.isEntityInvulnerable()) {
            return false;
        }
        if (this.isDead) {
            return false;
        }
        if (this.timeSinceHit > 0) {
            return false;
        }
        final String dmt = damageSource.getDamageType();
        if (dmt.equalsIgnoreCase("inFire")) {
            return false;
        }
        if (dmt.equalsIgnoreCase("cactus")) {
            return false;
        }
        if (this.worldObj.isRemote) {
            return true;
        }
        final MCH_Config config = MCH_MOD.config;
        damage = MCH_Config.applyDamageByExternal(this, damageSource, damage);
        if (this.getAcInfo() != null && this.getAcInfo().invulnerable) {
            damage = 0.0f;
        }
        if (damageSource == DamageSource.outOfWorld) {
            this.setDead();
        }
        if (!MCH_Multiplay.canAttackEntity(damageSource, this)) {
            return false;
        }
        if (dmt.equalsIgnoreCase("lava")) {
            damage *= this.rand.nextInt(8) + 2;
            this.timeSinceHit = 2;
        }
        if (dmt.startsWith("explosion")) {
            this.timeSinceHit = 1;
        }
        else if (this.isMountedEntity(damageSource.getEntity())) {
            return false;
        }
        if (dmt.equalsIgnoreCase("onFire")) {
            this.timeSinceHit = 10;
        }
        boolean isCreative = false;
        boolean isSneaking = false;
        final Entity entity = damageSource.getEntity();
        if (entity instanceof EntityLivingBase) {
            this.lastAttackedEntity = entity;
        }
        boolean isDamegeSourcePlayer = false;
        boolean playDamageSound = false;
        if (entity instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer)entity;
            isCreative = player.capabilities.isCreativeMode;
            isSneaking = player.isSneaking();
            if (dmt.equalsIgnoreCase("player")) {
                if (isCreative) {
                    isDamegeSourcePlayer = true;
                }
                else if (this.getAcInfo() != null && !this.getAcInfo().creativeOnly) {
                    final MCH_Config config2 = MCH_MOD.config;
                    if (!MCH_Config.PreventingBroken.prmBool) {
                        final MCH_Config config3 = MCH_MOD.config;
                        if (MCH_Config.BreakableOnlyPickaxe.prmBool) {
                            if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ItemPickaxe) {
                                isDamegeSourcePlayer = true;
                            }
                        }
                        else {
                            isDamegeSourcePlayer = !this.isRidePlayer();
                        }
                    }
                }
            }
            W_WorldFunc.MOD_playSoundAtEntity(this, "hit", (damage > 0.0f) ? 1.0f : 0.5f, 1.0f);
        }
        else {
            playDamageSound = true;
        }
        if (!this.isDestroyed()) {
            if (!isDamegeSourcePlayer) {
                final MCH_AircraftInfo acInfo = this.getAcInfo();
                if (acInfo != null && !dmt.equalsIgnoreCase("lava") && !dmt.equalsIgnoreCase("onFire")) {
                    if (damage > acInfo.armorMaxDamage) {
                        damage = acInfo.armorMaxDamage;
                    }
                    if (damageFactor <= 1.0f) {
                        damage *= damageFactor;
                    }
                    damage *= acInfo.armorDamageFactor;
                    damage -= acInfo.armorMinDamage;
                    if (damage <= 0.0f) {
                        MCH_Lib.DbgLog(this.worldObj, "MCH_EntityAircraft.attackEntityFrom:no damage=%.1f -> %.1f(factor=%.2f):%s", org_damage, damage, damageFactor, dmt);
                        return false;
                    }
                    if (damageFactor > 1.0f) {
                        damage *= damageFactor;
                    }
                }
                MCH_Lib.DbgLog(this.worldObj, "MCH_EntityAircraft.attackEntityFrom:damage=%.1f(factor=%.2f):%s", damage, damageFactor, dmt);
                this.setDamageTaken(this.getDamageTaken() + (int)damage);
            }
            this.setBeenAttacked();
            if (this.getDamageTaken() >= this.getMaxHP() || isDamegeSourcePlayer) {
                if (!isDamegeSourcePlayer) {
                    this.setDamageTaken(this.getMaxHP());
                    this.destroyAircraft();
                    this.timeSinceHit = 20;
                    String cmd = this.getCommand().trim();
                    if (cmd.startsWith("/")) {
                        cmd = cmd.substring(1);
                    }
                    if (!cmd.isEmpty()) {
                        MCH_DummyCommandSender.execCommand(cmd);
                    }
                    if (dmt.equalsIgnoreCase("inWall")) {
                        this.explosionByCrash(0.0);
                        this.damageSinceDestroyed = this.getMaxHP();
                    }
                    else {
                        MCH_Explosion.newExplosion(this.worldObj, null, entity, this.posX, this.posY, this.posZ, 2.0f, 2.0f, true, true, true, true, 5);
                    }
                }
                else {
                    if (this.getAcInfo() != null && this.getAcInfo().getItem() != null) {
                        if (isCreative) {
                            final MCH_Config config4 = MCH_MOD.config;
                            if (MCH_Config.DropItemInCreativeMode.prmBool && !isSneaking) {
                                this.dropItemWithOffset(this.getAcInfo().getItem(), 1, 0.0f);
                            }
                            final MCH_Config config5 = MCH_MOD.config;
                            if (!MCH_Config.DropItemInCreativeMode.prmBool && isSneaking) {
                                this.dropItemWithOffset(this.getAcInfo().getItem(), 1, 0.0f);
                            }
                        }
                        else {
                            this.dropItemWithOffset(this.getAcInfo().getItem(), 1, 0.0f);
                        }
                    }
                    this.setDead(true);
                }
            }
        }
        else if (isDamegeSourcePlayer && isCreative) {
            this.setDead(true);
        }
        if (playDamageSound) {
            W_WorldFunc.MOD_playSoundAtEntity(this, "helidmg", 1.0f, 0.9f + this.rand.nextFloat() * 0.1f);
        }
        return true;
    }
    
    public boolean isExploded() {
        return this.isDestroyed() && this.damageSinceDestroyed > this.getMaxHP() / 10 + 1;
    }
    
    public void destruct() {
        if (this.getRiddenByEntity() != null) {
            this.getRiddenByEntity().mountEntity((Entity)null);
        }
        this.setDead(true);
    }
    
    public EntityItem entityDropItem(final ItemStack is, final float par2) {
        if (is.stackSize == 0) {
            return null;
        }
        this.setAcDataToItem(is);
        return super.entityDropItem(is, par2);
    }
    
    public void setAcDataToItem(final ItemStack is) {
        if (!is.hasTagCompound()) {
            is.setTagCompound(new NBTTagCompound());
        }
        final NBTTagCompound nbt = is.getTagCompound();
        nbt.setString("MCH_Command", this.getCommand());
        final MCH_Config config = MCH_MOD.config;
        if (MCH_Config.ItemFuel.prmBool) {
            nbt.setInteger("MCH_Fuel", this.getFuel());
        }
        final MCH_Config config2 = MCH_MOD.config;
        if (MCH_Config.ItemDamage.prmBool) {
            is.setMetadata(this.getDamageTaken());
        }
    }
    
    public void getAcDataFromItem(final ItemStack is) {
        if (!is.hasTagCompound()) {
            return;
        }
        final NBTTagCompound nbt = is.getTagCompound();
        this.setCommandForce(nbt.getString("MCH_Command"));
        final MCH_Config config = MCH_MOD.config;
        if (MCH_Config.ItemFuel.prmBool) {
            this.setFuel(nbt.getInteger("MCH_Fuel"));
        }
        final MCH_Config config2 = MCH_MOD.config;
        if (MCH_Config.ItemDamage.prmBool) {
            this.setDamageTaken(is.getMetadata());
        }
    }
    
    @Override
    public boolean isUseableByPlayer(final EntityPlayer player) {
        if (this.isUAV()) {
            return super.isUseableByPlayer(player);
        }
        if (this.isDead) {
            return false;
        }
        if (this.getSeatIdByEntity((Entity)player) >= 0) {
            return player.getDistanceSqToEntity((Entity)this) <= 4096.0;
        }
        return player.getDistanceSqToEntity((Entity)this) <= 64.0;
    }
    
    public void applyEntityCollision(final Entity par1Entity) {
    }
    
    public void addVelocity(final double par1, final double par3, final double par5) {
    }
    
    public void setVelocity(final double par1, final double par3, final double par5) {
        this.motionX = par1;
        this.velocityX = par1;
        this.motionY = par3;
        this.velocityY = par3;
        this.motionZ = par5;
        this.velocityZ = par5;
    }
    
    public void onFirstUpdate() {
        if (!this.worldObj.isRemote) {
            final int bit = 3;
            final MCH_Config config = MCH_MOD.config;
            this.setCommonStatus(bit, MCH_Config.InfinityAmmo.prmBool);
            final int bit2 = 4;
            final MCH_Config config2 = MCH_MOD.config;
            this.setCommonStatus(bit2, MCH_Config.InfinityFuel.prmBool);
        }
    }
    
    public void onRidePilotFirstUpdate() {
        if (this.worldObj.isRemote && W_Lib.isClientPlayer(this.getRiddenByEntity())) {
            this.updateClientSettings(0);
        }
        final Entity pilot = this.getRiddenByEntity();
        if (pilot != null) {
            pilot.rotationYaw = this.getLastRiderYaw();
            pilot.rotationPitch = this.getLastRiderPitch();
        }
        this.keepOnRideRotation = false;
        if (this.getAcInfo() != null) {
            this.switchFreeLookModeClient(this.getAcInfo().defaultFreelook);
        }
    }
    
    public double getCurrentThrottle() {
        return this.currentThrottle;
    }
    
    public void setCurrentThrottle(final double throttle) {
        this.currentThrottle = throttle;
    }
    
    public void addCurrentThrottle(final double throttle) {
        this.setCurrentThrottle(this.getCurrentThrottle() + throttle);
    }
    
    public double getPrevCurrentThrottle() {
        return this.prevCurrentThrottle;
    }
    
    public boolean canMouseRot() {
        return !this.isDead && this.getRiddenByEntity() != null && !this.isDestroyed();
    }
    
    public boolean canUpdateYaw(final Entity player) {
        return this.getRidingEntity() == null && this.getCountOnUpdate() >= 30 && MCH_Lib.getBlockIdY(this, 3, -2) == 0;
    }
    
    public boolean canUpdatePitch(final Entity player) {
        return this.getCountOnUpdate() >= 30 && MCH_Lib.getBlockIdY(this, 3, -2) == 0;
    }
    
    public boolean canUpdateRoll(final Entity player) {
        return this.getRidingEntity() == null && this.getCountOnUpdate() >= 30 && MCH_Lib.getBlockIdY(this, 3, -2) == 0;
    }
    
    public boolean isOverridePlayerYaw() {
        return !this.isFreeLookMode();
    }
    
    public boolean isOverridePlayerPitch() {
        return !this.isFreeLookMode();
    }
    
    public double getAddRotationYawLimit() {
        return (this.getAcInfo() != null) ? (40.0 * this.getAcInfo().mobilityYaw) : 40.0;
    }
    
    public double getAddRotationPitchLimit() {
        return (this.getAcInfo() != null) ? (40.0 * this.getAcInfo().mobilityPitch) : 40.0;
    }
    
    public double getAddRotationRollLimit() {
        return (this.getAcInfo() != null) ? (40.0 * this.getAcInfo().mobilityRoll) : 40.0;
    }
    
    public float getYawFactor() {
        return 1.0f;
    }
    
    public float getPitchFactor() {
        return 1.0f;
    }
    
    public float getRollFactor() {
        return 1.0f;
    }
    
    public abstract void onUpdateAngles(final float p0);
    
    public float getControlRotYaw(final float mouseX, final float mouseY, final float tick) {
        return 0.0f;
    }
    
    public float getControlRotPitch(final float mouseX, final float mouseY, final float tick) {
        return 0.0f;
    }
    
    public float getControlRotRoll(final float mouseX, final float mouseY, final float tick) {
        return 0.0f;
    }
    
    public void setAngles(final Entity player, final boolean fixRot, final float fixYaw, final float fixPitch, final float deltaX, final float deltaY, float x, float y, float partialTicks) {
        if (partialTicks < 0.03f) {
            partialTicks = 0.4f;
        }
        if (partialTicks > 0.9f) {
            partialTicks = 0.6f;
        }
        this.lowPassPartialTicks.put(partialTicks);
        partialTicks = this.lowPassPartialTicks.getAvg();
        final float ac_pitch = this.getRotPitch();
        final float ac_yaw = this.getRotYaw();
        final float ac_roll = this.getRotRoll();
        if (this.isFreeLookMode()) {
            y = (x = 0.0f);
        }
        float yaw = 0.0f;
        float pitch = 0.0f;
        float roll = 0.0f;
        if (this.canUpdateYaw(player)) {
            final double limit = this.getAddRotationYawLimit();
            yaw = this.getControlRotYaw(x, y, partialTicks);
            if (yaw < -limit) {
                yaw = (float)(-limit);
            }
            if (yaw > limit) {
                yaw = (float)limit;
            }
            yaw = (float)(yaw * this.getYawFactor() * 0.06 * partialTicks);
        }
        if (this.canUpdatePitch(player)) {
            final double limit = this.getAddRotationPitchLimit();
            pitch = this.getControlRotPitch(x, y, partialTicks);
            if (pitch < -limit) {
                pitch = (float)(-limit);
            }
            if (pitch > limit) {
                pitch = (float)limit;
            }
            pitch = (float)(-pitch * this.getPitchFactor() * 0.06 * partialTicks);
        }
        if (this.canUpdateRoll(player)) {
            final double limit = this.getAddRotationRollLimit();
            roll = this.getControlRotRoll(x, y, partialTicks);
            if (roll < -limit) {
                roll = (float)(-limit);
            }
            if (roll > limit) {
                roll = (float)limit;
            }
            roll = roll * this.getRollFactor() * 0.06f * partialTicks;
        }
        final MCH_Math.FMatrix m_add = MCH_Math.newMatrix();
        MCH_Math.MatTurnZ(m_add, roll / 180.0f * 3.1415927f);
        MCH_Math.MatTurnX(m_add, pitch / 180.0f * 3.1415927f);
        MCH_Math.MatTurnY(m_add, yaw / 180.0f * 3.1415927f);
        MCH_Math.MatTurnZ(m_add, (float)(this.getRotRoll() / 180.0f * 3.141592653589793));
        MCH_Math.MatTurnX(m_add, (float)(this.getRotPitch() / 180.0f * 3.141592653589793));
        MCH_Math.MatTurnY(m_add, (float)(this.getRotYaw() / 180.0f * 3.141592653589793));
        final MCH_Math.FVector3D v = MCH_Math.MatrixToEuler(m_add);
        if (this.getAcInfo().limitRotation) {
            v.x = MCH_Lib.RNG(v.x, this.getAcInfo().minRotationPitch, this.getAcInfo().maxRotationPitch);
            v.z = MCH_Lib.RNG(v.z, this.getAcInfo().minRotationRoll, this.getAcInfo().maxRotationRoll);
        }
        if (v.z > 180.0f) {
            final MCH_Math.FVector3D fVector3D = v;
            fVector3D.z -= 360.0f;
        }
        if (v.z < -180.0f) {
            final MCH_Math.FVector3D fVector3D2 = v;
            fVector3D2.z += 360.0f;
        }
        this.setRotYaw(v.y);
        this.setRotPitch(v.x);
        this.setRotRoll(v.z);
        this.onUpdateAngles(partialTicks);
        if (this.getAcInfo().limitRotation) {
            v.x = MCH_Lib.RNG(this.getRotPitch(), this.getAcInfo().minRotationPitch, this.getAcInfo().maxRotationPitch);
            v.z = MCH_Lib.RNG(this.getRotRoll(), this.getAcInfo().minRotationRoll, this.getAcInfo().maxRotationRoll);
            this.setRotPitch(v.x);
            this.setRotRoll(v.z);
        }
        final float RV = 180.0f;
        if (MathHelper.abs(this.getRotPitch()) > 90.0f) {
            MCH_Lib.DbgLog(true, "MCH_EntityAircraft.setAngles Error:Pitch=%.1f", this.getRotPitch());
        }
        if (this.getRotRoll() > 180.0f) {
            this.setRotRoll(this.getRotRoll() - 360.0f);
        }
        if (this.getRotRoll() < -180.0f) {
            this.setRotRoll(this.getRotRoll() + 360.0f);
        }
        this.prevRotationRoll = this.getRotRoll();
        this.prevRotationPitch = this.getRotPitch();
        if (this.getRidingEntity() == null) {
            this.prevRotationYaw = this.getRotYaw();
        }
        if (this.isOverridePlayerYaw() || fixRot) {
            if (this.getRidingEntity() == null) {
                player.prevRotationYaw = this.getRotYaw() + (fixRot ? fixYaw : 0.0f);
            }
            else {
                if (this.getRotYaw() - player.rotationYaw > 180.0f) {
                    player.prevRotationYaw += 360.0f;
                }
                if (this.getRotYaw() - player.rotationYaw < -180.0f) {
                    player.prevRotationYaw -= 360.0f;
                }
            }
            player.rotationYaw = this.getRotYaw() + (fixRot ? fixYaw : 0.0f);
        }
        else {
            player.setAngles(deltaX, 0.0f);
        }
        if (this.isOverridePlayerPitch() || fixRot) {
            player.prevRotationPitch = this.getRotPitch() + (fixRot ? fixPitch : 0.0f);
            player.rotationPitch = this.getRotPitch() + (fixRot ? fixPitch : 0.0f);
        }
        else {
            player.setAngles(0.0f, deltaY);
        }
        if ((this.getRidingEntity() == null && ac_yaw != this.getRotYaw()) || ac_pitch != this.getRotPitch() || ac_roll != this.getRotRoll()) {
            this.aircraftRotChanged = true;
        }
    }
    
    public boolean canSwitchSearchLight(final Entity entity) {
        return this.haveSearchLight() && this.getSeatIdByEntity(entity) <= 1;
    }
    
    public boolean isSearchLightON() {
        return this.getCommonStatus(6);
    }
    
    public void setSearchLight(final boolean onoff) {
        this.setCommonStatus(6, onoff);
    }
    
    public boolean haveSearchLight() {
        return this.getAcInfo() != null && this.getAcInfo().searchLights.size() > 0;
    }
    
    public float getSearchLightValue(final Entity entity) {
        if (this.haveSearchLight() && this.isSearchLightON()) {
            for (final MCH_AircraftInfo.SearchLight sl : this.getAcInfo().searchLights) {
                final Vec3 pos = this.getTransformedPosition(sl.pos);
                final double dist = entity.getDistanceSq(pos.xCoord, pos.yCoord, pos.zCoord);
                if (dist > 2.0 && dist < sl.height * sl.height + 20.0f) {
                    final double cx = entity.posX - pos.xCoord;
                    final double cy = entity.posY - pos.yCoord;
                    final double cz = entity.posZ - pos.zCoord;
                    double h = 0.0;
                    double v = 0.0;
                    if (!sl.fixDir) {
                        final Vec3 vv = MCH_Lib.RotVec3(0.0, 0.0, 1.0, -this.lastSearchLightYaw + sl.yaw, -this.lastSearchLightPitch + sl.pitch, -this.getRotRoll());
                        h = MCH_Lib.getPosAngle(vv.xCoord, vv.zCoord, cx, cz);
                        v = Math.atan2(cy, Math.sqrt(cx * cx + cz * cz)) * 180.0 / 3.141592653589793;
                        v = Math.abs(v + this.lastSearchLightPitch + sl.pitch);
                    }
                    else {
                        float stRot = 0.0f;
                        if (sl.steering) {
                            stRot = this.rotYawWheel * sl.stRot;
                        }
                        final Vec3 vv2 = MCH_Lib.RotVec3(0.0, 0.0, 1.0, -this.getRotYaw() + sl.yaw + stRot, -this.getRotPitch() + sl.pitch, -this.getRotRoll());
                        h = MCH_Lib.getPosAngle(vv2.xCoord, vv2.zCoord, cx, cz);
                        v = Math.atan2(cy, Math.sqrt(cx * cx + cz * cz)) * 180.0 / 3.141592653589793;
                        v = Math.abs(v + this.getRotPitch() + sl.pitch);
                    }
                    final float angle = sl.angle * 3.0f;
                    if (h < angle && v < angle) {
                        float value = 0.0f;
                        if (h + v < angle) {
                            value = (float)(1440.0 * (1.0 - (h + v) / angle));
                        }
                        return (value <= 240.0f) ? value : 240.0f;
                    }
                    continue;
                }
            }
        }
        return 0.0f;
    }
    
    public abstract void onUpdateAircraft();
    
    public void onUpdate() {
        if (this.getCountOnUpdate() < 2) {
            this.prevPosition.clear(Vec3.createVectorHelper(this.posX, this.posY, this.posZ));
        }
        this.prevCurrentThrottle = this.getCurrentThrottle();
        this.lastBBDamageFactor = 1.0f;
        this.updateControl();
        this.checkServerNoMove();
        this.onUpdate_RidingEntity();
        final Iterator<UnmountReserve> itr = this.listUnmountReserve.iterator();
        while (itr.hasNext()) {
            final UnmountReserve ur = itr.next();
            if (ur.entity != null && !ur.entity.isDead) {
                ur.entity.setPosition(ur.posX, ur.posY, ur.posZ);
                ur.entity.fallDistance = this.fallDistance;
            }
            if (ur.cnt > 0) {
                final UnmountReserve unmountReserve = ur;
                --unmountReserve.cnt;
            }
            if (ur.cnt == 0) {
                itr.remove();
            }
        }
        if (this.isDestroyed() && this.getCountOnUpdate() % 20 == 0) {
            for (int sid = 0; sid < this.getSeatNum() + 1; ++sid) {
                final Entity entity = this.getEntityBySeatId(sid);
                if (entity != null) {
                    if (sid != 0 || !this.isUAV()) {
                        final MCH_Config config = MCH_MOD.config;
                        if (MCH_Config.applyDamageVsEntity(entity, DamageSource.inFire, 1.0f) > 0.0f) {
                            entity.setFire(5);
                        }
                    }
                }
            }
        }
        if ((this.aircraftRotChanged || this.aircraftRollRev) && this.worldObj.isRemote && this.getRiddenByEntity() != null) {
            MCH_PacketIndRotation.send(this);
            this.aircraftRotChanged = false;
            this.aircraftRollRev = false;
        }
        if (!this.worldObj.isRemote && (int)this.prevRotationRoll != (int)this.getRotRoll()) {
            final float roll = MathHelper.wrapAngleTo180_float(this.getRotRoll());
            this.getDataWatcher().updateObject(26, (Object)new Short((short)roll));
        }
        this.prevRotationRoll = this.getRotRoll();
        if (!this.worldObj.isRemote && this.isTargetDrone() && !this.isDestroyed() && this.getCountOnUpdate() > 20 && !this.canUseFuel()) {
            this.setDamageTaken(this.getMaxHP());
            this.destroyAircraft();
            MCH_Explosion.newExplosion(this.worldObj, null, null, this.posX, this.posY, this.posZ, 2.0f, 2.0f, true, true, true, true, 5);
        }
        if (this.worldObj.isRemote && this.getAcInfo() != null && this.getHP() <= 0 && this.getDespawnCount() <= 0) {
            this.destroyAircraft();
        }
        if (!this.worldObj.isRemote && this.getDespawnCount() > 0) {
            this.setDespawnCount(this.getDespawnCount() - 1);
            if (this.getDespawnCount() <= 1) {
                this.setDead(true);
            }
        }
        super.onUpdate();
        if (this.getParts() != null) {
            for (final Entity e : this.getParts()) {
                if (e != null) {
                    e.onUpdate();
                }
            }
        }
        this.updateNoCollisionEntities();
        this.updateUAV();
        this.supplyFuel();
        this.supplyAmmoToOtherAircraft();
        this.updateFuel();
        this.repairOtherAircraft();
        if (this.modeSwitchCooldown > 0) {
            --this.modeSwitchCooldown;
        }
        if (this.lastRiddenByEntity == null && this.getRiddenByEntity() != null) {
            this.onRidePilotFirstUpdate();
        }
        if (this.countOnUpdate == 0) {
            this.onFirstUpdate();
        }
        ++this.countOnUpdate;
        if (this.countOnUpdate >= 1000000) {
            this.countOnUpdate = 1;
        }
        if (this.worldObj.isRemote) {
            this.commonStatus = this.getDataWatcher().getWatchableObjectInt(23);
        }
        this.fallDistance = 0.0f;
        if (this.riddenByEntity != null) {
            this.riddenByEntity.fallDistance = 0.0f;
        }
        if (this.missileDetector != null) {
            this.missileDetector.update();
        }
        if (this.soundUpdater != null) {
            this.soundUpdater.update();
        }
        if (this.getTowChainEntity() != null && this.getTowChainEntity().isDead) {
            this.setTowChainEntity(null);
        }
        this.updateSupplyAmmo();
        this.autoRepair();
        final int ft = this.getFlareTick();
        this.flareDv.update();
        if (!this.worldObj.isRemote && this.getFlareTick() == 0 && ft != 0) {
            this.setCommonStatus(0, false);
        }
        final Entity e2 = this.getRiddenByEntity();
        if (e2 != null && !e2.isDead && !this.isDestroyed()) {
            this.lastRiderYaw = e2.rotationYaw;
            this.prevLastRiderYaw = e2.prevRotationYaw;
            this.lastRiderPitch = e2.rotationPitch;
            this.prevLastRiderPitch = e2.prevRotationPitch;
        }
        else if (this.getTowedChainEntity() != null || this.ridingEntity != null) {
            this.lastRiderYaw = this.rotationYaw;
            this.prevLastRiderYaw = this.prevRotationYaw;
            this.lastRiderPitch = this.rotationPitch;
            this.prevLastRiderPitch = this.prevRotationPitch;
        }
        this.updatePartCameraRotate();
        this.updatePartWheel();
        this.updatePartCrawlerTrack();
        this.updatePartLightHatch();
        this.regenerationMob();
        if (this.getRiddenByEntity() == null && this.lastRiddenByEntity != null) {
            this.unmountEntity();
        }
        this.updateExtraBoundingBox();
        final boolean prevOnGround = this.onGround;
        final double prevMotionY = this.motionY;
        this.onUpdateAircraft();
        if (this.getAcInfo() != null) {
            this.updateParts(this.getPartStatus());
        }
        if (this.recoilCount > 0) {
            --this.recoilCount;
        }
        if (!W_Entity.isEqual(MCH_MOD.proxy.getClientPlayer(), this.getRiddenByEntity())) {
            this.updateRecoil(1.0f);
        }
        if (!this.worldObj.isRemote && this.isDestroyed() && !this.isExploded() && !prevOnGround && this.onGround && prevMotionY < -0.2) {
            this.explosionByCrash(prevMotionY);
            this.damageSinceDestroyed = this.getMaxHP();
        }
        this.onUpdate_PartRotation();
        this.onUpdate_ParticleSmoke();
        this.updateSeatsPosition(this.posX, this.posY, this.posZ, false);
        this.updateHitBoxPosition();
        this.onUpdate_CollisionGroundDamage();
        this.onUpdate_UnmountCrew();
        this.onUpdate_Repelling();
        this.checkRideRack();
        if (this.lastRidingEntity == null && this.getRidingEntity() != null) {
            this.onRideEntity(this.getRidingEntity());
        }
        this.lastRiddenByEntity = this.getRiddenByEntity();
        this.lastRidingEntity = this.getRidingEntity();
        this.prevPosition.put(Vec3.createVectorHelper(this.posX, this.posY, this.posZ));
    }
    
    private void updateNoCollisionEntities() {
        if (this.worldObj.isRemote) {
            return;
        }
        if (this.getCountOnUpdate() % 10 != 0) {
            return;
        }
        for (int i = 0; i < 1 + this.getSeatNum(); ++i) {
            final Entity e = this.getEntityBySeatId(i);
            if (e != null) {
                this.noCollisionEntities.put(e, 8);
            }
        }
        if (this.getTowChainEntity() != null && this.getTowChainEntity().towedEntity != null) {
            this.noCollisionEntities.put(this.getTowChainEntity().towedEntity, 60);
        }
        if (this.getTowedChainEntity() != null && this.getTowedChainEntity().towEntity != null) {
            this.noCollisionEntities.put(this.getTowedChainEntity().towEntity, 60);
        }
        if (this.ridingEntity instanceof MCH_EntitySeat) {
            final MCH_EntityAircraft ac = ((MCH_EntitySeat)this.ridingEntity).getParent();
            if (ac != null) {
                this.noCollisionEntities.put(ac, 60);
            }
        }
        else if (this.ridingEntity != null) {
            this.noCollisionEntities.put(this.ridingEntity, 60);
        }
        for (final Entity key : this.noCollisionEntities.keySet()) {
            this.noCollisionEntities.put(key, this.noCollisionEntities.get(key) - 1);
        }
        final Iterator<Integer> key2 = this.noCollisionEntities.values().iterator();
        while (key2.hasNext()) {
            if (key2.next() <= 0) {
                key2.remove();
            }
        }
    }
    
    public void updateControl() {
        if (!this.worldObj.isRemote) {
            this.setCommonStatus(7, this.moveLeft);
            this.setCommonStatus(8, this.moveRight);
            this.setCommonStatus(9, this.throttleUp);
            this.setCommonStatus(10, this.throttleDown);
        }
        else if (MCH_MOD.proxy.getClientPlayer() != this.getRiddenByEntity()) {
            this.moveLeft = this.getCommonStatus(7);
            this.moveRight = this.getCommonStatus(8);
            this.throttleUp = this.getCommonStatus(9);
            this.throttleDown = this.getCommonStatus(10);
        }
    }
    
    public void updateRecoil(final float partialTicks) {
        if (this.recoilCount > 0 && this.recoilCount >= 12) {
            final float pitch = MathHelper.cos((float)((this.recoilYaw - this.getRotRoll()) * 3.141592653589793 / 180.0));
            final float roll = MathHelper.sin((float)((this.recoilYaw - this.getRotRoll()) * 3.141592653589793 / 180.0));
            final float recoil = MathHelper.cos((float)(this.recoilCount * 6 * 3.141592653589793 / 180.0)) * this.recoilValue;
            this.setRotPitch(this.getRotPitch() + recoil * pitch * partialTicks);
            this.setRotRoll(this.getRotRoll() + recoil * roll * partialTicks);
        }
    }
    
    private void updatePartLightHatch() {
        this.prevRotLightHatch = this.rotLightHatch;
        if (this.isSearchLightON()) {
            this.rotLightHatch += 0.5;
        }
        else {
            this.rotLightHatch -= 0.5;
        }
        if (this.rotLightHatch > 1.0f) {
            this.rotLightHatch = 1.0f;
        }
        if (this.rotLightHatch < 0.0f) {
            this.rotLightHatch = 0.0f;
        }
    }
    
    public void updateExtraBoundingBox() {
        for (final MCH_BoundingBox bb : this.extraBoundingBox) {
            bb.updatePosition(this.posX, this.posY, this.posZ, this.getRotYaw(), this.getRotPitch(), this.getRotRoll());
        }
    }
    
    public void updatePartWheel() {
        if (!this.worldObj.isRemote) {
            return;
        }
        if (this.getAcInfo() == null) {
            return;
        }
        this.prevRotWheel = this.rotWheel;
        this.prevRotYawWheel = this.rotYawWheel;
        final float LEN = 1.0f;
        final float MIN = 0.0f;
        double throttle = this.getCurrentThrottle();
        double pivotTurnThrottle = this.getAcInfo().pivotTurnThrottle;
        if (pivotTurnThrottle <= 0.0) {
            pivotTurnThrottle = 1.0;
        }
        else {
            pivotTurnThrottle *= 0.10000000149011612;
        }
        final boolean localMoveLeft = this.moveLeft;
        final boolean localMoveRight = this.moveRight;
        if (this.getAcInfo().enableBack && this.throttleBack > 0.01 && throttle <= 0.0) {
            throttle = -this.throttleBack * 15.0f;
        }
        if (localMoveLeft && !localMoveRight) {
            this.rotYawWheel += 0.1f;
            if (this.rotYawWheel > 1.0f) {
                this.rotYawWheel = 1.0f;
            }
        }
        else if (!localMoveLeft && localMoveRight) {
            this.rotYawWheel -= 0.1f;
            if (this.rotYawWheel < -1.0f) {
                this.rotYawWheel = -1.0f;
            }
        }
        else {
            this.rotYawWheel *= 0.9f;
        }
        this.rotWheel += (float)(throttle * this.getAcInfo().partWheelRot);
        if (this.rotWheel >= 360.0f) {
            this.rotWheel -= 360.0f;
            this.prevRotWheel -= 360.0f;
        }
        else if (this.rotWheel < 0.0f) {
            this.rotWheel += 360.0f;
            this.prevRotWheel += 360.0f;
        }
    }
    
    public void updatePartCrawlerTrack() {
        if (!this.worldObj.isRemote) {
            return;
        }
        if (this.getAcInfo() == null) {
            return;
        }
        this.prevRotTrackRoller[0] = this.rotTrackRoller[0];
        this.prevRotTrackRoller[1] = this.rotTrackRoller[1];
        this.prevRotCrawlerTrack[0] = this.rotCrawlerTrack[0];
        this.prevRotCrawlerTrack[1] = this.rotCrawlerTrack[1];
        final float LEN = 1.0f;
        final float MIN = 0.0f;
        double throttle = this.getCurrentThrottle();
        double pivotTurnThrottle = this.getAcInfo().pivotTurnThrottle;
        if (pivotTurnThrottle <= 0.0) {
            pivotTurnThrottle = 1.0;
        }
        else {
            pivotTurnThrottle *= 0.10000000149011612;
        }
        boolean localMoveLeft = this.moveLeft;
        boolean localMoveRight = this.moveRight;
        int dir = 1;
        if (this.getAcInfo().enableBack && this.throttleBack > 0.0f && throttle <= 0.0) {
            throttle = -this.throttleBack * 5.0f;
            if (localMoveLeft != localMoveRight) {
                final boolean tmp = localMoveLeft;
                localMoveLeft = localMoveRight;
                localMoveRight = tmp;
                dir = -1;
            }
        }
        if (localMoveLeft && !localMoveRight) {
            throttle = 0.2 * dir;
            final float[] throttleCrawlerTrack = this.throttleCrawlerTrack;
            final int n = 0;
            throttleCrawlerTrack[n] += (float)throttle;
            final float[] throttleCrawlerTrack2 = this.throttleCrawlerTrack;
            final int n2 = 1;
            throttleCrawlerTrack2[n2] -= (float)(pivotTurnThrottle * throttle);
        }
        else if (!localMoveLeft && localMoveRight) {
            throttle = 0.2 * dir;
            final float[] throttleCrawlerTrack3 = this.throttleCrawlerTrack;
            final int n3 = 0;
            throttleCrawlerTrack3[n3] -= (float)(pivotTurnThrottle * throttle);
            final float[] throttleCrawlerTrack4 = this.throttleCrawlerTrack;
            final int n4 = 1;
            throttleCrawlerTrack4[n4] += (float)throttle;
        }
        else {
            if (throttle > 0.2) {
                throttle = 0.2;
            }
            if (throttle < -0.2) {
                throttle = -0.2;
            }
            final float[] throttleCrawlerTrack5 = this.throttleCrawlerTrack;
            final int n5 = 0;
            throttleCrawlerTrack5[n5] += (float)throttle;
            final float[] throttleCrawlerTrack6 = this.throttleCrawlerTrack;
            final int n6 = 1;
            throttleCrawlerTrack6[n6] += (float)throttle;
        }
        for (int i = 0; i < 2; ++i) {
            if (this.throttleCrawlerTrack[i] < -0.72f) {
                this.throttleCrawlerTrack[i] = -0.72f;
            }
            else if (this.throttleCrawlerTrack[i] > 0.72f) {
                this.throttleCrawlerTrack[i] = 0.72f;
            }
            final float[] rotTrackRoller = this.rotTrackRoller;
            final int n7 = i;
            rotTrackRoller[n7] += this.throttleCrawlerTrack[i] * this.getAcInfo().trackRollerRot;
            if (this.rotTrackRoller[i] >= 360.0f) {
                final float[] rotTrackRoller2 = this.rotTrackRoller;
                final int n8 = i;
                rotTrackRoller2[n8] -= 360.0f;
                final float[] prevRotTrackRoller = this.prevRotTrackRoller;
                final int n9 = i;
                prevRotTrackRoller[n9] -= 360.0f;
            }
            else if (this.rotTrackRoller[i] < 0.0f) {
                final float[] rotTrackRoller3 = this.rotTrackRoller;
                final int n10 = i;
                rotTrackRoller3[n10] += 360.0f;
                final float[] prevRotTrackRoller2 = this.prevRotTrackRoller;
                final int n11 = i;
                prevRotTrackRoller2[n11] += 360.0f;
            }
            final float[] rotCrawlerTrack = this.rotCrawlerTrack;
            final int n12 = i;
            rotCrawlerTrack[n12] -= this.throttleCrawlerTrack[i];
            while (this.rotCrawlerTrack[i] >= 1.0f) {
                final float[] rotCrawlerTrack2 = this.rotCrawlerTrack;
                final int n13 = i;
                --rotCrawlerTrack2[n13];
                final float[] prevRotCrawlerTrack = this.prevRotCrawlerTrack;
                final int n14 = i;
                --prevRotCrawlerTrack[n14];
            }
            while (this.rotCrawlerTrack[i] < 0.0f) {
                final float[] rotCrawlerTrack3 = this.rotCrawlerTrack;
                final int n15 = i;
                ++rotCrawlerTrack3[n15];
            }
            while (this.prevRotCrawlerTrack[i] < 0.0f) {
                final float[] prevRotCrawlerTrack2 = this.prevRotCrawlerTrack;
                final int n16 = i;
                ++prevRotCrawlerTrack2[n16];
            }
            final float[] throttleCrawlerTrack7 = this.throttleCrawlerTrack;
            final int n17 = i;
            throttleCrawlerTrack7[n17] *= 0.75;
        }
    }
    
    public void checkServerNoMove() {
        if (!this.worldObj.isRemote) {
            final double moti = this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ;
            if (moti < 1.0E-4) {
                if (this.serverNoMoveCount < 20) {
                    ++this.serverNoMoveCount;
                    if (this.serverNoMoveCount >= 20) {
                        this.serverNoMoveCount = 0;
                        if (this.worldObj instanceof WorldServer) {
                            ((WorldServer)this.worldObj).getEntityTracker().sendToAllTrackingEntity((Entity)this, (Packet)new S12PacketEntityVelocity(this.getEntityId(), 0.0, 0.0, 0.0));
                        }
                    }
                }
            }
            else {
                this.serverNoMoveCount = 0;
            }
        }
    }
    
    public boolean haveRotPart() {
        return this.worldObj.isRemote && this.getAcInfo() != null && this.rotPartRotation.length > 0 && this.rotPartRotation.length == this.getAcInfo().partRotPart.size();
    }
    
    public void onUpdate_PartRotation() {
        if (this.haveRotPart()) {
            for (int i = 0; i < this.rotPartRotation.length; ++i) {
                this.prevRotPartRotation[i] = this.rotPartRotation[i];
                if ((!this.isDestroyed() && this.getAcInfo().partRotPart.get(i).rotAlways) || this.getRiddenByEntity() != null) {
                    final float[] rotPartRotation = this.rotPartRotation;
                    final int n = i;
                    rotPartRotation[n] += this.getAcInfo().partRotPart.get(i).rotSpeed;
                    if (this.rotPartRotation[i] < 0.0f) {
                        final float[] rotPartRotation2 = this.rotPartRotation;
                        final int n2 = i;
                        rotPartRotation2[n2] += 360.0f;
                    }
                    if (this.rotPartRotation[i] >= 360.0f) {
                        final float[] rotPartRotation3 = this.rotPartRotation;
                        final int n3 = i;
                        rotPartRotation3[n3] -= 360.0f;
                    }
                }
            }
        }
    }
    
    public void onRideEntity(final Entity ridingEntity) {
    }
    
    public int getAlt(final double px, final double py, final double pz) {
        int i;
        for (i = 0; i < 256; ++i) {
            if (py - i <= 0.0) {
                break;
            }
            if (py - i < 256.0) {
                if (0 != W_WorldFunc.getBlockId(this.worldObj, (int)px, (int)py - i, (int)pz)) {
                    break;
                }
            }
        }
        return i;
    }
    
    public boolean canRepelling(final Entity entity) {
        return this.isRepelling() && this.tickRepelling > 50;
    }
    
    private void onUpdate_Repelling() {
        if (this.getAcInfo() != null && this.getAcInfo().haveRepellingHook()) {
            if (this.isRepelling()) {
                final int alt = this.getAlt(this.posX, this.posY, this.posZ);
                if (this.ropesLength > -50.0f && this.ropesLength > -alt) {
                    this.ropesLength -= (float)(this.worldObj.isRemote ? 0.30000001192092896 : 0.25);
                }
            }
            else {
                this.ropesLength = 0.0f;
            }
        }
        this.onUpdate_UnmountCrewRepelling();
    }
    
    private void onUpdate_UnmountCrewRepelling() {
        if (this.getAcInfo() == null) {
            return;
        }
        if (!this.isRepelling()) {
            this.tickRepelling = 0;
            return;
        }
        if (this.tickRepelling < 60) {
            ++this.tickRepelling;
            return;
        }
        if (this.worldObj.isRemote) {
            return;
        }
        for (int ropeIdx = 0; ropeIdx < this.getAcInfo().repellingHooks.size(); ++ropeIdx) {
            final MCH_AircraftInfo.RepellingHook hook = this.getAcInfo().repellingHooks.get(ropeIdx);
            if (this.getCountOnUpdate() % hook.interval == 0) {
                for (int i = 1; i < this.getSeatNum(); ++i) {
                    final MCH_EntitySeat seat = this.getSeat(i);
                    if (seat != null && seat.riddenByEntity != null && !W_EntityPlayer.isPlayer(seat.riddenByEntity) && !(seat.riddenByEntity instanceof MCH_EntityGunner) && !(this.getSeatInfo(i + 1) instanceof MCH_SeatRackInfo)) {
                        final Entity entity = seat.riddenByEntity;
                        final Vec3 dropPos = this.getTransformedPosition(hook.pos, this.prevPosition.oldest());
                        seat.posX = dropPos.xCoord;
                        seat.posY = dropPos.yCoord - 2.0;
                        seat.posZ = dropPos.zCoord;
                        entity.mountEntity((Entity)null);
                        this.unmountEntityRepelling(entity, dropPos, ropeIdx);
                        this.lastUsedRopeIndex = ropeIdx;
                        break;
                    }
                }
            }
        }
    }
    
    public void unmountEntityRepelling(final Entity entity, final Vec3 dropPos, final int ropeIdx) {
        entity.posX = dropPos.xCoord;
        entity.posY = dropPos.yCoord - 2.0;
        entity.posZ = dropPos.zCoord;
        final MCH_EntityHide hideEntity = new MCH_EntityHide(this.worldObj, entity.posX, entity.posY, entity.posZ);
        hideEntity.setParent(this, entity, ropeIdx);
        final MCH_EntityHide mch_EntityHide = hideEntity;
        final double n = 0.0;
        entity.motionX = n;
        mch_EntityHide.motionX = n;
        final MCH_EntityHide mch_EntityHide2 = hideEntity;
        final double n2 = 0.0;
        entity.motionY = n2;
        mch_EntityHide2.motionY = n2;
        final MCH_EntityHide mch_EntityHide3 = hideEntity;
        final double n3 = 0.0;
        entity.motionZ = n3;
        mch_EntityHide3.motionZ = n3;
        final MCH_EntityHide mch_EntityHide4 = hideEntity;
        final float n4 = 0.0f;
        entity.fallDistance = n4;
        mch_EntityHide4.fallDistance = n4;
        this.worldObj.spawnEntityInWorld((Entity)hideEntity);
    }
    
    private void onUpdate_UnmountCrew() {
        if (this.getAcInfo() == null) {
            return;
        }
        if (this.isParachuting) {
            if (MCH_Lib.getBlockIdY(this, 3, -10) != 0) {
                this.stopUnmountCrew();
            }
            else if ((!this.haveHatch() || this.getHatchRotation() > 89.0f) && this.getCountOnUpdate() % this.getAcInfo().mobDropOption.interval == 0 && !this.unmountCrew(true)) {
                this.stopUnmountCrew();
            }
        }
    }
    
    public void unmountAircraft() {
        Vec3 v = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
        if (this.ridingEntity instanceof MCH_EntitySeat) {
            final MCH_EntityAircraft ac = ((MCH_EntitySeat)this.ridingEntity).getParent();
            final MCH_SeatInfo seatInfo = ac.getSeatInfo(this);
            if (seatInfo instanceof MCH_SeatRackInfo) {
                v = ((MCH_SeatRackInfo)seatInfo).getEntryPos();
                v = ac.getTransformedPosition(v);
            }
        }
        else if (this.ridingEntity instanceof EntityMinecartEmpty) {
            this.dismountedUserCtrl = true;
        }
        this.setLocationAndAngles(v.xCoord, v.yCoord, v.zCoord, this.getRotYaw(), this.getRotPitch());
        this.mountEntity((Entity)null);
        this.setLocationAndAngles(v.xCoord, v.yCoord, v.zCoord, this.getRotYaw(), this.getRotPitch());
    }
    
    public boolean canUnmount(final Entity entity) {
        return this.getAcInfo() != null && this.getAcInfo().isEnableParachuting && this.getSeatIdByEntity(entity) > 1 && (!this.haveHatch() || this.getHatchRotation() >= 89.0f);
    }
    
    public void unmount(final Entity entity) {
        if (this.getAcInfo() == null) {
            return;
        }
        if (this.canRepelling(entity) && this.getAcInfo().haveRepellingHook()) {
            final MCH_EntitySeat seat = this.getSeatByEntity(entity);
            if (seat != null) {
                this.lastUsedRopeIndex = (this.lastUsedRopeIndex + 1) % this.getAcInfo().repellingHooks.size();
                Vec3 dropPos = this.getTransformedPosition(this.getAcInfo().repellingHooks.get(this.lastUsedRopeIndex).pos, this.prevPosition.oldest());
                dropPos = dropPos.addVector(0.0, -2.0, 0.0);
                seat.posX = dropPos.xCoord;
                seat.posY = dropPos.yCoord;
                seat.posZ = dropPos.zCoord;
                entity.mountEntity((Entity)null);
                entity.posX = dropPos.xCoord;
                entity.posY = dropPos.yCoord;
                entity.posZ = dropPos.zCoord;
                this.unmountEntityRepelling(entity, dropPos, this.lastUsedRopeIndex);
            }
            else {
                MCH_Lib.Log(this, "Error:MCH_EntityAircraft.unmount seat=null : " + entity, new Object[0]);
            }
        }
        else if (this.canUnmount(entity)) {
            final MCH_EntitySeat seat = this.getSeatByEntity(entity);
            if (seat != null) {
                final Vec3 dropPos = this.getTransformedPosition(this.getAcInfo().mobDropOption.pos, this.prevPosition.oldest());
                seat.posX = dropPos.xCoord;
                seat.posY = dropPos.yCoord;
                seat.posZ = dropPos.zCoord;
                entity.mountEntity((Entity)null);
                entity.posX = dropPos.xCoord;
                entity.posY = dropPos.yCoord;
                entity.posZ = dropPos.zCoord;
                this.dropEntityParachute(entity);
            }
            else {
                MCH_Lib.Log(this, "Error:MCH_EntityAircraft.unmount seat=null : " + entity, new Object[0]);
            }
        }
    }
    
    public boolean canParachuting(final Entity entity) {
        if (this.getAcInfo() == null || !this.getAcInfo().isEnableParachuting || this.getSeatIdByEntity(entity) <= 1 || MCH_Lib.getBlockIdY(this, 3, -13) != 0) {
            return false;
        }
        if (this.haveHatch() && this.getHatchRotation() > 89.0f) {
            return this.getSeatIdByEntity(entity) > 1;
        }
        return this.getSeatIdByEntity(entity) > 1;
    }
    
    public void onUpdate_RidingEntity() {
        if (!this.worldObj.isRemote && this.waitMountEntity == 0 && this.getCountOnUpdate() > 20 && this.canMountWithNearEmptyMinecart()) {
            this.mountWithNearEmptyMinecart();
        }
        if (this.waitMountEntity > 0) {
            --this.waitMountEntity;
        }
        if (!this.worldObj.isRemote && this.getRidingEntity() != null) {
            this.setRotRoll(this.getRotRoll() * 0.9f);
            this.setRotPitch(this.getRotPitch() * 0.95f);
            final Entity re = this.getRidingEntity();
            float target = MathHelper.wrapAngleTo180_float(re.rotationYaw + 90.0f);
            if (target - this.rotationYaw > 180.0f) {
                target -= 360.0f;
            }
            if (target - this.rotationYaw < -180.0f) {
                target += 360.0f;
            }
            if (this.ticksExisted % 2 == 0) {}
            float dist = 50.0f * (float)re.getDistanceSq(re.prevPosX, re.prevPosY, re.prevPosZ);
            if (dist > 0.001) {
                dist = MathHelper.sqrt_double((double)dist);
                final float distYaw = MCH_Lib.RNG(target - this.rotationYaw, -dist, dist);
                this.rotationYaw += distYaw;
            }
            final double bkPosX = this.posX;
            final double bkPosY = this.posY;
            final double bkPosZ = this.posZ;
            if (this.getRidingEntity().isDead) {
                this.mountEntity((Entity)null);
                this.waitMountEntity = 20;
            }
            else if (this.getCurrentThrottle() > 0.8) {
                this.motionX = this.getRidingEntity().motionX;
                this.motionY = this.getRidingEntity().motionY;
                this.motionZ = this.getRidingEntity().motionZ;
                this.mountEntity((Entity)null);
                this.waitMountEntity = 20;
            }
            this.posX = bkPosX;
            this.posY = bkPosY;
            this.posZ = bkPosZ;
        }
    }
    
    public void explosionByCrash(final double prevMotionY) {
        float exp = (this.getAcInfo() != null) ? (this.getAcInfo().maxFuel / 400.0f) : 2.0f;
        if (exp < 1.0f) {
            exp = 1.0f;
        }
        if (exp > 15.0f) {
            exp = 15.0f;
        }
        MCH_Lib.DbgLog(this.worldObj, "OnGroundAfterDestroyed:motionY=%.3f", (float)prevMotionY);
        MCH_Explosion.newExplosion(this.worldObj, null, null, this.posX, this.posY, this.posZ, exp, (exp >= 2.0f) ? (exp * 0.5f) : 1.0f, true, true, true, true, 5);
    }
    
    public void onUpdate_CollisionGroundDamage() {
        if (this.isDestroyed()) {
            return;
        }
        if (MCH_Lib.getBlockIdY(this, 3, -3) > 0 && !this.worldObj.isRemote) {
            final float roll = MathHelper.abs(MathHelper.wrapAngleTo180_float(this.getRotRoll()));
            final float pitch = MathHelper.abs(MathHelper.wrapAngleTo180_float(this.getRotPitch()));
            if (roll > this.getGiveDamageRot() || pitch > this.getGiveDamageRot()) {
                float dmg = MathHelper.abs(roll) + MathHelper.abs(pitch);
                if (dmg < 90.0f) {
                    dmg *= 0.4f * (float)this.getDistance(this.prevPosX, this.prevPosY, this.prevPosZ);
                }
                else {
                    dmg *= 0.4f;
                }
                if (dmg > 1.0f && this.rand.nextInt(4) == 0) {
                    this.attackEntityFrom(DamageSource.inWall, dmg);
                }
            }
        }
        if (this.getCountOnUpdate() % 30 == 0 && (this.getAcInfo() == null || !this.getAcInfo().isFloat) && MCH_Lib.isBlockInWater(this.worldObj, (int)(this.posX + 0.5), (int)(this.posY + 1.5 + this.getAcInfo().submergedDamageHeight), (int)(this.posZ + 0.5))) {
            int hp = this.getMaxHP() / 10;
            if (hp <= 0) {
                hp = 1;
            }
            this.attackEntityFrom(DamageSource.inWall, hp);
        }
    }
    
    public float getGiveDamageRot() {
        return 40.0f;
    }
    
    public void applyServerPositionAndRotation() {
        final double rpinc = this.aircraftPosRotInc;
        final double yaw = MathHelper.wrapAngleTo180_double(this.aircraftYaw - this.getRotYaw());
        final double roll = MathHelper.wrapAngleTo180_double(this.getServerRoll() - (double)this.getRotRoll());
        if (!this.isDestroyed() && (!W_Lib.isClientPlayer(this.getRiddenByEntity()) || this.getRidingEntity() != null)) {
            this.setRotYaw((float)(this.getRotYaw() + yaw / rpinc));
            this.setRotPitch((float)(this.getRotPitch() + (this.aircraftPitch - this.getRotPitch()) / rpinc));
            this.setRotRoll((float)(this.getRotRoll() + roll / rpinc));
        }
        this.setPosition(this.posX + (this.aircraftX - this.posX) / rpinc, this.posY + (this.aircraftY - this.posY) / rpinc, this.posZ + (this.aircraftZ - this.posZ) / rpinc);
        this.setRotation(this.getRotYaw(), this.getRotPitch());
        --this.aircraftPosRotInc;
    }
    
    protected void autoRepair() {
        if (this.timeSinceHit > 0) {
            --this.timeSinceHit;
        }
        if (this.getMaxHP() <= 0) {
            return;
        }
        if (!this.isDestroyed()) {
            if (this.getDamageTaken() > this.beforeDamageTaken) {
                this.repairCount = 600;
            }
            else if (this.repairCount > 0) {
                --this.repairCount;
            }
            else {
                this.repairCount = 40;
                final double n;
                final double hpp = n = this.getHP() / (double)this.getMaxHP();
                final MCH_Config config = MCH_MOD.config;
                if (n >= MCH_Config.AutoRepairHP.prmDouble) {
                    this.repair(this.getMaxHP() / 100);
                }
            }
        }
        this.beforeDamageTaken = this.getDamageTaken();
    }
    
    public boolean repair(int tpd) {
        if (tpd < 1) {
            tpd = 1;
        }
        final int damage = this.getDamageTaken();
        if (damage > 0) {
            if (!this.worldObj.isRemote) {
                this.setDamageTaken(damage - tpd);
            }
            return true;
        }
        return false;
    }
    
    public void repairOtherAircraft() {
        final float range = (this.getAcInfo() != null) ? this.getAcInfo().repairOtherVehiclesRange : 0.0f;
        if (range <= 0.0f) {
            return;
        }
        if (!this.worldObj.isRemote && this.getCountOnUpdate() % 20 == 0) {
            final List list = this.worldObj.getEntitiesWithinAABB((Class)MCH_EntityAircraft.class, this.getBoundingBox().expand((double)range, (double)range, (double)range));
            for (int i = 0; i < list.size(); ++i) {
                final MCH_EntityAircraft ac = list.get(i);
                if (!W_Entity.isEqual(this, ac)) {
                    if (ac.getHP() < ac.getMaxHP()) {
                        ac.setDamageTaken(ac.getDamageTaken() - this.getAcInfo().repairOtherVehiclesValue);
                    }
                }
            }
        }
    }
    
    protected void regenerationMob() {
        if (this.isDestroyed()) {
            return;
        }
        if (this.worldObj.isRemote) {
            return;
        }
        if (this.getAcInfo() != null && this.getAcInfo().regeneration && this.getRiddenByEntity() != null) {
            final MCH_EntitySeat[] arr$;
            final MCH_EntitySeat[] st = arr$ = this.getSeats();
            for (final MCH_EntitySeat s : arr$) {
                if (s != null && !s.isDead) {
                    final Entity e = s.riddenByEntity;
                    if (W_Lib.isEntityLivingBase(e) && !e.isDead) {
                        final PotionEffect pe = W_Entity.getActivePotionEffect(e, Potion.regeneration);
                        if (pe == null || (pe != null && pe.getDuration() < 500)) {
                            W_Entity.addPotionEffect(e, new PotionEffect(Potion.regeneration.id, 250, 0, true));
                        }
                    }
                }
            }
        }
    }
    
    public double getWaterDepth() {
        final byte b0 = 5;
        double d0 = 0.0;
        for (int i = 0; i < b0; ++i) {
            double d2 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (i + 0) / b0 - 0.125;
            double d3 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (i + 1) / b0 - 0.125;
            d2 += this.getAcInfo().floatOffset;
            d3 += this.getAcInfo().floatOffset;
            final AxisAlignedBB axisalignedbb = W_AxisAlignedBB.getAABB(this.boundingBox.minX, d2, this.boundingBox.minZ, this.boundingBox.maxX, d3, this.boundingBox.maxZ);
            if (this.worldObj.isAABBInMaterial(axisalignedbb, Material.water)) {
                d0 += 1.0 / b0;
            }
        }
        return d0;
    }
    
    public int getCountOnUpdate() {
        return this.countOnUpdate;
    }
    
    public boolean canSupply() {
        if (!this.canFloatWater()) {
            return MCH_Lib.getBlockIdY(this, 1, -3) != 0 && !this.isInWater();
        }
        return MCH_Lib.getBlockIdY(this, 1, -3) != 0;
    }
    
    public void setFuel(int fuel) {
        if (!this.worldObj.isRemote) {
            if (fuel < 0) {
                fuel = 0;
            }
            if (fuel > this.getMaxFuel()) {
                fuel = this.getMaxFuel();
            }
            if (fuel != this.getFuel()) {
                this.getDataWatcher().updateObject(25, (Object)fuel);
            }
        }
    }
    
    public int getFuel() {
        return this.getDataWatcher().getWatchableObjectInt(25);
    }
    
    public float getFuelP() {
        final int m = this.getMaxFuel();
        if (m == 0) {
            return 0.0f;
        }
        return this.getFuel() / (float)m;
    }
    
    public boolean canUseFuel(final boolean checkOtherSeet) {
        return this.getMaxFuel() <= 0 || this.getFuel() > 1 || this.isInfinityFuel(this.getRiddenByEntity(), checkOtherSeet);
    }
    
    public boolean canUseFuel() {
        return this.canUseFuel(false);
    }
    
    public int getMaxFuel() {
        return (this.getAcInfo() != null) ? this.getAcInfo().maxFuel : 0;
    }
    
    public void supplyFuel() {
        final float range = (this.getAcInfo() != null) ? this.getAcInfo().fuelSupplyRange : 0.0f;
        if (range <= 0.0f) {
            return;
        }
        if (!this.worldObj.isRemote && this.getCountOnUpdate() % 10 == 0) {
            final List list = this.worldObj.getEntitiesWithinAABB((Class)MCH_EntityAircraft.class, this.getBoundingBox().expand((double)range, (double)range, (double)range));
            for (int i = 0; i < list.size(); ++i) {
                final MCH_EntityAircraft ac = list.get(i);
                if (!W_Entity.isEqual(this, ac)) {
                    if ((!this.onGround || ac.canSupply()) && ac.getFuel() < ac.getMaxFuel()) {
                        int fc = ac.getMaxFuel() - ac.getFuel();
                        if (fc > 30) {
                            fc = 30;
                        }
                        ac.setFuel(ac.getFuel() + fc);
                    }
                    ac.fuelSuppliedCount = 40;
                }
            }
        }
    }
    
    public void updateFuel() {
        if (this.getMaxFuel() == 0) {
            return;
        }
        if (this.fuelSuppliedCount > 0) {
            --this.fuelSuppliedCount;
        }
        if (!this.isDestroyed() && !this.worldObj.isRemote) {
            if (this.getCountOnUpdate() % 20 == 0 && this.getFuel() > 1 && this.getThrottle() > 0.0 && this.fuelSuppliedCount <= 0) {
                double t = this.getThrottle() * 1.4;
                if (t > 1.0) {
                    t = 1.0;
                }
                this.fuelConsumption += t * this.getAcInfo().fuelConsumption * this.getFuelConsumptionFactor();
                if (this.fuelConsumption > 1.0) {
                    final int f = (int)this.fuelConsumption;
                    this.fuelConsumption -= f;
                    this.setFuel(this.getFuel() - f);
                }
            }
            int curFuel = this.getFuel();
            if (this.canSupply() && this.getCountOnUpdate() % 10 == 0 && curFuel < this.getMaxFuel()) {
                for (int i = 0; i < 3; ++i) {
                    if (curFuel < this.getMaxFuel()) {
                        final ItemStack fuel = this.getGuiInventory().getFuelSlotItemStack(i);
                        if (fuel != null && fuel.getItem() instanceof MCH_ItemFuel && fuel.getMetadata() < fuel.getMaxDurability()) {
                            int fc = this.getMaxFuel() - curFuel;
                            if (fc > 100) {
                                fc = 100;
                            }
                            if (fuel.getMetadata() > fuel.getMaxDurability() - fc) {
                                fc = fuel.getMaxDurability() - fuel.getMetadata();
                            }
                            fuel.setMetadata(fuel.getMetadata() + fc);
                            curFuel += fc;
                        }
                    }
                }
                if (this.getFuel() != curFuel) {
                    MCH_Achievement.addStat(this.riddenByEntity, MCH_Achievement.supplyFuel, 1);
                }
                this.setFuel(curFuel);
            }
        }
    }
    
    public float getFuelConsumptionFactor() {
        return 1.0f;
    }
    
    public void updateSupplyAmmo() {
        if (!this.worldObj.isRemote) {
            boolean isReloading = false;
            if (this.getRiddenByEntity() instanceof EntityPlayer && !this.getRiddenByEntity().isDead && ((EntityPlayer)this.getRiddenByEntity()).openContainer instanceof MCH_AircraftGuiContainer) {
                isReloading = true;
            }
            this.setCommonStatus(2, isReloading);
            if (!this.isDestroyed() && this.beforeSupplyAmmo && !isReloading) {
                this.reloadAllWeapon();
                MCH_PacketNotifyAmmoNum.sendAllAmmoNum(this, null);
            }
            this.beforeSupplyAmmo = isReloading;
        }
        if (this.getCommonStatus(2)) {
            this.supplyAmmoWait = 20;
        }
        if (this.supplyAmmoWait > 0) {
            --this.supplyAmmoWait;
        }
    }
    
    public void supplyAmmo(final int weaponID) {
        if (this.worldObj.isRemote) {
            final MCH_WeaponSet ws = this.getWeapon(weaponID);
            ws.supplyRestAllAmmo();
        }
        else {
            MCH_Achievement.addStat(this.riddenByEntity, MCH_Achievement.supplyAmmo, 1);
            if (this.getRiddenByEntity() instanceof EntityPlayer) {
                final EntityPlayer player = (EntityPlayer)this.getRiddenByEntity();
                if (this.canPlayerSupplyAmmo(player, weaponID)) {
                    final MCH_WeaponSet ws2 = this.getWeapon(weaponID);
                    for (final MCH_WeaponInfo.RoundItem ri : ws2.getInfo().roundItems) {
                        int num = ri.num;
                        for (int i = 0; i < player.inventory.mainInventory.length; ++i) {
                            final ItemStack itemStack = player.inventory.mainInventory[i];
                            if (itemStack != null && itemStack.isItemEqual(ri.itemStack)) {
                                if (itemStack.getItem() == W_Item.getItemByName("water_bucket") || itemStack.getItem() == W_Item.getItemByName("lava_bucket")) {
                                    if (itemStack.stackSize == 1) {
                                        player.inventory.setInventorySlotContents(i, new ItemStack(W_Item.getItemByName("bucket"), 1));
                                        --num;
                                    }
                                }
                                else if (itemStack.stackSize > num) {
                                    final ItemStack itemStack2 = itemStack;
                                    itemStack2.stackSize -= num;
                                    num = 0;
                                }
                                else {
                                    num -= itemStack.stackSize;
                                    itemStack.stackSize = 0;
                                    player.inventory.mainInventory[i] = null;
                                }
                            }
                            if (num <= 0) {
                                break;
                            }
                        }
                    }
                    ws2.supplyRestAllAmmo();
                }
            }
        }
    }
    
    public void supplyAmmoToOtherAircraft() {
        final float range = (this.getAcInfo() != null) ? this.getAcInfo().ammoSupplyRange : 0.0f;
        if (range <= 0.0f) {
            return;
        }
        if (!this.worldObj.isRemote && this.getCountOnUpdate() % 40 == 0) {
            final List list = this.worldObj.getEntitiesWithinAABB((Class)MCH_EntityAircraft.class, this.getBoundingBox().expand((double)range, (double)range, (double)range));
            for (int i = 0; i < list.size(); ++i) {
                final MCH_EntityAircraft ac = list.get(i);
                if (!W_Entity.isEqual(this, ac)) {
                    if (ac.canSupply()) {
                        for (int wid = 0; wid < ac.getWeaponNum(); ++wid) {
                            final MCH_WeaponSet ws = ac.getWeapon(wid);
                            final int num = ws.getRestAllAmmoNum() + ws.getAmmoNum();
                            if (num < ws.getAllAmmoNum()) {
                                int ammo = ws.getAllAmmoNum() / 10;
                                if (ammo < 1) {
                                    ammo = 1;
                                }
                                ws.setRestAllAmmoNum(num + ammo);
                                final EntityPlayer player = ac.getEntityByWeaponId(wid);
                                if (num != ws.getRestAllAmmoNum() + ws.getAmmoNum()) {
                                    if (ws.getAmmoNum() <= 0) {
                                        ws.reloadMag();
                                    }
                                    MCH_PacketNotifyAmmoNum.sendAmmoNum(ac, player, wid);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    public boolean canPlayerSupplyAmmo(final EntityPlayer player, final int weaponId) {
        if (MCH_Lib.getBlockIdY(this, 1, -3) == 0) {
            return false;
        }
        if (!this.canSupply()) {
            return false;
        }
        final MCH_WeaponSet ws = this.getWeapon(weaponId);
        if (ws.getRestAllAmmoNum() + ws.getAmmoNum() >= ws.getAllAmmoNum()) {
            return false;
        }
        for (final MCH_WeaponInfo.RoundItem ri : ws.getInfo().roundItems) {
            int num = ri.num;
            for (final ItemStack itemStack : player.inventory.mainInventory) {
                if (itemStack != null && itemStack.isItemEqual(ri.itemStack)) {
                    num -= itemStack.stackSize;
                }
                if (num <= 0) {
                    break;
                }
            }
            if (num > 0) {
                return false;
            }
        }
        return true;
    }
    
    public MCH_EntityAircraft setTextureName(final String name) {
        if (name != null) {
            if (!name.isEmpty()) {
                this.getDataWatcher().updateObject(21, (Object)String.valueOf(name));
            }
        }
        return this;
    }
    
    public String getTextureName() {
        return this.getDataWatcher().getWatchableObjectString(21);
    }
    
    public void switchNextTextureName() {
        if (this.getAcInfo() != null) {
            this.setTextureName(this.getAcInfo().getNextTextureName(this.getTextureName()));
        }
    }
    
    public void zoomCamera() {
        if (this.canZoom()) {
            float z = this.camera.getCameraZoom();
            if (z >= this.getZoomMax() - 0.01) {
                z = 1.0f;
            }
            else {
                z *= 2.0f;
                if (z >= this.getZoomMax()) {
                    z = (float)this.getZoomMax();
                }
            }
            this.camera.setCameraZoom((z <= this.getZoomMax() + 0.01) ? z : 1.0f);
        }
    }
    
    public int getZoomMax() {
        return (this.getAcInfo() != null) ? this.getAcInfo().cameraZoom : 1;
    }
    
    public boolean canZoom() {
        return this.getZoomMax() > 1;
    }
    
    public boolean canSwitchCameraMode() {
        return !this.isDestroyed() && this.getAcInfo() != null && this.getAcInfo().isEnableNightVision;
    }
    
    public boolean canSwitchCameraMode(final int seatID) {
        return !this.isDestroyed() && this.canSwitchCameraMode() && this.camera.isValidUid(seatID);
    }
    
    public int getCameraMode(final EntityPlayer player) {
        return this.camera.getMode(this.getSeatIdByEntity((Entity)player));
    }
    
    public String getCameraModeName(final EntityPlayer player) {
        return this.camera.getModeName(this.getSeatIdByEntity((Entity)player));
    }
    
    public void switchCameraMode(final EntityPlayer player) {
        this.switchCameraMode(player, this.camera.getMode(this.getSeatIdByEntity((Entity)player)) + 1);
    }
    
    public void switchCameraMode(final EntityPlayer player, final int mode) {
        this.camera.setMode(this.getSeatIdByEntity((Entity)player), mode);
    }
    
    public void updateCameraViewers() {
        for (int i = 0; i < this.getSeatNum() + 1; ++i) {
            this.camera.updateViewer(i, this.getEntityBySeatId(i));
        }
    }
    
    public void updateRadar(final int radarSpeed) {
        if (this.isEntityRadarMounted()) {
            this.radarRotate += radarSpeed;
            if (this.radarRotate >= 360) {
                this.radarRotate = 0;
            }
            if (this.radarRotate == 0) {
                this.entityRadar.updateXZ(this, 64);
            }
        }
    }
    
    public int getRadarRotate() {
        return this.radarRotate;
    }
    
    public void initRadar() {
        this.entityRadar.clear();
        this.radarRotate = 0;
    }
    
    public ArrayList<MCH_Vector2> getRadarEntityList() {
        return this.entityRadar.getEntityList();
    }
    
    public ArrayList<MCH_Vector2> getRadarEnemyList() {
        return this.entityRadar.getEnemyList();
    }
    
    public void moveEntity(double par1, double par3, double par5) {
        if (this.getAcInfo() == null) {
            return;
        }
        this.worldObj.theProfiler.startSection("move");
        this.yOffset2 *= 0.4f;
        final double d3 = this.posX;
        final double d4 = this.posY;
        final double d5 = this.posZ;
        final double d6 = par1;
        final double d7 = par3;
        final double d8 = par5;
        final AxisAlignedBB axisalignedbb = this.boundingBox.copy();
        List list = getCollidingBoundingBoxes(this, this.boundingBox.addCoord(par1, par3, par5));
        for (int i = 0; i < list.size(); ++i) {
            par3 = list.get(i).calculateYOffset(this.boundingBox, par3);
        }
        this.boundingBox.offset(0.0, par3, 0.0);
        if (!this.field_70135_K && d7 != par3) {
            par5 = 0.0;
            par3 = 0.0;
            par1 = 0.0;
        }
        final boolean flag1 = this.onGround || (d7 != par3 && d7 < 0.0);
        for (int j = 0; j < list.size(); ++j) {
            par1 = list.get(j).calculateXOffset(this.boundingBox, par1);
        }
        this.boundingBox.offset(par1, 0.0, 0.0);
        if (!this.field_70135_K && d6 != par1) {
            par5 = 0.0;
            par3 = 0.0;
            par1 = 0.0;
        }
        for (int j = 0; j < list.size(); ++j) {
            par5 = list.get(j).calculateZOffset(this.boundingBox, par5);
        }
        this.boundingBox.offset(0.0, 0.0, par5);
        if (!this.field_70135_K && d8 != par5) {
            par5 = 0.0;
            par3 = 0.0;
            par1 = 0.0;
        }
        if (this.stepHeight > 0.0f && flag1 && this.yOffset2 < 0.05f && (d6 != par1 || d8 != par5)) {
            final double d9 = par1;
            final double d10 = par3;
            final double d11 = par5;
            par1 = d6;
            par3 = this.stepHeight;
            par5 = d8;
            final AxisAlignedBB axisalignedbb2 = this.boundingBox.copy();
            this.boundingBox.setBB(axisalignedbb);
            list = getCollidingBoundingBoxes(this, this.boundingBox.addCoord(d6, par3, d8));
            for (int k = 0; k < list.size(); ++k) {
                par3 = list.get(k).calculateYOffset(this.boundingBox, par3);
            }
            this.boundingBox.offset(0.0, par3, 0.0);
            if (!this.field_70135_K && d7 != par3) {
                par5 = 0.0;
                par3 = 0.0;
                par1 = 0.0;
            }
            for (int k = 0; k < list.size(); ++k) {
                par1 = list.get(k).calculateXOffset(this.boundingBox, par1);
            }
            this.boundingBox.offset(par1, 0.0, 0.0);
            if (!this.field_70135_K && d6 != par1) {
                par5 = 0.0;
                par3 = 0.0;
                par1 = 0.0;
            }
            for (int k = 0; k < list.size(); ++k) {
                par5 = list.get(k).calculateZOffset(this.boundingBox, par5);
            }
            this.boundingBox.offset(0.0, 0.0, par5);
            if (!this.field_70135_K && d8 != par5) {
                par5 = 0.0;
                par3 = 0.0;
                par1 = 0.0;
            }
            if (!this.field_70135_K && d7 != par3) {
                par5 = 0.0;
                par3 = 0.0;
                par1 = 0.0;
            }
            else {
                par3 = -this.stepHeight;
                for (int k = 0; k < list.size(); ++k) {
                    par3 = list.get(k).calculateYOffset(this.boundingBox, par3);
                }
                this.boundingBox.offset(0.0, par3, 0.0);
            }
            if (d9 * d9 + d11 * d11 >= par1 * par1 + par5 * par5) {
                par1 = d9;
                par3 = d10;
                par5 = d11;
                this.boundingBox.setBB(axisalignedbb2);
            }
        }
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("rest");
        this.posX = (this.boundingBox.minX + this.boundingBox.maxX) / 2.0;
        this.posY = this.boundingBox.minY + this.yOffset - this.yOffset2;
        this.posZ = (this.boundingBox.minZ + this.boundingBox.maxZ) / 2.0;
        this.isCollidedHorizontally = (d6 != par1 || d8 != par5);
        this.isCollidedVertically = (d7 != par3);
        this.onGround = (d7 != par3 && d7 < 0.0);
        this.isCollided = (this.isCollidedHorizontally || this.isCollidedVertically);
        this.updateFallState(par3, this.onGround);
        if (d6 != par1) {
            this.motionX = 0.0;
        }
        if (d7 != par3) {
            this.motionY = 0.0;
        }
        if (d8 != par5) {
            this.motionZ = 0.0;
        }
        final double d9 = this.posX - d3;
        final double d10 = this.posY - d4;
        final double d11 = this.posZ - d5;
        try {
            this.doBlockCollisions();
        }
        catch (Throwable throwable) {
            final CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Checking entity tile collision");
            final CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being checked for collision");
            this.addEntityCrashInfo(crashreportcategory);
            throw new ReportedException(crashreport);
        }
        this.worldObj.theProfiler.endSection();
    }
    
    public static List getCollidingBoundingBoxes(final Entity par1Entity, final AxisAlignedBB par2AxisAlignedBB) {
        final ArrayList collidingBoundingBoxes = new ArrayList();
        collidingBoundingBoxes.clear();
        final int i = MathHelper.floor_double(par2AxisAlignedBB.minX);
        final int j = MathHelper.floor_double(par2AxisAlignedBB.maxX + 1.0);
        final int k = MathHelper.floor_double(par2AxisAlignedBB.minY);
        final int l = MathHelper.floor_double(par2AxisAlignedBB.maxY + 1.0);
        final int i2 = MathHelper.floor_double(par2AxisAlignedBB.minZ);
        final int j2 = MathHelper.floor_double(par2AxisAlignedBB.maxZ + 1.0);
        for (int k2 = i; k2 < j; ++k2) {
            for (int l2 = i2; l2 < j2; ++l2) {
                if (par1Entity.worldObj.blockExists(k2, 64, l2)) {
                    for (int i3 = k - 1; i3 < l; ++i3) {
                        final Block block = W_WorldFunc.getBlock(par1Entity.worldObj, k2, i3, l2);
                        if (block != null) {
                            block.addCollisionBoxesToList(par1Entity.worldObj, k2, i3, l2, par2AxisAlignedBB, (List)collidingBoundingBoxes, par1Entity);
                        }
                    }
                }
            }
        }
        final double d0 = 0.25;
        final List list = par1Entity.worldObj.getEntitiesWithinAABBExcludingEntity(par1Entity, par2AxisAlignedBB.expand(d0, d0, d0));
        for (int j3 = 0; j3 < list.size(); ++j3) {
            final Entity entity = list.get(j3);
            if (!W_Lib.isEntityLivingBase(entity)) {
                if (!(entity instanceof MCH_EntitySeat)) {
                    if (!(entity instanceof MCH_EntityHitBox)) {
                        AxisAlignedBB axisalignedbb1 = entity.getBoundingBox();
                        if (axisalignedbb1 != null && axisalignedbb1.intersectsWith(par2AxisAlignedBB)) {
                            collidingBoundingBoxes.add(axisalignedbb1);
                        }
                        axisalignedbb1 = par1Entity.getCollisionBox(entity);
                        if (axisalignedbb1 != null && axisalignedbb1.intersectsWith(par2AxisAlignedBB)) {
                            collidingBoundingBoxes.add(axisalignedbb1);
                        }
                    }
                }
            }
        }
        return collidingBoundingBoxes;
    }
    
    protected void onUpdate_updateBlock() {
        final MCH_Config config = MCH_MOD.config;
        if (!MCH_Config.Collision_DestroyBlock.prmBool) {
            return;
        }
        for (int l = 0; l < 4; ++l) {
            final int i1 = MathHelper.floor_double(this.posX + (l % 2 - 0.5) * 0.8);
            final int j1 = MathHelper.floor_double(this.posZ + (l / 2 - 0.5) * 0.8);
            for (int k1 = 0; k1 < 2; ++k1) {
                final int l2 = MathHelper.floor_double(this.posY) + k1;
                final Block block = W_WorldFunc.getBlock(this.worldObj, i1, l2, j1);
                if (!W_Block.isNull(block)) {
                    if (block == W_Block.getSnowLayer()) {
                        this.worldObj.setBlockToAir(i1, l2, j1);
                    }
                    if (block == W_Blocks.waterlily || block == W_Blocks.cake) {
                        W_WorldFunc.destroyBlock(this.worldObj, i1, l2, j1, false);
                    }
                }
            }
        }
    }
    
    public void onUpdate_ParticleSmoke() {
        if (!this.worldObj.isRemote) {
            return;
        }
        if (this.getCurrentThrottle() <= 0.10000000149011612) {
            return;
        }
        final float yaw = this.getRotYaw();
        final float pitch = this.getRotPitch();
        final float roll = this.getRotRoll();
        final MCH_WeaponSet ws = this.getCurrentWeapon(this.getRiddenByEntity());
        if (!(ws.getFirstWeapon() instanceof MCH_WeaponSmoke)) {
            return;
        }
        for (int i = 0; i < ws.getWeaponNum(); ++i) {
            final MCH_WeaponBase wb = ws.getWeapon(i);
            if (wb != null) {
                final MCH_WeaponInfo wi = wb.getInfo();
                if (wi != null) {
                    final Vec3 rot = MCH_Lib.RotVec3(0.0, 0.0, 1.0, -yaw - 180.0f + wb.fixRotationYaw, pitch - wb.fixRotationPitch, roll);
                    if (this.rand.nextFloat() <= this.getCurrentThrottle() * 1.5) {
                        final Vec3 pos = MCH_Lib.RotVec3(wb.position, -yaw, -pitch, -roll);
                        final double x = this.posX + pos.xCoord + rot.xCoord;
                        final double y = this.posY + pos.yCoord + rot.yCoord;
                        final double z = this.posZ + pos.zCoord + rot.zCoord;
                        for (int smk = 0; smk < wi.smokeNum; ++smk) {
                            final float c = this.rand.nextFloat() * 0.05f;
                            final int maxAge = (int)(this.rand.nextDouble() * wi.smokeMaxAge);
                            final MCH_ParticleParam prm = new MCH_ParticleParam(this.worldObj, "smoke", x, y, z);
                            prm.setMotion(rot.xCoord * wi.acceleration + (this.rand.nextDouble() - 0.5) * 0.2, rot.yCoord * wi.acceleration + (this.rand.nextDouble() - 0.5) * 0.2, rot.zCoord * wi.acceleration + (this.rand.nextDouble() - 0.5) * 0.2);
                            prm.size = (this.rand.nextInt(5) + 5.0f) * wi.smokeSize;
                            prm.setColor(wi.color.a + this.rand.nextFloat() * 0.05f, wi.color.r + c, wi.color.g + c, wi.color.b + c);
                            prm.age = maxAge;
                            prm.toWhite = true;
                            prm.diffusible = true;
                            MCH_ParticlesUtil.spawnParticle(prm);
                        }
                    }
                }
            }
        }
    }
    
    protected void onUpdate_ParticleSandCloud(final boolean seaOnly) {
        if (seaOnly && !this.getAcInfo().enableSeaSurfaceParticle) {
            return;
        }
        double particlePosY = (int)this.posY;
        boolean b = false;
        float scale = this.getAcInfo().particlesScale * 3.0f;
        if (seaOnly) {
            scale *= 2.0f;
        }
        double throttle = this.getCurrentThrottle();
        throttle *= 2.0;
        if (throttle > 1.0) {
            throttle = 1.0;
        }
        int count = seaOnly ? ((int)(scale * 7.0f)) : 0;
        int rangeY;
        int y;
        for (rangeY = (int)(scale * 10.0f) + 1, y = 0; y < rangeY && !b; ++y) {
            for (int x = -1; x <= 1; ++x) {
                for (int z = -1; z <= 1; ++z) {
                    final Block block = W_WorldFunc.getBlock(this.worldObj, (int)(this.posX + 0.5) + x, (int)(this.posY + 0.5) - y, (int)(this.posZ + 0.5) + z);
                    if (!b && block != null && !Block.isEqualTo(block, Blocks.air)) {
                        if (seaOnly && W_Block.isEqual(block, W_Block.getWater())) {
                            --count;
                        }
                        if (count <= 0) {
                            particlePosY = this.posY + 1.0 + scale / 5.0f - y;
                            b = true;
                            x += 100;
                            break;
                        }
                    }
                }
            }
        }
        final double pn = (rangeY - y + 1) / (5.0 * scale) / 2.0;
        if (b && this.getAcInfo().particlesScale > 0.01f) {
            for (int k = 0; k < (int)(throttle * 6.0 * pn); ++k) {
                final float r = (float)(this.rand.nextDouble() * 3.141592653589793 * 2.0);
                final double dx = MathHelper.cos(r);
                final double dz = MathHelper.sin(r);
                final MCH_ParticleParam prm = new MCH_ParticleParam(this.worldObj, "smoke", this.posX + dx * scale * 3.0, particlePosY + (this.rand.nextDouble() - 0.5) * scale, this.posZ + dz * scale * 3.0, scale * (dx * 0.3), scale * -0.4 * 0.05, scale * (dz * 0.3), scale * 5.0f);
                prm.setColor(prm.a * 0.6f, prm.r, prm.g, prm.b);
                prm.age = (int)(10.0f * scale);
                prm.motionYUpAge = (seaOnly ? 0.2f : 0.1f);
                MCH_ParticlesUtil.spawnParticle(prm);
            }
        }
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
        return 0.0;
    }
    
    public float getShadowSize() {
        return 2.0f;
    }
    
    public boolean canBeCollidedWith() {
        return !this.isDead;
    }
    
    public boolean useFlare(final int type) {
        if (this.getAcInfo() == null || !this.getAcInfo().haveFlare()) {
            return false;
        }
        for (final int i : this.getAcInfo().flare.types) {
            if (i == type) {
                this.setCommonStatus(0, true);
                if (this.flareDv.use(type)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public int getCurrentFlareType() {
        if (!this.haveFlare()) {
            return 0;
        }
        return this.getAcInfo().flare.types[this.currentFlareIndex];
    }
    
    public void nextFlareType() {
        if (this.haveFlare()) {
            this.currentFlareIndex = (this.currentFlareIndex + 1) % this.getAcInfo().flare.types.length;
        }
    }
    
    public boolean canUseFlare() {
        return this.getAcInfo() != null && this.getAcInfo().haveFlare() && !this.getCommonStatus(0) && this.flareDv.tick == 0;
    }
    
    public boolean isFlarePreparation() {
        return this.flareDv.isInPreparation();
    }
    
    public boolean isFlareUsing() {
        return this.flareDv.isUsing();
    }
    
    public int getFlareTick() {
        return this.flareDv.tick;
    }
    
    public boolean haveFlare() {
        return this.getAcInfo() != null && this.getAcInfo().haveFlare();
    }
    
    public boolean haveFlare(final int seatID) {
        return this.haveFlare() && seatID >= 0 && seatID <= 1;
    }
    
    public MCH_EntitySeat[] getSeats() {
        return (this.seats != null) ? this.seats : MCH_EntityAircraft.seatsDummy;
    }
    
    public int getSeatIdByEntity(final Entity entity) {
        if (entity == null) {
            return -1;
        }
        if (W_Entity.isEqual(this.getRiddenByEntity(), entity)) {
            return 0;
        }
        for (int i = 0; i < this.getSeats().length; ++i) {
            final MCH_EntitySeat seat = this.getSeats()[i];
            if (seat != null && W_Entity.isEqual(seat.riddenByEntity, entity)) {
                return i + 1;
            }
        }
        return -1;
    }
    
    public MCH_EntitySeat getSeatByEntity(final Entity entity) {
        final int idx = this.getSeatIdByEntity(entity);
        if (idx > 0) {
            return this.getSeat(idx - 1);
        }
        return null;
    }
    
    public Entity getEntityBySeatId(int id) {
        if (id == 0) {
            return this.getRiddenByEntity();
        }
        if (--id < 0 || id >= this.getSeats().length) {
            return null;
        }
        return (this.seats[id] != null) ? this.seats[id].riddenByEntity : null;
    }
    
    public EntityPlayer getEntityByWeaponId(final int id) {
        if (id >= 0 && id < this.getWeaponNum()) {
            for (int i = 0; i < this.currentWeaponID.length; ++i) {
                if (this.currentWeaponID[i] == id) {
                    final Entity e = this.getEntityBySeatId(i);
                    if (e instanceof EntityPlayer) {
                        return (EntityPlayer)e;
                    }
                }
            }
        }
        return null;
    }
    
    public Entity getWeaponUserByWeaponName(final String name) {
        if (this.getAcInfo() == null) {
            return null;
        }
        final MCH_AircraftInfo.Weapon weapon = this.getAcInfo().getWeaponByName(name);
        Entity entity = null;
        if (weapon != null) {
            entity = this.getEntityBySeatId(this.getWeaponSeatID(null, weapon));
            if (entity == null && weapon.canUsePilot) {
                entity = this.getRiddenByEntity();
            }
        }
        return entity;
    }
    
    protected void newSeats(final int seatsNum) {
        if (seatsNum >= 2) {
            if (this.seats != null) {
                for (int i = 0; i < this.seats.length; ++i) {
                    if (this.seats[i] != null) {
                        this.seats[i].setDead();
                        this.seats[i] = null;
                    }
                }
            }
            this.seats = new MCH_EntitySeat[seatsNum - 1];
        }
    }
    
    public MCH_EntitySeat getSeat(final int idx) {
        return (idx < this.seats.length) ? this.seats[idx] : null;
    }
    
    public void setSeat(final int idx, final MCH_EntitySeat seat) {
        if (idx < this.seats.length) {
            MCH_Lib.DbgLog(this.worldObj, "MCH_EntityAircraft.setSeat SeatID=" + idx + " / seat[]" + (this.seats[idx] != null) + " / " + (seat.riddenByEntity != null), new Object[0]);
            if (this.seats[idx] == null || this.seats[idx].riddenByEntity != null) {}
            this.seats[idx] = seat;
        }
    }
    
    public boolean isValidSeatID(final int seatID) {
        return seatID >= 0 && seatID < this.getSeatNum() + 1;
    }
    
    public void updateHitBoxPosition() {
    }
    
    public void updateSeatsPosition(final double px, final double py, final double pz, final boolean setPrevPos) {
        final MCH_SeatInfo[] info = this.getSeatsInfo();
        if (this.pilotSeat != null && !this.pilotSeat.isDead) {
            this.pilotSeat.prevPosX = this.pilotSeat.posX;
            this.pilotSeat.prevPosY = this.pilotSeat.posY;
            this.pilotSeat.prevPosZ = this.pilotSeat.posZ;
            this.pilotSeat.setPosition(px, py, pz);
            if (info != null && info.length > 0 && info[0] != null) {
                final Vec3 v = this.getTransformedPosition(info[0].pos.xCoord, info[0].pos.yCoord, info[0].pos.zCoord, px, py, pz, info[0].rotSeat);
                this.pilotSeat.setPosition(v.xCoord, v.yCoord, v.zCoord);
            }
            this.pilotSeat.rotationPitch = this.getRotPitch();
            this.pilotSeat.rotationYaw = this.getRotYaw();
            if (setPrevPos) {
                this.pilotSeat.prevPosX = this.pilotSeat.posX;
                this.pilotSeat.prevPosY = this.pilotSeat.posY;
                this.pilotSeat.prevPosZ = this.pilotSeat.posZ;
            }
        }
        int i = 0;
        for (final MCH_EntitySeat seat : this.seats) {
            ++i;
            if (seat != null && !seat.isDead) {
                float offsetY = 0.0f;
                if (seat.riddenByEntity != null) {
                    if (W_Lib.isClientPlayer(seat.riddenByEntity)) {
                        offsetY = 1.0f;
                    }
                    else if (seat.riddenByEntity.height >= 1.0f) {
                        offsetY = -seat.riddenByEntity.height + 1.0f;
                    }
                }
                seat.prevPosX = seat.posX;
                seat.prevPosY = seat.posY;
                seat.prevPosZ = seat.posZ;
                final MCH_SeatInfo si = (i < info.length) ? info[i] : info[0];
                final Vec3 v2 = this.getTransformedPosition(si.pos.xCoord, si.pos.yCoord + offsetY, si.pos.zCoord, px, py, pz, si.rotSeat);
                seat.setPosition(v2.xCoord, v2.yCoord, v2.zCoord);
                seat.rotationPitch = this.getRotPitch();
                seat.rotationYaw = this.getRotYaw();
                if (setPrevPos) {
                    seat.prevPosX = seat.posX;
                    seat.prevPosY = seat.posY;
                    seat.prevPosZ = seat.posZ;
                }
                if (si instanceof MCH_SeatRackInfo) {
                    seat.updateRotation(((MCH_SeatRackInfo)si).fixYaw + this.getRotYaw(), ((MCH_SeatRackInfo)si).fixPitch);
                }
                seat.updatePosition();
            }
        }
    }
    
    public int getClientPositionDelayCorrection() {
        return 7;
    }
    
    public void setPositionAndRotation2(final double par1, final double par3, final double par5, final float par7, final float par8, final int par9) {
        this.aircraftPosRotInc = par9 + this.getClientPositionDelayCorrection();
        this.aircraftX = par1;
        this.aircraftY = par3;
        this.aircraftZ = par5;
        this.aircraftYaw = par7;
        this.aircraftPitch = par8;
        this.motionX = this.velocityX;
        this.motionY = this.velocityY;
        this.motionZ = this.velocityZ;
    }
    
    public void updateRiderPosition(final double px, final double py, final double pz) {
        final MCH_SeatInfo[] info = this.getSeatsInfo();
        if (this.riddenByEntity != null && !this.riddenByEntity.isDead) {
            final float riddenEntityYOffset = this.riddenByEntity.yOffset;
            float offset = 0.0f;
            if (this.riddenByEntity instanceof EntityPlayer && !W_Lib.isClientPlayer(this.riddenByEntity)) {
                offset -= 1.62f;
            }
            Vec3 v;
            if (info != null && info.length > 0) {
                v = this.getTransformedPosition(info[0].pos.xCoord, info[0].pos.yCoord + riddenEntityYOffset - 0.5, info[0].pos.zCoord, px, py, pz, info[0].rotSeat);
            }
            else {
                v = this.getTransformedPosition(0.0, riddenEntityYOffset - 1.0f, 0.0);
            }
            this.riddenByEntity.yOffset = 0.0f;
            this.riddenByEntity.setPosition(v.xCoord, v.yCoord, v.zCoord);
            this.riddenByEntity.yOffset = riddenEntityYOffset;
        }
    }
    
    public void updateRiderPosition() {
        this.updateRiderPosition(this.posX, this.posY, this.posZ);
    }
    
    public Vec3 calcOnTurretPos(final Vec3 pos) {
        float ry = this.getLastRiderYaw();
        if (this.getRiddenByEntity() != null) {
            ry = this.getRiddenByEntity().rotationYaw;
        }
        final Vec3 tpos = this.getAcInfo().turretPosition.addVector(0.0, pos.yCoord, 0.0);
        Vec3 v = pos.addVector(-tpos.xCoord, -tpos.yCoord, -tpos.zCoord);
        v = MCH_Lib.RotVec3(v, -ry, 0.0f, 0.0f);
        final Vec3 vv = MCH_Lib.RotVec3(tpos, -this.getRotYaw(), -this.getRotPitch(), -this.getRotRoll());
        final Vec3 vec3 = v;
        vec3.xCoord += vv.xCoord;
        final Vec3 vec4 = v;
        vec4.yCoord += vv.yCoord;
        final Vec3 vec5 = v;
        vec5.zCoord += vv.zCoord;
        return v;
    }
    
    public float getLastRiderYaw() {
        return this.lastRiderYaw;
    }
    
    public float getLastRiderPitch() {
        return this.lastRiderPitch;
    }
    
    @SideOnly(Side.CLIENT)
    public void setupAllRiderRenderPosition(final float tick, final EntityPlayer player) {
        double x = this.lastTickPosX + (this.posX - this.lastTickPosX) * tick;
        double y = this.lastTickPosY + (this.posY - this.lastTickPosY) * tick;
        double z = this.lastTickPosZ + (this.posZ - this.lastTickPosZ) * tick;
        this.updateRiderPosition(x, y, z);
        this.updateSeatsPosition(x, y, z, true);
        for (int i = 0; i < this.getSeatNum() + 1; ++i) {
            final Entity e = this.getEntityBySeatId(i);
            if (e != null) {
                e.lastTickPosX = e.posX;
                e.lastTickPosY = e.posY;
                e.lastTickPosZ = e.posZ;
            }
        }
        if (this.getTVMissile() != null && W_Lib.isClientPlayer(this.getTVMissile().shootingEntity)) {
            final Entity tv = this.getTVMissile();
            x = tv.prevPosX + (tv.posX - tv.prevPosX) * tick;
            y = tv.prevPosY + (tv.posY - tv.prevPosY) * tick;
            z = tv.prevPosZ + (tv.posZ - tv.prevPosZ) * tick;
            MCH_ViewEntityDummy.setCameraPosition(x, y, z);
        }
        else {
            final MCH_AircraftInfo.CameraPosition cpi = this.getCameraPosInfo();
            if (cpi != null && cpi.pos != null) {
                final MCH_SeatInfo seatInfo = this.getSeatInfo((Entity)player);
                Vec3 v;
                if (seatInfo != null && seatInfo.rotSeat) {
                    v = this.calcOnTurretPos(cpi.pos);
                }
                else {
                    v = MCH_Lib.RotVec3(cpi.pos, -this.getRotYaw(), -this.getRotPitch(), -this.getRotRoll());
                }
                MCH_ViewEntityDummy.setCameraPosition(x + v.xCoord, y + v.yCoord, z + v.zCoord);
                if (cpi.fixRot) {}
            }
        }
    }
    
    public Vec3 getTurretPos(final Vec3 pos, final boolean turret) {
        if (turret) {
            float ry = this.getLastRiderYaw();
            if (this.getRiddenByEntity() != null) {
                ry = this.getRiddenByEntity().rotationYaw;
            }
            final Vec3 tpos = this.getAcInfo().turretPosition.addVector(0.0, pos.yCoord, 0.0);
            Vec3 v = pos.addVector(-tpos.xCoord, -tpos.yCoord, -tpos.zCoord);
            v = MCH_Lib.RotVec3(v, -ry, 0.0f, 0.0f);
            final Vec3 vv = MCH_Lib.RotVec3(tpos, -this.getRotYaw(), -this.getRotPitch(), -this.getRotRoll());
            final Vec3 vec3 = v;
            vec3.xCoord += vv.xCoord;
            final Vec3 vec4 = v;
            vec4.yCoord += vv.yCoord;
            final Vec3 vec5 = v;
            vec5.zCoord += vv.zCoord;
            return v;
        }
        return Vec3.createVectorHelper(0.0, 0.0, 0.0);
    }
    
    public Vec3 getTransformedPosition(final Vec3 v) {
        return this.getTransformedPosition(v.xCoord, v.yCoord, v.zCoord);
    }
    
    public Vec3 getTransformedPosition(final double x, final double y, final double z) {
        return this.getTransformedPosition(x, y, z, this.posX, this.posY, this.posZ);
    }
    
    public Vec3 getTransformedPosition(final Vec3 v, final Vec3 pos) {
        return this.getTransformedPosition(v.xCoord, v.yCoord, v.zCoord, pos.xCoord, pos.yCoord, pos.zCoord);
    }
    
    public Vec3 getTransformedPosition(final Vec3 v, final double px, final double py, final double pz) {
        return this.getTransformedPosition(v.xCoord, v.yCoord, v.zCoord, this.posX, this.posY, this.posZ);
    }
    
    public Vec3 getTransformedPosition(final double x, final double y, final double z, final double px, final double py, final double pz) {
        final Vec3 v = MCH_Lib.RotVec3(x, y, z, -this.getRotYaw(), -this.getRotPitch(), -this.getRotRoll());
        return v.addVector(px, py, pz);
    }
    
    public Vec3 getTransformedPosition(double x, double y, double z, final double px, final double py, final double pz, final boolean rotSeat) {
        if (rotSeat && this.getAcInfo() != null) {
            final MCH_AircraftInfo info = this.getAcInfo();
            final Vec3 tv = MCH_Lib.RotVec3(x - info.turretPosition.xCoord, y - info.turretPosition.yCoord, z - info.turretPosition.zCoord, -this.getLastRiderYaw() + this.getRotYaw(), 0.0f, 0.0f);
            x = tv.xCoord + info.turretPosition.xCoord;
            y = tv.yCoord + info.turretPosition.xCoord;
            z = tv.zCoord + info.turretPosition.xCoord;
        }
        final Vec3 v = MCH_Lib.RotVec3(x, y, z, -this.getRotYaw(), -this.getRotPitch(), -this.getRotRoll());
        return v.addVector(px, py, pz);
    }
    
    protected MCH_SeatInfo[] getSeatsInfo() {
        if (this.seatsInfo != null) {
            return this.seatsInfo;
        }
        this.newSeatsPos();
        return this.seatsInfo;
    }
    
    public MCH_SeatInfo getSeatInfo(final int index) {
        final MCH_SeatInfo[] seats = this.getSeatsInfo();
        if (index >= 0 && seats != null && index < seats.length) {
            return seats[index];
        }
        return null;
    }
    
    public MCH_SeatInfo getSeatInfo(final Entity entity) {
        return this.getSeatInfo(this.getSeatIdByEntity(entity));
    }
    
    protected void setSeatsInfo(final MCH_SeatInfo[] v) {
        this.seatsInfo = v;
    }
    
    public int getSeatNum() {
        if (this.getAcInfo() == null) {
            return 0;
        }
        final int s = this.getAcInfo().getNumSeatAndRack();
        return (s >= 1) ? (s - 1) : 1;
    }
    
    protected void newSeatsPos() {
        if (this.getAcInfo() != null) {
            final MCH_SeatInfo[] v = new MCH_SeatInfo[this.getAcInfo().getNumSeatAndRack()];
            for (int i = 0; i < v.length; ++i) {
                v[i] = this.getAcInfo().seatList.get(i);
            }
            this.setSeatsInfo(v);
        }
    }
    
    public void createSeats(final String uuid) {
        if (this.worldObj.isRemote) {
            return;
        }
        if (uuid.isEmpty()) {
            return;
        }
        this.setCommonUniqueId(uuid);
        this.seats = new MCH_EntitySeat[this.getSeatNum()];
        for (int i = 0; i < this.seats.length; ++i) {
            this.seats[i] = new MCH_EntitySeat(this.worldObj, this.posX, this.posY, this.posZ);
            this.seats[i].parentUniqueID = this.getCommonUniqueId();
            this.seats[i].seatID = i;
            this.seats[i].setParent(this);
            this.worldObj.spawnEntityInWorld((Entity)this.seats[i]);
        }
    }
    
    public boolean interactFirstSeat(final EntityPlayer player) {
        if (this.getSeats() == null) {
            return false;
        }
        int seatId = 1;
        final MCH_EntitySeat[] arr$ = this.getSeats();
        final int len$ = arr$.length;
        int i$ = 0;
        while (i$ < len$) {
            final MCH_EntitySeat seat = arr$[i$];
            if (seat != null && seat.riddenByEntity == null && !this.isMountedEntity((Entity)player) && this.canRideSeatOrRack(seatId, (Entity)player)) {
                if (!this.worldObj.isRemote) {
                    player.mountEntity((Entity)seat);
                    break;
                }
                break;
            }
            else {
                ++seatId;
                ++i$;
            }
        }
        return true;
    }
    
    public void onMountPlayerSeat(final MCH_EntitySeat seat, final Entity entity) {
        if (seat == null || (!(entity instanceof EntityPlayer) && !(entity instanceof MCH_EntityGunner))) {
            return;
        }
        if (this.worldObj.isRemote && MCH_Lib.getClientPlayer() == entity) {
            this.switchGunnerFreeLookMode(false);
        }
        this.initCurrentWeapon(entity);
        MCH_Lib.DbgLog(this.worldObj, "onMountEntitySeat:%d", W_Entity.getEntityId(entity));
        final Entity pilot = this.getRiddenByEntity();
        final int sid = this.getSeatIdByEntity(entity);
        if (sid == 1 && (this.getAcInfo() == null || !this.getAcInfo().isEnableConcurrentGunnerMode)) {
            this.switchGunnerMode(false);
        }
        if (sid > 0) {
            this.isGunnerModeOtherSeat = true;
        }
        if (pilot != null && this.getAcInfo() != null) {
            final int cwid = this.getCurrentWeaponID(pilot);
            final MCH_AircraftInfo.Weapon w = this.getAcInfo().getWeaponById(cwid);
            if (w != null && this.getWeaponSeatID(this.getWeaponInfoById(cwid), w) == sid) {
                final int next = this.getNextWeaponID(pilot, 1);
                MCH_Lib.DbgLog(this.worldObj, "onMountEntitySeat:%d:->%d", W_Entity.getEntityId(pilot), next);
                if (next >= 0) {
                    this.switchWeapon(pilot, next);
                }
            }
        }
        if (this.worldObj.isRemote) {
            this.updateClientSettings(sid);
        }
    }
    
    public MCH_WeaponInfo getWeaponInfoById(final int id) {
        if (id >= 0) {
            final MCH_WeaponSet ws = this.getWeapon(id);
            if (ws != null) {
                return ws.getInfo();
            }
        }
        return null;
    }
    
    public abstract boolean canMountWithNearEmptyMinecart();
    
    protected void mountWithNearEmptyMinecart() {
        if (this.getRidingEntity() != null) {
            return;
        }
        int d = 2;
        if (this.dismountedUserCtrl) {
            d = 6;
        }
        final List list = this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this, this.boundingBox.expand((double)d, (double)d, (double)d));
        if (list != null && !list.isEmpty()) {
            for (int i = 0; i < list.size(); ++i) {
                final Entity entity = list.get(i);
                if (entity instanceof EntityMinecartEmpty) {
                    if (this.dismountedUserCtrl) {
                        return;
                    }
                    if (entity.riddenByEntity == null && entity.canBePushed()) {
                        this.waitMountEntity = 20;
                        MCH_Lib.DbgLog(this.worldObj.isRemote, "MCH_EntityAircraft.mountWithNearEmptyMinecart:" + entity, new Object[0]);
                        this.mountEntity(entity);
                        return;
                    }
                }
            }
        }
        this.dismountedUserCtrl = false;
    }
    
    public boolean isRidePlayer() {
        if (this.getRiddenByEntity() instanceof EntityPlayer) {
            return true;
        }
        for (final MCH_EntitySeat seat : this.getSeats()) {
            if (seat != null && seat.riddenByEntity instanceof EntityPlayer) {
                return true;
            }
        }
        return false;
    }
    
    public void onUnmountPlayerSeat(final MCH_EntitySeat seat, final Entity entity) {
        MCH_Lib.DbgLog(this.worldObj, "onUnmountPlayerSeat:%d", W_Entity.getEntityId(entity));
        final int sid = this.getSeatIdByEntity(entity);
        this.camera.initCamera(sid, entity);
        final MCH_SeatInfo seatInfo = this.getSeatInfo(seat.seatID + 1);
        if (seatInfo != null) {
            this.setUnmountPosition(entity, Vec3.createVectorHelper(seatInfo.pos.xCoord, 0.0, seatInfo.pos.zCoord));
        }
        if (!this.isRidePlayer()) {
            this.switchGunnerMode(false);
            this.switchHoveringMode(false);
        }
    }
    
    public boolean isCreatedSeats() {
        return !this.getCommonUniqueId().isEmpty();
    }
    
    public void onUpdate_Seats() {
        boolean b = false;
        for (int i = 0; i < this.seats.length; ++i) {
            if (this.seats[i] != null) {
                if (!this.seats[i].isDead) {
                    this.seats[i].fallDistance = 0.0f;
                }
            }
            else {
                b = true;
            }
        }
        if (b) {
            if (this.seatSearchCount > 40) {
                if (this.worldObj.isRemote) {
                    MCH_PacketSeatListRequest.requestSeatList(this);
                }
                else {
                    this.searchSeat();
                }
                this.seatSearchCount = 0;
            }
            ++this.seatSearchCount;
        }
    }
    
    public void searchSeat() {
        final List list = this.worldObj.getEntitiesWithinAABB((Class)MCH_EntitySeat.class, this.boundingBox.expand(60.0, 60.0, 60.0));
        for (int i = 0; i < list.size(); ++i) {
            final MCH_EntitySeat seat = list.get(i);
            if (!seat.isDead && seat.parentUniqueID.equals(this.getCommonUniqueId()) && seat.seatID >= 0 && seat.seatID < this.getSeatNum() && this.seats[seat.seatID] == null) {
                (this.seats[seat.seatID] = seat).setParent(this);
            }
        }
    }
    
    public String getCommonUniqueId() {
        return this.commonUniqueId;
    }
    
    public void setCommonUniqueId(final String uniqId) {
        this.commonUniqueId = uniqId;
    }
    
    @Override
    public void setDead() {
        this.setDead(false);
    }
    
    public void setDead(final boolean dropItems) {
        this.dropContentsWhenDead = dropItems;
        super.setDead();
        if (this.getRiddenByEntity() != null) {
            this.getRiddenByEntity().mountEntity((Entity)null);
        }
        this.getGuiInventory().setDead();
        for (final MCH_EntitySeat s : this.seats) {
            if (s != null) {
                s.setDead();
            }
        }
        if (this.soundUpdater != null) {
            this.soundUpdater.update();
        }
        if (this.getTowChainEntity() != null) {
            this.getTowChainEntity().setDead();
            this.setTowChainEntity(null);
        }
        for (final Entity e : this.getParts()) {
            if (e != null) {
                e.setDead();
            }
        }
        MCH_Lib.DbgLog(this.worldObj, "setDead:" + ((this.getAcInfo() != null) ? this.getAcInfo().name : "null"), new Object[0]);
    }
    
    public void unmountEntity() {
        if (!this.isRidePlayer()) {
            this.switchHoveringMode(false);
        }
        final boolean b = false;
        this.throttleUp = b;
        this.throttleDown = b;
        this.moveRight = b;
        this.moveLeft = b;
        Entity rByEntity = null;
        if (this.riddenByEntity != null) {
            rByEntity = this.riddenByEntity;
            this.camera.initCamera(0, rByEntity);
            if (!this.worldObj.isRemote) {
                this.riddenByEntity.mountEntity((Entity)null);
            }
        }
        else if (this.lastRiddenByEntity != null) {
            rByEntity = this.lastRiddenByEntity;
            if (rByEntity instanceof EntityPlayer) {
                this.camera.initCamera(0, rByEntity);
            }
        }
        MCH_Lib.DbgLog(this.worldObj, "unmountEntity:" + rByEntity, new Object[0]);
        if (!this.isRidePlayer()) {
            this.switchGunnerMode(false);
        }
        this.setCommonStatus(1, false);
        if (!this.isUAV()) {
            this.setUnmountPosition(rByEntity, this.getSeatsInfo()[0].pos);
        }
        else if (rByEntity != null && rByEntity.ridingEntity instanceof MCH_EntityUavStation) {
            rByEntity.mountEntity((Entity)null);
        }
        this.riddenByEntity = null;
        this.lastRiddenByEntity = null;
        if (this.cs_dismountAll) {
            this.unmountCrew(false);
        }
    }
    
    public Entity getRidingEntity() {
        return this.ridingEntity;
    }
    
    public void startUnmountCrew() {
        this.isParachuting = true;
        if (this.haveHatch()) {
            this.foldHatch(true, true);
        }
    }
    
    public void stopUnmountCrew() {
        this.isParachuting = false;
    }
    
    public void unmountCrew() {
        if (this.getAcInfo() == null) {
            return;
        }
        if (this.getAcInfo().haveRepellingHook()) {
            if (!this.isRepelling()) {
                if (MCH_Lib.getBlockIdY(this, 3, -4) > 0) {
                    this.unmountCrew(false);
                }
                else if (this.canStartRepelling()) {
                    this.startRepelling();
                }
            }
            else {
                this.stopRepelling();
            }
        }
        else if (this.isParachuting) {
            this.stopUnmountCrew();
        }
        else if (this.getAcInfo().isEnableParachuting && MCH_Lib.getBlockIdY(this, 3, -10) == 0) {
            this.startUnmountCrew();
        }
        else {
            this.unmountCrew(false);
        }
    }
    
    public boolean isRepelling() {
        return this.getCommonStatus(5);
    }
    
    public void setRepellingStat(final boolean b) {
        this.setCommonStatus(5, b);
    }
    
    public Vec3 getRopePos(final int ropeIndex) {
        if (this.getAcInfo() != null && this.getAcInfo().haveRepellingHook() && ropeIndex < this.getAcInfo().repellingHooks.size()) {
            return this.getTransformedPosition(this.getAcInfo().repellingHooks.get(ropeIndex).pos);
        }
        return Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
    }
    
    private void startRepelling() {
        MCH_Lib.DbgLog(this.worldObj, "MCH_EntityAircraft.startRepelling()", new Object[0]);
        this.setRepellingStat(true);
        this.throttleUp = false;
        this.throttleDown = false;
        this.moveLeft = false;
        this.moveRight = false;
        this.tickRepelling = 0;
    }
    
    private void stopRepelling() {
        MCH_Lib.DbgLog(this.worldObj, "MCH_EntityAircraft.stopRepelling()", new Object[0]);
        this.setRepellingStat(false);
    }
    
    public static float abs(final float p_76135_0_) {
        return (p_76135_0_ >= 0.0f) ? p_76135_0_ : (-p_76135_0_);
    }
    
    public static double abs(final double p_76135_0_) {
        return (p_76135_0_ >= 0.0) ? p_76135_0_ : (-p_76135_0_);
    }
    
    public boolean canStartRepelling() {
        if (this.getAcInfo().haveRepellingHook() && this.isHovering() && abs(this.getRotPitch()) < 3.0f && abs(this.getRotRoll()) < 3.0f) {
            final Vec3 v = this.prevPosition.oldest().addVector(-this.posX, -this.posY, -this.posZ);
            if (v.lengthVector() < 0.3) {
                return true;
            }
        }
        return false;
    }
    
    public boolean unmountCrew(final boolean unmountParachute) {
        boolean ret = false;
        final MCH_SeatInfo[] pos = this.getSeatsInfo();
        for (int i = 0; i < this.seats.length; ++i) {
            if (this.seats[i] != null && this.seats[i].riddenByEntity != null) {
                final Entity entity = this.seats[i].riddenByEntity;
                if (!(entity instanceof EntityPlayer) && !(pos[i + 1] instanceof MCH_SeatRackInfo)) {
                    if (unmountParachute) {
                        if (this.getSeatIdByEntity(entity) > 1) {
                            ret = true;
                            final Vec3 dropPos = this.getTransformedPosition(this.getAcInfo().mobDropOption.pos, this.prevPosition.oldest());
                            this.seats[i].posX = dropPos.xCoord;
                            this.seats[i].posY = dropPos.yCoord;
                            this.seats[i].posZ = dropPos.zCoord;
                            entity.mountEntity((Entity)null);
                            entity.posX = dropPos.xCoord;
                            entity.posY = dropPos.yCoord;
                            entity.posZ = dropPos.zCoord;
                            this.dropEntityParachute(entity);
                            break;
                        }
                    }
                    else {
                        ret = true;
                        final Vec3 dropPos = pos[i + 1].pos;
                        this.setUnmountPosition(this.seats[i], pos[i + 1].pos);
                        entity.mountEntity((Entity)null);
                        this.setUnmountPosition(entity, pos[i + 1].pos);
                    }
                }
            }
        }
        return ret;
    }
    
    public void setUnmountPosition(final Entity rByEntity, final Vec3 pos) {
        if (rByEntity != null) {
            final MCH_AircraftInfo info = this.getAcInfo();
            Vec3 v;
            if (info != null && info.unmountPosition != null) {
                v = this.getTransformedPosition(info.unmountPosition);
            }
            else {
                double x = pos.xCoord;
                x = ((x >= 0.0) ? (x + 3.0) : (x - 3.0));
                v = this.getTransformedPosition(x, 2.0, pos.zCoord);
            }
            rByEntity.setPosition(v.xCoord, v.yCoord, v.zCoord);
            this.listUnmountReserve.add(new UnmountReserve(rByEntity, v.xCoord, v.yCoord, v.zCoord));
        }
    }
    
    public boolean unmountEntityFromSeat(final Entity entity) {
        if (entity == null || this.seats == null || this.seats.length == 0) {
            return false;
        }
        for (final MCH_EntitySeat seat : this.seats) {
            if (seat != null && seat.riddenByEntity != null && W_Entity.isEqual(seat.riddenByEntity, entity)) {
                entity.mountEntity((Entity)null);
            }
        }
        return false;
    }
    
    public void ejectSeat(Entity entity) {
        final int sid = this.getSeatIdByEntity(entity);
        if (sid < 0 || sid > 1) {
            return;
        }
        if (this.getGuiInventory().haveParachute()) {
            if (sid == 0) {
                this.getGuiInventory().consumeParachute();
                this.unmountEntity();
                this.ejectSeatSub(entity, 0);
                entity = this.getEntityBySeatId(1);
                if (entity instanceof EntityPlayer) {
                    entity = null;
                }
            }
            if (this.getGuiInventory().haveParachute() && entity != null) {
                this.getGuiInventory().consumeParachute();
                this.unmountEntityFromSeat(entity);
                this.ejectSeatSub(entity, 1);
            }
        }
    }
    
    public void ejectSeatSub(final Entity entity, final int sid) {
        final Vec3 pos = (this.getSeatInfo(sid) != null) ? this.getSeatInfo(sid).pos : null;
        if (pos != null) {
            final Vec3 v = this.getTransformedPosition(pos.xCoord, pos.yCoord + 2.0, pos.zCoord);
            entity.setPosition(v.xCoord, v.yCoord, v.zCoord);
        }
        final Vec3 v = MCH_Lib.RotVec3(0.0, 2.0, 0.0, -this.getRotYaw(), -this.getRotPitch(), -this.getRotRoll());
        entity.motionX = this.motionX + v.xCoord + (this.rand.nextFloat() - 0.5) * 0.1;
        entity.motionY = this.motionY + v.yCoord;
        entity.motionZ = this.motionZ + v.zCoord + (this.rand.nextFloat() - 0.5) * 0.1;
        final MCH_EntityParachute parachute = new MCH_EntityParachute(this.worldObj, entity.posX, entity.posY, entity.posZ);
        parachute.rotationYaw = entity.rotationYaw;
        parachute.motionX = entity.motionX;
        parachute.motionY = entity.motionY;
        parachute.motionZ = entity.motionZ;
        parachute.fallDistance = entity.fallDistance;
        parachute.user = entity;
        parachute.setType(2);
        this.worldObj.spawnEntityInWorld((Entity)parachute);
        if (this.getAcInfo().haveCanopy() && this.isCanopyClose()) {
            this.openCanopy_EjectSeat();
        }
        W_WorldFunc.MOD_playSoundAtEntity(entity, "eject_seat", 5.0f, 1.0f);
    }
    
    public boolean canEjectSeat(final Entity entity) {
        final int sid = this.getSeatIdByEntity(entity);
        return (sid != 0 || !this.isUAV()) && sid >= 0 && sid < 2 && this.getAcInfo() != null && this.getAcInfo().isEnableEjectionSeat;
    }
    
    public int getNumEjectionSeat() {
        return 0;
    }
    
    public int getMountedEntityNum() {
        int num = 0;
        if (this.riddenByEntity != null && !this.riddenByEntity.isDead) {
            ++num;
        }
        if (this.seats != null && this.seats.length > 0) {
            for (final MCH_EntitySeat seat : this.seats) {
                if (seat != null && seat.riddenByEntity != null && !seat.riddenByEntity.isDead) {
                    ++num;
                }
            }
        }
        return num;
    }
    
    public void mountMobToSeats() {
        final List list = this.worldObj.getEntitiesWithinAABB(W_Lib.getEntityLivingBaseClass(), this.boundingBox.expand(3.0, 2.0, 3.0));
        for (int i = 0; i < list.size(); ++i) {
            final Entity entity = list.get(i);
            if (!(entity instanceof EntityPlayer)) {
                if (entity.ridingEntity == null) {
                    int sid = 1;
                    for (final MCH_EntitySeat seat : this.getSeats()) {
                        if (seat != null && seat.riddenByEntity == null && !this.isMountedEntity(entity) && this.canRideSeatOrRack(sid, entity)) {
                            if (this.getSeatInfo(sid) instanceof MCH_SeatRackInfo) {
                                break;
                            }
                            entity.mountEntity((Entity)seat);
                        }
                        ++sid;
                    }
                }
            }
        }
    }
    
    public void mountEntityToRack() {
        final MCH_Config config = MCH_MOD.config;
        if (!MCH_Config.EnablePutRackInFlying.prmBool) {
            if (this.getCurrentThrottle() > 0.3) {
                return;
            }
            final Block block = MCH_Lib.getBlockY(this, 1, -3, true);
            if (block == null || W_Block.isEqual(block, Blocks.air)) {
                return;
            }
        }
        int countRideEntity = 0;
        for (int sid = 0; sid < this.getSeatNum(); ++sid) {
            final MCH_EntitySeat seat = this.getSeat(sid);
            if (this.getSeatInfo(1 + sid) instanceof MCH_SeatRackInfo && seat != null && seat.riddenByEntity == null) {
                final MCH_SeatRackInfo info = (MCH_SeatRackInfo)this.getSeatInfo(1 + sid);
                final Vec3 rotVec3;
                final Vec3 v = rotVec3 = MCH_Lib.RotVec3(info.getEntryPos().xCoord, info.getEntryPos().yCoord, info.getEntryPos().zCoord, -this.getRotYaw(), -this.getRotPitch(), -this.getRotRoll());
                rotVec3.xCoord += this.posX;
                final Vec3 vec3 = v;
                vec3.yCoord += this.posY;
                final Vec3 vec4 = v;
                vec4.zCoord += this.posZ;
                final AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(v.xCoord, v.yCoord, v.zCoord, v.xCoord, v.yCoord, v.zCoord);
                final float range = info.range;
                final List list = this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this, bb.expand((double)range, (double)range, (double)range));
                for (int i = 0; i < list.size(); ++i) {
                    final Entity entity = list.get(i);
                    if (this.canRideSeatOrRack(1 + sid, entity)) {
                        if (entity instanceof MCH_IEntityCanRideAircraft) {
                            if (((MCH_IEntityCanRideAircraft)entity).canRideAircraft(this, sid, info)) {
                                MCH_Lib.DbgLog(this.worldObj, "MCH_EntityAircraft.mountEntityToRack:%d:%s", sid, entity);
                                entity.mountEntity((Entity)seat);
                                ++countRideEntity;
                                break;
                            }
                        }
                        else if (entity.ridingEntity == null) {
                            final NBTTagCompound nbt = entity.getEntityData();
                            if (nbt.hasKey("CanMountEntity") && nbt.getBoolean("CanMountEntity")) {
                                MCH_Lib.DbgLog(this.worldObj, "MCH_EntityAircraft.mountEntityToRack:%d:%s:%s", sid, entity, entity.getClass());
                                entity.mountEntity((Entity)seat);
                                ++countRideEntity;
                                break;
                            }
                        }
                    }
                }
            }
        }
        if (countRideEntity > 0) {
            W_WorldFunc.DEF_playSoundEffect(this.worldObj, this.posX, this.posY, this.posZ, "random.click", 1.0f, 1.0f);
        }
    }
    
    public void unmountEntityFromRack() {
        int sid = this.getSeatNum() - 1;
        while (sid >= 0) {
            final MCH_EntitySeat seat = this.getSeat(sid);
            if (this.getSeatInfo(sid + 1) instanceof MCH_SeatRackInfo && seat != null && seat.riddenByEntity != null) {
                final MCH_SeatRackInfo info = (MCH_SeatRackInfo)this.getSeatInfo(sid + 1);
                final Entity entity = seat.riddenByEntity;
                Vec3 pos = info.getEntryPos();
                if (entity instanceof MCH_EntityAircraft) {
                    if (pos.zCoord >= this.getAcInfo().bbZ) {
                        pos = pos.addVector(0.0, 0.0, 12.0);
                    }
                    else {
                        pos = pos.addVector(0.0, 0.0, -12.0);
                    }
                }
                final Vec3 v = MCH_Lib.RotVec3(pos.xCoord, pos.yCoord, pos.zCoord, -this.getRotYaw(), -this.getRotPitch(), -this.getRotRoll());
                final MCH_EntitySeat mch_EntitySeat = seat;
                final Entity entity2 = entity;
                final double n = this.posX + v.xCoord;
                entity2.posX = n;
                mch_EntitySeat.posX = n;
                final MCH_EntitySeat mch_EntitySeat2 = seat;
                final Entity entity3 = entity;
                final double n2 = this.posY + v.yCoord;
                entity3.posY = n2;
                mch_EntitySeat2.posY = n2;
                final MCH_EntitySeat mch_EntitySeat3 = seat;
                final Entity entity4 = entity;
                final double n3 = this.posZ + v.zCoord;
                entity4.posZ = n3;
                mch_EntitySeat3.posZ = n3;
                final UnmountReserve ur = new UnmountReserve(entity, entity.posX, entity.posY, entity.posZ);
                ur.cnt = 8;
                this.listUnmountReserve.add(ur);
                entity.mountEntity((Entity)null);
                if (MCH_Lib.getBlockIdY(this, 3, -20) > 0) {
                    MCH_Lib.DbgLog(this.worldObj, "MCH_EntityAircraft.unmountEntityFromRack:%d:%s", sid, entity);
                    break;
                }
                MCH_Lib.DbgLog(this.worldObj, "MCH_EntityAircraft.unmountEntityFromRack:%d Parachute:%s", sid, entity);
                this.dropEntityParachute(entity);
                break;
            }
            else {
                --sid;
            }
        }
    }
    
    public void dropEntityParachute(final Entity entity) {
        entity.motionX = this.motionX;
        entity.motionY = this.motionY;
        entity.motionZ = this.motionZ;
        final MCH_EntityParachute parachute = new MCH_EntityParachute(this.worldObj, entity.posX, entity.posY, entity.posZ);
        parachute.rotationYaw = entity.rotationYaw;
        parachute.motionX = entity.motionX;
        parachute.motionY = entity.motionY;
        parachute.motionZ = entity.motionZ;
        parachute.fallDistance = entity.fallDistance;
        parachute.user = entity;
        parachute.setType(3);
        this.worldObj.spawnEntityInWorld((Entity)parachute);
    }
    
    public void rideRack() {
        if (this.ridingEntity != null) {
            return;
        }
        final AxisAlignedBB bb = this.getBoundingBox();
        final List list = this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this, bb.expand(60.0, 60.0, 60.0));
        for (int i = 0; i < list.size(); ++i) {
            final Entity entity = list.get(i);
            if (entity instanceof MCH_EntityAircraft) {
                final MCH_EntityAircraft ac = (MCH_EntityAircraft)entity;
                if (ac.getAcInfo() != null) {
                    for (int sid = 0; sid < ac.getSeatNum(); ++sid) {
                        final MCH_SeatInfo seatInfo = ac.getSeatInfo(1 + sid);
                        if (seatInfo instanceof MCH_SeatRackInfo) {
                            if (ac.canRideSeatOrRack(1 + sid, entity)) {
                                final MCH_SeatRackInfo info = (MCH_SeatRackInfo)seatInfo;
                                final MCH_EntitySeat seat = ac.getSeat(sid);
                                if (seat != null && seat.riddenByEntity == null) {
                                    final Vec3 v = ac.getTransformedPosition(info.getEntryPos());
                                    final float r = info.range;
                                    if (this.posX >= v.xCoord - r && this.posX <= v.xCoord + r && this.posY >= v.yCoord - r && this.posY <= v.yCoord + r && this.posZ >= v.zCoord - r && this.posZ <= v.zCoord + r && this.canRideAircraft(ac, sid, info)) {
                                        W_WorldFunc.DEF_playSoundEffect(this.worldObj, this.posX, this.posY, this.posZ, "random.click", 1.0f, 1.0f);
                                        this.mountEntity((Entity)seat);
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    public boolean canPutToRack() {
        for (int i = 0; i < this.getSeatNum(); ++i) {
            final MCH_EntitySeat seat = this.getSeat(i);
            final MCH_SeatInfo seatInfo = this.getSeatInfo(i + 1);
            if (seat != null && seat.riddenByEntity == null && seatInfo instanceof MCH_SeatRackInfo) {
                return true;
            }
        }
        return false;
    }
    
    public boolean canDownFromRack() {
        for (int i = 0; i < this.getSeatNum(); ++i) {
            final MCH_EntitySeat seat = this.getSeat(i);
            final MCH_SeatInfo seatInfo = this.getSeatInfo(i + 1);
            if (seat != null && seat.riddenByEntity != null && seatInfo instanceof MCH_SeatRackInfo) {
                return true;
            }
        }
        return false;
    }
    
    public void checkRideRack() {
        if (this.getCountOnUpdate() % 10 != 0) {
            return;
        }
        this.canRideRackStatus = false;
        if (this.ridingEntity != null) {
            return;
        }
        final AxisAlignedBB bb = this.getBoundingBox();
        final List list = this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this, bb.expand(60.0, 60.0, 60.0));
        for (int i = 0; i < list.size(); ++i) {
            final Entity entity = list.get(i);
            if (entity instanceof MCH_EntityAircraft) {
                final MCH_EntityAircraft ac = (MCH_EntityAircraft)entity;
                if (ac.getAcInfo() != null) {
                    for (int sid = 0; sid < ac.getSeatNum(); ++sid) {
                        final MCH_SeatInfo seatInfo = ac.getSeatInfo(1 + sid);
                        if (seatInfo instanceof MCH_SeatRackInfo) {
                            final MCH_SeatRackInfo info = (MCH_SeatRackInfo)seatInfo;
                            final MCH_EntitySeat seat = ac.getSeat(sid);
                            if (seat != null && seat.riddenByEntity == null) {
                                final Vec3 v = ac.getTransformedPosition(info.getEntryPos());
                                final float r = info.range;
                                final boolean rx = this.posX >= v.xCoord - r && this.posX <= v.xCoord + r;
                                final boolean ry = this.posY >= v.yCoord - r && this.posY <= v.yCoord + r;
                                final boolean rz = this.posZ >= v.zCoord - r && this.posZ <= v.zCoord + r;
                                if (this.posX >= v.xCoord - r && this.posX <= v.xCoord + r && this.posY >= v.yCoord - r && this.posY <= v.yCoord + r && this.posZ >= v.zCoord - r && this.posZ <= v.zCoord + r && this.canRideAircraft(ac, sid, info)) {
                                    this.canRideRackStatus = true;
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    public boolean canRideRack() {
        return this.ridingEntity == null && this.canRideRackStatus;
    }
    
    @Override
    public boolean canRideAircraft(final MCH_EntityAircraft ac, final int seatID, final MCH_SeatRackInfo info) {
        if (this.getAcInfo() == null) {
            return false;
        }
        if (ac.ridingEntity != null) {
            return false;
        }
        if (this.ridingEntity != null) {
            return false;
        }
        boolean canRide = false;
        for (final String s : info.names) {
            if (s.equalsIgnoreCase(this.getAcInfo().name) || s.equalsIgnoreCase(this.getAcInfo().getKindName())) {
                canRide = true;
                break;
            }
        }
        if (!canRide) {
            for (final MCH_AircraftInfo.RideRack rr : this.getAcInfo().rideRacks) {
                final int id = ac.getAcInfo().getNumSeat() - 1 + (rr.rackID - 1);
                if (id == seatID && rr.name.equalsIgnoreCase(ac.getAcInfo().name)) {
                    final MCH_EntitySeat seat = ac.getSeat(ac.getAcInfo().getNumSeat() - 1 + rr.rackID - 1);
                    if (seat != null && seat.riddenByEntity == null) {
                        canRide = true;
                        break;
                    }
                    continue;
                }
            }
            if (!canRide) {
                return false;
            }
        }
        final MCH_EntitySeat[] arr$2 = this.getSeats();
        for (int len$ = arr$2.length, i$ = 0; i$ < len$; ++i$) {
            final MCH_EntitySeat seat = arr$2[i$];
            if (seat != null && seat.riddenByEntity instanceof MCH_IEntityCanRideAircraft) {
                return false;
            }
        }
        return true;
    }
    
    public boolean isMountedEntity(final Entity entity) {
        return entity != null && this.isMountedEntity(W_Entity.getEntityId(entity));
    }
    
    public EntityPlayer getFirstMountPlayer() {
        if (this.getRiddenByEntity() instanceof EntityPlayer) {
            return (EntityPlayer)this.getRiddenByEntity();
        }
        for (final MCH_EntitySeat seat : this.getSeats()) {
            if (seat != null && seat.riddenByEntity instanceof EntityPlayer) {
                return (EntityPlayer)seat.riddenByEntity;
            }
        }
        return null;
    }
    
    public boolean isMountedSameTeamEntity(final EntityLivingBase player) {
        if (player == null || player.getTeam() == null) {
            return false;
        }
        if (this.riddenByEntity instanceof EntityLivingBase && player.isOnSameTeam((EntityLivingBase)this.riddenByEntity)) {
            return true;
        }
        for (final MCH_EntitySeat seat : this.getSeats()) {
            if (seat != null && seat.riddenByEntity instanceof EntityLivingBase && player.isOnSameTeam((EntityLivingBase)seat.riddenByEntity)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isMountedOtherTeamEntity(final EntityLivingBase player) {
        if (player == null) {
            return false;
        }
        EntityLivingBase target = null;
        if (this.riddenByEntity instanceof EntityLivingBase) {
            target = (EntityLivingBase)this.riddenByEntity;
            if (player.getTeam() != null && target.getTeam() != null && !player.isOnSameTeam(target)) {
                return true;
            }
        }
        for (final MCH_EntitySeat seat : this.getSeats()) {
            if (seat != null && seat.riddenByEntity instanceof EntityLivingBase) {
                target = (EntityLivingBase)seat.riddenByEntity;
                if (player.getTeam() != null && target.getTeam() != null && !player.isOnSameTeam(target)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean isMountedEntity(final int entityId) {
        if (W_Entity.getEntityId(this.riddenByEntity) == entityId) {
            return true;
        }
        for (final MCH_EntitySeat seat : this.getSeats()) {
            if (seat != null && seat.riddenByEntity != null && W_Entity.getEntityId(seat.riddenByEntity) == entityId) {
                return true;
            }
        }
        return false;
    }
    
    public void onInteractFirst(final EntityPlayer player) {
    }
    
    public boolean checkTeam(final EntityPlayer player) {
        for (int i = 0; i < 1 + this.getSeatNum(); ++i) {
            final Entity entity = this.getEntityBySeatId(i);
            if (entity instanceof EntityPlayer || entity instanceof MCH_EntityGunner) {
                final EntityLivingBase riddenEntity = (EntityLivingBase)entity;
                if (riddenEntity.getTeam() != null && !riddenEntity.isOnSameTeam((EntityLivingBase)player)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public boolean interactFirst(final EntityPlayer player, final boolean ss) {
        this.switchSeat = ss;
        final boolean ret = this.interactFirst(player);
        this.switchSeat = false;
        return ret;
    }
    
    public boolean interactFirst(final EntityPlayer player) {
        if (this.isDestroyed()) {
            return false;
        }
        if (this.getAcInfo() == null) {
            return false;
        }
        if (!this.checkTeam(player)) {
            return false;
        }
        final ItemStack itemStack = player.getCurrentEquippedItem();
        if (itemStack != null && itemStack.getItem() instanceof MCH_ItemWrench) {
            if (!this.worldObj.isRemote && player.isSneaking()) {
                this.switchNextTextureName();
            }
            return false;
        }
        if (itemStack != null && itemStack.getItem() instanceof MCH_ItemSpawnGunner) {
            return false;
        }
        if (player.isSneaking()) {
            super.openInventory(player);
            return false;
        }
        if (!this.getAcInfo().canRide) {
            return false;
        }
        if (this.riddenByEntity != null || this.isUAV()) {
            return this.interactFirstSeat(player);
        }
        if (player.ridingEntity instanceof MCH_EntitySeat) {
            return false;
        }
        if (!this.canRideSeatOrRack(0, (Entity)player)) {
            return false;
        }
        if (!this.switchSeat) {
            if (this.getAcInfo().haveCanopy() && this.isCanopyClose()) {
                this.openCanopy();
                return false;
            }
            if (this.getModeSwitchCooldown() > 0) {
                return false;
            }
        }
        this.closeCanopy();
        this.riddenByEntity = null;
        this.lastRiddenByEntity = null;
        this.initRadar();
        if (!this.worldObj.isRemote) {
            player.mountEntity((Entity)this);
            if (!this.keepOnRideRotation) {
                this.mountMobToSeats();
            }
        }
        else {
            this.updateClientSettings(0);
        }
        this.setCameraId(0);
        this.initPilotWeapon();
        this.lowPassPartialTicks.clear();
        if (this.getAcInfo().name.equalsIgnoreCase("uh-1c")) {
            MCH_Achievement.addStat(this.riddenByEntity, MCH_Achievement.rideValkyries, 1);
        }
        this.onInteractFirst(player);
        return true;
    }
    
    public boolean canRideSeatOrRack(final int seatId, final Entity entity) {
        if (this.getAcInfo() == null) {
            return false;
        }
        for (final Integer[] a : this.getAcInfo().exclusionSeatList) {
            if (Arrays.asList(a).contains(seatId)) {
                for (final int id : a) {
                    if (this.getEntityBySeatId(id) != null) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    public void updateClientSettings(final int seatId) {
        final MCH_Config config = MCH_MOD.config;
        this.cs_dismountAll = MCH_Config.DismountAll.prmBool;
        final MCH_Config config2 = MCH_MOD.config;
        this.cs_heliAutoThrottleDown = MCH_Config.AutoThrottleDownHeli.prmBool;
        final MCH_Config config3 = MCH_MOD.config;
        this.cs_planeAutoThrottleDown = MCH_Config.AutoThrottleDownPlane.prmBool;
        final MCH_Config config4 = MCH_MOD.config;
        this.cs_tankAutoThrottleDown = MCH_Config.AutoThrottleDownTank.prmBool;
        this.camera.setShaderSupport(seatId, W_EntityRenderer.isShaderSupport());
        MCH_PacketNotifyClientSetting.send();
    }
    
    @Override
    public boolean canLockEntity(final Entity entity) {
        return !this.isMountedEntity(entity);
    }
    
    public void switchNextSeat(final Entity entity) {
        if (entity == null) {
            return;
        }
        if (this.seats == null || this.seats.length <= 0) {
            return;
        }
        if (!this.isMountedEntity(entity)) {
            return;
        }
        boolean isFound = false;
        int sid = 1;
        for (final MCH_EntitySeat seat : this.seats) {
            if (seat != null) {
                if (this.getSeatInfo(sid) instanceof MCH_SeatRackInfo) {
                    break;
                }
                if (W_Entity.isEqual(seat.riddenByEntity, entity)) {
                    isFound = true;
                }
                else if (isFound && seat.riddenByEntity == null) {
                    entity.mountEntity((Entity)seat);
                    return;
                }
                ++sid;
            }
        }
        sid = 1;
        final MCH_EntitySeat[] arr$ = this.seats;
        final int len$ = arr$.length;
        int i$ = 0;
        while (i$ < len$) {
            final MCH_EntitySeat seat = arr$[i$];
            if (seat != null && seat.riddenByEntity == null) {
                if (this.getSeatInfo(sid) instanceof MCH_SeatRackInfo) {
                    break;
                }
                entity.mountEntity((Entity)seat);
                this.onMountPlayerSeat(seat, entity);
            }
            else {
                ++sid;
                ++i$;
            }
        }
    }
    
    public void switchPrevSeat(final Entity entity) {
        if (entity == null) {
            return;
        }
        if (this.seats == null || this.seats.length <= 0) {
            return;
        }
        if (!this.isMountedEntity(entity)) {
            return;
        }
        boolean isFound = false;
        for (int i = this.seats.length - 1; i >= 0; --i) {
            final MCH_EntitySeat seat = this.seats[i];
            if (seat != null) {
                if (W_Entity.isEqual(seat.riddenByEntity, entity)) {
                    isFound = true;
                }
                else if (isFound && seat.riddenByEntity == null) {
                    entity.mountEntity((Entity)seat);
                    return;
                }
            }
        }
        for (int i = this.seats.length - 1; i >= 0; --i) {
            final MCH_EntitySeat seat = this.seats[i];
            if (!(this.getSeatInfo(i + 1) instanceof MCH_SeatRackInfo)) {
                if (seat != null && seat.riddenByEntity == null) {
                    entity.mountEntity((Entity)seat);
                    return;
                }
            }
        }
    }
    
    public Entity[] getParts() {
        return this.partEntities;
    }
    
    public float getSoundVolume() {
        return 1.0f;
    }
    
    public float getSoundPitch() {
        return 1.0f;
    }
    
    public abstract String getDefaultSoundName();
    
    public String getSoundName() {
        if (this.getAcInfo() == null) {
            return "";
        }
        return this.getAcInfo().soundMove.isEmpty() ? this.getDefaultSoundName() : this.getAcInfo().soundMove;
    }
    
    @Override
    public boolean isSkipNormalRender() {
        return this.ridingEntity instanceof MCH_EntitySeat;
    }
    
    public boolean isRenderBullet(final Entity entity, final Entity rider) {
        return !this.isCameraView(rider) || !W_Entity.isEqual(this.getTVMissile(), entity) || !W_Entity.isEqual(this.getTVMissile().shootingEntity, rider);
    }
    
    public boolean isCameraView(final Entity entity) {
        return this.getIsGunnerMode(entity) || this.isUAV();
    }
    
    public void updateCamera(final double x, final double y, final double z) {
        if (!this.worldObj.isRemote) {
            return;
        }
        if (this.getTVMissile() != null) {
            this.camera.setPosition(this.TVmissile.posX, this.TVmissile.posY, this.TVmissile.posZ);
            this.camera.setCameraZoom(1.0f);
            this.TVmissile.isSpawnParticle = !this.isMissileCameraMode(this.TVmissile.shootingEntity);
        }
        else {
            this.setTVMissile(null);
            final MCH_AircraftInfo.CameraPosition cpi = this.getCameraPosInfo();
            final Vec3 cp = (cpi != null) ? cpi.pos : Vec3.createVectorHelper(0.0, 0.0, 0.0);
            final Vec3 v = MCH_Lib.RotVec3(cp, -this.getRotYaw(), -this.getRotPitch(), -this.getRotRoll());
            this.camera.setPosition(x + v.xCoord, y + v.yCoord, z + v.zCoord);
        }
    }
    
    public void updateCameraRotate(final float yaw, final float pitch) {
        this.camera.prevRotationYaw = this.camera.rotationYaw;
        this.camera.prevRotationPitch = this.camera.rotationPitch;
        this.camera.rotationYaw = yaw;
        this.camera.rotationPitch = pitch;
    }
    
    public void updatePartCameraRotate() {
        if (this.worldObj.isRemote) {
            Entity e = this.getEntityBySeatId(1);
            if (e == null) {
                e = this.getRiddenByEntity();
            }
            if (e != null) {
                this.camera.partRotationYaw = e.rotationYaw;
                final float pitch = e.rotationPitch;
                this.camera.prevPartRotationYaw = this.camera.partRotationYaw;
                this.camera.prevPartRotationPitch = this.camera.partRotationPitch;
                this.camera.partRotationPitch = pitch;
            }
        }
    }
    
    public void setTVMissile(final MCH_EntityTvMissile entity) {
        this.TVmissile = entity;
    }
    
    public MCH_EntityTvMissile getTVMissile() {
        return (this.TVmissile != null && !this.TVmissile.isDead) ? this.TVmissile : null;
    }
    
    public MCH_WeaponSet[] createWeapon(final int seat_num) {
        this.currentWeaponID = new int[seat_num];
        for (int i = 0; i < this.currentWeaponID.length; ++i) {
            this.currentWeaponID[i] = -1;
        }
        if (this.getAcInfo() == null || this.getAcInfo().weaponSetList.size() <= 0 || seat_num <= 0) {
            return new MCH_WeaponSet[] { this.dummyWeapon };
        }
        final MCH_WeaponSet[] weaponSetArray = new MCH_WeaponSet[this.getAcInfo().weaponSetList.size()];
        for (int j = 0; j < this.getAcInfo().weaponSetList.size(); ++j) {
            final MCH_AircraftInfo.WeaponSet ws = this.getAcInfo().weaponSetList.get(j);
            final MCH_WeaponBase[] wb = new MCH_WeaponBase[ws.weapons.size()];
            for (int k = 0; k < ws.weapons.size(); ++k) {
                wb[k] = MCH_WeaponCreator.createWeapon(this.worldObj, ws.type, ws.weapons.get(k).pos, ws.weapons.get(k).yaw, ws.weapons.get(k).pitch, this, ws.weapons.get(k).turret);
                wb[k].aircraft = this;
            }
            if (wb.length > 0 && wb[0] != null) {
                final float defYaw = ws.weapons.get(0).defaultYaw;
                weaponSetArray[j] = new MCH_WeaponSet(wb);
                weaponSetArray[j].prevRotationYaw = defYaw;
                weaponSetArray[j].rotationYaw = defYaw;
                weaponSetArray[j].defaultRotationYaw = defYaw;
            }
        }
        return weaponSetArray;
    }
    
    public void switchWeapon(final Entity entity, int id) {
        final int sid = this.getSeatIdByEntity(entity);
        if (!this.isValidSeatID(sid)) {
            return;
        }
        final int beforeWeaponID = this.currentWeaponID[sid];
        if (this.getWeaponNum() <= 0 || this.currentWeaponID.length <= 0) {
            return;
        }
        if (id < 0) {
            this.currentWeaponID[sid] = -1;
        }
        if (id >= this.getWeaponNum()) {
            id = this.getWeaponNum() - 1;
        }
        MCH_Lib.DbgLog(this.worldObj, "switchWeapon:" + W_Entity.getEntityId(entity) + " -> " + id, new Object[0]);
        this.getCurrentWeapon(entity).reload();
        this.currentWeaponID[sid] = id;
        final MCH_WeaponSet ws = this.getCurrentWeapon(entity);
        ws.onSwitchWeapon(this.worldObj.isRemote, this.isInfinityAmmo(entity));
        if (!this.worldObj.isRemote) {
            MCH_PacketNotifyWeaponID.send(this, sid, id, ws.getAmmoNum(), ws.getRestAllAmmoNum());
        }
    }
    
    public void updateWeaponID(final int sid, int id) {
        if (sid < 0 || sid >= this.currentWeaponID.length) {
            return;
        }
        if (this.getWeaponNum() <= 0 || this.currentWeaponID.length <= 0) {
            return;
        }
        if (id < 0) {
            this.currentWeaponID[sid] = -1;
        }
        if (id >= this.getWeaponNum()) {
            id = this.getWeaponNum() - 1;
        }
        MCH_Lib.DbgLog(this.worldObj, "switchWeapon:seatID=" + sid + ", WeaponID=" + id, new Object[0]);
        this.currentWeaponID[sid] = id;
    }
    
    public void updateWeaponRestAmmo(final int id, final int num) {
        if (id < this.getWeaponNum()) {
            this.getWeapon(id).setRestAllAmmoNum(num);
        }
    }
    
    public MCH_WeaponSet getWeaponByName(final String name) {
        for (final MCH_WeaponSet ws : this.weapons) {
            if (ws.isEqual(name)) {
                return ws;
            }
        }
        return null;
    }
    
    public int getWeaponIdByName(final String name) {
        int id = 0;
        for (final MCH_WeaponSet ws : this.weapons) {
            if (ws.isEqual(name)) {
                return id;
            }
            ++id;
        }
        return -1;
    }
    
    public void reloadAllWeapon() {
        for (int i = 0; i < this.getWeaponNum(); ++i) {
            this.getWeapon(i).reloadMag();
        }
    }
    
    public MCH_WeaponSet getFirstSeatWeapon() {
        if (this.currentWeaponID != null && this.currentWeaponID.length > 0 && this.currentWeaponID[0] >= 0) {
            return this.getWeapon(this.currentWeaponID[0]);
        }
        return this.getWeapon(0);
    }
    
    public void initCurrentWeapon(final Entity entity) {
        final int sid = this.getSeatIdByEntity(entity);
        MCH_Lib.DbgLog(this.worldObj, "initCurrentWeapon:" + W_Entity.getEntityId(entity) + ":%d", sid);
        if (sid < 0 || sid >= this.currentWeaponID.length) {
            return;
        }
        this.currentWeaponID[sid] = -1;
        if (entity instanceof EntityPlayer || entity instanceof MCH_EntityGunner) {
            this.currentWeaponID[sid] = this.getNextWeaponID(entity, 1);
            this.switchWeapon(entity, this.getCurrentWeaponID(entity));
            if (this.worldObj.isRemote) {
                MCH_PacketIndNotifyAmmoNum.send(this, -1);
            }
        }
    }
    
    public void initPilotWeapon() {
        this.currentWeaponID[0] = -1;
    }
    
    public MCH_WeaponSet getCurrentWeapon(final Entity entity) {
        return this.getWeapon(this.getCurrentWeaponID(entity));
    }
    
    protected MCH_WeaponSet getWeapon(final int id) {
        if (id < 0 || this.weapons.length <= 0 || id >= this.weapons.length) {
            return this.dummyWeapon;
        }
        return this.weapons[id];
    }
    
    public int getWeaponIDBySeatID(final int sid) {
        if (sid < 0 || sid >= this.currentWeaponID.length) {
            return -1;
        }
        return this.currentWeaponID[sid];
    }
    
    public double getLandInDistance(final Entity user) {
        if (this.lastCalcLandInDistanceCount != this.getCountOnUpdate() && this.getCountOnUpdate() % 5 == 0) {
            this.lastCalcLandInDistanceCount = this.getCountOnUpdate();
            final MCH_WeaponParam prm = new MCH_WeaponParam();
            prm.setPosition(this.posX, this.posY, this.posZ);
            prm.entity = this;
            prm.user = user;
            prm.isInfinity = this.isInfinityAmmo(prm.user);
            if (prm.user != null) {
                final MCH_WeaponSet currentWs = this.getCurrentWeapon(prm.user);
                if (currentWs != null) {
                    final int sid = this.getSeatIdByEntity(prm.user);
                    if (this.getAcInfo().getWeaponSetById(sid) != null) {
                        prm.isTurret = this.getAcInfo().getWeaponSetById(sid).weapons.get(0).turret;
                    }
                    this.lastLandInDistance = currentWs.getLandInDistance(prm);
                }
            }
        }
        return this.lastLandInDistance;
    }
    
    public boolean useCurrentWeapon(final Entity user) {
        final MCH_WeaponParam prm = new MCH_WeaponParam();
        prm.setPosition(this.posX, this.posY, this.posZ);
        prm.entity = this;
        prm.user = user;
        return this.useCurrentWeapon(prm);
    }
    
    public boolean useCurrentWeapon(final MCH_WeaponParam prm) {
        prm.isInfinity = this.isInfinityAmmo(prm.user);
        if (prm.user != null) {
            final MCH_WeaponSet currentWs = this.getCurrentWeapon(prm.user);
            if (currentWs != null && currentWs.canUse()) {
                final int sid = this.getSeatIdByEntity(prm.user);
                if (this.getAcInfo().getWeaponSetById(sid) != null) {
                    prm.isTurret = this.getAcInfo().getWeaponSetById(sid).weapons.get(0).turret;
                }
                final int lastUsedIndex = currentWs.getCurrentWeaponIndex();
                if (currentWs.use(prm)) {
                    for (final MCH_WeaponSet ws : this.weapons) {
                        if (ws != currentWs && !ws.getInfo().group.isEmpty() && ws.getInfo().group.equals(currentWs.getInfo().group)) {
                            ws.waitAndReloadByOther(prm.reload);
                        }
                    }
                    if (!this.worldObj.isRemote) {
                        int shift = 0;
                        for (final MCH_WeaponSet ws2 : this.weapons) {
                            if (ws2 == currentWs) {
                                break;
                            }
                            shift += ws2.getWeaponNum();
                        }
                        shift += lastUsedIndex;
                        this.useWeaponStat |= ((shift < 32) ? (1 << shift) : 0);
                    }
                    return true;
                }
            }
        }
        return false;
    }
    
    public void switchCurrentWeaponMode(final Entity entity) {
        this.getCurrentWeapon(entity).switchMode();
    }
    
    public int getWeaponNum() {
        return this.weapons.length;
    }
    
    public int getCurrentWeaponID(final Entity entity) {
        if (!(entity instanceof EntityPlayer) && !(entity instanceof MCH_EntityGunner)) {
            return -1;
        }
        final int id = this.getSeatIdByEntity(entity);
        return (id >= 0 && id < this.currentWeaponID.length) ? this.currentWeaponID[id] : -1;
    }
    
    public int getNextWeaponID(final Entity entity, final int step) {
        if (this.getAcInfo() == null) {
            return -1;
        }
        final int sid = this.getSeatIdByEntity(entity);
        if (sid < 0) {
            return -1;
        }
        int id = this.getCurrentWeaponID(entity);
        int i;
        for (i = 0; i < this.getWeaponNum(); ++i) {
            if (step >= 0) {
                id = (id + 1) % this.getWeaponNum();
            }
            else {
                id = ((id > 0) ? (id - 1) : (this.getWeaponNum() - 1));
            }
            final MCH_AircraftInfo.Weapon w = this.getAcInfo().getWeaponById(id);
            if (w != null) {
                final MCH_WeaponInfo wi = this.getWeaponInfoById(id);
                final int wpsid = this.getWeaponSeatID(wi, w);
                if (wpsid < this.getSeatNum() + 1 + 1) {
                    if (wpsid == sid) {
                        break;
                    }
                    if (sid == 0 && w.canUsePilot && !(this.getEntityBySeatId(wpsid) instanceof EntityPlayer) && !(this.getEntityBySeatId(wpsid) instanceof MCH_EntityGunner)) {
                        break;
                    }
                }
            }
        }
        if (i >= this.getWeaponNum()) {
            return -1;
        }
        MCH_Lib.DbgLog(this.worldObj, "getNextWeaponID:%d:->%d", W_Entity.getEntityId(entity), id);
        return id;
    }
    
    public int getWeaponSeatID(final MCH_WeaponInfo wi, final MCH_AircraftInfo.Weapon w) {
        if (wi != null && (wi.target & 0xC3) == 0x0 && wi.type.isEmpty() && (MCH_MOD.proxy.isSinglePlayer() || MCH_Config.TestMode.prmBool)) {
            return 1000;
        }
        return w.seatID;
    }
    
    public boolean isMissileCameraMode(final Entity entity) {
        return this.getTVMissile() != null && this.isCameraView(entity);
    }
    
    public boolean isPilotReloading() {
        return this.getCommonStatus(2) || this.supplyAmmoWait > 0;
    }
    
    public int getUsedWeaponStat() {
        if (this.getAcInfo() == null) {
            return 0;
        }
        if (this.getAcInfo().getWeaponNum() <= 0) {
            return 0;
        }
        int stat = 0;
        int i = 0;
        for (final MCH_WeaponSet w : this.weapons) {
            if (i >= 32) {
                break;
            }
            for (int wi = 0; wi < w.getWeaponNum() && i < 32; ++i, ++wi) {
                stat |= (w.isUsed(wi) ? (1 << i) : 0);
            }
        }
        return stat;
    }
    
    public boolean isWeaponNotCooldown(final MCH_WeaponSet checkWs, final int index) {
        if (this.getAcInfo() == null) {
            return false;
        }
        if (this.getAcInfo().getWeaponNum() <= 0) {
            return false;
        }
        int shift = 0;
        for (final MCH_WeaponSet ws : this.weapons) {
            if (ws == checkWs) {
                break;
            }
            shift += ws.getWeaponNum();
        }
        shift += index;
        return shift < 32 && (this.useWeaponStat & 1 << shift) != 0x0;
    }
    
    public void updateWeapons() {
        if (this.getAcInfo() == null) {
            return;
        }
        if (this.getAcInfo().getWeaponNum() <= 0) {
            return;
        }
        final int prevUseWeaponStat = this.useWeaponStat;
        if (!this.worldObj.isRemote) {
            this.useWeaponStat |= this.getUsedWeaponStat();
            this.getDataWatcher().updateObject(24, (Object)new Integer(this.useWeaponStat));
            this.useWeaponStat = 0;
        }
        else {
            this.useWeaponStat = this.getDataWatcher().getWatchableObjectInt(24);
        }
        final float yaw = MathHelper.wrapAngleTo180_float(this.getRotYaw());
        final float pitch = MathHelper.wrapAngleTo180_float(this.getRotPitch());
        int id = 0;
        for (int wid = 0; wid < this.weapons.length; ++wid) {
            final MCH_WeaponSet w = this.weapons[wid];
            boolean isLongDelay = false;
            if (w.getFirstWeapon() != null) {
                isLongDelay = w.isLongDelayWeapon();
            }
            boolean isSelected = false;
            for (final int swid : this.currentWeaponID) {
                if (swid == wid) {
                    isSelected = true;
                    break;
                }
            }
            boolean isWpnUsed = false;
            for (int index = 0; index < w.getWeaponNum(); ++index) {
                final boolean isPrevUsed = id < 32 && (prevUseWeaponStat & 1 << id) != 0x0;
                boolean isUsed = id < 32 && (this.useWeaponStat & 1 << id) != 0x0;
                if (isLongDelay && isPrevUsed && isUsed) {
                    isUsed = false;
                }
                isWpnUsed |= isUsed;
                if (!isPrevUsed && isUsed) {
                    final float recoil = w.getInfo().recoil;
                    if (recoil > 0.0f) {
                        this.recoilCount = 30;
                        this.recoilValue = recoil;
                        this.recoilYaw = w.rotationYaw;
                    }
                }
                if (this.worldObj.isRemote && isUsed) {
                    final Vec3 wrv = MCH_Lib.RotVec3(0.0, 0.0, -1.0, -w.rotationYaw - yaw, -w.rotationPitch);
                    final Vec3 spv = w.getCurrentWeapon().getShotPos(this);
                    this.spawnParticleMuzzleFlash(this.worldObj, w.getInfo(), this.posX + spv.xCoord, this.posY + spv.yCoord, this.posZ + spv.zCoord, wrv);
                }
                w.updateWeapon(this, isUsed, index);
                ++id;
            }
            w.update(this, isSelected, isWpnUsed);
            final MCH_AircraftInfo.Weapon wi = this.getAcInfo().getWeaponById(wid);
            if (wi != null && !this.isDestroyed()) {
                Entity entity = this.getEntityBySeatId(this.getWeaponSeatID(this.getWeaponInfoById(wid), wi));
                if (wi.canUsePilot && !(entity instanceof EntityPlayer) && !(entity instanceof MCH_EntityGunner)) {
                    entity = this.getEntityBySeatId(0);
                }
                if (entity instanceof EntityPlayer || entity instanceof MCH_EntityGunner) {
                    if ((int)wi.minYaw != 0 || (int)wi.maxYaw != 0) {
                        final float ty = wi.turret ? (MathHelper.wrapAngleTo180_float(this.getLastRiderYaw()) - yaw) : 0.0f;
                        final float ey = MathHelper.wrapAngleTo180_float(entity.rotationYaw - yaw - wi.defaultYaw - ty);
                        if (Math.abs((int)wi.minYaw) < 360 && Math.abs((int)wi.maxYaw) < 360) {
                            final float targetYaw = MCH_Lib.RNG(ey, wi.minYaw, wi.maxYaw);
                            float wy = w.rotationYaw - wi.defaultYaw - ty;
                            if (targetYaw < wy) {
                                if (wy - targetYaw > 15.0f) {
                                    wy -= 15.0f;
                                }
                                else {
                                    wy = targetYaw;
                                }
                            }
                            else if (targetYaw > wy) {
                                if (targetYaw - wy > 15.0f) {
                                    wy += 15.0f;
                                }
                                else {
                                    wy = targetYaw;
                                }
                            }
                            w.rotationYaw = wy + wi.defaultYaw + ty;
                        }
                        else {
                            w.rotationYaw = ey + ty;
                        }
                    }
                    final float ep = MathHelper.wrapAngleTo180_float(entity.rotationPitch - pitch);
                    w.rotationPitch = MCH_Lib.RNG(ep, wi.minPitch, wi.maxPitch);
                    w.rotationTurretYaw = 0.0f;
                }
                else {
                    w.rotationTurretYaw = this.getLastRiderYaw() - this.getRotYaw();
                    if (this.getTowedChainEntity() != null || this.ridingEntity != null) {
                        w.rotationYaw = 0.0f;
                    }
                }
            }
        }
        this.updateWeaponBay();
        if (this.hitStatus > 0) {
            --this.hitStatus;
        }
    }
    
    public void updateWeaponsRotation() {
        if (this.getAcInfo() == null) {
            return;
        }
        if (this.getAcInfo().getWeaponNum() <= 0) {
            return;
        }
        if (this.isDestroyed()) {
            return;
        }
        final float yaw = MathHelper.wrapAngleTo180_float(this.getRotYaw());
        final float pitch = MathHelper.wrapAngleTo180_float(this.getRotPitch());
        for (int wid = 0; wid < this.weapons.length; ++wid) {
            final MCH_WeaponSet w = this.weapons[wid];
            final MCH_AircraftInfo.Weapon wi = this.getAcInfo().getWeaponById(wid);
            if (wi != null) {
                Entity entity = this.getEntityBySeatId(this.getWeaponSeatID(this.getWeaponInfoById(wid), wi));
                if (wi.canUsePilot && !(entity instanceof EntityPlayer) && !(entity instanceof MCH_EntityGunner)) {
                    entity = this.getEntityBySeatId(0);
                }
                if (entity instanceof EntityPlayer || entity instanceof MCH_EntityGunner) {
                    if ((int)wi.minYaw != 0 || (int)wi.maxYaw != 0) {
                        final float ty = wi.turret ? (MathHelper.wrapAngleTo180_float(this.getLastRiderYaw()) - yaw) : 0.0f;
                        final float ey = MathHelper.wrapAngleTo180_float(entity.rotationYaw - yaw - wi.defaultYaw - ty);
                        if (Math.abs((int)wi.minYaw) < 360 && Math.abs((int)wi.maxYaw) < 360) {
                            final float targetYaw = MCH_Lib.RNG(ey, wi.minYaw, wi.maxYaw);
                            float wy = w.rotationYaw - wi.defaultYaw - ty;
                            if (targetYaw < wy) {
                                if (wy - targetYaw > 15.0f) {
                                    wy -= 15.0f;
                                }
                                else {
                                    wy = targetYaw;
                                }
                            }
                            else if (targetYaw > wy) {
                                if (targetYaw - wy > 15.0f) {
                                    wy += 15.0f;
                                }
                                else {
                                    wy = targetYaw;
                                }
                            }
                            w.rotationYaw = wy + wi.defaultYaw + ty;
                        }
                        else {
                            w.rotationYaw = ey + ty;
                        }
                    }
                    final float ep = MathHelper.wrapAngleTo180_float(entity.rotationPitch - pitch);
                    w.rotationPitch = MCH_Lib.RNG(ep, wi.minPitch, wi.maxPitch);
                    w.rotationTurretYaw = 0.0f;
                }
                else {
                    w.rotationTurretYaw = this.getLastRiderYaw() - this.getRotYaw();
                }
            }
            w.prevRotationYaw = w.rotationYaw;
        }
    }
    
    private void spawnParticleMuzzleFlash(final World w, final MCH_WeaponInfo wi, final double px, final double py, final double pz, final Vec3 wrv) {
        if (wi.listMuzzleFlashSmoke != null) {
            for (final MCH_WeaponInfo.MuzzleFlash mf : wi.listMuzzleFlashSmoke) {
                final double x = px + -wrv.xCoord * mf.dist;
                final double y = py + -wrv.yCoord * mf.dist;
                final double z = pz + -wrv.zCoord * mf.dist;
                final MCH_ParticleParam p = new MCH_ParticleParam(w, "smoke", px, py, pz);
                p.size = mf.size;
                for (int i = 0; i < mf.num; ++i) {
                    p.a = mf.a * 0.9f + w.rand.nextFloat() * 0.1f;
                    final float color = w.rand.nextFloat() * 0.1f;
                    p.r = color + mf.r * 0.9f;
                    p.g = color + mf.g * 0.9f;
                    p.b = color + mf.b * 0.9f;
                    p.age = (int)(mf.age + 0.1 * mf.age * w.rand.nextFloat());
                    p.posX = x + (w.rand.nextDouble() - 0.5) * mf.range;
                    p.posY = y + (w.rand.nextDouble() - 0.5) * mf.range;
                    p.posZ = z + (w.rand.nextDouble() - 0.5) * mf.range;
                    p.motionX = w.rand.nextDouble() * ((p.posX < x) ? -0.2 : 0.2);
                    p.motionY = w.rand.nextDouble() * ((p.posY < y) ? -0.03 : 0.03);
                    p.motionZ = w.rand.nextDouble() * ((p.posZ < z) ? -0.2 : 0.2);
                    MCH_ParticlesUtil.spawnParticle(p);
                }
            }
        }
        if (wi.listMuzzleFlash != null) {
            for (final MCH_WeaponInfo.MuzzleFlash mf : wi.listMuzzleFlash) {
                final float color2 = this.rand.nextFloat() * 0.1f + 0.9f;
                MCH_ParticlesUtil.spawnParticleExplode(this.worldObj, px + -wrv.xCoord * mf.dist, py + -wrv.yCoord * mf.dist, pz + -wrv.zCoord * mf.dist, mf.size, color2 * mf.r, color2 * mf.g, color2 * mf.b, mf.a, mf.age + w.rand.nextInt(3));
            }
        }
    }
    
    private void updateWeaponBay() {
        for (int i = 0; i < this.weaponBays.length; ++i) {
            final WeaponBay wb = this.weaponBays[i];
            final MCH_AircraftInfo.WeaponBay info = this.getAcInfo().partWeaponBay.get(i);
            boolean isSelected = false;
            for (final int wid : info.weaponIds) {
                for (int sid = 0; sid < this.currentWeaponID.length; ++sid) {
                    if (wid == this.currentWeaponID[sid] && this.getEntityBySeatId(sid) != null) {
                        isSelected = true;
                    }
                }
            }
            wb.prevRot = wb.rot;
            if (isSelected) {
                if (wb.rot < 90.0f) {
                    final WeaponBay weaponBay = wb;
                    weaponBay.rot += 3.0f;
                }
                if (wb.rot >= 90.0f) {
                    wb.rot = 90.0f;
                }
            }
            else {
                if (wb.rot > 0.0f) {
                    final WeaponBay weaponBay2 = wb;
                    weaponBay2.rot -= 3.0f;
                }
                if (wb.rot <= 0.0f) {
                    wb.rot = 0.0f;
                }
            }
        }
    }
    
    public int getHitStatus() {
        return this.hitStatus;
    }
    
    public int getMaxHitStatus() {
        return 15;
    }
    
    public void hitBullet() {
        this.hitStatus = this.getMaxHitStatus();
    }
    
    public void initRotationYaw(final float yaw) {
        this.rotationYaw = yaw;
        this.prevRotationYaw = yaw;
        this.lastRiderYaw = yaw;
        this.lastSearchLightYaw = yaw;
        for (final MCH_WeaponSet w : this.weapons) {
            w.rotationYaw = w.defaultRotationYaw;
            w.rotationPitch = 0.0f;
        }
    }
    
    public MCH_AircraftInfo getAcInfo() {
        return this.acInfo;
    }
    
    public abstract Item getItem();
    
    public void setAcInfo(final MCH_AircraftInfo info) {
        this.acInfo = info;
        if (info != null) {
            this.partHatch = this.createHatch();
            this.partCanopy = this.createCanopy();
            this.partLandingGear = this.createLandingGear();
            this.weaponBays = this.createWeaponBays();
            this.rotPartRotation = new float[info.partRotPart.size()];
            this.prevRotPartRotation = new float[info.partRotPart.size()];
            this.extraBoundingBox = this.createExtraBoundingBox();
            this.partEntities = this.createParts();
            this.stepHeight = info.stepHeight;
        }
    }
    
    public MCH_BoundingBox[] createExtraBoundingBox() {
        final MCH_BoundingBox[] ar = new MCH_BoundingBox[this.getAcInfo().extraBoundingBox.size()];
        int i = 0;
        for (final MCH_BoundingBox bb : this.getAcInfo().extraBoundingBox) {
            ar[i] = bb.copy();
            ++i;
        }
        return ar;
    }
    
    public Entity[] createParts() {
        final Entity[] list = { this.partEntities[0] };
        return list;
    }
    
    public void updateUAV() {
        if (!this.isUAV()) {
            return;
        }
        if (this.worldObj.isRemote) {
            final int eid = this.getDataWatcher().getWatchableObjectInt(22);
            if (eid > 0) {
                if (this.uavStation == null) {
                    final Entity uavEntity = this.worldObj.getEntityByID(eid);
                    if (uavEntity instanceof MCH_EntityUavStation) {
                        (this.uavStation = (MCH_EntityUavStation)uavEntity).setControlAircract(this);
                    }
                }
            }
            else if (this.uavStation != null) {
                this.uavStation.setControlAircract(null);
                this.uavStation = null;
            }
        }
        else if (this.uavStation != null) {
            final double udx = this.posX - this.uavStation.posX;
            final double udz = this.posZ - this.uavStation.posZ;
            if (udx * udx + udz * udz > 15129.0) {
                this.uavStation.setControlAircract(null);
                this.setUavStation(null);
                this.attackEntityFrom(DamageSource.outOfWorld, this.getMaxHP() + 10);
            }
        }
        if (this.uavStation != null && this.uavStation.isDead) {
            this.uavStation = null;
        }
    }
    
    public void switchGunnerMode(final boolean mode) {
        final boolean debug_bk_mode = this.isGunnerMode;
        final Entity pilot = this.getEntityBySeatId(0);
        if (!mode || this.canSwitchGunnerMode()) {
            if (this.isGunnerMode && !mode) {
                this.setCurrentThrottle(this.beforeHoverThrottle);
                this.isGunnerMode = false;
                this.camera.setCameraZoom(1.0f);
                this.getCurrentWeapon(pilot).onSwitchWeapon(this.worldObj.isRemote, this.isInfinityAmmo(pilot));
            }
            else if (!this.isGunnerMode && mode) {
                this.beforeHoverThrottle = this.getCurrentThrottle();
                this.isGunnerMode = true;
                this.camera.setCameraZoom(1.0f);
                this.getCurrentWeapon(pilot).onSwitchWeapon(this.worldObj.isRemote, this.isInfinityAmmo(pilot));
            }
        }
        MCH_Lib.DbgLog(this.worldObj, "switchGunnerMode %s->%s", debug_bk_mode ? "ON" : "OFF", mode ? "ON" : "OFF");
    }
    
    public boolean canSwitchGunnerMode() {
        return this.getAcInfo() != null && this.getAcInfo().isEnableGunnerMode && this.isCanopyClose() && (this.getAcInfo().isEnableConcurrentGunnerMode || !(this.getEntityBySeatId(1) instanceof EntityPlayer)) && !this.isHoveringMode();
    }
    
    public boolean canSwitchGunnerModeOtherSeat(final EntityPlayer player) {
        final int sid = this.getSeatIdByEntity((Entity)player);
        if (sid > 0) {
            final MCH_SeatInfo info = this.getSeatInfo(sid);
            if (info != null) {
                return info.gunner && info.switchgunner;
            }
        }
        return false;
    }
    
    public void switchGunnerModeOtherSeat(final EntityPlayer player) {
        this.isGunnerModeOtherSeat = !this.isGunnerModeOtherSeat;
    }
    
    public boolean isHoveringMode() {
        return this.isHoveringMode;
    }
    
    public void switchHoveringMode(final boolean mode) {
        this.stopRepelling();
        if (this.canSwitchHoveringMode() && this.isHoveringMode() != mode) {
            if (mode) {
                this.beforeHoverThrottle = this.getCurrentThrottle();
            }
            else {
                this.setCurrentThrottle(this.beforeHoverThrottle);
            }
            this.isHoveringMode = mode;
            if (this.riddenByEntity != null) {
                this.riddenByEntity.rotationPitch = 0.0f;
                this.riddenByEntity.prevRotationPitch = 0.0f;
            }
        }
    }
    
    public boolean canSwitchHoveringMode() {
        return this.getAcInfo() != null && !this.isGunnerMode;
    }
    
    public boolean isHovering() {
        return this.isGunnerMode || this.isHoveringMode();
    }
    
    public boolean getIsGunnerMode(final Entity entity) {
        if (this.getAcInfo() == null) {
            return false;
        }
        final int id = this.getSeatIdByEntity(entity);
        if (id < 0) {
            return false;
        }
        if (id == 0 && this.getAcInfo().isEnableGunnerMode) {
            return this.isGunnerMode;
        }
        final MCH_SeatInfo[] st = this.getSeatsInfo();
        return id < st.length && st[id].gunner && (!this.worldObj.isRemote || !st[id].switchgunner || this.isGunnerModeOtherSeat);
    }
    
    public boolean isPilot(final Entity player) {
        return W_Entity.isEqual(this.getRiddenByEntity(), player);
    }
    
    public boolean canSwitchFreeLook() {
        return true;
    }
    
    public boolean isFreeLookMode() {
        return this.getCommonStatus(1) || this.isRepelling();
    }
    
    public void switchFreeLookMode(final boolean b) {
        this.setCommonStatus(1, b);
    }
    
    public void switchFreeLookModeClient(final boolean b) {
        this.setCommonStatus(1, b, true);
    }
    
    public boolean canSwitchGunnerFreeLook(final EntityPlayer player) {
        final MCH_SeatInfo seatInfo = this.getSeatInfo((Entity)player);
        return seatInfo != null && seatInfo.fixRot && this.getIsGunnerMode((Entity)player);
    }
    
    public boolean isGunnerLookMode(final EntityPlayer player) {
        return !this.isPilot((Entity)player) && this.isGunnerFreeLookMode;
    }
    
    public void switchGunnerFreeLookMode(final boolean b) {
        this.isGunnerFreeLookMode = b;
    }
    
    public void switchGunnerFreeLookMode() {
        this.switchGunnerFreeLookMode(!this.isGunnerFreeLookMode);
    }
    
    public void updateParts(final int stat) {
        if (this.isDestroyed()) {
            return;
        }
        final MCH_Parts[] arr$;
        final MCH_Parts[] parts = arr$ = new MCH_Parts[] { this.partHatch, this.partCanopy, this.partLandingGear };
        for (final MCH_Parts p : arr$) {
            if (p != null) {
                p.updateStatusClient(stat);
                p.update();
            }
        }
        if (!this.isDestroyed() && !this.worldObj.isRemote && this.partLandingGear != null) {
            int blockId = 0;
            if (!this.isLandingGearFolded() && this.partLandingGear.getFactor() <= 0.1f) {
                blockId = MCH_Lib.getBlockIdY(this, 3, -20);
                if (this.getCurrentThrottle() <= 0.800000011920929 || this.onGround || blockId != 0) {
                    if (this.getAcInfo().isFloat && (this.isInWater() || MCH_Lib.getBlockY(this, 3, -20, true) == W_Block.getWater())) {
                        this.partLandingGear.setStatusServer(true);
                    }
                }
            }
            else if (this.isLandingGearFolded() && this.partLandingGear.getFactor() >= 0.9f) {
                blockId = MCH_Lib.getBlockIdY(this, 3, -10);
                if (this.getCurrentThrottle() < this.getUnfoldLandingGearThrottle() && blockId != 0) {
                    boolean unfold = true;
                    if (this.getAcInfo().isFloat) {
                        blockId = MCH_Lib.getBlockIdY(this.worldObj, this.posX, this.posY + 1.0 + this.getAcInfo().floatOffset, this.posZ, 1, -150, true);
                        if (W_Block.isEqual(blockId, W_Block.getWater())) {
                            unfold = false;
                        }
                    }
                    if (unfold) {
                        this.partLandingGear.setStatusServer(false);
                    }
                }
                else if (this.getVtolMode() == 2 && blockId != 0) {
                    this.partLandingGear.setStatusServer(false);
                }
            }
        }
    }
    
    public float getUnfoldLandingGearThrottle() {
        return 0.8f;
    }
    
    private int getPartStatus() {
        return this.getDataWatcher().getWatchableObjectInt(31);
    }
    
    private void setPartStatus(final int n) {
        this.getDataWatcher().updateObject(31, (Object)n);
    }
    
    protected void initPartRotation(final float yaw, final float pitch) {
        this.lastRiderYaw = yaw;
        this.prevLastRiderYaw = yaw;
        this.camera.partRotationYaw = yaw;
        this.camera.prevPartRotationYaw = yaw;
        this.lastSearchLightYaw = yaw;
    }
    
    public int getLastPartStatusMask() {
        return 24;
    }
    
    public int getModeSwitchCooldown() {
        return this.modeSwitchCooldown;
    }
    
    public void setModeSwitchCooldown(final int n) {
        this.modeSwitchCooldown = n;
    }
    
    protected WeaponBay[] createWeaponBays() {
        final WeaponBay[] wbs = new WeaponBay[this.getAcInfo().partWeaponBay.size()];
        for (int i = 0; i < wbs.length; ++i) {
            wbs[i] = new WeaponBay();
        }
        return wbs;
    }
    
    protected MCH_Parts createHatch() {
        MCH_Parts hatch = null;
        if (this.getAcInfo().haveHatch()) {
            hatch = new MCH_Parts(this, 4, 31, "Hatch");
            hatch.rotationMax = 90.0f;
            hatch.rotationInv = 1.5f;
            hatch.soundEndSwichOn.setPrm("plane_cc", 1.0f, 1.0f);
            hatch.soundEndSwichOff.setPrm("plane_cc", 1.0f, 1.0f);
            hatch.soundSwitching.setPrm("plane_cv", 1.0f, 0.5f);
        }
        return hatch;
    }
    
    public boolean haveHatch() {
        return this.partHatch != null;
    }
    
    public boolean canFoldHatch() {
        return this.partHatch != null && this.modeSwitchCooldown <= 0 && this.partHatch.isOFF();
    }
    
    public boolean canUnfoldHatch() {
        return this.partHatch != null && this.modeSwitchCooldown <= 0 && this.partHatch.isON();
    }
    
    public void foldHatch(final boolean fold) {
        this.foldHatch(fold, false);
    }
    
    public void foldHatch(final boolean fold, final boolean force) {
        if (this.partHatch == null) {
            return;
        }
        if (!force && this.modeSwitchCooldown > 0) {
            return;
        }
        this.partHatch.setStatusServer(fold);
        this.modeSwitchCooldown = 20;
        if (!fold) {
            this.stopUnmountCrew();
        }
    }
    
    public float getHatchRotation() {
        return (this.partHatch != null) ? this.partHatch.rotation : 0.0f;
    }
    
    public float getPrevHatchRotation() {
        return (this.partHatch != null) ? this.partHatch.prevRotation : 0.0f;
    }
    
    public void foldLandingGear() {
        if (this.partLandingGear == null || this.getModeSwitchCooldown() > 0) {
            return;
        }
        this.partLandingGear.setStatusServer(true);
        this.setModeSwitchCooldown(20);
    }
    
    public void unfoldLandingGear() {
        if (this.partLandingGear == null || this.getModeSwitchCooldown() > 0) {
            return;
        }
        if (this.isLandingGearFolded()) {
            this.partLandingGear.setStatusServer(false);
            this.setModeSwitchCooldown(20);
        }
    }
    
    public boolean canFoldLandingGear() {
        if (this.getLandingGearRotation() >= 1.0f) {
            return false;
        }
        final Block block = MCH_Lib.getBlockY(this, 3, -10, true);
        return !this.isLandingGearFolded() && block == W_Blocks.air;
    }
    
    public boolean canUnfoldLandingGear() {
        return this.getLandingGearRotation() >= 89.0f && this.isLandingGearFolded();
    }
    
    public boolean isLandingGearFolded() {
        return this.partLandingGear != null && this.partLandingGear.getStatus();
    }
    
    protected MCH_Parts createLandingGear() {
        MCH_Parts lg = null;
        if (this.getAcInfo().haveLandingGear()) {
            lg = new MCH_Parts(this, 2, 31, "LandingGear");
            lg.rotationMax = 90.0f;
            lg.rotationInv = 2.5f;
            lg.soundStartSwichOn.setPrm("plane_cc", 1.0f, 0.5f);
            lg.soundEndSwichOn.setPrm("plane_cc", 1.0f, 0.5f);
            lg.soundStartSwichOff.setPrm("plane_cc", 1.0f, 0.5f);
            lg.soundEndSwichOff.setPrm("plane_cc", 1.0f, 0.5f);
            lg.soundSwitching.setPrm("plane_cv", 1.0f, 0.75f);
        }
        return lg;
    }
    
    public float getLandingGearRotation() {
        return (this.partLandingGear != null) ? this.partLandingGear.rotation : 0.0f;
    }
    
    public float getPrevLandingGearRotation() {
        return (this.partLandingGear != null) ? this.partLandingGear.prevRotation : 0.0f;
    }
    
    public int getVtolMode() {
        return 0;
    }
    
    public void openCanopy() {
        if (this.partCanopy == null || this.getModeSwitchCooldown() > 0) {
            return;
        }
        this.partCanopy.setStatusServer(true);
        this.setModeSwitchCooldown(20);
    }
    
    public void openCanopy_EjectSeat() {
        if (this.partCanopy == null) {
            return;
        }
        this.partCanopy.setStatusServer(true, false);
        this.setModeSwitchCooldown(40);
    }
    
    public void closeCanopy() {
        if (this.partCanopy == null || this.getModeSwitchCooldown() > 0) {
            return;
        }
        if (this.getCanopyStat()) {
            this.partCanopy.setStatusServer(false);
            this.setModeSwitchCooldown(20);
        }
    }
    
    public boolean getCanopyStat() {
        return this.partCanopy != null && this.partCanopy.getStatus();
    }
    
    public boolean isCanopyClose() {
        return this.partCanopy == null || (!this.getCanopyStat() && this.getCanopyRotation() <= 0.01f);
    }
    
    public float getCanopyRotation() {
        return (this.partCanopy != null) ? this.partCanopy.rotation : 0.0f;
    }
    
    public float getPrevCanopyRotation() {
        return (this.partCanopy != null) ? this.partCanopy.prevRotation : 0.0f;
    }
    
    protected MCH_Parts createCanopy() {
        MCH_Parts canopy = null;
        if (this.getAcInfo().haveCanopy()) {
            canopy = new MCH_Parts(this, 0, 31, "Canopy");
            canopy.rotationMax = 90.0f;
            canopy.rotationInv = 3.5f;
            canopy.soundEndSwichOn.setPrm("plane_cc", 1.0f, 1.0f);
            canopy.soundEndSwichOff.setPrm("plane_cc", 1.0f, 1.0f);
        }
        return canopy;
    }
    
    public boolean hasBrake() {
        return false;
    }
    
    public void setBrake(final boolean b) {
        if (!this.worldObj.isRemote) {
            this.setCommonStatus(11, b);
        }
    }
    
    public boolean getBrake() {
        return this.getCommonStatus(11);
    }
    
    public void setGunnerStatus(final boolean b) {
        if (!this.worldObj.isRemote) {
            this.setCommonStatus(12, b);
        }
    }
    
    public boolean getGunnerStatus() {
        return this.getCommonStatus(12);
    }
    
    @Override
    public int getSizeInventory() {
        return (this.getAcInfo() != null) ? this.getAcInfo().inventorySize : 0;
    }
    
    @Override
    public String getInvName() {
        if (this.getAcInfo() == null) {
            return super.getInvName();
        }
        final String s = this.getAcInfo().displayName;
        return (s.length() <= 32) ? s : s.substring(0, 31);
    }
    
    @Override
    public boolean isInvNameLocalized() {
        return this.getAcInfo() != null;
    }
    
    public MCH_EntityChain getTowChainEntity() {
        return this.towChainEntity;
    }
    
    public void setTowChainEntity(final MCH_EntityChain chainEntity) {
        this.towChainEntity = chainEntity;
    }
    
    public MCH_EntityChain getTowedChainEntity() {
        return this.towedChainEntity;
    }
    
    public void setTowedChainEntity(final MCH_EntityChain towedChainEntity) {
        this.towedChainEntity = towedChainEntity;
    }
    
    static {
        seatsDummy = new MCH_EntitySeat[0];
    }
    
    protected class UnmountReserve
    {
        final Entity entity;
        final double posX;
        final double posY;
        final double posZ;
        int cnt;
        
        public UnmountReserve(final Entity e, final double x, final double y, final double z) {
            this.cnt = 5;
            this.entity = e;
            this.posX = x;
            this.posY = y;
            this.posZ = z;
        }
    }
    
    public class WeaponBay
    {
        public float rot;
        public float prevRot;
        
        public WeaponBay() {
            this.rot = 0.0f;
            this.prevRot = 0.0f;
        }
    }
}
