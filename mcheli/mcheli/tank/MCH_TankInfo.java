//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.tank;

import mcheli.aircraft.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraft.util.*;
import mcheli.*;

public class MCH_TankInfo extends MCH_AircraftInfo
{
    public MCH_ItemTank item;
    public int weightType;
    public float weightedCenterZ;
    
    public Item getItem() {
        return (Item)this.item;
    }
    
    public MCH_TankInfo(final String name) {
        super(name);
        this.item = null;
        this.weightType = 0;
        this.weightedCenterZ = 0.0f;
    }
    
    public List<MCH_AircraftInfo.Wheel> getDefaultWheelList() {
        final List<MCH_AircraftInfo.Wheel> list = new ArrayList<MCH_AircraftInfo.Wheel>();
        list.add(new MCH_AircraftInfo.Wheel((MCH_AircraftInfo)this, Vec3.createVectorHelper(1.5, -0.24, 2.0)));
        list.add(new MCH_AircraftInfo.Wheel((MCH_AircraftInfo)this, Vec3.createVectorHelper(1.5, -0.24, -2.0)));
        return list;
    }
    
    public float getDefaultSoundRange() {
        return 50.0f;
    }
    
    public float getDefaultRotorSpeed() {
        return 47.94f;
    }
    
    private float getDefaultStepHeight() {
        return 0.6f;
    }
    
    public float getMaxSpeed() {
        return 1.8f;
    }
    
    public int getDefaultMaxZoom() {
        return 8;
    }
    
    public String getDefaultHudName(final int seatId) {
        if (seatId <= 0) {
            return "tank";
        }
        if (seatId == 1) {
            return "tank";
        }
        return "gunner";
    }
    
    public boolean isValidData() throws Exception {
        final double n = this.speed;
        final MCH_Config config = MCH_MOD.config;
        this.speed = (float)(n * MCH_Config.AllTankSpeed.prmDouble);
        return super.isValidData();
    }
    
    public void loadItemData(final String item, String data) {
        super.loadItemData(item, data);
        if (item.equalsIgnoreCase("WeightType")) {
            data = data.toLowerCase();
            this.weightType = (data.equals("tank") ? 2 : (data.equals("car") ? 1 : 0));
        }
        else if (item.equalsIgnoreCase("WeightedCenterZ")) {
            this.weightedCenterZ = this.toFloat(data, -1000.0f, 1000.0f);
        }
    }
    
    public String getDirectoryName() {
        return "tanks";
    }
    
    public String getKindName() {
        return "tank";
    }
    
    public void preReload() {
        super.preReload();
    }
    
    public void postReload() {
        MCH_MOD.proxy.registerModelsTank(this.name, true);
    }
}
