//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.command;

import mcheli.gui.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import net.minecraft.client.gui.*;
import java.util.*;

@SideOnly(Side.CLIENT)
public class MCH_GuiTitle extends MCH_Gui
{
    private final List chatLines;
    private int prevPlayerTick;
    private int restShowTick;
    private int showTick;
    private float colorAlpha;
    private int position;
    private static Minecraft s_minecraft;
    
    public MCH_GuiTitle(final Minecraft minecraft) {
        super(minecraft);
        this.chatLines = new ArrayList();
        this.prevPlayerTick = 0;
        this.restShowTick = 0;
        this.showTick = 0;
        this.colorAlpha = 0.0f;
        this.position = 0;
        MCH_GuiTitle.s_minecraft = minecraft;
    }
    
    @Override
    public void initGui() {
        super.initGui();
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    @Override
    public boolean isDrawGui(final EntityPlayer player) {
        if (this.restShowTick > 0 && this.chatLines.size() > 0 && player != null && player.worldObj != null) {
            if (this.prevPlayerTick != player.ticksExisted) {
                ++this.showTick;
                --this.restShowTick;
            }
            this.prevPlayerTick = player.ticksExisted;
        }
        return this.restShowTick > 0;
    }
    
    @Override
    public void drawGui(final EntityPlayer player, final boolean isThirdPersonView) {
        GL11.glLineWidth((float)(MCH_GuiTitle.scaleFactor * 2));
        GL11.glDisable(3042);
        if (MCH_GuiTitle.scaleFactor <= 0) {
            MCH_GuiTitle.scaleFactor = 1;
        }
        this.colorAlpha = 1.0f;
        if (this.restShowTick > 20 && this.showTick < 5) {
            this.colorAlpha = 0.2f * this.showTick;
        }
        if (this.showTick > 0 && this.restShowTick < 5) {
            this.colorAlpha = 0.2f * this.restShowTick;
        }
        this.drawChat();
    }
    
    private String formatColors(final String s) {
        return Minecraft.getMinecraft().gameSettings.chatColours ? s : EnumChatFormatting.getTextWithoutFormattingCodes(s);
    }
    
    private int calculateChatboxWidth() {
        final short short1 = 320;
        final byte b0 = 40;
        return MathHelper.floor_float(this.mc.gameSettings.chatWidth * (short1 - b0) + b0);
    }
    
    public void setupTitle(final IChatComponent chatComponent, final int showTime, final int pos) {
        final int displayTime = 20;
        final int line = 0;
        this.chatLines.clear();
        this.position = pos;
        this.showTick = 0;
        this.restShowTick = showTime;
        final int k = MathHelper.floor_float(this.calculateChatboxWidth() / this.mc.gameSettings.chatScale);
        int l = 0;
        ChatComponentText chatcomponenttext = new ChatComponentText("");
        final ArrayList arraylist = Lists.newArrayList();
        final ArrayList arraylist2 = Lists.newArrayList((Iterable)chatComponent);
        for (int i1 = 0; i1 < arraylist2.size(); ++i1) {
            final IChatComponent ichatcomponent1 = arraylist2.get(i1);
            final String[] splitLine = (ichatcomponent1.getUnformattedTextForChat() + "").split("\n");
            int lineCnt = 0;
            for (final String sLine : splitLine) {
                final String s = this.formatColors(ichatcomponent1.getChatStyle().getFormattingCode() + sLine);
                int j1 = this.mc.fontRendererObj.getStringWidth(s);
                ChatComponentText chatcomponenttext2 = new ChatComponentText(s);
                chatcomponenttext2.setChatStyle(ichatcomponent1.getChatStyle().createShallowCopy());
                boolean flag1 = false;
                if (l + j1 > k) {
                    String s2 = this.mc.fontRendererObj.trimStringToWidth(s, k - l, false);
                    String s3 = (s2.length() < s.length()) ? s.substring(s2.length()) : null;
                    if (s3 != null && s3.length() > 0) {
                        final int k2 = s2.lastIndexOf(" ");
                        if (k2 >= 0 && this.mc.fontRendererObj.getStringWidth(s.substring(0, k2)) > 0) {
                            s2 = s.substring(0, k2);
                            s3 = s.substring(k2);
                        }
                        final ChatComponentText chatcomponenttext3 = new ChatComponentText(s3);
                        chatcomponenttext3.setChatStyle(ichatcomponent1.getChatStyle().createShallowCopy());
                        arraylist2.add(i1 + 1, chatcomponenttext3);
                    }
                    j1 = this.mc.fontRendererObj.getStringWidth(s2);
                    chatcomponenttext2 = new ChatComponentText(s2);
                    chatcomponenttext2.setChatStyle(ichatcomponent1.getChatStyle().createShallowCopy());
                    flag1 = true;
                }
                if (l + j1 <= k) {
                    l += j1;
                    chatcomponenttext.appendSibling((IChatComponent)chatcomponenttext2);
                }
                else {
                    flag1 = true;
                }
                if (flag1) {
                    arraylist.add(chatcomponenttext);
                    l = 0;
                    chatcomponenttext = new ChatComponentText("");
                }
                if (++lineCnt < splitLine.length) {
                    arraylist.add(chatcomponenttext);
                    l = 0;
                    chatcomponenttext = new ChatComponentText("");
                }
            }
        }
        arraylist.add(chatcomponenttext);
        for (final IChatComponent ichatcomponent2 : arraylist) {
            this.chatLines.add(new ChatLine(displayTime, ichatcomponent2, line));
        }
        while (this.chatLines.size() > 100) {
            this.chatLines.remove(this.chatLines.size() - 1);
        }
    }
    
    private int calculateChatboxHeight() {
        final short short1 = 180;
        final byte b0 = 20;
        return MathHelper.floor_float(this.mc.gameSettings.chatHeightFocused * (short1 - b0) + b0);
    }
    
    private void drawChat() {
        final float charAlpha = this.mc.gameSettings.chatOpacity * 0.9f + 0.1f;
        final float scale = this.mc.gameSettings.chatScale * 2.0f;
        GL11.glPushMatrix();
        float posY = 0.0f;
        switch (this.position) {
            default: {
                posY = this.mc.displayHeight / 2 / MCH_GuiTitle.scaleFactor - this.chatLines.size() / 2.0f * 9.0f * scale;
                break;
            }
            case 1: {
                posY = 0.0f;
                break;
            }
            case 2: {
                posY = this.mc.displayHeight / MCH_GuiTitle.scaleFactor - this.chatLines.size() * 9.0f * scale;
                break;
            }
            case 3: {
                posY = this.mc.displayHeight / 3 / MCH_GuiTitle.scaleFactor - this.chatLines.size() / 2.0f * 9.0f * scale;
                break;
            }
            case 4: {
                posY = this.mc.displayHeight * 2 / 3 / MCH_GuiTitle.scaleFactor - this.chatLines.size() / 2.0f * 9.0f * scale;
                break;
            }
        }
        GL11.glTranslatef(0.0f, posY, 0.0f);
        GL11.glScalef(scale, scale, 1.0f);
        for (int i = 0; i < this.chatLines.size(); ++i) {
            final ChatLine chatline = this.chatLines.get(i);
            if (chatline != null) {
                final int alpha = (int)(255.0f * charAlpha * this.colorAlpha);
                final int y = i * 9;
                drawRect(0, y + 9, this.mc.displayWidth, y, alpha / 2 << 24);
                GL11.glEnable(3042);
                final String s = chatline.getChatComponent().getFormattedText();
                int sw = this.mc.displayWidth / 2 / MCH_GuiTitle.scaleFactor - this.mc.fontRendererObj.getStringWidth(s);
                sw /= (int)scale;
                this.mc.fontRendererObj.drawStringWithShadow(s, sw, y + 1, 16777215 + (alpha << 24));
                GL11.glDisable(3008);
            }
        }
        GL11.glTranslatef(-3.0f, 0.0f, 0.0f);
        GL11.glPopMatrix();
    }
}
