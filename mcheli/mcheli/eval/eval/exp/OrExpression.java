//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.eval.eval.exp;

public class OrExpression extends Col2OpeExpression
{
    public OrExpression() {
        this.setOperator("||");
    }
    
    protected OrExpression(final OrExpression from, final ShareExpValue s) {
        super((Col2Expression)from, s);
    }
    
    public AbstractExpression dup(final ShareExpValue s) {
        return (AbstractExpression)new OrExpression(this, s);
    }
    
    public long evalLong() {
        final long val = this.expl.evalLong();
        if (val != 0L) {
            return val;
        }
        return this.expr.evalLong();
    }
    
    public double evalDouble() {
        final double val = this.expl.evalDouble();
        if (val != 0.0) {
            return val;
        }
        return this.expr.evalDouble();
    }
    
    public Object evalObject() {
        final Object val = this.expl.evalObject();
        if (this.share.oper.bool(val)) {
            return val;
        }
        return this.expr.evalObject();
    }
}
