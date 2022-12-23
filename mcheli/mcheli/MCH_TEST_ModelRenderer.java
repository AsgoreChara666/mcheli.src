//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli;

import net.minecraft.client.model.*;
import org.lwjgl.opengl.*;

public class MCH_TEST_ModelRenderer extends ModelRenderer
{
    public MCH_TEST_ModelRenderer(final ModelBase par1ModelBase) {
        super(par1ModelBase);
    }
    
    public void render(final float par1) {
        GL11.glPushMatrix();
        GL11.glScaled(0.2, -0.2, 0.2);
        MCH_ModelManager.render("helicopters", "ah-64");
        GL11.glPopMatrix();
    }
}
