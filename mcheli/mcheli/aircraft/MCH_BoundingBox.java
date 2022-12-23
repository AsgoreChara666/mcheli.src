//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.aircraft;

import net.minecraft.util.*;
import java.util.*;
import mcheli.*;

public class MCH_BoundingBox
{
    public final AxisAlignedBB boundingBox;
    public final double offsetX;
    public final double offsetY;
    public final double offsetZ;
    public final float width;
    public final float height;
    public Vec3 rotatedOffset;
    public List<Vec3> pos;
    public final float damegeFactor;
    
    public MCH_BoundingBox(final double x, final double y, final double z, final float w, final float h, final float df) {
        this.pos = new ArrayList<Vec3>();
        this.offsetX = x;
        this.offsetY = y;
        this.offsetZ = z;
        this.width = w;
        this.height = h;
        this.damegeFactor = df;
        this.boundingBox = AxisAlignedBB.getBoundingBox(x - w / 2.0f, y - h / 2.0f, z - w / 2.0f, x + w / 2.0f, y + h / 2.0f, z + w / 2.0f);
        this.updatePosition(0.0, 0.0, 0.0, 0.0f, 0.0f, 0.0f);
    }
    
    public void add(final double x, final double y, final double z) {
        this.pos.add(0, Vec3.createVectorHelper(x, y, z));
        while (true) {
            final int size = this.pos.size();
            final MCH_Config config = MCH_MOD.config;
            if (size <= MCH_Config.HitBoxDelayTick.prmInt + 2) {
                break;
            }
            final List<Vec3> pos = this.pos;
            final MCH_Config config2 = MCH_MOD.config;
            pos.remove(MCH_Config.HitBoxDelayTick.prmInt + 2);
        }
    }
    
    public MCH_BoundingBox copy() {
        return new MCH_BoundingBox(this.offsetX, this.offsetY, this.offsetZ, this.width, this.height, this.damegeFactor);
    }
    
    public void updatePosition(final double posX, final double posY, final double posZ, final float yaw, final float pitch, final float roll) {
        final Vec3 v = Vec3.createVectorHelper(this.offsetX, this.offsetY, this.offsetZ);
        this.rotatedOffset = MCH_Lib.RotVec3(v, -yaw, -pitch, -roll);
        this.add(posX + this.rotatedOffset.xCoord, posY + this.rotatedOffset.yCoord, posZ + this.rotatedOffset.zCoord);
        final MCH_Config config = MCH_MOD.config;
        final int index = MCH_Config.HitBoxDelayTick.prmInt;
        final Vec3 cp = (index + 0 < this.pos.size()) ? this.pos.get(index + 0) : this.pos.get(this.pos.size() - 1);
        final Vec3 pp = (index + 1 < this.pos.size()) ? this.pos.get(index + 1) : this.pos.get(this.pos.size() - 1);
        final double sx = (this.width + Math.abs(cp.xCoord - pp.xCoord)) / 2.0;
        final double sy = (this.height + Math.abs(cp.yCoord - pp.yCoord)) / 2.0;
        final double sz = (this.width + Math.abs(cp.zCoord - pp.zCoord)) / 2.0;
        final double x = (cp.xCoord + pp.xCoord) / 2.0;
        final double y = (cp.yCoord + pp.yCoord) / 2.0;
        final double z = (cp.zCoord + pp.zCoord) / 2.0;
        this.boundingBox.setBounds(x - sx, y - sy, z - sz, x + sx, y + sy, z + sz);
    }
}
