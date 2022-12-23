//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.eval.eval.exp;

public class NotExpression extends Col1Expression
{
    public NotExpression() {
        this.setOperator("!");
    }
    
    protected NotExpression(final NotExpression from, final ShareExpValue s) {
        super((Col1Expression)from, s);
    }
    
    public AbstractExpression dup(final ShareExpValue s) {
        return (AbstractExpression)new NotExpression(this, s);
    }
    
    protected long operateLong(final long val) {
        return (val == 0L) ? 1 : 0;
    }
    
    protected double operateDouble(final double val) {
        return (val == 0.0) ? 1.0 : 0.0;
    }
    
    public Object evalObject() {
        return this.share.oper.not(this.exp.evalObject());
    }
}
