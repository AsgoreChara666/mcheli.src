//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.wrapper;

import net.minecraft.client.particle.*;
import net.minecraft.world.*;
import net.minecraft.util.*;

public abstract class W_EntityFX extends EntityFX
{
    public W_EntityFX(final World par1World, final double par2, final double par4, final double par6) {
        super(par1World, par2, par4, par6);
    }
    
    public W_EntityFX(final World par1World, final double par2, final double par4, final double par6, final double par8, final double par10, final double par12) {
        super(par1World, par2, par4, par6, par8, par10, par12);
    }
    
    public AxisAlignedBB getBoundingBox() {
        return this.boundingBox;
    }
    
    public void setIcon(final IIcon icon) {
        this.setParticleIcon(icon);
    }
    
    protected void doBlockCollisions() {
        super.doBlockCollisions();
    }
}
