//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.eval.eval.exp;

public class LetExpression extends Col2OpeExpression
{
    public LetExpression() {
        this.setOperator("=");
    }
    
    protected LetExpression(final LetExpression from, final ShareExpValue s) {
        super((Col2Expression)from, s);
    }
    
    public AbstractExpression dup(final ShareExpValue s) {
        return (AbstractExpression)new LetExpression(this, s);
    }
    
    public long evalLong() {
        final long val = this.expr.evalLong();
        this.expl.let(val, this.pos);
        return val;
    }
    
    public double evalDouble() {
        final double val = this.expr.evalDouble();
        this.expl.let(val, this.pos);
        return val;
    }
    
    public Object evalObject() {
        final Object val = this.expr.evalObject();
        this.expl.let(val, this.pos);
        return val;
    }
    
    protected AbstractExpression replace() {
        this.expl = this.expl.replaceVar();
        this.expr = this.expr.replace();
        return this.share.repl.replaceLet((Col2Expression)this);
    }
}
