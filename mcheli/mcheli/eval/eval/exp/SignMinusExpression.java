//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.eval.eval.exp;

public class SignMinusExpression extends Col1Expression
{
    public SignMinusExpression() {
        this.setOperator("-");
    }
    
    protected SignMinusExpression(final SignMinusExpression from, final ShareExpValue s) {
        super((Col1Expression)from, s);
    }
    
    public AbstractExpression dup(final ShareExpValue s) {
        return (AbstractExpression)new SignMinusExpression(this, s);
    }
    
    protected long operateLong(final long val) {
        return -val;
    }
    
    protected double operateDouble(final double val) {
        return -val;
    }
    
    public Object evalObject() {
        return this.share.oper.signMinus(this.exp.evalObject());
    }
}
