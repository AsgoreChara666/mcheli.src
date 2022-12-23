//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.eval.eval.func;

import java.lang.reflect.*;

public class MathFunction implements Function
{
    public long evalLong(final Object object, final String name, final Long[] args) throws Throwable {
        final Class[] types = new Class[args.length];
        for (int i = 0; i < types.length; ++i) {
            types[i] = Long.TYPE;
        }
        final Method m = Math.class.getMethod(name, (Class<?>[])types);
        final Object ret = m.invoke(null, (Object[])args);
        return (long)ret;
    }
    
    public double evalDouble(final Object object, final String name, final Double[] args) throws Throwable {
        final Class[] types = new Class[args.length];
        for (int i = 0; i < types.length; ++i) {
            types[i] = Double.TYPE;
        }
        final Method m = Math.class.getMethod(name, (Class<?>[])types);
        final Object ret = m.invoke(null, (Object[])args);
        return (double)ret;
    }
    
    public Object evalObject(final Object object, final String name, final Object[] args) throws Throwable {
        final Class[] types = new Class[args.length];
        for (int i = 0; i < types.length; ++i) {
            Class c = args[i].getClass();
            if (Double.class.isAssignableFrom(c)) {
                c = Double.TYPE;
            }
            else if (Float.class.isAssignableFrom(c)) {
                c = Float.TYPE;
            }
            else if (Integer.class.isAssignableFrom(c)) {
                c = Integer.TYPE;
            }
            else if (Number.class.isAssignableFrom(c)) {
                c = Long.TYPE;
            }
            types[i] = c;
        }
        final Method m = Math.class.getMethod(name, (Class<?>[])types);
        return m.invoke(null, args);
    }
}
