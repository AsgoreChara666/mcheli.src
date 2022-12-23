//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.chain;

import mcheli.wrapper.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.entity.*;
import mcheli.*;
import net.minecraft.util.*;

@SideOnly(Side.CLIENT)
public class MCH_RenderChain extends W_Render
{
    public void doRender(final Entity e, final double posX, final double posY, final double posZ, final float par8, final float par9) {
        if (!(e instanceof MCH_EntityChain)) {
            return;
        }
        final MCH_EntityChain chain = (MCH_EntityChain)e;
        if (chain.towedEntity == null || chain.towEntity == null) {
            return;
        }
        GL11.glPushMatrix();
        GL11.glEnable(2884);
        GL11.glColor4f(0.5f, 0.5f, 0.5f, 1.0f);
        final double lastTickPosX = chain.towedEntity.lastTickPosX;
        final RenderManager instance = RenderManager.instance;
        final double n = lastTickPosX - RenderManager.renderPosX;
        final double lastTickPosY = chain.towedEntity.lastTickPosY;
        final RenderManager instance2 = RenderManager.instance;
        final double n2 = lastTickPosY - RenderManager.renderPosY;
        final double lastTickPosZ = chain.towedEntity.lastTickPosZ;
        final RenderManager instance3 = RenderManager.instance;
        GL11.glTranslated(n, n2, lastTickPosZ - RenderManager.renderPosZ);
        this.bindTexture("textures/chain.png");
        final double dx = chain.towEntity.lastTickPosX - chain.towedEntity.lastTickPosX;
        final double dy = chain.towEntity.lastTickPosY - chain.towedEntity.lastTickPosY;
        final double dz = chain.towEntity.lastTickPosZ - chain.towedEntity.lastTickPosZ;
        double diff = Math.sqrt(dx * dx + dy * dy + dz * dz);
        final float CHAIN_LEN = 0.95f;
        final double x = dx * 0.949999988079071 / diff;
        final double y = dy * 0.949999988079071 / diff;
        final double z = dz * 0.949999988079071 / diff;
        while (diff > 0.949999988079071) {
            GL11.glTranslated(x, y, z);
            GL11.glPushMatrix();
            final Vec3 v = MCH_Lib.getYawPitchFromVec(x, y, z);
            GL11.glRotatef((float)v.yCoord, 0.0f, -1.0f, 0.0f);
            GL11.glRotatef((float)v.zCoord, 0.0f, 0.0f, 1.0f);
            MCH_ModelManager.render("chain");
            GL11.glPopMatrix();
            diff -= 0.949999988079071;
        }
        GL11.glPopMatrix();
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return MCH_RenderChain.TEX_DEFAULT;
    }
}
