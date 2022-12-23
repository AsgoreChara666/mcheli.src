//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.vehicle;

import mcheli.aircraft.*;
import net.minecraft.item.*;
import java.util.*;
import mcheli.*;

public class MCH_VehicleInfo extends MCH_AircraftInfo
{
    public MCH_ItemVehicle item;
    public boolean isEnableMove;
    public boolean isEnableRot;
    public List<VPart> partList;
    
    public float getMinRotationPitch() {
        return -90.0f;
    }
    
    public float getMaxRotationPitch() {
        return 90.0f;
    }
    
    public Item getItem() {
        return (Item)this.item;
    }
    
    public MCH_VehicleInfo(final String name) {
        super(name);
        this.item = null;
        this.isEnableMove = false;
        this.isEnableRot = false;
        this.partList = new ArrayList<VPart>();
    }
    
    public boolean isValidData() throws Exception {
        return super.isValidData();
    }
    
    public String getDefaultHudName(final int seatId) {
        return "vehicle";
    }
    
    public void loadItemData(final String item, final String data) {
        super.loadItemData(item, data);
        if (item.compareTo("canmove") == 0) {
            this.isEnableMove = this.toBool(data);
        }
        else if (item.compareTo("canrotation") == 0) {
            this.isEnableRot = this.toBool(data);
        }
        else if (item.compareTo("rotationpitchmin") == 0) {
            super.loadItemData("minrotationpitch", data);
        }
        else if (item.compareTo("rotationpitchmax") == 0) {
            super.loadItemData("maxrotationpitch", data);
        }
        else if (item.compareTo("addpart") == 0) {
            final String[] s = data.split("\\s*,\\s*");
            if (s.length >= 7) {
                final float rb = (s.length >= 8) ? this.toFloat(s[7]) : 0.0f;
                final VPart n = new VPart(this.toFloat(s[4]), this.toFloat(s[5]), this.toFloat(s[6]), "part" + this.partList.size(), this.toBool(s[0]), this.toBool(s[1]), this.toBool(s[2]), this.toInt(s[3]), rb);
                this.partList.add(n);
            }
        }
        else if (item.compareTo("addchildpart") == 0 && this.partList.size() > 0) {
            final String[] s = data.split("\\s*,\\s*");
            if (s.length >= 7) {
                final float rb = (s.length >= 8) ? this.toFloat(s[7]) : 0.0f;
                final VPart p = this.partList.get(this.partList.size() - 1);
                if (p.child == null) {
                    p.child = new ArrayList<VPart>();
                }
                final VPart n2 = new VPart(this.toFloat(s[4]), this.toFloat(s[5]), this.toFloat(s[6]), p.modelName + "_" + p.child.size(), this.toBool(s[0]), this.toBool(s[1]), this.toBool(s[2]), this.toInt(s[3]), rb);
                p.child.add(n2);
            }
        }
    }
    
    public String getDirectoryName() {
        return "vehicles";
    }
    
    public String getKindName() {
        return "vehicle";
    }
    
    public void preReload() {
        super.preReload();
        this.partList.clear();
    }
    
    public void postReload() {
        MCH_MOD.proxy.registerModelsVehicle(this.name, true);
    }
    
    public class VPart extends MCH_AircraftInfo.DrawnPart
    {
        public final boolean rotPitch;
        public final boolean rotYaw;
        public final int type;
        public List<VPart> child;
        public final boolean drawFP;
        public final float recoilBuf;
        
        public VPart(final float x, final float y, final float z, final String model, final boolean drawfp, final boolean roty, final boolean rotp, final int type, final float rb) {
            super((MCH_AircraftInfo)MCH_VehicleInfo.this, x, y, z, 0.0f, 0.0f, 0.0f, model);
            this.rotYaw = roty;
            this.rotPitch = rotp;
            this.type = type;
            this.child = null;
            this.drawFP = drawfp;
            this.recoilBuf = rb;
        }
    }
}
