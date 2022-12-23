//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.hud;

public class MCH_HudItemExit extends MCH_HudItem
{
    public MCH_HudItemExit(final int fileLine) {
        super(fileLine);
    }
    
    public void execute() {
        this.parent.exit = true;
    }
}
