//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli;

import net.minecraft.client.renderer.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.client.*;
import mcheli.aircraft.*;
import mcheli.uav.*;
import mcheli.gltd.*;
import mcheli.wrapper.*;

@SideOnly(Side.CLIENT)
public class MCH_ItemRendererDummy extends ItemRenderer
{
    protected static Minecraft mc;
    protected static ItemRenderer backupItemRenderer;
    protected static MCH_ItemRendererDummy instance;
    
    public MCH_ItemRendererDummy(final Minecraft par1Minecraft) {
        super(par1Minecraft);
        MCH_ItemRendererDummy.mc = par1Minecraft;
    }
    
    public void renderItemInFirstPerson(final float par1) {
        if (MCH_ItemRendererDummy.mc.thePlayer == null) {
            super.renderItemInFirstPerson(par1);
        }
        else if (!(MCH_ItemRendererDummy.mc.thePlayer.ridingEntity instanceof MCH_EntityAircraft) && !(MCH_ItemRendererDummy.mc.thePlayer.ridingEntity instanceof MCH_EntityUavStation)) {
            if (!(MCH_ItemRendererDummy.mc.thePlayer.ridingEntity instanceof MCH_EntityGLTD)) {
                super.renderItemInFirstPerson(par1);
            }
        }
    }
    
    public static void enableDummyItemRenderer() {
        if (MCH_ItemRendererDummy.instance == null) {
            MCH_ItemRendererDummy.instance = new MCH_ItemRendererDummy(Minecraft.getMinecraft());
        }
        if (!(MCH_ItemRendererDummy.mc.entityRenderer.itemRenderer instanceof MCH_ItemRendererDummy)) {
            MCH_ItemRendererDummy.backupItemRenderer = MCH_ItemRendererDummy.mc.entityRenderer.itemRenderer;
        }
        W_EntityRenderer.setItemRenderer(MCH_ItemRendererDummy.mc, MCH_ItemRendererDummy.instance);
    }
    
    public static void disableDummyItemRenderer() {
        if (MCH_ItemRendererDummy.backupItemRenderer != null) {
            W_EntityRenderer.setItemRenderer(MCH_ItemRendererDummy.mc, MCH_ItemRendererDummy.backupItemRenderer);
        }
    }
}
