//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.hud;

public class MCH_HudItemColor extends MCH_HudItem
{
    private final String updateColor;
    
    public MCH_HudItemColor(final int fileLine, final String newColor) {
        super(fileLine);
        this.updateColor = newColor;
    }
    
    public static MCH_HudItemColor createByParams(final int fileLine, final String[] prm) {
        if (prm.length == 1) {
            return new MCH_HudItemColor(fileLine, toFormula(prm[0]));
        }
        if (prm.length == 4) {
            return new MCH_HudItemColor(fileLine, "((" + toFormula(prm[0]) + ")<<24)|" + "((" + toFormula(prm[1]) + ")<<16)|" + "((" + toFormula(prm[2]) + ")<<8 )|" + "((" + toFormula(prm[3]) + ")<<0 )");
        }
        return null;
    }
    
    public void execute() {
        final double d = calc(this.updateColor);
        final long l = (long)d;
        MCH_HudItem.colorSetting = (int)l;
        updateVarMapItem("color", getColor());
    }
}
