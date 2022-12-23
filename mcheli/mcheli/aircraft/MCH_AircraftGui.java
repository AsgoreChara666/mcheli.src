//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.aircraft;

import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import mcheli.command.*;
import mcheli.*;
import mcheli.weapon.*;
import java.util.*;
import mcheli.multiplay.*;
import mcheli.wrapper.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.gui.*;

public class MCH_AircraftGui extends W_GuiContainer
{
    private final EntityPlayer thePlayer;
    private final MCH_EntityAircraft aircraft;
    private int scaleFactor;
    private GuiButton buttonReload;
    private GuiButton buttonNext;
    private GuiButton buttonPrev;
    private GuiButton buttonInventory;
    private int currentWeaponId;
    private int reloadWait;
    private GuiTextField editCommand;
    public static final int BUTTON_RELOAD = 1;
    public static final int BUTTON_NEXT = 2;
    public static final int BUTTON_PREV = 3;
    public static final int BUTTON_CLOSE = 4;
    public static final int BUTTON_CONFIG = 5;
    public static final int BUTTON_INVENTORY = 6;
    
    public MCH_AircraftGui(final EntityPlayer player, final MCH_EntityAircraft ac) {
        super(new MCH_AircraftGuiContainer(player, ac));
        this.aircraft = ac;
        this.thePlayer = player;
        this.xSize = 210;
        this.ySize = 236;
        this.buttonReload = null;
        this.currentWeaponId = 0;
    }
    
    public void initGui() {
        super.initGui();
        this.buttonList.clear();
        this.buttonReload = new GuiButton(1, this.guiLeft + 85, this.guiTop + 40, 50, 20, "Reload");
        this.buttonNext = new GuiButton(3, this.guiLeft + 140, this.guiTop + 40, 20, 20, "<<");
        this.buttonPrev = new GuiButton(2, this.guiLeft + 160, this.guiTop + 40, 20, 20, ">>");
        this.buttonReload.enabled = this.canReload(this.thePlayer);
        this.buttonNext.enabled = (this.aircraft.getWeaponNum() >= 2);
        this.buttonPrev.enabled = (this.aircraft.getWeaponNum() >= 2);
        this.buttonInventory = new GuiButton(6, this.guiLeft + 210 - 30 - 60, this.guiTop + 90, 80, 20, "Inventory");
        this.buttonList.add(new GuiButton(5, this.guiLeft + 210 - 30 - 60, this.guiTop + 110, 80, 20, "MOD Options"));
        this.buttonList.add(new GuiButton(4, this.guiLeft + 210 - 30 - 20, this.guiTop + 10, 40, 20, "Close"));
        this.buttonList.add(this.buttonReload);
        this.buttonList.add(this.buttonNext);
        this.buttonList.add(this.buttonPrev);
        if (this.aircraft != null && this.aircraft.getSizeInventory() > 0) {
            this.buttonList.add(this.buttonInventory);
        }
        (this.editCommand = new GuiTextField(this.fontRendererObj, this.guiLeft + 25, this.guiTop + 215, 160, 15)).setText(this.aircraft.getCommand());
        this.editCommand.setMaxStringLength(512);
        this.currentWeaponId = 0;
        this.reloadWait = 10;
    }
    
    public void closeScreen() {
        MCH_PacketCommandSave.send(this.editCommand.getText());
        this.mc.thePlayer.closeScreen();
    }
    
    public boolean canReload(final EntityPlayer player) {
        return this.aircraft.canPlayerSupplyAmmo(player, this.currentWeaponId);
    }
    
    public void updateScreen() {
        super.updateScreen();
        if (this.reloadWait > 0) {
            --this.reloadWait;
            if (this.reloadWait == 0) {
                this.buttonReload.enabled = this.canReload(this.thePlayer);
                this.reloadWait = 20;
            }
        }
        this.editCommand.updateCursorCounter();
    }
    
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        this.editCommand.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    public void onGuiClosed() {
        super.onGuiClosed();
    }
    
    protected void actionPerformed(final GuiButton button) {
        super.actionPerformed(button);
        if (!button.enabled) {
            return;
        }
        switch (button.id) {
            case 4: {
                this.closeScreen();
                break;
            }
            case 1: {
                this.buttonReload.enabled = this.canReload(this.thePlayer);
                if (this.buttonReload.enabled) {
                    MCH_PacketIndReload.send(this.aircraft, this.currentWeaponId);
                    this.aircraft.supplyAmmo(this.currentWeaponId);
                    this.reloadWait = 3;
                    this.buttonReload.enabled = false;
                    break;
                }
                break;
            }
            case 2: {
                ++this.currentWeaponId;
                if (this.currentWeaponId >= this.aircraft.getWeaponNum()) {
                    this.currentWeaponId = 0;
                }
                this.buttonReload.enabled = this.canReload(this.thePlayer);
                break;
            }
            case 3: {
                --this.currentWeaponId;
                if (this.currentWeaponId < 0) {
                    this.currentWeaponId = this.aircraft.getWeaponNum() - 1;
                }
                this.buttonReload.enabled = this.canReload(this.thePlayer);
                break;
            }
            case 5: {
                MCH_PacketIndOpenScreen.send(2);
                break;
            }
            case 6: {
                MCH_PacketIndOpenScreen.send(3);
                break;
            }
        }
    }
    
    protected void drawGuiContainerForegroundLayer(final int par1, final int par2) {
        super.drawGuiContainerForegroundLayer(par1, par2);
        final MCH_EntityAircraft ac = this.aircraft;
        this.drawString(ac.getGuiInventory().getInventoryName(), 10, 10, 16777215);
        if (this.aircraft.getNumEjectionSeat() > 0) {
            this.drawString("Parachute", 9, 95, 16777215);
        }
        if (this.aircraft.getWeaponNum() > 0) {
            final MCH_WeaponSet ws = this.aircraft.getWeapon(this.currentWeaponId);
            if (ws != null && !(ws.getFirstWeapon() instanceof MCH_WeaponDummy)) {
                this.drawString(ws.getName(), 79, 30, 16777215);
                final int rest = ws.getRestAllAmmoNum() + ws.getAmmoNum();
                final int color = (rest == 0) ? 16711680 : ((rest == ws.getAllAmmoNum()) ? 2675784 : 16777215);
                final String s = String.format("%4d/%4d", rest, ws.getAllAmmoNum());
                this.drawString(s, 145, 70, color);
                int itemPosX = 90;
                for (final MCH_WeaponInfo.RoundItem r : ws.getInfo().roundItems) {
                    this.drawString("" + r.num, itemPosX, 80, 16777215);
                    itemPosX += 20;
                }
                itemPosX = 85;
                for (final MCH_WeaponInfo.RoundItem r : ws.getInfo().roundItems) {
                    this.drawItemStack(r.itemStack, itemPosX, 62);
                    itemPosX += 20;
                }
            }
        }
        else {
            this.drawString("None", 79, 45, 16777215);
        }
    }
    
    protected void keyTyped(final char c, final int code) {
        if (code == 1) {
            this.closeScreen();
        }
        else if (code == 28) {
            String s = this.editCommand.getText().trim();
            if (s.startsWith("/")) {
                s = s.substring(1);
            }
            if (!s.isEmpty()) {
                MCH_PacketIndMultiplayCommand.send(768, s);
            }
        }
        else {
            this.editCommand.textboxKeyTyped(c, code);
        }
    }
    
    protected void drawGuiContainerBackgroundLayer(final float var1, final int var2, final int var3) {
        final ScaledResolution scaledresolution = new W_ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        this.scaleFactor = scaledresolution.getScaleFactor();
        W_McClient.MOD_bindTexture("textures/gui/gui.png");
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        final int x = (this.width - this.xSize) / 2;
        final int y = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
        for (int i = 0; i < this.aircraft.getNumEjectionSeat(); ++i) {
            this.drawTexturedModalRect(x + 10 + 18 * i - 1, y + 105 - 1, 215, 55, 18, 18);
        }
        int ff = (int)(this.aircraft.getFuelP() * 50.0f);
        if (ff >= 99) {
            ff = 100;
        }
        this.drawTexturedModalRect(x + 57, y + 30 + 50 - ff, 215, 0, 12, ff);
        ff = (int)(this.aircraft.getFuelP() * 100.0f + 0.5);
        final int color = (ff > 20) ? -14101432 : 16711680;
        this.drawString(String.format("%3d", ff) + "%", x + 30, y + 65, color);
        this.editCommand.drawTextBox();
    }
}
