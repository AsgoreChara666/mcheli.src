//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.multiplay;

import net.minecraft.entity.player.*;
import net.minecraft.client.gui.*;
import mcheli.wrapper.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.*;

public class MCH_GuiScoreboard_CreateTeam extends MCH_GuiScoreboard_Base
{
    private GuiButton buttonCreateTeamOK;
    private GuiButton buttonCreateTeamFF;
    private GuiTextField editCreateTeamName;
    private static boolean friendlyFire;
    private int lastTeamColor;
    private static final String[] colorNames;
    
    public MCH_GuiScoreboard_CreateTeam(final MCH_IGuiScoreboard switcher, final EntityPlayer player) {
        super(switcher, player);
        this.lastTeamColor = 0;
    }
    
    public void initGui() {
        super.initGui();
        final ScaledResolution sr = new W_ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        final int factor = (sr.getScaleFactor() > 0) ? sr.getScaleFactor() : 1;
        this.guiLeft = 0;
        this.guiTop = 0;
        final int x = this.mc.displayWidth / 2 / factor;
        final int y = this.mc.displayHeight / 2 / factor;
        final GuiButton buttonCTNextC = new GuiButton(576, x + 40, y - 20, 40, 20, ">");
        final GuiButton buttonCTPrevC = new GuiButton(577, x - 80, y - 20, 40, 20, "<");
        this.buttonCreateTeamFF = new GuiButton(560, x - 80, y + 20, 160, 20, "");
        this.buttonCreateTeamOK = new GuiButton(528, x - 80, y + 60, 80, 20, "OK");
        final GuiButton buttonCTCancel = new GuiButton(544, x + 0, y + 60, 80, 20, "Cancel");
        (this.editCreateTeamName = new GuiTextField(this.fontRendererObj, x - 80, y - 55, 160, 20)).setText("");
        this.editCreateTeamName.setTextColor(-1);
        this.editCreateTeamName.setMaxStringLength(16);
        this.editCreateTeamName.setFocused(true);
        this.listGui.add(buttonCTNextC);
        this.listGui.add(buttonCTPrevC);
        this.listGui.add(this.buttonCreateTeamFF);
        this.listGui.add(this.buttonCreateTeamOK);
        this.listGui.add(buttonCTCancel);
        this.listGui.add(this.editCreateTeamName);
    }
    
    public void updateScreen() {
        final String teamName = this.editCreateTeamName.getText();
        this.buttonCreateTeamOK.enabled = (teamName.length() > 0 && teamName.length() <= 16);
        this.editCreateTeamName.updateCursorCounter();
        this.buttonCreateTeamFF.displayString = "Friendly Fire : " + (MCH_GuiScoreboard_CreateTeam.friendlyFire ? "ON" : "OFF");
    }
    
    public void acviveScreen() {
        this.editCreateTeamName.setText("");
        this.editCreateTeamName.setFocused(true);
    }
    
    protected void keyTyped(final char c, final int code) {
        if (code == 1) {
            this.switchScreen(MCH_GuiScoreboard_Base.SCREEN_ID.MAIN);
        }
        else {
            this.editCreateTeamName.textboxKeyTyped(c, code);
        }
    }
    
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        this.editCreateTeamName.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    protected void actionPerformed(final GuiButton btn) {
        if (btn != null && btn.enabled) {
            switch (btn.id) {
                case 528: {
                    final String teamName = this.editCreateTeamName.getText();
                    if (teamName.length() > 0 && teamName.length() <= 16) {
                        MCH_PacketIndMultiplayCommand.send(768, "scoreboard teams add " + teamName);
                        MCH_PacketIndMultiplayCommand.send(768, "scoreboard teams option " + teamName + " color " + MCH_GuiScoreboard_CreateTeam.colorNames[this.lastTeamColor]);
                        MCH_PacketIndMultiplayCommand.send(768, "scoreboard teams option " + teamName + " friendlyfire " + MCH_GuiScoreboard_CreateTeam.friendlyFire);
                    }
                    this.switchScreen(MCH_GuiScoreboard_Base.SCREEN_ID.MAIN);
                    break;
                }
                case 544: {
                    this.switchScreen(MCH_GuiScoreboard_Base.SCREEN_ID.MAIN);
                    break;
                }
                case 560: {
                    MCH_GuiScoreboard_CreateTeam.friendlyFire = !MCH_GuiScoreboard_CreateTeam.friendlyFire;
                    break;
                }
                case 576: {
                    ++this.lastTeamColor;
                    if (this.lastTeamColor >= MCH_GuiScoreboard_CreateTeam.colorNames.length) {
                        this.lastTeamColor = 0;
                        break;
                    }
                    break;
                }
                case 577: {
                    --this.lastTeamColor;
                    if (this.lastTeamColor < 0) {
                        this.lastTeamColor = MCH_GuiScoreboard_CreateTeam.colorNames.length - 1;
                        break;
                    }
                    break;
                }
            }
        }
    }
    
    protected void drawGuiContainerBackgroundLayer(final float par1, final int par2, final int par3) {
        drawList(this.mc, this.fontRendererObj, true);
        final ScaledResolution sr = new W_ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        final int factor = (sr.getScaleFactor() > 0) ? sr.getScaleFactor() : 1;
        W_McClient.MOD_bindTexture("textures/gui/mp_new_team.png");
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        int x = (this.mc.displayWidth / factor - 222) / 2;
        int y = (this.mc.displayHeight / factor - 200) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, 222, 200);
        x = this.mc.displayWidth / 2 / factor;
        y = this.mc.displayHeight / 2 / factor;
        this.drawCenteredString("Create team", x, y - 85, -1);
        this.drawCenteredString("Team name", x, y - 70, -1);
        final EnumChatFormatting ecf = EnumChatFormatting.getValueByName(MCH_GuiScoreboard_CreateTeam.colorNames[this.lastTeamColor]);
        this.drawCenteredString(ecf + "Team Color" + ecf, x, y - 13, -1);
        this.editCreateTeamName.drawTextBox();
    }
    
    static {
        MCH_GuiScoreboard_CreateTeam.friendlyFire = true;
        colorNames = new String[] { "RESET", "BLACK", "DARK_BLUE", "DARK_GREEN", "DARK_AQUA", "DARK_RED", "DARK_PURPLE", "GOLD", "GRAY", "DARK_GRAY", "BLUE", "GREEN", "AQUA", "RED", "LIGHT_PURPLE", "YELLOW" };
    }
}
