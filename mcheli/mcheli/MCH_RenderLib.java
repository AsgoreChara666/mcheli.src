//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli;

import net.minecraft.util.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;

public class MCH_RenderLib
{
    public static void drawLine(final Vec3[] points, final int color) {
        drawLine(points, color, 1, 1);
    }
    
    public static void drawLine(final Vec3[] points, final int color, final int mode, final int width) {
        final int prevWidth = GL11.glGetInteger(2849);
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4ub((byte)(color >> 16 & 0xFF), (byte)(color >> 8 & 0xFF), (byte)(color >> 0 & 0xFF), (byte)(color >> 24 & 0xFF));
        GL11.glLineWidth((float)width);
        final Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawing(mode);
        for (final Vec3 v : points) {
            tessellator.addVertex(v.xCoord, v.yCoord, v.zCoord);
        }
        tessellator.draw();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glColor4b((byte)(-1), (byte)(-1), (byte)(-1), (byte)(-1));
        GL11.glLineWidth((float)prevWidth);
    }
}
