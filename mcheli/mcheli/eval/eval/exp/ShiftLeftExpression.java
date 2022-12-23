//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.eval.eval.exp;

public class ShiftLeftExpression extends Col2Expression
{
    public ShiftLeftExpression() {
        this.setOperator("<<");
    }
    
    protected ShiftLeftExpression(final ShiftLeftExpression from, final ShareExpValue s) {
        super((Col2Expression)from, s);
    }
    
    public AbstractExpression dup(final ShareExpValue s) {
        return (AbstractExpression)new ShiftLeftExpression(this, s);
    }
    
    protected long operateLong(final long vl, final long vr) {
        return vl << (int)vr;
    }
    
    protected double operateDouble(final double vl, final double vr) {
        return vl * Math.pow(2.0, vr);
    }
    
    protected Object operateObject(final Object vl, final Object vr) {
        return this.share.oper.shiftLeft(vl, vr);
    }
}
