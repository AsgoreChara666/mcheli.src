//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.aircraft;

import mcheli.*;
import net.minecraft.item.crafting.*;
import net.minecraft.item.*;
import java.util.*;

public abstract class MCH_AircraftInfoManager extends MCH_InfoManagerBase implements MCH_IRecipeList
{
    private List<IRecipe> listItemRecipe;
    
    public MCH_AircraftInfoManager() {
        this.listItemRecipe = new ArrayList<IRecipe>();
    }
    
    @Override
    public int getRecipeListSize() {
        return this.listItemRecipe.size();
    }
    
    @Override
    public IRecipe getRecipe(final int index) {
        return this.listItemRecipe.get(index);
    }
    
    public void addRecipe(final IRecipe recipe, final int count, final String name, final String recipeString) {
        if (recipe == null || recipe.getRecipeOutput() == null || recipe.getRecipeOutput().getItem() == null) {
            throw new RuntimeException("[mcheli]Recipe Parameter Error! recipe" + count + " : " + name + ".txt : " + String.valueOf(recipe) + " : " + recipeString);
        }
        this.listItemRecipe.add(recipe);
    }
    
    public abstract MCH_AircraftInfo getAcInfoFromItem(final Item p0);
    
    public MCH_AircraftInfo getAcInfoFromItem(final IRecipe recipe) {
        final Map<String, MCH_AircraftInfo> map = (Map<String, MCH_AircraftInfo>)this.getMap();
        if (recipe != null) {
            return this.getAcInfoFromItem(recipe.getRecipeOutput().getItem());
        }
        return null;
    }
}
