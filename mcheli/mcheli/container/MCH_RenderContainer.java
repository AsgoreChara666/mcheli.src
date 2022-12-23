//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.container;

import mcheli.wrapper.*;
import cpw.mods.fml.relauncher.*;
import java.util.*;
import net.minecraft.entity.*;
import mcheli.aircraft.*;
import org.lwjgl.opengl.*;
import mcheli.*;
import net.minecraft.util.*;

@SideOnly(Side.CLIENT)
public class MCH_RenderContainer extends W_Render
{
    public static final Random rand;
    
    public MCH_RenderContainer() {
        this.shadowSize = 0.5f;
    }
    
    public void doRender(final Entity entity, final double posX, final double posY, final double posZ, final float par8, final float tickTime) {
        if (MCH_RenderAircraft.shouldSkipRender(entity)) {
            return;
        }
        GL11.glPushMatrix();
        GL11.glEnable(2884);
        GL11.glTranslated(posX, posY - 0.2, posZ);
        final float yaw = MCH_Lib.smoothRot(entity.rotationYaw, entity.prevRotationYaw, tickTime);
        final float pitch = MCH_Lib.smoothRot(entity.rotationPitch, entity.prevRotationPitch, tickTime);
        GL11.glRotatef(yaw, 0.0f, -1.0f, 0.0f);
        GL11.glRotatef(pitch, 1.0f, 0.0f, 0.0f);
        GL11.glColor4f(0.75f, 0.75f, 0.75f, 1.0f);
        this.bindTexture("textures/container.png");
        MCH_ModelManager.render("container");
        GL11.glPopMatrix();
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return MCH_RenderContainer.TEX_DEFAULT;
    }
    
    static {
        rand = new Random();
    }
}
