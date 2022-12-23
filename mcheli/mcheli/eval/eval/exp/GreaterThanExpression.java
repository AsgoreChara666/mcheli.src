//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.eval.eval.exp;

public class GreaterThanExpression extends Col2Expression
{
    public GreaterThanExpression() {
        this.setOperator(">");
    }
    
    protected GreaterThanExpression(final GreaterThanExpression from, final ShareExpValue s) {
        super((Col2Expression)from, s);
    }
    
    public AbstractExpression dup(final ShareExpValue s) {
        return (AbstractExpression)new GreaterThanExpression(this, s);
    }
    
    protected long operateLong(final long vl, final long vr) {
        return (vl > vr) ? 1 : 0;
    }
    
    protected double operateDouble(final double vl, final double vr) {
        return (vl > vr) ? 1.0 : 0.0;
    }
    
    protected Object operateObject(final Object vl, final Object vr) {
        return this.share.oper.greaterThan(vl, vr);
    }
}
