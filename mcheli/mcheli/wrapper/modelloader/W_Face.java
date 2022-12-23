//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.wrapper.modelloader;

import cpw.mods.fml.relauncher.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;

@SideOnly(Side.CLIENT)
public class W_Face
{
    public int[] verticesID;
    public W_Vertex[] vertices;
    public W_Vertex[] vertexNormals;
    public W_Vertex faceNormal;
    public W_TextureCoordinate[] textureCoordinates;
    
    public W_Face copy() {
        final W_Face f = new W_Face();
        return f;
    }
    
    public void addFaceForRender(final Tessellator tessellator) {
        this.addFaceForRender(tessellator, 0.0f);
    }
    
    public void addFaceForRender(final Tessellator tessellator, final float textureOffset) {
        if (this.faceNormal == null) {
            this.faceNormal = this.calculateFaceNormal();
        }
        tessellator.setNormal(this.faceNormal.x, this.faceNormal.y, this.faceNormal.z);
        float averageU = 0.0f;
        float averageV = 0.0f;
        if (this.textureCoordinates != null && this.textureCoordinates.length > 0) {
            for (int i = 0; i < this.textureCoordinates.length; ++i) {
                averageU += this.textureCoordinates[i].u;
                averageV += this.textureCoordinates[i].v;
            }
            averageU /= this.textureCoordinates.length;
            averageV /= this.textureCoordinates.length;
        }
        for (int j = 0; j < this.vertices.length; ++j) {
            if (this.textureCoordinates != null && this.textureCoordinates.length > 0) {
                float offsetU = textureOffset;
                float offsetV = textureOffset;
                if (this.textureCoordinates[j].u > averageU) {
                    offsetU = -offsetU;
                }
                if (this.textureCoordinates[j].v > averageV) {
                    offsetV = -offsetV;
                }
                if (this.vertexNormals != null && j < this.vertexNormals.length) {
                    tessellator.setNormal(this.vertexNormals[j].x, this.vertexNormals[j].y, this.vertexNormals[j].z);
                }
                tessellator.addVertexWithUV((double)this.vertices[j].x, (double)this.vertices[j].y, (double)this.vertices[j].z, (double)(this.textureCoordinates[j].u + offsetU), (double)(this.textureCoordinates[j].v + offsetV));
            }
            else {
                tessellator.addVertex((double)this.vertices[j].x, (double)this.vertices[j].y, (double)this.vertices[j].z);
            }
        }
    }
    
    public W_Vertex calculateFaceNormal() {
        final Vec3 v1 = Vec3.createVectorHelper((double)(this.vertices[1].x - this.vertices[0].x), (double)(this.vertices[1].y - this.vertices[0].y), (double)(this.vertices[1].z - this.vertices[0].z));
        final Vec3 v2 = Vec3.createVectorHelper((double)(this.vertices[2].x - this.vertices[0].x), (double)(this.vertices[2].y - this.vertices[0].y), (double)(this.vertices[2].z - this.vertices[0].z));
        Vec3 normalVector = null;
        normalVector = v1.crossProduct(v2).normalize();
        return new W_Vertex((float)normalVector.xCoord, (float)normalVector.yCoord, (float)normalVector.zCoord);
    }
}
