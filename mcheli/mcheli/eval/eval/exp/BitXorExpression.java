//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.eval.eval.exp;

public class BitXorExpression extends Col2Expression
{
    public BitXorExpression() {
        this.setOperator("^");
    }
    
    protected BitXorExpression(final BitXorExpression from, final ShareExpValue s) {
        super(from, s);
    }
    
    public AbstractExpression dup(final ShareExpValue s) {
        return new BitXorExpression(this, s);
    }
    
    @Override
    protected long operateLong(final long vl, final long vr) {
        return vl ^ vr;
    }
    
    @Override
    protected double operateDouble(final double vl, final double vr) {
        return (double)((long)vl ^ (long)vr);
    }
    
    @Override
    protected Object operateObject(final Object vl, final Object vr) {
        return this.share.oper.bitXor(vl, vr);
    }
}
