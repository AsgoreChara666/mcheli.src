//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.multiplay;

import mcheli.plane.*;
import mcheli.helicopter.*;
import mcheli.vehicle.*;
import mcheli.tank.*;
import net.minecraft.entity.*;
import mcheli.aircraft.*;
import mcheli.*;
import net.minecraft.command.*;
import net.minecraft.server.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.command.server.*;
import net.minecraft.scoreboard.*;
import java.util.*;
import net.minecraft.server.management.*;

public class MCH_Multiplay
{
    public static final MCH_TargetType[][] ENTITY_SPOT_TABLE;
    
    public static boolean canSpotEntityWithFilter(final int filter, final Entity entity) {
        if (entity instanceof MCP_EntityPlane) {
            return (filter & 0x20) != 0x0;
        }
        if (entity instanceof MCH_EntityHeli) {
            return (filter & 0x10) != 0x0;
        }
        if (entity instanceof MCH_EntityVehicle || entity instanceof MCH_EntityTank) {
            return (filter & 0x8) != 0x0;
        }
        if (entity instanceof EntityPlayer) {
            return (filter & 0x4) != 0x0;
        }
        if (!(entity instanceof EntityLivingBase)) {
            return false;
        }
        if (isMonster(entity)) {
            return (filter & 0x2) != 0x0;
        }
        return (filter & 0x1) != 0x0;
    }
    
    public static boolean isMonster(final Entity entity) {
        return entity.getClass().toString().toLowerCase().indexOf("monster") >= 0;
    }
    
    public static MCH_TargetType canSpotEntity(final Entity user, final double posX, final double posY, final double posZ, final Entity target, final boolean checkSee) {
        if (!(user instanceof EntityLivingBase)) {
            return MCH_TargetType.NONE;
        }
        final EntityLivingBase spotter = (EntityLivingBase)user;
        final int col = (spotter.getTeam() != null) ? 1 : 0;
        int row = 0;
        if (target instanceof EntityLivingBase) {
            if (!isMonster(target)) {
                row = 1;
            }
            else {
                row = 2;
            }
        }
        if (spotter.getTeam() != null) {
            if (target instanceof EntityPlayer) {
                final EntityPlayer player = (EntityPlayer)target;
                if (player.getTeam() == null) {
                    row = 3;
                }
                else if (spotter.isOnSameTeam((EntityLivingBase)player)) {
                    row = 4;
                }
                else {
                    row = 5;
                }
            }
            else if (target instanceof MCH_EntityAircraft) {
                final MCH_EntityAircraft ac = (MCH_EntityAircraft)target;
                final EntityPlayer rideEntity = ac.getFirstMountPlayer();
                if (rideEntity == null) {
                    row = 6;
                }
                else if (rideEntity.getTeam() == null) {
                    row = 7;
                }
                else if (spotter.isOnSameTeam((EntityLivingBase)rideEntity)) {
                    row = 8;
                }
                else {
                    row = 9;
                }
            }
        }
        else if (target instanceof EntityPlayer || target instanceof MCH_EntityAircraft) {
            row = 0;
        }
        MCH_TargetType ret = MCH_Multiplay.ENTITY_SPOT_TABLE[row][col];
        if (checkSee && ret != MCH_TargetType.NONE) {
            final Vec3 vs = Vec3.createVectorHelper(posX, posY, posZ);
            final Vec3 ve = Vec3.createVectorHelper(target.posX, target.posY + target.getEyeHeight(), target.posZ);
            final MovingObjectPosition mop = target.worldObj.rayTraceBlocks(vs, ve);
            if (mop != null && mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                ret = MCH_TargetType.NONE;
            }
        }
        return ret;
    }
    
    public static boolean canAttackEntity(final DamageSource ds, final Entity target) {
        return canAttackEntity(ds.getEntity(), target);
    }
    
    public static boolean canAttackEntity(final Entity attacker, final Entity target) {
        if (attacker != null && target != null) {
            EntityPlayer attackPlayer = null;
            EntityPlayer targetPlayer = null;
            if (attacker instanceof EntityPlayer) {
                attackPlayer = (EntityPlayer)attacker;
            }
            if (target instanceof EntityPlayer) {
                targetPlayer = (EntityPlayer)target;
            }
            else if (target.riddenByEntity instanceof EntityPlayer) {
                targetPlayer = (EntityPlayer)target.riddenByEntity;
            }
            if (target instanceof MCH_EntityAircraft) {
                final MCH_EntityAircraft ac = (MCH_EntityAircraft)target;
                if (ac.getRiddenByEntity() instanceof EntityPlayer) {
                    targetPlayer = (EntityPlayer)ac.getRiddenByEntity();
                }
            }
            if (attackPlayer != null && targetPlayer != null && !attackPlayer.canAttackPlayer(targetPlayer)) {
                return false;
            }
        }
        return true;
    }
    
    public static void jumpSpawnPoint(final EntityPlayer player) {
        MCH_Lib.DbgLog(false, "JumpSpawnPoint", new Object[0]);
        final CommandTeleport cmd = new CommandTeleport();
        if (cmd.canCommandSenderUseCommand((ICommandSender)player)) {
            final MinecraftServer minecraftServer = MinecraftServer.getServer();
            for (final String playerName : minecraftServer.getConfigurationManager().getAllUsernames()) {
                final EntityPlayerMP jumpPlayer = CommandTeleport.getPlayer((ICommandSender)player, playerName);
                ChunkCoordinates cc = null;
                if (jumpPlayer != null && jumpPlayer.dimension == player.dimension) {
                    cc = jumpPlayer.getBedLocation(jumpPlayer.dimension);
                    if (cc != null) {
                        cc = EntityPlayer.verifyRespawnCoordinates((World)minecraftServer.worldServerForDimension(jumpPlayer.dimension), cc, true);
                    }
                    if (cc == null) {
                        cc = jumpPlayer.worldObj.provider.getRandomizedSpawnPoint();
                    }
                }
                if (cc != null) {
                    final String[] cmdStr = { playerName, String.format("%.1f", cc.posX + 0.5), String.format("%.1f", cc.posY + 0.1), String.format("%.1f", cc.posZ + 0.5) };
                    cmd.processCommand((ICommandSender)player, cmdStr);
                }
            }
        }
    }
    
    public static void shuffleTeam(final EntityPlayer player) {
        final Collection teams = player.worldObj.getScoreboard().getTeams();
        final int teamNum = teams.size();
        MCH_Lib.DbgLog(false, "ShuffleTeam:%d teams ----------", new Object[] { teamNum });
        if (teamNum > 0) {
            final CommandScoreboard cmd = new CommandScoreboard();
            if (cmd.canCommandSenderUseCommand((ICommandSender)player)) {
                final List<String> list = Arrays.asList(MinecraftServer.getServer().getConfigurationManager().getAllUsernames());
                Collections.shuffle(list);
                final ArrayList<String> listTeam = new ArrayList<String>();
                for (final Object o : teams) {
                    final ScorePlayerTeam team = (ScorePlayerTeam)o;
                    listTeam.add(team.getRegisteredName());
                }
                Collections.shuffle(listTeam);
                int i = 0;
                int j = 0;
                while (i < list.size()) {
                    listTeam.set(j, listTeam.get(j) + " " + list.get(i));
                    if (++j >= teamNum) {
                        j = 0;
                    }
                    ++i;
                }
                for (int k = 0; k < listTeam.size(); ++k) {
                    final String exe_cmd = "teams join " + listTeam.get(k);
                    final String[] process_cmd = exe_cmd.split(" ");
                    if (process_cmd.length > 3) {
                        MCH_Lib.DbgLog(false, "ShuffleTeam:" + exe_cmd, new Object[0]);
                        cmd.processCommand((ICommandSender)player, process_cmd);
                    }
                }
            }
        }
    }
    
    public static boolean spotEntity(final EntityLivingBase player, final MCH_EntityAircraft ac, final double posX, final double posY, final double posZ, final int targetFilter, final float spotLength, final int markTime, final float angle) {
        boolean ret = false;
        if (!player.worldObj.isRemote) {
            float acYaw = 0.0f;
            float acPitch = 0.0f;
            float acRoll = 0.0f;
            if (ac != null) {
                acYaw = ac.getRotYaw();
                acPitch = ac.getRotPitch();
                acRoll = ac.getRotRoll();
            }
            final Vec3 vv = MCH_Lib.RotVec3(0.0, 0.0, 1.0, -player.rotationYaw, -player.rotationPitch, -acRoll);
            final double tx = vv.xCoord;
            final double tz = vv.zCoord;
            final List list = player.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)player, player.boundingBox.expand((double)spotLength, (double)spotLength, (double)spotLength));
            final List<Integer> entityList = new ArrayList<Integer>();
            final Vec3 pos = Vec3.createVectorHelper(posX, posY, posZ);
            for (int i = 0; i < list.size(); ++i) {
                final Entity entity = list.get(i);
                if (canSpotEntityWithFilter(targetFilter, entity)) {
                    final MCH_TargetType stopType = canSpotEntity((Entity)player, posX, posY, posZ, entity, true);
                    if (stopType != MCH_TargetType.NONE && stopType != MCH_TargetType.SAME_TEAM_PLAYER) {
                        final double dist = entity.getDistanceSq(pos.xCoord, pos.yCoord, pos.zCoord);
                        if (dist > 1.0 && dist < spotLength * spotLength) {
                            final double cx = entity.posX - pos.xCoord;
                            final double cy = entity.posY - pos.yCoord;
                            final double cz = entity.posZ - pos.zCoord;
                            final double h = MCH_Lib.getPosAngle(tx, tz, cx, cz);
                            double v = Math.atan2(cy, Math.sqrt(cx * cx + cz * cz)) * 180.0 / 3.141592653589793;
                            v = Math.abs(v + player.rotationPitch);
                            if (h < angle * 2.0f && v < angle * 2.0f) {
                                entityList.add(entity.getEntityId());
                            }
                        }
                    }
                }
            }
            if (entityList.size() > 0) {
                final int[] entityId = new int[entityList.size()];
                for (int j = 0; j < entityId.length; ++j) {
                    entityId[j] = entityList.get(j);
                }
                sendSpotedEntityListToSameTeam(player, markTime, entityId);
                ret = true;
            }
            else {
                ret = false;
            }
        }
        return ret;
    }
    
    public static void sendSpotedEntityListToSameTeam(final EntityLivingBase player, final int count, final int[] entityId) {
        final ServerConfigurationManager svCnf = MinecraftServer.getServer().getConfigurationManager();
        for (final EntityPlayer notifyPlayer : svCnf.playerEntityList) {
            if (player == notifyPlayer || player.isOnSameTeam((EntityLivingBase)notifyPlayer)) {
                MCH_PacketNotifySpotedEntity.send(notifyPlayer, count, entityId);
            }
        }
    }
    
    public static boolean markPoint(final EntityPlayer player, final double posX, final double posY, final double posZ) {
        final Vec3 vs = Vec3.createVectorHelper(posX, posY, posZ);
        Vec3 ve = MCH_Lib.Rot2Vec3(player.rotationYaw, player.rotationPitch);
        ve = vs.addVector(ve.xCoord * 300.0, ve.yCoord * 300.0, ve.zCoord * 300.0);
        final MovingObjectPosition mop = player.worldObj.rayTraceBlocks(vs, ve, true);
        if (mop != null && mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            sendMarkPointToSameTeam(player, mop.blockX, mop.blockY + 2, mop.blockZ);
            return true;
        }
        sendMarkPointToSameTeam(player, 0, 1000, 0);
        return false;
    }
    
    public static void sendMarkPointToSameTeam(final EntityPlayer player, final int x, final int y, final int z) {
        final ServerConfigurationManager svCnf = MinecraftServer.getServer().getConfigurationManager();
        for (final EntityPlayer notifyPlayer : svCnf.playerEntityList) {
            if (player == notifyPlayer || player.isOnSameTeam((EntityLivingBase)notifyPlayer)) {
                MCH_PacketNotifyMarkPoint.send(notifyPlayer, x, y, z);
            }
        }
    }
    
    static {
        ENTITY_SPOT_TABLE = new MCH_TargetType[][] { { MCH_TargetType.NONE, MCH_TargetType.NONE }, { MCH_TargetType.OTHER_MOB, MCH_TargetType.OTHER_MOB }, { MCH_TargetType.MONSTER, MCH_TargetType.MONSTER }, { MCH_TargetType.NONE, MCH_TargetType.NO_TEAM_PLAYER }, { MCH_TargetType.NONE, MCH_TargetType.SAME_TEAM_PLAYER }, { MCH_TargetType.NONE, MCH_TargetType.OTHER_TEAM_PLAYER }, { MCH_TargetType.NONE, MCH_TargetType.NONE }, { MCH_TargetType.NONE, MCH_TargetType.NO_TEAM_PLAYER }, { MCH_TargetType.NONE, MCH_TargetType.SAME_TEAM_PLAYER }, { MCH_TargetType.NONE, MCH_TargetType.OTHER_TEAM_PLAYER } };
    }
}
