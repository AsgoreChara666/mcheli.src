//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.tool.rangefinder;

import net.minecraftforge.client.*;
import net.minecraft.item.*;
import org.lwjgl.opengl.*;
import mcheli.wrapper.*;
import mcheli.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;

public class MCH_ItemRenderRangeFinder implements IItemRenderer
{
    public boolean handleRenderType(final ItemStack item, final IItemRenderer.ItemRenderType type) {
        return type == IItemRenderer.ItemRenderType.EQUIPPED || type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON || type == IItemRenderer.ItemRenderType.ENTITY;
    }
    
    public boolean shouldUseRenderHelper(final IItemRenderer.ItemRenderType type, final ItemStack item, final IItemRenderer.ItemRendererHelper helper) {
        return type == IItemRenderer.ItemRenderType.EQUIPPED || type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON || type == IItemRenderer.ItemRenderType.ENTITY;
    }
    
    public void renderItem(final IItemRenderer.ItemRenderType type, final ItemStack item, final Object... data) {
        GL11.glPushMatrix();
        W_McClient.MOD_bindTexture("textures/rangefinder.png");
        float size = 1.0f;
        switch (type) {
            case ENTITY: {
                size = 2.2f;
                GL11.glScalef(size, size, size);
                GL11.glRotatef(-130.0f, 0.0f, 1.0f, 0.0f);
                GL11.glRotatef(70.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(5.0f, 0.0f, 0.0f, 1.0f);
                GL11.glTranslatef(0.0f, 0.0f, -0.0f);
                MCH_ModelManager.render("rangefinder");
                break;
            }
            case EQUIPPED: {
                size = 2.2f;
                GL11.glScalef(size, size, size);
                GL11.glRotatef(-130.0f, 0.0f, 1.0f, 0.0f);
                GL11.glRotatef(70.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(5.0f, 0.0f, 0.0f, 1.0f);
                if (Minecraft.getMinecraft().thePlayer.getItemInUseDuration() > 0) {
                    GL11.glTranslatef(0.4f, -0.35f, -0.3f);
                }
                else {
                    GL11.glTranslatef(0.2f, -0.35f, -0.3f);
                }
                MCH_ModelManager.render("rangefinder");
                break;
            }
            case EQUIPPED_FIRST_PERSON: {
                if (!MCH_ItemRangeFinder.isUsingScope((EntityPlayer)Minecraft.getMinecraft().thePlayer)) {
                    size = 2.2f;
                    GL11.glScalef(size, size, size);
                    GL11.glRotatef(-210.0f, 0.0f, 1.0f, 0.0f);
                    GL11.glRotatef(-10.0f, 1.0f, 0.0f, 0.0f);
                    GL11.glRotatef(-10.0f, 0.0f, 0.0f, 1.0f);
                    GL11.glTranslatef(0.06f, 0.53f, -0.1f);
                    MCH_ModelManager.render("rangefinder");
                    break;
                }
                break;
            }
        }
        GL11.glPopMatrix();
    }
}
