//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.hud;

import org.lwjgl.opengl.*;
import mcheli.wrapper.*;

public class MCH_HudItemTexture extends MCH_HudItem
{
    private final String name;
    private final String left;
    private final String top;
    private final String width;
    private final String height;
    private final String uLeft;
    private final String vTop;
    private final String uWidth;
    private final String vHeight;
    private final String rot;
    private int textureWidth;
    private int textureHeight;
    
    public MCH_HudItemTexture(final int fileLine, final String name, final String left, final String top, final String width, final String height, final String uLeft, final String vTop, final String uWidth, final String vHeight, final String rot) {
        super(fileLine);
        this.name = name;
        this.left = toFormula(left);
        this.top = toFormula(top);
        this.width = toFormula(width);
        this.height = toFormula(height);
        this.uLeft = toFormula(uLeft);
        this.vTop = toFormula(vTop);
        this.uWidth = toFormula(uWidth);
        this.vHeight = toFormula(vHeight);
        this.rot = toFormula(rot);
        final int n = 0;
        this.textureHeight = n;
        this.textureWidth = n;
    }
    
    public void execute() {
        GL11.glEnable(3042);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        if (this.textureWidth == 0 || this.textureHeight == 0) {
            int w = 0;
            int h = 0;
            final W_TextureUtil.TextureParam prm = W_TextureUtil.getTextureInfo("mcheli", "textures/gui/" + this.name + ".png");
            if (prm != null) {
                w = prm.width;
                h = prm.height;
            }
            this.textureWidth = ((w > 0) ? w : 256);
            this.textureHeight = ((h > 0) ? h : 256);
        }
        this.drawTexture(this.name, MCH_HudItemTexture.centerX + calc(this.left), MCH_HudItemTexture.centerY + calc(this.top), calc(this.width), calc(this.height), calc(this.uLeft), calc(this.vTop), calc(this.uWidth), calc(this.vHeight), (float)calc(this.rot), this.textureWidth, this.textureHeight);
    }
}
