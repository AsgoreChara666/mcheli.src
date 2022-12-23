//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.hud;

public class MCH_HudItemLineStipple extends MCH_HudItem
{
    private final String pat;
    private final String fac;
    private final String[] pos;
    
    public MCH_HudItemLineStipple(final int fileLine, final String[] position) {
        super(fileLine);
        this.pat = position[0];
        this.fac = position[1];
        this.pos = new String[position.length - 2];
        for (int i = 0; i < position.length - 2; ++i) {
            this.pos[i] = toFormula(position[2 + i]);
        }
    }
    
    public void execute() {
        final double[] lines = new double[this.pos.length];
        for (int i = 0; i < lines.length; i += 2) {
            lines[i + 0] = MCH_HudItemLineStipple.centerX + calc(this.pos[i + 0]);
            lines[i + 1] = MCH_HudItemLineStipple.centerY + calc(this.pos[i + 1]);
        }
        this.drawLineStipple(lines, MCH_HudItemLineStipple.colorSetting, (int)calc(this.fac), (int)calc(this.pat));
    }
}
