//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.eval.eval.exp;

public class IncAfterExpression extends Col1AfterExpression
{
    public IncAfterExpression() {
        this.setOperator("++");
    }
    
    protected IncAfterExpression(final IncAfterExpression from, final ShareExpValue s) {
        super((Col1Expression)from, s);
    }
    
    public AbstractExpression dup(final ShareExpValue s) {
        return (AbstractExpression)new IncAfterExpression(this, s);
    }
    
    protected long operateLong(final long val) {
        this.exp.let(val + 1L, this.pos);
        return val;
    }
    
    protected double operateDouble(final double val) {
        this.exp.let(val + 1.0, this.pos);
        return val;
    }
    
    public Object evalObject() {
        final Object val = this.exp.evalObject();
        this.exp.let(this.share.oper.inc(val, 1), this.pos);
        return val;
    }
}
