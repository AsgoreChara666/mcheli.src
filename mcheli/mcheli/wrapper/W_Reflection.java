//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.wrapper;

import net.minecraft.client.renderer.entity.*;
import cpw.mods.fml.common.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.server.*;
import net.minecraft.network.*;
import java.util.*;

public class W_Reflection
{
    public static RenderManager getRenderManager(final Render render) {
        try {
            return (RenderManager)ObfuscationReflectionHelper.getPrivateValue((Class)Render.class, (Object)render, new String[] { "renderManager", "renderManager" });
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static void restoreDefaultThirdPersonDistance() {
        setThirdPersonDistance(4.0f);
    }
    
    public static void setThirdPersonDistance(final float dist) {
        if (dist < 0.1) {
            return;
        }
        try {
            final Minecraft mc = Minecraft.getMinecraft();
            ObfuscationReflectionHelper.setPrivateValue((Class)EntityRenderer.class, (Object)mc.entityRenderer, (Object)dist, new String[] { "thirdPersonDistance", "thirdPersonDistance" });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static float getThirdPersonDistance() {
        try {
            final Minecraft mc = Minecraft.getMinecraft();
            return (float)ObfuscationReflectionHelper.getPrivateValue((Class)EntityRenderer.class, (Object)mc.entityRenderer, new String[] { "thirdPersonDistance", "thirdPersonDistance" });
        }
        catch (Exception e) {
            e.printStackTrace();
            return 4.0f;
        }
    }
    
    public static void setCameraRoll(float roll) {
        try {
            roll = MathHelper.wrapAngleTo180_float(roll);
            final Minecraft mc = Minecraft.getMinecraft();
            ObfuscationReflectionHelper.setPrivateValue((Class)EntityRenderer.class, (Object)Minecraft.getMinecraft().entityRenderer, (Object)roll, new String[] { "camRoll", "camRoll" });
            ObfuscationReflectionHelper.setPrivateValue((Class)EntityRenderer.class, (Object)Minecraft.getMinecraft().entityRenderer, (Object)roll, new String[] { "prevCamRoll", "prevCamRoll" });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static float getPrevCameraRoll() {
        try {
            final Minecraft mc = Minecraft.getMinecraft();
            return (float)ObfuscationReflectionHelper.getPrivateValue((Class)EntityRenderer.class, (Object)Minecraft.getMinecraft().entityRenderer, new String[] { "prevCamRoll", "prevCamRoll" });
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0.0f;
        }
    }
    
    public static void restoreCameraZoom() {
        setCameraZoom(1.0f);
    }
    
    public static void setCameraZoom(final float zoom) {
        try {
            final Minecraft mc = Minecraft.getMinecraft();
            ObfuscationReflectionHelper.setPrivateValue((Class)EntityRenderer.class, (Object)mc.entityRenderer, (Object)zoom, new String[] { "cameraZoom", "cameraZoom" });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void setItemRenderer(final ItemRenderer r) {
        try {
            final Minecraft mc = Minecraft.getMinecraft();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void setCreativeDigSpeed(final int n) {
        try {
            final Minecraft mc = Minecraft.getMinecraft();
            ObfuscationReflectionHelper.setPrivateValue((Class)PlayerControllerMP.class, (Object)mc.playerController, (Object)n, new String[] { "blockHitDelay", "blockHitDelay" });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static ItemRenderer getItemRenderer() {
        return Minecraft.getMinecraft().entityRenderer.itemRenderer;
    }
    
    public static void setItemRenderer_ItemToRender(final ItemStack itemToRender) {
        try {
            ObfuscationReflectionHelper.setPrivateValue((Class)ItemRenderer.class, (Object)getItemRenderer(), (Object)itemToRender, new String[] { "itemToRender", "itemToRender" });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static ItemStack getItemRenderer_ItemToRender() {
        try {
            final ItemStack itemstack = (ItemStack)ObfuscationReflectionHelper.getPrivateValue((Class)ItemRenderer.class, (Object)getItemRenderer(), new String[] { "itemToRender", "itemToRender" });
            return itemstack;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static void setItemRendererProgress(final float equippedProgress) {
        try {
            ObfuscationReflectionHelper.setPrivateValue((Class)ItemRenderer.class, (Object)getItemRenderer(), (Object)equippedProgress, new String[] { "equippedProgress", "equippedProgress" });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void setBoundingBox(final Entity entity, final AxisAlignedBB bb) {
        try {
            ObfuscationReflectionHelper.setPrivateValue((Class)Entity.class, (Object)entity, (Object)bb, new String[] { "boundingBox", "boundingBox" });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static List getNetworkManagers() {
        try {
            final List list = (List)ObfuscationReflectionHelper.getPrivateValue((Class)NetworkSystem.class, (Object)MinecraftServer.getServer().getNetworkSystem(), new String[] { "networkManagers", "networkManagers" });
            return list;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static Queue getReceivedPacketsQueue(final NetworkManager nm) {
        try {
            final Queue queue = (Queue)ObfuscationReflectionHelper.getPrivateValue((Class)NetworkManager.class, (Object)nm, new String[] { "receivedPacketsQueue", "receivedPacketsQueue" });
            return queue;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static Queue getSendPacketsQueue(final NetworkManager nm) {
        try {
            final Queue queue = (Queue)ObfuscationReflectionHelper.getPrivateValue((Class)NetworkManager.class, (Object)nm, new String[] { "outboundPacketsQueue", "outboundPacketsQueue" });
            return queue;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
