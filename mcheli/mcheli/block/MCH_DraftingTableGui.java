//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.block;

import net.minecraft.entity.player.*;
import mcheli.gui.*;
import mcheli.helicopter.*;
import mcheli.plane.*;
import mcheli.vehicle.*;
import mcheli.tank.*;
import mcheli.*;
import java.util.*;
import net.minecraft.client.*;
import org.lwjgl.opengl.*;
import org.lwjgl.input.*;
import net.minecraft.inventory.*;
import net.minecraft.item.crafting.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import mcheli.aircraft.*;
import mcheli.wrapper.modelloader.*;
import mcheli.wrapper.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;

public class MCH_DraftingTableGui extends W_GuiContainer
{
    private final EntityPlayer thePlayer;
    private int scaleFactor;
    private MCH_GuiSliderVertical listSlider;
    private GuiButton buttonCreate;
    private GuiButton buttonNext;
    private GuiButton buttonPrev;
    private GuiButton buttonNextPage;
    private GuiButton buttonPrevPage;
    private int drawFace;
    private int buttonClickWait;
    public static final int RECIPE_HELI = 0;
    public static final int RECIPE_PLANE = 1;
    public static final int RECIPE_VEHICLE = 2;
    public static final int RECIPE_TANK = 3;
    public static final int RECIPE_ITEM = 4;
    public MCH_IRecipeList currentList;
    public MCH_CurrentRecipe current;
    public static final int BUTTON_HELI = 10;
    public static final int BUTTON_PLANE = 11;
    public static final int BUTTON_VEHICLE = 12;
    public static final int BUTTON_TANK = 13;
    public static final int BUTTON_ITEM = 14;
    public static final int BUTTON_NEXT = 20;
    public static final int BUTTON_PREV = 21;
    public static final int BUTTON_CREATE = 30;
    public static final int BUTTON_SELECT = 40;
    public static final int BUTTON_NEXT_PAGE = 50;
    public static final int BUTTON_PREV_PAGE = 51;
    public List<List<GuiButton>> screenButtonList;
    public int screenId;
    public static final int SCREEN_MAIN = 0;
    public static final int SCREEN_LIST = 1;
    public static float modelZoom;
    public static float modelRotX;
    public static float modelRotY;
    public static float modelPosX;
    public static float modelPosY;
    
    public MCH_DraftingTableGui(final EntityPlayer player, final int posX, final int posY, final int posZ) {
        super(new MCH_DraftingTableGuiContainer(player, posX, posY, posZ));
        this.screenId = 0;
        this.thePlayer = player;
        this.xSize = 400;
        this.ySize = 240;
        this.screenButtonList = new ArrayList<List<GuiButton>>();
        this.drawFace = 0;
        this.buttonClickWait = 0;
        MCH_Lib.DbgLog(player.worldObj, "MCH_DraftingTableGui.MCH_DraftingTableGui", new Object[0]);
    }
    
    public void initGui() {
        super.initGui();
        this.buttonList.clear();
        this.screenButtonList.clear();
        this.screenButtonList.add(new ArrayList<GuiButton>());
        this.screenButtonList.add(new ArrayList<GuiButton>());
        List<GuiButton> list = null;
        list = this.screenButtonList.get(0);
        final GuiButton btnHeli = new GuiButton(10, this.guiLeft + 20, this.guiTop + 20, 90, 20, "Helicopter List");
        final GuiButton btnPlane = new GuiButton(11, this.guiLeft + 20, this.guiTop + 40, 90, 20, "Plane List");
        final GuiButton btnVehicle = new GuiButton(12, this.guiLeft + 20, this.guiTop + 60, 90, 20, "Vehicle List");
        final GuiButton btnTank = new GuiButton(13, this.guiLeft + 20, this.guiTop + 80, 90, 20, "Tank List");
        final GuiButton btnItem = new GuiButton(14, this.guiLeft + 20, this.guiTop + 100, 90, 20, "Item List");
        btnHeli.enabled = (MCH_HeliInfoManager.getInstance().getRecipeListSize() > 0);
        btnPlane.enabled = (MCP_PlaneInfoManager.getInstance().getRecipeListSize() > 0);
        btnVehicle.enabled = (MCH_VehicleInfoManager.getInstance().getRecipeListSize() > 0);
        btnTank.enabled = (MCH_TankInfoManager.getInstance().getRecipeListSize() > 0);
        btnItem.enabled = (MCH_ItemRecipe.getInstance().getRecipeListSize() > 0);
        list.add(btnHeli);
        list.add(btnPlane);
        list.add(btnVehicle);
        list.add(btnTank);
        list.add(btnItem);
        this.buttonCreate = new GuiButton(30, this.guiLeft + 120, this.guiTop + 89, 50, 20, "Create");
        this.buttonPrev = new GuiButton(21, this.guiLeft + 120, this.guiTop + 111, 36, 20, "<<");
        this.buttonNext = new GuiButton(20, this.guiLeft + 155, this.guiTop + 111, 35, 20, ">>");
        list.add(this.buttonCreate);
        list.add(this.buttonPrev);
        list.add(this.buttonNext);
        this.buttonPrevPage = new GuiButton(51, this.guiLeft + 210, this.guiTop + 210, 60, 20, "Prev Page");
        this.buttonNextPage = new GuiButton(50, this.guiLeft + 270, this.guiTop + 210, 60, 20, "Next Page");
        list.add(this.buttonPrevPage);
        list.add(this.buttonNextPage);
        list = this.screenButtonList.get(1);
        int y = 0;
        int i = 0;
        while (y < 3) {
            for (int x = 0; x < 2; ++x, ++i) {
                final int px = this.guiLeft + 30 + x * 140;
                final int py = this.guiTop + 40 + y * 70;
                list.add(new GuiButton(40 + i, px, py, 45, 20, "Select"));
            }
            ++y;
        }
        list.add(this.listSlider = new MCH_GuiSliderVertical(0, this.guiLeft + 360, this.guiTop + 20, 20, 200, "", 0.0f, 0.0f, 0.0f, 1.0f));
        for (int j = 0; j < this.screenButtonList.size(); ++j) {
            list = this.screenButtonList.get(j);
            for (int k = 0; k < list.size(); ++k) {
                this.buttonList.add(list.get(k));
            }
        }
        this.switchScreen(0);
        initModelTransform();
        MCH_DraftingTableGui.modelRotX = 180.0f;
        MCH_DraftingTableGui.modelRotY = 90.0f;
        if (MCH_ItemRecipe.getInstance().getRecipeListSize() > 0) {
            this.switchRecipeList(MCH_ItemRecipe.getInstance());
        }
        else if (MCH_HeliInfoManager.getInstance().getRecipeListSize() > 0) {
            this.switchRecipeList((MCH_IRecipeList)MCH_HeliInfoManager.getInstance());
        }
        else if (MCP_PlaneInfoManager.getInstance().getRecipeListSize() > 0) {
            this.switchRecipeList((MCH_IRecipeList)MCP_PlaneInfoManager.getInstance());
        }
        else if (MCH_VehicleInfoManager.getInstance().getRecipeListSize() > 0) {
            this.switchRecipeList((MCH_IRecipeList)MCH_VehicleInfoManager.getInstance());
        }
        else if (MCH_TankInfoManager.getInstance().getRecipeListSize() > 0) {
            this.switchRecipeList((MCH_IRecipeList)MCH_TankInfoManager.getInstance());
        }
        else {
            this.switchRecipeList(MCH_ItemRecipe.getInstance());
        }
    }
    
    public static void initModelTransform() {
        MCH_DraftingTableGui.modelRotX = 0.0f;
        MCH_DraftingTableGui.modelRotY = 0.0f;
        MCH_DraftingTableGui.modelPosX = 0.0f;
        MCH_DraftingTableGui.modelPosY = 0.0f;
        MCH_DraftingTableGui.modelZoom = 1.0f;
    }
    
    public void updateListSliderSize(final int listSize) {
        int s = listSize / 2;
        if (listSize % 2 != 0) {
            ++s;
        }
        if (s > 3) {
            this.listSlider.valueMax = (float)(s - 3);
        }
        else {
            this.listSlider.valueMax = 0.0f;
        }
        this.listSlider.setSliderValue(0.0f);
    }
    
    public void switchScreen(final int id) {
        this.screenId = id;
        for (int i = 0; i < this.buttonList.size(); ++i) {
            W_GuiButton.setVisible(this.buttonList.get(i), false);
        }
        if (id < this.screenButtonList.size()) {
            final List<GuiButton> list = this.screenButtonList.get(id);
            for (final GuiButton b : list) {
                W_GuiButton.setVisible(b, true);
            }
        }
        if (this.getScreenId() == 0 && this.current != null && this.current.getDescMaxPage() > 1) {
            W_GuiButton.setVisible(this.buttonNextPage, true);
            W_GuiButton.setVisible(this.buttonPrevPage, true);
        }
        else {
            W_GuiButton.setVisible(this.buttonNextPage, false);
            W_GuiButton.setVisible(this.buttonPrevPage, false);
        }
    }
    
    public void setCurrentRecipe(final MCH_CurrentRecipe currentRecipe) {
        MCH_DraftingTableGui.modelPosX = 0.0f;
        MCH_DraftingTableGui.modelPosY = 0.0f;
        if (this.current == null || currentRecipe == null || !this.current.recipe.getRecipeOutput().isItemEqual(currentRecipe.recipe.getRecipeOutput())) {
            this.drawFace = 0;
        }
        this.current = currentRecipe;
        if (this.getScreenId() == 0 && this.current != null && this.current.getDescMaxPage() > 1) {
            W_GuiButton.setVisible(this.buttonNextPage, true);
            W_GuiButton.setVisible(this.buttonPrevPage, true);
        }
        else {
            W_GuiButton.setVisible(this.buttonNextPage, false);
            W_GuiButton.setVisible(this.buttonPrevPage, false);
        }
    }
    
    public MCH_IRecipeList getCurrentList() {
        return this.currentList;
    }
    
    public void switchRecipeList(final MCH_IRecipeList list) {
        if (this.getCurrentList() != list) {
            this.setCurrentRecipe(new MCH_CurrentRecipe(list, 0));
            this.currentList = list;
            this.updateListSliderSize(list.getRecipeListSize());
        }
        else {
            this.listSlider.setSliderValue((float)(this.current.index / 2));
        }
    }
    
    public void updateScreen() {
        super.updateScreen();
        final MCH_DraftingTableGuiContainer container = (MCH_DraftingTableGuiContainer)this.inventorySlots;
        this.buttonCreate.enabled = false;
        if (!container.getSlot(container.outputSlotIndex).getHasStack() && MCH_Lib.canPlayerCreateItem(this.current.recipe, this.thePlayer.inventory)) {
            this.buttonCreate.enabled = true;
        }
        if (this.thePlayer.capabilities.isCreativeMode) {
            this.buttonCreate.enabled = true;
        }
        if (this.buttonClickWait > 0) {
            --this.buttonClickWait;
        }
    }
    
    public void onGuiClosed() {
        super.onGuiClosed();
        MCH_Lib.DbgLog(this.thePlayer.worldObj, "MCH_DraftingTableGui.onGuiClosed", new Object[0]);
    }
    
    protected void actionPerformed(final GuiButton button) {
        super.actionPerformed(button);
        if (this.buttonClickWait > 0) {
            return;
        }
        if (!button.enabled) {
            return;
        }
        this.buttonClickWait = 3;
        int index = 0;
        int page = this.current.getDescCurrentPage();
        switch (button.id) {
            case 30: {
                MCH_DraftingTableCreatePacket.send(this.current.recipe);
                break;
            }
            case 21: {
                if (this.current.isCurrentPageTexture()) {
                    page = 0;
                }
                index = this.current.index - 1;
                if (index < 0) {
                    index = this.getCurrentList().getRecipeListSize() - 1;
                }
                this.setCurrentRecipe(new MCH_CurrentRecipe(this.getCurrentList(), index));
                this.current.setDescCurrentPage(page);
                break;
            }
            case 20: {
                if (this.current.isCurrentPageTexture()) {
                    page = 0;
                }
                index = (this.current.index + 1) % this.getCurrentList().getRecipeListSize();
                this.setCurrentRecipe(new MCH_CurrentRecipe(this.getCurrentList(), index));
                this.current.setDescCurrentPage(page);
                break;
            }
            case 10: {
                initModelTransform();
                MCH_DraftingTableGui.modelRotX = 180.0f;
                MCH_DraftingTableGui.modelRotY = 90.0f;
                this.switchRecipeList((MCH_IRecipeList)MCH_HeliInfoManager.getInstance());
                this.switchScreen(1);
                break;
            }
            case 11: {
                initModelTransform();
                MCH_DraftingTableGui.modelRotX = 90.0f;
                MCH_DraftingTableGui.modelRotY = 180.0f;
                this.switchRecipeList((MCH_IRecipeList)MCP_PlaneInfoManager.getInstance());
                this.switchScreen(1);
                break;
            }
            case 13: {
                initModelTransform();
                MCH_DraftingTableGui.modelRotX = 180.0f;
                MCH_DraftingTableGui.modelRotY = 90.0f;
                this.switchRecipeList((MCH_IRecipeList)MCH_TankInfoManager.getInstance());
                this.switchScreen(1);
                break;
            }
            case 12: {
                initModelTransform();
                MCH_DraftingTableGui.modelRotX = 180.0f;
                MCH_DraftingTableGui.modelRotY = 90.0f;
                this.switchRecipeList((MCH_IRecipeList)MCH_VehicleInfoManager.getInstance());
                this.switchScreen(1);
                break;
            }
            case 14: {
                this.switchRecipeList(MCH_ItemRecipe.getInstance());
                this.switchScreen(1);
                break;
            }
            case 50: {
                if (this.current != null) {
                    this.current.switchNextPage();
                    break;
                }
                break;
            }
            case 51: {
                if (this.current != null) {
                    this.current.switchPrevPage();
                    break;
                }
                break;
            }
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45: {
                index = (int)this.listSlider.getSliderValue() * 2 + (button.id - 40);
                if (index < this.getCurrentList().getRecipeListSize()) {
                    this.setCurrentRecipe(new MCH_CurrentRecipe(this.getCurrentList(), index));
                    this.switchScreen(0);
                    break;
                }
                break;
            }
        }
    }
    
    protected void keyTyped(final char par1, final int keycode) {
        if (keycode == 1 || keycode == W_KeyBinding.getKeyCode(Minecraft.getMinecraft().gameSettings.keyBindInventory)) {
            if (this.getScreenId() == 0) {
                this.mc.thePlayer.closeScreen();
            }
            else {
                this.switchScreen(0);
            }
        }
        if (this.getScreenId() == 0) {
            if (keycode == 205) {
                this.actionPerformed(this.buttonNext);
            }
            if (keycode == 203) {
                this.actionPerformed(this.buttonPrev);
            }
        }
        else if (this.getScreenId() == 1) {
            if (keycode == 200) {
                this.listSlider.scrollDown(1.0f);
            }
            if (keycode == 208) {
                this.listSlider.scrollUp(1.0f);
            }
        }
    }
    
    protected void drawGuiContainerForegroundLayer(final int mx, final int my) {
        super.drawGuiContainerForegroundLayer(mx, my);
        final float z = this.zLevel;
        this.zLevel = 0.0f;
        GL11.glEnable(3042);
        if (this.getScreenId() == 0) {
            final ArrayList<String> list = new ArrayList<String>();
            if (this.current != null) {
                if (this.current.isCurrentPageTexture()) {
                    GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
                    this.mc.getTextureManager().bindTexture(this.current.getCurrentPageTexture());
                    this.drawTexturedModalRect(210, 20, 170, 190, 0, 0, 340, 380);
                }
                else if (this.current.isCurrentPageAcInfo()) {
                    final int COLOR = -9491968;
                    for (int i = 0; i < this.current.infoItem.size(); ++i) {
                        this.fontRendererObj.drawString((String)this.current.infoItem.get(i), 210, 40 + 10 * i, -9491968);
                        final String data = this.current.infoData.get(i);
                        if (!data.isEmpty()) {
                            this.fontRendererObj.drawString(data, 280, 40 + 10 * i, -9491968);
                        }
                    }
                }
                else {
                    W_McClient.MOD_bindTexture("textures/gui/drafting_table.png");
                    this.drawTexturedModalRect(340, 215, 45, 15, 400, 60, 90, 30);
                    if (mx >= 350 && mx <= 400 && my >= 214 && my <= 230) {
                        final boolean lb = Mouse.isButtonDown(0);
                        final boolean rb = Mouse.isButtonDown(1);
                        final boolean mb = Mouse.isButtonDown(2);
                        list.add((lb ? EnumChatFormatting.AQUA : "") + "Mouse left button drag : Rotation model");
                        list.add((rb ? EnumChatFormatting.AQUA : "") + "Mouse right button drag : Zoom model");
                        list.add((mb ? EnumChatFormatting.AQUA : "") + "Mouse middle button drag : Move model");
                    }
                }
            }
            this.drawString(this.current.displayName, 120, 20, -1);
            this.drawItemRecipe(this.current.recipe, 121, 34);
            if (list.size() > 0) {
                this.drawHoveringText((List)list, mx - 30, my - 0, this.fontRendererObj);
            }
        }
        if (this.getScreenId() == 1) {
            final int index = 2 * (int)this.listSlider.getSliderValue();
            int j = 0;
            for (int r = 0; r < 3; ++r) {
                for (int c = 0; c < 2; ++c) {
                    if (index + j < this.getCurrentList().getRecipeListSize()) {
                        final int rx = 110 + 140 * c;
                        final int ry = 20 + 70 * r;
                        final String s = this.getCurrentList().getRecipe(index + j).getRecipeOutput().getDisplayName();
                        this.drawCenteredString(s, rx, ry, -1);
                    }
                    ++j;
                }
            }
            W_McClient.MOD_bindTexture("textures/gui/drafting_table.png");
            j = 0;
            for (int r = 0; r < 3; ++r) {
                for (int c = 0; c < 2; ++c) {
                    if (index + j < this.getCurrentList().getRecipeListSize()) {
                        final int rx = 80 + 140 * c - 1;
                        final int ry = 30 + 70 * r - 1;
                        this.drawTexturedModalRect(rx, ry, 400, 0, 75, 54);
                    }
                    ++j;
                }
            }
            j = 0;
            for (int r = 0; r < 3; ++r) {
                for (int c = 0; c < 2; ++c) {
                    if (index + j < this.getCurrentList().getRecipeListSize()) {
                        final int rx = 80 + 140 * c;
                        final int ry = 30 + 70 * r;
                        this.drawItemRecipe(this.getCurrentList().getRecipe(index + j), rx, ry);
                    }
                    ++j;
                }
            }
        }
    }
    
    protected void handleMouseClick(final Slot slotIn, final int slotId, final int clickedButton, final int clickType) {
        if (this.getScreenId() != 1) {
            super.handleMouseClick(slotIn, slotId, clickedButton, clickType);
        }
    }
    
    private int getScreenId() {
        return this.screenId;
    }
    
    public void drawItemRecipe(final IRecipe recipe, final int x, final int y) {
        if (recipe == null) {
            return;
        }
        if (recipe.getRecipeOutput() == null) {
            return;
        }
        if (recipe.getRecipeOutput().getItem() == null) {
            return;
        }
        if (recipe instanceof ShapedRecipes) {
            final ShapedRecipes rcp = (ShapedRecipes)recipe;
            for (int RH = rcp.recipeHeight, h = 0; h < RH; ++h) {
                for (int w = 0; w < rcp.recipeWidth; ++w) {
                    final int IDX = h * RH + w;
                    if (IDX < rcp.recipeItems.length) {
                        this.drawItemStack(rcp.recipeItems[IDX], x + w * 18, y + h * 18);
                    }
                }
            }
        }
        else if (recipe instanceof ShapelessRecipes) {
            final ShapelessRecipes rcp2 = (ShapelessRecipes)recipe;
            for (int i = 0; i < rcp2.recipeItems.size(); ++i) {
                this.drawItemStack(rcp2.recipeItems.get(i), x + i % 3 * 18, y + i / 3 * 18);
            }
        }
        this.drawItemStack(recipe.getRecipeOutput(), x + 54 + 3, y + 18);
    }
    
    public void handleMouseInput() {
        super.handleMouseInput();
        final int dx = Mouse.getEventDX();
        final int dy = Mouse.getEventDY();
        if (this.getScreenId() == 0 && Mouse.getX() > this.mc.displayWidth / 2) {
            if (Mouse.isButtonDown(0) && (dx != 0 || dy != 0)) {
                MCH_DraftingTableGui.modelRotX -= (float)(dy / 2.0);
                MCH_DraftingTableGui.modelRotY -= (float)(dx / 2.0);
                if (MCH_DraftingTableGui.modelRotX > 360.0f) {
                    MCH_DraftingTableGui.modelRotX -= 360.0f;
                }
                if (MCH_DraftingTableGui.modelRotX < -360.0f) {
                    MCH_DraftingTableGui.modelRotX += 360.0f;
                }
                if (MCH_DraftingTableGui.modelRotY > 360.0f) {
                    MCH_DraftingTableGui.modelRotY -= 360.0f;
                }
                if (MCH_DraftingTableGui.modelRotY < -360.0f) {
                    MCH_DraftingTableGui.modelRotY += 360.0f;
                }
            }
            if (Mouse.isButtonDown(2) && (dx != 0 || dy != 0)) {
                MCH_DraftingTableGui.modelPosX += (float)(dx / 2.0);
                MCH_DraftingTableGui.modelPosY -= (float)(dy / 2.0);
                if (MCH_DraftingTableGui.modelRotX > 1000.0f) {
                    MCH_DraftingTableGui.modelRotX = 1000.0f;
                }
                if (MCH_DraftingTableGui.modelRotX < -1000.0f) {
                    MCH_DraftingTableGui.modelRotX = -1000.0f;
                }
                if (MCH_DraftingTableGui.modelRotY > 1000.0f) {
                    MCH_DraftingTableGui.modelRotY = 1000.0f;
                }
                if (MCH_DraftingTableGui.modelRotY < -1000.0f) {
                    MCH_DraftingTableGui.modelRotY = -1000.0f;
                }
            }
            if (Mouse.isButtonDown(1) && dy != 0) {
                MCH_DraftingTableGui.modelZoom += (float)(dy / 100.0);
                if (MCH_DraftingTableGui.modelZoom < 0.1) {
                    MCH_DraftingTableGui.modelZoom = 0.1f;
                }
                if (MCH_DraftingTableGui.modelZoom > 10.0f) {
                    MCH_DraftingTableGui.modelZoom = 10.0f;
                }
            }
        }
        final int wheel = Mouse.getEventDWheel();
        if (wheel != 0) {
            if (this.getScreenId() == 1) {
                if (wheel > 0) {
                    this.listSlider.scrollDown(1.0f);
                }
                else if (wheel < 0) {
                    this.listSlider.scrollUp(1.0f);
                }
            }
            else if (this.getScreenId() == 0) {
                if (wheel > 0) {
                    this.actionPerformed(this.buttonPrev);
                }
                else if (wheel < 0) {
                    this.actionPerformed(this.buttonNext);
                }
            }
        }
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        if (this.getScreenId() == 0) {
            super.drawScreen(mouseX, mouseY, partialTicks);
        }
        else {
            final List inventory = this.inventorySlots.inventorySlots;
            this.inventorySlots.inventorySlots = new ArrayList();
            super.drawScreen(mouseX, mouseY, partialTicks);
            this.inventorySlots.inventorySlots = inventory;
        }
        if (this.getScreenId() == 0 && this.current.isCurrentPageModel()) {
            RenderHelper.enableGUIStandardItemLighting();
            this.drawModel(partialTicks);
        }
    }
    
    public void drawModel(final float partialTicks) {
        final W_ModelCustom model = this.current.getModel();
        final double scl = 162.0 / ((MathHelper.abs(model.size) < 0.01) ? 0.01 : model.size);
        this.mc.getTextureManager().bindTexture(this.current.getModelTexture());
        GL11.glPushMatrix();
        final double cx = (model.maxX - model.minX) * 0.5 + model.minX;
        final double cy = (model.maxY - model.minY) * 0.5 + model.minY;
        final double cz = (model.maxZ - model.minZ) * 0.5 + model.minZ;
        if (this.current.modelRot == 0) {
            GL11.glTranslated(cx * scl, cz * scl, 0.0);
        }
        else {
            GL11.glTranslated(cz * scl, cy * scl, 0.0);
        }
        GL11.glTranslated((double)(this.guiLeft + 300 + MCH_DraftingTableGui.modelPosX), (double)(this.guiTop + 110 + MCH_DraftingTableGui.modelPosY), 550.0);
        GL11.glRotated((double)MCH_DraftingTableGui.modelRotX, 1.0, 0.0, 0.0);
        GL11.glRotated((double)MCH_DraftingTableGui.modelRotY, 0.0, 1.0, 0.0);
        GL11.glScaled(scl * MCH_DraftingTableGui.modelZoom, scl * MCH_DraftingTableGui.modelZoom, -scl * MCH_DraftingTableGui.modelZoom);
        GL11.glDisable(32826);
        GL11.glDisable(2896);
        GL11.glEnable(3008);
        GL11.glEnable(3042);
        final int faceNum = model.getFaceNum();
        if (this.drawFace < faceNum * 2) {
            GL11.glColor4d(0.10000000149011612, 0.10000000149011612, 0.10000000149011612, 1.0);
            GL11.glDisable(3553);
            GL11.glPolygonMode(1032, 6913);
            final float lw = GL11.glGetFloat(2849);
            GL11.glLineWidth(1.0f);
            model.renderAll(this.drawFace - faceNum, this.drawFace);
            MCH_RenderAircraft.renderCrawlerTrack((MCH_EntityAircraft)null, this.current.getAcInfo(), partialTicks);
            GL11.glLineWidth(lw);
            GL11.glPolygonMode(1032, 6914);
            GL11.glEnable(3553);
        }
        if (this.drawFace >= faceNum) {
            GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
            model.renderAll(0, this.drawFace - faceNum);
            MCH_RenderAircraft.renderCrawlerTrack((MCH_EntityAircraft)null, this.current.getAcInfo(), partialTicks);
        }
        GL11.glEnable(32826);
        GL11.glEnable(2896);
        GL11.glPopMatrix();
        if (this.drawFace < 10000000) {
            this.drawFace += (int)20.0f;
        }
    }
    
    protected void drawGuiContainerBackgroundLayer(final float var1, final int var2, final int var3) {
        final ScaledResolution scaledresolution = new W_ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        this.scaleFactor = scaledresolution.getScaleFactor();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        final float z = this.zLevel;
        this.zLevel = 0.0f;
        W_McClient.MOD_bindTexture("textures/gui/drafting_table.png");
        if (this.getScreenId() == 0) {
            this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        }
        if (this.getScreenId() == 1) {
            this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, this.ySize, this.xSize, this.ySize);
            final List<GuiButton> list = this.screenButtonList.get(1);
            final int index = (int)this.listSlider.getSliderValue() * 2;
            for (int i = 0; i < 6; ++i) {
                W_GuiButton.setVisible(list.get(i), index + i < this.getCurrentList().getRecipeListSize());
            }
        }
        this.zLevel = z;
    }
    
    public void drawTexturedModalRect(final int par1, final int par2, final int par3, final int par4, final int par5, final int par6) {
        final float w = 0.001953125f;
        final float h = 0.001953125f;
        final Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double)(par1 + 0), (double)(par2 + par6), (double)this.zLevel, (double)((par3 + 0) * w), (double)((par4 + par6) * h));
        tessellator.addVertexWithUV((double)(par1 + par5), (double)(par2 + par6), (double)this.zLevel, (double)((par3 + par5) * w), (double)((par4 + par6) * h));
        tessellator.addVertexWithUV((double)(par1 + par5), (double)(par2 + 0), (double)this.zLevel, (double)((par3 + par5) * w), (double)((par4 + 0) * h));
        tessellator.addVertexWithUV((double)(par1 + 0), (double)(par2 + 0), (double)this.zLevel, (double)((par3 + 0) * w), (double)((par4 + 0) * h));
        tessellator.draw();
    }
    
    public void drawTexturedModalRect(final int dx, final int dy, final int dw, final int dh, final int u, final int v, final int tw, final int th) {
        final float w = 0.001953125f;
        final float h = 0.001953125f;
        final Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double)(dx + 0), (double)(dy + dh), (double)this.zLevel, (double)((u + 0) * w), (double)((v + th) * h));
        tessellator.addVertexWithUV((double)(dx + dw), (double)(dy + dh), (double)this.zLevel, (double)((u + tw) * w), (double)((v + th) * h));
        tessellator.addVertexWithUV((double)(dx + dw), (double)(dy + 0), (double)this.zLevel, (double)((u + tw) * w), (double)((v + 0) * h));
        tessellator.addVertexWithUV((double)(dx + 0), (double)(dy + 0), (double)this.zLevel, (double)((u + 0) * w), (double)((v + 0) * h));
        tessellator.draw();
    }
    
    static {
        MCH_DraftingTableGui.modelZoom = 1.0f;
        MCH_DraftingTableGui.modelRotX = 0.0f;
        MCH_DraftingTableGui.modelRotY = 0.0f;
        MCH_DraftingTableGui.modelPosX = 0.0f;
        MCH_DraftingTableGui.modelPosY = 0.0f;
    }
}
