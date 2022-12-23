//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.uav;

import net.minecraft.client.gui.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import mcheli.plane.*;
import mcheli.helicopter.*;
import mcheli.tank.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import mcheli.aircraft.*;
import org.lwjgl.opengl.*;
import mcheli.wrapper.*;

public class MCH_GuiUavStation extends W_GuiContainer
{
    final MCH_EntityUavStation uavStation;
    static final int BX = 20;
    static final int BY = 22;
    private final int BUTTON_ID_CONTINUE = 256;
    private GuiButton buttonContinue;
    
    public MCH_GuiUavStation(final InventoryPlayer inventoryPlayer, final MCH_EntityUavStation uavStation) {
        super((Container)new MCH_ContainerUavStation(inventoryPlayer, uavStation));
        this.uavStation = uavStation;
    }
    
    protected void drawGuiContainerForegroundLayer(final int param1, final int param2) {
        if (this.uavStation == null) {
            return;
        }
        final ItemStack item = this.uavStation.getStackInSlot(0);
        MCH_AircraftInfo info = null;
        if (item != null && item.getItem() instanceof MCP_ItemPlane) {
            info = (MCH_AircraftInfo)MCP_PlaneInfoManager.getFromItem(item.getItem());
        }
        if (item != null && item.getItem() instanceof MCH_ItemHeli) {
            info = (MCH_AircraftInfo)MCH_HeliInfoManager.getFromItem(item.getItem());
        }
        if (item != null && item.getItem() instanceof MCH_ItemTank) {
            info = (MCH_AircraftInfo)MCH_TankInfoManager.getFromItem(item.getItem());
        }
        if (item == null || (item != null && info != null && info.isUAV)) {
            if (this.uavStation.getKind() <= 1) {
                this.drawString("UAV Station", 8, 6, 16777215);
            }
            else if (item == null || info.isSmallUAV) {
                this.drawString("UAV Controller", 8, 6, 16777215);
            }
            else {
                this.drawString("Small UAV only", 8, 6, 16711680);
            }
        }
        else if (item != null) {
            this.drawString("Not UAV", 8, 6, 16711680);
        }
        this.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 16777215);
        this.drawString(String.format("X.%+2d", this.uavStation.posUavX), 58, 15, 16777215);
        this.drawString(String.format("Y.%+2d", this.uavStation.posUavY), 58, 37, 16777215);
        this.drawString(String.format("Z.%+2d", this.uavStation.posUavZ), 58, 59, 16777215);
    }
    
    protected void drawGuiContainerBackgroundLayer(final float par1, final int par2, final int par3) {
        W_McClient.MOD_bindTexture("textures/gui/uav_station.png");
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        final int x = (this.width - this.xSize) / 2;
        final int y = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
    }
    
    protected void actionPerformed(final GuiButton btn) {
        if (btn != null && btn.enabled) {
            if (btn.id == 256) {
                if (this.uavStation != null && !this.uavStation.isDead && this.uavStation.getLastControlAircraft() != null && !this.uavStation.getLastControlAircraft().isDead) {
                    final MCH_UavPacketStatus data = new MCH_UavPacketStatus();
                    data.posUavX = (byte)this.uavStation.posUavX;
                    data.posUavY = (byte)this.uavStation.posUavY;
                    data.posUavZ = (byte)this.uavStation.posUavZ;
                    data.continueControl = true;
                    W_Network.sendToServer((W_PacketBase)data);
                }
                this.buttonContinue.enabled = false;
            }
            else {
                final int[] pos = { this.uavStation.posUavX, this.uavStation.posUavY, this.uavStation.posUavZ };
                final int i = btn.id >> 4 & 0xF;
                final int j = (btn.id & 0xF) - 1;
                final int[] BTN = { -10, -1, 1, 10 };
                final int[] array = pos;
                final int n = i;
                array[n] += BTN[j];
                if (pos[i] < -50) {
                    pos[i] = -50;
                }
                if (pos[i] > 50) {
                    pos[i] = 50;
                }
                if (this.uavStation.posUavX != pos[0] || this.uavStation.posUavY != pos[1] || this.uavStation.posUavZ != pos[2]) {
                    final MCH_UavPacketStatus data2 = new MCH_UavPacketStatus();
                    data2.posUavX = (byte)pos[0];
                    data2.posUavY = (byte)pos[1];
                    data2.posUavZ = (byte)pos[2];
                    W_Network.sendToServer((W_PacketBase)data2);
                }
            }
        }
    }
    
    public void initGui() {
        super.initGui();
        this.buttonList.clear();
        final int x = this.width / 2 - 5;
        final int y = this.height / 2 - 76;
        final String[] BTN = { "-10", "-1", "+1", "+10" };
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 4; ++col) {
                final int id = row << 4 | col + 1;
                this.buttonList.add(new GuiButton(id, x + col * 20, y + row * 22, 20, 20, BTN[col]));
            }
        }
        this.buttonContinue = new GuiButton(256, x - 80 + 3, y + 44, 50, 20, "Continue");
        this.buttonContinue.enabled = false;
        if (this.uavStation != null && !this.uavStation.isDead && this.uavStation.getAndSearchLastControlAircraft() != null) {
            this.buttonContinue.enabled = true;
        }
        this.buttonList.add(this.buttonContinue);
    }
}
