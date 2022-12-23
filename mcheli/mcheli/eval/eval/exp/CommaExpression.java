//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.eval.eval.exp;

public class CommaExpression extends Col2OpeExpression
{
    public CommaExpression() {
        this.setOperator(",");
    }
    
    protected CommaExpression(final CommaExpression from, final ShareExpValue s) {
        super((Col2Expression)from, s);
    }
    
    public AbstractExpression dup(final ShareExpValue s) {
        return (AbstractExpression)new CommaExpression(this, s);
    }
    
    public long evalLong() {
        this.expl.evalLong();
        return this.expr.evalLong();
    }
    
    public double evalDouble() {
        this.expl.evalDouble();
        return this.expr.evalDouble();
    }
    
    public Object evalObject() {
        this.expl.evalObject();
        return this.expr.evalObject();
    }
    
    protected String toStringLeftSpace() {
        return "";
    }
}
