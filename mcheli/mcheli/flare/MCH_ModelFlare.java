//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.flare;

import mcheli.wrapper.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.client.model.*;

@SideOnly(Side.CLIENT)
public class MCH_ModelFlare extends W_ModelBase
{
    public ModelRenderer model;
    
    public MCH_ModelFlare() {
        final int SIZE = 4;
        (this.model = new ModelRenderer((ModelBase)this, 0, 0).setTextureSize(4, 4)).addBox(-2.0f, -2.0f, -2.0f, 4, 4, 4, 0.0f);
    }
    
    public void renderModel(final double yaw, final double pitch, final float par7) {
        this.model.render(par7);
    }
}
