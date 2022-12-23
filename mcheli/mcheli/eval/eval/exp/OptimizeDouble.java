//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.eval.eval.exp;

public class OptimizeDouble extends OptimizeObject
{
    @Override
    protected boolean isTrue(final AbstractExpression x) {
        return x.evalDouble() != 0.0;
    }
    
    @Override
    protected AbstractExpression toConst(final AbstractExpression exp) {
        try {
            final double val = exp.evalDouble();
            return (AbstractExpression)NumberExpression.create(exp, Double.toString(val));
        }
        catch (Exception e) {
            return exp;
        }
    }
}
