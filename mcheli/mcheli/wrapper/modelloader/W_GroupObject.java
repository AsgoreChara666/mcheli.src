//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.wrapper.modelloader;

import cpw.mods.fml.relauncher.*;
import net.minecraft.client.renderer.*;
import java.util.*;

@SideOnly(Side.CLIENT)
public class W_GroupObject
{
    public String name;
    public ArrayList<W_Face> faces;
    public int glDrawingMode;
    
    public W_GroupObject() {
        this("");
    }
    
    public W_GroupObject(final String name) {
        this(name, -1);
    }
    
    public W_GroupObject(final String name, final int glDrawingMode) {
        this.faces = new ArrayList<W_Face>();
        this.name = name;
        this.glDrawingMode = glDrawingMode;
    }
    
    public void render() {
        if (this.faces.size() > 0) {
            final Tessellator tessellator = Tessellator.instance;
            tessellator.startDrawing(this.glDrawingMode);
            this.render(tessellator);
            tessellator.draw();
        }
    }
    
    public void render(final Tessellator tessellator) {
        if (this.faces.size() > 0) {
            for (final W_Face face : this.faces) {
                face.addFaceForRender(tessellator);
            }
        }
    }
}
