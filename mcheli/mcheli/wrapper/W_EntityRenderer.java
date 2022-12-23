//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.wrapper;

import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.minecraft.client.shader.*;
import java.io.*;
import mcheli.*;
import com.google.gson.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.*;

public class W_EntityRenderer
{
    public static void setItemRenderer(final Minecraft mc, final ItemRenderer ir) {
        W_Reflection.setItemRenderer(ir);
    }
    
    public static boolean isShaderSupport() {
        if (OpenGlHelper.shadersSupported) {
            final MCH_Config config = MCH_MOD.config;
            if (!MCH_Config.DisableShader.prmBool) {
                return true;
            }
        }
        return false;
    }
    
    public static void activateShader(final String n) {
        activateShader(new ResourceLocation("mcheli", "shaders/post/" + n + ".json"));
    }
    
    public static void activateShader(final ResourceLocation r) {
        final Minecraft mc = Minecraft.getMinecraft();
        try {
            (mc.entityRenderer.theShaderGroup = new ShaderGroup(mc.getTextureManager(), mc.getResourceManager(), mc.getFramebuffer(), r)).createBindFramebuffers(mc.displayWidth, mc.displayHeight);
        }
        catch (IOException ioexception) {
            ioexception.printStackTrace();
        }
        catch (JsonSyntaxException jsonsyntaxexception) {
            MCH_Lib.Log("Failed to load shader: " + r, new Object[0]);
            jsonsyntaxexception.printStackTrace();
        }
    }
    
    public static void deactivateShader() {
        Minecraft.getMinecraft().entityRenderer.deactivateShader();
    }
    
    public static void renderEntityWithPosYaw(final RenderManager rm, final Entity par1Entity, final double par2, final double par4, final double par6, final float par8, final float par9, final boolean b) {
        rm.doRenderEntity(par1Entity, par2, par4, par6, par8, par9, b);
    }
}
