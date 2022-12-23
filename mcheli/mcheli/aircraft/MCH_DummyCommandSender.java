//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.aircraft;

import net.minecraft.server.*;
import net.minecraft.command.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class MCH_DummyCommandSender implements ICommandSender
{
    public static MCH_DummyCommandSender instance;
    
    public static void execCommand(final String s) {
        final ICommandManager icommandmanager = MinecraftServer.getServer().getCommandManager();
        icommandmanager.executeCommand((ICommandSender)MCH_DummyCommandSender.instance, s);
    }
    
    public String getCommandSenderName() {
        return "";
    }
    
    public IChatComponent getFormattedCommandSenderName() {
        return null;
    }
    
    public void addChatMessage(final IChatComponent message) {
    }
    
    public boolean canCommandSenderUseCommand(final int permissionLevel, final String command) {
        return true;
    }
    
    public ChunkCoordinates getCommandSenderPosition() {
        return null;
    }
    
    public World getEntityWorld() {
        return null;
    }
    
    static {
        MCH_DummyCommandSender.instance = new MCH_DummyCommandSender();
    }
}
