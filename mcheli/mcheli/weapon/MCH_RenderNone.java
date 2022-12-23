//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.weapon;

import cpw.mods.fml.relauncher.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

@SideOnly(Side.CLIENT)
public class MCH_RenderNone extends MCH_RenderBulletBase
{
    public void renderBullet(final Entity entity, final double posX, final double posY, final double posZ, final float yaw, final float partialTickTime) {
    }
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return MCH_RenderNone.TEX_DEFAULT;
    }
}
