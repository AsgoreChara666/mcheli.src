//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.hud;

import mcheli.*;
import java.util.*;

public class MCH_HudItemRadar extends MCH_HudItem
{
    private final String rot;
    private final String left;
    private final String top;
    private final String width;
    private final String height;
    private final boolean isEntityRadar;
    
    public MCH_HudItemRadar(final int fileLine, final boolean isEntityRadar, final String rot, final String left, final String top, final String width, final String height) {
        super(fileLine);
        this.isEntityRadar = isEntityRadar;
        this.rot = toFormula(rot);
        this.left = toFormula(left);
        this.top = toFormula(top);
        this.width = toFormula(width);
        this.height = toFormula(height);
    }
    
    public void execute() {
        if (this.isEntityRadar) {
            if (MCH_HudItemRadar.EntityList != null && MCH_HudItemRadar.EntityList.size() > 0) {
                this.drawEntityList(MCH_HudItemRadar.EntityList, (float)calc(this.rot), MCH_HudItemRadar.centerX + calc(this.left), MCH_HudItemRadar.centerY + calc(this.top), calc(this.width), calc(this.height));
            }
        }
        else if (MCH_HudItemRadar.EnemyList != null && MCH_HudItemRadar.EnemyList.size() > 0) {
            this.drawEntityList(MCH_HudItemRadar.EnemyList, (float)calc(this.rot), MCH_HudItemRadar.centerX + calc(this.left), MCH_HudItemRadar.centerY + calc(this.top), calc(this.width), calc(this.height));
        }
    }
    
    protected void drawEntityList(final ArrayList<MCH_Vector2> src, final float r, final double left, final double top, final double w, final double h) {
        final double w2 = -w / 2.0;
        final double w3 = w / 2.0;
        final double h2 = -h / 2.0;
        final double h3 = h / 2.0;
        final double w_factor = w / 64.0;
        final double h_factor = h / 64.0;
        final double[] list = new double[src.size() * 2];
        int idx = 0;
        for (final MCH_Vector2 v : src) {
            list[idx + 0] = v.x / 2.0 * w_factor;
            list[idx + 1] = v.y / 2.0 * h_factor;
            idx += 2;
        }
        MCH_Lib.rotatePoints(list, r);
        final ArrayList<Double> drawList = new ArrayList<Double>();
        for (int i = 0; i + 1 < list.length; i += 2) {
            if (list[i + 0] > w2 && list[i + 0] < w3 && list[i + 1] > h2 && list[i + 1] < h3) {
                drawList.add(list[i + 0] + left + w / 2.0);
                drawList.add(list[i + 1] + top + h / 2.0);
            }
        }
        this.drawPoints((ArrayList)drawList, MCH_HudItemRadar.colorSetting, MCH_HudItemRadar.scaleFactor * 2);
    }
}
