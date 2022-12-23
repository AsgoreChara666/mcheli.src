//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.eval.eval.exp;

public class BitNotExpression extends Col1Expression
{
    public BitNotExpression() {
        this.setOperator("~");
    }
    
    protected BitNotExpression(final BitNotExpression from, final ShareExpValue s) {
        super(from, s);
    }
    
    public AbstractExpression dup(final ShareExpValue s) {
        return new BitNotExpression(this, s);
    }
    
    @Override
    protected long operateLong(final long val) {
        return ~val;
    }
    
    @Override
    protected double operateDouble(final double val) {
        return (double)~(long)val;
    }
    
    public Object evalObject() {
        return this.share.oper.bitNot(this.exp.evalObject());
    }
}
