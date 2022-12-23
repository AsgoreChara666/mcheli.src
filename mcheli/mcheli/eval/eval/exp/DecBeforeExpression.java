//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.eval.eval.exp;

public class DecBeforeExpression extends Col1Expression
{
    public DecBeforeExpression() {
        this.setOperator("--");
    }
    
    protected DecBeforeExpression(final DecBeforeExpression from, final ShareExpValue s) {
        super((Col1Expression)from, s);
    }
    
    public AbstractExpression dup(final ShareExpValue s) {
        return (AbstractExpression)new DecBeforeExpression(this, s);
    }
    
    protected long operateLong(long val) {
        --val;
        this.exp.let(val, this.pos);
        return val;
    }
    
    protected double operateDouble(double val) {
        --val;
        this.exp.let(val, this.pos);
        return val;
    }
    
    public Object evalObject() {
        Object val = this.exp.evalObject();
        val = this.share.oper.inc(val, -1);
        this.exp.let(val, this.pos);
        return val;
    }
    
    protected AbstractExpression replace() {
        this.exp = this.exp.replaceVar();
        return this.share.repl.replaceVar1(this);
    }
}
