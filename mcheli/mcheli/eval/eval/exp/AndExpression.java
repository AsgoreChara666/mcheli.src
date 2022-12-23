//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.eval.eval.exp;

public class AndExpression extends Col2OpeExpression
{
    public AndExpression() {
        this.setOperator("&&");
    }
    
    protected AndExpression(final AndExpression from, final ShareExpValue s) {
        super(from, s);
    }
    
    public AbstractExpression dup(final ShareExpValue s) {
        return new AndExpression(this, s);
    }
    
    @Override
    public long evalLong() {
        final long val = this.expl.evalLong();
        if (val == 0L) {
            return val;
        }
        return this.expr.evalLong();
    }
    
    @Override
    public double evalDouble() {
        final double val = this.expl.evalDouble();
        if (val == 0.0) {
            return val;
        }
        return this.expr.evalDouble();
    }
    
    @Override
    public Object evalObject() {
        final Object val = this.expl.evalObject();
        if (!this.share.oper.bool(val)) {
            return val;
        }
        return this.expr.evalObject();
    }
}
