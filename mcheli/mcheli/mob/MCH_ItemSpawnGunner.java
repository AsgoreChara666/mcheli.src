//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.mob;

import cpw.mods.fml.relauncher.*;
import net.minecraft.creativetab.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import mcheli.aircraft.*;
import mcheli.wrapper.*;
import net.minecraft.util.*;
import net.minecraft.scoreboard.*;
import java.util.*;
import net.minecraft.client.renderer.texture.*;

public class MCH_ItemSpawnGunner extends W_Item
{
    public int primaryColor;
    public int secondaryColor;
    public int targetType;
    @SideOnly(Side.CLIENT)
    private IIcon theIcon;
    
    public MCH_ItemSpawnGunner() {
        this.primaryColor = 16777215;
        this.secondaryColor = 16777215;
        this.targetType = 0;
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.tabTransport);
    }
    
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer player) {
        final float f = 1.0f;
        final float pitch = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * f;
        final float yaw = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * f;
        final double dx = player.prevPosX + (player.posX - player.prevPosX) * f;
        final double dy = player.prevPosY + (player.posY - player.prevPosY) * f + 1.62 - player.yOffset;
        final double dz = player.prevPosZ + (player.posZ - player.prevPosZ) * f;
        final Vec3 vec3 = Vec3.createVectorHelper(dx, dy, dz);
        final float f2 = MathHelper.cos(-yaw * 0.017453292f - 3.1415927f);
        final float f3 = MathHelper.sin(-yaw * 0.017453292f - 3.1415927f);
        final float f4 = -MathHelper.cos(-pitch * 0.017453292f);
        final float f5 = MathHelper.sin(-pitch * 0.017453292f);
        final float f6 = f3 * f4;
        final float f7 = f2 * f4;
        final double d3 = 5.0;
        final Vec3 vec4 = vec3.addVector(f6 * d3, f5 * d3, f7 * d3);
        List list = world.getEntitiesWithinAABB((Class)MCH_EntityGunner.class, player.boundingBox.expand(5.0, 5.0, 5.0));
        Entity target = null;
        for (int i = 0; i < list.size(); ++i) {
            final MCH_EntityGunner gunner = list.get(i);
            if (gunner.boundingBox.calculateIntercept(vec3, vec4) != null && (target == null || player.getDistanceSqToEntity((Entity)gunner) < player.getDistanceSqToEntity(target))) {
                target = (Entity)gunner;
            }
        }
        if (target == null) {
            list = world.getEntitiesWithinAABB((Class)MCH_EntitySeat.class, player.boundingBox.expand(5.0, 5.0, 5.0));
            for (int i = 0; i < list.size(); ++i) {
                final MCH_EntitySeat seat = list.get(i);
                if (seat.getParent() != null && seat.getParent().getAcInfo() != null && seat.boundingBox.calculateIntercept(vec3, vec4) != null && (target == null || player.getDistanceSqToEntity((Entity)seat) < player.getDistanceSqToEntity(target))) {
                    if (seat.riddenByEntity instanceof MCH_EntityGunner) {
                        target = seat.riddenByEntity;
                    }
                    else {
                        target = (Entity)seat;
                    }
                }
            }
        }
        if (target == null) {
            list = world.getEntitiesWithinAABB((Class)MCH_EntityAircraft.class, player.boundingBox.expand(5.0, 5.0, 5.0));
            for (int i = 0; i < list.size(); ++i) {
                final MCH_EntityAircraft ac = list.get(i);
                if (!ac.isUAV() && ac.getAcInfo() != null && ac.boundingBox.calculateIntercept(vec3, vec4) != null && (target == null || player.getDistanceSqToEntity((Entity)ac) < player.getDistanceSqToEntity(target))) {
                    if (ac.getRiddenByEntity() instanceof MCH_EntityGunner) {
                        target = ac.getRiddenByEntity();
                    }
                    else {
                        target = (Entity)ac;
                    }
                }
            }
        }
        if (target instanceof MCH_EntityGunner) {
            target.interactFirst(player);
            return itemStack;
        }
        if (this.targetType == 1 && !world.isRemote && player.getTeam() == null) {
            player.addChatMessage((IChatComponent)new ChatComponentText("You are not on team."));
            return itemStack;
        }
        if (target == null) {
            if (!world.isRemote) {
                player.addChatMessage((IChatComponent)new ChatComponentText("Right click to seat."));
            }
            return itemStack;
        }
        if (!world.isRemote) {
            final MCH_EntityGunner gunner2 = new MCH_EntityGunner(world, target.posX, target.posY, target.posZ);
            gunner2.rotationYaw = (float)(((MathHelper.floor_double(player.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3) - 1) * 90);
            gunner2.isCreative = player.capabilities.isCreativeMode;
            gunner2.targetType = this.targetType;
            gunner2.ownerUUID = player.getUniqueID().toString();
            final ScorePlayerTeam team = world.getScoreboard().getPlayersTeam(player.getDisplayName());
            if (team != null) {
                gunner2.setTeamName(team.getRegisteredName());
            }
            world.spawnEntityInWorld((Entity)gunner2);
            gunner2.mountEntity(target);
            W_WorldFunc.MOD_playSoundAtEntity((Entity)gunner2, "wrench", 1.0f, 3.0f);
            final MCH_EntityAircraft ac2 = (target instanceof MCH_EntityAircraft) ? target : ((MCH_EntitySeat)target).getParent();
            player.addChatMessage((IChatComponent)new ChatComponentText("The gunner was put on " + EnumChatFormatting.GOLD + ac2.getAcInfo().displayName + EnumChatFormatting.RESET + " seat " + (ac2.getSeatIdByEntity((Entity)gunner2) + 1) + " by " + ScorePlayerTeam.formatPlayerName(player.getTeam(), player.getDisplayName())));
        }
        if (!player.capabilities.isCreativeMode) {
            --itemStack.stackSize;
        }
        return itemStack;
    }
    
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(final ItemStack itemStack, final int layer) {
        return (layer == 0) ? this.primaryColor : this.secondaryColor;
    }
    
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(final int p_77618_1_, final int p_77618_2_) {
        return (p_77618_2_ > 0) ? this.theIcon : super.getIconFromDamageForRenderPass(p_77618_1_, p_77618_2_);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister icon) {
        super.registerIcons(icon);
        this.theIcon = icon.registerIcon(this.getIconString() + "_overlay");
    }
}
