//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.multiplay;

import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.client.*;
import mcheli.wrapper.*;
import java.util.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.*;
import net.minecraft.client.network.*;
import net.minecraft.scoreboard.*;
import net.minecraft.client.renderer.*;

public abstract class MCH_GuiScoreboard_Base extends W_GuiContainer
{
    public List<Gui> listGui;
    public static final int BUTTON_ID_SHUFFLE = 256;
    public static final int BUTTON_ID_CREATE_TEAM = 512;
    public static final int BUTTON_ID_CREATE_TEAM_OK = 528;
    public static final int BUTTON_ID_CREATE_TEAM_CANCEL = 544;
    public static final int BUTTON_ID_CREATE_TEAM_FF = 560;
    public static final int BUTTON_ID_CREATE_TEAM_NEXT_C = 576;
    public static final int BUTTON_ID_CREATE_TEAM_PREV_C = 577;
    public static final int BUTTON_ID_JUMP_SPAWN_POINT = 768;
    public static final int BUTTON_ID_SWITCH_PVP = 1024;
    public static final int BUTTON_ID_DESTORY_ALL = 1280;
    private MCH_IGuiScoreboard screen_switcher;
    
    public MCH_GuiScoreboard_Base(final MCH_IGuiScoreboard switcher, final EntityPlayer player) {
        super((Container)new MCH_ContainerScoreboard(player));
        this.screen_switcher = switcher;
        this.mc = Minecraft.getMinecraft();
    }
    
    public void initGui() {
    }
    
    public void initGui(final List buttonList, final GuiScreen parents) {
        this.listGui = new ArrayList<Gui>();
        this.mc = Minecraft.getMinecraft();
        this.fontRendererObj = this.mc.fontRendererObj;
        this.width = parents.width;
        this.height = parents.height;
        this.initGui();
        for (final Gui b : this.listGui) {
            if (b instanceof GuiButton) {
                buttonList.add(b);
            }
        }
        this.buttonList.clear();
    }
    
    public static void setVisible(final Object g, final boolean v) {
        if (g instanceof GuiButton) {
            ((GuiButton)g).visible = v;
        }
        if (g instanceof GuiTextField) {
            ((GuiTextField)g).setVisible(v);
        }
    }
    
    public void updateScreenButtons(final List list) {
    }
    
    protected void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY) {
    }
    
    public int getTeamNum() {
        return this.mc.theWorld.getScoreboard().getTeams().size();
    }
    
    protected void acviveScreen() {
    }
    
    public void onSwitchScreen() {
        for (final Object b : this.listGui) {
            setVisible(b, true);
        }
        this.acviveScreen();
    }
    
    public void leaveScreen() {
        for (final Object b : this.listGui) {
            setVisible(b, false);
        }
    }
    
    public void keyTypedScreen(final char c, final int code) {
        this.keyTyped(c, code);
    }
    
    public void mouseClickedScreen(final int mouseX, final int mouseY, final int mouseButton) {
        try {
            this.mouseClicked(mouseX, mouseY, mouseButton);
        }
        catch (Exception e) {
            if (mouseButton == 0) {
                for (int l = 0; l < this.buttonList.size(); ++l) {
                    final GuiButton guibutton = this.buttonList.get(l);
                    if (guibutton.mousePressed(this.mc, mouseX, mouseY)) {
                        guibutton.playPressSound(this.mc.getSoundHandler());
                        this.actionPerformed(guibutton);
                    }
                }
            }
        }
    }
    
    public void drawGuiContainerForegroundLayerScreen(final int param1, final int param2) {
        this.drawGuiContainerForegroundLayer(param1, param2);
    }
    
    protected void actionPerformedScreen(final GuiButton btn) {
        this.actionPerformed(btn);
    }
    
    public void switchScreen(final SCREEN_ID id) {
        this.screen_switcher.switchScreen(id);
    }
    
    public static int getScoreboradWidth(final Minecraft mc) {
        final ScaledResolution scaledresolution = new W_ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
        final int ScaledWidth = scaledresolution.getScaledWidth() - 40;
        int width = ScaledWidth * 3 / 4 / (mc.theWorld.getScoreboard().getTeams().size() + 1);
        if (width > 150) {
            width = 150;
        }
        return width;
    }
    
    public static int getScoreBoardLeft(final Minecraft mc, final int teamNum, final int teamIndex) {
        final ScaledResolution scaledresolution = new W_ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
        final int ScaledWidth = scaledresolution.getScaledWidth();
        return (int)(ScaledWidth / 2 + (getScoreboradWidth(mc) + 10) * (-teamNum / 2.0 + teamIndex));
    }
    
    public static void drawList(final Minecraft mc, final FontRenderer fontRendererObj, final boolean mng) {
        final ArrayList<ScorePlayerTeam> teamList = new ArrayList<ScorePlayerTeam>();
        teamList.add(null);
        for (final Object team : mc.theWorld.getScoreboard().getTeams()) {
            teamList.add((ScorePlayerTeam)team);
        }
        Collections.sort(teamList, new Comparator<ScorePlayerTeam>() {
            @Override
            public int compare(final ScorePlayerTeam o1, final ScorePlayerTeam o2) {
                if (o1 == null && o2 == null) {
                    return 0;
                }
                if (o1 == null) {
                    return -1;
                }
                if (o2 == null) {
                    return 1;
                }
                return o1.getRegisteredName().compareTo(o2.getRegisteredName());
            }
        });
        for (int i = 0; i < teamList.size(); ++i) {
            if (mng) {
                drawPlayersList(mc, fontRendererObj, teamList.get(i), 1 + i, 1 + teamList.size());
            }
            else {
                drawPlayersList(mc, fontRendererObj, teamList.get(i), i, teamList.size());
            }
        }
    }
    
    public static void drawPlayersList(final Minecraft mc, final FontRenderer fontRendererObj, final ScorePlayerTeam team, final int teamIndex, final int teamNum) {
        final ScaledResolution scaledresolution = new W_ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
        final int ScaledWidth = scaledresolution.getScaledWidth();
        final int ScaledHeight = scaledresolution.getScaledHeight();
        final ScoreObjective scoreobjective = mc.theWorld.getScoreboard().getObjectiveInDisplaySlot(0);
        final NetHandlerPlayClient nethandlerplayclient = mc.thePlayer.sendQueue;
        final List list = nethandlerplayclient.playerInfoList;
        int MaxPlayers = (list.size() / 5 + 1) * 5;
        MaxPlayers = ((MaxPlayers < 10) ? 10 : MaxPlayers);
        if (MaxPlayers > nethandlerplayclient.currentServerMaxPlayers) {
            MaxPlayers = nethandlerplayclient.currentServerMaxPlayers;
        }
        final int width = getScoreboradWidth(mc);
        final int listLeft = getScoreBoardLeft(mc, teamNum, teamIndex);
        final int listTop = ScaledHeight / 2 - (MaxPlayers * 9 + 10) / 2;
        drawRect(listLeft - 1, listTop - 1 - 18, listLeft + width, listTop + 9 * MaxPlayers, Integer.MIN_VALUE);
        final String teamName = ScorePlayerTeam.formatPlayerName((Team)team, (team == null) ? "No team" : team.getRegisteredName());
        final int teamNameX = listLeft + width / 2 - fontRendererObj.getStringWidth(teamName) / 2;
        fontRendererObj.drawStringWithShadow(teamName, teamNameX, listTop - 18, -1);
        final String ff_onoff = "FriendlyFire : " + ((team == null) ? "ON" : (team.getAllowFriendlyFire() ? "ON" : "OFF"));
        final int ff_onoffX = listLeft + width / 2 - fontRendererObj.getStringWidth(ff_onoff) / 2;
        fontRendererObj.drawStringWithShadow(ff_onoff, ff_onoffX, listTop - 9, -1);
        int drawY = 0;
        for (int i = 0; i < MaxPlayers; ++i) {
            final int x = listLeft;
            final int y = listTop + drawY * 9;
            final int rectY = listTop + i * 9;
            drawRect(x, rectY, x + width - 1, rectY + 8, 553648127);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glEnable(3008);
            if (i < list.size()) {
                final GuiPlayerInfo guiplayerinfo = list.get(i);
                final String playerName = guiplayerinfo.name;
                final ScorePlayerTeam steam = mc.theWorld.getScoreboard().getPlayersTeam(playerName);
                if (steam != null || team != null) {
                    if (steam == null || team == null) {
                        continue;
                    }
                    if (!steam.isSameTeam((Team)team)) {
                        continue;
                    }
                }
                ++drawY;
                fontRendererObj.drawStringWithShadow(playerName, x, y, -1);
                if (scoreobjective != null) {
                    final int j4 = x + fontRendererObj.getStringWidth(playerName) + 5;
                    final int k4 = x + width - 12 - 5;
                    if (k4 - j4 > 5) {
                        final Score score = scoreobjective.getScoreboard().getValueFromObjective(guiplayerinfo.name, scoreobjective);
                        final String s1 = EnumChatFormatting.YELLOW + "" + score.getScorePoints();
                        fontRendererObj.drawStringWithShadow(s1, k4 - fontRendererObj.getStringWidth(s1), y, 16777215);
                    }
                }
                drawResponseTime(x + width - 12, y, guiplayerinfo.responseTime);
            }
        }
    }
    
    public static void drawResponseTime(final int x, final int y, final int responseTime) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        Minecraft.getMinecraft().getTextureManager().bindTexture(MCH_GuiScoreboard_Base.icons);
        byte b2;
        if (responseTime < 0) {
            b2 = 5;
        }
        else if (responseTime < 150) {
            b2 = 0;
        }
        else if (responseTime < 300) {
            b2 = 1;
        }
        else if (responseTime < 600) {
            b2 = 2;
        }
        else if (responseTime < 1000) {
            b2 = 3;
        }
        else {
            b2 = 4;
        }
        static_drawTexturedModalRect(x, y, 0, 176 + b2 * 8, 10, 8, 0.0);
    }
    
    public static void static_drawTexturedModalRect(final int x, final int y, final int x2, final int y2, final int x3, final int y3, final double zLevel) {
        final float f = 0.00390625f;
        final Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double)(x + 0), (double)(y + y3), zLevel, (double)((x2 + 0) * 0.00390625f), (double)((y2 + y3) * 0.00390625f));
        tessellator.addVertexWithUV((double)(x + x3), (double)(y + y3), zLevel, (double)((x2 + x3) * 0.00390625f), (double)((y2 + y3) * 0.00390625f));
        tessellator.addVertexWithUV((double)(x + x3), (double)(y + 0), zLevel, (double)((x2 + x3) * 0.00390625f), (double)((y2 + 0) * 0.00390625f));
        tessellator.addVertexWithUV((double)(x + 0), (double)(y + 0), zLevel, (double)((x2 + 0) * 0.00390625f), (double)((y2 + 0) * 0.00390625f));
        tessellator.draw();
    }
    
    protected enum SCREEN_ID
    {
        MAIN, 
        CREATE_TEAM;
    }
}
