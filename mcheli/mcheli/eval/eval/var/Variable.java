//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.eval.eval.var;

public interface Variable
{
    void setValue(final Object p0, final Object p1);
    
    Object getObject(final Object p0);
    
    long evalLong(final Object p0);
    
    double evalDouble(final Object p0);
    
    Object getObject(final Object p0, final int p1);
    
    void setValue(final Object p0, final int p1, final Object p2);
    
    Object getObject(final Object p0, final String p1);
    
    void setValue(final Object p0, final String p1, final Object p2);
}
