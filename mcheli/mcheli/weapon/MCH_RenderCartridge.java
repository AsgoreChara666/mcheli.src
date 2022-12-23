//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.weapon;

import mcheli.wrapper.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.*;

@SideOnly(Side.CLIENT)
public class MCH_RenderCartridge extends W_Render
{
    public MCH_RenderCartridge() {
        this.shadowSize = 0.0f;
    }
    
    public void doRender(final Entity entity, final double posX, final double posY, final double posZ, final float par8, final float tickTime) {
        MCH_EntityCartridge cartridge = null;
        cartridge = (MCH_EntityCartridge)entity;
        if (cartridge.model != null && !cartridge.texture_name.isEmpty()) {
            GL11.glPushMatrix();
            GL11.glTranslated(posX, posY, posZ);
            GL11.glScalef(cartridge.getScale(), cartridge.getScale(), cartridge.getScale());
            float prevYaw = cartridge.prevRotationYaw;
            if (cartridge.rotationYaw - prevYaw < -180.0f) {
                prevYaw -= 360.0f;
            }
            else if (prevYaw - cartridge.rotationYaw < -180.0f) {
                prevYaw += 360.0f;
            }
            final float yaw = -(prevYaw + (cartridge.rotationYaw - prevYaw) * tickTime);
            final float pitch = cartridge.prevRotationPitch + (cartridge.rotationPitch - cartridge.prevRotationPitch) * tickTime;
            GL11.glRotatef(yaw, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(pitch, 1.0f, 0.0f, 0.0f);
            this.bindTexture("textures/bullets/" + cartridge.texture_name + ".png");
            cartridge.model.renderAll();
            GL11.glPopMatrix();
        }
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return MCH_RenderCartridge.TEX_DEFAULT;
    }
}
