//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.helicopter;

import mcheli.aircraft.*;
import java.util.*;
import mcheli.*;
import net.minecraft.item.*;

public class MCH_HeliInfo extends MCH_AircraftInfo
{
    public MCH_ItemHeli item;
    public boolean isEnableFoldBlade;
    public List<Rotor> rotorList;
    
    public MCH_HeliInfo(final String name) {
        super(name);
        this.item = null;
        this.isEnableGunnerMode = false;
        this.isEnableFoldBlade = false;
        this.rotorList = new ArrayList<Rotor>();
        this.minRotationPitch = -20.0f;
        this.maxRotationPitch = 20.0f;
    }
    
    public boolean isValidData() throws Exception {
        final double n = this.speed;
        final MCH_Config config = MCH_MOD.config;
        this.speed = (float)(n * MCH_Config.AllHeliSpeed.prmDouble);
        return super.isValidData();
    }
    
    public float getDefaultSoundRange() {
        return 80.0f;
    }
    
    public float getDefaultRotorSpeed() {
        return 79.99f;
    }
    
    public int getDefaultMaxZoom() {
        return 8;
    }
    
    public Item getItem() {
        return (Item)this.item;
    }
    
    public String getDefaultHudName(final int seatId) {
        if (seatId <= 0) {
            return "heli";
        }
        if (seatId == 1) {
            return "heli_gnr";
        }
        return "gunner";
    }
    
    public void loadItemData(final String item, final String data) {
        super.loadItemData(item, data);
        if (item.compareTo("enablefoldblade") == 0) {
            this.isEnableFoldBlade = this.toBool(data);
        }
        else if (item.compareTo("addrotor") == 0 || item.compareTo("addrotorold") == 0) {
            final String[] s = data.split("\\s*,\\s*");
            if (s.length == 8 || s.length == 9) {
                final boolean cfb = s.length == 9 && this.toBool(s[8]);
                final Rotor e = new Rotor(this.toInt(s[0]), this.toInt(s[1]), this.toFloat(s[2]), this.toFloat(s[3]), this.toFloat(s[4]), this.toFloat(s[5]), this.toFloat(s[6]), this.toFloat(s[7]), "blade" + this.rotorList.size(), cfb, item.compareTo("addrotorold") == 0);
                this.rotorList.add(e);
            }
        }
    }
    
    public String getDirectoryName() {
        return "helicopters";
    }
    
    public String getKindName() {
        return "helicopter";
    }
    
    public void preReload() {
        super.preReload();
        this.rotorList.clear();
    }
    
    public void postReload() {
        MCH_MOD.proxy.registerModelsHeli(this.name, true);
    }
    
    public class Rotor extends MCH_AircraftInfo.DrawnPart
    {
        public final int bladeNum;
        public final int bladeRot;
        public final boolean haveFoldFunc;
        public final boolean oldRenderMethod;
        
        public Rotor(final int b, final int br, final float x, final float y, final float z, final float rx, final float ry, final float rz, final String model, final boolean hf, final boolean old) {
            super((MCH_AircraftInfo)MCH_HeliInfo.this, x, y, z, rx, ry, rz, model);
            this.bladeNum = b;
            this.bladeRot = br;
            this.haveFoldFunc = hf;
            this.oldRenderMethod = old;
        }
    }
}
