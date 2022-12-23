//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli;

public class MCH_LowPassFilterFloat
{
    private MCH_Queue<Float> filter;
    
    public MCH_LowPassFilterFloat(final int filterLength) {
        this.filter = new MCH_Queue<Float>(filterLength, 0.0f);
    }
    
    public void clear() {
        this.filter.clear(0.0f);
    }
    
    public void put(final float t) {
        this.filter.put(t);
    }
    
    public float getAvg() {
        float f = 0.0f;
        for (int i = 0; i < this.filter.size(); ++i) {
            f += this.filter.get(i);
        }
        return f / this.filter.size();
    }
}
