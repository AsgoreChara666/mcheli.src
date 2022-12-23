//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.weapon;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import mcheli.wrapper.*;

public class MCH_DummyEntityPlayer extends W_EntityPlayer
{
    public MCH_DummyEntityPlayer(final World p_i45324_1_, final EntityPlayer player) {
        super(p_i45324_1_, player);
    }
    
    public void addChatMessage(final IChatComponent var1) {
    }
    
    public boolean canCommandSenderUseCommand(final int var1, final String var2) {
        return false;
    }
    
    public ChunkCoordinates getCommandSenderPosition() {
        return null;
    }
    
    public void sendChatToPlayer(final ChatMessageComponent chatmessagecomponent) {
    }
}
