//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.hud;

import net.minecraft.entity.*;
import mcheli.*;

public class MCH_HudItemCameraRot extends MCH_HudItem
{
    private final String drawPosX;
    private final String drawPosY;
    
    public MCH_HudItemCameraRot(final int fileLine, final String posx, final String posy) {
        super(fileLine);
        this.drawPosX = toFormula(posx);
        this.drawPosY = toFormula(posy);
    }
    
    public void execute() {
        this.drawCommonGunnerCamera((Entity)MCH_HudItemCameraRot.ac, MCH_HudItemCameraRot.ac.camera, MCH_HudItemCameraRot.colorSetting, MCH_HudItemCameraRot.centerX + calc(this.drawPosX), MCH_HudItemCameraRot.centerY + calc(this.drawPosY));
    }
    
    private void drawCommonGunnerCamera(final Entity ac, final MCH_Camera camera, final int color, final double posX, final double posY) {
        if (camera == null) {
            return;
        }
        final double centerX = posX;
        final double centerY = posY;
        final int WW = 20;
        final int WH = 10;
        final int LW = 1;
        double[] line = { centerX - 21.0, centerY - 11.0, centerX + 21.0, centerY - 11.0, centerX + 21.0, centerY + 11.0, centerX - 21.0, centerY + 11.0 };
        this.drawLine(line, color, 2);
        line = new double[] { centerX - 21.0, centerY, centerX, centerY, centerX + 21.0, centerY, centerX, centerY, centerX, centerY - 11.0, centerX, centerY, centerX, centerY + 11.0, centerX, centerY };
        this.drawLineStipple(line, color, 1, 52428);
        float pitch = camera.rotationPitch;
        if (pitch < -30.0f) {
            pitch = -30.0f;
        }
        if (pitch > 70.0f) {
            pitch = 70.0f;
        }
        pitch -= 20.0f;
        pitch *= (float)0.16;
        final float heliYaw = ac.prevRotationYaw + (ac.rotationYaw - ac.prevRotationYaw) / 2.0f;
        final float cameraYaw = camera.prevRotationYaw + (camera.rotationYaw - camera.prevRotationYaw) / 2.0f;
        float yaw = (float)MCH_Lib.getRotateDiff(ac.rotationYaw, camera.rotationYaw);
        yaw *= 2.0f;
        if (yaw < -50.0f) {
            yaw = -50.0f;
        }
        if (yaw > 50.0f) {
            yaw = 50.0f;
        }
        yaw *= (float)0.34;
        line = new double[] { centerX + yaw - 3.0, centerY + pitch - 2.0, centerX + yaw + 3.0, centerY + pitch - 2.0, centerX + yaw + 3.0, centerY + pitch + 2.0, centerX + yaw - 3.0, centerY + pitch + 2.0 };
        this.drawLine(line, color, 2);
    }
}
