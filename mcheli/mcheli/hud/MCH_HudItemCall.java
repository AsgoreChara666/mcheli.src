//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.hud;

public class MCH_HudItemCall extends MCH_HudItem
{
    private final String hudName;
    
    public MCH_HudItemCall(final int fileLine, final String name) {
        super(fileLine);
        this.hudName = name;
    }
    
    public void execute() {
        final MCH_Hud hud = MCH_HudManager.get(this.hudName);
        if (hud != null) {
            hud.drawItems();
        }
    }
}
