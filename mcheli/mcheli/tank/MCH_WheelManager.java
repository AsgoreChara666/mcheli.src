//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.tank;

import net.minecraft.world.*;
import java.util.*;
import mcheli.aircraft.*;
import mcheli.*;
import mcheli.wrapper.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import mcheli.particles.*;

public class MCH_WheelManager
{
    public final MCH_EntityAircraft parent;
    public MCH_EntityWheel[] wheels;
    private double minZ;
    private double maxZ;
    private double avgZ;
    public Vec3 weightedCenter;
    public float targetPitch;
    public float targetRoll;
    public float prevYaw;
    private static Random rand;
    
    public MCH_WheelManager(final MCH_EntityAircraft ac) {
        this.parent = ac;
        this.wheels = new MCH_EntityWheel[0];
        this.weightedCenter = Vec3.createVectorHelper(0.0, 0.0, 0.0);
    }
    
    public void createWheels(final World w, final List<MCH_AircraftInfo.Wheel> list, final Vec3 weightedCenter) {
        this.wheels = new MCH_EntityWheel[list.size() * 2];
        this.minZ = 999999.0;
        this.maxZ = -999999.0;
        this.weightedCenter = weightedCenter;
        for (int i = 0; i < this.wheels.length; ++i) {
            final MCH_EntityWheel wheel = new MCH_EntityWheel(w);
            wheel.setParents(this.parent);
            final Vec3 wp = list.get(i / 2).pos;
            wheel.setWheelPos(Vec3.createVectorHelper((i % 2 == 0) ? wp.xCoord : (-wp.xCoord), wp.yCoord, wp.zCoord), this.weightedCenter);
            final Vec3 v = this.parent.getTransformedPosition(wheel.pos.xCoord, wheel.pos.yCoord, wheel.pos.zCoord);
            wheel.setLocationAndAngles(v.xCoord, v.yCoord + 1.0, v.zCoord, 0.0f, 0.0f);
            this.wheels[i] = wheel;
            if (wheel.pos.zCoord <= this.minZ) {
                this.minZ = wheel.pos.zCoord;
            }
            if (wheel.pos.zCoord >= this.maxZ) {
                this.maxZ = wheel.pos.zCoord;
            }
        }
        this.avgZ = this.maxZ - this.minZ;
    }
    
    public void move(final double x, final double y, final double z) {
        final MCH_EntityAircraft ac = this.parent;
        if (ac.getAcInfo() == null) {
            return;
        }
        final boolean showLog = ac.ticksExisted % 1 == 1;
        if (showLog) {
            MCH_Lib.DbgLog(ac.worldObj, "[" + (ac.worldObj.isRemote ? "Client" : "Server") + "] ==============================", new Object[0]);
        }
        for (final MCH_EntityWheel wheel : this.wheels) {
            wheel.prevPosX = wheel.posX;
            wheel.prevPosY = wheel.posY;
            wheel.prevPosZ = wheel.posZ;
            final Vec3 v = ac.getTransformedPosition(wheel.pos.xCoord, wheel.pos.yCoord, wheel.pos.zCoord);
            wheel.motionX = v.xCoord - wheel.posX + x;
            wheel.motionY = v.yCoord - wheel.posY;
            wheel.motionZ = v.zCoord - wheel.posZ + z;
        }
        for (final MCH_EntityWheel mch_EntityWheel : this.wheels) {
            final MCH_EntityWheel wheel = mch_EntityWheel;
            mch_EntityWheel.motionY *= 0.15;
            wheel.moveEntity(wheel.motionX, wheel.motionY, wheel.motionZ);
            final double f = 1.0;
            wheel.moveEntity(0.0, -0.1 * f, 0.0);
        }
        int zmog = -1;
        for (int i = 0; i < this.wheels.length / 2; ++i) {
            zmog = i;
            final MCH_EntityWheel w1 = this.wheels[i * 2 + 0];
            final MCH_EntityWheel w2 = this.wheels[i * 2 + 1];
            if (!w1.isPlus && (w1.onGround || w2.onGround)) {
                zmog = -1;
                break;
            }
        }
        if (zmog >= 0) {
            this.wheels[zmog * 2 + 0].onGround = true;
            this.wheels[zmog * 2 + 1].onGround = true;
        }
        zmog = -1;
        for (int i = this.wheels.length / 2 - 1; i >= 0; --i) {
            zmog = i;
            final MCH_EntityWheel w1 = this.wheels[i * 2 + 0];
            final MCH_EntityWheel w2 = this.wheels[i * 2 + 1];
            if (w1.isPlus && (w1.onGround || w2.onGround)) {
                zmog = -1;
                break;
            }
        }
        if (zmog >= 0) {
            this.wheels[zmog * 2 + 0].onGround = true;
            this.wheels[zmog * 2 + 1].onGround = true;
        }
        Vec3 rv = Vec3.createVectorHelper(0.0, 0.0, 0.0);
        final Vec3 transformedPosition;
        final Vec3 wc = transformedPosition = ac.getTransformedPosition(this.weightedCenter);
        transformedPosition.xCoord -= ac.posX;
        wc.yCoord = this.weightedCenter.yCoord;
        final Vec3 vec3 = wc;
        vec3.zCoord -= ac.posZ;
        for (int j = 0; j < this.wheels.length / 2; ++j) {
            final MCH_EntityWheel w3 = this.wheels[j * 2 + 0];
            final MCH_EntityWheel w4 = this.wheels[j * 2 + 1];
            final Vec3 v2 = Vec3.createVectorHelper(w3.posX - (ac.posX + wc.xCoord), w3.posY - (ac.posY + wc.yCoord), w3.posZ - (ac.posZ + wc.zCoord));
            final Vec3 v3 = Vec3.createVectorHelper(w4.posX - (ac.posX + wc.xCoord), w4.posY - (ac.posY + wc.yCoord), w4.posZ - (ac.posZ + wc.zCoord));
            Vec3 v4 = (w3.pos.zCoord >= 0.0) ? v3.crossProduct(v2) : v2.crossProduct(v3);
            v4 = v4.normalize();
            double f2 = Math.abs(w3.pos.zCoord / this.avgZ);
            if (!w3.onGround && !w4.onGround) {
                f2 = 0.0;
            }
            final Vec3 vec4 = rv;
            vec4.xCoord += v4.xCoord * f2;
            final Vec3 vec5 = rv;
            vec5.yCoord += v4.yCoord * f2;
            final Vec3 vec6 = rv;
            vec6.zCoord += v4.zCoord * f2;
            if (showLog) {
                v4.rotateAroundY((float)(ac.getRotYaw() * 3.141592653589793 / 180.0));
                MCH_Lib.DbgLog(ac.worldObj, "%2d : %.2f :[%+.1f, %+.1f, %+.1f][%s %d %d][%+.2f(%+.2f), %+.2f(%+.2f)][%+.1f, %+.1f, %+.1f]", new Object[] { j, f2, v4.xCoord, v4.yCoord, v4.zCoord, w3.isPlus ? "+" : "-", w3.onGround ? 1 : 0, w4.onGround ? 1 : 0, w3.posY - w3.prevPosY, w3.motionY, w4.posY - w4.prevPosY, w4.motionY, v4.xCoord, v4.yCoord, v4.zCoord });
            }
        }
        rv = rv.normalize();
        if (rv.yCoord > 0.01 && rv.yCoord < 0.7) {
            final MCH_EntityAircraft mch_EntityAircraft = ac;
            mch_EntityAircraft.motionX += rv.xCoord / 50.0;
            final MCH_EntityAircraft mch_EntityAircraft2 = ac;
            mch_EntityAircraft2.motionZ += rv.zCoord / 50.0;
        }
        rv.rotateAroundY((float)(ac.getRotYaw() * 3.141592653589793 / 180.0));
        float pitch = (float)(90.0 - Math.atan2(rv.yCoord, rv.zCoord) * 180.0 / 3.141592653589793);
        float roll = -(float)(90.0 - Math.atan2(rv.yCoord, rv.xCoord) * 180.0 / 3.141592653589793);
        final float ogpf = ac.getAcInfo().onGroundPitchFactor;
        if (pitch - ac.getRotPitch() > ogpf) {
            pitch = ac.getRotPitch() + ogpf;
        }
        if (pitch - ac.getRotPitch() < -ogpf) {
            pitch = ac.getRotPitch() - ogpf;
        }
        final float ogrf = ac.getAcInfo().onGroundRollFactor;
        if (roll - ac.getRotRoll() > ogrf) {
            roll = ac.getRotRoll() + ogrf;
        }
        if (roll - ac.getRotRoll() < -ogrf) {
            roll = ac.getRotRoll() - ogrf;
        }
        this.targetPitch = pitch;
        this.targetRoll = roll;
        if (!W_Lib.isClientPlayer(ac.getRiddenByEntity())) {
            ac.setRotPitch(pitch);
            ac.setRotRoll(roll);
        }
        if (showLog) {
            MCH_Lib.DbgLog(ac.worldObj, "%+03d, %+03d :[%.2f, %.2f, %.2f] yaw=%.2f, pitch=%.2f, roll=%.2f", new Object[] { (int)pitch, (int)roll, rv.xCoord, rv.yCoord, rv.zCoord, ac.getRotYaw(), this.targetPitch, this.targetRoll });
        }
        for (final MCH_EntityWheel wheel2 : this.wheels) {
            final Vec3 v5 = this.getTransformedPosition(wheel2.pos.xCoord, wheel2.pos.yCoord, wheel2.pos.zCoord, ac, ac.getRotYaw(), this.targetPitch, this.targetRoll);
            final double offset = wheel2.onGround ? 0.01 : -0.0;
            final double rangeH = 2.0;
            final double poy = wheel2.stepHeight / 2.0f;
            int b = 0;
            if (wheel2.posX > v5.xCoord + rangeH) {
                wheel2.posX = v5.xCoord + rangeH;
                wheel2.posY = v5.yCoord + poy;
                b |= 0x1;
            }
            if (wheel2.posX < v5.xCoord - rangeH) {
                wheel2.posX = v5.xCoord - rangeH;
                wheel2.posY = v5.yCoord + poy;
                b |= 0x2;
            }
            if (wheel2.posZ > v5.zCoord + rangeH) {
                wheel2.posZ = v5.zCoord + rangeH;
                wheel2.posY = v5.yCoord + poy;
                b |= 0x4;
            }
            if (wheel2.posZ < v5.zCoord - rangeH) {
                wheel2.posZ = v5.zCoord - rangeH;
                wheel2.posY = v5.yCoord + poy;
                b |= 0x8;
            }
            wheel2.setPositionAndRotation(wheel2.posX, wheel2.posY, wheel2.posZ, 0.0f, 0.0f);
        }
    }
    
    public Vec3 getTransformedPosition(final double x, final double y, final double z, final MCH_EntityAircraft ac, final float yaw, final float pitch, final float roll) {
        final Vec3 v = MCH_Lib.RotVec3(x, y, z, -yaw, -pitch, -roll);
        return v.addVector(ac.posX, ac.posY, ac.posZ);
    }
    
    public void updateBlock() {
        final MCH_Config config = MCH_MOD.config;
        if (!MCH_Config.Collision_DestroyBlock.prmBool) {
            return;
        }
        final MCH_EntityAircraft ac = this.parent;
        for (final MCH_EntityWheel w : this.wheels) {
            final Vec3 v = ac.getTransformedPosition(w.pos);
            final int x = (int)(v.xCoord + 0.5);
            final int y = (int)(v.yCoord + 0.5);
            final int z = (int)(v.zCoord + 0.5);
            final Block block = ac.worldObj.getBlock(x, y, z);
            if (block == W_Block.getSnowLayer()) {
                ac.worldObj.setBlockToAir(x, y, z);
            }
            if (block == W_Blocks.waterlily || block == W_Blocks.cake) {
                W_WorldFunc.destroyBlock(ac.worldObj, x, y, z, false);
            }
        }
    }
    
    public void particleLandingGear() {
        if (this.wheels.length <= 0) {
            return;
        }
        final MCH_EntityAircraft ac = this.parent;
        final double d = ac.motionX * ac.motionX + ac.motionZ * ac.motionZ + Math.abs(this.prevYaw - ac.getRotYaw());
        this.prevYaw = ac.getRotYaw();
        if (d > 0.001) {
            for (int i = 0; i < 2; ++i) {
                final MCH_EntityWheel w = this.wheels[MCH_WheelManager.rand.nextInt(this.wheels.length)];
                final Vec3 v = ac.getTransformedPosition(w.pos);
                final int x = MathHelper.floor_double(v.xCoord + 0.5);
                int y = MathHelper.floor_double(v.yCoord - 0.5);
                final int z = MathHelper.floor_double(v.zCoord + 0.5);
                Block block = ac.worldObj.getBlock(x, y, z);
                if (Block.isEqualTo(block, Blocks.air)) {
                    y = MathHelper.floor_double(v.yCoord + 0.5);
                    block = ac.worldObj.getBlock(x, y, z);
                }
                if (!Block.isEqualTo(block, Blocks.air)) {
                    MCH_ParticlesUtil.spawnParticleTileCrack(ac.worldObj, x, y, z, v.xCoord + (MCH_WheelManager.rand.nextFloat() - 0.5), v.yCoord + 0.1, v.zCoord + (MCH_WheelManager.rand.nextFloat() - 0.5), -ac.motionX * 4.0 + (MCH_WheelManager.rand.nextFloat() - 0.5) * 0.1, MCH_WheelManager.rand.nextFloat() * 0.5, -ac.motionZ * 4.0 + (MCH_WheelManager.rand.nextFloat() - 0.5) * 0.1);
                }
            }
        }
    }
    
    static {
        MCH_WheelManager.rand = new Random();
    }
}
