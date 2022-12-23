//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.hud;

public class MCH_HudItemConditional extends MCH_HudItem
{
    private final boolean isEndif;
    private final String conditional;
    
    public MCH_HudItemConditional(final int fileLine, final boolean isEndif, final String conditional) {
        super(fileLine);
        this.isEndif = isEndif;
        this.conditional = conditional;
    }
    
    public boolean canExecute() {
        return true;
    }
    
    public void execute() {
        if (!this.isEndif) {
            this.parent.isIfFalse = (calc(this.conditional) == 0.0);
        }
        else {
            this.parent.isIfFalse = false;
        }
    }
}
