//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.eval.eval.exp;

public class SignPlusExpression extends Col1Expression
{
    public SignPlusExpression() {
        this.setOperator("+");
    }
    
    protected SignPlusExpression(final SignPlusExpression from, final ShareExpValue s) {
        super((Col1Expression)from, s);
    }
    
    public AbstractExpression dup(final ShareExpValue s) {
        return (AbstractExpression)new SignPlusExpression(this, s);
    }
    
    protected long operateLong(final long val) {
        return val;
    }
    
    protected double operateDouble(final double val) {
        return val;
    }
    
    public Object evalObject() {
        return this.share.oper.signPlus(this.exp.evalObject());
    }
}
