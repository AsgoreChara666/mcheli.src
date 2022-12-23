//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.eval.eval.exp;

public class PowerExpression extends Col2Expression
{
    public PowerExpression() {
        this.setOperator("**");
    }
    
    protected PowerExpression(final PowerExpression from, final ShareExpValue s) {
        super((Col2Expression)from, s);
    }
    
    public AbstractExpression dup(final ShareExpValue s) {
        return (AbstractExpression)new PowerExpression(this, s);
    }
    
    protected long operateLong(final long vl, final long vr) {
        return (long)Math.pow((double)vl, (double)vr);
    }
    
    protected double operateDouble(final double vl, final double vr) {
        return Math.pow(vl, vr);
    }
    
    protected Object operateObject(final Object vl, final Object vr) {
        return this.share.oper.power(vl, vr);
    }
}
