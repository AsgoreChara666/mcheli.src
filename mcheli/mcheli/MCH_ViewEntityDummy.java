//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli;

import net.minecraft.client.entity.*;
import net.minecraft.world.*;
import net.minecraft.client.*;
import mcheli.wrapper.*;
import net.minecraft.entity.*;

public class MCH_ViewEntityDummy extends EntityPlayerSP
{
    private static MCH_ViewEntityDummy instance;
    private float zoom;
    
    private MCH_ViewEntityDummy(final World world) {
        super(Minecraft.getMinecraft(), world, W_Session.newSession(), 0);
        this.hurtTime = 0;
        this.maxHurtTime = 1;
        this.setSize(1.0f, 1.0f);
    }
    
    public static MCH_ViewEntityDummy getInstance(final World w) {
        if ((MCH_ViewEntityDummy.instance == null || MCH_ViewEntityDummy.instance.isDead) && w.isRemote) {
            MCH_ViewEntityDummy.instance = new MCH_ViewEntityDummy(w);
            if (Minecraft.getMinecraft().thePlayer != null) {
                MCH_ViewEntityDummy.instance.movementInput = Minecraft.getMinecraft().thePlayer.movementInput;
            }
            MCH_ViewEntityDummy.instance.setPosition(0.0, -4.0, 0.0);
            w.spawnEntityInWorld((Entity)MCH_ViewEntityDummy.instance);
        }
        return MCH_ViewEntityDummy.instance;
    }
    
    public static void onUnloadWorld() {
        if (MCH_ViewEntityDummy.instance != null) {
            MCH_ViewEntityDummy.instance.setDead();
            MCH_ViewEntityDummy.instance = null;
        }
    }
    
    public void onUpdate() {
    }
    
    public void update(final MCH_Camera camera) {
        if (camera == null) {
            return;
        }
        this.zoom = camera.getCameraZoom();
        this.prevRotationYaw = this.rotationYaw;
        this.prevRotationPitch = this.rotationPitch;
        this.rotationYaw = camera.rotationYaw;
        this.rotationPitch = camera.rotationPitch;
        this.prevPosX = camera.posX;
        this.prevPosY = camera.posY;
        this.prevPosZ = camera.posZ;
        this.posX = camera.posX;
        this.posY = camera.posY;
        this.posZ = camera.posZ;
    }
    
    public static void setCameraPosition(final double x, final double y, final double z) {
        if (MCH_ViewEntityDummy.instance == null) {
            return;
        }
        MCH_ViewEntityDummy.instance.prevPosX = x;
        MCH_ViewEntityDummy.instance.prevPosY = y;
        MCH_ViewEntityDummy.instance.prevPosZ = z;
        MCH_ViewEntityDummy.instance.lastTickPosX = x;
        MCH_ViewEntityDummy.instance.lastTickPosY = y;
        MCH_ViewEntityDummy.instance.lastTickPosZ = z;
        MCH_ViewEntityDummy.instance.posX = x;
        MCH_ViewEntityDummy.instance.posY = y;
        MCH_ViewEntityDummy.instance.posZ = z;
    }
    
    public float getFOVMultiplier() {
        return super.getFOVMultiplier() * (1.0f / this.zoom);
    }
    
    static {
        MCH_ViewEntityDummy.instance = null;
    }
}
