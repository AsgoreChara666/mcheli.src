//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.command;

import net.minecraft.entity.*;
import net.minecraftforge.event.*;
import net.minecraft.entity.player.*;
import mcheli.multiplay.*;
import mcheli.*;
import org.apache.commons.lang3.exception.*;
import com.google.gson.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.inventory.*;
import net.minecraft.command.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.*;
import net.minecraft.item.*;
import net.minecraft.server.*;

public class MCH_Command extends CommandBase
{
    public static final String CMD_GET_SS = "sendss";
    public static final String CMD_MOD_LIST = "modlist";
    public static final String CMD_RECONFIG = "reconfig";
    public static final String CMD_TITLE = "title";
    public static final String CMD_FILL = "fill";
    public static final String CMD_STATUS = "status";
    public static final String CMD_KILL_ENTITY = "killentity";
    public static final String CMD_REMOVE_ENTITY = "removeentity";
    public static final String CMD_ATTACK_ENTITY = "attackentity";
    public static final String CMD_SHOW_BB = "showboundingbox";
    public static final String CMD_DELAY_BB = "delayhitbox";
    public static final String CMD_LIST = "list";
    public static String[] ALL_COMMAND;
    public static MCH_Command instance;
    
    public static boolean canUseCommand(final Entity player) {
        return player instanceof EntityPlayer && MCH_Command.instance.canCommandSenderUseCommand((ICommandSender)player);
    }
    
    public String getCommandName() {
        return "mcheli";
    }
    
    public static boolean checkCommandPermission(final ICommandSender sender, final String cmd) {
        if (new CommandGameMode().canCommandSenderUseCommand(sender)) {
            return true;
        }
        if (sender instanceof EntityPlayer && cmd.length() > 0) {
            final String playerName = ((EntityPlayer)sender).getGameProfile().getName();
            final MCH_Config config = MCH_MOD.config;
            for (final MCH_Config.CommandPermission c : MCH_Config.CommandPermissionList) {
                if (c.name.equals(cmd)) {
                    for (final String s : c.players) {
                        if (s.equalsIgnoreCase(playerName)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    public static void onCommandEvent(final CommandEvent event) {
        if (!(event.command instanceof MCH_Command)) {
            return;
        }
        if (event.parameters.length <= 0 || event.parameters[0].length() <= 0) {
            event.setCanceled(true);
            return;
        }
        if (!checkCommandPermission(event.sender, event.parameters[0])) {
            event.setCanceled(true);
            final ChatComponentTranslation c = new ChatComponentTranslation("commands.generic.permission", new Object[0]);
            c.getChatStyle().setColor(EnumChatFormatting.RED);
            event.sender.addChatMessage((IChatComponent)c);
        }
    }
    
    public boolean canCommandSenderUseCommand(final ICommandSender player) {
        return true;
    }
    
    public String getCommandUsage(final ICommandSender sender) {
        return "commands.mcheli.usage";
    }
    
    public void processCommand(final ICommandSender sender, final String[] prm) {
        final MCH_Config config = MCH_MOD.config;
        if (!MCH_Config.EnableCommand.prmBool) {
            return;
        }
        if (!checkCommandPermission(sender, prm[0])) {
            final ChatComponentTranslation c = new ChatComponentTranslation("commands.generic.permission", new Object[0]);
            c.getChatStyle().setColor(EnumChatFormatting.RED);
            sender.addChatMessage((IChatComponent)c);
            return;
        }
        if (prm[0].equalsIgnoreCase("sendss")) {
            if (prm.length != 2) {
                throw new CommandException("Parameter error! : /mcheli sendss playerName", new Object[0]);
            }
            final EntityPlayerMP player = getPlayer(sender, prm[1]);
            if (player != null) {
                MCH_PacketIndClient.send((EntityPlayer)player, 1, prm[1]);
            }
        }
        else if (prm[0].equalsIgnoreCase("modlist")) {
            if (prm.length < 2) {
                throw new CommandException("Parameter error! : /mcheli modlist playerName", new Object[0]);
            }
            final EntityPlayerMP reqPlayer = (sender instanceof EntityPlayerMP) ? sender : null;
            for (int i = 1; i < prm.length; ++i) {
                final EntityPlayerMP player2 = getPlayer(sender, prm[i]);
                if (player2 != null) {
                    MCH_PacketIndClient.send((EntityPlayer)player2, 2, "" + MCH_MultiplayPacketHandler.getPlayerInfoId((EntityPlayer)reqPlayer));
                }
            }
        }
        else if (prm[0].equalsIgnoreCase("reconfig")) {
            if (prm.length != 1) {
                throw new CommandException("Parameter error! : /mcheli reconfig", new Object[0]);
            }
            MCH_MOD.proxy.reconfig();
            if (sender.getEntityWorld() != null && !sender.getEntityWorld().isRemote) {
                MCH_PacketNotifyServerSettings.sendAll();
            }
            if (MCH_MOD.proxy.isSinglePlayer()) {
                sender.addChatMessage((IChatComponent)new ChatComponentText("Reload mcheli.cfg"));
            }
            else {
                sender.addChatMessage((IChatComponent)new ChatComponentText("Reload server side mcheli.cfg"));
            }
        }
        else if (prm[0].equalsIgnoreCase("title")) {
            if (prm.length < 4) {
                throw new WrongUsageException("Parameter error! : /mcheli title time[1~180] position[0~4] messege[JSON format]", new Object[0]);
            }
            final String s = getStringFromNthArg(sender, prm, 3);
            int showTime = Integer.valueOf(prm[1]);
            if (showTime < 1) {
                showTime = 1;
            }
            if (showTime > 180) {
                showTime = 180;
            }
            int pos = Integer.valueOf(prm[2]);
            if (pos < 0) {
                pos = 0;
            }
            if (pos > 5) {
                pos = 5;
            }
            try {
                final IChatComponent ichatcomponent = IChatComponent.Serializer.jsonToComponent(s);
                MCH_PacketTitle.send(ichatcomponent, 20 * showTime, pos);
            }
            catch (JsonParseException jsonparseexception) {
                final Throwable throwable = ExceptionUtils.getRootCause((Throwable)jsonparseexception);
                throw new SyntaxErrorException("mcheli.title.jsonException", new Object[] { (throwable == null) ? "" : throwable.getMessage() });
            }
        }
        else if (prm[0].equalsIgnoreCase("fill")) {
            this.executeFill(sender, prm);
        }
        else if (prm[0].equalsIgnoreCase("status")) {
            this.executeStatus(sender, prm);
        }
        else if (prm[0].equalsIgnoreCase("killentity")) {
            this.executeKillEntity(sender, prm);
        }
        else if (prm[0].equalsIgnoreCase("removeentity")) {
            this.executeRemoveEntity(sender, prm);
        }
        else if (prm[0].equalsIgnoreCase("attackentity")) {
            this.executeAttackEntity(sender, prm);
        }
        else if (prm[0].equalsIgnoreCase("showboundingbox")) {
            if (prm.length != 2) {
                throw new CommandException("Parameter error! : /mcheli showboundingbox true or false", new Object[0]);
            }
            if (!parseBoolean(sender, prm[1])) {
                final MCH_Config config2 = MCH_MOD.config;
                MCH_Config.EnableDebugBoundingBox.prmBool = false;
                MCH_PacketNotifyServerSettings.sendAll();
                sender.addChatMessage((IChatComponent)new ChatComponentText("Disabled bounding box"));
            }
            else {
                final MCH_Config config3 = MCH_MOD.config;
                MCH_Config.EnableDebugBoundingBox.prmBool = true;
                MCH_PacketNotifyServerSettings.sendAll();
                sender.addChatMessage((IChatComponent)new ChatComponentText("Enabled bounding box [F3 + b]"));
            }
            MCH_MOD.proxy.save();
        }
        else if (prm[0].equalsIgnoreCase("list")) {
            String msg = "";
            for (final String s2 : MCH_Command.ALL_COMMAND) {
                msg = msg + s2 + ", ";
            }
            sender.addChatMessage((IChatComponent)new ChatComponentText("/mcheli command list : " + msg));
        }
        else {
            if (!prm[0].equalsIgnoreCase("delayhitbox")) {
                throw new CommandException("Unknown mcheli command. please type /mcheli list", new Object[0]);
            }
            if (prm.length == 1) {
                final StringBuilder append = new StringBuilder().append("Current delay of hitbox = ");
                final MCH_Config config4 = MCH_MOD.config;
                sender.addChatMessage((IChatComponent)new ChatComponentText(append.append(MCH_Config.HitBoxDelayTick.prmInt).append(" [0 ~ 50]").toString()));
            }
            else {
                if (prm.length != 2) {
                    throw new CommandException("Parameter error! : /mcheli delayhitbox 0 ~ 50", new Object[0]);
                }
                final MCH_Config config5 = MCH_MOD.config;
                MCH_Config.HitBoxDelayTick.prmInt = parseInt(sender, prm[1]);
                final MCH_Config config6 = MCH_MOD.config;
                if (MCH_Config.HitBoxDelayTick.prmInt > 50) {
                    final MCH_Config config7 = MCH_MOD.config;
                    MCH_Config.HitBoxDelayTick.prmInt = 50;
                }
                MCH_MOD.proxy.save();
                final StringBuilder append2 = new StringBuilder().append("Current delay of hitbox = ");
                final MCH_Config config8 = MCH_MOD.config;
                sender.addChatMessage((IChatComponent)new ChatComponentText(append2.append(MCH_Config.HitBoxDelayTick.prmInt).append(" [0 ~ 50]").toString()));
            }
        }
    }
    
    private void executeAttackEntity(final ICommandSender sender, final String[] args) {
        if (args.length < 3) {
            throw new WrongUsageException("/mcheli attackentity <entity class name : example1 EntityBat , example2 minecraft.entity.passive> <damage> [damage source]", new Object[0]);
        }
        final String className = args[1].toLowerCase();
        final float damage = Float.valueOf(args[2]);
        final String damageName = (args.length >= 4) ? args[3].toLowerCase() : "";
        DamageSource ds = DamageSource.generic;
        if (!damageName.isEmpty()) {
            if (damageName.equals("player")) {
                if (sender instanceof EntityPlayer) {
                    ds = DamageSource.causePlayerDamage((EntityPlayer)sender);
                }
            }
            else if (damageName.equals("anvil")) {
                ds = DamageSource.anvil;
            }
            else if (damageName.equals("cactus")) {
                ds = DamageSource.cactus;
            }
            else if (damageName.equals("drown")) {
                ds = DamageSource.drown;
            }
            else if (damageName.equals("fall")) {
                ds = DamageSource.fall;
            }
            else if (damageName.equals("fallingblock")) {
                ds = DamageSource.fallingBlock;
            }
            else if (damageName.equals("generic")) {
                ds = DamageSource.generic;
            }
            else if (damageName.equals("infire")) {
                ds = DamageSource.inFire;
            }
            else if (damageName.equals("inwall")) {
                ds = DamageSource.inWall;
            }
            else if (damageName.equals("lava")) {
                ds = DamageSource.lava;
            }
            else if (damageName.equals("magic")) {
                ds = DamageSource.magic;
            }
            else if (damageName.equals("onfire")) {
                ds = DamageSource.onFire;
            }
            else if (damageName.equals("starve")) {
                ds = DamageSource.starve;
            }
            else if (damageName.equals("wither")) {
                ds = DamageSource.wither;
            }
        }
        int attacked = 0;
        final List list = sender.getEntityWorld().loadedEntityList;
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i) != null && !(list.get(i) instanceof EntityPlayer) && list.get(i).getClass().getName().toLowerCase().indexOf(className) >= 0) {
                list.get(i).attackEntityFrom(ds, damage);
                ++attacked;
            }
        }
        sender.addChatMessage((IChatComponent)new ChatComponentText(attacked + " entity attacked(" + args[1] + ", damage=" + damage + ")."));
    }
    
    private void executeKillEntity(final ICommandSender sender, final String[] args) {
        if (args.length < 2) {
            throw new WrongUsageException("/mcheli killentity <entity class name : example1 EntityBat , example2 minecraft.entity.passive>", new Object[0]);
        }
        final String className = args[1].toLowerCase();
        int killed = 0;
        final List list = sender.getEntityWorld().loadedEntityList;
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i) != null && !(list.get(i) instanceof EntityPlayer) && list.get(i).getClass().getName().toLowerCase().indexOf(className) >= 0) {
                list.get(i).setDead();
                ++killed;
            }
        }
        sender.addChatMessage((IChatComponent)new ChatComponentText(killed + " entity killed(" + args[1] + ")."));
    }
    
    private void executeRemoveEntity(final ICommandSender sender, final String[] args) {
        if (args.length < 2) {
            throw new WrongUsageException("/mcheli removeentity <entity class name : example1 EntityBat , example2 minecraft.entity.passive>", new Object[0]);
        }
        final String className = args[1].toLowerCase();
        final List list = sender.getEntityWorld().loadedEntityList;
        int removed = 0;
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i) != null && !(list.get(i) instanceof EntityPlayer) && list.get(i).getClass().getName().toLowerCase().indexOf(className) >= 0) {
                list.get(i).isDead = true;
                ++removed;
            }
        }
        sender.addChatMessage((IChatComponent)new ChatComponentText(removed + " entity removed(" + args[1] + ")."));
    }
    
    private void executeStatus(final ICommandSender sender, final String[] args) {
        if (args.length < 2) {
            throw new WrongUsageException("/mcheli status <entity or tile> [min num]", new Object[0]);
        }
        if (args[1].equalsIgnoreCase("entity")) {
            this.executeStatusSub(sender, args, "Server loaded Entity List", sender.getEntityWorld().loadedEntityList);
        }
        else if (args[1].equalsIgnoreCase("tile")) {
            this.executeStatusSub(sender, args, "Server loaded Tile Entity List", sender.getEntityWorld().loadedTileEntityList);
        }
    }
    
    private void executeStatusSub(final ICommandSender sender, final String[] args, final String title, final List list) {
        final int minNum = (args.length >= 3) ? Integer.valueOf(args[2]) : 0;
        final HashMap<String, Integer> map = new HashMap<String, Integer>();
        for (int i = 0; i < list.size(); ++i) {
            final String key = list.get(i).getClass().getName();
            if (map.containsKey(key)) {
                map.put(key, map.get(key) + 1);
            }
            else {
                map.put(key, 1);
            }
        }
        final List<Map.Entry<String, Integer>> entries = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(final Map.Entry<String, Integer> entry1, final Map.Entry<String, Integer> entry2) {
                return entry1.getKey().compareTo((String)entry2.getKey());
            }
        });
        boolean send = false;
        sender.addChatMessage((IChatComponent)new ChatComponentText("--- " + title + " ---"));
        for (final Map.Entry<String, Integer> s : entries) {
            if (s.getValue() >= minNum) {
                final String msg = " " + s.getKey() + " : " + s.getValue();
                System.out.println(msg);
                sender.addChatMessage((IChatComponent)new ChatComponentText(msg));
                send = true;
            }
        }
        if (!send) {
            System.out.println("none");
            sender.addChatMessage((IChatComponent)new ChatComponentText("none"));
        }
    }
    
    public void executeFill(final ICommandSender sender, final String[] args) {
        if (args.length < 8) {
            throw new WrongUsageException("/mcheli fill <x1> <y1> <z1> <x2> <y2> <z2> <block name> [meta data] [oldBlockHandling] [data tag]", new Object[0]);
        }
        int x1 = sender.getCommandSenderPosition().posX;
        int y1 = sender.getCommandSenderPosition().posY;
        int z1 = sender.getCommandSenderPosition().posZ;
        int x2 = sender.getCommandSenderPosition().posX;
        int y2 = sender.getCommandSenderPosition().posY;
        int z2 = sender.getCommandSenderPosition().posZ;
        x1 = MathHelper.floor_double(clamp_coord(sender, (double)x1, args[1]));
        y1 = MathHelper.floor_double(clamp_coord(sender, (double)y1, args[2]));
        z1 = MathHelper.floor_double(clamp_coord(sender, (double)z1, args[3]));
        x2 = MathHelper.floor_double(clamp_coord(sender, (double)x2, args[4]));
        y2 = MathHelper.floor_double(clamp_coord(sender, (double)y2, args[5]));
        z2 = MathHelper.floor_double(clamp_coord(sender, (double)z2, args[6]));
        final Block block = CommandBase.getBlockByText(sender, args[7]);
        int metadata = 0;
        if (args.length >= 9) {
            metadata = parseIntBounded(sender, args[8], 0, 15);
        }
        final World world = sender.getEntityWorld();
        if (x1 > x2) {
            final int t = x1;
            x1 = x2;
            x2 = t;
        }
        if (y1 > y2) {
            final int t = y1;
            y1 = y2;
            y2 = t;
        }
        if (z1 > z2) {
            final int t = z1;
            z1 = z2;
            z2 = t;
        }
        if (y1 < 0 || y2 >= 256) {
            throw new CommandException("commands.setblock.outOfWorld", new Object[0]);
        }
        final int blockNum = (x2 - x1 + 1) * (y2 - y1 + 1) * (z2 - z1 + 1);
        if (blockNum > 3000000) {
            throw new CommandException("commands.setblock.tooManyBlocks " + blockNum + " limit=327680", new Object[] { blockNum, 3276800 });
        }
        boolean result = false;
        final boolean keep = args.length >= 10 && args[9].equals("keep");
        final boolean destroy = args.length >= 10 && args[9].equals("destroy");
        final boolean override = args.length >= 10 && args[9].equals("override");
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        boolean flag = false;
        if (args.length >= 11 && block.hasTileEntity()) {
            final String s = getChatComponentFromNthArg(sender, args, 10).getUnformattedText();
            try {
                final NBTBase nbtbase = JsonToNBT.func_150315_a(s);
                if (!(nbtbase instanceof NBTTagCompound)) {
                    throw new CommandException("commands.setblock.tagError", new Object[] { "Not a valid tag" });
                }
                nbttagcompound = (NBTTagCompound)nbtbase;
                flag = true;
            }
            catch (NBTException nbtexception) {
                throw new CommandException("commands.setblock.tagError", new Object[] { nbtexception.getMessage() });
            }
        }
        for (int x3 = x1; x3 <= x2; ++x3) {
            for (int y3 = y1; y3 <= y2; ++y3) {
                for (int z3 = z1; z3 <= z2; ++z3) {
                    if (world.blockExists(x3, y3, z3)) {
                        if (world.isAirBlock(x3, y3, z3)) {
                            if (override) {
                                continue;
                            }
                        }
                        else if (keep) {
                            continue;
                        }
                        if (destroy) {
                            world.breakBlock(x3, y3, z3, false);
                        }
                        final TileEntity block2 = world.getTileEntity(x3, y3, z3);
                        if (block2 instanceof IInventory) {
                            final IInventory ii = (IInventory)block2;
                            for (int i = 0; i < ii.getSizeInventory(); ++i) {
                                final ItemStack is = ii.getStackInSlotOnClosing(i);
                                if (is != null) {
                                    is.stackSize = 0;
                                }
                            }
                        }
                        if (world.setBlock(x3, y3, z3, block, metadata, 3)) {
                            if (flag) {
                                final TileEntity tileentity = world.getTileEntity(x3, y3, z3);
                                if (tileentity != null) {
                                    nbttagcompound.setInteger("x", x3);
                                    nbttagcompound.setInteger("y", y3);
                                    nbttagcompound.setInteger("z", z3);
                                    tileentity.readFromNBT(nbttagcompound);
                                }
                            }
                            result = true;
                        }
                    }
                }
            }
        }
        if (result) {
            notifyOperators(sender, (ICommand)this, "commands.setblock.success", new Object[0]);
            return;
        }
        throw new CommandException("commands.setblock.noChange", new Object[0]);
    }
    
    public List addTabCompletionOptions(final ICommandSender sender, final String[] prm) {
        final MCH_Config config = MCH_MOD.config;
        if (!MCH_Config.EnableCommand.prmBool) {
            return null;
        }
        if (prm.length <= 1) {
            return getListOfStringsMatchingLastWord(prm, MCH_Command.ALL_COMMAND);
        }
        if (prm[0].equalsIgnoreCase("sendss")) {
            if (prm.length == 2) {
                return getListOfStringsMatchingLastWord(prm, MinecraftServer.getServer().getAllUsernames());
            }
        }
        else if (prm[0].equalsIgnoreCase("modlist")) {
            if (prm.length >= 2) {
                return getListOfStringsMatchingLastWord(prm, MinecraftServer.getServer().getAllUsernames());
            }
        }
        else if (prm[0].equalsIgnoreCase("fill")) {
            if ((prm.length == 2 || prm.length == 5) && sender instanceof Entity) {
                final Entity entity = (Entity)sender;
                final List a = new ArrayList();
                final int x = (entity.posX < 0.0) ? ((int)(entity.posX - 1.0)) : ((int)entity.posX);
                final int z = (entity.posZ < 0.0) ? ((int)(entity.posZ - 1.0)) : ((int)entity.posZ);
                a.add("" + x + " " + (int)(entity.posY + 0.5) + " " + z);
                return a;
            }
            return (prm.length == 8) ? getListOfStringsFromIterableMatchingLastWord(prm, (Iterable)Block.blockRegistry.getKeys()) : ((prm.length == 10) ? getListOfStringsMatchingLastWord(prm, new String[] { "replace", "destroy", "keep", "override" }) : null);
        }
        else if (prm[0].equalsIgnoreCase("status")) {
            if (prm.length == 2) {
                return getListOfStringsMatchingLastWord(prm, new String[] { "entity", "tile" });
            }
        }
        else if (prm[0].equalsIgnoreCase("attackentity")) {
            if (prm.length == 4) {
                return getListOfStringsMatchingLastWord(prm, new String[] { "player", "inFire", "onFire", "lava", "inWall", "drown", "starve", "cactus", "fall", "outOfWorld", "generic", "magic", "wither", "anvil", "fallingBlock" });
            }
        }
        else if (prm[0].equalsIgnoreCase("showboundingbox") && prm.length == 2) {
            return getListOfStringsMatchingLastWord(prm, new String[] { "true", "false" });
        }
        return null;
    }
    
    static {
        MCH_Command.ALL_COMMAND = new String[] { "sendss", "modlist", "reconfig", "title", "fill", "status", "killentity", "removeentity", "attackentity", "showboundingbox", "list", "delayhitbox" };
        MCH_Command.instance = new MCH_Command();
    }
}
