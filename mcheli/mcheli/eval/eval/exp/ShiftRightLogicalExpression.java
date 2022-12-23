//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.eval.eval.exp;

public class ShiftRightLogicalExpression extends Col2Expression
{
    public ShiftRightLogicalExpression() {
        this.setOperator(">>>");
    }
    
    protected ShiftRightLogicalExpression(final ShiftRightLogicalExpression from, final ShareExpValue s) {
        super((Col2Expression)from, s);
    }
    
    public AbstractExpression dup(final ShareExpValue s) {
        return (AbstractExpression)new ShiftRightLogicalExpression(this, s);
    }
    
    protected long operateLong(final long vl, final long vr) {
        return vl >>> (int)vr;
    }
    
    protected double operateDouble(double vl, final double vr) {
        if (vl < 0.0) {
            vl = -vl;
        }
        return vl / Math.pow(2.0, vr);
    }
    
    protected Object operateObject(final Object vl, final Object vr) {
        return this.share.oper.shiftRightLogical(vl, vr);
    }
}
