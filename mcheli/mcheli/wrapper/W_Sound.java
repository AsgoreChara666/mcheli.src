//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.wrapper;

import net.minecraft.client.audio.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public class W_Sound extends MovingSound
{
    protected W_Sound(final ResourceLocation r, final float volume, final float pitch, final double x, final double y, final double z) {
        super(r);
        this.setVolumeAndPitch(volume, pitch);
        this.setPosition(x, y, z);
    }
    
    protected W_Sound(final ResourceLocation r, final float volume, final float pitch) {
        super(r);
        this.setVolumeAndPitch(volume, pitch);
        final Entity entity = W_McClient.getRenderEntity();
        if (entity != null) {
            this.setPosition(entity.posX, entity.posY, entity.posZ);
        }
    }
    
    public void setRepeat(final boolean b) {
        this.repeat = b;
    }
    
    public void setSoundParam(final Entity e, final float v, final float p) {
        this.setPosition(e);
        this.setVolumeAndPitch(v, p);
    }
    
    public void setVolumeAndPitch(final float v, final float p) {
        this.setVolume(v);
        this.setPitch(p);
    }
    
    public void setVolume(final float v) {
        this.volume = v;
    }
    
    public void setPitch(final float p) {
        this.pitch = p;
    }
    
    public void setPosition(final double x, final double y, final double z) {
        this.xPosF = (float)x;
        this.yPosF = (float)y;
        this.zPosF = (float)z;
    }
    
    public void setPosition(final Entity e) {
        this.setPosition(e.posX, e.posY, e.posZ);
    }
    
    public void update() {
    }
}
