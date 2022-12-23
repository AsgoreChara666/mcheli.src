//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.aircraft;

import net.minecraft.world.*;
import mcheli.*;
import net.minecraft.entity.*;
import mcheli.wrapper.*;
import net.minecraft.entity.monster.*;
import java.util.*;

public class MCH_Radar
{
    private World worldObj;
    private ArrayList<MCH_Vector2> entityList;
    private ArrayList<MCH_Vector2> enemyList;
    
    public ArrayList<MCH_Vector2> getEntityList() {
        return this.entityList;
    }
    
    public ArrayList<MCH_Vector2> getEnemyList() {
        return this.enemyList;
    }
    
    public MCH_Radar(final World world) {
        this.entityList = new ArrayList<MCH_Vector2>();
        this.enemyList = new ArrayList<MCH_Vector2>();
        this.worldObj = world;
    }
    
    public void clear() {
        this.entityList.clear();
        this.enemyList.clear();
    }
    
    public void updateXZ(final Entity centerEntity, final int range) {
        if (!this.worldObj.isRemote) {
            return;
        }
        this.clear();
        final List list = centerEntity.worldObj.getEntitiesWithinAABBExcludingEntity(centerEntity, centerEntity.boundingBox.expand((double)range, (double)range, (double)range));
        for (int i = 0; i < list.size(); ++i) {
            final Entity entity = list.get(i);
            if (entity instanceof EntityLiving) {
                final double x = entity.posX - centerEntity.posX;
                final double z = entity.posZ - centerEntity.posZ;
                if (x * x + z * z < range * range) {
                    int y = 1 + (int)entity.posY;
                    if (y < 0) {
                        y = 1;
                    }
                    int blockCnt;
                    for (blockCnt = 0; y < 200 && (W_WorldFunc.getBlockId(this.worldObj, (int)entity.posX, y, (int)entity.posZ) == 0 || ++blockCnt < 5); ++y) {}
                    if (blockCnt < 5) {
                        if (entity instanceof EntityMob) {
                            this.enemyList.add(new MCH_Vector2(x, z));
                        }
                        else {
                            this.entityList.add(new MCH_Vector2(x, z));
                        }
                    }
                }
            }
        }
    }
}
