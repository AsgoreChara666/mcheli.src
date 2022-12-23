//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.eval.eval.exp;

public class ParenExpression extends Col1Expression
{
    public ParenExpression() {
        this.setOperator("(");
        this.setEndOperator(")");
    }
    
    protected ParenExpression(final ParenExpression from, final ShareExpValue s) {
        super((Col1Expression)from, s);
    }
    
    public AbstractExpression dup(final ShareExpValue s) {
        return (AbstractExpression)new ParenExpression(this, s);
    }
    
    protected long operateLong(final long val) {
        return val;
    }
    
    protected double operateDouble(final double val) {
        return val;
    }
    
    public Object evalObject() {
        return this.exp.evalObject();
    }
    
    public String toString() {
        if (this.exp == null) {
            return "";
        }
        return this.getOperator() + this.exp.toString() + this.getEndOperator();
    }
}
