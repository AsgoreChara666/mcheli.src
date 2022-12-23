//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli;

import net.minecraft.server.*;
import cpw.mods.fml.common.*;
import mcheli.aircraft.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class MCH_CommonProxy
{
    public MCH_Config config;
    public String lastConfigFileName;
    
    public MCH_CommonProxy() {
        this.config = null;
    }
    
    public String getDataDir() {
        return MinecraftServer.getServer().getFolderName();
    }
    
    public void registerRenderer() {
    }
    
    public void registerBlockRenderer() {
    }
    
    public void registerModels() {
    }
    
    public void registerModelsHeli(final String name, final boolean reload) {
    }
    
    public void registerModelsPlane(final String name, final boolean reload) {
    }
    
    public void registerModelsVehicle(final String name, final boolean reload) {
    }
    
    public void registerModelsTank(final String name, final boolean reload) {
    }
    
    public void registerClientTick() {
    }
    
    public void registerServerTick() {
        FMLCommonHandler.instance().bus().register((Object)new MCH_ServerTickHandler());
    }
    
    public boolean isRemote() {
        return false;
    }
    
    public String side() {
        return "Server";
    }
    
    public MCH_SoundUpdater CreateSoundUpdater(final MCH_EntityAircraft aircraft) {
        return null;
    }
    
    public void registerSounds() {
    }
    
    public void loadConfig(final String fileName) {
        this.lastConfigFileName = fileName;
        (this.config = new MCH_Config("./", fileName)).load();
        this.config.write();
    }
    
    public void reconfig() {
        MCH_Lib.DbgLog(false, "MCH_CommonProxy.reconfig()", new Object[0]);
        this.loadConfig(this.lastConfigFileName);
    }
    
    public void save() {
        MCH_Lib.DbgLog(false, "MCH_CommonProxy.save()", new Object[0]);
        this.config.write();
    }
    
    public void loadHUD(final String path) {
    }
    
    public void reloadHUD() {
    }
    
    public Entity getClientPlayer() {
        return null;
    }
    
    public void setCreativeDigDelay(final int n) {
    }
    
    public void init() {
    }
    
    public boolean isFirstPerson() {
        return false;
    }
    
    public int getNewRenderType() {
        return -1;
    }
    
    public boolean isSinglePlayer() {
        return MinecraftServer.getServer().isSinglePlayer();
    }
    
    public void readClientModList() {
    }
    
    public void printChatMessage(final IChatComponent chat, final int showTime, final int pos) {
    }
    
    public void hitBullet() {
    }
    
    public void clientLocked() {
    }
}
