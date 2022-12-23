//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.hud;

public class MCH_HudItemRect extends MCH_HudItem
{
    private final String left;
    private final String top;
    private final String width;
    private final String height;
    
    public MCH_HudItemRect(final int fileLine, final String left, final String top, final String width, final String height) {
        super(fileLine);
        this.left = toFormula(left);
        this.top = toFormula(top);
        this.width = toFormula(width);
        this.height = toFormula(height);
    }
    
    public void execute() {
        final double x2 = MCH_HudItemRect.centerX + calc(this.left);
        final double y2 = MCH_HudItemRect.centerY + calc(this.top);
        final double x3 = x2 + (int)calc(this.width);
        final double y3 = y2 + (int)calc(this.height);
        drawRect(x3, y3, x2, y2, MCH_HudItemRect.colorSetting);
    }
}
