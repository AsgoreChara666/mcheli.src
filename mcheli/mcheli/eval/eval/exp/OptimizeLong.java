//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.eval.eval.exp;

public class OptimizeLong extends OptimizeObject
{
    @Override
    protected boolean isTrue(final AbstractExpression x) {
        return x.evalLong() != 0L;
    }
    
    @Override
    protected AbstractExpression toConst(final AbstractExpression exp) {
        try {
            final long val = exp.evalLong();
            return (AbstractExpression)NumberExpression.create(exp, Long.toString(val));
        }
        catch (Exception e) {
            return exp;
        }
    }
}
