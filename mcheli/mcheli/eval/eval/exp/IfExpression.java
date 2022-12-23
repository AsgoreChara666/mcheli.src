//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.eval.eval.exp;

public class IfExpression extends Col3Expression
{
    public IfExpression() {
        this.setOperator("?");
        this.setEndOperator(":");
    }
    
    protected IfExpression(final IfExpression from, final ShareExpValue s) {
        super((Col3Expression)from, s);
    }
    
    public AbstractExpression dup(final ShareExpValue s) {
        return (AbstractExpression)new IfExpression(this, s);
    }
    
    public long evalLong() {
        if (this.exp1.evalLong() != 0L) {
            return this.exp2.evalLong();
        }
        return this.exp3.evalLong();
    }
    
    public double evalDouble() {
        if (this.exp1.evalDouble() != 0.0) {
            return this.exp2.evalDouble();
        }
        return this.exp3.evalDouble();
    }
    
    public Object evalObject() {
        if (this.share.oper.bool(this.exp1.evalObject())) {
            return this.exp2.evalObject();
        }
        return this.exp3.evalObject();
    }
}
