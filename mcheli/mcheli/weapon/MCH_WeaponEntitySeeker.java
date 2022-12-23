//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.weapon;

import net.minecraft.world.*;
import net.minecraft.util.*;

public abstract class MCH_WeaponEntitySeeker extends MCH_WeaponBase
{
    public MCH_IEntityLockChecker entityLockChecker;
    public MCH_WeaponGuidanceSystem guidanceSystem;
    
    public MCH_WeaponEntitySeeker(final World w, final Vec3 v, final float yaw, final float pitch, final String nm, final MCH_WeaponInfo wi) {
        super(w, v, yaw, pitch, nm, wi);
        this.guidanceSystem = new MCH_WeaponGuidanceSystem(w);
        this.guidanceSystem.lockRange = 200.0;
        this.guidanceSystem.lockAngle = 5;
        this.guidanceSystem.setLockCountMax(25);
    }
    
    public MCH_WeaponGuidanceSystem getGuidanceSystem() {
        return this.guidanceSystem;
    }
    
    public int getLockCount() {
        return this.guidanceSystem.getLockCount();
    }
    
    public void setLockCountMax(final int n) {
        this.guidanceSystem.setLockCountMax(n);
    }
    
    public int getLockCountMax() {
        return this.guidanceSystem.getLockCountMax();
    }
    
    public void setLockChecker(final MCH_IEntityLockChecker checker) {
        this.guidanceSystem.checker = checker;
    }
    
    public void update(final int countWait) {
        super.update(countWait);
        this.guidanceSystem.update();
    }
}
