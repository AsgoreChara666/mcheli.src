//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.lweapon;

import net.minecraft.entity.player.*;
import com.google.common.io.*;
import net.minecraft.potion.*;
import mcheli.*;
import net.minecraft.util.*;
import mcheli.wrapper.*;
import net.minecraft.item.*;
import mcheli.weapon.*;
import net.minecraft.entity.*;

public class MCH_LightWeaponPacketHandler
{
    public static void onPacket_PlayerControl(final EntityPlayer player, final ByteArrayDataInput data) {
        if (player.worldObj.isRemote) {
            return;
        }
        final MCH_PacketLightWeaponPlayerControl pc = new MCH_PacketLightWeaponPlayerControl();
        pc.readData(data);
        if (pc.camMode == 1) {
            player.removePotionEffect(Potion.nightVision.getId());
        }
        final ItemStack is = player.getHeldItem();
        if (is == null) {
            return;
        }
        if (!(is.getItem() instanceof MCH_ItemLightWeaponBase)) {
            return;
        }
        final MCH_ItemLightWeaponBase lweapon = (MCH_ItemLightWeaponBase)is.getItem();
        if (pc.camMode == 2 && MCH_ItemLightWeaponBase.isHeld(player)) {
            player.addPotionEffect(new PotionEffect(Potion.nightVision.getId(), 255, 0, false));
        }
        if (pc.camMode > 0) {
            MCH_Lib.DbgLog(false, "MCH_LightWeaponPacketHandler NV=%s", (pc.camMode == 2) ? "ON" : "OFF");
        }
        if (pc.useWeapon && is.getMetadata() < is.getMaxDurability()) {
            final String name = MCH_ItemLightWeaponBase.getName(player.getHeldItem());
            final MCH_WeaponBase w = MCH_WeaponCreator.createWeapon(player.worldObj, name, Vec3.createVectorHelper(0.0, 0.0, 0.0), 0.0f, 0.0f, null, false);
            final MCH_WeaponParam prm = new MCH_WeaponParam();
            prm.entity = (Entity)player;
            prm.user = (Entity)player;
            prm.setPosAndRot(pc.useWeaponPosX, pc.useWeaponPosY, pc.useWeaponPosZ, player.rotationYaw, player.rotationPitch);
            prm.option1 = pc.useWeaponOption1;
            prm.option2 = pc.useWeaponOption2;
            w.shot(prm);
            if (!player.capabilities.isCreativeMode && is.getMaxDurability() == 1) {
                final ItemStack itemStack = is;
                --itemStack.stackSize;
            }
            if (is.getMaxDurability() > 1) {
                is.setMetadata(is.getMaxDurability());
            }
        }
        else if (pc.cmpReload > 0 && is.getMetadata() > 1 && W_EntityPlayer.hasItem(player, (Item)lweapon.bullet)) {
            if (!player.capabilities.isCreativeMode) {
                W_EntityPlayer.consumeInventoryItem(player, (Item)lweapon.bullet);
            }
            is.setMetadata(0);
        }
    }
}
