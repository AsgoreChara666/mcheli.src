//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.debug;

import mcheli.wrapper.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.client.model.*;

@SideOnly(Side.CLIENT)
public class MCH_ModelTest extends W_ModelBase
{
    public ModelRenderer test;
    
    public MCH_ModelTest() {
        final int SIZE = 10;
        (this.test = new ModelRenderer((ModelBase)this, 0, 0)).addBox(-5.0f, -5.0f, -5.0f, 10, 10, 10, 0.0f);
    }
    
    public void renderModel(final double yaw, final double pitch, final float par7) {
        this.test.render(par7);
    }
}
