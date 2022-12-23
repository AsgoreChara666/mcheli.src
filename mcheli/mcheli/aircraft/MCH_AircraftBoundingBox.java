//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.aircraft;

import net.minecraft.util.*;

public class MCH_AircraftBoundingBox extends AxisAlignedBB
{
    private final MCH_EntityAircraft ac;
    
    protected MCH_AircraftBoundingBox(final MCH_EntityAircraft ac) {
        super(ac.boundingBox.minX, ac.boundingBox.minY, ac.boundingBox.minZ, ac.boundingBox.maxX, ac.boundingBox.maxY, ac.boundingBox.maxZ);
        this.ac = ac;
    }
    
    public AxisAlignedBB NewAABB(final double x1, final double y1, final double z1, final double x2, final double y2, final double z2) {
        return new MCH_AircraftBoundingBox(this.ac).setBounds(x1, y1, z1, x2, y2, z2);
    }
    
    public double getDistSq(final AxisAlignedBB a1, final AxisAlignedBB a2) {
        final double x1 = (a1.maxX + a1.minX) / 2.0;
        final double y1 = (a1.maxY + a1.minY) / 2.0;
        final double z1 = (a1.maxZ + a1.minZ) / 2.0;
        final double x2 = (a2.maxX + a2.minX) / 2.0;
        final double y2 = (a2.maxY + a2.minY) / 2.0;
        final double z2 = (a2.maxZ + a2.minZ) / 2.0;
        final double dx = x1 - x2;
        final double dy = y1 - y2;
        final double dz = z1 - z2;
        return dx * dx + dy * dy + dz * dz;
    }
    
    public boolean intersectsWith(final AxisAlignedBB aabb) {
        boolean ret = false;
        double dist = 1.0E7;
        this.ac.lastBBDamageFactor = 1.0f;
        if (super.intersectsWith(aabb)) {
            dist = this.getDistSq(aabb, this);
            ret = true;
        }
        for (final MCH_BoundingBox bb : this.ac.extraBoundingBox) {
            if (bb.boundingBox.intersectsWith(aabb)) {
                final double dist2 = this.getDistSq(aabb, this);
                if (dist2 < dist) {
                    dist = dist2;
                    this.ac.lastBBDamageFactor = bb.damegeFactor;
                }
                ret = true;
            }
        }
        return ret;
    }
    
    public AxisAlignedBB expand(final double x, final double y, final double z) {
        final double d3 = this.minX - x;
        final double d4 = this.minY - y;
        final double d5 = this.minZ - z;
        final double d6 = this.maxX + x;
        final double d7 = this.maxY + y;
        final double d8 = this.maxZ + z;
        return this.NewAABB(d3, d4, d5, d6, d7, d8);
    }
    
    public AxisAlignedBB union(final AxisAlignedBB other) {
        final double d0 = Math.min(this.minX, other.minX);
        final double d2 = Math.min(this.minY, other.minY);
        final double d3 = Math.min(this.minZ, other.minZ);
        final double d4 = Math.max(this.maxX, other.maxX);
        final double d5 = Math.max(this.maxY, other.maxY);
        final double d6 = Math.max(this.maxZ, other.maxZ);
        return this.NewAABB(d0, d2, d3, d4, d5, d6);
    }
    
    public AxisAlignedBB addCoord(final double x, final double y, final double z) {
        double d3 = this.minX;
        double d4 = this.minY;
        double d5 = this.minZ;
        double d6 = this.maxX;
        double d7 = this.maxY;
        double d8 = this.maxZ;
        if (x < 0.0) {
            d3 += x;
        }
        if (x > 0.0) {
            d6 += x;
        }
        if (y < 0.0) {
            d4 += y;
        }
        if (y > 0.0) {
            d7 += y;
        }
        if (z < 0.0) {
            d5 += z;
        }
        if (z > 0.0) {
            d8 += z;
        }
        return this.NewAABB(d3, d4, d5, d6, d7, d8);
    }
    
    public AxisAlignedBB contract(final double x, final double y, final double z) {
        final double d3 = this.minX + x;
        final double d4 = this.minY + y;
        final double d5 = this.minZ + z;
        final double d6 = this.maxX - x;
        final double d7 = this.maxY - y;
        final double d8 = this.maxZ - z;
        return this.NewAABB(d3, d4, d5, d6, d7, d8);
    }
    
    public AxisAlignedBB copy() {
        return this.NewAABB(this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ);
    }
    
    public AxisAlignedBB getOffsetBoundingBox(final double x, final double y, final double z) {
        return this.NewAABB(this.minX + x, this.minY + y, this.minZ + z, this.maxX + x, this.maxY + y, this.maxZ + z);
    }
    
    public MovingObjectPosition calculateIntercept(final Vec3 v1, final Vec3 v2) {
        this.ac.lastBBDamageFactor = 1.0f;
        MovingObjectPosition mop = super.calculateIntercept(v1, v2);
        double dist = 1.0E7;
        if (mop != null) {
            dist = v1.distanceTo(mop.hitVec);
        }
        for (final MCH_BoundingBox bb : this.ac.extraBoundingBox) {
            final MovingObjectPosition mop2 = bb.boundingBox.calculateIntercept(v1, v2);
            if (mop2 != null) {
                final double dist2 = v1.distanceTo(mop2.hitVec);
                if (dist2 < dist) {
                    mop = mop2;
                    dist = dist2;
                    this.ac.lastBBDamageFactor = bb.damegeFactor;
                }
            }
        }
        return mop;
    }
}
